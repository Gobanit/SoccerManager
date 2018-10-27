package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;

import java.util.List;

/**
 * DAO interface for soccer player
 * @author Dominik Pilar
 *
 */
public interface SoccerPlayerDAO {
	/**
	 * Finds all soccer players in database
	 * @return list of all players
	 */
	List<SoccerPlayer> findAll();

	/**
	 * Find one exact soccer player in database, based on its id
	 * @param id - id of player to be find
	 * @return team instance
	 */
	SoccerPlayer findById(Long id);

	/**
	 * Create the soccer player in database.
	 * @param player - soccer player to create
	 * @return created player.
	 */
	void create(SoccerPlayer player);

	/**
	 * Update the soccer player in database.
	 * @param player - soccer player to update
	 * @return updated player.
	 */
	void update(SoccerPlayer player);

	/**
	 * Removes the soccer player from database
	 * @param player - player to delete
	 */
	void delete(SoccerPlayer player);
	/**
	 * Finds all free soccer players without team
	 * @return list of all free players
	 */
	List<SoccerPlayer> findAllFreePlayers();
}
