/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * 
 * Implementation of {@link TeamDAO}
 * 
 * @author Michal Randak
 *
 */
@Repository
public class TeamDAOImpl implements TeamDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Team> findAll() {
		return em.createQuery("from Team", Team.class).getResultList();
	}

	@Override
	public Team findById(Long id) {
		return em.find(Team.class, id);
	}

	@Override
	public void save(Team team) {
		em.persist(team);
	}

	@Override
	public Team update(Team team) {
		return em.merge(team);
	}

	@Override
	public void delete(Team entity) {
		em.remove(entity);
	}

}