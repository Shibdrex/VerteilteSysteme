package dh.distributed.systems.List_Service.listelement.dto;

import java.sql.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;

import dh.distributed.systems.List_Service.listelement.model.ElementPriority;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public ListElementResponse(ListElement element, EntityModel<ListElement> model) {
        this.id = element.getId();
        this.status = element.getStatus();
        this.priority = element.getPriority();
        this.tags = element.getTags();
        this.dueDate = element.getDueDate();
        this.name = element.getName();
        this.links = model.getLinks().toList().stream()
                .collect(Collectors.toMap(link -> link.getRel().value(), link -> link.getHref()));
    }
}
