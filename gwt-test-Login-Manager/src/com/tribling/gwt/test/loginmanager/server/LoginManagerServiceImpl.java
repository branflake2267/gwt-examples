package com.tribling.gwt.test.loginmanager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.loginmanager.client.Account;
import com.tribling.gwt.test.loginmanager.client.LoginManagerService;
import com.tribling.gwt.test.loginmanager.client.SignInStatus;

public class LoginManagerServiceImpl extends RemoteServiceServlet implements LoginManagerService {

	public LoginManagerServiceImpl() {
	}
	
	/**
	 * Sign in and get session id
	 * @param UserName
	 * @param Password
	 * @return
	 */
	public SignInStatus processSignIn(String UserName, String Password) {
		SignInStatus sis;
		
		try {
			DB_SignIn db = new DB_SignIn();
			sis = db.processSignIn(UserName, Password);
			return sis;
		 } catch(Exception e) {
			 System.out.println("SignInStatus processSignIn Error: ");
			 e.printStackTrace();
		 }
		 
		 return null;
	}
	
	/**
	 * Check to see if the session is still legal and can use it for this user
	 * @param SessionID
	 * @return
	 */
	public SignInStatus checkSessionIsStillLegal(String SessionID) {
		SignInStatus sis;
		
		try {
			DB_SignIn db = new DB_SignIn();
			sis = db.checkSessionIsStillLegal(SessionID);
			return sis;
		 } catch(Exception e) {
			 System.out.println("SignInStatus checkSessionIsStillLegal Error: ");
			 e.printStackTrace();
		 }
		
		return null;
	}
	
	/**
	 * Save User Account information
	 * @param account
	 * @return
	 */
	public Account saveAccount(Account account) {
		
		System.out.println("Processing Account:");
		
		DB_Account dba = new DB_Account();
		account = dba.saveAccount(account);
		
		return account;
	}
	
	
	  /**
	   * debug output
	   * 
	   * Write the serialized response out to stdout. This is a very unusual thing
	   * to do, but it allows us to create a static file version of the response
	   * without deploying a servlet.
	   */
	  protected void onAfterResponseSerialized(String serializedResponse) {
		  System.out.println(serializedResponse);
	  }
	
}
