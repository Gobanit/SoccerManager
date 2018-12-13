package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.PlayerFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
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
 * @author Lenka Horvathova, Dominik Pilar
 */
@RestController
@ExposesResourceFor(PlayerDTO.class)
@RequestMapping("/players")
public class PlayerRestController {

    private final static Logger logger = LoggerFactory.getLogger(PlayerRestController.class);

    private PlayerFacade playerFacade;
    private PlayerResourceAssembler playerResourceAssembler;

    @Autowired
    public PlayerRestController(PlayerFacade playerFacade, PlayerResourceAssembler playerResourceAssembler) {
        this.playerFacade = playerFacade;
        this.playerResourceAssembler = playerResourceAssembler;

    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Void> createTeam(@RequestBody PlayerCreateDTO playerCreateDTO) {
        logger.debug("rest createPlayer()");

        try {
            playerFacade.createPlayer(playerCreateDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.DELETE)
    public final HttpEntity<Void> deleteTeam(@PathVariable("playerId") long playerId) {
        logger.debug("rest deleteTeam()");

        try {
            playerFacade.removePlayer(playerId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw ExceptionSorter.throwException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<Resource<PlayerDTO>>> getAllPlayers() {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<PlayerDTO>> getPlayerById(@PathVariable("id") long id) {
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
