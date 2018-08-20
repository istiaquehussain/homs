package com.coe.homs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.AuthenticationService;
import com.coe.homs.serviceapi.UserService;

@RestController
@RequestMapping("/public/auth")
public class AuthenticationServiceController {
	@Autowired
	@Qualifier("InMemoryUserService")
	UserService userService;
	@Autowired
	//@Qualifier("UIDAuthenticationService")
	@Qualifier("JwtTokenAuthenticationService")
	AuthenticationService authenticationService ;
	
	@PostMapping("/users")
	public User registerUser(@RequestBody User user) {
		return userService.create(user);
	}
	
	@PostMapping("/login/{id}/{pwd}")
	public ResponseEntity<?> login(@PathVariable("id") String uid,@PathVariable("pwd") String pwd) throws Exception
	{
		String id=authenticationService.login(uid, pwd).orElseThrow(()->new Exception("Login Failed"));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization",id);
		
		return new ResponseEntity<String>(id,responseHeaders,HttpStatus.OK);
	}
	
	

}
