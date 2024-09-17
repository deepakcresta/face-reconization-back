package deepak.imgae.based.system.classrom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping("/{id}")
    public ClassroomDTO getClassroomById(@PathVariable Long id) {
        return classroomService.getClassroomById(id);
    }

    @PostMapping()
    public ClassroomDTO createClassroom(@RequestBody ClassroomDTO classroomDTO) {
        return classroomService.createClassroom(classroomDTO);
    }

    @PutMapping("/{id}")
    public ClassroomDTO updateClassroom(@PathVariable Long id, @RequestBody ClassroomDTO classroomDTO) {
        return classroomService.updateClassroom(id, classroomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
    }
}
