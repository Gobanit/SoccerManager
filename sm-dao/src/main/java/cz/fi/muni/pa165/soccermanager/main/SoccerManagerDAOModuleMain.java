package cz.fi.muni.pa165.soccermanager.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.fi.muni.pa165.soccermanager.data.DummyObject;

public class SoccerManagerDAOModuleMain {

	public static void main(String[] args) {
		System.out.println("SoccerManagerDAOModuleMain main");
		
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
			System.out.println("Dummy id = "+dummy.getId());
			System.out.println("Dummy name = "+dummy.getName());

			em.getTransaction().commit();
		} finally { 
			if(em != null) em.close();
		}
		
	}

}
