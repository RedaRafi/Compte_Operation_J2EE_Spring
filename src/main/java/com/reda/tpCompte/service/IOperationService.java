package com.reda.tpCompte.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.reda.tpCompte.entities.Operation;

public interface IOperationService {
	
   public int verser(String num,Double montant);
   public int retrait(String num,Double montant);
   public int virement(String num1,String num2,Double montant);
   public Page<Operation> findOperations(String num,int page,int size);
   public List<Object> findOperationsFilterMinMax(String versement,String retrait,Date min,
		   Date max, String num, int page,int size);
   public List<Double> findOperationsStat(int annee,Long client,String compte);
   public List<Double> findOperationsStat(int annee,Long client,String compte,String oper);
   public List<Integer> getAnnee();
   
}
