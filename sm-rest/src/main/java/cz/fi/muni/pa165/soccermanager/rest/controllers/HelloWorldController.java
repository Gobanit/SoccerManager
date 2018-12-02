/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michal Randak
 *
 */

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping(method = RequestMethod.GET)
	public String helloWorld() {
    	return "Hello World";
    }
}
