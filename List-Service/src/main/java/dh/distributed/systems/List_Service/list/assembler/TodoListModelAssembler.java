package dh.distributed.systems.List_Service.list.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import dh.distributed.systems.List_Service.list.controller.TodoListController;
import dh.distributed.systems.List_Service.list.model.TodoList;

@Component
public class TodoListModelAssembler implements RepresentationModelAssembler<TodoList, EntityModel<TodoList>> {
    
    @Override
    public @NonNull EntityModel<TodoList> toModel(@NonNull TodoList list) {

        return EntityModel.of(list,
        linkTo(methodOn(TodoListController.class).getOne(list.getId())).withSelfRel(),
        linkTo(methodOn(TodoListController.class).get()).withRel("lists"));
    }
}