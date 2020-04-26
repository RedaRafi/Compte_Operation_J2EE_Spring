package com.reda.tpCompte.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_CPTE",discriminatorType=DiscriminatorType.STRING,length=2)
public abstract class Compte implements Serializable{

	@Id 
	private String id;
	private Date dateCreation;
	private Date dateSupression;
	private Double solde;
	@ManyToOne(fetch=FetchType.LAZY)
	private Client client;
	@OneToMany(mappedBy = "compte")
	private Collection<Operation> operations;
	
	public Compte() {
	}

	public Compte(Date dateCreation, Double solde, Client client) {
		super();
		this.dateCreation = dateCreation;
		this.solde = solde;
		this.client = client;
	}
	public Compte(Date dateCreation, Double solde) {
		super();
		this.dateCreation = dateCreation;
		this.solde = solde;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	public Date getDateSupression() {
		return dateSupression;
	}

	public void setDateSupression(Date dateSupression) {
		this.dateSupression = dateSupression;
	}

	public Compte(String id, Date dateCreation, Double solde) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.solde = solde;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", solde=" + solde + "}";
	}
	
	
	

}
