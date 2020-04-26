package com.reda.tpCompte.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reda.tpCompte.entities.Client;

public interface ClientDao extends JpaRepository<Client, Long>{

	@Query("SELECT c FROM Client c where c.id=:x")
	public Client findById(@Param("x") Long num);
	
	public Page<Client> findByNomContainsOrCinContainsOrEmailContains(
			String nom,String cin,String email, Pageable pageable);
	
}
