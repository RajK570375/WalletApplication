package com.capgemini.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Transaction;
import com.capgemini.Beans.Transaction.TransType;
import com.capgemini.Beans.Wallet;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Exception.NoTransactionOccurException;
import com.capgemini.Repo.WalletRepo;

public class WalletServiceImpl implements WalletService {

	private WalletRepo walletrepo;

	public WalletServiceImpl(WalletRepo walletrepo) {
		super();
		this.walletrepo = walletrepo;
	}

	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount)
			throws MobileNoAlreadyRegisteredException {

		if (walletrepo.findMobile(mobileno)) {
			throw new MobileNoAlreadyRegisteredException();
		}

		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileno(mobileno);
		customer.setName(name);
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		if (walletrepo.save(customer)) {
			return customer;
		}
		return null;
	}

	@Override
	public Customer showBalance(String mobileno) throws MobileNoNotFoundException {

		return walletrepo.findOne(mobileno);
	}

	@Override
	public Customer[] fundTransfer(String sourceMobileno, String targetMobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException {
		Customer c[] = new Customer[2];
		if (walletrepo.findMobile(sourceMobileno) && walletrepo.findMobile(targetMobileno)) {
			c[0] = walletrepo.findOne(sourceMobileno);
			if (c[0].getWallet().getBalance().compareTo(amount) < 0) {
				throw new InsufficientBalanceException();
			}

			Wallet wallet = c[0].getWallet();
			wallet.setBalance(wallet.getBalance().subtract(amount));
			c[0].setWallet(wallet);
			Transaction t = new Transaction();
			t.setMobileNo(sourceMobileno);
			t.setTrtype(TransType.FundTransfer_From);
			t.setAmount(c[0].getWallet().getBalance());
			walletrepo.saveTrans(sourceMobileno, t);

			c[1] = walletrepo.findOne(targetMobileno);
			Wallet wallet1 = c[1].getWallet();
			wallet1.setBalance(wallet1.getBalance().add(amount));
			c[1].setWallet(wallet1);
			Transaction t1 = new Transaction();
			t1.setMobileNo(targetMobileno);
			t1.setTrtype(TransType.FundTransfer_To);
			t1.setAmount(c[1].getWallet().getBalance());
			walletrepo.saveTrans(targetMobileno, t1);
			return c;
		}
		throw new MobileNoNotFoundException();
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNoNotFoundException {

		Customer customer = walletrepo.findOne(mobileNo);
		Wallet wallet = customer.getWallet();
		wallet.setBalance(wallet.getBalance().add(amount));
		customer.setWallet(wallet);

		Transaction t = new Transaction();
		t.setMobileNo(mobileNo);
		t.setTrtype(TransType.Deposit);
		t.setAmount(customer.getWallet().getBalance());
		walletrepo.saveTrans(mobileNo, t);
		return customer;
	}

	@Override
	public Customer withdrawAmount(String mobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException {

		Customer customer = walletrepo.findOne(mobileno);

		if (customer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();

		} else {
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().subtract(amount));
			customer.setWallet(wallet);
			Transaction t = new Transaction();
			t.setMobileNo(mobileno);
			t.setTrtype(TransType.Withdraw);
			t.setAmount(customer.getWallet().getBalance());
			walletrepo.saveTrans(mobileno, t);
			return customer;
		}
	}

	@Override
	public List<Transaction> printlastTransaction(String mobileNo)
			throws MobileNoNotFoundException, NoTransactionOccurException {
		List<Transaction> l = new ArrayList<>();
		Customer c = walletrepo.findOne(mobileNo);
		if (c.getTrans().size() == 0) {
			throw new NoTransactionOccurException();
		}
		if (c.getTrans().size() < 10)
			return c.getTrans();
		else {
			Collections.reverse(c.getTrans());
			for (int i = 0; i < 10; i++) {
				l.add(c.getTrans().get(i));
			}
			Collections.reverse(l);
			return l;
		}
	}
}
