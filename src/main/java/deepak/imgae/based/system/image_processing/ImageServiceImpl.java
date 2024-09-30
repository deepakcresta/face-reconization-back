package deepak.imgae.based.system.image_processing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final String uploadDir = "uploads/images/";

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageDTO uploadImage(MultipartFile file) throws IOException {
        // Ensure the upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate the file path
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save the file to disk
        Files.write(filePath, file.getBytes());

        // Save the image details in the database
        Image image = new Image();
        image.setName(fileName);
        image.setType(file.getContentType());
        image.setPath(filePath.toString());

        Image savedImage = imageRepository.save(image);

        // Convert to DTO
        return new ImageDTO(savedImage.getId(), savedImage.getName(), savedImage.getType(), savedImage.getPath());
    }

    @Override
    public Optional<ImageDTO> getImage(Long id) {
        return imageRepository.findById(id)
                .map(image -> new ImageDTO(image.getId(), image.getName(), image.getType(), image.getPath()));
    }

    @Override
    public ImageDTO updateImage(Long id, MultipartFile file) throws IOException {
        Optional<Image> optionalImage = imageRepository.findById(id);

        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();

            // Delete old image file
            Files.deleteIfExists(Paths.get(image.getPath()));

            // Save new file
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            // Update the image details
            image.setName(fileName);
            image.setType(file.getContentType());
            image.setPath(filePath.toString());

            Image updatedImage = imageRepository.save(image);

            // Convert to DTO
            return new ImageDTO(updatedImage.getId(), updatedImage.getName(), updatedImage.getType(), updatedImage.getPath());
        }

        throw new RuntimeException("Image not found");
    }

    @Override
    public void deleteImage(Long id) throws IOException {
        Optional<Image> optionalImage = imageRepository.findById(id);

        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();

            // Delete the image file from the disk
            Files.deleteIfExists(Paths.get(image.getPath()));

            // Delete the image record from the database
            imageRepository.deleteById(id);
        } else {
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public byte[] loadImageFromFileSystem(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readAllBytes(filePath);
    }

    // In ImageServiceImpl.java (Implementation)
    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll(); // Assuming you have a JPA repository for images
    }

}
