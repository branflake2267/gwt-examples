package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SignInStatus implements IsSerializable{

	private String SessionID = null;
	private String DisplayError;
	private String UserName;
	
	public SignInStatus() {
	}
	
	public void setSessionID(String SessionID) {
		this.SessionID = SessionID;
	}
	
	public void setDisplayError(String DisplayError) {
		this.DisplayError = DisplayError;
	}
	
	public String getSessionID() {
		return this.SessionID;
	}
	
	public String getDisplayError() {
		return this.DisplayError;
	}

}
