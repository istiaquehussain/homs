package com.coe.homs.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
	@JsonIgnore
	private String id;
	@JsonProperty("id")
	private String rid;
	private String name;
	private String desciption;
	private List<User> users;
	
	
	
	public Role(String id, String rid, String name, String desciption) {
		super();
		this.id = id;
		this.rid = rid;
		this.name = name;
		this.desciption = desciption;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", rid=" + rid + ", name=" + name + ", desciption=" + desciption + ", users=" + users
				+ "]";
	}
	

}
