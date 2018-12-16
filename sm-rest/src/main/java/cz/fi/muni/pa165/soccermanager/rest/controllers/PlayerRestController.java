package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerChangeDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerFreeDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.PlayerFacade;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.PlayerFreeResourceAssembler;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.PlayerResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Rest Controller for Player entity.
 *
 * @author Lenka Horvathova
 */
@RestController
@ExposesResourceFor(PlayerDTO.class)
@RequestMapping("/players")
public class PlayerRestController {

    private final static Logger logger = LoggerFactory.getLogger(PlayerRestController.class);

    private PlayerFacade playerFacade;
    private TeamFacade teamFacade;
    private PlayerResourceAssembler playerResourceAssembler;
    private PlayerFreeResourceAssembler playerFreeResourceAssembler;

    @Autowired
    public PlayerRestController(PlayerFacade playerFacade, TeamFacade teamFacade, PlayerResourceAssembler playerResourceAssembler, PlayerFreeResourceAssembler playerFreeResourceAssembler) {
        this.playerFacade = playerFacade;
        this.teamFacade = teamFacade;
        this.playerResourceAssembler = playerResourceAssembler;
        this.playerFreeResourceAssembler = playerFreeResourceAssembler;

    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> createPlayer(@RequestBody PlayerCreateDTO playerCreateDTO) {
        logger.debug("rest createPlayer()");

        try {
            playerFacade.createPlayer(playerCreateDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> updatePlayer(@RequestBody PlayerChangeDTO playerChangeDTO) {
        logger.debug("rest createPlayer()");

        try {
            playerFacade.changePlayerAttributes(playerChangeDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.DELETE)
    public HttpEntity<Void> deletePlayer(@PathVariable("playerId") long playerId) {
        logger.debug("rest deletePlayer()");

        try {
            PlayerDTO playerDTO = playerFacade.findPlayerById(playerId);
            if (playerDTO.getTeam() != null) {
                teamFacade.removePlayerFromTeam(playerId, playerDTO.getTeam().getId());
            }
            playerFacade.removePlayer(playerId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<Resource<PlayerDTO>>> getAllPlayers() {
        logger.debug("rest getAllPlayers()");

        try {
            List<PlayerDTO> playerDTOs = playerFacade.findAllPlayers();
            List<Resource<PlayerDTO>> playerResourceList = new ArrayList<>();

            for (PlayerDTO playerDTO : playerDTOs) {
                playerResourceList.add(playerResourceAssembler.toResource(playerDTO));
            }

            Resources<Resource<PlayerDTO>> playersResources = new Resources<>(playerResourceList);
            playersResources.add(linkTo(TeamRestController.class).withSelfRel().withType("GET"));

            return new ResponseEntity<>(playersResources, HttpStatus.OK);

        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(value = "/free", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<Resource<PlayerFreeDTO>>> getAllFreePlayers() {
        logger.debug("rest getAllPlayers()");

        try {
            List<PlayerFreeDTO> playerDTOs = playerFacade.findFreePlayers();
            List<Resource<PlayerFreeDTO>> playerResourceList = new ArrayList<>();

            for (PlayerFreeDTO playerDTO : playerDTOs) {
                playerResourceList.add(playerFreeResourceAssembler.toResource(playerDTO));
            }

            Resources<Resource<PlayerFreeDTO>> playersResources = new Resources<>(playerResourceList);
            return new ResponseEntity<>(playersResources, HttpStatus.OK);

        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resource<PlayerDTO>> getPlayerById(@PathVariable("id") long id) {
        logger.debug("rest getPlayerById()");

        try {
            PlayerDTO playerDTO = playerFacade.findPlayerById(id);
            Resource<PlayerDTO> resource = playerResourceAssembler.toResource(playerDTO);

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }
}
