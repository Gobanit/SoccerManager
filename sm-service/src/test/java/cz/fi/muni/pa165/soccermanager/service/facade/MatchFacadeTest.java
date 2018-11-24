/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchAwaitingDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.MatchFacade;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.MatchService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;

/**
 * Unit test for {@link MatchFacadeImpl}.
 * @author Michal Randak
 *
 */
public class MatchFacadeTest {
	@Mock
	private MatchService matchService;
	@Mock
	private TeamService teamService;
	@Mock
	private BeanMapping beanMapping;
	
	private MatchFacade matchFacade;
	
	private Team arsenal;
	private Team barcelona;
	private Team slovan;
	
	private Match fabricatedMatch;
	private Match fabricatedMatch2;
	private Match fabricatedMatch3;

	
	@BeforeMethod
	public void init() {
        MockitoAnnotations.initMocks(this);
        
        matchFacade = new MatchFacadeImpl(matchService, beanMapping, teamService);
        
		arsenal = team(1l, "Arsenal", "Premier League", "England", "10000");
		barcelona = team(2l, "Barcelona", "Spain League", "Spain", "20000");
		slovan = team(3l, "Slovan", "Corgon Liga", "Slovakia", "10");
	
		fabricatedMatch = matchFuture(1l, arsenal, barcelona, 5);
		fabricatedMatch2 = matchPast(2l, slovan, arsenal, 250, 1, 5);
		fabricatedMatch3 = matchFuture(1l, arsenal, slovan, 10);

	}


	private Team team(Long id, String name, String league, String country, String budget) {
		Team t = new Team();
		t.setId(id);
		t.setClubName(name);
		t.setChampionshipName(league);
		t.setCountry(country);
		t.setBudget(new BigDecimal(budget));
		
		return t;
	}
	
	private Match matchFuture(Long id, Team home, Team away, int dayOffset) {
		Match match = new Match();
		match.setId(id);
		match.setHomeTeam(home);
		match.setAwayTeam(away);
		match.setDate(LocalDateTime.now().plusDays(dayOffset));
		
		return match;
	}
	
	private Match matchPast(Long id, Team home, Team away, int dayOffset, int homeGoals, int awayGoals) {
		Match match = new Match();
		match.setId(id);
		match.setHomeTeam(home);
		match.setAwayTeam(away);
		match.setDate(LocalDateTime.now().minusDays(dayOffset));
		match.setHomeTeamGoals(homeGoals);
		match.setAwayTeamGoals(awayGoals);
		
		return match;
	}

	

	@Test
	public void testCreate() {
		MatchCreateDTO createDTO = new MatchCreateDTO();
		createDTO.setDate(LocalDateTime.of(2011, 10, 13, 18, 30));
		createDTO.setHomeTeam(arsenal.getId());
		createDTO.setAwayTeam(barcelona.getId());

		Match match = new Match();
		match.setDate(createDTO.getDate());
		
		Mockito.when(teamService.findById(arsenal.getId())).thenReturn(arsenal);
		Mockito.when(teamService.findById(barcelona.getId())).thenReturn(barcelona);
		Mockito.when(beanMapping.mapTo(createDTO, Match.class)).thenReturn(match);

		matchFacade.create(createDTO);
		Mockito.verify(matchService).create(Mockito.argThat(new MatchArgumentChecker(arsenal, barcelona, createDTO.getDate())));		
	}
	
	@Test
	public void testFindAll() {
		List<Match> matches = Arrays.asList(fabricatedMatch, fabricatedMatch2, fabricatedMatch3);
		
		List<MatchDTO> list = new ArrayList<>();
		Mockito.when(matchService.findAll()).thenReturn(matches);
		Mockito.when(beanMapping.mapTo(matches, MatchDTO.class)).thenReturn(list);
		
		List<MatchDTO> found = matchFacade.findAll();
		Assert.assertTrue(list == found);
	}
	
	@Test
	public void testFindById() {
		Mockito.when(matchService.findById(fabricatedMatch.getId())).thenReturn(fabricatedMatch);
		MatchDTO dto = new MatchDTO();
		
		Mockito.when(beanMapping.mapTo(fabricatedMatch, MatchDTO.class)).thenReturn(dto);
		
		MatchDTO found = matchFacade.findById(fabricatedMatch.getId());
		Assert.assertTrue(found == dto);		
	}
	
	
	@Test
	public void testAwaitingMatches() {
		List<Match> matches = Arrays.asList(fabricatedMatch, fabricatedMatch2, fabricatedMatch3);
		
		List<MatchAwaitingDTO> list = new ArrayList<>();
		Mockito.when(matchService.findNotSimulatedMatches()).thenReturn(matches);
		Mockito.when(beanMapping.mapTo(matches, MatchAwaitingDTO.class)).thenReturn(list);
		
		List<MatchAwaitingDTO> found = matchFacade.findAwaitingMatches();
		Assert.assertTrue(list == found);
	}
	
	@Test
	public void testSimulateMatch() {
		Mockito.when(matchService.findById(fabricatedMatch.getId())).thenReturn(fabricatedMatch);
		matchFacade.simulateMatch(fabricatedMatch.getId());
		Mockito.verify(matchService).simulateMatch(fabricatedMatch);
	}
	
	@Test
	public void testFindByTeam() {
		List<Match> matches = Arrays.asList(fabricatedMatch, fabricatedMatch2);
		Mockito.when(teamService.findById(arsenal.getId())).thenReturn(arsenal);
		Mockito.when(matchService.findByTeam(arsenal)).thenReturn(matches);
		List<MatchDTO> list = new ArrayList<>();
		Mockito.when(beanMapping.mapTo(matches, MatchDTO.class)).thenReturn(list);
		List<MatchDTO> found = matchFacade.findByTeam(arsenal.getId());
		Assert.assertTrue(found == list);
	}
	
	
}

class MatchArgumentChecker implements ArgumentMatcher<Match> {
	private Team home;
	private Team away;
	private LocalDateTime date;
	
	public MatchArgumentChecker(Team home, Team away, LocalDateTime date) {
		this.home = home;
		this.away = away;
		this.date = date;
	}
	
	@Override
	public boolean matches(Match match) {
		if(match.getHomeTeam() != home && 
				!match.getHomeTeam().equals(home)) return false;
		if(match.getAwayTeam() != away && 
				!match.getAwayTeam().equals(away)) return false;
		if(match.getDate() != date && 
				!match.getDate().equals(date)) return false;
		return true;
	}
}
