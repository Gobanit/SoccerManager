package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.InvalidRequestException;
import cz.fi.muni.pa165.soccermanager.rest.hateoas.TeamResource;
import cz.fi.muni.pa165.soccermanager.rest.hateoas.TeamResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Dominik Pilar
 *
 */

@RestController
@ExposesResourceFor(TeamDTO.class)
@RequestMapping("/teams")
public class TeamRestController {

    final static Logger logger = LoggerFactory.getLogger(TeamRestController.class);

    private TeamFacade teamFacade;
    private TeamResourceAssembler teamResourceAssembler;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private EntityLinks entityLinks;

    @Autowired
    public TeamRestController(TeamFacade teamFacade, TeamResourceAssembler teamResourceAssembler, EntityLinks entityLinks) {
        this.teamFacade = teamFacade;
        this.teamResourceAssembler = teamResourceAssembler;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<TeamResource>> teams() {

        logger.debug("rest getTeams()");
        List<TeamDTO> allTeams = teamFacade.findAll();
        Resources<TeamResource> teamsResources = new Resources<>(
                teamResourceAssembler.toResources(allTeams),
                linkTo(TeamRestController.class).withSelfRel(),
                linkTo(TeamRestController.class).slash("/create").withRel("create"));
        return new ResponseEntity<>(teamsResources  , HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<TeamResource> getTeamById(@PathVariable("id") long id) {

        logger.debug("rest getTeamById()");
        try {
            TeamDTO teamDTO = teamFacade.findById(id);
            TeamResource resource = teamResourceAssembler.toResource(teamDTO);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

    @RequestMapping(value = "/{teamId}/players/{playerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void removePlayerFromTeam(@PathVariable("teamId") long teamId, @PathVariable("playerId") long playerId) {

        logger.debug("rest removePlayerFromTeam()");
        try {
            teamFacade.removePlayerFromTeam(playerId, teamId);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTeam(@PathVariable("teamId") long teamId) {

        logger.debug("rest getTeamById()");
        try {
            teamFacade.remove(teamId);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

    @RequestMapping(value = "/{teamId}/players/{playerId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void addPlayerToTeam(@PathVariable("teamId") long teamId, @PathVariable("playerId") long playerId) {

        logger.debug("rest addPlayerToTeam()");
        try {
            teamFacade.addPlayerToTeam(playerId, teamId);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

//    @RequestMapping(value = "/{teamId}/players", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final List<PlayerDTO> getAllPlayersInTeam(@PathVariable("teamId") long teamId) {
//
//        logger.debug("rest getAllPlayersInTeam()");
//        try {
//            return teamFacade.getAllPlayersInTeam(teamId);
//        } catch (SoccerManagerServiceException ex) {
//            throw ExceptionSorter.throwException(ex);
//        }
//    }

    @RequestMapping(value = "/{country}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resources<TeamResource>> findByCountry(@PathVariable("country") String country) {

        logger.debug("rest findByCountry()");
        try {
            List<TeamResource> resourceCollection = teamResourceAssembler.toResources(teamFacade.findByCountry(country));
            Resources<TeamResource> teamsResources = new Resources<>(resourceCollection,
                    linkTo(TeamRestController.class).withSelfRel(),
                    linkTo(TeamRestController.class).slash("/create").withRel("create"));
            return new ResponseEntity<>(teamsResources, HttpStatus.OK);
        } catch (SoccerManagerServiceException ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Void> createProduct(@RequestBody @Valid TeamCreateDTO teamCreateDTO, BindingResult bindingResult) {

        logger.debug("rest createTeam()");
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Failed validation");
        }
        try {
            teamFacade.create(teamCreateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SoccerManagerServiceException ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }



}
