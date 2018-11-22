package cz.fi.muni.pa165.soccermanager.api.dto;

import java.math.BigDecimal;

public class TeamDTO {

    private Long id;
    private String clubName;
    private String championshipName;
    private String country;
    private BigDecimal budget;
    
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
}
