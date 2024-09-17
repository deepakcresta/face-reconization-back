package deepak.imgae.based.system.teachers;


import java.util.List;

public interface TeacherService {
    List<TeacherDto> getAllTeachers();
    TeacherDto getTeacherById(Long id);
    TeacherDto createTeacher(TeacherDto teacherDTO);
    TeacherDto updateTeacher(Long id, TeacherDto teacherDTO);
    void deleteTeacher(Long id);
}
