package com.kata.bankAccount.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kata.bankAccount.entities.Account;

public interface AccountRepository  extends JpaRepository<Account, Long> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Account acc set acc.currentBalance = :newBalance where acc.id = :id")
	void updateAccountCurrentBalance(@Param("newBalance") double newBalance, @Param("id") Long id);
}
