/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchAwaitingDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * @author Michal Randak
 *
 */
public class BeanMappingTest {

	private ModelMapper mapper;
	private BeanMapping beanMapping;
	private Team slovan, senica;
	private SoccerPlayer player1, player2;

	@BeforeTest
	public void init() {
		mapper = new ModelMapper();
		
		beanMapping = new BeanMappingImpl(mapper);

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
	}
	
	
	@Test
	public void testUserCreateMapping() {
		UserCreateDTO dto = new UserCreateDTO();
		dto.setUsername("someuser");
		dto.setRawPassword("pass");
		dto.setAdmin(false);
		
		
		User user = beanMapping.mapTo(dto, User.class);
		
		Assert.assertEquals(user.getUserName(), dto.getUsername());
		Assert.assertEquals(user.isAdmin(), dto.isAdmin());
	}
	
	@Test
	public void testUserMapping() {
		User user = new User();
		user.setId(10l);
		user.setUserName("SomeSuperUser");
		user.setPasswordHash("A12B34");
		user.setAdmin(false);
		user.setTeam(null);		
		
		UserDTO userDTO = beanMapping.mapTo(user, UserDTO.class);
		
		Assert.assertEquals(userDTO.getId(), user.getId());
		Assert.assertEquals(userDTO.getUsername(), user.getUserName());
		Assert.assertEquals(userDTO.getPasswordHash(), user.getPasswordHash());
		Assert.assertEquals(userDTO.isAdmin(), user.isAdmin());	
	}

	@Test
	public void testMatchMapping() {
		Match match = new Match();
		match.setDate(Instant.now());
		match.setAwayTeam(senica);
		match.setHomeTeam(slovan);
		match.setHomeTeamGoals(1);
		match.setAwayTeamGoals(3);
		match.setId(1L);

		MatchDTO matchDTO = beanMapping.mapTo(match, MatchDTO.class);

		assertEqualsMatchAndMatchDTO(match, matchDTO);
	}

	@Test
	public void testMatchAwaitingMapping() {
		Match match = new Match();
		match.setDate(Instant.now());
		match.setAwayTeam(senica);
		match.setHomeTeam(slovan);
		match.setHomeTeamGoals(1);
		match.setAwayTeamGoals(3);
		match.setId(1L);
		MatchAwaitingDTO matchAwaitingDTO = beanMapping.mapTo(match, MatchAwaitingDTO.class);

		assertEqualsMatchAndMatchAwaitingDTO(match, matchAwaitingDTO);
	}

	@Test
	public void testMatchCreateMapping() {
		MatchCreateDTO matchCreateDTO = new MatchCreateDTO();
		matchCreateDTO.setAwayTeam(1L);
		matchCreateDTO.setHomeTeam(2L);
		matchCreateDTO.setDate(Instant.now());

		Match match = beanMapping.mapTo(matchCreateDTO, Match.class);

		Assert.assertEquals(match.getDate(), matchCreateDTO.getDate());
		Assert.assertNotNull(matchCreateDTO.getAwayTeam());
		Assert.assertNotNull(matchCreateDTO.getHomeTeam());
	}

	private void assertEqualsMatchAndMatchAwaitingDTO(Match match, MatchAwaitingDTO matchDTO) {
		Assert.assertEquals(match.getId(), matchDTO.getId());
		Assert.assertEquals(match.getDate(), matchDTO.getDate());
		assertEqualsTeamAndTeamDTO(match.getAwayTeam(), matchDTO.getAwayTeam());
		assertEqualsTeamAndTeamDTO(match.getHomeTeam(), matchDTO.getHomeTeam());
	}

	private void assertEqualsMatchAndMatchDTO(Match match, MatchDTO matchDTO) {
		Assert.assertEquals(match.getId(), matchDTO.getId());
		Assert.assertEquals(match.getDate(), matchDTO.getDate());
		Assert.assertEquals(match.getHomeTeamGoals(), matchDTO.getHomeTeamGoals());
		Assert.assertEquals(match.getAwayTeamGoals(), matchDTO.getAwayTeamGoals());
		assertEqualsTeamAndTeamDTO(match.getAwayTeam(), matchDTO.getAwayTeam());
		assertEqualsTeamAndTeamDTO(match.getHomeTeam(), matchDTO.getHomeTeam());
	}

	private void assertEqualsTeamAndTeamDTO(Team team, TeamDTO teamDTO) {
		Assert.assertEquals(team.getId(), teamDTO.getId());
		Assert.assertEquals(team.getChampionshipName(), teamDTO.getChampionshipName());
		Assert.assertEquals(team.getBudget(), teamDTO.getBudget());
		Assert.assertEquals(team.getClubName(), teamDTO.getClubName());
		Assert.assertEquals(team.getCountry(), teamDTO.getCountry());

	}

}
