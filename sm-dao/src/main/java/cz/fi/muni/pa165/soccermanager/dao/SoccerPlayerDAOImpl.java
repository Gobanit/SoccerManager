package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Named
public class SoccerPlayerDAOImpl implements SoccerPlayerDAO {
	private EntityManagerFactory emf;

	@Inject
	public SoccerPlayerDAOImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override public List<SoccerPlayer> findAll() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			List<SoccerPlayer> players = em.createQuery("from SoccerPlayer", SoccerPlayer.class).getResultList();
			tx.commit();
			return players;
		}
		catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; // or display error message
		}
		finally {
			em.close();
		}
	}

	@Override public SoccerPlayer findById(Long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			SoccerPlayer player = em.find(SoccerPlayer.class, id);
			tx.commit();
			return player;
		}
		catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; // or display error message
		}
		finally {
			em.close();
		}
	}

	@Override public SoccerPlayer create(SoccerPlayer player) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			em.persist(player);
			tx.commit();
			return player;
		}
		catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; // or display error message
		}
		finally {
			em.close();
		}
	}

	@Override public SoccerPlayer update(SoccerPlayer player) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			em.merge(player);
			tx.commit();
			return player;
		}
		catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; // or display error message
		}
		finally {
			em.close();
		}
	}

	@Override public void delete(SoccerPlayer player) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			em.remove(player);
			tx.commit();
		}
		catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();
			throw e; // or display error message
		}
		finally {
			em.close();
		}
	}
}
