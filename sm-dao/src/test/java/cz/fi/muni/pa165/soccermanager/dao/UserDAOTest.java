package cz.fi.muni.pa165.soccermanager.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.dao.config.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * Test for UserDAO implementation class.
 * @author Dominik Pilar
 *
 */

@ContextConfiguration(classes = {DAOBeansConfig.class })
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDAOTest extends AbstractTestNGSpringContextTests {

	@PersistenceContext
	public EntityManager em;

	@Autowired
	public UserDAO userDAO;

	@Autowired
	public TeamDAO teamDAO;

	private User user1, user2;
	private Team team1, team2;

	@BeforeMethod
	public void createUsers() {
		team1 = new Team();
		team1.setCountry("Slovakia");
		team1.setClubName("Zilina");
		team1.setChampionshipName("Fortuna liga");
		team1.setBudget(BigDecimal.valueOf(500000));

		team2 = new Team();
		team2.setCountry("Slovakia");
		team2.setClubName("Trnava");
		team2.setChampionshipName("Fortuna liga");
		team2.setBudget(BigDecimal.valueOf(1000000));

		teamDAO.save(team1);
		teamDAO.save(team2);

		user1 = new User();
		user1.setAdmin(true);
		user1.setPasswordHash("hash");
		user1.setUserName("user1");
		user1.setTeam(team1);

		user2 = new User();
		user2.setAdmin(false);
		user2.setPasswordHash("hash");
		user2.setUserName("user2");

		userDAO.save(user1);
		userDAO.save(user2);
	}

	@Test
	public void nonExistentReturnsNull() {
		Assert.assertNull(userDAO.findById(321321l));
	}

	@Test
	public void find() {
		User found = userDAO.findById(user1.getId());

		Assert.assertEquals(found.getPasswordHash(), "hash");
		Assert.assertEquals(found.getUserName(), "user1");
		Assert.assertTrue(found.isAdmin());
	}

	@Test
	public void createWithoutIsAdminField() {
		User user3 = new User();
		user3.setUserName("user3");
		user3.setPasswordHash("hash3");
		userDAO.save(user3);

		Assert.assertNotNull(user3.getId());
		Assert.assertEquals(user3.isAdmin(), false);
	}

	@Test
	public void getNumberOfAdministrators() {
		Long n = userDAO.getNumberOfAdministrators();

		Assert.assertTrue(n.equals(1L));
	}


	@Test(expectedExceptions = PersistenceException.class)
	public void createUserWithoutPassword() {
		User u = new User();
		u.setUserName("user4");
		userDAO.save(u);
	}

	@Test(expectedExceptions = PersistenceException.class)
	public void createUserWithoutName() {
		User u = new User();
		u.setPasswordHash("hash");
		userDAO.save(u);
	}

	@Test
	public void findAll() {
		List<User> users = userDAO.findAll();

		Assert.assertEquals(users.size(), 2);
		Assert.assertTrue(users.contains(user1));
		Assert.assertTrue(users.contains(user2));
	}

	@Test
	public void findByName() {
		User found = userDAO.findByUserName("user1");
		User u = new User();
		u.setPasswordHash("hash");
		u.setUserName("user1");
		u.setAdmin(true);
		Assert.assertEquals(found, u);
	}

	@Test
	public void hasAlreadyTeamAssigned() {
		boolean has = userDAO.isTeamAlreadyAssignedToUser(team1.getId());
		Assert.assertEquals(true, has);
	}

	@Test
	public void hasAlreadyTeamAssignedNot() {
		boolean has = userDAO.isTeamAlreadyAssignedToUser(team2.getId());
		Assert.assertEquals(false, has);
	}

	@Test
	public void deleteUser() {
		Assert.assertNotNull(userDAO.findById(user1.getId()));
		userDAO.delete(user1);
		Assert.assertNull(userDAO.findById(user1.getId()));
	}

}
