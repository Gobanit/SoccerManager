package cz.fi.muni.pa165.soccermanager.api.dto;

import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;

import java.time.LocalDate;
import java.util.Objects;

public class PlayerDTO {
    private Long id;
    private String playerName;
    private String nationality;
    private LocalDate birthday;
    private Integer rating;
    private TeamDTO teamDTO;
    private Position position;
    private Footed footed;

    public PlayerDTO() {
    }

    public PlayerDTO(Long id, String playerName, String nationality, LocalDate birthday, Integer rating, TeamDTO teamDTO, Position position, Footed footed) {
        this.id = id;
        this.playerName = playerName;
        this.nationality = nationality;
        this.birthday = birthday;
        this.rating = rating;
        this.teamDTO = teamDTO;
        this.position = position;
        this.footed = footed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TeamDTO getTeamDTO() {
        return teamDTO;
    }

    public void setTeamDTO(TeamDTO teamDTO) {
        this.teamDTO = teamDTO;
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


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(getPlayerName());
        hash = 37 * hash + Objects.hashCode(getNationality());
        hash = 37 * hash + Objects.hashCode(getBirthday());

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
        if (!(object instanceof PlayerDTO)) {
            return false;
        }

        final PlayerDTO other = (PlayerDTO) object;

        if (!Objects.equals(getPlayerName(), other.getPlayerName())) {
            return false;
        }
        if (!Objects.equals(getNationality(), other.getNationality())) {
            return false;
        }
        if (!Objects.equals(getBirthday(), other.getBirthday())) {
            return false;
        }

        return true;
    }
}