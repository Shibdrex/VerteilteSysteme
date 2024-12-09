package dh.distributed.systems.List_Service.list.dto;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;

import dh.distributed.systems.List_Service.list.model.TodoList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object of {@link TodoList}, adds HATEOAS-links.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoListResponse {
    private Integer id;
    private String title;
    private Boolean favorite;
    private Map<String, String> links;

    public TodoListResponse(TodoList list, EntityModel<TodoList> model) {
        this.id = list.getId();
        this.title = list.getTitle();
        this.favorite = list.getFavorite();
        this.links = model.getLinks().toList().stream()
                .collect(Collectors.toMap(link -> link.getRel().value(), link -> link.getHref()));
    }

    public TodoListResponse(TodoList list) {
        this.id = list.getId();
        this.title = list.getTitle();
        this.favorite = list.getFavorite();
    }
}
