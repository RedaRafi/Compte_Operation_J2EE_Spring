package com.reda.tpCompte.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Users implements Serializable{
	
	@Id 
	private String username; 
	private String password;
	private int active;
	private int role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public Users() {
	}
	public Users(String username, String password, int active) {
		this.username = username;
		this.password = password;
		this.active = active;
	}
	public Users(String username, String password, int active, int role) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.role = role;
	}

}
