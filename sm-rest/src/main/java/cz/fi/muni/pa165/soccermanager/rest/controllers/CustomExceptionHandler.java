package cz.fi.muni.pa165.soccermanager.rest.controllers;

import cz.fi.muni.pa165.soccermanager.rest.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleProblem(Exception e) {

        ErrorResource error = new ErrorResource(e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus;
        if (e instanceof InternalServerErrorException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof ResourceNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ConflictException) {
            httpStatus = HttpStatus.CONFLICT;
        } else if (e instanceof MethodNotAllowedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof ResourceNotModifiedException) {
            httpStatus = HttpStatus.NOT_MODIFIED;
        } else if (e instanceof AccessDeniedException) {
        	httpStatus = HttpStatus.FORBIDDEN;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.debug("handleProblem({}(\"{}\")) httpStatus={}", e.getClass().getName(), e.getMessage(),httpStatus);
        return new ResponseEntity<>(error, httpStatus);
    }
}
