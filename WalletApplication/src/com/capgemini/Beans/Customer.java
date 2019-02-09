package com.capgemini.Beans;

import java.util.LinkedList;

public class Customer {

	private String name;
	private String mobileno;
	private Wallet wallet;
	private LinkedList<Transaction> trans = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public LinkedList<Transaction> getTrans() {
		return trans;
	}

	public void setTrans(LinkedList<Transaction> trans) {
		this.trans = trans;
	}

	@Override
	public String toString() {
		return "CustomerName: " + name + "\nMobile No.: " + mobileno + "\nWallet Balance: " + wallet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobileno == null) ? 0 : mobileno.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((trans == null) ? 0 : trans.hashCode());
		result = prime * result + ((wallet == null) ? 0 : wallet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (mobileno == null) {
			if (other.mobileno != null)
				return false;
		} else if (!mobileno.equals(other.mobileno))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (trans == null) {
			if (other.trans != null)
				return false;
		} else if (!trans.equals(other.trans))
			return false;
		if (wallet == null) {
			if (other.wallet != null)
				return false;
		} else if (!wallet.equals(other.wallet))
			return false;
		return true;
	}

}
