package cz.fi.muni.pa165.soccermanager.service;

import cz.fi.muni.pa165.soccermanager.dao.MatchDAO;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.data.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
