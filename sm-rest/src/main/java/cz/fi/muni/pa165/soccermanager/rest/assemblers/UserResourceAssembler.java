package cz.fi.muni.pa165.soccermanager.rest.assemblers;

import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.rest.controllers.UserRestController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michal Mazourek
 */

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>>{

    @Override
    public Resource<UserDTO> toResource(UserDTO user) {
        Resource<UserDTO> userResource = new Resource<UserDTO>(user);
        
        try {
            userResource.add(linkTo(UserRestController.class).slash(user.getId()).withSelfRel().withType("GET"));
        } catch (Exception ex) {
            Logger.getLogger(UserResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from UserController", ex);
        }
        
        return userResource;
    }
    
}
