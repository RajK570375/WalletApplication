package com.capgemini.Beans;

import java.math.BigDecimal;

public class Transaction {

	public enum TransType {
		Deposit, Withdraw, FundTransfer_From, FundTransfer_To
	}

	private int id;
	private String mobileNo;
	private TransType trtype;
	private BigDecimal amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public TransType getTrtype() {
		return trtype;
	}

	public void setTrtype(TransType trtype) {
		this.trtype = trtype;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction Id: " + id + "\nMobile No.: " + mobileNo + "\nTransaction Type: " + trtype + "\nAmount: "
				+ amount + "\n";
	}

}
