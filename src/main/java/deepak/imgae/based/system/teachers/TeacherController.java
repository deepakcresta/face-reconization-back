package deepak.imgae.based.system.teachers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private  final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public TeacherDto createTeacher(@RequestBody TeacherDto teacherDTO) {
        return teacherService.createTeacher(teacherDTO);
    }

    @PutMapping("/{id}")
    public TeacherDto updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDTO) {
        return teacherService.updateTeacher(id, teacherDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
