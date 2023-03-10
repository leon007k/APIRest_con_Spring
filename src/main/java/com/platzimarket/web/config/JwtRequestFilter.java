package com.platzimarket.web.config;

import com.platzimarket.domain.service.MarketPlaceUserDetailsService;
import com.platzimarket.web.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private MarketPlaceUserDetailsService mPUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String userName = null;
        String jwtToken = null;

        /*
        * JWT token is in the form "Bearer token". Remove Bearer word and get only Token
        * */
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                userName = jwtUtil.getUserNameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                logger.error("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                logger.error("JWT Token has expired");
            }
        }else{
            logger.warn("JWT Token does not begin with Bearer String, token: " + requestTokenHeader);
        }


        // * Once we get the token validate it
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.mPUserDetailsService.loadUserByUsername(userName);

            // * if token is valid, configure Spring Security to manually set authentication
            if(jwtUtil.validateToken(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /*
                * After setting the Authentication in the context, we specify
                * that the current user is authenticated. So it passes the
                * Spring Security Configurations successfully.
                */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
