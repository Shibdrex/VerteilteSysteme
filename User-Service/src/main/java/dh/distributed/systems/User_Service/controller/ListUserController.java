package dh.distributed.systems.User_Service.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dh.distributed.systems.User_Service.dto.ListUserResponse;
import dh.distributed.systems.User_Service.manager.ListUserManager;
import dh.distributed.systems.User_Service.model.ListUser;
import dh.distributed.systems.User_Service.transform.ListUserTransformer;
import lombok.AllArgsConstructor;

/**
 * Class is a rest-controller for the list-users. Uses a transformer to create
 * DTOs of the database models, these DTOs are returned to the client making the
 * requests. Injected manager handles database transactions.
 */
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/list-users")
public class ListUserController {

    private final ListUserTransformer transformer;
    private final ListUserManager manager;

    @GetMapping()
    public List<ListUserResponse> get() {
        return this.transformer.getAllAssistantUsers();
    }

    @GetMapping("/containing")
    public List<ListUserResponse> getAllListUsers(@RequestParam(required = false, name = "firstname") String firstname,
            @RequestParam(required = false, name = "lastname") String lastname) {
        if (!firstname.isEmpty())
            return this.transformer.getAllListUsersByFirstname(firstname);
        return this.transformer.getAllListUsersByLastname(lastname);
    }

    @GetMapping("/email")
    public List<ListUserResponse> findByEmail(@RequestParam(required = false, name = "email") String email) {
        return this.transformer.findListUserByEmail(email);
    }

    @GetMapping("/{id}")
    public ListUserResponse getOne(@PathVariable(value = "id") Integer id) {
        return this.transformer.getListUser(id);
    }

    @PostMapping()
    public ResponseEntity<ListUserResponse> post(@RequestBody ListUser user) {
        if (this.manager.isValid(user)) {
            ListUserResponse response = this.transformer.getListUser(this.manager.createListUser(user).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody ListUser user, @PathVariable(value = "id") Integer id) {
        if (this.manager.isValid(user)) {
            ListUserResponse response = this.transformer.getListUser(this.manager.updateListUser(user, id).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListUserResponse> delete(@PathVariable(value = "id") Integer id) {
        ListUserResponse response = this.transformer.getListUser(id);
        this.manager.deleteListUser(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll() {
        this.manager.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
