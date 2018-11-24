package cz.fi.muni.pa165.soccermanager.api.dto;

import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Michal Mazourek
 */
public class PlayerCreateDTO {
    
    @NotNull
    private String playerName;
    
    @NotNull
    private String nationality;
    
    @NotNull
    private LocalDate birthday;
    
    @NotNull
    private Integer rating;
    
    @NotNull
    private Position position;
    
    @NotNull
    private Footed footed;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Footed getFooted() {
        return footed;
    }

    public void setFooted(Footed footed) {
        this.footed = footed;
    }
    
}
