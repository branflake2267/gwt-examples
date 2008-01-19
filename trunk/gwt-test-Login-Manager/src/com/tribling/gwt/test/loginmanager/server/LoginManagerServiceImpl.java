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
		
		DB_SignIn db = new DB_SignIn();
		SignInStatus sis = db.processSignIn(UserName, Password);
		
		return sis;
	}
	
	/**
	 * Check to see if the session is still legal and can use it for this user
	 * @param SessionID
	 * @return
	 */
	public SignInStatus checkSessionIsStillLegal(String SessionID) {
		
		DB_SignIn db = new DB_SignIn();
		SignInStatus sis = db.checkSessionIsStillLegal(SessionID);
		
		return sis;
	}
	

	
}
