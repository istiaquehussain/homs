package com.coe.homs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.AuthenticationService;
import com.coe.homs.serviceapi.UserService;

@RestController
@RequestMapping("/admin")
public class AdminServiceController {
	@Autowired
	@Qualifier("InMemoryUserService")
	UserService userService;
	
	
		@GetMapping("/users")
		public ResponseEntity<?> getAllUsers() throws Exception
		{
			List<User> uses=userService.getUsers();
			return new ResponseEntity<List<User>>(uses,HttpStatus.OK);
		}


}
