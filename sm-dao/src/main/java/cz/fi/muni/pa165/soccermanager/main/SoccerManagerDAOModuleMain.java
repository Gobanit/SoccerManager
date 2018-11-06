package cz.fi.muni.pa165.soccermanager.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.fi.muni.pa165.soccermanager.dao.config.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.dao.config.PersistenceBeansConfig;

/**
 * @author Michal Randak
 *
 */
public class SoccerManagerDAOModuleMain {
    private static final Logger LOG = LoggerFactory.getLogger(SoccerManagerDAOModuleMain.class);

	public static void main(String[] args) {
		LOG.info("SoccerManagerDAOModuleMain main starting");
		
		AnnotationConfigApplicationContext appContext = null;
		try {
			appContext = new AnnotationConfigApplicationContext();
			appContext.register(PersistenceBeansConfig.class);
			appContext.register(DAOBeansConfig.class);
			appContext.refresh();			
		} finally {
			if(appContext != null) appContext.close();
		}
	}
	
	



}
