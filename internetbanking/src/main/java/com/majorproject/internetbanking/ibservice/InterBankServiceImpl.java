package com.majorproject.internetbanking.ibservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.majorproject.internetbanking.userbean.Credentials;
import com.majorproject.internetbanking.userbean.UserBean;

@Service
public class InterBankServiceImpl implements InterBankService {
	String url="jdbc:mysql://localhost:3306/mydb";
	String dbuser="root";
	String dbpassword="cov@lent12";
	
	@Override
	public UserBean addUser(UserBean user) throws SQLException {
		// TODO Auto-generated method stub
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getName();
		String address = user.getAddress();
		String email = user.getEmail();
		long mobileNo = user.getMobileNo();
		long aadharNo = user.getAadharCard();
		String panCard = user.getPanCard();
		double accountBal = user.getAccBalance();
		String accountTypes = user.getAccountTypes();
		Connection con = DriverManager.getConnection(url,dbuser,dbpassword);
		System.out.println("Connection successfully made!!!");
		String query = "insert into user values (null,?,?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(query);
		//User Table
		st.setString(1,username);
		st.setString(2,name);
		st.setString(3,address);
		st.setString(4,email);
		st.setLong(5,mobileNo);
		st.setLong(6,aadharNo);
		st.setString(7,panCard);
		int row = st.executeUpdate();
		
		//Accounts Table
		Random rand = new Random();
		//10001
		//0+100000 = 100000
		//99999+100000 = 199999
		int number = rand.nextInt(99999)+100000;
		String sixDig = String.format("%06d",number);
		int sixDigNum = Integer.parseInt(sixDig);
		String bankName = "IIB";
		String IFSC = bankName+sixDigNum;
		query = "select UserID from user where AadharNumber = ?";
		st = con.prepareStatement(query);
		st.setLong(1,aadharNo);
		System.out.println("!!!! Statement ====="+st);
		ResultSet rs = st.executeQuery();
		int userID=0;
		while(rs.next()) {
		System.out.println("!!!! Result Set value : "+rs.getInt(1));
		userID = rs.getInt(1);
		}
		query = "insert into accounts values(?,?,?,?,?)";
		st = con.prepareStatement(query);
		st.setInt(1,userID);
		st.setInt(2,sixDigNum);
		st.setString(3,IFSC);
		st.setString(4,randString());
		st.setString(5,"Rs."+accountBal);
		row = st.executeUpdate();
		
		//User Data Table
		query = "insert into UserData values (?,?,?)";
		st = con.prepareStatement(query);
		st.setInt(1,userID);
		st.setString(2,username);
		st.setString(3,password);
		st.executeUpdate();
		
		//Type Table 
		query = "select TypeCode from accounts where User_UserID= ?";
		st = con.prepareStatement(query);
		st.setInt(1,userID);
		String typeCode = "";
		rs = st.executeQuery();
		while(rs.next()) {
			typeCode = rs.getString(1);
		}
		
		String[] typeCodeArr = accountTypes.split(",");
		query = "insert into TypeTable values(?,?)";
		st = con.prepareStatement(query);
		for(int i=0;i<typeCodeArr.length;i++) {
			st.setString(1,typeCode);
			st.setString(2,typeCodeArr[i]);
			st.addBatch();
		}
		st.executeBatch();
		st.close();
		con.close();
		return user;
	}
	
	@Override
	public Credentials checkUser(Credentials credentials) throws SQLException {
		Connection con = DriverManager.getConnection(url,dbuser,dbpassword);
		String query = "select * from UserData";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			if(rs.getString(2).equals(credentials.getUsername())) {
				if(rs.getString(3).equals(credentials.getPassword()))
					return credentials;
				else
					return null;
			}
		}
		return null;
		
	}
	
	@Override
	public UserBean viewUser(String username) throws SQLException {
		// TODO Auto-generated method stub
		UserBean user=null;
		Connection con = DriverManager.getConnection(url,dbuser,dbpassword);
		String query = "select * from user";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		//UserBean Object creation
		while(rs.next()) {
			if(username.equals(rs.getString(2))) {
				user= new UserBean(rs.getString(2),"•••••",rs.getString(3),rs.getString(4),rs.getString(5),rs.getLong(6),rs.getLong(7),rs.getString(8),0.0,null);
				break;
			}
		}
		
		//Setting Account Types to the already created UserBean object
		query = "select AccountType from TypeTable where TypeCode = (select TypeCode from Accounts where User_UserID = (select UserID from User where Username = ?";
		PreparedStatement prep = con.prepareStatement(query);
		prep.setString(1,username);
		rs = prep.executeQuery();
		StringBuilder accTypes = new StringBuilder();
		while(rs.next()) {
			accTypes.append(rs.getString(1)+",");
		}
		String accountTypes = accTypes.substring(0, (accTypes.length()-1));
		user.setAccountTypes(accountTypes);
		
		//Setting Balance to the already created UserBean object
		query= "select Balance from Accounts where User_UserID=(select UserID from User where Username = ?";
		prep = con.prepareStatement(query);
		rs=prep.executeQuery();
		double balance=0.0;
		while(rs.next()) {
			balance = rs.getDouble(1);
		}
		user.setAccBalance(balance);
		
		return user;
	}
	
	
	public String randString() {
		   String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 4) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	    }

}
