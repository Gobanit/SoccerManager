package cz.fi.muni.pa165.soccermanager.data;

import cz.fi.muni.pa165.soccermanager.data.enums.Footed;
import cz.fi.muni.pa165.soccermanager.data.enums.Position;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static javax.persistence.GenerationType.IDENTITY;
@Entity(name = "SoccerPlayer")
@Table(name = "soccer_player")
public class SoccerPlayer {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "player_name", nullable = false)
	private String playerName;

	@Column(name = "nationality", nullable = false)
	private String nationality;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "rating", nullable = false)
	@Max(100)
	@Min(1)
	private Integer rating;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "team_id")
	private Team team;

	@Column(name = "position", nullable = false)
	@Enumerated(EnumType.STRING)
	private Position position;

	@Column(name = "footed", nullable = false)
	@Enumerated(EnumType.STRING)
	private Footed footed;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SoccerPlayer))
			return false;

		SoccerPlayer that = (SoccerPlayer) o;

		if (!getPlayerName().equals(that.getPlayerName()))
			return false;
		if (!getNationality().equals(that.getNationality()))
			return false;
		if (!getAge().equals(that.getAge()))
			return false;
		return getRating().equals(that.getRating());
	}

	@Override public int hashCode() {
		int result = getPlayerName().hashCode();
		result = 31 * result + getNationality().hashCode();
		result = 31 * result + getAge().hashCode();
		result = 31 * result + getRating().hashCode();
		return result;
	}

	@Override public String toString() {
		return "SoccerPlayer{" + "playerName='" + playerName + '\'' + ", nationality='" + nationality + '\'' + ", age=" + age + ", rating="
				+ rating + '}';
	}
}