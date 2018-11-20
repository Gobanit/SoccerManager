package cz.fi.muni.pa165.soccermanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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

    private UserDAO userDAO;
    private TeamDAO teamDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, TeamDAO teamDAO) {
        this.userDAO = userDAO;
        this.teamDAO = teamDAO;
    }

    @Override
    public User updateUser(User u) {
        userDAO.update(u);
        return u;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        User u = userDAO.findById(userId);
        if(u == null) {
            throw new SoccerManagerServiceException("User with id " + userId + " not found.");
        }
        return u;
    }

    @Override
    public User getUserByUsername(String userName) {
        User u = userDAO.findByUserName(userName);
        if(u == null) {
            throw new SoccerManagerServiceException("User with user name " + userName + " not found.");
        }
        return u;
    }

    @Override
    public User registerNewUser(User user, String unencryptedPassword) {
        String hashedPassword = BCrypt.hashpw(unencryptedPassword, BCrypt.gensalt(12));
        user.setPasswordHash(hashedPassword);
        userDAO.save(user);
        return user;
    }

    @Override
    public boolean authenticateUser(String userName, String password) {
        return BCrypt.checkpw(password,  getUserByUsername(userName).getPasswordHash());
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
            throw new SoccerManagerServiceException("Team with id " + teamId + " not found.");

        }
        if (userDAO.isTeamAlreadyAssignedToUser(teamId)) {
            throw new SoccerManagerServiceException("Team with id " + teamId + " is already assigned to other user.");
        }
        u.setTeam(t);
        userDAO.update(u);
    }

    @Override
    public boolean isAdmin(String userName) {
        return getUserByUsername(userName).isAdmin();
    }

    @Override
    public void changeAdministratorRights(String userName) {
        User u = getUserByUsername(userName);
        u.setAdmin(!u.isAdmin());
        userDAO.update(u);
    }
}
