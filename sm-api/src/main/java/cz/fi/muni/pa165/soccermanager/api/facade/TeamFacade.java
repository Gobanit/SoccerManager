package cz.fi.muni.pa165.soccermanager.api.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamChangeDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;

import java.util.List;

/**
 * Interface of a facade layer for Team entity.
 *
 * @author Lenka Horvathova
 */
public interface TeamFacade {

    /**
     * Creates a new team.
     *
     * @param teamCreateDTO a team to be created
     */
    void create(TeamCreateDTO teamCreateDTO);

    /**
     * Removes a specified team.
     *
     * @param id an ID of a team to be removed
     */
    void remove(Long id);

    /**
     * Finds all teams.
     *
     * @return a list of all teams
     */
    List<TeamDTO> findAll();
    
        /**
     * Finds all free teams.
     *
     * @return a list of all free teams
     */
    List<TeamDTO> findAllFree();

    /**
     * Finds a team by its ID.
     *
     * @param id    an ID to search by
     * @return      a found team
     */
    TeamDTO findById(Long id);

    /**
     * Finds all teams from a specified country.
     *
     * @param country   a country to search by
     * @return          a list of teams from such a country
     */
    List<TeamDTO> findByCountry(String country);

    /**
     * Gets all players in a specified team.
     *
     * @param id    an ID of a team to get its players from
     * @return      a list of all team's players
     */
    List<PlayerDTO> getAllPlayersInTeam(Long id);

    /**
     * Adds a player to a team.
     *
     * @param playerId an ID of a player to be added
     * @param teamId   an ID of a team to be added to
     */
    void addPlayerToTeam(Long playerId, Long teamId);

    /**
     * Removed a player from a team.
     *
     * @param playerId an ID of a  player to be removed
     * @param teamId   an ID of a team to be removed from
     */
    void removePlayerFromTeam(Long playerId, Long teamId);


    /**
     * Changes attributes of team.
     *
     * @param teamChangeDTO - team to update
     */
    void updateTeam(TeamChangeDTO teamChangeDTO);

    boolean isTeamPicked(Long teamId);

}
