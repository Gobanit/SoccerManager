package cz.fi.muni.pa165.soccermanager.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.TeamResourceAssembler;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.InvalidRequestException;

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


    @Autowired
    public TeamRestController(TeamFacade teamFacade, TeamResourceAssembler teamResourceAssembler) {
        this.teamFacade = teamFacade;
        this.teamResourceAssembler = teamResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<Resource<TeamDTO>>> teams() {

        logger.debug("rest getTeams()");
        List<TeamDTO> allTeams = teamFacade.findAll();
        List<Resource<TeamDTO>> teamResourceList = new ArrayList<>();

        for (TeamDTO teamDTO : allTeams) {
            teamResourceList.add(teamResourceAssembler.toResource(teamDTO));
        }
        Resources<Resource<TeamDTO>> teamsResources = new Resources<>(teamResourceList);
        teamsResources.add(linkTo(TeamRestController.class).withSelfRel().withType("GET"));
        return new ResponseEntity<>(teamsResources  , HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<TeamDTO>> getTeamById(@PathVariable("id") long id) {

        logger.debug("rest getTeamById()");
        try {
            TeamDTO teamDTO = teamFacade.findById(id);
            Resource<TeamDTO> resource = teamResourceAssembler.toResource(teamDTO);
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
    public final HttpEntity<Resources<Resource<TeamDTO>>> findByCountry(@PathVariable("country") String country) {

        logger.debug("rest findByCountry()");
        try {
            List<TeamDTO> allTeams = teamFacade.findByCountry(country);
            List<Resource<TeamDTO>> teamResourceList = new ArrayList<>();

            for (TeamDTO teamDTO : allTeams) {
                teamResourceList.add(teamResourceAssembler.toResource(teamDTO));
            }
            Resources<Resource<TeamDTO>> teamsResources = new Resources<>(teamResourceList);
            teamsResources.add(linkTo(TeamRestController.class).withSelfRel().withType("GET"));
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
