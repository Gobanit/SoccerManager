package cz.fi.muni.pa165.soccermanager.api.dto;

import java.util.Objects;

/**
 * DTO class for list of Team entities.
 *
 * @author Lenka Horvathova
 */
public class TeamDTO {

    private Long id;
    private String clubName;
    private String country;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String clubName, String country) {
        this.id = id;
        this.clubName = clubName;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getClubName() {
        return clubName;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(getId());
        hash = 37 * hash + Objects.hashCode(getClubName());
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
        if (!(object instanceof TeamDTO)) {
            return false;
        }

        final TeamDTO other = (TeamDTO) object;

        if (!Objects.equals(getId(), other.id)) {
            return false;
        }
        if (!Objects.equals(getClubName(), other.clubName)) {
            return false;
        }
        if (!Objects.equals(getCountry(), other.country)) {
            return false;
        }

        return true;
    }
}
