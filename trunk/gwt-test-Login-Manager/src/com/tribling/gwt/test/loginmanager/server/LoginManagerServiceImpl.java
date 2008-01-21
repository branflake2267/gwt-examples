package com.tribling.gwt.test.loginmanager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
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
	

	
}
