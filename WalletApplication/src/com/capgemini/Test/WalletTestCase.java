package com.capgemini.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Wallet;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Exception.NoTransactionOccurException;
import com.capgemini.Repo.WalletRepoImpl;
import com.capgemini.Service.WalletService;
import com.capgemini.Service.WalletServiceImpl;

public class WalletTestCase {

	WalletService wsr = new WalletServiceImpl(new WalletRepoImpl());

	/*
	 * Add Customer 
	 * 1. When all the information is valid
	 * 2. When Mobile Number Already registered Exception
	 */

	@Test
	public void whenInfoIsValid() throws MobileNoAlreadyRegisteredException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(4000);
		w.setBalance(bd);
		c.setMobileno("8989898989");
		c.setName("Robin");
		c.setWallet(w);
		assertEquals(c, wsr.createAccount("Robin", "8989898989", bd));
	}

	@Test(expected = MobileNoAlreadyRegisteredException.class)
	public void whenMobileNumberIsAlreadyRegistered() throws MobileNoAlreadyRegisteredException {
		wsr.createAccount("Robin", "8989898989", new BigDecimal(4000));
		wsr.createAccount("Robin", "8989898989", new BigDecimal(4000));
	}

	/*
	 * Deposit 
	 * 1. When All Info Is Valid
	 * 2. When mobile Number Not Found Exception Occur
	 */
	@Test
	public void whenAllInfoIsValid() throws MobileNoNotFoundException, MobileNoAlreadyRegisteredException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(500);
		w.setBalance(bd);
		c.setMobileno("8989898989");
		c.setName("Robin");
		c.setWallet(w);
		wsr.createAccount("Robin", "8989898989", bd);
		assertEquals(c.getWallet().getBalance().add(b1), wsr.depositAmount("8989898989", b1).getWallet().getBalance());
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberNotFoundException() throws MobileNoNotFoundException {
		wsr.depositAmount("7878787878", new BigDecimal(600));
	}

	/*
	 * Withdraw
	 *  1. When all the information is Valid 
	 *  2. When mobile is not found exception 
	 *  3. when insufficient balance exception
	 */

	@Test
	public void whenAllInfoIsValidForWithdraw()
			throws MobileNoAlreadyRegisteredException, InsufficientBalanceException, MobileNoNotFoundException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(500);
		w.setBalance(bd);
		c.setMobileno("8989898989");
		c.setName("Robin");
		c.setWallet(w);
		wsr.createAccount("Robin", "8989898989", bd);
		assertEquals(c.getWallet().getBalance().subtract(b1),
				wsr.withdrawAmount("8989898989", b1).getWallet().getBalance());
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberIsNotFoundExcepion() throws InsufficientBalanceException, MobileNoNotFoundException {
		wsr.withdrawAmount("9999991122", new BigDecimal(400));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void whenInsufficientBalanceOccurInWithdraw()
			throws MobileNoAlreadyRegisteredException, InsufficientBalanceException, MobileNoNotFoundException {
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(5000);
		wsr.createAccount("Robin", "8989898989", bd);
		wsr.withdrawAmount("8989898989", b1);
	}

	/*
	 * Show Details
	 *  1. When information is valid 
	 *  2. When Mobile Number Not Found Exception
	 */

	@Test
	public void whenInfoIsValidInShowDetails() throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException {
		BigDecimal b1 = new BigDecimal(5000);
		Customer c = wsr.createAccount("Robin", "8989898989", b1);

		assertEquals(c, wsr.showBalance("8989898989"));
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberIsNotFoundException() throws MobileNoNotFoundException {
		wsr.showBalance("7878787878");
	}
	/*
	 * FundTransfer
	 *  1. When Information is valid in FundTransfer 
	 *  2. When Mobile Number Not Found Exception
	 *  3. When there is Insufficient balance in Sender's Account
	 */

	@Test
	public void whenTheInfoIsValidInFundTransfer()
			throws MobileNoAlreadyRegisteredException, InsufficientBalanceException, MobileNoNotFoundException {
		Customer[] c = new Customer[2];
		Wallet wallet = new Wallet();
		BigDecimal b1 = new BigDecimal(5000);
		BigDecimal bd = new BigDecimal(3500);
		c[0] = wsr.createAccount("Robin", "8989898989", b1);
		wallet.setBalance(c[0].getWallet().getBalance().subtract(bd));
		c[0].setWallet(wallet);
		BigDecimal b2 = new BigDecimal(200);
		c[1] = wsr.createAccount("Rahul", "9898989898", b2);
		wallet.setBalance(c[1].getWallet().getBalance().add(bd));
		c[1].setWallet(wallet);
		assertArrayEquals(c, wsr.fundTransfer("8989898989", "9898989898", bd));
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMoileNumberNotFoundExceptionInFundTransfer()
			throws InsufficientBalanceException, MobileNoNotFoundException {
		wsr.fundTransfer("9090909090", "8989898989", new BigDecimal(80));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void whenThereIsInsufficienBalanceException()
			throws MobileNoAlreadyRegisteredException, InsufficientBalanceException, MobileNoNotFoundException {
		wsr.createAccount("Raj", "8989898989", new BigDecimal(80));
		wsr.createAccount("Rahul", "7878787878", new BigDecimal(20));
		wsr.fundTransfer("7878787878", "8989898989", new BigDecimal(30));
	}

	/*
	 * Print Transaction
	 *  1. When mobile no. is not found Exception
	 */
	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberNotFoundExceptionInTransaction()
			throws MobileNoNotFoundException, NoTransactionOccurException {
		wsr.printlastTransaction("8989898989");
	}
}
