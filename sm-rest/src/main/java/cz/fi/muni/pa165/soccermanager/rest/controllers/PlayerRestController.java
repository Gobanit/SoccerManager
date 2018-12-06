package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.PlayerFacade;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.PlayerResourceAssembler;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.TeamResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(PlayerDTO.class)
@RequestMapping("/players")
public class PlayerRestController {

    final static Logger logger = LoggerFactory.getLogger(PlayerRestController.class);

    private PlayerFacade playerFacade;
    private PlayerResourceAssembler playerResourceAssembler;

    @Autowired
    public PlayerRestController(PlayerFacade playerFacade, PlayerResourceAssembler playerResourceAssembler) {
        this.playerFacade= playerFacade;
        this.playerResourceAssembler = playerResourceAssembler;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<Resource<PlayerDTO>> getPlayerById(@PathVariable("id") long id) {

        logger.debug("rest getPlayerById()");
        try {
            PlayerDTO playerDTO= playerFacade.findPlayerById(id);
            Resource<PlayerDTO> resource = playerResourceAssembler.toResource(playerDTO);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception ex) {
            throw ExceptionSorter.throwException(ex);
        }
    }
}
