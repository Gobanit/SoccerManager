package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The requested resource was not found")
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -332543924845412307L;

	public ResourceNotFoundException(String s) {
        super(s);
    }

}
