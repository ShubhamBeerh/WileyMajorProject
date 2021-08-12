package com.majorproject.internetbanking.userbean;

public class UserBean {
	String username;
	String name;
	String address;
	String email;
	long mobileNo;
	long aadharCard;
	String panCard;
	double accBalance;
	String password;
	String accountTypes;
	
	public UserBean(String username, String password, String name, String address, String email, long mobileNo, long aadharCard,
			String panCard, double accBalance, String accountTypes) {
		super();
		this.username = username;
		this.address = address;
		this.email = email;
		this.mobileNo = mobileNo;
		this.aadharCard = aadharCard;
		this.panCard = panCard;
		this.accBalance = accBalance;
		this.password=password;
		this.name=name;
		this.accountTypes=accountTypes;
	}
	
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public long getAadharCard() {
		return aadharCard;
	}
	public void setAadharCard(long aadharCard) {
		this.aadharCard = aadharCard;
	}
	public String getPanCard() {
		return panCard;
	}
	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}
	public double getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(double accBalance) {
		this.accBalance = accBalance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(String accountTypes) {
		this.accountTypes = accountTypes;
	}

	@Override
	public String toString() {
		return "UserBean [username=" + username + ", name=" + name + ", address=" + address + ", email=" + email
				+ ", mobileNo=" + mobileNo + ", aadharCard=" + aadharCard + ", panCard=" + panCard + ", accBalance="
				+ accBalance + ", accountTypes=" + accountTypes + "]";
	}
	
}
