package deepak.imgae.based.system.user_login_module;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String userName;
    private String firstName;
    private String lastName;
    private String password;

}
