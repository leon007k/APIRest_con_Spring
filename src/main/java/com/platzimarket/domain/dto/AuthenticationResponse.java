package com.platzimarket.domain.dto;

import java.io.Serializable;

// * We ship the JWT
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;

    public AuthenticationResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public String getToken(){
        return jwtToken;
    }

}
