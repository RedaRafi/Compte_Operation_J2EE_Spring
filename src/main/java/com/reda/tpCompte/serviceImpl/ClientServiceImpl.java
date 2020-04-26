package com.reda.tpCompte.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reda.tpCompte.dao.ClientDao;
import com.reda.tpCompte.entities.Client;
import com.reda.tpCompte.service.IClientService;

@Service
@Transactional
public class ClientServiceImpl implements IClientService{

	@Autowired
	ClientDao clientDao;
	
	@Override
	public Client consulterClient(Long num) {
		try {
			Client c = clientDao.findById(num);
			return c;
		} catch (Exception e) {
			throw new RuntimeException("ce Client n'existe pas");
		}
	}

	@Override
	public Page<Client> findAllPage(int page,int size) {
		Page<Client> clients = clientDao.findAll(new PageRequest(page, size));
		return clients;
	}
	
	@Override
	public Page<Client> findByCle(String cle,int page,int size) {
		Page<Client> clients = clientDao.findByNomContainsOrCinContainsOrEmailContains(cle, cle, cle, new PageRequest(page, size));
		return clients;
	}

	@Override
	public void creerClient(String nom,String cin,String email) {
          clientDao.save(new Client(nom,email,cin));		
	}

	@Override
	public void editClient(Long client, String nom,String cin,String email) {
		Client c = consulterClient(client);
		c.setNom(nom);
		c.setCin(cin);
		c.setEmail(email);
		clientDao.save(c);
	}

	@Override
	public void deleteClient(Long code) {
		clientDao.delete(code);
	}

	@Override
	public List<Client> findAll() {
		return clientDao.findAll();
	}
	
}
