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

	/**
	 * see {@link RuntimeException}
	 */
	public SoccerManagerServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * see {@link RuntimeException}
	 */
	public SoccerManagerServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * see {@link RuntimeException}
	 */
	public SoccerManagerServiceException(String message) {
		super(message);
	}

	/**
	 * see {@link RuntimeException}
	 */
	public SoccerManagerServiceException(Throwable cause) {
		super(cause);
	}

	
}
