package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED,
        reason = "A request method is not supported for the requested resource; for example, a GET request on a form that requires data to be presented via POST, or a PUT request on a read-only resource.")
public class MethodNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = -8117884553620241134L;

	public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException(String message, Throwable ex) {
        super(message, ex);
    }

    public MethodNotAllowedException(Throwable ex) {
        super(ex);
    }

}
