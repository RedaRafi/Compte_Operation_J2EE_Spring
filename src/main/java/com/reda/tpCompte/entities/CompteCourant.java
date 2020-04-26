package com.reda.tpCompte.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class CompteCourant extends Compte{
	private Double decouvert;

	public CompteCourant(Double decouvert) {
		this.decouvert = decouvert;
	}

	public CompteCourant(Date dateCreation, Double solde, Client client,Double decouvert) {
		super(dateCreation, solde, client);
		this.decouvert = decouvert;
	}
	
	

	public CompteCourant() {
	}

	public Double getDecouvert() {
		return decouvert;
	}

	public void setDecouvert(Double decouvert) {
		this.decouvert = decouvert;
	}

	public CompteCourant(Date dateCreation, Double solde, Double decouvert) {
		super(dateCreation, solde);
		this.decouvert = decouvert;
	}

	public CompteCourant(String id, Date dateCreation, Double solde, Double decouvert) {
		super(id, dateCreation, solde);
		this.decouvert = decouvert;
	}

	@Override
	public String toString() {
		return "[\""+super.getId()+"\", \""+super.getSolde()+"\", \""+decouvert + "\", \"CC\"]";
	}


	
	
	
	
}
