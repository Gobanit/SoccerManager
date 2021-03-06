package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Request is conflicting with specification.")
public class ConflictException extends RuntimeException {
	private static final long serialVersionUID = -2416064508383683762L;

	public ConflictException(String message) {
        super(message);
    }
}
