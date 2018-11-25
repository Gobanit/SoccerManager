/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.util.List;

import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.testng.Assert;
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
	
	@Test
	public void registerNewUser() {
		User user = new User();
		user.setUserName("NewUser");
		user.setAdmin(false);
		String rawPassword = "mojeHeslo";

		Mockito.when(userDAO.findByUserName(user.getUserName())).thenReturn(null);
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
		User user = user(100l, "SomeUser", BCrypt.hashpw("pass", BCrypt.gensalt()), false, null);
		String rawPassword = "pass";
		
		mockFindForUser(user);

		Assert.assertTrue(userService.authenticateUser(user.getUserName(), rawPassword));
		Assert.assertFalse(userService.authenticateUser(user.getUserName(), "passw"));
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
		
		mockFindForUser(user);
		Mockito.when(userDAO.getNumberOfAdministrators()).thenReturn(100l);

		Assert.assertFalse(user.isAdmin());
		userService.giveAdministratorRights(user.getUserName());
		Assert.assertTrue(user.isAdmin());
		userService.takeAdministratorRights(user.getUserName());
		Assert.assertFalse(user.isAdmin());
	}
	

}
