package com.reda.tpCompte.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reda.tpCompte.dao.CompteDao;
import com.reda.tpCompte.dao.OperationDao;
import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.entities.CompteCourant;
import com.reda.tpCompte.entities.Operation;
import com.reda.tpCompte.entities.Retrait;
import com.reda.tpCompte.entities.Versement;
import com.reda.tpCompte.service.IOperationService;

@Service
@Transactional
public class OperationServiceImpl implements IOperationService{

	@Autowired
	CompteDao compteDao;

	@Autowired
	OperationDao operationDao;

	@Override
	public int verser(String num, Double montant) {
		Compte cp = compteDao.findOne(num);
		if (cp!=null) {
			Double solde =cp.getSolde();
			cp.setSolde(solde+montant);
			compteDao.save(cp);
			Operation op = new Versement(new Date(), montant, cp);
			operationDao.save(op);
			return 1;
		}
		return -1;

	}

	@Override
	public int retrait(String num, Double montant) {
		Compte cp = compteDao.findOne(num);
		if (cp==null) {
			throw new RuntimeException("compte not found");
		}else {
			Double decouvert = 0D,solde =cp.getSolde();
			if (cp instanceof CompteCourant) {
				decouvert+=((CompteCourant) cp).getDecouvert();
			}
			if (solde+decouvert>=montant) {
				cp.setSolde(solde-montant);
				compteDao.save(cp);
				Operation op = new Retrait(new Date(), montant, cp);
				operationDao.save(op);
				return 1;
			}else { 
				throw new RuntimeException("Solde insuffisant");
			}
		}
	}

	@Override
	public int virement(String num1, String num2, Double montant) {
		if(num1.equals(num2)) {
			throw new RuntimeException("Impossible d'effectuer ce virement");
		}
		retrait(num1, montant);
		verser(num2, montant);
		return 1;
	}

	@Override
	public Page<Operation> findOperations(String num, int page, int size) {
		Page<Operation> operations = operationDao.findOperations(num, new PageRequest(page, size));
		return operations;
	}


	@Autowired
	private EntityManager em;


	@Override
	public List<Object> findOperationsFilterMinMax(String v,String r,Date min, Date max, String num, int page,
			int size) {
		if (min==null) {
			min = operationDao.getMinDate();
		}
		if (max==null) {
			max = operationDao.getMaxDate();
		}
		
		Page<Operation> operations = operationDao.findByDateOperationBetweenAndCompteIdOrderByDateOperationDesc(min, max, num, new PageRequest(page, size));
		List<Operation> ops = new ArrayList();
		Boolean versement = v.equals("") ? false : true ;
		Boolean retrait = r.equals("") ? false : true ;
		if (!(versement && retrait) && !(!versement && !retrait)) {
			if (versement) {
				for (Operation operation : operations) {
					if (operation.getClass().getSimpleName().equals("Versement")) {
						ops.add(operation);
					}
				}
			}else {
				for (Operation operation : operations) {
					if (operation.getClass().getSimpleName().equals("Retrait")) {
						ops.add(operation);
					}
				}
			}
		}else {
			ops.addAll(operations.getContent());
		}
		List<Object> list = new ArrayList();
		list.add(ops);
		list.add(operations.getTotalPages());
		return list;
	}

	@Override
	public List<Double> findOperationsStat(int annee, Long client, String compte) {
		List<Double> dataPoints = new ArrayList<Double>();
		String query ="SELECT SUM(o.mantant) as s from Operation o where 1=1 ";
		if (!compte.equals("")) {
			query+=" AND o.compte.id='"+compte+"'";
		}
		if (client!=null) {
			query+=" AND o.compte.client.id="+client;
		}
		for (int i = 0; i < 12; i++) {
			String requete = query + findByMois(i+1, annee);
			Double res = (Double) em.createQuery(requete).getSingleResult();
			dataPoints.add(res!=null ? res : 0);
		}
		return dataPoints;
	}
	
	@Override
	public List<Double> findOperationsStat(int annee, Long client, String compte,String oper) {
		List<Double> dataPoints = new ArrayList<Double>();
		String query ="SELECT SUM(mantant) as s from Operation o";
		if (client!=null) {
			query+=",Compte c WHERE c.id=o.compte And c.client="+client+" AND ";
		}else {
			query+=" WHERE ";
		}
		if (oper.equals("V")) {
			query+=" type_op='V' ";
		}else {
			query+=" type_op='R' ";
		}
		if (!compte.equals("")) {
			query+=" AND compte='"+compte+"'";
		}
		for (int i = 0; i < 12; i++) {
			String requete = query + findByMoisSql(i+1, annee);
			Double res = (Double) em.createNativeQuery(requete).getSingleResult();
			dataPoints.add(res!=null ? res : 0);
		}
		return dataPoints;
	}

	public String findByMoisSql(int mois,int year) {
		String moisFormate = "";
		if (mois < 10) {
			moisFormate += "0" + mois;
		} else {
			moisFormate = "" + mois;
		}
		String annee = year == 0 ? "%" : new String(year+"");
		String requete = " AND o.date_operation LIKE '" + annee + "-" + moisFormate + "-%'";
		return requete;

	}
	
	public String findByMois(int mois,int year) {
		String moisFormate = "";
		if (mois < 10) {
			moisFormate += "0" + mois;
		} else {
			moisFormate = "" + mois;
		}
		String annee = year == 0 ? "%" : new String(year+"");
		String requete = " AND o.dateOperation LIKE '" + annee + "-" + moisFormate + "-%'";
		return requete;

	}

	@Override
	public List<Integer> getAnnee() {
		List<Integer> annees = em.createQuery("SELECT DISTINCT YEAR(o.dateOperation) FROM Operation o").getResultList();
		return annees; 
	}
	

}
