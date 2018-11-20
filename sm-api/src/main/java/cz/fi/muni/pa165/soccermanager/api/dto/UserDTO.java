/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.dto;

/**
 * Contains fields related to User entity
 * 
 * @author Michal Randak
 *
 */
public class UserDTO {

	private Long id;

	private String username;

	private String passwordHash;

	private boolean isAdmin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
