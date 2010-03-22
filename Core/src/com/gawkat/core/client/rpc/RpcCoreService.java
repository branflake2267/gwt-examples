package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.ChangePasswordData;
import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.client.account.thing.ThingsData;
import com.gawkat.core.client.account.thing.ownership.ThingLinksData;
import com.gawkat.core.client.account.thing.ownership.ThingLinkFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
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
  
  public boolean changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData);
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken);
  
  
  public boolean setDefaults(OAuthTokenData accessToken, int defaultType);
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter);
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData);
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData);
  
  
  public ThingsData getThings(OAuthTokenData accessToken, ThingFilterData filter);
  public ThingData saveThing(OAuthTokenData accessToken, ThingFilterData filter, ThingData thingData);
  public ThingsData saveThings(OAuthTokenData accessToken, ThingFilterData filter, ThingData[] thingData);
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData);
  
  
  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter);
  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter, ThingStuffTypeData[] thingStuffTypeData);
  public boolean deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData);
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter);
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData);
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId);
  
  public ThingLinksData getHierarchy(OAuthTokenData accessToken, ThingLinkFilterData filter);
}
