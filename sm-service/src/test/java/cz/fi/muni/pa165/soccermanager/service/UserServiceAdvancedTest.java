/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * Test for {@link UserServiceImpl} class. 
 * Tests various advanced situations, mostly when 
 * unexpected or invalid input is given.
 * @author Michal Randak
 *
 */
public class UserServiceAdvancedTest extends UserServiceAbstractTestBase {
	
	@Test(expectedExceptions = EntityExistsException.class)
	public void registerExistingUser() {
		User user = fabricatedUsers.get(0);

		mockFindForUser(user);
		Mockito.doThrow(EntityExistsException.class).when(userDAO).save(user);

		userService.registerNewUser(user, "abcd");
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void registerNonUniqueUser() {
		User existing = fabricatedUsers.get(2);
		User user = user(null, existing.getUserName(), "newPass", false, null);
				
		mockFindForUser(existing);
		Mockito.doThrow(PersistenceException.class).when(userDAO).save(user);

		userService.registerNewUser(user, "abcd");
	}

	@Test(expectedExceptions = SoccerManagerServiceException.class)
	public void authenticateNonExistingUser() {
		User user = user(null, "abcdef", "aaa", false, null);

		userService.authenticateUser(user.getUserName(), "pass");
	}

	@Test(expectedExceptions = SoccerManagerServiceException.class)
	public void pickNonExistingTeam() {
		User user = fabricatedUsers.get(0);
		Team existingTeam = fabricatedTeam;
		Team team = team(null, "nonexisting", "some champ", "whatever");

		mockFindForUser(user);
		mockFindForTeam(existingTeam);

		userService.pickTeamForUser(user.getUserName(), team.getId());
	}
	
	@Test(expectedExceptions = SoccerManagerServiceException.class)
	public void pickAlreadyPickedTeam() {
		User user = fabricatedUsers.get(0);
		Team team = fabricatedTeam;
		
		mockFindForUser(user);
		mockFindForTeam(team);
		Mockito.when(userDAO.isTeamAlreadyAssignedToUser(team.getId())).thenReturn(true);

		userService.pickTeamForUser(user.getUserName(), team.getId());
	}

}
