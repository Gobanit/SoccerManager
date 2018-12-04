package cz.fi.muni.pa165.soccermanager.rest.assemblers;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.rest.controllers.TeamRestController;
import cz.fi.muni.pa165.soccermanager.rest.hateoas.TeamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TeamResourceAssembler implements ResourceAssembler<TeamDTO, Resource<TeamDTO>> {


    private final static Logger log = LoggerFactory.getLogger(TeamResourceAssembler.class);

    @Override
    public Resource<TeamDTO> toResource(TeamDTO teamDTO) {
        Resource<TeamDTO> teamResource= new Resource<TeamDTO>(teamDTO);
        try {
            teamResource.add(linkTo(TeamRestController.class).slash(teamDTO.getId()).withSelfRel());

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return teamResource;
    }
}
