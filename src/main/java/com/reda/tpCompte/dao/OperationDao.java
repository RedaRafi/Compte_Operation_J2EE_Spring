package com.reda.tpCompte.dao;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reda.tpCompte.entities.Operation;

public interface OperationDao extends JpaRepository<Operation, Long> {
	 @Query("select o FROM Operation o where o.compte.id=:x order By dateOperation desc")
     public Page<Operation> findOperations(@Param("x") String num,Pageable pageable);
	 
     public Page<Operation> findByDateOperationBetweenAndCompteIdOrderByDateOperationDesc(Date min, Date max,String compte,Pageable pageable);
     
     @Query("select max(o.dateOperation) FROM Operation o")
     public Date getMaxDate();
     
     @Query("select min(o.dateOperation) FROM Operation o")
     public Date getMinDate();
}
