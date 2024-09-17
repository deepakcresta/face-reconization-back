package deepak.imgae.based.system.teachers;

import lombok.Data;

import java.util.Set;

@Data
public class TeacherDto {
    private Long id;
    private String name;
    private String email;
    private String rollNo;
    private String address;
    private String phoneNumber;
    private String faculty;
    private String subject;
    private Set<Long> classroomIds;

    // Getters and Setters
}
