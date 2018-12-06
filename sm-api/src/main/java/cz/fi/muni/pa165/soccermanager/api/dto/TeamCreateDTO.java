package cz.fi.muni.pa165.soccermanager.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO class for a creation of Team entity.
 *
 * @author Lenka Horvathova
 */
public class TeamCreateDTO {

    @NotNull
    private String clubName;

    @NotNull
    private String championshipName;

    @NotNull
    private String country;

    @NotNull
    @Min(1)
    private BigDecimal budget;

    public TeamCreateDTO() {
    }

    public TeamCreateDTO(String clubName, String championshipName, String country) {
        this.clubName = clubName;
        this.championshipName = championshipName;
        this.country = country;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(getClubName());
        hash = 37 * hash + Objects.hashCode(getChampionshipName());
        hash = 37 * hash + Objects.hashCode(getCountry());

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof TeamCreateDTO)) {
            return false;
        }

        final TeamCreateDTO other = (TeamCreateDTO) object;

        if (!Objects.equals(getClubName(), other.getClubName())) {
            return false;
        }
        if (!Objects.equals(getChampionshipName(), other.getChampionshipName())) {
            return false;
        }
        if (!Objects.equals(getCountry(), other.getCountry())) {
            return false;
        }

        return true;
    }
}
