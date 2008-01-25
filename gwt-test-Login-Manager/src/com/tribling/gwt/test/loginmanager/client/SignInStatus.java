package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SignInStatus implements IsSerializable {

	//set to public if tomcat permssions are set to all
	private String SessionID = null;
	private String DisplayError = null;
	private String UserName = null;
	
	public SignInStatus() {
	}
	
	public void setSessionID(String SessionID) {
		this.SessionID = SessionID;
	}
	
	public void setDisplayError(String DisplayError) {
		this.DisplayError = DisplayError;
	}
	
	public void setUserName(String UserName) {
		this.UserName = UserName; 
	}
	
	public String getSessionID() {
		return this.SessionID;
	}
	
	public String getDisplayError() {
		return this.DisplayError;
	}
	
	public String getUserName() {
		return this.UserName;
	}

}
