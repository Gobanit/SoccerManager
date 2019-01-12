package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.api.exceptions.ErrorStatus;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.dao.UserDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import cz.fi.muni.pa165.soccermanager.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Implementation of service layer for team
 * 
 * @author Michal Mazourek
 */
@Service
public class TeamServiceImpl implements TeamService {
    static final int MAXIMUM_TEAM_SIZE = 30;
    private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamDAO teamDAO;
    private final UserDAO userDAO;
    private final MatchDAO matchDAO;


    @Inject
    public TeamServiceImpl(TeamDAO teamDAO, UserDAO userDAO, MatchDAO matchDAO) {
        this.teamDAO = teamDAO;
        this.userDAO = userDAO;
        this.matchDAO = matchDAO;
    }
    
    @Override
    public void create(Team team) {
        teamDAO.save(team);
    }

    @Override
    public void remove(Team team) {
        if (userDAO.isTeamAlreadyAssignedToUser(team.getId())) {
            throw new SoccerManagerServiceException("Cannot delete team. Team is assigned to user.", ErrorStatus.TEAM_CANNOT_REMOVE);
        }
        if (matchDAO.isTeamParticipatedInMatch(team.getId())) {
            throw new SoccerManagerServiceException("Cannot delete team. team is participated in the match.", ErrorStatus.TEAM_CANNOT_REMOVE);
        }
        teamDAO.delete(team);
    }

    @Override
    public Team findById(Long id) {
        return teamDAO.findById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamDAO.findAll();
    }
    
    @Override
    public List<Team> findByCountry(String country) {
        List<Team> teamsInCountry = new ArrayList<>();
        
        for (Team team : this.findAll()) {
            if (team.getCountry().equals(country)) {
                teamsInCountry.add(team);
            }
        }
        
        return teamsInCountry;
    }

    @Override
    public List<SoccerPlayer> findAllPlayersInTeam(Team team) {
        List<SoccerPlayer> teamPlayers = team.getPlayers();
        return teamPlayers;
    }

    @Override
    public Integer averageTeamRating(Team team) {
        Integer teamRating = 0;
        Integer numberOfPlayers = 0;
        List<SoccerPlayer> teamPlayers = this.findAllPlayersInTeam(team);
        
        for (SoccerPlayer player : teamPlayers) {
            teamRating += player.getRating();
            numberOfPlayers++;
        }
        
        if (numberOfPlayers == 0) {
            throw new SoccerManagerServiceException("Team can not be empty "
                    + "for evaluation of average team rating", ErrorStatus.NO_PLAYER_IN_TEAM);
        }
        
        return (teamRating / numberOfPlayers);
    }

    @Override
    public void addPlayerToTeam(Team team, SoccerPlayer player) {
        if (player.getTeam() != null) {
            throw new SoccerManagerServiceException("Can not add player that is already "
                    + "belonging to another team", ErrorStatus.PLAYER_IS_IN_TEAM);
        }
        
        if (team.getPlayers().size() > MAXIMUM_TEAM_SIZE) {
            throw new SoccerManagerServiceException("Can not add player to the "
                    + "full team.", ErrorStatus.TOO_MANY_PLAYERS_IN_TEAM);
        }
        team.addPlayer(player);
        teamDAO.update(team);
    }

    @Override
    public void removePlayerFromTeam(Team team, SoccerPlayer player) {
        logger.debug("Remove player from team SERVICE");
        logger.debug(team.toString());
        logger.debug(player.toString());
        team.removePlayer(player);
        teamDAO.update(team);
    }

    @Override
    public void changeBudgetBy(Team team, BigDecimal budgetChange) {
        team.setBudget(team.getBudget().add(budgetChange));
        teamDAO.update(team);
    }

    @Override
    public List<Team> findAllFree() {
        List<Team> freeTeams = new ArrayList<>();
        
        for (Team team : this.findAll()) {
            if (!userDAO.isTeamAlreadyAssignedToUser(team.getId())) {
                freeTeams.add(team);
            }
        }
        
        return freeTeams;
    }

    @Override
    public void updateTeam(Team t) {
        User u = userDAO.findByTeamId(t.getId());
        if(u == null) {
            if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new SoccerManagerServiceException("Cannot update team. User is not owner nor admin.", ErrorStatus.TEAM_CANNOT_UPDATE);

            }

        }
        Team teamToUpdate = teamDAO.findById(t.getId());
        teamToUpdate.setCountry(t.getCountry());
        if ((teamToUpdate.getBudget().compareTo(t.getBudget()) != 0) && !SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new SoccerManagerServiceException("User cannot update budget.", ErrorStatus.TEAM_CANNOT_UPDATE);
        }
        teamToUpdate.setBudget(t.getBudget());
        teamToUpdate.setChampionshipName(t.getChampionshipName());
        teamToUpdate.setClubName(t.getClubName());
    }

    @Override
    public boolean isTeamPicked(Long teamId) {
        return userDAO.isTeamAlreadyAssignedToUser(teamId);
    }
}
