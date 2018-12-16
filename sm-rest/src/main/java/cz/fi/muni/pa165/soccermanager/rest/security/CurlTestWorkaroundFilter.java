/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cz.fi.muni.pa165.soccermanager.api.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.UserFacade;

/**
 * Filter serves as workaround for curl tests. 
 * In case of CurlTest header in request, it logs in admin user.
 * 
 * @author Michal Randak
 *
 */
@WebFilter("/*")
public class CurlTestWorkaroundFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(CurlTestWorkaroundFilter.class);

	@Override
	public void init(FilterConfig cfg) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next)
			throws IOException, ServletException {
		LOG.info("doFilter: ");

        if(isCurlTestRequest(req)) {
        	ServletContext sc = req.getServletContext();
        	UserFacade userFacade = WebApplicationContextUtils.
        			getWebApplicationContext(sc).getBean(UserFacade.class);
        	
        	UserAuthenticateDTO auth = new UserAuthenticateDTO();
        	auth.setUsername("admin");
        	auth.setRawPassword("pass");
        	userFacade.authenticateUser(auth);
        }
		
		// continue filter chain
		next.doFilter(req, resp);
	}

	@Override
	public void destroy() {}
	
	private boolean isCurlTestRequest(ServletRequest req) {
		// need http request to check headers
		HttpServletRequest request = (HttpServletRequest) req;

		// find out if special header is sent
        String header = request.getHeader("CurlTest");
        return Boolean.parseBoolean(header);
    }
}
