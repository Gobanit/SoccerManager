package cz.fi.muni.pa165.soccermanager.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security configuration
 * @author Michal Randak
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = false, 
		  securedEnabled = false, 
		  jsr250Enabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// disabling csrf for easier handling in client
		http.csrf().disable();
		
		// allow any url patterns, security is handled on 
    	// method level individually
		http.authorizeRequests().anyRequest().permitAll(); 
        
		// set page to redirect if session is invalid
		//http.sessionManagement().invalidSessionUrl("/#!/login");
	}
}