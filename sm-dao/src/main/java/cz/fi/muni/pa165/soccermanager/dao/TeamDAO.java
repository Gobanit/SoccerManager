/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * 
 * Interface for TeamDAO (Team DataAccessObject), used for accessing database
 * @author Michal Randak
 *
 */
public interface TeamDAO {
	/**
	 * Finds all teams in database
	 * @return list of all teams
	 */
	public List<Team> findAll();
	
	/**
	 * Find one exact team in database, based on its id
	 * @param id - team unique identification
	 * @return team instance
	 */
	public Team findById(Long id);
	
	/**
	 * Creates new team in database.
	 * @param team - team to save
	 */
	public void create(Team team);
	
	/**
	 * Updates team ind database and returns the updated instance
	 * @param team - team with new values
	 * @return saved team. Due to technical implementation, it is
	 * the other instance than the team instance passed to method 
	 * as parameter.
	 */
	public Team update(Team team);
	
	/**
	 * Removes the team from database
	 * @param team - team to delete
	 */
	public void delete(Team team);
}
