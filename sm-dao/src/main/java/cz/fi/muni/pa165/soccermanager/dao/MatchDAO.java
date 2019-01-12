package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.data.Match;

/**
 * DAO layer for Match entity.
 * 
 * @author Michal Mazourek
 *
 */

public interface MatchDAO {
    /**
     * Saves a match to the database.
     *
     * @param match  a match to be saved
     */
    public void save(Match match);
    
    /**
     * Deletes a match from the database.
     *
     * @param match  a match to be deleted
     */
    public void delete(Match match);
    
    /**
     * Update the match in database.
     * @param match - match to update
     * @return updated match.
     */
    public Match update(Match match);
    
    /**
     * Finds a match by its id.
     *
     * @param id    a match id to search by
     * @return      a match if found;
     *              otherwise null
     */
    public Match findById(Long id);
    
    /**
     * Finds all matches in the database.
     *
     * @return  a list of all matches
     */
    public List<Match> findAll();

    boolean isTeamParticipatedInMatch(Long teamId);
    
}
