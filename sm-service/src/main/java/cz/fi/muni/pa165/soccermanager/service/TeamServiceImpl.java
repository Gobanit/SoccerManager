package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Implementation of service layer for team
 * 
 * @author Michal Mazourek
 */
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamDAO teamDAO;
    private final Integer maximalTeamSize = 30;

    @Inject
    public TeamServiceImpl(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }
    
    @Override
    public void create(Team team) {
        teamDAO.save(team);
    }

    @Override
    public void remove(Team team) {
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
            throw new SoccerManagerServiceException("Team can not be empty"
                    + "for evaluation of average team rating");
        }
        
        return (teamRating / numberOfPlayers);
    }

    @Override
    public void addPlayerToTeam(Team team, SoccerPlayer player) {
        if (player.getTeam() != null) {
            throw new SoccerManagerServiceException("Can not add player that is already "
                    + "belonging to another team");
        }
        
        if (team.getPlayers().size() > maximalTeamSize) {
            throw new SoccerManagerServiceException("Can not add player to the"
                    + "full team.");
        }
        team.addPlayer(player);
        teamDAO.update(team);
    }

    @Override
    public void removePlayerFromTeam(Team team, SoccerPlayer player) {
        team.removePlayer(player);
        teamDAO.update(team);
    }

    @Override
    public void changeBudgetBy(Team team, BigDecimal budgetChange) {
        team.setBudget(team.getBudget().add(budgetChange));
        teamDAO.update(team);
    }
}
