package com.reda.tpCompte.service;

import org.springframework.data.domain.Page;

import com.reda.tpCompte.entities.Users;

public interface IUserService {
	
	public void creerUser(String username,String password,String role);
	
	public void deleteUser(String username);
	
	public Page<Users> findAllPage(int page,int size);
	
	public void blockerUser(String username);
	
	public void deBlockerUser(String username);
	
	public void changePass(String username,String password, String apassword);
	
	public void changeRole(String username,String role);
	
}
