package com.reda.tpCompte.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reda.tpCompte.entities.Compte;

public interface CompteDao extends JpaRepository<Compte, String>{
	
       @Query("SELECT c From Compte c WHERE c.client.id=:x and c.dateSupression= null order by dateCreation Desc")
	   public Page<Compte> findByClient(@Param("x") Long cl,Pageable pageable);
       
       @Query("SELECT c From Compte c WHERE c.client.id=:x and c.dateSupression= null order by dateCreation Desc")
	   public List<Compte> findByClient(@Param("x") Long cl);
       
}
