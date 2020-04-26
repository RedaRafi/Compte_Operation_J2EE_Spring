package com.reda.tpCompte.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reda.tpCompte.entities.Users_Roles;

public interface UsersRoleDao extends JpaRepository<Users_Roles, Long>{
	
	public List<Users_Roles> findByUsername(String username);

	public void deleteByUsername(String username);
	
	public void deleteByUsernameAndRole(String username,String role);
}
