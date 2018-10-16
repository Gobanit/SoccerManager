package cz.fi.muni.pa165.soccermanager.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.fi.muni.pa165.soccermanager.data.DummyObject;

public class SoccerManagerDAOModuleMain {
    private static final Logger LOG = LoggerFactory.getLogger(SoccerManagerDAOModuleMain.class);

	public static void main(String[] args) {
		LOG.info("SoccerManagerDAOModuleMain main starting");
		
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);

		EntityManagerFactory emf = null;
		try {
			emf = Persistence.createEntityManagerFactory("default");
			PeristanceTest(emf);
		} finally {
			if(emf != null) emf.close();
			if(appContext != null) appContext.close();
		}
		

	}

	private static void PeristanceTest(EntityManagerFactory emf) {
		EntityManager em = null;
		
		try { 
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			DummyObject dummy = new DummyObject();
			dummy.setName("TestObject");
			
			em.persist(dummy);
			LOG.info("Dummy id = "+dummy.getId());
			LOG.info("Dummy name = "+dummy.getName());

			em.getTransaction().commit();
		} finally { 
			if(em != null) em.close();
		}
		
	}

}
