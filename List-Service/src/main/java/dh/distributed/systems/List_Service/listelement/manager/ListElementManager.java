package dh.distributed.systems.List_Service.listelement.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.list.exception.TodoListNotFoundException;
import dh.distributed.systems.List_Service.list.repository.TodoListRepository;
import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;
import dh.distributed.systems.List_Service.listUser.repository.ListUserRepository;
import dh.distributed.systems.List_Service.listelement.exception.ListElementNotFoundException;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import dh.distributed.systems.List_Service.listelement.repository.ListElementRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListElementManager {

    private final ListElementRepository listElementRepository;

    private final ListUserRepository listUserRepository;

    private final TodoListRepository listRepository;

    public Boolean isValid(final ListElement element) {
        return element != null
                && element.getStatus() != null
                && element.getPriority() != null
                && element.getTags() != null
                && element.getDueDate() != null
                && element.getName() != null;
    }

    public List<ListElement> getAllElements() {
        return this.listElementRepository.findAll();
    }

    public List<ListElement> getAllElementsByUserID(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        return this.listElementRepository.findByUserId(userID);
    }

    public List<ListElement> getAllElementsByListID(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        return this.listElementRepository.findByListId(listID);
    }

    public ListElement getElement(final Integer ID) {
        return this.listElementRepository.findById(ID)
                .orElseThrow(() -> new ListElementNotFoundException(ID));
    }

    public ListElement createElement(final Integer userID, final Integer listID, final ListElement element) {
        if (!isValid(element)) {
            throw new IllegalArgumentException("Invalid element data.");
        }
        ListElement elem = this.listUserRepository.findById(userID).map(user -> {
            element.setUser(user);
            return element;
        }).orElseThrow(() -> new ListUserNotFoundException(userID));
        elem = this.listRepository.findById(listID).map(list -> {
            element.setList(list);
            return this.listElementRepository.save(element);
        }).orElseThrow(() -> new TodoListNotFoundException(listID));
        return elem;
    }

    public ListElement updateElement(final ListElement newElement, final Integer ID) {
        if (!isValid(newElement)) {
            throw new IllegalArgumentException("Invalid element data.");
        }
        return this.listElementRepository.findById(ID)
                .map(element -> {
                    element.setStatus(newElement.getStatus());
                    element.setPriority(newElement.getPriority());
                    element.setTags(newElement.getTags());
                    element.setDueDate(newElement.getDueDate());
                    element.setName(newElement.getName());
                    return this.listElementRepository.save(element);
                })
                .orElseGet(() -> this.listElementRepository.save(newElement));
    }

    public void deleteElement(final Integer ID) {
        ListElement element = this.listElementRepository.findById(ID)
                .orElseThrow(() -> new ListElementNotFoundException(ID));
        this.listElementRepository.delete(element);
    }

    public void deleteAllByUser(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        this.listElementRepository.deleteByUserId(userID);
    }

    public void deleteAllByList(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        this.listElementRepository.deleteByListId(listID);
    }

    public void deleteAll() {
        this.listElementRepository.deleteAll();
    }
}
