package cz.fi.muni.pa165.soccermanager.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidParameterException extends RuntimeException {
	private static final long serialVersionUID = 6223262365208901422L;

	public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String s) {
        super(s);
    }

    public InvalidParameterException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidParameterException(Throwable throwable) {
        super(throwable);
    }
}
