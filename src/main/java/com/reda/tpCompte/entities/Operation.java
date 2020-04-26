package com.reda.tpCompte.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_OP",discriminatorType = DiscriminatorType.STRING,length=1)
public abstract class Operation implements Serializable{

	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	private Date dateOperation;
	private Double mantant;
	@ManyToOne
	private Compte compte;
	public Operation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Operation(Long id, Date dateOperation, Double mantant, Compte compte) {
		super();
		this.id = id;
		this.dateOperation = dateOperation;
		this.mantant = mantant;
		this.compte = compte;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}
	public Double getMantant() {
		return mantant;
	}
	public void setMantant(Double mantant) {
		this.mantant = mantant;
	}
	public Compte getCompte() {
		return compte;
	}
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	public Operation(Date dateOperation, Double mantant) {
		super();
		this.dateOperation = dateOperation;
		this.mantant = mantant;
	}
	public Operation(Date dateOperation, Double mantant, Compte compte) {
		super();
		this.dateOperation = dateOperation;
		this.mantant = mantant;
		this.compte = compte;
	}
	
	
	

}
