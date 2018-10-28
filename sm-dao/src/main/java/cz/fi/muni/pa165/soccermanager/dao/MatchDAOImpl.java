package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.soccermanager.data.Match;

/**
 * Implementation of {@link MatchDAO}
 *
 * @author Michal Mazourek
 */

@Repository
public class MatchDAOImpl implements MatchDAO {

    @PersistenceContext
    private EntityManager entityManager;
	
    @Override
    public void save(Match match) {
    	entityManager.persist(match);
    }

    @Override
    public void delete(Match match) {
    	entityManager.remove(match);
    }

    @Override
    public Match findById(Long id) {
    	return entityManager.find(Match.class, id);
    }

    @Override
    public List<Match> findAll() {
    	return entityManager.createQuery("from Match", Match.class).getResultList();
    }

	@Override
	public Match update(Match match) {
		return entityManager.merge(match);
	}

}
