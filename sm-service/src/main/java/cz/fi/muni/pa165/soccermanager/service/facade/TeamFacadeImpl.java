package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamListDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.PlayerService;
import cz.fi.muni.pa165.soccermanager.service.TeamService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link TeamFacade}.
 *
 * @author Lenka Horvathova
 */
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
    public TeamListDTO findAll() {
        List<Team> stores = teamService.findAll();
        List<TeamDTO> out = new ArrayList<>();
        stores.forEach(team -> out.add(beanMapping.mapTo(team, TeamDTO.class)));
        return new TeamListDTO(out, out.size());
    }

    @Override
    public TeamDTO findById(Long id) {
        return beanMapping.mapTo(teamService.findById(id), TeamDTO.class);
    }

    @Override
    public TeamListDTO findByCountry(String country) {
        List<Team> stores = teamService.findByCountry(country);
        List<TeamDTO> out = new ArrayList<>();
        stores.forEach(team -> out.add(beanMapping.mapTo(team, TeamDTO.class)));
        return new TeamListDTO(out, out.size());
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
