package cz.fi.muni.pa165.soccermanager.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain Entity for representing soccer team
 * 
 * @author Michal Randak
 *
 */
@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column
	private String clubName;
	
	@Column
	private String championshipName;
	
	@Column
	private String country;

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
		return players;
	}

	public void setPlayers(List<SoccerPlayer> players) {
		this.players = players;
	}
	
	
}
