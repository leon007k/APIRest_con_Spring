package com.platzimarket.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String keySecret;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JWTUtil.class);

    public String generateToken(UserDetails userDetails) throws ParseException {

        // * Set SecretKey
        SecretKey key = Keys.hmacShaKeyFor(keySecret.getBytes());

        // * set expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,10);

        /*
        * biulder(), permitira en una secuencia de metodos construir el jwt
        * setSubject(), contendra el usuario
        * setIssuedAt(), inicamos la fecha en la que se crea el token
        * */
        String jwt = Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date()).
                setExpiration(DateFormat.getTimeInstance().parse(String.valueOf(calendar)))
                .signWith(key, SignatureAlgorithm.HS256).compact();

        LOGGER.debug("Generated JWT: {}", jwt);

        return jwt;
    }
}
