package com.gawkat.gwt.oauth.client.rpc;

import com.gawkat.gwt.oauth.client.account.UserData;
import com.gawkat.gwt.oauth.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.rpc.RemoteService;

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
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken);
}
