/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.enums.Footed;
import cz.fi.muni.pa165.soccermanager.data.enums.Position;
import cz.fi.muni.pa165.soccermanager.main.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.main.PersistenceBeansConfig;

/**
 * Test for SoccerManagerDAO implementation class. 
 * Tests various situations that could happen when used.
 * @author Michal Randak
 *
 */

@ContextConfiguration(classes = {PersistenceBeansConfig.class, DAOBeansConfig.class })
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SoccerPlayerDAOTest extends AbstractTestNGSpringContextTests {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private SoccerPlayerDAO playerDAO;
	
	@Test
	@Transactional
	public void testCreate() {
		// generate some player
		SoccerPlayer sp = generateDummyPlayer("Thierry Henry");		
		
		// call DAO create to persist it
		playerDAO.create(sp);
		
		// player id should be already available
		Assert.assertNotNull(sp.getId());

		// find it in DB
		SoccerPlayer found = em.find(SoccerPlayer.class, sp.getId());
		
		// check Assertions
		Assert.assertNotNull(found);
		Assert.assertEquals(sp.getPlayerName(), found.getPlayerName());
		Assert.assertEquals(sp, found);
	}
	
	@Test
	@Transactional
	public void testFindAll() {
		// prepare data
		SoccerPlayer sp1 = generateDummyPlayer("Ronaldinho");	
		SoccerPlayer sp2 = generateDummyPlayer("Buffon");		
		SoccerPlayer sp3 = generateDummyPlayer("Fabregas");		
		em.persist(sp1);
		em.persist(sp2);
		em.persist(sp3);

		// call DAO findAll method
		List<SoccerPlayer> players = playerDAO.findAll();		
		
		// prepare equal players for assert
		SoccerPlayer spAssert1 = generateDummyPlayer("Ronaldinho");	
		SoccerPlayer spAssert2 = generateDummyPlayer("Buffon");		
		SoccerPlayer spAssert3 = generateDummyPlayer("Fabregas");
		
		// check assertions
		Assert.assertEquals(players.size(), 3);		
		Assert.assertTrue(players.contains(spAssert1));
		Assert.assertTrue(players.contains(spAssert2));
		Assert.assertTrue(players.contains(spAssert3));
	}
	
	@Test
	@Transactional
	public void testUpdate() {
		// prepare data
		SoccerPlayer sp = generateDummyPlayer("Thierry Henry");		
		sp.setPosition(Position.OFFENSE);
		em.persist(sp);

		// change some value
		sp.setPosition(Position.DEFFENSE);
		
		// call DAO update method
		playerDAO.update(sp);
		
		// find row with the same id
		SoccerPlayer found = em.find(SoccerPlayer.class, sp.getId());
		
		// check Assertions
		Assert.assertNotNull(found);
		Assert.assertEquals(sp.getPlayerName(), found.getPlayerName());
		Assert.assertEquals(found.getPosition(), Position.DEFFENSE);
	}
	
	@Test
	@Transactional
	public void testRemove() {
		// prepare data
		SoccerPlayer sp = generateDummyPlayer("Thierry Henry");		
		em.persist(sp);

		// call DAO delete method
		playerDAO.delete(sp);
		
		// find the player
		SoccerPlayer found = em.find(SoccerPlayer.class, sp.getId());
		
		// check Assertions
		Assert.assertNull(found);
	}
	
	@Test(expectedExceptions=PersistenceException.class)
	@Transactional
	public void nullPlayerNameNotAllowed(){
		// generate player with null name
		SoccerPlayer sp = generateDummyPlayer(null);
		
		// call DAO create method
		playerDAO.create(sp);		
	}
	
	@Test
	@Transactional
	public void createPlayerWithTeam(){
		// prepare data
		SoccerPlayer sp = generateDummyPlayer("Thierry Henry");
		Team t = new Team();
		t.setClubName("Arsenal");
		t.setChampionshipName("Premier League");
		t.setCountry("England");
		sp.setTeam(t);		
		em.persist(t);
		
		// call DAO create method
		playerDAO.create(sp);
		
		// check Assertions
		SoccerPlayer found = em.find(SoccerPlayer.class, sp.getId());
		Assert.assertNotNull(found);
		Assert.assertNotNull(found.getTeam());
		Assert.assertEquals(t, found.getTeam());
	}
	
	/**
	 * Used to generate some "dummy" player with required name 
	 * but any values for other fields to fullfill notNull constraints
	 */
	private SoccerPlayer generateDummyPlayer(String name) {
		SoccerPlayer sp = new SoccerPlayer();
		sp.setPlayerName(name);
		sp.setPosition(Position.OFFENSE);
		sp.setAge(10);
		sp.setNationality("Fchrance");
		sp.setFooted(Footed.RIGHT);
		sp.setRating(97);
		return sp;
	}
	
}
