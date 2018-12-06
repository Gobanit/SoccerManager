package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.api.exceptions.ErrorStatus;
import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link MatchService}
 *
 * @author Lenka Horvathova
 */
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchDAO matchDAO;

    @Inject
    public MatchServiceImpl(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    @Override
    public Match create(Match match) {
    	if(match.getHomeTeam() == null) throw new IllegalArgumentException("Home team is null");
    	if(match.getAwayTeam() == null) throw new IllegalArgumentException("Away team is null");
    	if(match.getHomeTeam().equals(match.getAwayTeam())) 
    		throw new IllegalArgumentException("Home team and away team is the same!");
        
    	matchDAO.save(match);
        return match;
    }

    @Override
    public void remove(Match match) {
        Match matchToBeDeleted = this.findById(match.getId());
        matchDAO.delete(matchToBeDeleted);
    }

    @Override
    public Match findById(Long id) {
        Match foundMatch = matchDAO.findById(id);
        if (foundMatch == null) {
            throw new SoccerManagerServiceException("No match found with an ID: " + id, ErrorStatus.RESOURCE_NOT_FOUND);
        }
        return foundMatch;
    }

    @Override
    public List<Match> findAll() {
        return matchDAO.findAll();
    }

    @Override
    public List<Match> findByTeam(Team team) {
        List<Match> teamMatches = new ArrayList<>();

        for (Match match : this.findAll()) {
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                teamMatches.add(match);
            }
        }
        return teamMatches;
    }

    @Override
    public List<Match> findNotSimulatedMatches() {
        List<Match> awaitingMatches = new ArrayList<>();

        for (Match match : this.findAll()) {
            if (LocalDateTime.now().isAfter(match.getDate())
                    && match.getHomeTeamGoals() == null && match.getAwayTeamGoals() == null) {
                awaitingMatches.add(match);
            }
        }
        return awaitingMatches;
    }

    @Override
    public void simulateMatch(Match match) {
        if (match.getAwayTeamGoals() != null || match.getHomeTeamGoals() != null) {
            throw new SoccerManagerServiceException(
                    "A match with an ID \"" + match.getId() + "\" has already been simulated!",
                    ErrorStatus.MATCH_ALREADY_SIMULATED
            );
        }

        if (LocalDateTime.now().isBefore(match.getDate())) {
            throw new SoccerManagerServiceException(
                    "A match with an ID \"" + match.getId() + "\" has not taken place yet!",
                    ErrorStatus.MATCH_DATE_IN_THE_FUTURE
            );
        }

        int awayTeamChance = 0;
        int homeTeamChance = 0;

        for (SoccerPlayer player : match.getAwayTeam().getPlayers()) {
            awayTeamChance += player.getRating();
        }
        for (SoccerPlayer player : match.getHomeTeam().getPlayers()) {
            homeTeamChance += player.getRating();
        }

        awayTeamChance = ((awayTeamChance * 100) / (awayTeamChance + homeTeamChance));
        homeTeamChance = ((homeTeamChance * 100) / (awayTeamChance + homeTeamChance));

        int random = (int) (Math.random() * 100);

        if (awayTeamChance < homeTeamChance) {
            if (random < awayTeamChance) {
                int divider = randomWithRange(0, 10);

                match.setAwayTeamGoals(randomWithRange(divider, 0));
                match.setHomeTeamGoals(randomWithRange(0, divider));
            } else {
                int divider = randomWithRange(0, 10);

                match.setAwayTeamGoals(randomWithRange(0, divider));
                match.setHomeTeamGoals(randomWithRange(divider, 10));
            }
        } else {
            if (random < homeTeamChance) {
                int divider = randomWithRange(0, 10);

                match.setAwayTeamGoals(randomWithRange(0, divider));
                match.setHomeTeamGoals(randomWithRange(divider, 10));
            } else {
                int divider = randomWithRange(0, 10);

                match.setAwayTeamGoals(randomWithRange(divider, 0));
                match.setHomeTeamGoals(randomWithRange(0, divider));
            }
        }
    }

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public Team getWinner(Match match) {
        if (match.getAwayTeamGoals() == null || match.getHomeTeamGoals() == null) {
            throw new SoccerManagerServiceException(
                    "A match with an ID \"" + match.getId() + "\" has not been simulated yet!",
                    ErrorStatus.MATCH_NOT_SIMULATED
            );
        }

        if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
            return match.getHomeTeam();
        } else if (match.getHomeTeamGoals() < match.getAwayTeamGoals()) {
            return match.getAwayTeam();
        }
        return null;
    }

    @Override
    public int getVictoriesOfTeam(Team team) {
        int victories = 0;

        for (Match match : this.findByTeam(team)) {
            if (!(match.getAwayTeamGoals() == null || match.getHomeTeamGoals() == null)) {
                if (this.getWinner(match).equals(team)) {
                    victories += 1;
                }
            }
        }
        return victories;
    }
}
