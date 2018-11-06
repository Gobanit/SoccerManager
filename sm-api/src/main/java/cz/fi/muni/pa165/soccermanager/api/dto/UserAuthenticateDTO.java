/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.dto;

/**
 * Class used for user authentication.
 * 
 * @author Michal Randak
 *
 */
public class UserAuthenticateDTO {

	private String username;
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
