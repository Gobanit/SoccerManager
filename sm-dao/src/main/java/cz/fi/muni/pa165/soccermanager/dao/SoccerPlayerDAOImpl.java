package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;

/**
 * DAO implementation for soccer player
 * @author Dominik Pilar
 *
 */
@Repository
public class SoccerPlayerDAOImpl implements SoccerPlayerDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<SoccerPlayer> findAll() {
		return em.createQuery("select sp from SoccerPlayer sp", SoccerPlayer.class).getResultList();
	}

	@Override 
	public List<SoccerPlayer> findAllFreePlayers() {
		return em.createQuery("select sp from SoccerPlayer sp where sp.team is null", SoccerPlayer.class).getResultList();
	}

	@Override
	public SoccerPlayer findById(Long id) {
		return em.find(SoccerPlayer.class, id);
	}

	@Override
	public void save(SoccerPlayer player) {
		em.persist(player);
	}

	@Override
	public void update(SoccerPlayer player) {
		em.merge(player);
	}

	@Override
	public void delete(SoccerPlayer player) {
		em.remove(player);
	}
}
