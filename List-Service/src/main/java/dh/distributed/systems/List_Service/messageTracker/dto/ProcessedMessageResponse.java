package dh.distributed.systems.List_Service.messageTracker.dto;

import java.sql.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;

import dh.distributed.systems.List_Service.listelement.model.ElementPriority;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessedMessageResponse {
    private Integer id;
    private UUID messageId;
    private Integer userId;
    private String action;
    private Integer listId;
    private String title;
    private Boolean favorite;
    private Integer elementId;
    private Boolean status;
    private ElementPriority priority;
    private Set<String> tags;
    private Date dueDate;
    private String name;
    private Map<String, String> links;

    public ProcessedMessageResponse(ProcessedMessage procMessage, EntityModel<ProcessedMessage> model) {
        this.id = procMessage.getId();
        this.messageId = procMessage.getMessageId();
        this.userId = procMessage.getUserId();
        this.action = procMessage.getAction();
        this.listId = procMessage.getListId();
        this.title = procMessage.getTitle();
        this.favorite = procMessage.getFavorite();
        this.elementId = procMessage.getElementId();
        this.status = procMessage.getStatus();
        this.priority = procMessage.getPriority();
        this.tags = procMessage.getTags();
        this.dueDate = procMessage.getDueDate();
        this.name = procMessage.getName();
        this.links = model.getLinks().toList().stream()
                .collect(Collectors.toMap(link -> link.getRel().value(), link -> link.getHref()));
    }
}
