package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpccore")
public interface RpcCoreService extends RemoteService {

	public String testMethod(String s);

	public OAuthTokenData requestToken(OAuthTokenData tokenData);
	
  public UserData createUser(UserData userData);
  
  public UserData doesUserNameExist(UserData userData);
  
  public UserData forgotPassword(UserData userData);
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken);
  
  
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter);
  
  public boolean setDefaults(OAuthTokenData accessToken, int defaultType);
  
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData);
  
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData);
}
