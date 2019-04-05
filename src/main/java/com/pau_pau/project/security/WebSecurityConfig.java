package com.pau_pau.project.security;


import com.pau_pau.project.common.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoderUtil passwordEncoderUtil) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoderUtil = passwordEncoderUtil;
        tokenAuthenticationService = new TokenAuthenticationService(userDetailsService);
    }

    private TokenAuthenticationService tokenAuthenticationService;

    private UserDetailsService userDetailsService;

    private PasswordEncoderUtil passwordEncoderUtil;

    //  Тут мы пишем права доступа к адресам
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // No need authentication.
                .antMatchers("/").permitAll() //
                .antMatchers(HttpMethod.POST, "/login").permitAll() //
                .antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
                // Need authentication.

                .antMatchers("/accounts").hasRole("ADMIN")
                .antMatchers("/api/account/role").hasAuthority("ADMIN")
                //.antMatchers("/swagger-ui.html").permitAll()
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**" ,
                        /*Probably not needed*/ "/swagger.json")
                .permitAll()
                .anyRequest().authenticated()
                //
                .and()
                //
                // Add Filter 1 - JWTLoginFilter
                //
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userDetailsService, tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                //
                // Add Filter 2 - JWTAuthenticationFilter
                //
                .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderUtil.passwordEncoder());
    }

}
