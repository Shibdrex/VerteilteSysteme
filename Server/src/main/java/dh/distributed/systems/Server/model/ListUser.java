package dh.distributed.systems.Server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUser {

    private Integer id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
}
