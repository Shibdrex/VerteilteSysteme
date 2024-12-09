package dh.distributed.systems.Server.message;

import java.sql.Date;
import java.util.Map;
import java.util.Set;

import dh.distributed.systems.Server.model.ElementPriority;
import dh.distributed.systems.Server.model.ListElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object of {@link ListElement}, adds HATEOAS-links.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListElementResponse {
    private Integer id;
    private Boolean status;
    private ElementPriority priority;
    private Set<String> tags;
    private Date dueDate;
    private String name;
    private Map<String, String> links;

    public ListElementResponse(ListElement element) {
        this.id = element.getId();
        this.status = element.getStatus();
        this.priority = element.getPriority();
        this.tags = element.getTags();
        this.dueDate = element.getDueDate();
        this.name = element.getName();
    }
}
