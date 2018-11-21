package cz.fi.muni.pa165.soccermanager.api.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchAwaitingDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;

import java.util.List;
/**
 * interface of facade layer for match
 * @author Dominik Pilar
 *
 */
public interface MatchFacade {

    /**
     * Creates an instance of a match.
     *
     * @param match a match to be created
     */
    void create(MatchCreateDTO match);

    /**
     * Removes an instance of match.
     *
     * @param matchId if of match to be removed
     */
    void remove(Long matchId);

    /**
     * Finds a match by its ID.
     *
     * @param id    an ID of a match to be find
     * @return      an instance of a match;
     *              null, if not found
     */
    MatchDTO findById(Long id);

    /**
     * Finds all matches.
     *
     * @return  a list of all matches
     */
    List<MatchDTO> findAll();

    /**
     * Finds all matches that a specified team participated in.
     *
     * @param teamId  id of team to filter all matches by
     * @return      a list of all matches played by such a team
     */
    List<MatchDTO> findByTeam(Long teamId);

    /**
     * Find all matches that have already taken place but haven't got set result.
     *
     * @return  a list of all matches awaiting for their results
     */
    List<MatchAwaitingDTO> findAwaitingMatches();

    /**
     * Simulates a match by assigning a result of it.
     *
     * @param matchId if of match to be simulated
     */
    void simulateMatch(Long matchId);

    /**
     * Gets a winner of a specified match.
     *
     * @param matchId id of match to get a winner of
     * @return      a team that won;
     *              null, if it was a draw
     */
    TeamDTO getWinner(Long matchId);

    /**
     * Gets a number of team victories so far.
     *
     * @param teamId  id of team to count victories of
     * @return      a number of team's victories
     */
    int getVictoriesOfTeam(Long teamId);
}
