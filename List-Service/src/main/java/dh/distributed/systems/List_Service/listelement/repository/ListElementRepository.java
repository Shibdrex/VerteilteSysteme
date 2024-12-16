package dh.distributed.systems.List_Service.listelement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dh.distributed.systems.List_Service.listelement.model.ListElement;
import jakarta.transaction.Transactional;

public interface ListElementRepository extends JpaRepository<ListElement, Integer> {
    List<ListElement> findByUserId(Integer userID);

    List<ListElement> findByListId(Integer listID);

    @Transactional
    void deleteByUserId(Integer userID);
    
    @Transactional
    void deleteByListId(Integer listID);
}
