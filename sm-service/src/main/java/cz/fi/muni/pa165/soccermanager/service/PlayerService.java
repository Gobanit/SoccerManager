/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;

/**
 * Interface defining methods related to Player entity at service layer.
 * 
 * @author Michal Randak
 *
 */
public interface PlayerService {

	/**
	 * Creates new SoccerPlayer
	 * 
	 * @param player
	 *            - player to be created
	 */
	public void createPlayer(SoccerPlayer player);

	/**
	 * Removes existing player from application
	 * 
	 * @param player
	 *            - player to be removed
	 */
	public void removePlayer(SoccerPlayer player);

	/**
	 * Finds player by its id
	 * 
	 * @param id
	 *            - player id
	 * @return found player or null if not existing
	 */
	public SoccerPlayer findPlayerById(Long id);

	/**
	 * Finds all players in application
	 * 
	 * @return list of existing players
	 */
	public List<SoccerPlayer> findAllPlayers();

	/**
	 * Finds all players not belonging to any team
	 * 
	 * @return list of players without team
	 */
	public List<SoccerPlayer> findFreePlayers();

}
