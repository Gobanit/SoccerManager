package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.PlayerService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import java.util.List;

/**
 * Implementation of {@link TeamFacade}.
 *
 * @author Lenka Horvathova
 */
@Transactional
@Named
public class TeamFacadeImpl implements TeamFacade {

    private TeamService teamService;
    private PlayerService playerService;
    private BeanMapping beanMapping;

    @Inject
    public TeamFacadeImpl(TeamService teamService, PlayerService playerService, BeanMapping beanMapping) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.beanMapping = beanMapping;
    }

    @Override
    public void create(TeamCreateDTO teamCreateDTO) {
        teamService.create(beanMapping.mapTo(teamCreateDTO, Team.class));
    }

    @Override
    public void remove(Long id) {
        teamService.remove(teamService.findById(id));
    }

    @Override
    public List<TeamDTO> findAll() {
        List<Team> teams = teamService.findAll();
        return beanMapping.mapTo(teams, TeamDTO.class);
    }
    
    @Override
    public List<TeamDTO> findAllFree() {
        List<Team> teams = teamService.findAllFree();
        return beanMapping.mapTo(teams, TeamDTO.class);
    }
    
    @Override
    public TeamDTO findById(Long id) {
        return beanMapping.mapTo(teamService.findById(id), TeamDTO.class);
    }

    @Override
    public List<TeamDTO> findByCountry(String country) {
        List<Team> teams = teamService.findByCountry(country);
        return beanMapping.mapTo(teams, TeamDTO.class);
    }

    @Override
    public List<PlayerDTO> getAllPlayersInTeam(Long id) {
        List<SoccerPlayer> players = teamService.findAllPlayersInTeam(teamService.findById(id));
        return beanMapping.mapTo(players, PlayerDTO.class);
    }

    @Override
    public void addPlayerToTeam(Long playerId, Long teamId) {
        teamService.addPlayerToTeam(teamService.findById(teamId), playerService.findPlayerById(playerId));
    }

    @Override
    public void removePlayerFromTeam(Long playerId, Long teamId) {
        teamService.removePlayerFromTeam(teamService.findById(teamId), playerService.findPlayerById(playerId));
    }
}
