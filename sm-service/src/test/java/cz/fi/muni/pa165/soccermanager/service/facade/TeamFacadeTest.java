package cz.fi.muni.pa165.soccermanager.service.facade;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.BeanMappingImpl;
import cz.fi.muni.pa165.soccermanager.service.PlayerService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;

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
    private TeamCreateDTO teamCreateDTO;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        beanMapping = new BeanMappingImpl(new ModelMapper());
        teamFacade = new TeamFacadeImpl(teamService, playerService, beanMapping);
    }
    @BeforeMethod
    public void setup() {
        senica = getTeam(1L, "Slovakia", "Senica", "Fortuna liga", BigDecimal.valueOf(200000));
        slovan = getTeam(2L, "Slovakia", "Slovan", "Fortuna liga", BigDecimal.valueOf(500000));

        player1 = getPlayer(1L, 50, slovan);
        player2 = getPlayer(2L, 55, senica);
        slovan.addPlayer(player1);
        senica.addPlayer(player2);

        teamCreateDTO = new TeamCreateDTO();
        teamCreateDTO.setChampionshipName("Fortuna liga");
        teamCreateDTO.setClubName("Slovan");
        teamCreateDTO.setCountry("Slovakia");

    }

    @Test
    public void createTeam() {
        Team plainTeam = new Team();
        plainTeam.setChampionshipName("Fortuna liga");
        plainTeam.setClubName("Slovan");
        plainTeam.setCountry("Slovakia");
        teamFacade.create(teamCreateDTO);
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
        assertEqualsTeamWithTeamDTO(slovan, t);
    }

    @Test
    public void getAllPlayersInTeam() {
        given(teamService.findById(slovan.getId())).willReturn(slovan);
        given(teamService.findAllPlayersInTeam(slovan)).willReturn(Arrays.asList(player1));
        List<PlayerDTO> players = teamFacade.getAllPlayersInTeam(slovan.getId());
        Assert.assertTrue(players.size() == 1);
        Assert.assertEquals(players.get(0).getId(), player1.getId());
        Assert.assertEquals(players.get(0).getPlayerName(), player1.getPlayerName());
        Assert.assertEquals(players.get(0).getBirthDate(), player1.getBirthDate());
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
        List<TeamDTO> teamListDTO = teamFacade.findAll();
        assertEqualsTeamWithTeamDTO(slovan, teamListDTO.get(0));
        assertEqualsTeamWithTeamDTO(senica, teamListDTO.get(1));
        Assert.assertTrue(teamListDTO.size() == 2);
    }

    @Test
    public void findByCountry() {
        given(teamService.findByCountry("Slovakia")).willReturn(Arrays.asList(slovan, senica));
        List<TeamDTO> teamListDTO = teamFacade.findByCountry("Slovakia");
        assertEqualsTeamWithTeamDTO(slovan, teamListDTO.get(0));
        assertEqualsTeamWithTeamDTO(senica, teamListDTO.get(1));
        Assert.assertTrue(teamListDTO.size() == 2);
    }

    private void assertEqualsTeamWithTeamDTO(Team t, TeamDTO tDTO) {
        Assert.assertEquals(t.getId(), tDTO.getId());
        Assert.assertEquals(t.getBudget(), tDTO.getBudget());
        Assert.assertEquals(t.getChampionshipName(), tDTO.getChampionshipName());
        Assert.assertEquals(t.getClubName(), tDTO.getClubName());
        Assert.assertEquals(t.getCountry(), tDTO.getCountry());

    }


    private Team getTeam(Long id, String country, String clubName, String championShip, BigDecimal budget) {
        Team t = new Team();
        t.setId(id);
        t.setCountry(country);
        t.setClubName(clubName);
        t.setChampionshipName(championShip);
        t.setBudget(budget);
        t.setPlayers(new ArrayList<>());
        return t;
    }

    private SoccerPlayer getPlayer(Long id, Integer rating, Team team) {
        SoccerPlayer p = new SoccerPlayer();
        p.setId(id);
        p.setRating(rating);
        p.setTeam(team);
        return p;
    }

}
