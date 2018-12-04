/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.rest.controllers.MatchRestController;

/**
 * @author Michal Randak
 *
 */

@Component
public class MatchesResourceAssembler implements ResourceAssembler<MatchDTO, Resource<MatchDTO>>{

	@Override
	public Resource<MatchDTO> toResource(MatchDTO match) {
        Resource<MatchDTO> matchResource = new Resource<MatchDTO>(match);

        try {
            matchResource.add(linkTo(MatchRestController.class).slash(match.getId()).withSelfRel().withType("GET"));
            matchResource.add(linkTo(MatchRestController.class).slash(match.getId()).withRel("delete").withType("DELETE"));
            matchResource.add(linkTo(MatchRestController.class).slash("simulate").slash(match.getId()).withRel("simulate").withType("POST"));

        } catch (Exception ex) {
            Logger.getLogger(MatchesResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from MatchController", ex);
        }
        
        return matchResource;

	}

}
