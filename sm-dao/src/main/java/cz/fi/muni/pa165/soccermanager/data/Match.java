package cz.fi.muni.pa165.soccermanager.data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Domain Entity for representing match
 * 
 * @author Michal Mazourek
 *
 */

@Entity
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column
	private Timestamp date;
	
	@Column
	private Integer homeTeamGoals;
	
	@Column
	private Integer awayTeamGoals;
	
	@ManyToOne
	@JoinColumn(name = "homeTeam_id")
	private Team homeTeam;
	
	@ManyToOne
	@JoinColumn(name = "awayTeam_id")
	private Team awayTeam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Integer getHomeTeamGoals() {
		return homeTeamGoals;
	}

	public void setHomeTeamGoals(Integer homeTeamGoals) {
		this.homeTeamGoals = homeTeamGoals;
	}

	public Integer getAwayTeamGoals() {
		return awayTeamGoals;
	}

	public void setAwayTeamGoals(Integer awayTeamGoals) {
		this.awayTeamGoals = awayTeamGoals;
	}
	
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((awayTeam == null) ? 0 : awayTeam.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((homeTeam == null) ? 0 : homeTeam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Match other = (Match) obj;
		
		if (getAwayTeam() == null) {
			if (other.getAwayTeam() != null)
				return false;
		} else if (!getAwayTeam().equals(other.getAwayTeam()))
			return false;
		if (getDate() == null) {
			if (other.getDate() != null)
				return false;
		} else if (!getDate().equals(other.getDate()))
			return false;
		if (getHomeTeam() == null) {
			if (other.getHomeTeam() != null)
				return false;
		} else if (!getHomeTeam().equals(other.getHomeTeam()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", date=" + date + ", homeTeamGoals=" + homeTeamGoals + ", awayTeamGoals="
				+ awayTeamGoals + "]";
	}
}
