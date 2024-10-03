package deepak.imgae.based.system.students;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String rollNo;
    private String address;
    private String phoneNumber;
    private String classFaculty;
    private byte[] image;
    private String imagePath;
    private MultipartFile imageFile;
    private boolean presentStatus;
}
