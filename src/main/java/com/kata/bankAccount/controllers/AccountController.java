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

@RestController
@RequestMapping("/kata/bankAccount/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
	@Autowired
    private AccountRepository accountRepository;
	
	@PostMapping("/")
	public Account createAccount(@RequestBody Account newAccount) {
		Account createdAccount=null;
		try {
			if (newAccount == null) {
				throw new Exception("Cannot create Account with empty fields");
			}else {
				
			}
			 createdAccount = accountRepository.save(newAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdAccount;
	}
	@GetMapping("/{id}")
	public ResponseEntity getAccountById(@PathVariable(value = "id") Long accountId) {
        if (accountId == null) {
            return ResponseEntity.badRequest().body("Cannot retrieve account with null ID");
        }
    	Account account = accountRepository.getOne(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
 
	
		return ResponseEntity.ok().body(account);
	}
}
