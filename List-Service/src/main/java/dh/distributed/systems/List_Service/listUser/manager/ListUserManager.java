package dh.distributed.systems.List_Service.listUser.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;
import dh.distributed.systems.List_Service.listUser.model.ListUser;
import dh.distributed.systems.List_Service.listUser.repository.ListUserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListUserManager {

    private final ListUserRepository repository;

    public Boolean isValid(final ListUser user) {
        return user != null
                && user.getFirstname() != null
                && user.getLastname() != null
                && user.getPassword() != null
                && user.getEmail() != null;
    }

    public List<ListUser> getAllListUsers() {
        return this.repository.findAll();
    }

    public List<ListUser> getAllListUsersWithFirstname(final String firstname) {
        List<ListUser> listUsers = new ArrayList<>();

        if (firstname == null)
            this.repository.findAll().forEach(listUsers::add);
        else
            this.repository.findByFirstnameContaining(firstname).forEach(listUsers::add);

        if (listUsers.isEmpty()) {
            return null;
        }
        return listUsers;
    }

    public List<ListUser> getAllListUsersWithLastname(final String lastname) {
        List<ListUser> listUsers = new ArrayList<>();

        if (lastname == null)
            this.repository.findAll().forEach(listUsers::add);
        else
            this.repository.findByLastnameContaining(lastname).forEach(listUsers::add);

        if (listUsers.isEmpty()) {
            return null;
        }
        return listUsers;
    }

    public List<ListUser> findByEmail(final String email) {
        List<ListUser> users = this.repository.findByEmail(email);

        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    public ListUser getListUser(final Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ListUserNotFoundException(id));
    }

    public ListUser createListUser(final ListUser user) {
        if (!isValid(user)) {
            throw new IllegalArgumentException("Invalid user data.");
        }
        return this.repository.save(user);
    }

    @Transactional
    public ListUser updateListUser(final ListUser newUser, final Integer id) {
        if (!isValid(newUser)) {
            throw new IllegalArgumentException("Invalid user data.");
        }
        return repository.findById(id)
                .map(user -> {
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setPassword(newUser.getPassword());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> this.repository.save(newUser));
    }

    @Transactional
    public void deleteListUser(final Integer id) {
        ListUser user = this.repository.findById(id)
                .orElseThrow(() -> new ListUserNotFoundException(id));
        this.repository.delete(user);
    }

    @Transactional
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
