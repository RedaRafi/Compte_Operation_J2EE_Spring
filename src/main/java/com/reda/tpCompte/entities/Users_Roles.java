package com.reda.tpCompte.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Users_Roles implements Serializable{

	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Users_Roles(String username, String role) {
		this.username = username;
		this.role = role;
	}
	public Users_Roles() {
		super();
	}
	
	
}
