package com.kata.bankAccount.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.bankAccount.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
