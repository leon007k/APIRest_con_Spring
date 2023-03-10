package com.platzimarket.domain.dto;

import java.io.Serializable;

// * This class will get the user data
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    // * need default constructor for JSON Parsing
    public AuthenticationRequest(){}

    public AuthenticationRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
