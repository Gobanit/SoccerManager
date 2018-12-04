package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.")
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(Throwable throwable) {
        super(throwable);
    }
}
