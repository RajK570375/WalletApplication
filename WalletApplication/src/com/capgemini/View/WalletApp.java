package com.capgemini.View;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.Beans.Customer;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Exception.NoTransactionOccurException;
import com.capgemini.Repo.WalletRepo;
import com.capgemini.Repo.WalletRepoImpl;
import com.capgemini.Service.WalletServiceImpl;

public class WalletApp {
	static WalletRepo wallet = new WalletRepoImpl();
	static WalletServiceImpl wsi = new WalletServiceImpl(wallet);
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			menu();
		}

	}

	public static void menu() {
		System.out.println(" Wallet Application\n" + "____________________________\n" + "1. Add Customer\n"
				+ "2. Show Balance\n" + "3. WithDraw Amount\n" + "4. Depsoit Amount\n" + "5. Fund-Transfer\n"
				+ "6. Print Last 10 Transaction\n" + "7. Exit\n" + "Enter your choice: ");
		int ch = sc.nextInt();
		switch (ch) {
		case 1:
			getDetails();
			break;
		case 2:
			showBal();
			break;
		case 3:
			withdraw();
			break;
		case 4:
			deposit();
			break;
		case 5:
			fundTransfer();
			break;
		case 6:
			printlastTransaction();
			break;
		case 7:
			System.exit(0);
			break;
		default:
			System.out.println("Wrong Choice\n");
		}

	}

	private static void getDetails() {

		System.out.println("Enter the name:");
		String name = sc.next();
		boolean validn = nullValidation(name);
		while (validn) {
			System.out.println("This Field cannot be empty\nPlease enter the valid name: ");
			name = sc.next();
			validn = nullValidation(name);
		}

		System.out.println("Enter mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}
		System.out.println("Enter the amount: ");
		long l = sc.nextLong();
		BigDecimal amount = new BigDecimal(l);
		try {
			System.out.println(wsi.createAccount(name, mobileno1, amount));
			System.out.println("Account Created Successfully\n");
		} catch (MobileNoAlreadyRegisteredException e) {
			System.out.println("\n" + e);
		}

	}

	private static void showBal() {

		System.out.println("Enter mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}
		try {
			Customer c = wsi.showBalance(mobileno1);
			System.out.println("Account Holder's Name: " + c.getName() + "\nCurrent Balance: "
					+ c.getWallet().getBalance() + "\n");
		} catch (MobileNoNotFoundException e) {
			System.out.println("\n" + e);
		}

	}

	private static void withdraw() {

		System.out.println("Enter mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}
		System.out.println("Enter the amount for withdraw: ");
		long amount = sc.nextLong();
		BigDecimal amount1 = new BigDecimal(amount);
		try {
			Customer c = wsi.withdrawAmount(mobileno1, amount1);
			System.out.println("Account Holder's Name: " + c.getName() + "\nUpdated Balance: "
					+ c.getWallet().getBalance() + "\n");
		} catch (InsufficientBalanceException | MobileNoNotFoundException e) {
			System.out.println("\n" + e);
		}
	}

	private static void deposit() {

		System.out.println("Enter mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}
		System.out.println("Enter the amount for Deposit: ");
		long amount = sc.nextLong();
		BigDecimal amount1 = new BigDecimal(amount);
		try {
			Customer c = wsi.depositAmount(mobileno1, amount1);
			System.out.println("Account Holder's Name: " + c.getName() + "\nUpdated Balance: "
					+ c.getWallet().getBalance() + "\n");
		} catch (MobileNoNotFoundException e) {
			System.out.println("\n" + e);
		}

	}

	private static void fundTransfer() {

		System.out.println("Enter your mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}

		System.out.println("Enter the other's mobile Number: ");
		String mobileno2 = sc.next();
		boolean validnum2 = nullValidation(mobileno2);
		while (validnum2) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno2 = sc.next();
			validnum2 = nullValidation(mobileno2);
		}
		boolean valid2 = validateNum(mobileno2);
		while (!valid2) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno2 = sc.next();
			valid2 = validateNum(mobileno2);
		}

		System.out.println("Enter the amount for Fund Transfer: ");
		long amount = sc.nextLong();
		BigDecimal amount1 = new BigDecimal(amount);
		try {
			Customer[] c = wsi.fundTransfer(mobileno1, mobileno2, amount1);
			System.out.println(
					"Sender's Name:" + c[0].getName() + "\nSender's Updated Balance: " + c[0].getWallet().getBalance());
			System.out.println("\nRecipient's Name:" + c[1].getName() + "\nRecipient's Updated Balance: "
					+ c[1].getWallet().getBalance() + "\n");
		} catch (InsufficientBalanceException | MobileNoNotFoundException e) {
			System.out.println("\n" + e);
		}

	}

	private static void printlastTransaction() {
		System.out.println("Enter mobile Number: ");
		String mobileno1 = sc.next();
		boolean validnum = nullValidation(mobileno1);
		while (validnum) {
			System.out.println("This Field cannot be empty\nPlease enter the valid Number: ");
			mobileno1 = sc.next();
			validnum = nullValidation(mobileno1);
		}
		boolean valid = validateNum(mobileno1);
		while (!valid) {
			System.out.println("Enter the valid 10-digits of mobileNo.: ");
			mobileno1 = sc.next();
			valid = validateNum(mobileno1);
		}
		try {
			System.out.println(wsi.printlastTransaction(mobileno1));
		} catch (MobileNoNotFoundException | NoTransactionOccurException e) {
			System.out.println("\n" + e);
		}

	}

	private static boolean validateNum(String mobileno) {
		if (mobileno.length() == 10) {
			return true;
		}
		return false;
	}

	private static boolean nullValidation(String n) {
		if (n == null) {
			return true;
		}
		return false;
	}
}
