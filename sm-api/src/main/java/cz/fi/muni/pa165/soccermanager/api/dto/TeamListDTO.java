package cz.fi.muni.pa165.soccermanager.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO class for Team entity.
 *
 * @author Lenka Horvathova
 */
public class TeamListDTO {

    private List<TeamDTO> teams = new ArrayList<>();
    private long count = 0L;

    public TeamListDTO() {
    }

    public TeamListDTO(List<TeamDTO> teams, long count) {
        this.teams = teams;
        this.count = count;
    }

    public List<TeamDTO> getTeams() {
        return teams;
    }

    public long getCount() {
        return count;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(getTeams());
        hash = 37 * hash + Objects.hashCode(getCount());

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
        if (!(object instanceof TeamListDTO)) {
            return false;
        }

        final TeamListDTO other = (TeamListDTO) object;

        if (!Objects.equals(getCount(), other.count)) {
            return false;
        }
        if (!Objects.equals(getTeams(), other.teams)) {
            return false;
        }

        return true;
    }

}
