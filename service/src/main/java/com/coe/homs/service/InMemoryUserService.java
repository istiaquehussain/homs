package com.coe.homs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.UserService;
@Service("InMemoryUserService")
public class InMemoryUserService implements UserService {

	private Map<String,User> users= new HashMap<String,User>();
	
	public InMemoryUserService()
	{
		String id1 = UUID.randomUUID().toString();
		users.put(id1,new User(id1,"uid1","pwd1"));
		
		String id2 = UUID.randomUUID().toString();
		users.put(id2,new User(id2,"uid2","pwd2"));
		String id3 = UUID.randomUUID().toString();
		
		users.put(id3,new User(id3,"uid3","pwd3"));
		System.out.println("@@@@@@@@@ Users Initialized ");
	
	}
	
	@Override
	public User create(User user) {
		//Integer id = rand.nextInt(100);
		//user.setUid(id.toString());
		users.put(user.getUid(), user);
		return user;
	}

	@Override
	public Optional<User> findById(String id) {
		return Optional.ofNullable(users.get(id));
	}

	@Override
	public Optional<User> findByName(String name) {
		
		return users.values().stream().filter(user->Objects.equals(name, user.getUid())).findFirst();
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return new ArrayList<User>(users.values());
	}

}
