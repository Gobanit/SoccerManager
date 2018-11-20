/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * Test for {@link UserServiceImpl} class. 
 * Tests various basic situations that could happen when used correctly.
 * @author Michal Randak
 *
 */

public class UserServiceBasicTest extends UserServiceAbstractTestBase {

	
	@BeforeMethod
	public void fabricate() {
		super.fabricateObjects();
	}
	
	@Test
	public void registerNewUser() {
		User user = new User();
		user.setUserName("NewUser");
		user.setAdmin(false);
		String rawPassword = "mojeHeslo";
		
		User createdUser = userService.registerNewUser(user, rawPassword);
		Mockito.verify(userDAO).save(user);
		Assert.assertNotNull(createdUser.getPasswordHash());
		Assert.assertNotEquals(rawPassword, createdUser.getPasswordHash());
	}
	
	@Test
	public void getAllUsers() {
		Mockito.when(userDAO.findAll()).thenReturn(fabricatedUsers);
		
		List<User> users = userService.getAllUsers();
		Assert.assertEquals(users, fabricatedUsers);
	}
	
	@Test
	public void getUserById() {
		User user = fabricatedUsers.get(3);

		mockFindForUser(user);
		
		User u = userService.getUserById(user.getId());
		Assert.assertEquals(u, user);
	}
	
	@Test
	public void getUserByUsername() {
		User user = fabricatedUsers.get(2);

		mockFindForUser(user);

		User u = userService.getUserByUsername(user.getUserName());
		Assert.assertEquals(u, user);
	}
	
	@Test
	public void authenticateUser() {
		User user = user(100l, "SomeUser", null, false, null);
		String rawPassword = "blablah";
		
		// need to register it before, so the encrypted password is generated
		user = userService.registerNewUser(user, rawPassword);
		
		mockFindForUser(user);

		Assert.assertTrue(userService.authenticateUser(user.getUserName(), rawPassword));
		Assert.assertFalse(userService.authenticateUser(user.getUserName(), "blablag"));
	}
	
	@Test
	public void deleteUser() {
		User user = fabricatedUsers.get(1);
		
		mockFindForUser(user);

		userService.deleteUser(user.getUserName());
		Mockito.verify(userDAO).delete(user);		
	}
	
	@Test
	public void pickTeamForUser() {
		User user = fabricatedUsers.get(2);
		if(user.getTeam() != null) throw new RuntimeException("Wrong test initialization");
		Team team = fabricatedTeam;
		

		mockFindForUser(user);
		mockFindForTeam(team);
		
		userService.pickTeamForUser(user.getUserName(), team.getId());
		Assert.assertEquals(user.getTeam(), team);
	}
	
	@Test
	public void isAdmin() {
		User user = fabricatedUsers.get(0);
		User admin = fabricatedUsers.get(1);
		
		mockFindForUser(user);
		mockFindForUser(admin);
		
		Assert.assertFalse(userService.isAdmin(user.getUserName()));
		Assert.assertTrue(userService.isAdmin(admin.getUserName()));
	}
	
	@Test
	public void changeAdministratorRights() {
		User user = fabricatedUsers.get(0);
		User admin = fabricatedUsers.get(1);
		
		mockFindForUser(user);
		mockFindForUser(admin);

		userService.changeAdministratorRights(user.getUserName());
		Assert.assertTrue(user.isAdmin());
		
		userService.changeAdministratorRights(admin.getUserName());
		Assert.assertFalse(admin.isAdmin());
	}
	

}
