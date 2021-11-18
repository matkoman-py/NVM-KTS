package com.rest.RestaurantApp.security.auth;

import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.services.UserAuthService;
import com.rest.RestaurantApp.util.TokenUtils;
import com.sun.istack.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserAuthService userDetailsService;

    @Autowired
    protected final Log LOGGER = LogFactory.getLog(getClass());

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserAuthService userDetailsService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String identity;
        String authToken = tokenUtils.getToken(request);
        if(authToken != null) {
            identity = tokenUtils.getIdentityFromToken(authToken);
            if(identity != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(identity);

                if(tokenUtils.validateToken(authToken, userDetails)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(userDetails.getAuthorities().toArray()[0].toString());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
