package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Account implements IsSerializable {

	//use this to tell the server what the clients time is
	public int TimeStamp;
	
	//use this once the account is created
	public String SessionID;

	//use this to send back any errors
	public String DisplayError;
	
	//user account info
	public String FirstName;
	public String LastName;
	public String UserName;
	public String Password;
	
	/** 
	 * constructor
	 */
	public Account() {
	}
	
	public void setTimeStamp(int TimeStamp) {
		this.TimeStamp = TimeStamp;
	}

	public void setSessionID(String SessionID) {
		this.SessionID = SessionID;
	}
	
	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}
	
	public void setLastName(String LastName) {
		this.LastName = LastName;
	}
	
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	
	public void setPassword(String Password) {
		this.Password = Password;
	}
	
	public void setDisplayError(String DisplayError) {
		this.DisplayError = DisplayError;
	}
	
	
	public int getTimeStamp() {
		return this.TimeStamp;
	}
	
	public String getSessionID() {
		return this.SessionID;
	}
	
	public String getFirstName() {
		return this.FirstName;
	}
	
	public String getLastName() {
		return this.LastName;
	}
	
	public String getUserName() {
		return this.UserName;
	}
	
	public String getPassword() {
		return this.Password;
	}
	
	public String getDisplayError() {
		return this.DisplayError;
	}
	
	
	
}
