/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.SoccerPlayerDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.enums.Footed;
import cz.fi.muni.pa165.soccermanager.data.enums.Position;

/**
 * Implementation of {@link PlayerService}
 * 
 * @author Michal Randak
 *
 */
@Named
public class PlayerServiceImpl implements PlayerService {

	private final SoccerPlayerDAO playerDAO;

	/**
	 * @param playerDAO
	 */
	@Inject
	public PlayerServiceImpl(SoccerPlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

	@Override
	public void createPlayer(SoccerPlayer player) {
		playerDAO.save(player);
	}

	@Override
	public void removePlayer(SoccerPlayer player) {
		if (player.getTeam() != null) {
			// TODO consider remove from team here instead of exception throw
			throw new SoccerManagerServiceException("Can not delete player belonging to existing team!");
		}

		playerDAO.delete(player);
	}

	@Override
	public SoccerPlayer findPlayerById(Long id) {
		return playerDAO.findById(id);
	}

	@Override
	public List<SoccerPlayer> findAllPlayers() {
		return playerDAO.findAll();
	}

	@Override
	public List<SoccerPlayer> findFreePlayers() {
		return playerDAO.findAllFreePlayers();
	}

	@Override
	public void changePlayerAttributes(SoccerPlayer player, Position pos, Footed foot, Integer rating) {
		if(player.getTeam() != null) 
			throw new SoccerManagerServiceException("Cannot change attributes of player already in team!");
		
		player.setPosition(pos);
		player.setFooted(foot);
		player.setRating(rating);
	}

}