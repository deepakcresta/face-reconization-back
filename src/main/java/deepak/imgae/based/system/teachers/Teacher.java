package deepak.imgae.based.system.teachers;

import deepak.imgae.based.system.classrom.Classroom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String faculty;
    private String subject;

    @OneToMany(mappedBy = "teacher")
    private Set<Classroom> classrooms = new HashSet<>();

}
