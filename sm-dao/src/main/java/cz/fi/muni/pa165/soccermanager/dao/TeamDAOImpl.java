/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * 
 * Implementation of {@link TeamDAO}
 * 
 * @author Michal Randak
 *
 */
@Named
public class TeamDAOImpl implements TeamDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public List<Team> findAll() {
		return em.createQuery("from Team", Team.class).getResultList();
	}

	@Transactional
	@Override
	public Team findById(Long id) {
		return em.find(Team.class, id);
	}

	@Transactional
	@Override
	public void create(Team team) {
		em.persist(team);
	}

	@Transactional
	@Override
	public Team update(Team team) {
		return em.merge(team);
	}

	@Transactional
	@Override
	public void delete(Team entity) {
		em.remove(entity);
	}

}