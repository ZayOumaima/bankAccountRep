package com.kata.bankAccount.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kata.bankAccount.entities.Account;
import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.repositories.AccountRepository;
import com.kata.bankAccount.repositories.TransactionRepository;
import com.kata.bankAccount.utils.ExceptionMessages;
import com.kata.bankAccount.utils.TypeTransaction;

@RestController
@RequestMapping("/kata/bankAccount/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * create new transaction
	 * 
	 * @param newTransaction
	 * @return
	 * @throws Exception
	 */

	@PostMapping("/")
	public Transaction createTransaction(@RequestBody Transaction newTransaction) throws Exception {
		if (newTransaction == null) {
			throw new Exception(ExceptionMessages.EMPTY_TRANSACTION_CREATION_ERROR);
		} else {
			if (newTransaction.getAmount() < 0) {
				throw new Exception(ExceptionMessages.NEGATIVE_AMOUNT_ERROR);
			}
		}
		Transaction createdTransaction = transactionRepository.save(newTransaction);
		this.updateAccountCurrentBalance(newTransaction.getAccount(), newTransaction.getAmount(),
				newTransaction.getTransactionType());

		return createdTransaction;
	}

	/**
	 * update account balance after each transaction
	 * 
	 * @param account
	 * @param amount
	 * @param typeTransaction
	 */
	private void updateAccountCurrentBalance(Account account, double amount, TypeTransaction typeTransaction) {
		Account currentAccount = accountRepository.getOne(account.getIdAccount());

		double newBalance = 0.0;
		if (typeTransaction.equals(TypeTransaction.deposit)) {
			newBalance = currentAccount.getCurrentBalance() + amount;
		}
		if (typeTransaction.equals(TypeTransaction.withdrawal)) {
			newBalance = currentAccount.getCurrentBalance() - amount;
		}
		account.setCurrentBalance(newBalance);
		accountRepository.updateAccountCurrentBalance(newBalance, account.getIdAccount());
	}

	/**
	 * find all transaction by account id
	 * 
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findAllByAccountID/{accountId}")
	public @ResponseBody List<Transaction> findAll(@PathVariable(value = "accountId") Long accountId) throws Exception {
		if (accountId == null) {
			throw new Exception(ExceptionMessages.ID_ACCOUNT_NOT_FOUND_ERROR);
		}
		Account account = accountRepository.getOne(accountId);
		if (account == null) {
			throw new Exception(ExceptionMessages.ACCOUNT_NOT_FOUND_ERROR);
		}
		List<Transaction> transactions = transactionRepository.findByAccount(account);
		return transactions;
	}
}
