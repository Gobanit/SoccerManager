package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.main.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.main.PersistenceBeansConfig;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

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

	private User user1, user2;

	@BeforeMethod
	public void createUsers() {
		user1 = new User();
		user1.setAdmin(true);
		user1.setPasswordHash("hash");
		user1.setUserName("user1");

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
	public void deleteUser() {
		Assert.assertNotNull(userDAO.findById(user1.getId()));
		userDAO.delete(user1);
		Assert.assertNull(userDAO.findById(user1.getId()));
	}

}
