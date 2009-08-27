package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
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
  
  public ThingTypeData[] getThingTypes(ThingTypeFilterData filter);
  
  public boolean setDefaults(int defaultType);
  
}
