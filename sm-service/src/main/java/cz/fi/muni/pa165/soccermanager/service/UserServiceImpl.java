package cz.fi.muni.pa165.soccermanager.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.soccermanager.api.exceptions.ErrorStatus;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.dao.UserDAO;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;
/**
 * implementation of service layer for user
 * @author Dominik Pilar
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;
    private TeamDAO teamDAO;
    
    @Autowired
    public UserServiceImpl(UserDAO userDAO, TeamDAO teamDAO) {
        this.userDAO = userDAO;
        this.teamDAO = teamDAO;
    }


    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        User u = userDAO.findById(userId);
        if(u == null) {
            throw new SoccerManagerServiceException("User with id " + userId + " not found.", ErrorStatus.RESOURCE_NOT_FOUND);
        }
        return u;
    }

    @Override
    public User getUserByUsername(String userName) {
        User u = userDAO.findByUserName(userName);
        if(u == null) {
            throw new SoccerManagerServiceException("User with user name " + userName + " not found.", ErrorStatus.RESOURCE_NOT_FOUND);
        }
        return u;

    }

    @Override
    public User registerNewUser(User user, String unencryptedPassword) {
        if(userDAO.findByUserName(user.getUserName()) != null) {
            throw new SoccerManagerServiceException("User with user name " + user.getUserName() + " already exists.", ErrorStatus.RESOURCE_ALREADY_EXISTS);
        }
        String hashedPassword = BCrypt.hashpw(unencryptedPassword, BCrypt.gensalt(12));
        user.setPasswordHash(hashedPassword);
        userDAO.save(user);
        return user;
    }

    @Override
    public User authenticateUser(String userName, String password) {
    	User user = getUserByUsername(userName);
    	   	
    	boolean success =  BCrypt.checkpw(password, user.getPasswordHash());
    	if(!success) throw new SoccerManagerServiceException("Incorrect password!",
				ErrorStatus.INCORRECT_PASSWORD);
    	
    	List<String> roles = new ArrayList<>();
    	roles.add("USER");
    	if(user.isAdmin()) roles.add("ADMIN");   	
    	
    	setSpringSecurityUser(user.getUserName(), user.getPasswordHash(), roles);
    	
    	return user;
    }

    @Override
    public void deleteUser(String userName) {
        User u = getUserByUsername(userName);
        userDAO.delete(u);
    }

    @Override
    public void pickTeamForUser(String userName, Long teamId) {
        User u = getUserByUsername(userName);
        Team t = teamDAO.findById(teamId);
        if(t == null) {
            throw new SoccerManagerServiceException("Team with id " + teamId + " not found.", ErrorStatus.RESOURCE_NOT_FOUND);

        }
        if (userDAO.isTeamAlreadyAssignedToUser(teamId)) {
            throw new SoccerManagerServiceException("Team with id " + teamId + " is already assigned to other user.", ErrorStatus.TEAM_ALREADY_ASSIGNED);
        }
        u.setTeam(t);
    }

    @Override
    public boolean isAdmin(String userName) {
        return getUserByUsername(userName).isAdmin();
    }

    @Override
    public void giveAdministratorRights(String userName) {
        User u = getUserByUsername(userName);
        u.setAdmin(true);
    }

    @Override
    public void takeAdministratorRights(String userName) {
        User u = getUserByUsername(userName);
        if(u.isAdmin()) {
          if(userDAO.getNumberOfAdministrators() > 1) {
              u.setAdmin(false);
          }  else {
              throw new SoccerManagerServiceException("User with user name " + userName + " is last admin and rights cannot be taken from him.", ErrorStatus.NO_MORE_ADMINISTRATOR);
          }
        }
    }

    @Override
    public Team getTeamOfUser(String userName) {
        User u = getUserByUsername(userName);
        if(u.getTeam() == null) {
            throw new SoccerManagerServiceException("User has no team.", ErrorStatus.RESOURCE_NOT_FOUND);
        }
        return u.getTeam();
    }

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return null;
		return getUserByUsername(auth.getName());
	}

	@Override
	public void logoutCurrentUser() {
		SecurityContextHolder.clearContext();		
	}
    
	private void setSpringSecurityUser(String username, String passwordHash, List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String role : roles) authorities.add(new SimpleGrantedAuthority("ROLE_"+role));     

		org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(username, passwordHash, authorities);
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));

		LOG.info("User set to security context");
	}
}
