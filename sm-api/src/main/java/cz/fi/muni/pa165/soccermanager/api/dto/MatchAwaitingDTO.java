package cz.fi.muni.pa165.soccermanager.api.dto;

import java.time.Instant;
/**
 * @author Dominik Pilar
 *
 */
public class MatchAwaitingDTO {

    private Long id;
    private Instant date;
    private TeamDTO homeTeam;
    private TeamDTO awayTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamDTO awayTeam) {
        this.awayTeam = awayTeam;
    }
}
