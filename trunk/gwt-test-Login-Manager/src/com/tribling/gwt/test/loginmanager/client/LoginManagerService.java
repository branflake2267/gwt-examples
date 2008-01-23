package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.RemoteService;


/**
 * The interface for the RPC server endpoint to get GoneVertical
 * information.
 */
public interface LoginManagerService extends RemoteService {

	
	
	/**
	 * sign in and get session id and display error
	 */
	public SignInStatus processSignIn(String UserName, String Password);
	
	
	public SignInStatus checkSessionIsStillLegal(String SessionID);


	public Account saveAccount(Account account);
}
