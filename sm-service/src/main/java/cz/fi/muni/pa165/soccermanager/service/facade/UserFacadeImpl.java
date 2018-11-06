/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service.facade;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;

/**
 * Implementation of {@link UserFacade}
 * 
 * @author Michal Randak
 *
 */
public class UserFacadeImpl implements UserFacade {

	@Override
	public List<UserDTO> getAllUsers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserDTO getUserById(Long userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long registerNewUser(UserCreateDTO user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean authenticateUser(UserAuthenticateDTO userAuth) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteUser(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pickTeamForUser(Long userId, Long teamId) {
		throw new UnsupportedOperationException();
	}

}
