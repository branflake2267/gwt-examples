package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginManagerServiceAsync {

	
	/**
	 * process the login
	 * @param UserName
	 * @param Password
	 * @param callback
	 */
	public void processSignIn(String UserName, String Password, AsyncCallback callback);
	
	/**
	 * check if the session is still legal to use
	 * @param SessionID
	 * @param callback
	 */
	public void checkSessionIsStillLegal(String SessionID, AsyncCallback callback);
	
}
