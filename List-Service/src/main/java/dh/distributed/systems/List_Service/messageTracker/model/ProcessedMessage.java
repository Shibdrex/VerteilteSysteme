package dh.distributed.systems.List_Service.messageTracker.model;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import dh.distributed.systems.List_Service.TodoMessage;
import dh.distributed.systems.List_Service.list.model.ListMessage;
import dh.distributed.systems.List_Service.listelement.model.ElementMessage;
import dh.distributed.systems.List_Service.listelement.model.ElementPriority;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents the processed message entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "processed_message")
public class ProcessedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "messageID", columnDefinition = "VARCHAR(255)", nullable = false)
    private UUID messageId;

    @Column(name = "userID", columnDefinition = "INT", nullable = false)
    private Integer userId;

    @Column(name = "action", columnDefinition = "TEXT", nullable = false)
    private String action;

    @Column(name = "listID", columnDefinition = "INT", nullable = true)
    private Integer listId;

    @Column(name = "title", columnDefinition = "TEXT", nullable = true)
    private String title;

    @Column(name = "favorite", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean favorite;

    @Column(name = "elementID", columnDefinition = "INT", nullable = true)
    private Integer elementId;

    @Column(name = "status", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", columnDefinition = "VARCHAR(255)", nullable = true)
    private ElementPriority priority;

    @ElementCollection
    @Column(name = "tags", columnDefinition = "VARCHAR(255)", nullable = true)
    private Set<String> tags;

    @Temporal(TemporalType.DATE)
    @Column(name = "dueDate", columnDefinition = "DATE", nullable = true)
    private Date dueDate;

    @Column(name = "name", columnDefinition = "TEXT", nullable = true)
    private String name;

    public ProcessedMessage(TodoMessage message) {
        if (message instanceof ListMessage listMessage) {
            this.messageId = listMessage.getMessageID();
            this.userId = listMessage.getUserID();
            this.action = listMessage.getAction();
            this.listId = listMessage.getListID();
            if (listMessage.getList() == null) return; // Exit early if there is no list-object in the message
            this.title = listMessage.getList().getTitle();
            this.favorite = listMessage.getList().getFavorite();
        }
        if (message instanceof ElementMessage elementMessage) {
            this.messageId = elementMessage.getMessageID();
            this.userId = elementMessage.getUserID();
            this.action = elementMessage.getAction();
            this.listId = elementMessage.getListID();
            if (elementMessage.getElement() == null) return; // Exit early if there is no element-object in the message
            this.elementId = elementMessage.getElement().getId();
            this.status = elementMessage.getElement().getStatus();
            this.priority = elementMessage.getElement().getPriority();
            this.tags = elementMessage.getElement().getTags();
            this.dueDate = elementMessage.getElement().getDueDate();
            this.name = elementMessage.getElement().getName();
        }
    }
}
