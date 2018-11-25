package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.*;
import cz.fi.muni.pa165.soccermanager.api.facade.MatchFacade;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dominik Pilar
 *
 */
public class TeamFacadeTest {
    private TeamFacade teamFacade;

    @Mock
    private TeamService teamService;

    @Mock
    private PlayerService playerService;

    private BeanMapping beanMapping;


    private Team slovan, senica;
    private SoccerPlayer player1, player2;
    private TeamDTO teamDTO;
    private TeamCreateDTO teamCreateDTO;
    private MatchCreateDTO matchCreateDTO;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        beanMapping = new BeanMappingImpl(new ModelMapper());
        teamFacade = new TeamFacadeImpl(teamService, playerService, beanMapping);
    }
    @BeforeMethod
    public void setup() {
        senica = new Team();
        senica.setId(1L);
        senica.setBudget(BigDecimal.valueOf(200000));
        senica.setChampionshipName("Fortuna liga");
        senica.setClubName("Senica");
        senica.setCountry("Slovakia");
        senica.setPlayers(new ArrayList<>());


        slovan = new Team();
        slovan.setId(2L);
        slovan.setBudget(BigDecimal.valueOf(500000));
        slovan.setChampionshipName("Fortuna liga");
        slovan.setClubName("Slovan");
        slovan.setCountry("Slovakia");
        slovan.setPlayers(new ArrayList<>());

        player1 = new SoccerPlayer();
        player1.setId(1L);
        player1.setRating(50);
        player1.setTeam(slovan);
        player2 = new SoccerPlayer();
        player2.setId(2L);
        player2.setRating(55);
        player2.setTeam(senica);

        slovan.addPlayer(player1);
        senica.addPlayer(player2);

        teamCreateDTO = new TeamCreateDTO();
        teamCreateDTO.setChampionshipName("Fortuna liga");
        teamCreateDTO.setClubName("Slovan");
        teamCreateDTO.setCountry("Slovakia");

    }

    @Test
    public void createTeam() {
        teamFacade.create(teamCreateDTO);
        Team plainTeam = new Team();
        plainTeam.setChampionshipName("Fortuna liga");
        plainTeam.setClubName("Slovan");
        plainTeam.setCountry("Slovakia");
        then(teamService).should().create(slovan);
    }

    @Test
    public void removeTeam() {
        given(teamService.findById(slovan.getId())).willReturn(slovan);
        teamFacade.remove(slovan.getId());
        then(teamService).should().remove(slovan);
    }

    @Test
    public void findTeamById() {
        given(teamService.findById(slovan.getId())).willReturn(slovan);
        TeamDTO t = teamFacade.findById(slovan.getId());
        teamEqualsWithDTO(slovan, t);
    }

    @Test
    public void getAllPlayersInTeam() {
        given(teamService.findById(slovan.getId())).willReturn(slovan);
        given(teamService.findAllPlayersInTeam(slovan)).willReturn(Arrays.asList(player1));
        List<PlayerDTO> players = teamFacade.getAllPlayersInTeam(slovan.getId());
        Assert.assertTrue(players.size() == 1);
        Assert.assertEquals(players.get(0).getId(), player1.getId());
        Assert.assertEquals(players.get(0).getPlayerName(), player1.getPlayerName());
        Assert.assertEquals(players.get(0).getBirthday(), player1.getBirthDate());
    }
    @Test
    public void removePlayerFromTeam() {
        given(teamService.findById(senica.getId())).willReturn(senica);
        given(playerService.findPlayerById(player2.getId())).willReturn(player2);
        teamFacade.removePlayerFromTeam(player2.getId(), senica.getId());
        then(teamService).should().removePlayerFromTeam(senica, player2);
    }

    @Test
    public void addPlayerToTeam() {
        given(teamService.findById(senica.getId())).willReturn(senica);
        given(playerService.findPlayerById(player1.getId())).willReturn(player1);
        teamFacade.addPlayerToTeam(player1.getId(), senica.getId());
        then(teamService).should().addPlayerToTeam(senica, player1);
    }

    @Test
    public void findAll() {
        given(teamService.findAll()).willReturn(Arrays.asList(slovan, senica));
        TeamListDTO teamListDTO = teamFacade.findAll();
        teamEqualsWithDTO(slovan, teamListDTO.getTeams().get(0));
        teamEqualsWithDTO(senica, teamListDTO.getTeams().get(1));
        Assert.assertTrue(teamListDTO.getCount() == 2);
    }

    @Test
    public void findByCountry() {
        given(teamService.findByCountry("Slovakia")).willReturn(Arrays.asList(slovan, senica));
        TeamListDTO teamListDTO = teamFacade.findByCountry("Slovakia");
        teamEqualsWithDTO(slovan, teamListDTO.getTeams().get(0));
        teamEqualsWithDTO(senica, teamListDTO.getTeams().get(1));
        Assert.assertTrue(teamListDTO.getCount() == 2);
    }

    private void teamEqualsWithDTO(Team t, TeamDTO tDTO) {
        Assert.assertEquals(t.getId(), tDTO.getId());
        Assert.assertEquals(t.getBudget(), tDTO.getBudget());
        Assert.assertEquals(t.getChampionshipName(), tDTO.getChampionshipName());
        Assert.assertEquals(t.getClubName(), tDTO.getClubName());
        Assert.assertEquals(t.getCountry(), tDTO.getCountry());

    }

}
