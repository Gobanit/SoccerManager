package cz.fi.muni.pa165.soccermanager.rest.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class configures Spring ServletDispatcher
 * @author Michal Randak
 *
 */
public class RestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RestBeansConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("utf-8");
		encodingFilter.setForceEncoding(true);

		ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();

		return new Filter[] { encodingFilter, shallowEtagHeaderFilter };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws javax.servlet.ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);
	}

}
