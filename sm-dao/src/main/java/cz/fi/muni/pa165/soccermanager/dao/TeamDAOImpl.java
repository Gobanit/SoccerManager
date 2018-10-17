/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * 
 * Implementation of {@link TeamDAO}
 * @author Michal Randak
 *
 */
@Named
public class TeamDAOImpl implements TeamDAO {

	private EntityManagerFactory emf;

	@Inject
	public TeamDAOImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Team> findAll() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
	        List<Team> teams = em.createQuery("from Team", Team.class).getResultList();
	        em.getTransaction().commit();
	        return teams;
		} finally {
			if(em != null) em.close();
		}
	}

	@Override
	public Team findById(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Team team = em.find(Team.class, id);
	        em.getTransaction().commit();
	        return team;
		} finally {
			if(em != null) em.close();
		}

	}

	@Override
	public Team saveOrUpdate(Team entity) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			if(entity != null && entity.getId() != null
					&& em.find(Team.class, entity.getId()) == null) {
				em.persist(entity);
			} else {
				entity = em.merge(entity);
			}			
	        em.getTransaction().commit();
	        return entity;
		} finally {
			if(em != null) em.close();
		}
	
	}
	
	@Override
	public void delete(Team entity) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
	        em.remove(entity);
	        em.getTransaction().commit();
		} finally {
			if(em != null) em.close();
		}

	}

}