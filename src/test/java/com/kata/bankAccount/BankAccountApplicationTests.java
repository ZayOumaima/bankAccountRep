package com.kata.bankAccount;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.kata.bankAccount.entities.Account;
import com.kata.bankAccount.entities.Client;
import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.utils.TypeTransaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankAccountApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private static final String SERVER_PORT = "8082";

	private String getRootUrl() {
		return "http://localhost:" + SERVER_PORT + "/kata/bankAccount";
	}

	@Test
	/**
	 * test create new client function
	 */
	public void testCreateClient() {
		Client c = new Client();
		c.setName("Oumaima");
		ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + "/client/", c, Client.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals("Oumaima", postResponse.getBody().getName());
	}

	@Test
	/**
	 * test get client by id function
	 */
	public void testGetClientById() {
		Client c = new Client();
		c.setName("Oumaima");
		ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + "/client/", c, Client.class);
		// get the created client above
		Client client = restTemplate.getForObject(getRootUrl() + "/client/" + postResponse.getBody().getId(),
				Client.class);

		Assert.assertNotNull(client);
		Assert.assertEquals("Oumaima", client.getName());
	}

	@Test
	/**
	 * test create new account function
	 */
	public void testCreateAccount() {
		// create New Client
		Client clientToCreate = new Client();
		clientToCreate.setName("OumaimaZAY");
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + "/client/",
				clientToCreate, Client.class);
		// create New Account
		Account account = new Account();
		// get the created client above
		Client client = restTemplate
				.getForObject(getRootUrl() + "/client/" + postResponseCreateClient.getBody().getId(), Client.class);
		// set the account client
		account.setClient(client);
		account.setCurrentBalance(0);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + "/account/",
				account, Account.class);
		Assert.assertNotNull(postResponseCreateAccount);
		Assert.assertNotNull(postResponseCreateAccount.getBody());
		Assert.assertEquals("OumaimaZAY", postResponseCreateAccount.getBody().getClient().getName());
		Assert.assertEquals(0.0, postResponseCreateAccount.getBody().getCurrentBalance(), 0.0);
	}

	@Test
	/**
	 * test get account by id function
	 */
	public void testGetAccountById() {
		// create New Client
		Client clientToCreate = new Client();
		clientToCreate.setName("Oumaima");
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + "/client/",
				clientToCreate, Client.class);
		// create New Account
		Account newAccount = new Account();
		// get the created client above
		Client client = restTemplate
				.getForObject(getRootUrl() + "/client/" + postResponseCreateClient.getBody().getId(), Client.class);
		// set the account client
		newAccount.setClient(client);
		newAccount.setCurrentBalance(0);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + "/account/",
				newAccount, Account.class);
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + "/account/" + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		Assert.assertNotNull(account);
		Assert.assertEquals("Oumaima", account.getClient().getName());
		Assert.assertEquals(0.0, account.getCurrentBalance(), 0.0);
	}

	@Test
	/**
	 * test make a deposit in an account with success
	 */
	public void testMakeAccountDepositSuccess() {
		// create New Client
		Client clientToCreate = new Client();
		clientToCreate.setName("OumaimaZAYANIS");
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + "/client/",
				clientToCreate, Client.class);
		// create New Account
		Account accountToCreate = new Account();
		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + "/client/" + postResponseCreateClient.getBody().getId(), Client.class);
		// set the account client
		accountToCreate.setClient(client);
		accountToCreate.setCurrentBalance(0);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + "/account/",
				accountToCreate, Account.class);
		// create New Transaction
		Transaction trans = new Transaction();
		trans.setTransactionDate(new Date());
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + "/account/" + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// set the transaction account
		trans.setAccount(account);
		trans.setTransactionType(TypeTransaction.deposit);
		trans.setAmount(500);
		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + "/transaction/", trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals("OumaimaZAYANI", postResponse.getBody().getAccount().getClient().getName());
		Assert.assertEquals(500, postResponse.getBody().getAmount(), 0.0);
		Assert.assertEquals(500, postResponse.getBody().getAccount().getCurrentBalance(), 0.0);

	}

	@Test
	/**
	 * test failing make a deposit in an account
	 */
	public void testMakeAccountDepositFail() {
		// create New Client
		Client clientToCreate = new Client();
		clientToCreate.setName("OumaimaZAYANIF");
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + "/client/",
				clientToCreate, Client.class);
		// create New Account
		Account accountToCreate = new Account();
		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + "/client/" + postResponseCreateClient.getBody().getId(), Client.class);
		// set the account client
		accountToCreate.setClient(client);
		accountToCreate.setCurrentBalance(0);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + "/account/",
				accountToCreate, Account.class);
		// create New Transaction
		Transaction trans = new Transaction();
		trans.setTransactionDate(new Date());
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + "/account/" + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// set the transaction account
		trans.setAccount(account);
		trans.setTransactionType(TypeTransaction.deposit);
		trans.setAmount(-500);
		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + "/transaction/", trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNull(postResponse.getBody());
	}
}
