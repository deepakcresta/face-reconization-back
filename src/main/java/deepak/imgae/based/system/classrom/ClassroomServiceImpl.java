package deepak.imgae.based.system.classrom;

import deepak.imgae.based.system.students.StudentRepository;
import deepak.imgae.based.system.teachers.Teacher;
import deepak.imgae.based.system.teachers.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<ClassroomDTO> getClassrooms() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO getClassroom(Long id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        return classroom.map(this::convertToDto).orElse(null);
    }

    @Override
    public List<ClassroomDTO> getAllClassrooms() {
        return null;
    }

    @Override
    public ClassroomDTO getClassroomById(Long id) {
        return null;
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = convertToEntity(classroomDTO);
        Classroom savedClassroom = classroomRepository.save(classroom);
        return convertToDto(savedClassroom);
    }

    @Override
    public ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(id);
        if (!classroomOptional.isPresent()) {
            throw new RuntimeException("Classroom not found");
        }

        Optional<Teacher> teacher = teacherRepository.findById(classroomDTO.getTeacherId());
        if (!teacher.isPresent()) {
            throw new RuntimeException("Teacher not found");
        }

        Classroom classroom = classroomOptional.get();
        classroom.setName(classroomDTO.getName());
        classroom.setTeacher(teacher.get());
        classroom.setStudents(classroomDTO.getStudentIds().stream()
                .map(studentId -> studentRepository.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found")))
                .collect(Collectors.toSet()));

        Classroom updatedClassroom = classroomRepository.save(classroom);
        return convertToDto(updatedClassroom);
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }

    private ClassroomDTO convertToDto(Classroom classroom) {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(classroom.getId());
        classroomDTO.setName(classroom.getName());
        classroomDTO.setSection(classroom.getSection());
        classroomDTO.setClassTeacherName(classroom.getClassTeacherName());
        classroomDTO.setFaculty(classroom.getFaculty());

//        classroomDTO.setTeacherId(classroom.getTeacher().getId());
//        classroomDTO.setStudentIds(
//                classroom.getStudents().stream()
//                        .map(student -> student.getId())
//                        .collect(Collectors.toSet())
//        );
        return classroomDTO;
    }

    private Classroom convertToEntity(ClassroomDTO classroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setName(classroomDTO.getName());
        classroom.setFaculty(classroomDTO.getFaculty());
        classroom.setSection(classroomDTO.getSection());
        classroom.setClassTeacherName(classroomDTO.getClassTeacherName());
        if (!ObjectUtils.isEmpty(classroomDTO.getTeacherId())) {
            classroom.setTeacher(teacherRepository.findById(classroomDTO.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found")));
        }
        if (!ObjectUtils.isEmpty(classroomDTO.getStudentIds())) {
            classroom.setStudents(
                    classroomDTO.getStudentIds().stream()
                            .map(studentId -> studentRepository.findById(studentId)
                                    .orElseThrow(() -> new RuntimeException("Student not found")))
                            .collect(Collectors.toSet())
            );
        }
        return classroom;
    }
}
