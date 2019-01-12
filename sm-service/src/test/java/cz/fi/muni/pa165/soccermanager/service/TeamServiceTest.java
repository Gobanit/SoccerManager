package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.dao.TeamDAO;
import cz.fi.muni.pa165.soccermanager.dao.UserDAO;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link TeamService}.
 *
 * @author Lenka Horvathova
 */
public class TeamServiceTest {
    private TeamService teamService;

    @Mock
    private TeamDAO teamDAO;
    
    @Mock
    private UserDAO userDAO;

    @Mock
    private MatchDAO matchDAO;

    @Mock
    private Team fcb;

    @Mock
    private Team mfks;

    @Mock
    private SoccerPlayer messi;

    private List<Team> teams;
    private List<Team> slovakian;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        teamService = new TeamServiceImpl(teamDAO, userDAO, matchDAO);

        when(fcb.getId()).thenReturn(1L);
        when(fcb.getBudget()).thenReturn(BigDecimal.TEN);
        when(fcb.getCountry()).thenReturn("Spain");

        when(mfks.getCountry()).thenReturn("Slovakia");

        teams = new ArrayList<>();
        teams.add(fcb);
        teams.add(mfks);

        slovakian = new ArrayList<>();
        slovakian.add(mfks);

        when(messi.getRating()).thenReturn(10);

        SoccerPlayer coutinho = Mockito.mock(SoccerPlayer.class);
        when(coutinho.getRating()).thenReturn(8);

        ArrayList<SoccerPlayer> fcbPlayers = new ArrayList<>();
        fcbPlayers.add(messi);
        fcbPlayers.add(coutinho);
        when(fcb.getPlayers()).thenReturn(fcbPlayers);
    }

    @Test
    public void createTeamTest() {
        teamService.create(fcb);
        verify(teamDAO).save(fcb);
    }

    @Test
    public void removeTeamTest() {
        teamService.remove(fcb);
        verify(teamDAO).delete(fcb);
    }

    @Test
    public void findByIdTest() {
        when(teamDAO.findById(fcb.getId())).thenReturn(fcb);
        Team found = teamService.findById(fcb.getId());
        assertEquals(found, fcb);
    }

    @Test
    public void findAllTest() {
        when(teamDAO.findAll()).thenReturn(teams);
        List<Team> found = teamService.findAll();
        assertEquals(found, teams);
    }

    @Test
    public void findByCountryTest() {
        when(teamDAO.findAll()).thenReturn(teams);
        List<Team> found = teamService.findByCountry("Slovakia");
        assertEquals(found, slovakian);
    }

    @Test
    public void findAllPlayersInTeamTest() {
        List<SoccerPlayer> found = teamService.findAllPlayersInTeam(fcb);
        assertEquals(found, fcb.getPlayers());
    }

    @Test
    public void averageTeamRatingTest() {
        Integer result = teamService.averageTeamRating(fcb);
        assertEquals(result, (Integer) 9);
    }

    @Test(expectedExceptions = SoccerManagerServiceException.class)
    public void averageTeamRatingNoPlayersTest() {
        teamService.averageTeamRating(mfks);
    }


    @Test
    public void addPlayerToTeamTest() {
        SoccerPlayer mockPlayer = Mockito.mock(SoccerPlayer.class);
        when(mockPlayer.getTeam()).thenReturn(null);

        teamService.addPlayerToTeam(mfks, mockPlayer);
        verify(mfks).addPlayer(mockPlayer);
        verify(teamDAO).update(mfks);
    }

    @Test(expectedExceptions = SoccerManagerServiceException.class)
    public void addAlreadyAssignedPlayerToDifferentTeamTest() {
        SoccerPlayer mockPlayer = Mockito.mock(SoccerPlayer.class);
        when(mockPlayer.getTeam()).thenReturn(mfks);

        teamService.addPlayerToTeam(Mockito.mock(Team.class), mockPlayer);
    }

    @Test(expectedExceptions = SoccerManagerServiceException.class)
    public void addPlayerToFullTeamTest() {
        Team mockTeam = Mockito.mock(Team.class);
        ArrayList<SoccerPlayer> mockPlayers = new ArrayList<>();
        for (int i = 0; i < TeamServiceImpl.MAXIMUM_TEAM_SIZE + 1; i++) {
            mockPlayers.add(Mockito.mock(SoccerPlayer.class));
        }
        when(mockTeam.getPlayers()).thenReturn(mockPlayers);

        SoccerPlayer mockPlayer = Mockito.mock(SoccerPlayer.class);
        when(mockPlayer.getTeam()).thenReturn(null);

        teamService.addPlayerToTeam(mockTeam, mockPlayer);
    }

    @Test
    public void removePlayerFromTeamTest() {
        teamService.removePlayerFromTeam(fcb, messi);
        verify(fcb).removePlayer(messi);
        verify(teamDAO).update(fcb);
    }

    @Test
    public void changeBudgetByTest() {
        teamService.changeBudgetBy(fcb, BigDecimal.ONE);
        verify(fcb).setBudget(BigDecimal.valueOf(11));
        verify(teamDAO).update(fcb);
    }
}
