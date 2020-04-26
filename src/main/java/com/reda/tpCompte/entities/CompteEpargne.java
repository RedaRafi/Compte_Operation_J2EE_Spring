package com.reda.tpCompte.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {
	private Double taux;

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public CompteEpargne(Date dateCreation, Double solde, Client client, Double taux) {
		super(dateCreation, solde, client);
		this.taux = taux;
	}

	public CompteEpargne() {
		super();
	}

	public CompteEpargne(Date dateCreation, Double solde, Double taux) {
		super(dateCreation, solde);
		this.taux = taux;
	}

	public CompteEpargne(String id, Date dateCreation, Double solde) {
		super(id, dateCreation, solde);
	}

	public CompteEpargne(String id, Date dateCreation, Double solde, Double taux) {
		super(id, dateCreation, solde);
		this.taux = taux;
	}
	
	@Override
	public String toString() {
		return "[\""+super.getId()+"\", \""+super.getSolde()+"\", \""+taux + "\", \"CE\"]";
	}
	

}
