package cz.fi.muni.pa165.soccermanager.rest.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.soccermanager.api.dto.TeamDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;

@Relation(value = "team", collectionRelation = "teams")
@JsonPropertyOrder({"id", "clubName", "championshipName", "country", "budget"})
public class TeamResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport already has getId() method
    private long dtoId;
    private String clubName;
    private String championshipName;
    private String country;
    private BigDecimal budget;

    public TeamResource(TeamDTO dto) {
        this.dtoId = dto.getId();
        this.clubName = dto.getClubName();
        this.championshipName = dto.getChampionshipName();
        this.country = dto.getCountry();
        this.budget = dto.getBudget();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getChampionshipName() {
        return championshipName;
    }

    public void setChampionshipName(String championshipName) {
        this.championshipName = championshipName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
