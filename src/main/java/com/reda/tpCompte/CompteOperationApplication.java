package com.reda.tpCompte;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reda.tpCompte.dao.CompteDao;
import com.reda.tpCompte.dao.OperationDao;
import com.reda.tpCompte.entities.Compte;
import com.reda.tpCompte.entities.CompteCourant;
import com.reda.tpCompte.entities.CompteEpargne;
import com.reda.tpCompte.entities.Retrait;
import com.reda.tpCompte.entities.Versement;
import com.reda.tpCompte.service.IOperationService;

@SpringBootApplication
public class CompteOperationApplication implements CommandLineRunner {
	
	@Autowired
	private CompteDao compteDao;
	@Autowired
	private OperationDao operationDao;
	@Autowired
	private IOperationService operationService;

	public static void main(String[] args) {
		SpringApplication.run(CompteOperationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		compteDao.save(new CompteCourant("c1",new Date(),50000D,1000D));
//		compteDao.save(new CompteEpargne("c2",new Date(),36000D,1500D));
//		operationService.virement("c1", "c2", 120D);
//		operationService.virement("c2", "c1", 100D);
	}

}
