package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcCoreServiceAsync {

	public void testMethod(String s, AsyncCallback<String> callback);
	
	public void requestToken(OAuthTokenData tokenData, AsyncCallback<OAuthTokenData> callback);
	
  public void createUser(UserData userData, AsyncCallback<UserData> callback);
  
  public void doesUserNameExist(UserData userData, AsyncCallback<UserData> callback);
  
  public void forgotPassword(UserData userData, AsyncCallback<UserData> callback);
  
  public void getUserAccessToken(OAuthTokenData appAccessToken, AsyncCallback<OAuthTokenData> callback);
  
  
  public void getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, AsyncCallback<ThingTypesData> callback);
  
  public void setDefaults(OAuthTokenData accessToken, int defaultType, AsyncCallback<Boolean> callback);
  
  public void saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData, AsyncCallback<ThingTypesData> callback);
  
  public void deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData, AsyncCallback<Boolean> callback);
}
