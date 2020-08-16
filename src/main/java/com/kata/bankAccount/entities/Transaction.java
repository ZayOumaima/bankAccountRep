package com.kata.bankAccount.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.kata.bankAccount.utils.TypeTransaction;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long idTransaction;
	
	Date transactionDate;
	
	TypeTransaction transactionType;
	
	double amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE )
    @JoinColumn(name = "idAccount")
    private Account account;

	public long getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(long idTransaction) {
		this.idTransaction = idTransaction;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TypeTransaction getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TypeTransaction transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	
}
