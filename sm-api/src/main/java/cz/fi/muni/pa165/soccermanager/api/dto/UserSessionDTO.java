/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.soccermanager.api.dto;

import javax.validation.constraints.NotNull;

/**
 *
 * @author michal
 */
public class UserSessionDTO {
    
    @NotNull
    String token;
    
    @NotNull
    UserDTO user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
}
