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

    private Boolean status;
    private ElementPriority priority;
    private Set<String> tags;
    private Date dueDate;
    private String name;

}
