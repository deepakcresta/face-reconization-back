package deepak.imgae.based.system.image_processing;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final String uploadDir = "D:/BCA Eight Semester/project/Souce Code/image_base-attendance-system/uploads/images/";

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

    @Override
    public List<ImageDTO> getAllImageIdsAndPaths() {
        return null;
    }

    // In ImageServiceImpl.java (Implementation)
//    @Override
//    public List<Image> getAllImages() {
//        return imageRepository.findAll(); // Assuming you have a JPA repository for images
//    }
//    @Override
//    public List<ImageDTO> getAllImageIdsAndPaths() {
//        return imageRepository.findAll().stream()
//                .map(image -> {
//                    ImageDTO imageDTO = new ImageDTO();
//                    imageDTO.setId(image.getId()); // Assuming you have an ID in your Image entity
//                    imageDTO.setPath(image.getPath()); // Assuming you have a path in your Image entity
//                    return imageDTO;
//                })
//                .collect(Collectors.toList());
//    }
    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Map Image entity to ImageDTO
    private ImageDTO mapToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setName(image.getName());
        imageDTO.setType(image.getType());
        imageDTO.setPath(image.getPath());
        return imageDTO;
    }
    @Override
    public boolean updatePresentStatus(Long id) {
        // Find the image by ID
        Image image = imageRepository.findById(id).orElse(null);

        // If the image is found, update the presentStatus to true
        if (image != null) {
            image.setPresentStatus(true); // Set to true as the requirement states
            imageRepository.save(image);   // Save the updated image entity
            return true;                   // Indicate that the update was successful
        }

        // Return false if the image is not found
        return false;
    }
}
