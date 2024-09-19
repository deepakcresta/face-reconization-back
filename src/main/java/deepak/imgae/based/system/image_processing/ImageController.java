package deepak.imgae.based.system.image_processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Upload an image
    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageDTO imageDTO = imageService.uploadImage(file);
            return new ResponseEntity<>(imageDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get image metadata by ID
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        Optional<ImageDTO> imageDTO = imageService.getImage(id);
        return imageDTO.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Load image file by ID (downloads the image)
    @GetMapping("/load/{id}")
    public ResponseEntity<byte[]> loadImageFileById(@PathVariable Long id) {
        Optional<ImageDTO> imageDTO = imageService.getImage(id);
        if (imageDTO.isPresent()) {
            try {
                byte[] imageData = imageService.loadImageFromFileSystem(imageDTO.get().getPath());
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // You can dynamically set content type based on the image type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDTO.get().getName() + "\"")
                        .body(imageData);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an image
    @PutMapping("/update/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            ImageDTO updatedImage = imageService.updateImage(id, file);
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an image
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

