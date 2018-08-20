package com.coe.homs.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
@Service("CustomAuthenticationProviderWithRole")
public class CustomAuthenticationProviderWithRole implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//System.out.println("CEOCustomRESTAuthenticationProvider->Authentication Service Envocked !!");
		 //String username = authentication.getPrincipal().toString() ;
		 //String password = authentication.getCredentials().toString();
		//authentication.
		 List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		 authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		 return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials(),authorities);
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(
		          UsernamePasswordAuthenticationToken.class);
	}


}
