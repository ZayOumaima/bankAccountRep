package com.kata.bankAccount.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.bankAccount.entities.Account;
import com.kata.bankAccount.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findByAccount(Account account);
}
