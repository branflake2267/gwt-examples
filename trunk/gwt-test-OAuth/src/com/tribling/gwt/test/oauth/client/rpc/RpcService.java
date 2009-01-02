package com.tribling.gwt.test.oauth.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.tribling.gwt.test.oauth.client.account.UserData;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

public interface RpcService extends RemoteService {

	/**
	 * example rpc request
	 * 
	 * @param s
	 * @return
	 */
	public String testMethod(String s);

	/**
	 * A. grant request token?
	 * 
	 * @param tokenData
	 * @return
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData);
	
	
  public UserData createUser(UserData userData);
  
  public UserData isUserNameExist(UserData userData);
  
  public UserData forgotPassword(UserData userData);
}
