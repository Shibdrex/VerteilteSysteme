package dh.distributed.systems.User_Service.model;

import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

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
    private Integer id;

    private VarcharJdbcType firstname;

    private VarcharJdbcType lastname;

    private VarcharJdbcType password;

    private VarcharJdbcType email;
}
