package dh.distributed.systems.List_Service.list.controller;

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
import org.springframework.web.bind.annotation.RestController;

import dh.distributed.systems.List_Service.list.dto.TodoListResponse;
import dh.distributed.systems.List_Service.list.manager.TodoListManager;
import dh.distributed.systems.List_Service.list.model.TodoList;
import dh.distributed.systems.List_Service.list.transformer.TodoListTransformer;
import lombok.AllArgsConstructor;

/**
 * Class is a rest-controller for the todo-lists.
 * Uses a transformer to create DTOs of the database models, these DTOs are
 * returned to the client making the request.
 * Injected manager handles database transactions.
 */
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/todo-lists")
public class TodoListController {

    private final TodoListTransformer transformer;
    private final TodoListManager manager;

    @GetMapping()
    public List<TodoListResponse> get() {
        return this.transformer.getAllTodoLists();
    }

    @GetMapping("/user{userID}")
    public List<TodoListResponse> getAllListsByUserId(
            @PathVariable(value = "userID") Integer userID) {
        return this.transformer.getAllTodoListsByUserID(userID);
    }

    @GetMapping("/{id}")
    public TodoListResponse getOne(@PathVariable(value = "id") Integer id) {
        return this.transformer.getTodoList(id);
    }

    @PostMapping("/user/{userID}")
    public ResponseEntity<TodoListResponse> post(@PathVariable(value = "userID") Integer userID,
            @RequestBody TodoList list) {
        if (this.manager.isValid(list)) {
            TodoListResponse response = this.transformer.getTodoList(this.manager.createList(userID, list).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody TodoList list, @PathVariable(value = "id") Integer id) {
        if (this.manager.isValid(list)) {
            TodoListResponse response = this.transformer.getTodoList(this.manager.updateList(list, id).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoListResponse> delete(@PathVariable(value = "id") Integer id) {
        TodoListResponse response = this.transformer.getTodoList(id);
        this.manager.deleteList(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userID}")
    public List<TodoListResponse> deleteAllListsOfUser(@PathVariable(value = "userID") Integer userID) {
        List<TodoListResponse> responses = this.transformer.getAllTodoListsByUserID(userID);
        this.manager.deleteAllByUser(userID);
        return responses;
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll() {
        this.manager.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
