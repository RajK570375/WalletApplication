package com.capgemini.Repo;

import java.util.HashMap;
import java.util.Map.Entry;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Transaction;
import com.capgemini.Exception.MobileNoNotFoundException;

public class WalletRepoImpl implements WalletRepo {

	HashMap<String, Customer> al = new HashMap<>();
	static int id = 1;

	@Override
	public boolean save(Customer customer) {

		if (!findMobile(customer.getMobileno())) {
			al.put(customer.getMobileno(), customer);
			return true;
		}
		return false;
	}

	@Override
	public Customer findOne(String mobileno) throws MobileNoNotFoundException {

		if(al.containsKey(mobileno))
		{
			return al.get(mobileno);
		}

		throw new MobileNoNotFoundException();

	}

	@Override
	public boolean findMobile(String mobileno) {
		if(al.containsKey(mobileno))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean saveTrans(String mobileNo, Transaction t) throws MobileNoNotFoundException {

		if (al.containsKey(mobileNo)) {
			t.setId(id);
			al.get(mobileNo).getTrans().add(t);
			id++;
			return true;
		}
		throw new MobileNoNotFoundException();
	}

}
