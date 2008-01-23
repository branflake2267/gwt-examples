package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginManagerServiceAsync {

	
	/**
	 * process the login
	 * @param UserName
	 * @param Password
	 * @param callback
	 */
	public void processSignIn(String UserName, String Password, AsyncCallback callback_processSignIn);
	
	/**
	 * check if the session is still legal to use
	 * @param SessionID
	 * @param callback
	 */
	public void checkSessionIsStillLegal(String SessionID, AsyncCallback callback_checkSessionIsStillLegal);
	
	
	/**
	 * Save Account
	 * @param account
	 * @param callback_SaveAccount
	 */
	public void SaveAccount(Account account, AsyncCallback callback_SaveAccount);
}
