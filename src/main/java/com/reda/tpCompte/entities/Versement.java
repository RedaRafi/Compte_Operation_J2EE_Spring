package com.reda.tpCompte.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("V")
public class Versement extends Operation{


	public Versement() {
		super();
	}

	public Versement(Long id, Date dateOperation, Double mantant, Compte compte) {
		super(id, dateOperation, mantant, compte);
		// TODO Auto-generated constructor stub
	}

	public Versement(Date dateOperation, Double mantant, Compte compte) {
		super(dateOperation, mantant, compte);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
