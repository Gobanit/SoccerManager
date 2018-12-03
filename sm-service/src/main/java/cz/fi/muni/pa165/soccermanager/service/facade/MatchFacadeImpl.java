package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchAwaitingDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.MatchFacade;
import cz.fi.muni.pa165.soccermanager.data.Match;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.MatchService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * implementation of facade layer for match
 * @author Dominik Pilar
 *
 */
@Service
@Transactional
public class MatchFacadeImpl implements MatchFacade {

    private MatchService matchService;
    private BeanMapping beanMapping;
    private TeamService teamService;


    @Autowired
    public MatchFacadeImpl(MatchService matchService, BeanMapping beanMapping, TeamService teamService) {
        this.matchService = matchService;
        this.beanMapping = beanMapping;
        this.teamService = teamService;
    }

    @Override
    public Long create(MatchCreateDTO match) {
        Match m = beanMapping.mapTo(match, Match.class);
        m.setHomeTeam(teamService.findById(match.getHomeTeam()));
        m.setAwayTeam(teamService.findById(match.getAwayTeam()));
        m = matchService.create(m);
        return m.getId();
    }


    @Override
    public void remove(Long matchId) {
        matchService.remove(matchService.findById(matchId));
    }

    @Override
    public MatchDTO findById(Long id) {
        return beanMapping.mapTo(matchService.findById(id), MatchDTO.class);
    }

    @Override
    public List<MatchDTO> findAll() {
        return beanMapping.mapTo(matchService.findAll(), MatchDTO.class);
    }

    @Override
    public List<MatchDTO> findByTeam(Long teamId) {
        return beanMapping.mapTo(matchService.findByTeam(teamService.findById(teamId)), MatchDTO.class);
    }

    @Override
    public List<MatchAwaitingDTO> findAwaitingMatches() {
        return beanMapping.mapTo(matchService.findNotSimulatedMatches(), MatchAwaitingDTO.class);
    }

    @Override
    public void simulateMatch(Long matchId) {
        matchService.simulateMatch(matchService.findById(matchId));

    }

    @Override
    public TeamDTO getWinner(Long matchId) {
        return beanMapping.mapTo(matchService.getWinner(matchService.findById(matchId)), TeamDTO.class);
    }

    @Override
    public int getVictoriesOfTeam(Long teamId) {
        return matchService.getVictoriesOfTeam(teamService.findById(teamId));
    }
}
