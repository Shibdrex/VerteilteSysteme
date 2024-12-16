package dh.distributed.systems.List_Service.list.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.list.exception.TodoListNotFoundException;
import dh.distributed.systems.List_Service.list.model.TodoList;
import dh.distributed.systems.List_Service.list.repository.TodoListRepository;
import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;
import dh.distributed.systems.List_Service.listUser.repository.ListUserRepository;
import lombok.AllArgsConstructor;

/**
 * Handles database transactions of the todo lists table.
 */
@Service
@AllArgsConstructor
public class TodoListManager {

    private final TodoListRepository listRepository;

    private final ListUserRepository listUserRepository;

    /**
     * Method to check if certain fields are filled.
     * 
     * @param list {@link TodoList} object to check
     * @return true if and only if title and favorite fields are not null, false
     *         otherwise
     */
    public Boolean isValid(final TodoList list) {
        return list != null
                && list.getTitle() != null
                && list.getFavorite() != null;
    }

    /**
     * Method to retrieve all lists.
     * 
     * @return a list of all lists
     */
    public List<TodoList> getAllLists() {
        return this.listRepository.findAll();
    }

    /**
     * Method to search for all lists that are connected to the user with the
     * specified userID. If there is no user found with the userID an exception is
     * thrown.
     * 
     * @param userID of the user whose lists are to be searched for
     * @return a list of all list that are connected to the user
     */
    public List<TodoList> getAllListsByUserID(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        return this.listRepository.findByUserId(userID);
    }

    /**
     * Method to retrieve a specific list with the given ID. If no list is found
     * with the ID an exception is thrown.
     * 
     * @param ID of the list to look for
     * @return the list with the ID
     */
    public TodoList getList(final Integer ID) {
        return this.listRepository.findById(ID)
                .orElseThrow(() -> new TodoListNotFoundException(ID));
    }

    /**
     * Method to create a new list and add it to the database table. First checks
     * that data of given list is valid and not empty. Requires a userID to link
     * list to. If the user with the userID is not found throws an exception.
     * 
     * @param userID of the user to link list to
     * @param list   object to create
     * @return the created list object
     */
    public TodoList createList(final Integer userID, final TodoList list) {
        if (!isValid(list)) {
            throw new IllegalArgumentException("Invalid list data.");
        }
        return this.listUserRepository.findById(userID).map(user -> {
            list.setUser(user);
            return this.listRepository.save(list);
        }).orElseThrow(() -> new ListUserNotFoundException(userID));
    }

    /**
     * Method to update a specific list in the database. First checks that data of
     * given list is valid and not empty. Updates the list with the given ID, if no
     * list with the ID exists a new list is created instead with the given data.
     * 
     * @param newList object containing data to update
     * @param ID      of the list to update
     * @return the updated or created list object
     */
    public TodoList updateList(final TodoList newList, final Integer ID) {
        if (!isValid(newList)) {
            throw new IllegalArgumentException("Invalid list data.");
        }
        return this.listRepository.findById(ID)
                .map(list -> {
                    list.setTitle(newList.getTitle());
                    list.setFavorite(newList.getFavorite());
                    return this.listRepository.save(list);
                })
                .orElseGet(() -> this.listRepository.save(newList));
    }

    /**
     * Method to delete a list with the specified ID, if no list with the ID is
     * found throws an exception.
     * 
     * @param ID of the list to delete
     */
    public void deleteList(final Integer ID) {
        TodoList list = this.listRepository.findById(ID)
                .orElseThrow(() -> new TodoListNotFoundException(ID));
        this.listRepository.delete(list);
    }

    /**
     * Method to delete all lists connected to the user with the specified userID,
     * if no user with the userID is found throws an exception.
     * 
     * @param userID of the user were all lists are deleted
     */
    public void deleteAllByUser(final Integer userID) {
        if (!this.listRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        this.listRepository.deleteByUserId(userID);
    }

    /**
     * Method to delete all lists of every user, just deletes all lists from the
     * database.
     */
    public void deleteAll() {
        this.listRepository.deleteAll();
    }
}
