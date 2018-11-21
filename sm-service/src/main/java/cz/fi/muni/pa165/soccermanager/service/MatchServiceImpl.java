package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private MatchDAO matchDAO;

    @Override
    public void create(Match match) {
        matchDAO.save(match);
    }

    @Override
    public void remove(Match match) {
        matchDAO.delete(match);
    }

    @Override
    public Match findById(Long id) {
        return matchDAO.findById(id);
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
    public List<Match> findAwaitingMatches() {
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

        for (Match match : this.findAll()) {
            if (this.getWinner(match).equals(team)) {
                victories += 1;
            }
        }
        return victories;
    }
}
