package com.capgemini.Repo;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Transaction;
import com.capgemini.Exception.MobileNoNotFoundException;

public interface WalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileno) throws MobileNoNotFoundException;

	public boolean findMobile(String mobileno);

	public boolean saveTrans(String mobileNo, Transaction t) throws MobileNoNotFoundException;
}
