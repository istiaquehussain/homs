package com.coe.homs.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private String id;
	
	@JsonProperty("username")
	private String uid;
	
	@JsonIgnore
	private String pwd;
	
	@JsonIgnore
	private List<Role> roles;
	
	@JsonIgnore
	List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
	
	
	public User(String id, String uid,String pwd) {
		super();
		this.id = id;
		this.uid = uid;
		this.pwd = pwd;
	}

	
	
		
	@Override
	public String toString() {
		return "User [id=" + id + ", uid=" + uid + ", pwd=" + pwd + ", roles=" + roles + ", authorities=" + authorities
				+ "]";
	}




	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonIgnore
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@JsonIgnore
	public String getPwd() {
		return pwd;
	}
	@JsonProperty("password")
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return pwd;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return uid;
	}
	
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		
		return authorities;
	}
	
	@JsonProperty("acctActive")
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonProperty("acctUnLocked")
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonProperty("credValid")
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonProperty("acctEnabled")
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
		roles.forEach(role->authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
