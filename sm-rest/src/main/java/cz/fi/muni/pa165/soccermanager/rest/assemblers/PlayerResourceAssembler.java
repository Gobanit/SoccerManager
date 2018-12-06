package cz.fi.muni.pa165.soccermanager.rest.assemblers;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.rest.controllers.PlayerRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<PlayerDTO, Resource<PlayerDTO>> {

    private final static Logger log = LoggerFactory.getLogger(PlayerResourceAssembler.class);

    @Override
    public Resource<PlayerDTO> toResource(PlayerDTO playerDTO) {
        Resource<PlayerDTO> playerResource= new Resource<>(playerDTO);
        try {
            playerResource.add(linkTo(PlayerRestController.class).slash(playerDTO.getId()).withSelfRel());

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return playerResource;
    }
}
