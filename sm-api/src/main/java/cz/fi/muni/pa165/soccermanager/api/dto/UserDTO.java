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

	private String hashedPassword;

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

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
