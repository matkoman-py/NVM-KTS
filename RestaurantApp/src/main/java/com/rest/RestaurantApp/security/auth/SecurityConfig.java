package com.rest.RestaurantApp.security.auth;

import com.rest.RestaurantApp.services.EmployeeService;
import com.rest.RestaurantApp.services.UserAuthService;
import com.rest.RestaurantApp.util.TokenUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public SecurityConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

//    @Override
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("manager_test")
                .password(encoder.encode("test"))
                .roles("MANAGER");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("owner_test")
                .password(encoder.encode("test"))
                .roles("OWNER");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("employee")
                .password(encoder.encode("cook123"))
                .roles("COOK")
                .authorities("COOK");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("employee")
                .password(encoder.encode("barman123"))
                .roles("BARMAN")
                .authorities("BARMAN");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("employee")
                .password(encoder.encode("waiter123"))
                .roles("WAITER")
                .authorities("WAITER");

        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and();

//        http = http.exceptionHandling()
//                .authenticationEntryPoint((req, res, ex) -> {
//                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//                }).and();

        http.authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/employee/**").permitAll()
                .antMatchers("/api/article/**").permitAll()
                .antMatchers("/api/menu/**").permitAll()
                .antMatchers("/api/order/**").permitAll()
                .antMatchers("/api/ingredient").permitAll()
                .antMatchers("/api/privilegedUser/**").permitAll().and().httpBasic().and()
//                .anyRequest().authenticated().and().formLogin().loginProcessingUrl("/api/auth/loging").and()

                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userAuthService), BasicAuthenticationFilter.class);
        http.cors().and().csrf().disable();

    }

}
