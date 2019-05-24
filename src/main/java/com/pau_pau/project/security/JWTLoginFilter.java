package com.pau_pau.project.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserDetailsService userDetailsService;
    private TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(
            String url,
            AuthenticationManager authManager,
            UserDetailsService userDetailsService,
            TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userDetailsService = userDetailsService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //TODO
        // Тестовые штуки, потом обязательно выкинуть
        System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", username, password);
        System.out.println();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //TODO
        // Тестовые штуки, потом обязательно выкинуть
        System.out.println("JWTLoginFilter.successfulAuthentication:");

        // Write Authorization to Headers of Response.
        tokenAuthenticationService.addAuthentication(response, authResult.getName());

        String authorizationString = response.getHeader("Authorization");

        System.out.println("Authorization String=" + authorizationString);
    }

}