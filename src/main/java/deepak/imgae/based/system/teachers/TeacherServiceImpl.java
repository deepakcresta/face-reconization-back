package deepak.imgae.based.system.teachers;

import deepak.imgae.based.system.classrom.Classroom;
import deepak.imgae.based.system.classrom.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        return teacherRepository.findById(id).map(this::convertToDTO).orElseThrow(() -> new NullPointerException("Teacher not found"));
    }

    @Override
    public TeacherDto createTeacher(TeacherDto teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setAddress(teacherDTO.getAddress());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setFaculty(teacherDTO.getFaculty());
        teacher.setSubject(teacherDTO.getSubject());

//        teacher.setClassrooms(new HashSet<>(classroomRepository.findAllById(teacherDTO.getClassroomIds())));
        return convertToDTO(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDto updateTeacher(Long id, TeacherDto teacherDTO) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NullPointerException("Teacher not found"));
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setClassrooms(new HashSet<>(classroomRepository.findAllById(teacherDTO.getClassroomIds())));
        return convertToDTO(teacherRepository.save(teacher));
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    private TeacherDto convertToDTO(Teacher teacher) {
        TeacherDto teacherDTO = new TeacherDto();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setEmail(teacher.getEmail());
        teacherDTO.setClassroomIds(teacher.getClassrooms().stream().map(Classroom::getId).collect(Collectors.toSet()));
        return teacherDTO;
    }
}
