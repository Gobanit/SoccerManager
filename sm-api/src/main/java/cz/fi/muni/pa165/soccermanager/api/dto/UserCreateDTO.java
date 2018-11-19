/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.dto;

import javax.validation.constraints.NotNull;

/**
 * Class serves to group fields needed for creating new user.
 * 
 * @author Michal Randak
 *
 */
public class UserCreateDTO {

	@NotNull
	private String username;

	@NotNull
	private String rawPassword;

	@NotNull
	private boolean isAdmin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
