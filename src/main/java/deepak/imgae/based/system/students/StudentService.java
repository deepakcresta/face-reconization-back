package deepak.imgae.based.system.students;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    void deleteStudent(Long id);

    List<StudentDTO> getAllStudentIdsAndImages();

}
