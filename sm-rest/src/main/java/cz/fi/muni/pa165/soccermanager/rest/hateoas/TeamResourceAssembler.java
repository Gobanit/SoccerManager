package cz.fi.muni.pa165.soccermanager.rest.hateoas;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.rest.controllers.TeamRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TeamResourceAssembler extends ResourceAssemblerSupport<TeamDTO, TeamResource> {

    private EntityLinks entityLinks;

    private final static Logger log = LoggerFactory.getLogger(TeamResourceAssembler.class);

    @Autowired
    public TeamResourceAssembler(@SuppressWarnings("SpringJavaAutowiringInspection")
                                      EntityLinks entityLinks) {
        super(TeamRestController.class, TeamResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public TeamResource toResource(TeamDTO teamDTO) {
        long id = teamDTO.getId();
        TeamResource teamResource= new TeamResource(teamDTO);
        try {
            Link teamLink = entityLinks.linkForSingleResource(TeamDTO.class, id).withSelfRel();
            teamResource.add(teamLink);

        } catch (Exception ex) {
            log.error("cannot link HATEOAS", ex);
        }
        return teamResource;
    }
}
