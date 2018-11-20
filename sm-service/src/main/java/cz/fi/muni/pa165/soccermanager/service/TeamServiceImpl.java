package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
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
    public Team update(Team team) {
        teamDAO.update(team);
        return team;
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
    public List<Team> FindByCountry(String country) {
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
    public Integer AverageTeamRating(Team team) {
        Integer teamRating = 0;
        Integer numberOfPlayers = 0;
        List<SoccerPlayer> teamPlayers = this.findAllPlayersInTeam(team);
        
        for (SoccerPlayer player : teamPlayers) {
            teamRating += player.getRating();
            numberOfPlayers++;
        }
        
        return (teamRating / numberOfPlayers);
    }
}
