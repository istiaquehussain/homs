package com.coe.homs.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.AuthenticationService;
@Service("CustomAuthenticationProvider")
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	AuthenticationService authenticationService;
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		final Object uidToken = authentication.getCredentials();
		return authenticationService.findUserByKey((String)uidToken).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + uidToken));
	}
   /*
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final Object uidToken = authentication.getCredentials();
		
		User user =authenticationService.findUserByKey((String)uidToken).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + uidToken));
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		user.getRoles().forEach(role->authorities.add(new SimpleGrantedAuthority("ROLE_"+role)));
		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials(),authorities);
		
	}
	*/
}
