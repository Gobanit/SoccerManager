/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;

/**
 * Servers as workaround for integration of custom "token-based" 
 * authorization with Spring Security objects. 
 * It finds user by token in Authorization header and then manually 
 * sets this user to security context.
 * It also overrides already logged user in spring context (based on JSESSIONID) 
 * with null, if the token is not valid.
 * 
 * @author Michal Randak
 *
 */
@WebFilter("/*")
public class UserRoleFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(UserRoleFilter.class);

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next)
			throws IOException, ServletException {
		LOG.info("doFilter: ");

        // find authenticated user based on request
        UserDTO user = findAuthenticatedUser(req);     
        		
		if(user != null) {
			// found authenticated user		
			
			// add roles based on user type
			List<String> roles = new ArrayList<>();
			roles.add("USER");
			if(user.isAdmin()) roles.add("ADMIN");
			
			// manually set user to spring context
			setSpringSecurityUser(user.getUsername(), user.getPasswordHash(), roles);
			
		} else {
			// no authenticated user
			LOG.info("Token missing or no user bind with it");
			clearSpringSecurityUser();
		}
		
		// print content of spring security context
		LOG.info("Security content:");
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			LOG.info("Authentication is null");
		} else {
			LOG.info("User: "+SecurityContextHolder.getContext().getAuthentication().getName());
			Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(GrantedAuthority r : auths) LOG.info("GrantedRole: "+r);
		}
		
		// continue filter chain
		next.doFilter(req, resp);
	}

	
	private void setSpringSecurityUser(String username, String passwordHash, List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String role : roles) authorities.add(new SimpleGrantedAuthority("ROLE_"+role));     

		User u = new User(username, passwordHash, authorities);
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));

		LOG.info("User set to security context");
	}
	
	private void clearSpringSecurityUser() {
		SecurityContextHolder.clearContext();		
	}

	private UserDTO findAuthenticatedUser(ServletRequest req) {
		// need http request to check headers
		HttpServletRequest request = (HttpServletRequest) req;

		// find if any user logged
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.equals("")) {
            return null;
        }
        
        String token = parseAuthHeader(auth);
        if(token == null) return null;
        
        //get Spring context and UserFacade from it
        UserFacade userFacade = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(UserFacade.class);
        UserDTO matchingUser = userFacade.getBySessionToken(token);
        if (matchingUser == null) {
            LOG.warn("no user found for token {}", token);
            return null;
        }
        
        return matchingUser;
	}

	private String parseAuthHeader(String auth) {
		// header should look like 'Bearer {token}'
		String[] strs = auth.split(" ");
		if(strs.length != 2) return null;
		return strs[1];
	}

	@Override
	public void destroy() {
	}
}
