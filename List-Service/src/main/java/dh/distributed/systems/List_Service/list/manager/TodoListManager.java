package dh.distributed.systems.List_Service.list.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.list.exception.TodoListNotFoundException;
import dh.distributed.systems.List_Service.list.model.TodoList;
import dh.distributed.systems.List_Service.list.repository.TodoListRepository;
import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;
import dh.distributed.systems.List_Service.listUser.repository.ListUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoListManager {

    private final TodoListRepository listRepository;

    private final ListUserRepository listUserRepository;

    public Boolean isValid(final TodoList list) {
        if (list != null
                && list.getTitle() != null
                && list.getFavorite() != null) {
            return true;
        }
        return false;
    }

    public List<TodoList> getAllListsByUserID(final Integer userID) {
        if (!this.listUserRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        List<TodoList> lists = this.listRepository.findByUserId(userID);
        return lists;
    }

    public List<TodoList> getAllLists() {
        return this.listRepository.findAll();
    }

    public TodoList getList(final Integer ID) {
        TodoList list = this.listRepository.findById(ID)
                .orElseThrow(() -> new TodoListNotFoundException(ID));

        return list;
    }

    public TodoList createList(final Integer userID, final TodoList list) {
        TodoList lst = this.listUserRepository.findById(userID).map(user -> {
            list.setUser(user);
            return this.listRepository.save(list);
        }).orElseThrow(() -> new ListUserNotFoundException(userID));

        return lst;
    }

    @Transactional
    public TodoList updateList(final TodoList newList, final Integer ID) {
        return this.listRepository.findById(ID)
            .map(list -> {
                list.setTitle(newList.getTitle());
                list.setFavorite(newList.getFavorite());
                return this.listRepository.save(list);
            })
            .orElseGet(() -> {
                return this.listRepository.save(newList);
            });
    }

    @Transactional
    public void deleteList(final Integer ID) {
        Optional<TodoList> optList = this.listRepository.findById(ID);
        TodoList list = optList.get();
        this.listRepository.delete(list);
    }

    @Transactional
    public void deleteAllByUser(final Integer userID) {
        if (!this.listRepository.existsById(userID)) {
            throw new ListUserNotFoundException(userID);
        }
        this.listRepository.deleteByUserId(userID);
    }
}
