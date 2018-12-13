package cz.fi.muni.pa165.soccermanager.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cz.fi.muni.pa165.soccermanager.rest.security.CustomUserDetailsService;

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
@ComponentScan(basePackageClasses = { CustomUserDetailsService.class })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// disabling csrf for easier handling in client
		http.csrf().disable();
		
		// allow any url patterns, security is handled on 
    	// method level individually
		http.authorizeRequests().anyRequest().permitAll(); 
        
        // create generic login form	
//        http.formLogin();
		
//		http.httpBasic();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

}