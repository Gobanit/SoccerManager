package cz.fi.muni.pa165.soccermanager.rest.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.data.User;
import cz.fi.muni.pa165.soccermanager.service.MatchService;
import cz.fi.muni.pa165.soccermanager.service.PlayerService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;
import cz.fi.muni.pa165.soccermanager.service.UserService;

/**
 * Implementation of {@link SampleDataFacade}
 * @author Michal Randak
 *
 */
@Transactional
@Named
public class SampleDataFacadeImpl implements SampleDataFacade {

	private PlayerService playerService;
	private TeamService teamService;
	private MatchService matchService;
	private UserService userService;
	
	
	@Inject
	public SampleDataFacadeImpl(PlayerService playerService, TeamService teamService, MatchService matchService,
			UserService userService) {
		this.playerService = playerService;
		this.teamService = teamService;
		this.matchService = matchService;
		this.userService = userService;
	}

	@Override
	public void initData() {
		// Users
		User admin = createUser("admin", "pass", true);				
		
		// Players
		SoccerPlayer henry = createPlayer("Thierry Henry", "France", LocalDate.of(1977,  8,  17),Position.OFFENSE, Footed.RIGHT, 9);
		SoccerPlayer hleb = createPlayer("Alexander Hleb", "Belarus", LocalDate.of(1981, 5, 1), Position.MIDFIELD, Footed.RIGHT, 8);
		SoccerPlayer ronald = createPlayer("Ronaldinho", "Brazil", LocalDate.of(1980, 3, 21), Position.MIDFIELD, Footed.BOTH, 10);
		
		// Teams
		Team arsenal = createTeam("Arsenal", "England", "Premier League", "1000000", Arrays.asList(henry, hleb));
		Team barcelona = createTeam("Barcelona", "Spain", "Primera División", "2000000", Arrays.asList(ronald));
		Team liverpool = createTeam("Liverpool", "England", "Premier League", "1100000", Arrays.asList());

		// Matches
		Match match0 = createMatch(liverpool, arsenal, LocalDateTime.of(2018, 6, 2, 17, 30, 00), null, null);
		Match match1 = createMatch(arsenal, barcelona, LocalDateTime.now().plusDays(4), null, null);
		Match match2 = createMatch(barcelona, arsenal, LocalDateTime.now().plusDays(8), null, null);
	}

	private User createUser(String name, String pass, boolean admin) {
		User u = new User();
		u.setUserName(name);
		u.setAdmin(admin);
		userService.registerNewUser(u, pass);
		return u;
	}

	private Match createMatch(Team home, Team away, LocalDateTime date, Integer homeGoals, Integer awayGoals) {
		Match m = new Match();
		m.setHomeTeam(home);
		m.setAwayTeam(away);
		m.setDate(date);
		m.setHomeTeamGoals(homeGoals);
		m.setAwayTeamGoals(awayGoals);
		matchService.create(m);
		return m;
	}

	private Team createTeam(String name, String country, String league, String budget, List<SoccerPlayer> players) {
		Team t = new Team();
		t.setClubName(name);
		t.setCountry(country);
		t.setChampionshipName(league);
		t.setBudget(new BigDecimal(budget));
		t.setPlayers(players);
		teamService.create(t);
		return t;
	}

	private SoccerPlayer createPlayer(String name, String nation, LocalDate birthDate, Position pos, Footed foot, int rate) {
		SoccerPlayer p = new SoccerPlayer();
		p.setPlayerName(name);
		p.setNationality(nation);
		p.setBirthDate(birthDate);
		p.setPosition(pos);
		p.setFooted(foot);
		p.setRating(rate);
		playerService.createPlayer(p);
		return p;
	}

}
