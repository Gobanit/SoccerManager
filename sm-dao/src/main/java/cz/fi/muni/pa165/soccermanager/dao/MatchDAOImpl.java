package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * Implementation of {@link MatchDAO}
 *
 * @author Michal Mazourek
 */

public class MatchDAOImpl implements MatchDAO {

    @PersistenceContext
    private EntityManager entityManager;
	
    @Transactional
	@Override
	public void save(Match match) {
		entityManager.persist(match);
	}

    @Transactional
	@Override
	public void delete(Match match) {
    	entityManager.remove(match);
	}

    @Transactional
	@Override
	public Match findById(Long id) {
    	return entityManager.find(Match.class, id);
	}

    @Transactional
	@Override
	public List<Match> findAll() {
    	return entityManager.createQuery("from Match", Match.class).getResultList();
	}

}
