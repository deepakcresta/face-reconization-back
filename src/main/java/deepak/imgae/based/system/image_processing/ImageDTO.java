package deepak.imgae.based.system.image_processing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private String name;
    private String type;
    private String path;
    private boolean presentStatus;

    public ImageDTO(Long id, String name, String type, String path) {
    }
}
