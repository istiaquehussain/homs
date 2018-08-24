package com.coe.homs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.UserService;
import com.coe.homs.util.CustomException;

@RestController
@RequestMapping("/admin")
public class AdminServiceController {
	@Autowired
	@Qualifier("${app.service.UserService}")
	UserService userService;
	
	
		@GetMapping("/users")
		public ResponseEntity<?> getAllUsers() throws Exception
		{
			List<User> uses=userService.getUsers();
			return new ResponseEntity<List<User>>(uses,HttpStatus.OK);
		}
		
		@GetMapping("/users/{id}")
		public ResponseEntity<?> getUserById(@PathVariable("id") String id) throws Exception
		{
			//User user=userService.findById(id).orElseThrow(()->new Exception("User Not found for id "+id));
			User user=userService.findById(id).orElseThrow(()->new CustomException(HttpStatus.BAD_REQUEST,"User Not found for id "+id));

			return new ResponseEntity<>(user,HttpStatus.OK);
		} 


}
