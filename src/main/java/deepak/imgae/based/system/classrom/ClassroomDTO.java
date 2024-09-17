package deepak.imgae.based.system.classrom;

import lombok.Data;

import java.util.Set;

@Data
public class ClassroomDTO {
    private Long id;

    private String name;
    private String faculty;
    private String section;
    private String classTeacherName;

    private Long teacherId;
    private Set<Long> studentIds;

}
