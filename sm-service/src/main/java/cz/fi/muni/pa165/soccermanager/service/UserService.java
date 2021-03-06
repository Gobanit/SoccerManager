package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.Team;
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
     * Check if user is authenticated and logs him in if so.
     *
     * @param userName name of the user to be authenticated
     * @return authenticated user
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found
     * @throws SoccerManagerServiceException with error status INCORRECT_PASSWORD - if user password does not match
     */
    User authenticateUser(String userName, String password);
    /**
     * Returns currently logged in user for this thread
     * @return current user
     */
    User getCurrentUser();
    
    /**
     * Clears security context, so current user will no longer 
     * be logged in.
     */
    void logoutCurrentUser();
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

    /**
     * Get team of the given user.
     *
     * @param userName name of the user to get his team
     * @throws SoccerManagerServiceException with error status RESOURCE_NOT_FOUND - if user with user name was not found or user has no team
     */
    Team getTeamOfUser(String userName);
}
