package com.reda.tpCompte.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation{

	public Retrait() {
		super();
	}

	public Retrait(Long id, Date dateOperation, Double mantant, Compte compte) {
		super(id, dateOperation, mantant, compte);
		// TODO Auto-generated constructor stub
	}

	public Retrait(Date dateOperation, Double mantant, Compte compte) {
		super(dateOperation, mantant, compte);
		// TODO Auto-generated constructor stub
	}
	
		

	

}

