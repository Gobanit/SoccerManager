package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.SoccerPlayerDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Michal Mazourek
 */
public class PlayerServiceTest {
    
    private PlayerService playerService;
    
    @Mock
    private SoccerPlayerDAO playerDAO;
    
    private SoccerPlayer goodPlayer, averagePlayer, badPlayer;
    private Team team;
    
    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        playerService = new PlayerServiceImpl(playerDAO);
        
        team = new Team();
        team.setId(10L);
        team.setBudget(BigDecimal.valueOf(500000));
        team.setChampionshipName("Masters");
        team.setClubName("Good Team");
        team.setCountry("England");
        team.setPlayers(new ArrayList<>());
        
        goodPlayer = this.player(1L, "Good Player", "England", 
                LocalDate.of(1992, Month.JANUARY, 1),
                90, team, Position.OFFENSE, Footed.BOTH);
        
        averagePlayer = this.player(2L, "Average Player", "Croatia", 
                LocalDate.of(1991, Month.JUNE, 2),
                50, null, Position.DEFFENSE, Footed.RIGHT);
        
        badPlayer = this.player(3L, "Bad Player", "Malta", 
                LocalDate.of(1988, Month.JULY, 10),
                10, null, Position.MIDFIELD, Footed.LEFT);
        
        team.addPlayer(goodPlayer);
    }
    
    /**
     * Set player parameters
     */
    private SoccerPlayer player(Long id, String playerName, String nationality, 
            LocalDate birthday, Integer rating, Team team, Position position, Footed footed) {
        SoccerPlayer player = new SoccerPlayer();
        player.setId(id);
        player.setPlayerName(playerName);
        player.setNationality(nationality);
        player.setBirthDate(birthday);
        player.setRating(rating);
        player.setTeam(team);
        player.setPosition(position);
        player.setFooted(footed);
        return player;
    }
    
    @Test
    public void createPlayerTest() {
        playerService.createPlayer(goodPlayer);
        then(playerDAO).should().save(goodPlayer);
    }
    
    @Test
    public void removePlayerTest() {
        playerService.removePlayer(averagePlayer);
        then(playerDAO).should().delete(averagePlayer);
    }
    
    @Test
    public void findPlayerByIdTest() {
        given(playerDAO.findById(goodPlayer.getId())).willReturn(goodPlayer);
        SoccerPlayer player = playerService.findPlayerById(goodPlayer.getId());
        Assert.assertEquals(goodPlayer, player);
    }
    
    @Test(expectedExceptions = SoccerManagerServiceException.class)
    public void removePlayerWithTeamTest() {
        given(playerDAO.findById(goodPlayer.getId())).willReturn(goodPlayer);
        playerService.removePlayer(goodPlayer);
    }
    
    @Test
    public void findAllPlayersTest() {
        given(playerDAO.findAll()).willReturn(Arrays.asList(goodPlayer, averagePlayer, 
                badPlayer));
        List<SoccerPlayer> found = playerService.findAllPlayers();
        
        Assert.assertEquals(3, found.size());
        Assert.assertTrue(found.contains(goodPlayer));
        Assert.assertTrue(found.contains(averagePlayer));
        Assert.assertTrue(found.contains(badPlayer));

    }
    
    @Test
    public void findFreePlayersTest() {
        given(playerDAO.findAllFreePlayers()).willReturn(Arrays.asList(averagePlayer, 
                badPlayer));
        List<SoccerPlayer> foundFree = playerService.findFreePlayers();
        
        Assert.assertEquals(2, foundFree.size());
        Assert.assertTrue(foundFree.contains(averagePlayer));
        Assert.assertTrue(foundFree.contains(badPlayer));
    }
    
    @Test
    public void changePlayerAttributesTest() {
        playerService.changePlayerAttributes(averagePlayer, Position.MIDFIELD, 
                Footed.BOTH, 55);
        
        Assert.assertEquals(averagePlayer.getPosition(), Position.MIDFIELD);
        Assert.assertEquals(averagePlayer.getFooted(), Footed.BOTH);
        Assert.assertEquals(averagePlayer.getRating(), new Integer(55));
    }
}
