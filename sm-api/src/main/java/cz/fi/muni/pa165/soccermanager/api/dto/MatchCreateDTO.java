package cz.fi.muni.pa165.soccermanager.api.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
/**
 * @author Dominik Pilar
 *
 */
public class MatchCreateDTO {
    @NotNull
    @Future
    private LocalDateTime date;
    @NotNull
    private Long homeTeam;
    @NotNull
    private Long awayTeam;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Long awayTeam) {
        this.awayTeam = awayTeam;
    }
}
