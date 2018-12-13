package cz.fi.muni.pa165.soccermanager.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.PlayerResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private UserFacade userFacade;
    private TeamResourceAssembler teamResourceAssembler;
    private PlayerResourceAssembler playerResourceAssembler;


    @Autowired
    public TeamRestController(TeamFacade teamFacade, TeamResourceAssembler teamResourceAssembler, UserFacade userFacade, PlayerResourceAssembler playerResourceAssembler) {
        this.teamFacade = teamFacade;
        this.teamResourceAssembler = teamResourceAssembler;
        this.userFacade = userFacade;
        this.playerResourceAssembler = playerResourceAssembler;
    }

    @RolesAllowed("ROLE_USER")
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

    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{id}/players" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<Resource<PlayerDTO>>> getPlayersOfTeam(@PathVariable("id") long id) {

        logger.debug("rest getPlayersOfTeam()");
        List<PlayerDTO> allPlayers = teamFacade.getAllPlayersInTeam(id);
        List<Resource<PlayerDTO>> playerResourceList= new ArrayList<>();

        for (PlayerDTO playerDTO: allPlayers) {
            playerResourceList.add(playerResourceAssembler.toResource(playerDTO));
        }
        Link selfLink = linkTo(TeamRestController.class).slash(id).slash("/players").withSelfRel();
        return new ResponseEntity<>(new Resources<>(playerResourceList, selfLink)  , HttpStatus.OK);
    }

    @RolesAllowed("ROLE_ADMIN")
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
    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{teamId}/players/{playerId}", method = RequestMethod.DELETE)
    public final HttpEntity<Void> removePlayerFromTeam(@PathVariable("teamId") long teamId, @PathVariable("playerId") long playerId) {

        logger.debug("rest removePlayerFromTeam()");
        try {
            teamFacade.removePlayerFromTeam(playerId, teamId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
    public final HttpEntity<Void> deleteTeam(@PathVariable("teamId") long teamId) {

        logger.debug("rest deleteTeam()");
        try {
            teamFacade.remove(teamId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{teamId}/players/{playerId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void addPlayerToTeam(@PathVariable("teamId") long teamId, @PathVariable("playerId") long playerId) {

        logger.debug("rest addPlayerToTeam()");
        try {
            teamFacade.addPlayerToTeam(playerId, teamId);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<TeamDTO>> getTeamOfUser() {

        logger.debug("rest getTeamOfUser()");
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info(userName);
            TeamDTO teamDTO = teamFacade.findById(1L);//userFacade.getTeamOfUser(userName);
            logger.debug(teamDTO + "");
            Resource<TeamDTO> resource = teamResourceAssembler.toResource(teamDTO);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (SoccerManagerServiceException ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(value = "/country/{country}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Void> createTeam(@RequestBody @Valid TeamCreateDTO teamCreateDTO, BindingResult bindingResult) {

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
