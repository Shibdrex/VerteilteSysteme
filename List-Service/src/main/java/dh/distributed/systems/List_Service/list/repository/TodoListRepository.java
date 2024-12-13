package dh.distributed.systems.List_Service.list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dh.distributed.systems.List_Service.list.model.TodoList;
import jakarta.transaction.Transactional;

public interface TodoListRepository extends JpaRepository<TodoList, Integer> {
    List<TodoList> findByUserId(Integer userID);
    
    @Transactional
    void deleteByUserId(Integer userID);
}