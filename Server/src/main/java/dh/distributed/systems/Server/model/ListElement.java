package dh.distributed.systems.Server.model;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListElement {

    private Integer id;
    private Boolean status;
    private ElementPriority priority;
    private Set<String> tags;
    private Date dueDate;
    private String name;

    public ListElement(Boolean status, ElementPriority priority, Set<String> tags, Date dueDate, String name) {
        this.status = status;
        this.priority = priority != null ? priority : ElementPriority.MEDIUM;
        this.tags = tags;
        this.dueDate = dueDate;
        this.name = name;
    }

    public void setPriority(ElementPriority priority) {
        this.priority = priority != null ? priority : ElementPriority.MEDIUM;
    }
}
