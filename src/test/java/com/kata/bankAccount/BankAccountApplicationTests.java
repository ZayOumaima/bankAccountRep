package com.kata.bankAccount;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	private static final String CLIENT_NAME = "Oumaima";
	private static final String CLIENT_FULL_NAME = "OumaimaZAYANI";
	private static final String CLIENT_PATH = "/client/";
	private static final String ACCOUNT_PATH = "/account/";
	private static final String TRANSACTION_PATH = "/transaction/";
	private static final String ALL_TRANSACTION_PATH = TRANSACTION_PATH + "findAllByAccountID/";

	private String getRootUrl() {
		return "http://localhost:" + SERVER_PORT + "/kata/bankAccount";
	}

	 @Test
	/**
	 * test create new client function
	 */
	public void testCreateClient() {
		Client c = new Client(CLIENT_NAME);
		ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH, c, Client.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(CLIENT_NAME, postResponse.getBody().getName());
	}

	 @Test
	/**
	 * test get client by id function
	 */
	public void testGetClientById() {
		Client c = new Client(CLIENT_NAME);
		ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH, c, Client.class);
		// get the created client above
		Client client = restTemplate.getForObject(getRootUrl() + CLIENT_PATH + postResponse.getBody().getId(),
				Client.class);

		Assert.assertNotNull(client);
		Assert.assertEquals(CLIENT_NAME, client.getName());
	}

	@Test
	/**
	 * test create new account function
	 */
	public void testCreateAccount() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);

		// get the created client above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account account = new Account(client, 10);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				account, Account.class);
		Assert.assertNotNull(postResponseCreateAccount);
		Assert.assertNotNull(postResponseCreateAccount.getBody());
		Assert.assertEquals(CLIENT_NAME, postResponseCreateAccount.getBody().getClient().getName());
		Assert.assertEquals(10, postResponseCreateAccount.getBody().getCurrentBalance(), 0.0);
	}

	 @Test
	/**
	 * test get account by id function
	 */
	public void testGetAccountById() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);
		// get the created client above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account newAccount = new Account(client, 0);

		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				newAccount, Account.class);
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		Assert.assertNotNull(account);
		Assert.assertEquals(CLIENT_NAME, account.getClient().getName());
		Assert.assertEquals(0.0, account.getCurrentBalance(), 0.0);
	}

	 @Test
	/**
	 * test make a deposit in an account with success US1
	 */
	public void testMakeAccountDepositSuccess() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_FULL_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);
		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account accountToCreate = new Account(client, 0);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				accountToCreate, Account.class);
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// create New Transaction
		Transaction trans = new Transaction(new Date(), TypeTransaction.deposit, 500, account);

		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(CLIENT_FULL_NAME, postResponse.getBody().getAccount().getClient().getName());
		Assert.assertEquals(500, postResponse.getBody().getAmount(), 0.0);
		Assert.assertEquals(500, postResponse.getBody().getAccount().getCurrentBalance(), 0.0);

	}

	 @Test
	/**
	 * test failing make a deposit in an account US1
	 */
	public void testMakeAccountDepositFail() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_FULL_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);
		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);

		// create New Account
		Account accountToCreate = new Account(client, 0);

		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				accountToCreate, Account.class);

		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// create New Transaction
		Transaction trans = new Transaction(new Date(), TypeTransaction.deposit, -500, account);
		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertEquals(500,postResponse.getStatusCodeValue());
	}

	 @Test
	/**
	 * test make a withdrawal from an account with success US2
	 */
	public void testMakeAccountWithdrawalSuccess() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_FULL_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);

		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account accountToCreate = new Account(client, 1000);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				accountToCreate, Account.class);

		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// create New Transaction
		Transaction trans = new Transaction(new Date(), TypeTransaction.withdrawal, 200, account);

		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(CLIENT_FULL_NAME, postResponse.getBody().getAccount().getClient().getName());
		Assert.assertEquals(200, postResponse.getBody().getAmount(), 0.0);
		Assert.assertEquals(800, postResponse.getBody().getAccount().getCurrentBalance(), 0.0);

	}

	 @Test
	/**
	 * test failing make a withdrawal from an account US2
	 */
	public void testMakeAccountWithdrawalFail() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_FULL_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);
		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account accountToCreate = new Account(client, 700);

		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				accountToCreate, Account.class);
		// get the account created above
		Account account = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
		// create New Transaction
		Transaction trans = new Transaction(new Date(), TypeTransaction.withdrawal, -500, account);

		ResponseEntity<Transaction> postResponse = restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, trans,
				Transaction.class);
		Assert.assertNotNull(postResponse);
		Assert.assertEquals(500,postResponse.getStatusCodeValue());
	}

	@Test
	/**
	 * test failing make a withdrawal from an account US2
	 */
	public void testFindAllTransactionByAccountId() {
		// create New Client
		Client clientToCreate = new Client(CLIENT_FULL_NAME);
		ResponseEntity<Client> postResponseCreateClient = restTemplate.postForEntity(getRootUrl() + CLIENT_PATH,
				clientToCreate, Client.class);

		// get the client created above
		Client client = restTemplate
				.getForObject(getRootUrl() + CLIENT_PATH + postResponseCreateClient.getBody().getId(), Client.class);
		// create New Account
		Account accountToCreate = new Account(client, 700);
		ResponseEntity<Account> postResponseCreateAccount = restTemplate.postForEntity(getRootUrl() + ACCOUNT_PATH,
				accountToCreate, Account.class);

		// get the account created above
		Account accountCreated = restTemplate.getForObject(
				getRootUrl() + ACCOUNT_PATH + postResponseCreateAccount.getBody().getIdAccount(), Account.class);
	
		// create first New Deposit Transaction
		Transaction firstDepTrans = new Transaction(new Date("01/07/2020"), TypeTransaction.deposit, 800,
				accountCreated);
		restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, firstDepTrans, Transaction.class);
		
		// create Second New Deposit Transaction
		Transaction secondDepTrans = new Transaction(new Date("01/08/2020"), TypeTransaction.deposit, 1200,
				accountCreated);
		restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, secondDepTrans, Transaction.class);
	
		// create third New withdrawal Transaction
		Transaction thirdWithdrawalTrans = new Transaction(new Date(), TypeTransaction.withdrawal, 1000,
				accountCreated);
		restTemplate.postForEntity(getRootUrl() + TRANSACTION_PATH, thirdWithdrawalTrans, Transaction.class);
		
		// Get list of transactions of an account
		ResponseEntity<Transaction[]> responseEntity = restTemplate.getForEntity(getRootUrl() + ALL_TRANSACTION_PATH + postResponseCreateAccount.getBody().getIdAccount(), Transaction[].class);
		Transaction[] response = responseEntity.getBody();
		Assert.assertNotNull(response);
		Assert.assertEquals(3, response.length, 0.0);
		Assert.assertEquals(new Date("01/07/2020"), response[0].getTransactionDate());
		Assert.assertEquals(800, response[0].getAmount(),0.0);
		Assert.assertEquals(1700, response[0].getAccount().getCurrentBalance(), 0.0);
		Assert.assertEquals(TypeTransaction.deposit, response[0].getTransactionType());
			}
}
