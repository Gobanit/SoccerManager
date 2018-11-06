package cz.fi.muni.pa165.soccermanager.data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Domain Entity for representing soccer team
 * 
 * @author Michal Randak
 *
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"club_name", "championship_name", "country"}))
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "club_name", nullable = false, updatable = false)
	private String clubName;
	
	@Column(nullable = false, updatable = false)
	private String championshipName;
	
	@Column(nullable = false, updatable = false)
	private String country;
	
	@Column
	private BigDecimal budget;

	@OneToMany
	@JoinColumn(name = "team_id")
	private List<SoccerPlayer> players = new ArrayList<>();

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

	public List<SoccerPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public void setPlayers(List<SoccerPlayer> players) {
		this.players = players;
	}
	
	public void addPlayer(SoccerPlayer p) {
		getPlayers().add(p);
		p.setTeam(this);
	}
	
	public void removePlayer(SoccerPlayer p) {
		getPlayers().remove(p);
		p.setTeam(null);
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getChampionshipName() == null) ? 0 : getChampionshipName().hashCode());
		result = prime * result + ((getClubName() == null) ? 0 : getClubName().hashCode());
		result = prime * result + ((getCountry() == null) ? 0 : getCountry().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Team)) {
			return false;
		}
		Team other = (Team) obj;
		if (getChampionshipName() == null) {
			if (other.getChampionshipName() != null) {
				return false;
			}
		} else if (!getChampionshipName().equals(other.getChampionshipName())) {
			return false;
		}
		if (getClubName() == null) {
			if (other.getClubName() != null) {
				return false;
			}
		} else if (!getClubName().equals(other.getClubName())) {
			return false;
		}
		if (getCountry() == null) {
			if (other.getCountry() != null) {
				return false;
			}
		} else if (!getCountry().equals(other.getCountry())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Team [id=" + getId() + ", clubName=" + getClubName() + ", championshipName=" + getChampionshipName() + ", country="
				+ getCountry() + ", players=" + getPlayers() + "budget=" + budget + "]";
	}
	
	
	
}
