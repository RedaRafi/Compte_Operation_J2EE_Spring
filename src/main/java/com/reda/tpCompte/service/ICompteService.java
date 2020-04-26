package com.reda.tpCompte.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.reda.tpCompte.entities.Compte;

public interface ICompteService {
	public Compte consulterCompte(String num);
	
	public Page<Compte> findByClient(Long cl,int page,int size);
	
	public List<Compte> findByClient(Long cl);
	
	public List<Compte> findAll();
	
	public Compte findById(String id);
	
	public String createCompte(String code,Double solde,Double montantType,Long client,int type);
	
	public String editeCompte(String code, Double montantType, int type,Long client);
	
	public String deleteCompte(String code);
}
