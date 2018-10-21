package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;

import java.util.List;

public interface SoccerPlayerDAO {
	/**
	 * Finds all soccer players in database
	 * @return list of all players
	 */
	public List<SoccerPlayer> findAll();

	/**
	 * Find one exact soccer player in database, based on its id
	 * @param id - id of player to be find
	 * @return team instance
	 */
	public SoccerPlayer findById(Long id);

	/**
	 * Create the soccer player in database.
	 * @param player - soccer player to create
	 * @return created player.
	 */
	public SoccerPlayer create(SoccerPlayer player);

	/**
	 * Update the soccer player in database.
	 * @param player - soccer player to update
	 * @return updated player.
	 */
	public SoccerPlayer update(SoccerPlayer player);

	/**
	 * Removes the soccer player from database
	 * @param player - player to delete
	 */
	public void delete(SoccerPlayer player);
}
