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

import com.kata.bankAccount.entities.Client;
import com.kata.bankAccount.repositories.ClientRepository;
import com.kata.bankAccount.utils.ExceptionMessages;

@RestController
@RequestMapping("/kata/bankAccount/client")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {
	@Autowired
	private ClientRepository clientRepository;


	@PostMapping("/")
	public Client createClient(@RequestBody Client newClient) {
		Client createdClient = null;
		try {
			if (newClient == null) {
				throw new Exception(ExceptionMessages.EMPTY_CLIENT_CREATION_ERROR);
			}
			createdClient = clientRepository.save(newClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdClient;
	}

	@GetMapping("/{id}")
	public ResponseEntity getClientById(@PathVariable(value = "id") Long clientId) {
        if (clientId == null) {
            return ResponseEntity.badRequest().body(ExceptionMessages.NULL_ID_CLIENT_RETRIEVE_ERROR);
        }
    	Client client = clientRepository.getOne(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
 
	
		return ResponseEntity.ok().body(client);
	}
}