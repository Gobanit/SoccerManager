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
	 * Saves the team into database or updates it, if already exists.
	 * @param team - team to save
	 * @return saved team. Due to technical implementation, could be 
	 * other instance than the team instance passed to method as parameter.
	 * Especially when the updating already existing team.
	 */
	public Team saveOrUpdate(Team team);
	
	/**
	 * Removes the team from database
	 * @param team - team to delete
	 */
	public void delete(Team team);
}
