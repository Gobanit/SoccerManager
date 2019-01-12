package cz.fi.muni.pa165.soccermanager.rest;

import cz.fi.muni.pa165.soccermanager.api.exceptions.SoccerManagerServiceException;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.ConflictException;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.InternalServerErrorException;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.MethodNotAllowedException;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.soccermanager.rest.exceptions.ResourceNotModifiedException;


public class ExceptionSorter {
    private ExceptionSorter() {
        throw new IllegalStateException("Util class");
    }

    public static RuntimeException throwException(Exception ex) {
        if(ex instanceof SoccerManagerServiceException) {
            switch (((SoccerManagerServiceException) ex).getStatus()) {
                case RESOURCE_NOT_FOUND:
                    return new ResourceNotFoundException(ex.getLocalizedMessage());
                case TEAM_ALREADY_ASSIGNED:
                    return new ConflictException(ex.getLocalizedMessage());
                case NO_MORE_ADMINISTRATOR:
                    return new MethodNotAllowedException(ex.getLocalizedMessage());
                case PLAYER_IS_IN_TEAM:
                    return new ConflictException(ex.getLocalizedMessage());
                case NO_PLAYER_IN_TEAM:
                    return new MethodNotAllowedException(ex.getLocalizedMessage());
                case TOO_MANY_PLAYERS_IN_TEAM:
                    return new ConflictException(ex.getLocalizedMessage());
                case MATCH_ALREADY_SIMULATED:
                    return new ConflictException(ex.getLocalizedMessage());
                case MATCH_NOT_SIMULATED:
                    return new ResourceNotModifiedException(ex.getLocalizedMessage());
                case MATCH_DATE_IN_THE_FUTURE:
                case TEAM_CANNOT_UPDATE:
                case TEAM_CANNOT_REMOVE:
                    return new ConflictException(ex.getLocalizedMessage());
                default:
                    return new InternalServerErrorException(ex.getLocalizedMessage());
            }

        }
        
        if(ex instanceof IllegalArgumentException) {
        	return new ConflictException(ex.getLocalizedMessage());
        }        
        
        return new InternalServerErrorException(ex);
    }
}
