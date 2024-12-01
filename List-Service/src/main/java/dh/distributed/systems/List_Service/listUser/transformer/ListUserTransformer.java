package dh.distributed.systems.List_Service.listUser.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.listUser.assembler.ListUserModelAssembler;
import dh.distributed.systems.List_Service.listUser.dto.ListUserResponse;
import dh.distributed.systems.List_Service.listUser.manager.ListUserManager;
import dh.distributed.systems.List_Service.listUser.model.ListUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListUserTransformer {

    private final ListUserManager manager;
    private final ListUserModelAssembler assembler;

    private ListUserResponse transformToResponse(ListUser user) {
        EntityModel<ListUser> model = this.assembler.toModel(user);
        return new ListUserResponse(user, model);
    }

    public ListUserResponse getListUser(Integer id) {
        ListUser user = this.manager.getListUser(id);
        EntityModel<ListUser> model = this.assembler.toModel(user);
        return new ListUserResponse(user, model);
    }

    public List<ListUserResponse> getAllAssistantUsers() {
        return this.manager.getAllListUsers().stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    public List<ListUserResponse> getAllListUsersByFirstname(String firstname) {
        return this.manager.getAllListUsersWithFirstname(firstname).stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    public List<ListUserResponse> getAllListUsersByLastname(String lastname) {
        return this.manager.getAllListUsersWithLastname(lastname).stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    public List<ListUserResponse> findListUserByEmail(String email) {
        List<ListUser> found = this.manager.findByEmail(email);
        if (found == null) {
            return new ArrayList<ListUserResponse>();
        }
        return found.stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }
}
