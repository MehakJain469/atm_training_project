package entity;

import java.sql.Date;

public class Statement {
	String accountNumber;
	int initialAmount;
	int closingAmount;
	Date transactionTime;
	
	@Override
	public String toString() {
		return "Statement [accountNumber=" + accountNumber + ", initialAmount=" + initialAmount + ", closingAmount="
				+ closingAmount + ", transactionTime=" + transactionTime + "]";
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(int initialAmount) {
		this.initialAmount = initialAmount;
	}
	public int getClosingAmount() {
		return closingAmount;
	}
	public void setClosingAmount(int closingAmount) {
		this.closingAmount = closingAmount;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public Statement(String accountNumber, int initialAmount, int closingAmount, Date transactionTime) {
		super();
		this.accountNumber = accountNumber;
		this.initialAmount = initialAmount;
		this.closingAmount = closingAmount;
		this.transactionTime = transactionTime;
	}
}