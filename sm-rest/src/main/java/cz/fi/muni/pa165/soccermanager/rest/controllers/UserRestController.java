package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.UserResourceAssembler;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.TeamResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Michal Mazourek
 */

@RestController
@RequestMapping("/users")
public class UserRestController {
    
    final static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    private UserFacade userFacade;
    private UserResourceAssembler userResourceAssembler;
    private TeamResourceAssembler teamResourceAssembler;
    
    @Inject
    public UserRestController(UserFacade userFacade, UserResourceAssembler userResourceAssembler, TeamResourceAssembler teamResourceAssembler) {
        this.userFacade = userFacade;
        this.userResourceAssembler = userResourceAssembler;
        this.teamResourceAssembler = teamResourceAssembler;
    }
   
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<Resource<UserDTO>>> getAll() {
        
        logger.debug("rest getAllUsers()");
        
        try {
            Collection<UserDTO> userDTOs = userFacade.getAllUsers();
            Collection<Resource<UserDTO>> userResourceCollection = new ArrayList<>();
            
            for (UserDTO user : userDTOs) {
                userResourceCollection.add(userResourceAssembler.toResource(user));
            }
            
            Resources<Resource<UserDTO>> userResources = new Resources<>(userResourceCollection);
            userResources.add(linkTo(UserRestController.class).withSelfRel().withType("GET"));
            return new ResponseEntity<>(userResources, HttpStatus.OK);
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<UserDTO> create(@RequestBody UserCreateDTO body) {
        
        logger.debug("rest registerNewUser()");
        
        try {
            Long id = userFacade.registerNewUser(body);
            UserDTO user = userFacade.getUserById(id);
            return userResourceAssembler.toResource(user);
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<UserDTO> getById(@PathVariable Long id) {
        
        logger.debug("rest getUserById()");
        
        try {
            UserDTO user = userFacade.getUserById(id);
            return userResourceAssembler.toResource(user);
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("name") String name) {
        
        logger.debug("rest deleteUser()");
        
        try {
            userFacade.deleteUser(name);
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<UserDTO> getByName(@PathVariable("name") String name) {
        
        logger.debug("rest getUserByUsername()");
        
        try {
            UserDTO user = userFacade.getUserByUsername(name);
            return userResourceAssembler.toResource(user);
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/{name}/team/{teamId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void pickTeam(@PathVariable("name") String name, @PathVariable("teamId") long teamId) {

        logger.debug("rest pickTeamForUser()");
        
        try {
            userFacade.pickTeamForUser(name, teamId);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/{name}/team", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<TeamDTO>> getTeam(@PathVariable("name") String name) {

        logger.debug("rest getTeamOfUser()");
        
        try {
            TeamDTO team = userFacade.getTeamOfUser(name);
            Resource<TeamDTO> teamResource = teamResourceAssembler.toResource(team);
            return new ResponseEntity<>(teamResource, HttpStatus.OK);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/{name}/{admin}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void changeRights(@PathVariable("name") String name, @PathVariable("admin") boolean admin) {

        logger.debug("rest changeAdminRights()");
        
        try {
            userFacade.changeAdminRights(name, admin);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean authenticateUser(@RequestBody UserAuthenticateDTO body) {
                
        logger.debug("rest authenticateUser()");
        
        try {
            boolean auth = userFacade.authenticateUser(body);
            return auth;
        } catch(Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
}
