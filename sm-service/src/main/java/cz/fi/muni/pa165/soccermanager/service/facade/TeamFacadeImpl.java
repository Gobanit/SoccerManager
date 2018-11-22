package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamListDTO;
import cz.fi.muni.pa165.soccermanager.api.facade.TeamFacade;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.data.Team;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.TeamService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TeamFacadeImpl implements TeamFacade {

    private TeamService teamService;
    private BeanMapping beanMapping;

    @Inject
    public TeamFacadeImpl(TeamService teamService, BeanMapping beanMapping) {
        this.teamService = teamService;
        this.beanMapping = beanMapping;
    }

    @Override
    public void create(TeamCreateDTO teamCreateDTO) {
        teamService.create(beanMapping.mapTo(teamCreateDTO, Team.class));
    }

    @Override
    public void remove(TeamDTO teamDTO) {
        teamService.remove(beanMapping.mapTo(teamDTO, Team.class));
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
        return null;
    }

    @Override
    public List<PlayerDTO> getAllPlayersInTeam(TeamDTO teamDTO) {
        List<SoccerPlayer> players = teamService.findAllPlayersInTeam(beanMapping.mapTo(teamDTO, Team.class));
        return beanMapping.mapTo(players, PlayerDTO.class);
    }

    @Override
    public void addPlayerToTeam(PlayerDTO playerDTO, TeamDTO teamDTO) {
        teamService.addPlayerToTeam(beanMapping.mapTo(teamDTO, Team.class), beanMapping.mapTo(playerDTO, SoccerPlayer.class));
    }

    @Override
    public void removePlayerFromTeam(PlayerDTO playerDTO, TeamDTO teamDTO) {
        teamService.removePlayerFromTeam(beanMapping.mapTo(teamDTO, Team.class), beanMapping.mapTo(playerDTO, SoccerPlayer.class));
    }
}
