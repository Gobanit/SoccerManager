package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(Throwable throwable) {
        super(throwable);
    }
}
