package cz.fi.muni.pa165.soccermanager.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
// TODO MiRa uncomment after Player entity exists
//	@OneToMany
//	@JoinColumn(name = "TEAM_ID")
//	private List<Player> players = new ArrayList<>();

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

// TODO MiRa uncomment after Player entity exists
//	public List<Player> getPlayers() {
//		return players;
//	}
//
//	public void setPlayers(List<Player> players) {
//		this.players = players;
//	}
	
	
}
