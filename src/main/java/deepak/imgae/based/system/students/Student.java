package deepak.imgae.based.system.students;

import deepak.imgae.based.system.classrom.Classroom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String rollNo;
    private String address;
    private String phoneNumber;
    private String classFaculty;
    private String imagePath;

    @ManyToMany(mappedBy = "students")
    private Set<Classroom> classrooms = new HashSet<>();
}
