/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.facade;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserSessionDTO;

/**
 * Interface for accessing users related use cases.
 * 
 * @author Michal Randak
 *
 */
public interface UserFacade {

	/**
	 * Finds all existing users in application
	 * 
	 * @return list of users
	 */
	public List<UserDTO> getAllUsers();

	/**
	 * Finds user by its id
	 * 
	 * @param userId
	 *            - user id
	 * @return found user or null if not existing
	 */
	public UserDTO getUserById(Long userId);

	/**
	 * Finds user by its username.
	 * 
	 * @param username
	 *            - username
	 * @return found user or null if not existing
	 */
	public UserDTO getUserByUsername(String username);

	/**
	 * Registers new user
	 * 
	 * @param user
	 *            - user to be created
	 * @return id of created user
	 */
	public Long registerNewUser(UserCreateDTO user);

	/**
	 * Authenticates user and returns the result.
	 * 
	 * @param userAuth
	 *            - authentication data
	 * @return UserSessionDTO representing user with token
	 */
	public UserSessionDTO authenticateUser(UserAuthenticateDTO userAuth);

	/**
	 * Removes user from application
	 * 
	 * @param name
	 *            - name of user to be deleted
	 */
	public void deleteUser(String name);

	/**
	 * Pick team for specified user
	 * 
	 * @param userName
	 *            - name of user
	 * @param teamId
	 *            - id of team
	 */
	public void pickTeamForUser(String userName, Long teamId);

	/**
	 * Sets admin rights based on specified value
	 * 
	 * @param userName
	 *            - name of user to be changed
	 * @param admin
	 *            - whether user should have admin rights
	 */
	public void changeAdminRights(String userName, boolean adminRights);

	TeamDTO getTeamOfUser(String userName);
}
