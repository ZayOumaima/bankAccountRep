package com.kata.bankAccount.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.bankAccount.entities.Account;
import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.repositories.AccountRepository;
import com.kata.bankAccount.repositories.TransactionRepository;
import com.kata.bankAccount.utils.TypeTransaction;


@RestController
@RequestMapping("/kata/bankAccount/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
	@Autowired
    private TransactionRepository transactionRepository;
	@Autowired
    private AccountRepository accountRepository;
 
 @GetMapping("/")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }
 
 @PostMapping("/")
	public Transaction createTransaction(@RequestBody Transaction newTransaction) {
	 Transaction createdTransaction=null;
		try {
			if (newTransaction == null) {
				throw new Exception("Cannot create new Transaction with empty fields");
			}else {
				if(newTransaction.getTransactionType().equals(TypeTransaction.deposit) && newTransaction.getAmount()<0) {
					throw new Exception("Cannot deposit a negative amount");
				}
			}
			 createdTransaction = transactionRepository.save(newTransaction);
			 this.updateAccountCurrentBalance(newTransaction.getAccount(),newTransaction.getAmount(),newTransaction.getTransactionType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdTransaction;
	}

private void updateAccountCurrentBalance(Account account, double amount, TypeTransaction typeTransaction) {
	double newBalance=0.0;
	if(typeTransaction.equals(TypeTransaction.deposit)) {
		newBalance=account.getCurrentBalance()+amount;
	}
	if(typeTransaction.equals(TypeTransaction.withdrawal)) {
		newBalance=account.getCurrentBalance()-amount;
	}
	account.setCurrentBalance(newBalance);
	accountRepository.updateAccountCurrentBalance(newBalance,account.getIdAccount());
}
}
