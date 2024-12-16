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

/**
 * Handles database transactions of the elements table.
 */
@Service
@AllArgsConstructor
public class ListElementManager {

    private final ListElementRepository listElementRepository;

    private final ListUserRepository listUserRepository;

    private final TodoListRepository listRepository;

    /**
     * Method to check if certain fields are filled.
     * 
     * @param element {@link ListElement} object to check
     * @return true if and only if status, priority, tags, dueDate and name fields
     *         are not null, false otherwise
     */
    public Boolean isValid(final ListElement element) {
        return element != null
                && element.getStatus() != null
                && element.getPriority() != null
                && element.getTags() != null
                && element.getDueDate() != null
                && element.getName() != null;
    }

    /**
     * Method to retrieve all elements.
     * 
     * @return a list of all elements
     */
    public List<ListElement> getAllElements() {
        return this.listElementRepository.findAll();
    }

    /**
     * Method to search for all elements that are connected to the user with the
     * specified userID. If there is no user found with the userId an exception is
     * thrown.
     * 
     * @param userID of the user whose elements are to be searched for
     * @return a list of all elements that are connected to the user
     */
    public List<ListElement> getAllElementsByUserID(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        return this.listElementRepository.findByUserId(userID);
    }

    /**
     * Method to search for all elements that are connected to the list with the
     * specified listID. If there is no list found with the listID an exception is
     * thrown.
     * 
     * @param listID of the list whose elements are to be searched for
     * @return a list of all elements that are connected to the list
     */
    public List<ListElement> getAllElementsByListID(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        return this.listElementRepository.findByListId(listID);
    }

    /**
     * Method to retrieve a specific element with the given ID, if no list is found
     * with the ID an exception is thrown.
     * 
     * @param ID of the element to look for
     * @return the element with the ID
     */
    public ListElement getElement(final Integer ID) {
        return this.listElementRepository.findById(ID)
                .orElseThrow(() -> new ListElementNotFoundException(ID));
    }

    /**
     * Method to create a new element and add it to the database table. First checks
     * that data of given element is valid and not empty. Requires a userID and
     * listID to link the element to them. If the user or list with the ID is not
     * found throws an exception.
     * 
     * @param userID  of the user to link element to
     * @param listID  of the list to link element to
     * @param element object to create
     * @return the created element object
     */
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

    /**
     * Method to update a specific element in the database. First checks that data
     * of given element is valid and not empty. Updates the element with the given ID,
     * if no element with the ID exists a new element is created instead with the
     * given data.
     * 
     * @param newElement object containing data to update
     * @param ID         of the element to update
     * @return the updated or created element object
     */
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

    /**
     * Method to delete an element with the specified ID, if no element with the ID
     * is found throws an exception.
     * 
     * @param ID of the element to delete
     */
    public void deleteElement(final Integer ID) {
        ListElement element = this.listElementRepository.findById(ID)
                .orElseThrow(() -> new ListElementNotFoundException(ID));
        this.listElementRepository.delete(element);
    }

    /**
     * Method to delete all elements connected to the user with the specified
     * userID, if no user with the userID is found throws an exception.
     * 
     * @param userID of the user were all elements are deleted
     */
    public void deleteAllByUser(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        this.listElementRepository.deleteByUserId(userID);
    }

    /**
     * Method to delete all elements connected to the list with the specified
     * listID, if no list with the listID is found throws an exception.
     * 
     * @param listID of the list were all elements are deleted
     */
    public void deleteAllByList(final Integer listID) {
        if (!this.listRepository.existsById(listID)) {
            throw new TodoListNotFoundException(listID);
        }
        this.listElementRepository.deleteByListId(listID);
    }

    /**
     * Method to delete all elements of every user and every list, just deletes all
     * elements from the database.
     */
    public void deleteAll() {
        this.listElementRepository.deleteAll();
    }
}
