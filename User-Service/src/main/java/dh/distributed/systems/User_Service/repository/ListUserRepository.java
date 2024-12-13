package dh.distributed.systems.User_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dh.distributed.systems.User_Service.model.ListUser;

public interface ListUserRepository extends JpaRepository<ListUser, Integer> {
    List<ListUser> findByEmail(String email);

    List<ListUser> findByFirstnameContaining(String firstname);

    List<ListUser> findByLastnameContaining(String lastname);
}
