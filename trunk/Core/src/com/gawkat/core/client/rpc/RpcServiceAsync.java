package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
  
  public void getUserAccessToken(OAuthTokenData appAccessToken, AsyncCallback<OAuthTokenData> callback);
  
  public void getThingTypes(ThingTypeFilterData filter, AsyncCallback<ThingTypeData[]> callback);
  
  public void setDefaults(int defaultType, AsyncCallback<Boolean> callback);
  
}
