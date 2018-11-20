/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.api.exceptions;

/**
 * Exception might thrown in some cases on service layer
 * @author Michal Randak
 *
 */
public class SoccerManagerServiceException extends RuntimeException {

	private static final long serialVersionUID = 8297057445621104136L;

	private final ErrorStatus status;

	public SoccerManagerServiceException(ErrorStatus status) {
		this.status = status;
	}

	public SoccerManagerServiceException(String message, ErrorStatus status) {
		super(message);
		this.status = status;
	}

	public SoccerManagerServiceException(String message, Throwable ex, ErrorStatus status) {
		super(message, ex);
		this.status = status;
	}

	public SoccerManagerServiceException(Throwable ex, ErrorStatus status) {
		super(ex);
		this.status = status;
	}

	public ErrorStatus getCode() {
		return status;
	}

	
}
