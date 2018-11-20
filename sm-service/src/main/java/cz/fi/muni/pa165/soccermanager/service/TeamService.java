package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import java.util.List;

/**
 * Interface for service layer of Team 
 * 
 * @author Michal Mazourek
 */
public interface TeamService {
    
    /**
     * Creates an instance of a Team.
     *
     * @param team a team to be created
     */
    public void create(Team team);
    
    /**
     * Removes an instance of team.
     *
     * @param team a team to be removed
     */
    public void remove(Team team);
    
    /**
     * Update specific Team.
     *
     * @param team a team to be updated
     * @return updated team
     */
    public Team update(Team team);
    
    /**
     * Finds a team by its ID.
     *
     * @param id an ID of a team to be find
     * @return  an instance of a team;
     *          null, if not found
     */
    public Team findById(Long id);
    
    /**
     * Finds all teams.
     *
     * @return  a list of all teams
     */
    public List<Team> findAll();
    
    /**
     * Finds all Teams in specified Country.
     *
     * @param country a country where teams to be found.
     * @return  a list of all teams in specified country
     */
    public List<Team> FindByCountry(String country);
    
    /**
     * Finds all Players that are playing for the specified Team.
     *
     * @param team a team to be searched
     * @return  a list of all players playing for the team
     */
    public List<SoccerPlayer> findAllPlayersInTeam(Team team);
    
    /**
     * Counts an average team rating by arithmetic mean 
     * of all its players rating, rounded by Integer
     *
     * @param team a team for average rating evaluation
     * @return  an integer represents average team rating
     */
    public Integer AverageTeamRating(Team team);
}
