package dh.distributed.systems.Server.message;

import java.util.Map;

import dh.distributed.systems.Server.model.TodoList;
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

    public TodoListResponse(TodoList list) {
        this.id = list.getId();
        this.title = list.getTitle();
        this.favorite = list.getFavorite();
    }
}
