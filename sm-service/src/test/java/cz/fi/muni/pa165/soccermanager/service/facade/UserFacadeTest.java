package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.BeanMappingImpl;
import cz.fi.muni.pa165.soccermanager.service.UserService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Michal Mazourek
 */
public class UserFacadeTest {
    
    private UserFacade userFacade;
    
    private BeanMapping beanMapping;
    
    @Mock
    private UserService userService;
    
    private User user1, user2, user3;
    private Team team1, team2;
    private UserCreateDTO createDTO;
    private UserAuthenticateDTO userAuth;
    
    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        beanMapping = new BeanMappingImpl(new ModelMapper());
        userFacade = new UserFacadeImpl(userService, beanMapping);
       
        team1 = team(10L, "Good Team", "Masters", "England");
        team2 = team(11L, "Average Team", "Local", "Croatia");
        
        user1 = user(1L, "Admin", "pass", true, null);
        user2 = user(2L, "User123", "123", false, null);
        user3 = user(3L, "User321", "321", false, team2);
        
        createDTO = new UserCreateDTO();
        createDTO.setUsername("Admin");
        createDTO.setRawPassword("pass");
        createDTO.setAdmin(true);
        
        userAuth = new UserAuthenticateDTO();
        userAuth.setUsername("Admin");
        userAuth.setRawPassword("pass");
    }
    
    private User user(Long id, String name, String pass, boolean admin, Team team) {
        User user = new User();
        user.setId(id);
        user.setUserName(name);
        user.setPasswordHash(BCrypt.hashpw(pass, BCrypt.gensalt(12)));
        user.setAdmin(admin);
        user.setTeam(team);
        return user;
    }
    
    private Team team(Long id, String name, String champ, String country) {
        Team team = new Team();
        team.setId(id);
        team.setClubName(name);
        team.setChampionshipName(champ);
        team.setCountry(country);
        team.setBudget(new BigDecimal(100));
        return team;
    }
    
    @Test
    public void registrateNewUserTest() {
        BeanMapping mockBM = Mockito.mock(BeanMapping.class);
        given(mockBM.mapTo(createDTO, User.class)).willReturn(user1);
        given(userService.registerNewUser(user1, "pass")).willReturn(user1);
        
        userFacade.registerNewUser(createDTO);
        then(userService).should().registerNewUser(user1, "pass");
    }
    
    @Test
    public void deleteUserTest() {
        userFacade.deleteUser(user1.getUserName());
        then(userService).should().deleteUser(user1.getUserName());
    }
    
    @Test
    public void getUserByIdTest() {
        given(userService.getUserById(user2.getId())).willReturn(user2);
        
        UserDTO user = userFacade.getUserById(user2.getId());
        
        Assert.assertEquals(user2.getId(), user.getId());
        Assert.assertEquals(user2.getUserName(), user.getUsername());
    }
    
    @Test
    public void getAllUsersTest() {
        given(userService.getAllUsers()).willReturn(Arrays.asList(user1, user2, user3));
        List<UserDTO> userListDTO = userFacade.getAllUsers();
        
        Assert.assertTrue(userListDTO.size() == 3);
        
        Assert.assertEquals(user1.getId(), userListDTO.get(0).getId());
        Assert.assertEquals(user1.getUserName(), userListDTO.get(0).getUsername());
        
        Assert.assertEquals(user2.getId(), userListDTO.get(1).getId());
        Assert.assertEquals(user2.getUserName(), userListDTO.get(1).getUsername());
        
        Assert.assertEquals(user3.getId(), userListDTO.get(2).getId());
        Assert.assertEquals(user3.getUserName(), userListDTO.get(2).getUsername());
    }
    
    @Test
    public void getUserByUsernameTest() {
        given(userService.getUserByUsername(user3.getUserName())).willReturn(user3);
        
        UserDTO user = userFacade.getUserByUsername(user3.getUserName());
        
        Assert.assertEquals(user3.getId(), user.getId());
        Assert.assertEquals(user3.getUserName(), user.getUsername());
    }
    
    @Test
    public void authenticateUserTest() {
        given(userService.authenticateUser(user1.getUserName(), 
                "pass")).willReturn(Boolean.TRUE);

        boolean auth = userFacade.authenticateUser(userAuth);
        Assert.assertTrue(auth);
    }
    
    @Test
    public void pickTeamForUserTest() {        
        userFacade.pickTeamForUser(user2.getUserName(), team1.getId());
        
        then(userService).should().pickTeamForUser(user2.getUserName(), team1.getId());
    }
    
    @Test
    public void changeAdminRightsTest() {        
        userFacade.changeAdminRights(user3.getUserName(), true);
        then(userService).should().giveAdministratorRights(user3.getUserName());
        
        userFacade.changeAdminRights(user1.getUserName(), false);
        then(userService).should().takeAdministratorRights(user1.getUserName());
    }
}
