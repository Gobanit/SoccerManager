/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.MatchFacade;
import cz.fi.muni.pa165.soccermanager.rest.ExceptionSorter;
import cz.fi.muni.pa165.soccermanager.rest.assemblers.MatchesResourceAssembler;

/**
 * @author Michal Randak
 *
 */

@RestController
@RequestMapping("/matches")
public class MatchController {

	private MatchFacade matchFacade;
	private MatchesResourceAssembler matchResourceAssembler;

	/**
	 *
	 * @param matchFacade
	 * @param matchResourceAssembler
	 */
	@Inject
	public MatchController(MatchFacade matchFacade, MatchesResourceAssembler matchResourceAssembler) {
		super();
		this.matchFacade = matchFacade;
		this.matchResourceAssembler = matchResourceAssembler;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<Resource<MatchDTO>>> getAll() {
		try {
			Collection<MatchDTO> matchDTOs = matchFacade.findAll();
	        Collection<Resource<MatchDTO>> matchResourceCollection = new ArrayList<>();
	
	        for (MatchDTO m : matchDTOs) {
	        	matchResourceCollection.add(matchResourceAssembler.toResource(m));
	        }
	
	        Resources<Resource<MatchDTO>> matchResources = new Resources<Resource<MatchDTO>>(matchResourceCollection);
	        matchResources.add(linkTo(MatchController.class).withSelfRel().withType("GET"));
	
	        return new ResponseEntity<Resources<Resource<MatchDTO>>>(matchResources, HttpStatus.OK);
		} catch(Exception ex) {
			throw ExceptionSorter.throwException(ex);
		}
	}


	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MatchDTO> create(@RequestBody MatchCreateDTO body) {
		try {
			Long id = matchFacade.create(body);
			MatchDTO match = matchFacade.findById(id);
			return matchResourceAssembler.toResource(match);
		} catch(Exception ex) {
			throw ExceptionSorter.throwException(ex);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MatchDTO> getById(@PathVariable Long id) {
		try {
			MatchDTO match = matchFacade.findById(id);		
			return matchResourceAssembler.toResource(match);
		} catch(Exception ex) {
			throw ExceptionSorter.throwException(ex);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable Long id) {
		try {
			matchFacade.remove(id);
		} catch(Exception ex) {
			throw ExceptionSorter.throwException(ex);
		}
	}	
	
	@RequestMapping(value = "/simulate/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<MatchDTO> simulateMatches(@PathVariable Long id) {
		try {
			matchFacade.simulateMatch(id);
			MatchDTO match = matchFacade.findById(id);
			return matchResourceAssembler.toResource(match);
		} catch(Exception ex) {
			throw ExceptionSorter.throwException(ex);
		}
	}
}
