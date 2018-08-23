package com.coe.homs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coe.homs.entity.Role;
import com.coe.homs.entity.User;
import com.coe.homs.serviceapi.UserService;
@Service("InMemoryUserService")
public class InMemoryUserService implements UserService {

	private Map<String,User> users= new HashMap<String,User>();
	private Map<String,Role> roles= new HashMap<String,Role>();
	
	public InMemoryUserService()
	{
		String id5 = UUID.randomUUID().toString();
		Role role5=new Role(id5,id5,"ADMIN","Admin Role");
		roles.put(id5,role5);
		
		String id6 = UUID.randomUUID().toString();
		Role role6=new Role(id6,id6,"USER","Standard Role");
		roles.put(id6,role6);
		
		
		String id1 = UUID.randomUUID().toString();
		User user1=new User(id1,"uid1","pwd1");
		List<Role> user1Roles= new ArrayList<Role>();
		user1Roles.add(role6);
		user1.setRoles(user1Roles);
		users.put(id1,user1);
		
		String id2 = UUID.randomUUID().toString();
		User user2=new User(id2,"uid2","pwd2");
		List<Role> user2Roles= new ArrayList<Role>();
		user2Roles.add(role6);
		user2.setRoles(user2Roles);
		users.put(id2,user2);
		
		String id3 = UUID.randomUUID().toString();
		User user3=new User(id3,"uid3","pwd3");
		List<Role> user3Roles= new ArrayList<Role>();
		user3Roles.add(role6);
		user3.setRoles(user3Roles);
		users.put(id3,user3);
		
		String id4 = UUID.randomUUID().toString();
		User user4=new User(id4,"admin","adminpwd");
		List<Role> user4Roles= new ArrayList<Role>();
		user4Roles.add(role5);
		user4.setRoles(user4Roles);
		users.put(id4,user4);
		
		System.out.println("@@@@@@@@@ Users and Role Initialized ");
		
		
		
		
		
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
		return users.values().stream().filter(user->Objects.equals(id, user.getUid())).findFirst();
		//return Optional.ofNullable(users.get(id));
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

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<User>> getUsersByRole(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<Role>> getUserRoles(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
