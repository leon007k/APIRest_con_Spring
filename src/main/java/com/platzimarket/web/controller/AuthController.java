package com.platzimarket.web.controller;

import com.platzimarket.domain.dto.AuthenticationRequest;
import com.platzimarket.domain.dto.AuthenticationResponse;
import com.platzimarket.domain.service.MarketPlaceUserDetailsService;
import com.platzimarket.web.security.JWTUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MarketPlaceUserDetailsService marketPlaceUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            UserDetails userDetails = marketPlaceUserDetailsService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);

            // * Agregar el token al encabezado de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","Bearer" + jwt);

            return ResponseEntity.ok().headers(headers).body(new AuthenticationResponse(jwt));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
