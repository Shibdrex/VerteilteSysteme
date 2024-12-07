package dh.distributed.systems.User_Service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list_user",
    uniqueConstraints = 
            @UniqueConstraint(columnNames = {"email"}))
public class ListUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT", nullable = false)
    private Integer id;

    @Column(name = "firstname", columnDefinition = "TEXT", nullable = false)
    private String firstname;

    @Column(name = "lastname", columnDefinition = "TEXT", nullable = false)
    private String lastname;

    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "TEXT", nullable = false)
    private String email;

    public ListUser(String firstname, String lastname, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }
}
