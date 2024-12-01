package dh.distributed.systems.List_Service.list.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.list.assembler.TodoListModelAssembler;
import dh.distributed.systems.List_Service.list.dto.TodoListResponse;
import dh.distributed.systems.List_Service.list.manager.TodoListManager;
import dh.distributed.systems.List_Service.list.model.TodoList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoListTransformer {

    private final TodoListManager manager;
    private final TodoListModelAssembler assembler;


    private TodoListResponse transformToResponse(TodoList list) {
        EntityModel<TodoList> model = this.assembler.toModel(list);
        return new TodoListResponse(list, model);
    }


    public TodoListResponse getTodoList(Integer ID) {
        TodoList list = this.manager.getList(ID);
        EntityModel<TodoList> model = this.assembler.toModel(list);
        return new TodoListResponse(list, model);
    }


    public List<TodoListResponse> getAllTodoLists() {
        return this.manager.getAllLists().stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }


    public List<TodoListResponse> getAllTodoListsByUserID(Integer userID) {
        List<TodoList> found = this.manager.getAllListsByUserID(userID);
        if (found == null) {
            return new ArrayList<TodoListResponse>();
        }
        return found.stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }
}
