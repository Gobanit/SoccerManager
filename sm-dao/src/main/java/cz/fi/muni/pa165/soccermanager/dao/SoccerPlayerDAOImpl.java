package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SoccerPlayerDAOImpl implements SoccerPlayerDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<SoccerPlayer> findAll() {
		return em.createQuery("select sp from SoccerPlayer sp", SoccerPlayer.class).getResultList();
	}

	@Override
	public SoccerPlayer findById(Long id) {
		return em.find(SoccerPlayer.class, id);
	}

	@Override
	public void create(SoccerPlayer player) {
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
