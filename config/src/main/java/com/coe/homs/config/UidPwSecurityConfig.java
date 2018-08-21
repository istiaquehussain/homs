package com.coe.homs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.coe.homs.security.CustomAuthenticationProvider;

public class UidPwSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final RequestMatcher PUBLIC_URLS =new OrRequestMatcher(new AntPathRequestMatcher("/public/**")); 
	private static final RequestMatcher PRIVALTE_URLS =new NegatedRequestMatcher(PUBLIC_URLS);	
	
	@Autowired
	@Qualifier("CustomAuthenticationProvider")
	CustomAuthenticationProvider customAuthenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("@@@@ configuring UidPwSecurityConfig security");
		auth.authenticationProvider(customAuthenticationProvider);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/public/**").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/private/**").hasRole("USER")
		.anyRequest()
		.authenticated()
        .and()
        //.authenticationProvider(ceoCustomAuthenticationProvider)
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .logout().disable();
		
		
		
        
        
	}
	
	
	

}
