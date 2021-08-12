package com.majorproject.internetbanking.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.majorproject.internetbanking.ibservice.InterBankService;
import com.majorproject.internetbanking.userbean.Credentials;
import com.majorproject.internetbanking.userbean.UserBean;

@RestController
public class IBController {
	
	@Autowired
	InterBankService inter;
	
	@GetMapping("/home")
	public String homePage() {
		return "Welcome to International Internet Bank";
	}
	
	@PostMapping("/register")
	public UserBean newUser(@RequestBody UserBean user) throws SQLException {
		return this.inter.addUser(user);
	}
	
	@PostMapping("/login")
	public String checkUser(@RequestBody Credentials credentials) throws SQLException {
		if(this.inter.checkUser(credentials)==null)
			return "Invalid Username or Password! Please try again!";
		else
			return "Login Successfull!";
	}
	
	@PostMapping("/Mainpage/view/{username}")
	public UserBean viewUser(@PathVariable String username) throws SQLException {
		return this.inter.viewUser(username);
	}
	
}
