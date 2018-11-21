package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.Team;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for operations with Match entity.
 *
 * @author Lenka Horvathova
 */
@Service
public interface MatchService {

    /**
     * Creates an instance of a match.
     *
     * @param match a match to be created
     */
    void create(Match match);

    /**
     * Removes an instance of match.
     *
     * @param match a match to be removed
     */
    void remove(Match match);

    /**
     * Finds a match by its ID.
     *
     * @param id    an ID of a match to be find
     * @return      an instance of a match;
     *              null, if not found
     */
    Match findById(Long id);

    /**
     * Finds all matches.
     *
     * @return  a list of all matches
     */
    List<Match> findAll();

    /**
     * Finds all matches that a specified team participated in.
     *
     * @param team  a team to filter all matches by
     * @return      a list of all matches played by such a team
     */
    List<Match> findByTeam(Team team);

    /**
     * Find all matches that have already taken place but haven't got set result.
     *
     * @return  a list of all matches awaiting for their results
     */
    List<Match> findAwaitingMatches();

    /**
     * Simulates a match by assigning a result of it.
     *
     * @param match a match to be simulated
     */
    void simulateMatch(Match match);

    /**
     * Gets a winner of a specified match.
     *
     * @param match a match to get a winner of
     * @return      a team that won;
     *              null, if it was a draw
     */
    Team getWinner(Match match);

    /**
     * Gets a number of team victories so far.
     *
     * @param team  a team to count victories of
     * @return      a number of team's victories
     */
    int getVictoriesOfTeam(Team team);
}
