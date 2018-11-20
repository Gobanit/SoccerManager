/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.dao.UserDAO;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * Base abstract class for {@link UserServiceImpl} testing. 
 * It contains fabricated objects and some useful 
 * methods used often in real test classes.
 * @author Michal Randak
 *
 */
public abstract class UserServiceAbstractTestBase {

	@Mock
	protected UserDAO userDAO;
	@Mock
	protected TeamDAO teamDAO;

	protected UserService userService;
	
	protected List<User> fabricatedUsers;
	protected Team fabricatedTeam;
	
	@BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDAO, teamDAO);
    }
	
	@BeforeMethod
	public void fabricateObjects() {
		// create new objects before every test method
		
		fabricatedUsers = new ArrayList<>();
		fabricatedUsers.add(user(3l, "User1", "Pass1", false, null));
		fabricatedUsers.add(user(5l, "User2", "Pass2", true, null));
		fabricatedUsers.add(user(7l, "User3", "Pass3", false, null));
		fabricatedUsers.add(user(150l, "User4", "Pass4", false, null));

		fabricatedTeam = team(1l, "SuperTeam", "Masters", "SK");
	}
	
	/**
	 * Creates fabricated Team entity based on parameters
	 */
	protected Team team(Long id, String name, String champ, String country) {
		Team t = new Team();
		t.setId(id);
		t.setClubName(name);
		t.setChampionshipName(champ);
		t.setCountry(country);
		t.setBudget(new BigDecimal(100));
		return t;
	}


	/**
	 * Creates fabricated User entity based on parameters 
	 */
	protected User user(Long id, String name, String pass, boolean admin, Team t) {
		User u = new User();
		u.setId(id);
		u.setUserName(name);
		u.setPasswordHash(pass);
		u.setAdmin(admin);
		u.setTeam(t);
		return u;
	}
	
	/**
	 * Mocks findById and findByUserName methods
	 */
	protected void mockFindForUser(User user) {
		Mockito.when(userDAO.findById(user.getId())).thenReturn(user);
		Mockito.when(userDAO.findByUserName(user.getUserName())).thenReturn(user);
	}
	
	/**
	 * Mocks findById method
	 */
	protected void mockFindForTeam(Team team) {
		Mockito.when(teamDAO.findById(team.getId())).thenReturn(team);
	}
}
