package com.coe.homs.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.AuthenticationService;
import com.coe.homs.serviceapi.UserService;
@Service("UidAuthenticationService")
public class UidAuthenticationService implements AuthenticationService {

	@Autowired
	@Qualifier("${app.service.UserService}")
	UserService userService;
	
	@Override
	public Optional<User> register(User user) {
		String id=UUID.randomUUID().toString();
		user.setId(id);
		return Optional.ofNullable(userService.create(user));
	}


	@Override
	public Optional <String> login(String uid, String pwd) {
		Optional <User> logedUser= userService.findByName(uid).filter(user->Objects.equals(user.getPwd(), pwd));
		return logedUser.map(user->user.getId());
	}

	@Override
	public Optional <User> findUserByKey(String key) {
		return userService.findById(key);
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

}
