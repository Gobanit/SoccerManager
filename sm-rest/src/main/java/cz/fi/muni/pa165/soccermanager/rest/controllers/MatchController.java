/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchAwaitingDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.MatchFacade;

/**
 * @author Michal Randak
 *
 */

@RestController
@RequestMapping("/matches")
public class MatchController {

	private MatchFacade matchFacade;

	/**
	 * @param matchFacade
	 */
	@Inject
	public MatchController(MatchFacade matchFacade) {
 		this.matchFacade = matchFacade;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MatchDTO> getAll() {
		return matchFacade.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public MatchDTO create(@RequestBody MatchCreateDTO body) {
		Long id = matchFacade.create(body);
		return matchFacade.findById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public MatchDTO getById(@PathVariable Long id) {
		return matchFacade.findById(id);		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable Long id) {
		matchFacade.remove(id);
		//TODO catch exception
	}	
	
	@RequestMapping(value = "/simulate/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public MatchDTO simulateMatches(@PathVariable Long id) {
		matchFacade.simulateMatch(id);
		return matchFacade.findById(id);
	}
}
