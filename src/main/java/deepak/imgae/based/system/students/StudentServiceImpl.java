package deepak.imgae.based.system.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = mapToEntity(studentDTO);
        Student newStudent = studentRepository.save(student);
        return mapToDTO(newStudent);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student"));
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setRollNo(studentDTO.getRollNo());
        student.setAddress(studentDTO.getAddress());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setClassFaculty(studentDTO.getClassFaculty());
//        student.setImage(studentDTO.getImage());
        Student updatedStudent = studentRepository.save(student);
        return mapToDTO(updatedStudent);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student"));
        return mapToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student"));
        studentRepository.delete(student);
    }

    private StudentDTO mapToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setRollNo(student.getRollNo());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setClassFaculty(student.getClassFaculty());
        studentDTO.setImagePath(student.getImagePath());
//        studentDTO.setImage(student.getImage());
        return studentDTO;
    }

    private Student mapToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setRollNo(studentDTO.getRollNo());
        student.setAddress(studentDTO.getAddress());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setClassFaculty(studentDTO.getClassFaculty());
        student.setImagePath(studentDTO.getImagePath());
//        student.setImage(studentDTO.getImage());
        return student;
    }
}
