package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.service.exceptions.ServiceLayerException;
import java.util.List;

/**
 * interface for service layer of user
 * @author Dominik Pilar
 *
 */
public interface UserService {
    /**
     * Attempt to register new user in system.
     *
     * @param user to be registered in system
     * @param unencryptedPassword new unencrypted password for user
     * @return new registered user
     * @throws ServiceLayerException if user already exists in system.
     */
    User registerNewUser(User user, String unencryptedPassword);
    /**
     * Update user in system.
     *
     * @param user to be updated in system
     * @return updated user
     * @throws ServiceLayerException if user is not found in system.
     */
    User updateUser(User user);
    /**
     * Find all users.
     *
     * @return all users from system
     */
    List<User> getAllUsers();

    /**
     * Get user with given user id.
     *
     * @param userId id of the user to be loaded
     * @return user with given user id
     * @throws ServiceLayerException if user is not found in system.
     */
    User getUserById(Long userId);
    /**
     * Get user with given user name.
     *
     * @param userName name of the user to be loaded
     * @return user with given user name
     * @throws ServiceLayerException if user is not found in system.
     */
    User getUserByUsername(String userName);
    /**
     * Check if user is authenticated.
     *
     * @param userName name of the user to be authenticated
     * @return true if is authenticated, false otherwise
     * @throws ServiceLayerException if user is not found in system.
     */
    boolean authenticateUser(String userName, String password);
    /**
     * Delete user from system.
     *
     * @param userName name of the user to be deleted
     */
    void deleteUser(String userName);
    /**
     * Assign team to the user.
     *
     * @param userName name of the user
     * @param teamId id of team to be assigned to the user
     * @throws ServiceLayerException if user is not found in system, team is not found or team is already picked by someone else.
     */
    void pickTeamForUser(String userName, Long teamId);
    /**
     * Check if user is admin.
     *
     * @param userName name of the user to be checked if is admin
     * @return true user is admin, false otherwise
     * @throws ServiceLayerException if user is not found in system.
     */
    boolean isAdmin(String userName);
    /**
     * Change administrator rights of user.
     *
     * @param userName name of the user to change administrator rights
     * @throws ServiceLayerException if user is not found in system.
     */
    void changeAdministratorRights(String userName);
}
