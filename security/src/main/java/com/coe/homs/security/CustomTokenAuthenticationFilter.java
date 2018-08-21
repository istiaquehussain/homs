package com.coe.homs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.coe.homs.serviceapi.AuthenticationService;

public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	@Qualifier("JwtTokenAuthenticationService")
	AuthenticationService authenticationService ;
	
	public CustomTokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		//System.out.println("Request intercepred by COECustomAuthenticationFilter and authenticated" );
		/*
		String uid= request.getParameter("uid");
		String pwd= request.getParameter("pwd");
		final Authentication auth = new UsernamePasswordAuthenticationToken("uid", "pwd");
		*/
		String token=request.getHeader("Authorization");
		//System.out.println("@@ Token Value -> "+token );
		String id = authenticationService.findUserByKey(token).orElseThrow(()->new ServletException("Invalid Token !!")).getUid();
		//System.out.println("@@ Token id -> "+id );
		final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
		
	    return getAuthenticationManager().authenticate(auth);
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	
	

}
