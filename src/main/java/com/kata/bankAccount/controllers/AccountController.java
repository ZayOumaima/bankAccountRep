package com.kata.bankAccount.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.bankAccount.entities.Account;
import com.kata.bankAccount.repositories.AccountRepository;
import com.kata.bankAccount.utils.ExceptionMessages;

@RestController
@RequestMapping("/kata/bankAccount/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * create new account
	 * 
	 * @param newAccount
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public Account createAccount(@RequestBody Account newAccount) throws Exception {
		if (newAccount == null) {
			throw new Exception(ExceptionMessages.EMPTY_ACCOUNT_CREATION_ERROR);
		} else {

		}
		Account createdAccount = accountRepository.save(newAccount);

		return createdAccount;
	}

	/**
	 * get account by id
	 * 
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	public ResponseEntity getAccountById(@PathVariable(value = "id") Long accountId) throws Exception {
		if (accountId == null) {
			return ResponseEntity.badRequest().body(ExceptionMessages.NULL_ID_ACCOUNT_RETRIEVE_ERROR);
		}
		Account account = accountRepository.getOne(accountId);

		if (account == null) {
			throw new Exception(ExceptionMessages.ACCOUNT_NOT_FOUND_ERROR);
		}

		return ResponseEntity.ok().body(account);
	}
}
