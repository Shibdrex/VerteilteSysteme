package dh.distributed.systems.Server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents the todo-list entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    private Integer id;
    private String title;
    private Boolean favorite;

    public TodoList(String title, Boolean favorite) {
        this.title = title;
        this.favorite = favorite;
    }
}
