package com.gawkat.gwt.test.oauth.client.rpc;

import com.gawkat.gwt.test.oauth.client.account.UserData;
import com.gawkat.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tribling.gwt.test.oauth.server.db.Db_User;

public interface RpcServiceAsync {

	/**
	 * example rpc request with the callback
	 * 
	 * @param s
	 * @param callback
	 */
	public void testMethod(String s, AsyncCallback<String> callback);
	
	/**
	 * A. grant request token?
	 * 
	 * @param tokenData
	 * @param callback
	 */
	public void requestToken(OAuthTokenData tokenData, AsyncCallback<OAuthTokenData> callback);
	
	
  public void createUser(UserData userData, AsyncCallback<UserData> callback);
  
  public void isUserNameExist(UserData userData, AsyncCallback<UserData> callback);
  
  public void forgotPassword(UserData userData, AsyncCallback<UserData> callback);
}
