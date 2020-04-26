package com.reda.tpCompte.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reda.tpCompte.entities.Client;

public interface IClientService {
	public Client consulterClient(Long num);

	public Page<Client> findAllPage(int page,int size);

	public List<Client> findAll();

	public Page<Client> findByCle(String cle,int page,int size);

	public void creerClient(String nom,String cin,String email);

	public void editClient(Long client,String nom, String cin,String email);

	public void deleteClient(Long code);
}

