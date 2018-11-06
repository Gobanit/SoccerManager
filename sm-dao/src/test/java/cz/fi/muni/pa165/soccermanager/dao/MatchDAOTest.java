package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.dao.config.DAOBeansConfig;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.Team;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * Tests for MatchDAOImpl class.
 *
 * @author Lenka Horvathova
 */
@ContextConfiguration(classes = {DAOBeansConfig.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MatchDAOTest extends AbstractTestNGSpringContextTests {

    @Inject
    public MatchDAO matchDAO;

    @Inject
    public TeamDAO teamDAO;

    private Match match;
    private Team awayTeam;
    private Team homeTeam;

    private static final LocalDateTime DATE = LocalDateTime.of(2018, Month.OCTOBER, 24, 15, 0);
    private static final LocalDateTime NEW_DATE = LocalDateTime.of(2018, Month.MARCH, 13, 16, 30);

    @BeforeMethod
    public void prepareMatches() {
        awayTeam = new Team();
        awayTeam.setClubName("Manchester United");
        awayTeam.setChampionshipName("UEFA");
        awayTeam.setCountry("England");
        awayTeam.setBudget(new BigDecimal(999999999));

        homeTeam = new Team();
        homeTeam.setClubName("Viktoria Plzen");
        homeTeam.setChampionshipName("Fortuna");
        homeTeam.setCountry("Czech Republic");
        homeTeam.setBudget(new BigDecimal(10000000));

        teamDAO.save(awayTeam);
        teamDAO.save(homeTeam);

        match = new Match();
        match.setDate(DATE);
        match.setAwayTeam(awayTeam);
        match.setAwayTeamGoals(5);
        match.setHomeTeam(homeTeam);
        match.setHomeTeamGoals(1);

        matchDAO.save(match);
    }

    @Test
    public void testDelete() {
        Match matchToBeDeleted = matchDAO.findById(match.getId());

        Assert.assertNotNull(matchToBeDeleted);
        Assert.assertEquals(matchToBeDeleted, match);

        matchDAO.delete(match);

        matchToBeDeleted = matchDAO.findById(match.getId());

        Assert.assertNull(matchToBeDeleted);
    }

    @Test
    public void testFindById() {
        Match foundMatch = matchDAO.findById(match.getId());

        Assert.assertEquals(foundMatch.getDate(), DATE);
        Assert.assertEquals(foundMatch.getAwayTeam().getId(), awayTeam.getId());
        Assert.assertEquals((int) foundMatch.getAwayTeamGoals(), 5);
        Assert.assertEquals(foundMatch.getHomeTeam().getId(), homeTeam.getId());
        Assert.assertEquals((int) foundMatch.getHomeTeamGoals(), 1);
    }

    @Test
    public void testFindAll() {
        List<Match> foundMatches = matchDAO.findAll();

        Assert.assertEquals(foundMatches.size(), 1);
        Assert.assertTrue(foundMatches.contains(match));
    }

    @Test
    public void testUpdate() {
        match.setDate(NEW_DATE);
        matchDAO.update(match);

        Assert.assertEquals(match.getDate(), NEW_DATE);
    }
}