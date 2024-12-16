package dh.distributed.systems.User_Service.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dh.distributed.systems.User_Service.exception.ListUserNotFoundException;
import dh.distributed.systems.User_Service.model.ListUser;
import dh.distributed.systems.User_Service.repository.ListUserRepository;
import lombok.AllArgsConstructor;

/**
 * Handles database transactions of the users table.
 */
@AllArgsConstructor
@Service
public class ListUserManager {

    private final ListUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to check if certain fields are filled.
     * 
     * @param user {@link ListUser} object to check
     * @return true if and only if firstname, lastname, password and email fields
     *         are not null, false otherwise
     */
    public Boolean isValid(final ListUser user) {
        return user != null
                && user.getFirstname() != null
                && user.getLastname() != null
                && user.getPassword() != null
                && user.getEmail() != null;
    }

    /**
     * Method to retrieve all users.
     * 
     * @return a list of all users
     */
    public List<ListUser> getAllListUsers() {
        return this.repository.findAll();
    }

    /**
     * Method to search for all users that contain the given firstname, can be a
     * partial name. If no firstname is given it will retrieve all users. If no
     * users are found in both cases it will return null.
     * 
     * @param firstname to search for in the users table
     * @return a list of all users containing the firstname or all users if no
     *         firstname is provided or null
     */
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

    /**
     * Method to search for all users that contain the given lastname, can be a
     * partial name. If no lastname is given it will retrieve all users. If no users
     * are found in both cases it will return null.
     * 
     * @param lastname to search for in the users table
     * @return a list of all users containing the lastname or all users if no
     *         lastname is provided or null
     */
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

    /**
     * Method to search for users whose email partially or fully matches the given
     * email. If no users are found with the given email null is returned.
     * 
     * @param email to search for in the users table
     * @return a list of all users with the provided email
     */
    public List<ListUser> findByEmail(final String email) {
        List<ListUser> users = this.repository.findByEmail(email);

        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    /**
     * Method to retrieve a specific user with the given ID, if no user is found
     * with the ID an exception is thrown.
     * 
     * @param ID of the user to look for
     * @return the user with the ID
     */
    public ListUser getListUser(final Integer ID) {
        return this.repository.findById(ID)
                .orElseThrow(() -> new ListUserNotFoundException(ID));
    }

    /**
     * Method to create a new user and add it to the database table. First checks
     * that data of given user is valid and not empty.
     * 
     * @param user object to create
     * @return the created user object
     */
    public ListUser createListUser(final ListUser user) {
        if (!isValid(user)) {
            throw new IllegalArgumentException("Invalid user data.");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.repository.save(user);
    }

    /**
     * Method to update a specific user in the database. First checks that data of
     * given user is valid and not empty. If no user with the ID exists a new user
     * is created instead with the given data.
     * 
     * @param newUser object containing data to update
     * @param ID      of the user to update
     * @return the updated or created user object
     */
    @Transactional
    public ListUser updateListUser(final ListUser newUser, final Integer ID) {
        if (!isValid(newUser)) {
            throw new IllegalArgumentException("Invalid user data.");
        }
        return repository.findById(ID)
                .map(user -> {
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                        user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
                    } else {
                        user.setPassword(user.getPassword());
                    }
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
                    return this.repository.save(newUser);
                });
    }

    /**
     * Method to delete a user with the specified ID, if no user with the ID is
     * found throws an exception.
     * 
     * @param ID of the user to delete
     */
    @Transactional
    public void deleteListUser(final Integer ID) {
        ListUser user = this.repository.findById(ID)
                .orElseThrow(() -> new ListUserNotFoundException(ID));
        this.repository.delete(user);
    }

    /**
     * Method to delete all users just deletes everything.
     */
    @Transactional
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
