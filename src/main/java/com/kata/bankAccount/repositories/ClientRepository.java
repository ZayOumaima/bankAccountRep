package com.kata.bankAccount.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.bankAccount.entities.Client;

public interface ClientRepository  extends JpaRepository<Client, Long>{

}
