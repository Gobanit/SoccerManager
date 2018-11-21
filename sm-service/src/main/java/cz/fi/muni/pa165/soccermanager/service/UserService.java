package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
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
     * @throws SoccerManagerServiceException with error status RESOURCE_ALREADY_EXISTS - if user already exists in system
     */
    User registerNewUser(User user, String unencryptedPassword);

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
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     */
    User getUserById(Long userId);
    /**
     * Get user with given user name.
     *
     * @param userName name of the user to be loaded
     * @return user with given user name
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     */
    User getUserByUsername(String userName);
    /**
     * Check if user is authenticated.
     *
     * @param userName name of the user to be authenticated
     * @return true if is authenticated, false otherwise
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
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
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user or tema is not found
     *                                                         TEAM_ALREADY_ASSIGNED - if team is assigned to other user
     */
    void pickTeamForUser(String userName, Long teamId);
    /**
     * Check if user is admin.
     *
     * @param userName name of the user to be checked if is admin
     * @return true user is admin, false otherwise
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     */
    boolean isAdmin(String userName);
    /**
     * Give administrator rights to user.
     *
     * @param userName name of the user to give administrator rights
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     */
    void giveAdministratorRights(String userName);
    /**
     * Take administrator rights from user.
     *
     * @param userName name of the user to take administrator rights from
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     *                                                         NO_MORE_ADMINISTRATORS - if there is no more administrators in system so this rights cannot be taken from user
     */
    void takeAdministratorRights(String userName);
}
