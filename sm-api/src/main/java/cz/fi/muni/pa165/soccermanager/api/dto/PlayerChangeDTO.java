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
    
}
