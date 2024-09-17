package deepak.imgae.based.system.students;

import lombok.Data;

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
}
