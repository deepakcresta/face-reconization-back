package deepak.imgae.based.system.image_processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
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
    // Endpoint to get all image metadata
//    @GetMapping("/images")
//    public ResponseEntity<?> getAllImages() {
//        List<ImageDTO> images = imageService.getAllImages();
//        if (!images.isEmpty()) {
//            return new ResponseEntity<>(images, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

    @GetMapping("/view")
    public ResponseEntity<?> getImage(@RequestParam String imagePath) {
        try {
            // Construct the path to the image
            Path path = Paths.get(imagePath);
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            // Load the image as a resource
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                // Return the image with content-disposition to view it inline
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path.getFileName().toString() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path)) // Set the content type
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/all-ids-and-paths")
    public ResponseEntity<List<ImageDTO>> getAllImageIdsAndPaths() {
        List<ImageDTO> images = imageService.getAllImageIdsAndPaths();
        if (!images.isEmpty()) {
            return new ResponseEntity<>(images, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
        @GetMapping("/list")
        public ResponseEntity<List<ImageDTO>> getAllImages() {
            try {
                List<ImageDTO> images = imageService.getAllImages();
                return ResponseEntity.ok(images);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    @PutMapping("/{id}/present-status")
    public ResponseEntity<String> updatePresentStatus(@PathVariable Long id) {
        // Call the service to update the present status
        boolean updated = imageService.updatePresentStatus(id);

        // Return response based on whether the update was successful
        if (updated) {
            return ResponseEntity.ok("Present status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with id " + id);
        }
    }
    }

