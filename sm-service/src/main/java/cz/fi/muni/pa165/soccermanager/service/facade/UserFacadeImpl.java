/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service.facade;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;
import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UserFacade}
 * 
 * @author Michal Randak
 *
 */

@Transactional
@Named
public class UserFacadeImpl implements UserFacade {

	private UserService userService;
	private BeanMapping beanMapping;
	private final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);
	
	/**
	 * @param userService
	 * @param beanMapping
	 */
	@Inject
	public UserFacadeImpl(UserService userService, BeanMapping beanMapping) {
		super();
		this.userService = userService;
		this.beanMapping = beanMapping;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userService.getAllUsers(); 
		return beanMapping.mapTo(users, UserDTO.class);
	}

	@Override
	public UserDTO getUserById(Long userId) {
		User user = userService.getUserById(userId);
		return beanMapping.mapTo(user, UserDTO.class);
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		User user = userService.getUserByUsername(username);
		return beanMapping.mapTo(user, UserDTO.class);
	}

	@Override
	public Long registerNewUser(UserCreateDTO userDTO) {
		User user = beanMapping.mapTo(userDTO, User.class);
		user = userService.registerNewUser(user, userDTO.getRawPassword());
		return user.getId();
	}

	@Override
	public boolean authenticateUser(UserAuthenticateDTO userAuth) {
		return userService.authenticateUser(userAuth.getUsername(), userAuth.getRawPassword());
	}

	@Override
	public void deleteUser(String userName) {
		userService.deleteUser(userName);
	}

	@Override
	public void pickTeamForUser(String userName, Long teamId) {
		userService.pickTeamForUser(userName, teamId);
	}

	@Override
	public void changeAdminRights(String userName, boolean adminRights) {
		if(adminRights) userService.giveAdministratorRights(userName);
		else userService.takeAdministratorRights(userName);
	}

	@Override
	public TeamDTO getTeamOfUser(String userName) {
		return  beanMapping.mapTo(userService.getTeamOfUser(userName), TeamDTO.class);
	}

}
