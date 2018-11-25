/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.soccermanager.api.facade;

import java.util.List;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerChangeDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerFreeDTO;

/**
 *
 * @author Michal Mazourek
 */
public interface PlayerFacade {
    
    	/**
	 * Creates new SoccerPlayer
	 * 
	 * @param player a player to be created
	 */
	public void createPlayer(PlayerCreateDTO player);

	/**
	 * Removes existing player from application
	 * 
	 * @param playerId an ID of player to be removed
	 */
	public void removePlayer(Long playerId);

	/**
	 * Finds player by its id
	 * 
	 * @param id player id
	 * @return found player 
	 */
	public PlayerDTO findPlayerById(Long id);

	/**
	 * Finds all players in application
	 * 
	 * @return list of existing players
	 */
	public List<PlayerDTO> findAllPlayers();

	/**
	 * Finds all players not belonging to any team
	 * 
	 * @return list of players without team
	 */
	public List<PlayerFreeDTO> findFreePlayers();
	
	/**
	 * Changes attributes of player. 
	 * 
	 * @param player - player to update
	 */
	public void changePlayerAttributes(PlayerChangeDTO player);

}
