/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.dto;

import javax.validation.constraints.NotNull;

/**
 * Class used for user authentication.
 * 
 * @author Michal Randak
 *
 */
public class UserAuthenticateDTO {

	@NotNull
	private String username;
	
	@NotNull
	private String rawPassword;

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

}
