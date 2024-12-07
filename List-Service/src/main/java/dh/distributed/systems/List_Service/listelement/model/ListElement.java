package dh.distributed.systems.List_Service.listelement.model;

import java.sql.Date;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dh.distributed.systems.List_Service.list.model.TodoList;
import dh.distributed.systems.List_Service.listUser.model.ListUser;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list_element")
public class ListElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT", nullable = false)
    private Integer id;

    @Column(name = "status", columnDefinition = "BOOLEAN", nullable = false)
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", columnDefinition = "VARCHAR(255)", nullable = false)
    private ElementPriority priority;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "tags", columnDefinition = "VARCHAR(255)", nullable = false)
    private Set<String> tags;

    @Temporal(TemporalType.DATE)
    @Column(name = "dueDate", columnDefinition = "DATE", nullable = false)
    private Date dueDate;
    
    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ListUser user;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TodoList list; 

    public ListElement(Boolean status, ElementPriority priority, Set<String> tags, Date dueDate, String name) {
        this.status = status;
        this.priority = priority;
        this.tags = tags;
        this.dueDate = dueDate;
        this.name = name;
    }
}
