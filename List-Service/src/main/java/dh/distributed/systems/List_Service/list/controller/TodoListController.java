package dh.distributed.systems.List_Service.list.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dh.distributed.systems.List_Service.list.assembler.TodoListModelAssembler;
import dh.distributed.systems.List_Service.list.manager.TodoListManager;
import dh.distributed.systems.List_Service.list.model.TodoList;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/todo-lists")
public class TodoListController {

    private final TodoListManager manager;

    private final TodoListModelAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<TodoList>> get() {
        List<EntityModel<TodoList>> lists = this.manager.getAllLists().stream()
                .map(this.assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lists, linkTo(methodOn(TodoListController.class).get()).withSelfRel());
    }

    @GetMapping("/users/{userID}/lists")
    public CollectionModel<EntityModel<TodoList>> getAllListsByUserId(
            @PathVariable(value = "userID") Integer userID) {
        List<EntityModel<TodoList>> lists = this.manager.getAllListsByUserID(userID).stream()
                .map(this.assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lists, linkTo(methodOn(TodoListController.class).get()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TodoList> getOne(@PathVariable(value = "id") Integer id) {
        return this.assembler.toModel(this.manager.getList(id));
    }

    @PostMapping("/users/{userID}")
    public ResponseEntity<EntityModel<TodoList>> post(@PathVariable(value = "userID") Integer userID,
            @RequestBody TodoList list) {
        if (this.manager.isValid(list)) {
            EntityModel<TodoList> model = this.assembler.toModel(this.manager.createList(userID, list));
            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoList> delete(@PathVariable(value = "id") Integer id) {
        if (id >= 0) {
            TodoList deletedList = this.manager.getList(id);
            this.manager.deleteList(id);
            return ResponseEntity.ok(deletedList);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/users/{userID}")
    public CollectionModel<EntityModel<TodoList>> deleteAllListsOfUser(@PathVariable(value = "userID") Integer userID) {
        if (userID >= 0) {
            List<EntityModel<TodoList>> lists = this.manager.getAllListsByUserID(userID).stream()
                    .map(this.assembler::toModel)
                    .collect(Collectors.toList());
            this.manager.deleteAllByUser(userID);
            return CollectionModel.of(lists, linkTo(methodOn(TodoListController.class).get()).withSelfRel());
        }
        return CollectionModel.empty();
    }
}
