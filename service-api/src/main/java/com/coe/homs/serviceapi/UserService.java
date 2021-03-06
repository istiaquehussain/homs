package com.coe.homs.serviceapi;

import java.util.List;
import java.util.Optional;

import com.coe.homs.entity.Role;
import com.coe.homs.entity.User;



public interface UserService {
	List<User> getUsers();
	User create(User user);
	Optional<User> findById(String id);
	Optional<User> findByName(String name);
	List<Role> getRoles();
	Optional<List<User>> getUsersByRole(String roleId );
	Optional<List<Role>> getUserRoles(String id );

}
