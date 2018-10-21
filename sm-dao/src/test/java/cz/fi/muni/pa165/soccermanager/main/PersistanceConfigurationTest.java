package cz.fi.muni.pa165.soccermanager.main;
/**
 * 
 */

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michal Randak
 *
 */

@Configuration
@ComponentScan(basePackageClasses = DummyDBAccessImpl.class)
public class PersistanceConfigurationTest {

	private AnnotationConfigApplicationContext createSpringContext() {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.register(PersistenceBeansConfig.class);
		appContext.register(PersistanceConfigurationTest.class);
		appContext.refresh();
		return appContext;
	}

	@Test
	public void testTransactionsCommiting() {
		AnnotationConfigApplicationContext appContext = null;
		try {
			appContext = createSpringContext();
			DummyDBAccess ptb = appContext.getBean(DummyDBAccess.class);
			ptb.write();
			int results = ptb.read();
			Assert.assertTrue(results > 0);
		} finally {
			if (appContext != null)
				appContext.close();
		}
	}
}
