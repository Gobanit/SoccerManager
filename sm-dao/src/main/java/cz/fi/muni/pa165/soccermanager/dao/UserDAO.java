package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.User;

import java.util.List;

/**
 * DAO layer for User entity.
 *
 * @author Lenka Horvathova
 */
public interface UserDAO {

    /**
     * Saves a user to the database.
     *
     * @param user  a user to be saved
     */
    public void save(User user);

    /**
     * Deletes a user from the database.
     *
     * @param user  a user to be deleted
     */
    public void delete(User user);

    /**
     * Updates an existing user.
     *
     * @param user  a user to be updated
     */
    public void update(User user);

    /**
     * Finds a user by his/her id.
     *
     * @param id    a user's id to search by
     * @return      a user if found;
     *              otherwise null
     */
    public User findById(Long id);

    /**
     * Finds a user by his/her name.
     *
     * @param userName  a user's name to search by
     * @return          a user if found;
     *                  otherwise null
     */
    public User findByUserName(String userName);

    /**
     * Finds all users in the database.
     *
     * @return  a list of all users
     */
    public List<User> findAll();

    public boolean isTeamAlreadyAssignedToUser(Long teamId);
}
