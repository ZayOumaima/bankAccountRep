package com.kata.bankAccount.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "account")
public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long idAccount;
	
    double currentBalance;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE )
    @JoinColumn(name = "idClient")
    Client client;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
    List<Transaction> transactions;


    
	public Account() {
		super();
	}


	public Account(Client client, double amount) {
		super();
		this.client = client;
		this.currentBalance = amount;
	}


	public long getIdAccount() {
		return idAccount;
	}


	public void setIdAccount(long idAccount) {
		this.idAccount = idAccount;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}
@JsonIgnore
	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public double getCurrentBalance() {
		return currentBalance;
	}


	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
    
    
}
