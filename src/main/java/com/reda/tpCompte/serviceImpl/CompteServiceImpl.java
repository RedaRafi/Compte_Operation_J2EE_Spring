package com.reda.tpCompte.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reda.tpCompte.dao.ClientDao;
import com.reda.tpCompte.dao.CompteDao;
import com.reda.tpCompte.entities.Client;
import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.entities.CompteCourant;
import com.reda.tpCompte.entities.CompteEpargne;
import com.reda.tpCompte.service.ICompteService;


@Service
@Transactional
public class CompteServiceImpl implements ICompteService{

	@Autowired
	CompteDao compteDao;

	@Autowired
	ClientDao clientDao;

	@Autowired
	private EntityManager em;

	@Override
	public Compte consulterCompte(String num) {
		Compte c = compteDao.findOne(num);	
		if (c != null) {
			return c;
		} else {
			throw new RuntimeException("Ce Compte n'existe pas");
		}
	}

	@Override
	public Page<Compte> findByClient(Long cl, int page, int size) {
		Page<Compte> comptes = compteDao.findByClient(cl,new PageRequest(page,size));
		comptes.forEach(c -> System.out.println("compte :: "+c));
		return comptes;
	}

	@Override
	public String createCompte(String code, Double solde, Double montantType, Long client, int type) {
		Compte c;
		c = compteDao.findOne(code);
		if (c!=null) {
			return "Compte déja existant";
		}else {
			Client cl;
			try {
				cl = clientDao.findOne(client);
			}catch (Exception e1) {
				return "Client n'exist pas";
			}
			if (type == 1) {
				c = new CompteCourant(code, new Date(), solde, montantType);
			} else {
				c = new CompteEpargne(code, new Date(), solde, montantType);
			}
			c.setClient(cl);
			compteDao.save(c);
			return "Compte creer avec succes";
		}

	}

	@Override
	public String editeCompte(String code, Double montantType, int type,Long client) {
		try {
			Compte c = compteDao.findOne(code);
			if (type == 1) {
				em.createNativeQuery("Update Compte SET taux= null, type_cpte= 'CC',"
						+ " decouvert ="+ montantType +" WHERE id='"+code+"'").executeUpdate();
			} else {
				em.createNativeQuery("Update Compte SET decouvert= null , type_cpte= 'CE',"
						+ " taux ="+ montantType +" WHERE id='"+code+"'").executeUpdate();
			}
			return "Compte modifier avec succes";
		} catch (Exception e) {
			return "Compte introuvable !";
		}
	}

	@Override
	public String deleteCompte(String code) {
		try {
			Compte c = compteDao.findOne(code);
			c.setDateSupression(new Date());
			compteDao.save(c);
			return "Compte supprimé avec succes";
		} catch (Exception e) {
			return "Compte introuvable !";
		}
	}

	@Override
	public List<Compte> findByClient(Long cl) {
		return compteDao.findByClient(cl);
	}

	@Override
	public List<Compte> findAll() {
		return compteDao.findAll();
	}

	@Override
	public Compte findById(String id) {
		return compteDao.findOne(id);
	}

}