package cz.fi.muni.pa165.soccermanager.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.dao.config.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;

/**
 * Test for TeamDAO implementation class.
 * @author Michal Mazourek
 *
 */

@ContextConfiguration(classes = {DAOBeansConfig.class })
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TeamDAOTest extends AbstractTestNGSpringContextTests {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private TeamDAO teamDAO;
	
	private Team team1, team2;
	
	@BeforeMethod
	public void createTeams() {
		team1 = new Team();
		team1.setClubName("Manchester United");
		team1.setChampionshipName("UEFA");
		team1.setCountry("England");
		team1.setBudget(new BigDecimal(999999999));
		
		team2 = new Team();
		team2.setClubName("Viktoria Plzen");
		team2.setChampionshipName("Fortuna");
		team2.setCountry("Czech Republic");
		team2.setBudget(new BigDecimal(10000000));
		
		teamDAO.save(team1);
		teamDAO.save(team2);
	}
	
	@Test
	public void testFind() {
		Team found = teamDAO.findById(team1.getId());
		
		Assert.assertNotNull(found);
		Assert.assertEquals(found.getClubName(), "Manchester United");
		Assert.assertEquals(found.getChampionshipName(), "UEFA");
		Assert.assertEquals(found.getCountry(), "England");
		Assert.assertEquals(found.getBudget(), new BigDecimal(999999999));
	}
	
	@Test
	public void testFindAll() {
		List<Team> teams = teamDAO.findAll();
		
		Assert.assertEquals(teams.size(), 2);
		Assert.assertTrue(teams.contains(team1));
		Assert.assertTrue(teams.contains(team2));
	}
	
	@Test
	public void testDelete() {
		teamDAO.delete(team1);
		Team found = em.find(Team.class, team1.getId());
		
		Assert.assertNull(found);
	}
	
	@Test
	public void testUpdate() {
		team1.setBudget(new BigDecimal(400000000));
		teamDAO.update(team1);
		Team found = em.find(Team.class, team1.getId());
		
		Assert.assertNotNull(found);
		Assert.assertEquals(found.getBudget(), new BigDecimal(400000000));
	}
	
	@Test
	public void testTeamWithPlayers(){
		SoccerPlayer p1 = new SoccerPlayer();
		SoccerPlayer p2 = new SoccerPlayer();
		
		List<SoccerPlayer> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		
		team1.setPlayers(players);
		teamDAO.update(team1);
		Team found = em.find(Team.class, team1.getId());
		
		Assert.assertNotNull(found);
		Assert.assertNotNull(found.getPlayers());
		Assert.assertEquals(found.getPlayers(), players);
	}
	
}
