/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;

/**
 * Class for spring security to authenticate user. 
 * Since authentication is handled with custom login controller, 
 * this class is probably not used at all. 
 * 
 * @author Michal Randak
 *
 */

@Component
public class CustomUserDetailsService implements UserDetailsService {
	private UserFacade userFacade;
	
	/**
	 * @param userFacade
	 */
	@Autowired
	public CustomUserDetailsService(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO user = userFacade.getUserByUsername(username);
		if(user == null) return null;
		
		UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
		builder.username(user.getUsername());
		builder.password(user.getPasswordHash());
		if(user.isAdmin()) builder.roles("USER", "ADMIN");
		else builder.roles("USER");
		
		UserDetails details = builder.build();

		return details;
	}

}
