/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.facade;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;

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
	 * @return true if user was successfully authenticated or false otherwise
	 */
	public boolean authenticateUser(UserAuthenticateDTO userAuth);

	/**
	 * Removes user from application
	 * 
	 * @param id
	 *            - id of user to be deleted
	 */
	public void deleteUser(Long id);

	/**
	 * Pick team for specified user
	 * 
	 * @param userId
	 *            - id of user
	 * @param teamId
	 *            - id of team
	 */
	public void pickTeamForUser(Long userId, Long teamId);

}
