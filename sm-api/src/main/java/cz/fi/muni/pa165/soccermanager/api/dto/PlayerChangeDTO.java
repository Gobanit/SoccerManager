package cz.fi.muni.pa165.soccermanager.api.dto;

import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;
import javax.validation.constraints.NotNull;

/**
 * DTO class for changing specific attributes
 *
 * @author Michal Mazourek
 */
public class PlayerChangeDTO {
    
    @NotNull
    private Long id;
    
    @NotNull
    private Position position;
    
    @NotNull
    private Footed footed;
    
    @NotNull
    private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    
    
}
