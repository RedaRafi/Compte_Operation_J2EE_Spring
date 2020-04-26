package com.reda.tpCompte.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client implements Serializable{

	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String email;
	private String cin;
	
	@OneToMany(mappedBy= "client",fetch=FetchType.LAZY)
	private Collection<Compte> comptes;
	
	public Client(String nom) {
		this.nom = nom;
	}
	public Client() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Collection<Compte> getComptes() {
		return comptes;
	}
	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public Client(String nom, String email, String cin) {
		super();
		this.nom = nom;
		this.email = email;
		this.cin = cin;
	}
	
}
