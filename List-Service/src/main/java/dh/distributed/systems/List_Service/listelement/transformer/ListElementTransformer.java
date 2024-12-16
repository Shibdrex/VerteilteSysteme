package dh.distributed.systems.List_Service.listelement.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.listelement.assembler.ListElementModelAssembler;
import dh.distributed.systems.List_Service.listelement.dto.ListElementResponse;
import dh.distributed.systems.List_Service.listelement.manager.ListElementManager;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import lombok.AllArgsConstructor;

/**
 * Handles converting {@link ListElement} objects to {@link ListElementResponse}
 * objects, which contain HATEOAS-links.
 */
@AllArgsConstructor
@Service
public class ListElementTransformer {

    private final ListElementManager manager;
    private final ListElementModelAssembler assembler;

    private ListElementResponse transformToResponse(ListElement element) {
        EntityModel<ListElement> model = this.assembler.toModel(element);
        return new ListElementResponse(element, model);
    }

    public ListElementResponse getListElement(Integer ID) {
        ListElement element = this.manager.getElement(ID);
        EntityModel<ListElement> model = this.assembler.toModel(element);
        return new ListElementResponse(element, model);
    }

    public List<ListElementResponse> getAllListElements() {
        return this.manager.getAllElements().stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    public List<ListElementResponse> getAllElementsByUserID(Integer userID) {
        List<ListElement> found = this.manager.getAllElementsByUserID(userID);
        if (found == null) {
            return new ArrayList<ListElementResponse>();
        }
        return found.stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    public List<ListElementResponse> getAllElementsByListID(Integer listID) {
        List<ListElement> found = this.manager.getAllElementsByListID(listID);
        if (found == null) {
            return new ArrayList<ListElementResponse>();
        }
        return found.stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }
}
