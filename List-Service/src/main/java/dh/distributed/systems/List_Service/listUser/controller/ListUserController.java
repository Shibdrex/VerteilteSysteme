package dh.distributed.systems.List_Service.listUser.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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

import dh.distributed.systems.List_Service.listUser.assembler.ListUserModelAssembler;
import dh.distributed.systems.List_Service.listUser.manager.ListUserManager;
import dh.distributed.systems.List_Service.listUser.model.ListUser;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/list-users")
public class ListUserController {

    private final ListUserManager manager;

    private final ListUserModelAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<ListUser>> get() {
        List<EntityModel<ListUser>> users = this.manager.getAllListUsers().stream()
        .map(this.assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(ListUserController.class).get()).withSelfRel());
    }

    @GetMapping("/containing")
    public CollectionModel<EntityModel<ListUser>> getAllListUsers(@RequestParam(required = false, name = "firstname") String firstname) {
        List<ListUser> users = this.manager.getAllListUsersWithFirstname(firstname);
        if (users == null)
            return CollectionModel.empty();
        
        List<EntityModel<ListUser>> listUsers = users.stream()
        .map(this.assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(listUsers, linkTo(methodOn(ListUserController.class).get()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ListUser> getOne(@PathVariable Integer id) {
        return this.assembler.toModel(this.manager.getListUser(id));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<ListUser>> post(@RequestBody ListUser user) {
        if (this.manager.isValid(user)) {
            EntityModel<ListUser> model = this.assembler.toModel(this.manager.createListUser(user));
            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody ListUser user, @PathVariable Integer id) {
        if (this.manager.isValid(user)) {
            EntityModel<ListUser> model = this.assembler.toModel(this.manager.updateListUser(user, id));
            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListUser> delete(@PathVariable Integer id) {
        if (id >= 0) {
            ListUser deletedUser = this.manager.getListUser(id);
            this.manager.deleteListUser(id);
            return ResponseEntity.ok(deletedUser);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll() {
        this.manager.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email")
    public CollectionModel<EntityModel<ListUser>> findByEmail(@RequestParam(required = false, name = "email") String email) {
        List<ListUser> users = this.manager.findByEmail(email);
        if (users == null)
            return CollectionModel.empty();
        
        List<EntityModel<ListUser>> listUsers = users.stream()
        .map(this.assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(listUsers, linkTo(methodOn(ListUserController.class).get()).withSelfRel());
    }
}
