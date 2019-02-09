package com.capgemini.Service;

import java.math.BigDecimal;
import java.util.List;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Transaction;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Exception.NoTransactionOccurException;

public interface WalletService {

	public Customer createAccount(String name, String mobileno, BigDecimal amount)
			throws MobileNoAlreadyRegisteredException;

	public Customer showBalance(String mobileno) throws MobileNoNotFoundException;

	public Customer[] fundTransfer(String sourceMobileno, String targetMobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException;

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNoNotFoundException;

	public Customer withdrawAmount(String mobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException;

	public List<Transaction> printlastTransaction(String mobileNo)
			throws MobileNoNotFoundException, NoTransactionOccurException;
}
