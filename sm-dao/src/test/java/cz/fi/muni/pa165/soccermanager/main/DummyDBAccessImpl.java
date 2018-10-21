/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.main;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import cz.fi.muni.pa165.soccermanager.data.DummyObject;

/**
 * @author Michal Randak
 *
 */
@Named
public class DummyDBAccessImpl implements DummyDBAccess {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	@Override
	public void write() {
		DummyObject d = new DummyObject();
		d.setName("Abc");
		em.persist(d);		
	}

	@Transactional
	@Override
	public int read() {
		List<DummyObject> list = em.createQuery("from DummyObject", DummyObject.class).getResultList();
		return list.size();
	}
}
