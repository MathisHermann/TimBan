/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import rocks.process.timban.business.service.UserDetailsServiceImpl;
import rocks.process.security.config.EnableTokenSecurity;
import rocks.process.security.service.TokenService;
import rocks.process.security.web.CSRFRequestMatcher;
import rocks.process.security.web.TokenAuthenticationFilter;
import rocks.process.security.web.TokenLoginFilter;
import rocks.process.security.web.TokenLogoutHandler;

@EnableWebSecurity
@EnableTokenSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
            .requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure().and() // If the X-Forwarded-Proto header is present, redirect to HTTPS (Heroku)
            .csrf()
                .requireCsrfProtectionMatcher(new CSRFRequestMatcher())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .authorizeRequests()
                .antMatchers("/", "/index", "/css/**", "/js/**", "/images/**", "/login/**", "/api/**").permitAll()
                .antMatchers("/dashboard/**", "/projects/**", "/admin/**", "/overview/**", "/record-settings", "/reports/**").authenticated()
                .antMatchers(HttpMethod.GET, "/logout").permitAll()
                .anyRequest().authenticated().and()
                    .addFilter(new TokenLoginFilter(authenticationManagerBean(), tokenService))
                    .addFilter(new TokenAuthenticationFilter(authenticationManagerBean(), tokenService))
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .addLogoutHandler(new TokenLogoutHandler(tokenService));

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}