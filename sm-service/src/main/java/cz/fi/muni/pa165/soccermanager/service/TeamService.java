package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import java.math.BigDecimal;
import java.util.List;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;

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
     * Finds all free teams.
     *
     * @return  a list of all free teams
     */
    public List<Team> findAllFree();
    
    /**
     * Finds all Teams in specified Country.
     *
     * @param country a country where teams to be found.
     * @return  a list of all teams in specified country
     */
    public List<Team> findByCountry(String country);
    
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
     * @throws SoccerManagerServiceException if number of players is 0
     */
    public Integer averageTeamRating(Team team);
    
    /**
     * Adds player to team
     *
     * @param team a team for a new player
     * @param player a new player to the team
     * @throws SoccerManagerServiceException if player already exists in another team 
     * or the team is already have enough players (30)
     */
    public void addPlayerToTeam(Team team, SoccerPlayer player);
    
    /**
     * Remove player from a team
     *
     * @param team a team from which a specified player is going to be removed
     * @param player a player to remove
     */
    public void removePlayerFromTeam(Team team, SoccerPlayer player);
    
    /**
     * Change budget of the team by specified number
     *
     * @param team a team which budget is going to change
     * @param budgetChange a number that will change the budget (negative number
     * for lowering, positive for adjusting)
     */
    public void changeBudgetBy(Team team, BigDecimal budgetChange);

    void updateTeam(Team t);

    boolean isTeamPicked(Long teamId);
}
