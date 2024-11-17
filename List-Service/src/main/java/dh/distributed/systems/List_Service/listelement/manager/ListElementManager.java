package dh.distributed.systems.List_Service.listelement.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.list.exception.TodoListNotFoundException;
import dh.distributed.systems.List_Service.list.repository.TodoListRepository;
import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;
import dh.distributed.systems.List_Service.listUser.repository.ListUserRepository;
import dh.distributed.systems.List_Service.listelement.exception.ListElementNotFoundException;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import dh.distributed.systems.List_Service.listelement.repository.ListElementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListElementManager {

    private final ListElementRepository listElementRepository;

    private final ListUserRepository listUserRepository;

    private final TodoListRepository listRepository;

    public Boolean isValid(final ListElement element) {
        if (element != null
                && element.getStatus() != null
                && element.getPriority() != null
                && element.getTags() != null
                && element.getDueDate() != null
                && element.getName() != null) {
                return true;
        }
        return false;
    }

    public List<ListElement> getAllElementsByUserID(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        List<ListElement> elements = this.listElementRepository.findByUserId(userID);
        return elements;
    }

    public List<ListElement> getAllElementsByListID(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        List<ListElement> elements = this.listElementRepository.findByListId(listID);
        return elements;
    }

    public List<ListElement> getAllElements() {
        return this.listElementRepository.findAll();
    }

    public ListElement getElement(final Integer ID) {
        ListElement element = this.listElementRepository.findById(ID)
                .orElseThrow(() -> new ListElementNotFoundException(ID));

        return element;
    }

    public ListElement createElement(final Integer userID, final Integer listID, final ListElement element) {
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

    @Transactional
    public ListElement updateElement(final ListElement newElement, final Integer ID) {
        return this.listElementRepository.findById(ID)
                .map(element -> {
                    element.setStatus(newElement.getStatus());
                    element.setPriority(newElement.getPriority());
                    element.setTags(newElement.getTags());
                    element.setDueDate(newElement.getDueDate());
                    element.setName(newElement.getName());
                    return this.listElementRepository.save(element);
                })
                .orElseGet(() -> {
                    return this.listElementRepository.save(newElement);
                });
    }

    @Transactional
    public void deleteElement(final Integer ID) {
        Optional<ListElement> optElement = this.listElementRepository.findById(ID);
        ListElement element = optElement.get();
        this.listElementRepository.delete(element);
    }

    @Transactional
    public void deleteAllByUser(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        this.listElementRepository.deleteByUserId(userID);
    }

    @Transactional
    public void deleteAllByList(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        this.listElementRepository.deleteByListId(listID);
    }
}
