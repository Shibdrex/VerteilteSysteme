package dh.distributed.systems.List_Service.listUser.dto;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;

import dh.distributed.systems.List_Service.listUser.model.ListUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListUserResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Map<String, String> links;

    public ListUserResponse(ListUser user, EntityModel<ListUser> model) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.links = model.getLinks().toList().stream()
                .collect(Collectors.toMap(link -> link.getRel().value(), link -> link.getHref()));
    }
}