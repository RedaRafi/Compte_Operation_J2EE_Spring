package com.reda.tpCompte.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reda.tpCompte.entities.Users;

public interface UserDao extends JpaRepository<Users, String>{

	public Users findByUsername(String username);
}
