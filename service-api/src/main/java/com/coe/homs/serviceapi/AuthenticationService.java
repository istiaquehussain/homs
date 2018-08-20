package com.coe.homs.serviceapi;

import java.util.Optional;

import com.coe.homs.entity.User;


public interface AuthenticationService {
	public Optional<User> register(User user);
	//Implementor should return token it to be used for following call
	public Optional<String> login(String uid, String pwd);
	public Optional<User> findUserByKey(String key);
	public void logout();
}
