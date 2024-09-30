package deepak.imgae.based.system.image_processing;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    ImageDTO uploadImage(MultipartFile file) throws IOException;

    Optional<ImageDTO> getImage(Long id);

    ImageDTO updateImage(Long id, MultipartFile file) throws IOException;

    void deleteImage(Long id) throws IOException;

    byte[] loadImageFromFileSystem(String path) throws IOException;
    // In ImageService.java (Interface)
    public List<Image> getAllImages();


}
