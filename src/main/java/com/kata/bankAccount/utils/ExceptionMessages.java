package com.kata.bankAccount.utils;

public class ExceptionMessages {
	
	public static final String EMPTY_CLIENT_CREATION_ERROR ="Cannot create client with empty fields";
	public static final String NULL_ID_CLIENT_RETRIEVE_ERROR ="Cannot retrieve client with null ID";
	public static final String EMPTY_ACCOUNT_CREATION_ERROR ="Cannot create Account with empty fields";
	public static final String NULL_ID_ACCOUNT_RETRIEVE_ERROR ="Cannot retrieve account with null ID";
	public static final String EMPTY_TRANSACTION_CREATION_ERROR = "Cannot create new Transaction with empty fields";
	public static final String NEGATIVE_AMOUNT_ERROR ="Cannot deposit/withdrawal a negative amount";
	public static final String ID_ACCOUNT_NOT_FOUND_ERROR = "Cannot retrieve transactions with id account null";
	public static final String ACCOUNT_NOT_FOUND_ERROR = "Account not found";
	public static final String	CLIENT_NOT_FOUND_ERROR= "Client not found";
}
