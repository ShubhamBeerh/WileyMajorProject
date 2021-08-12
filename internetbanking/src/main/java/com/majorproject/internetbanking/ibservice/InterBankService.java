package com.majorproject.internetbanking.ibservice;

import java.sql.SQLException;

import com.majorproject.internetbanking.userbean.Credentials;
import com.majorproject.internetbanking.userbean.UserBean;

public interface InterBankService {
	public UserBean addUser(UserBean user) throws SQLException;
	
	public Credentials checkUser(Credentials credentials) throws SQLException;
	
	public UserBean viewUser(String username) throws SQLException;
}
