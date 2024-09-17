package deepak.imgae.based.system.classrom;

import java.util.List;

public interface ClassroomService {
    List<ClassroomDTO> getClassrooms();

    ClassroomDTO getClassroom(Long id);

    List<ClassroomDTO> getAllClassrooms();
    ClassroomDTO getClassroomById(Long id);
    ClassroomDTO createClassroom(ClassroomDTO classroomDTO);
    ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO);
    void deleteClassroom(Long id);
}
