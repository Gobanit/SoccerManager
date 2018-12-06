package cz.fi.muni.pa165.soccermanager.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO class for Team entity.
 *
 * @author Lenka Horvathova
 */
public class TeamDTO {

    private Long id;
    private String clubName;
    private String championshipName;
    private String country;
    private BigDecimal budget;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String clubName, String championshipName, String country, BigDecimal budget) {
        this.id = id;
        this.clubName = clubName;
        this.championshipName = championshipName;
        this.country = country;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        hash = 37 * hash + Objects.hashCode(getBudget());

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
        if (!(object instanceof TeamDTO)) {
            return false;
        }

        final TeamDTO other = (TeamDTO) object;

        if (!Objects.equals(getClubName(), other.getClubName())) {
            return false;
        }
        if (!Objects.equals(getChampionshipName(), other.getChampionshipName())) {
            return false;
        }
        if (!Objects.equals(getCountry(), other.getCountry())) {
            return false;
        }
        if (!Objects.equals(getBudget(), other.getBudget())) {
            return false;
        }

        return true;
    }
}
