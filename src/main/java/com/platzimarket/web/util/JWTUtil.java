package com.platzimarket.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static final long serialVersionUID = -2550185165626007488L;

    private String secretKey = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private byte[] keyBytes = secretKey.getBytes();
    private SecretKey sigingSecretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

    // * Retrieve username from jwt token
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    // * Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // * For retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(sigingSecretKey).build().parseClaimsJws(token).getBody();
    }

    // * Check if the token has expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // * Generate token for user
    public String generateToken(String userName) throws ParseException {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userName);
    }

    /*
    * while creating the token -
    * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    * 2. Sign the JWT using the HS512 algorithm and secret key.
    * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    * compaction of the JWT to a URL-safe string
    */
    private String doGenerateToken(Map<String, Object> claims, String subject) throws ParseException {
        // * set expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,10);

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateFormat.getTimeInstance().parse(String.valueOf(calendar)))
                .signWith(sigingSecretKey, SignatureAlgorithm.HS256).compact();
    }

    // * Validate Token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

 }
