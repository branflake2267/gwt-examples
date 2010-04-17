package com.gawkat.core.client.rpc;

import com.gawkat.core.client.account.ChangePasswordData;
import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.client.account.thing.ThingsData;
import com.gawkat.core.client.account.thing.ownership.ThingLinkFilterData;
import com.gawkat.core.client.account.thing.ownership.ThingLinksData;
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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcCoreServiceAsync {

	public void testMethod(String s, AsyncCallback<String> callback);
	
	public void requestToken(OAuthTokenData tokenData, AsyncCallback<OAuthTokenData> callback);
	
  public void createUser(UserData userData, AsyncCallback<UserData> callback);
  
  public void doesUserNameExist(UserData userData, AsyncCallback<UserData> callback);
  
  public void forgotPassword(UserData userData, AsyncCallback<UserData> callback);
  
  public void changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData, AsyncCallback<Boolean> callback);
  
  public void getUserAccessToken(OAuthTokenData appAccessToken, AsyncCallback<OAuthTokenData> callback);
  
  
  public void setDefaults(OAuthTokenData accessToken, int defaultType, AsyncCallback<Boolean> callback);
  public void getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, AsyncCallback<ThingTypesData> callback);
  public void saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData, AsyncCallback<ThingTypesData> callback);
  public void deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData, AsyncCallback<Boolean> callback);
  

  public void getThings(OAuthTokenData accessToken, ThingFilterData filter, AsyncCallback<ThingsData> callback);
  public void saveThing(OAuthTokenData accessToken, ThingFilterData filter, ThingData thingData, AsyncCallback<ThingData> callback);
  public void saveThings(OAuthTokenData accessToken, ThingFilterData filter, ThingData[] thingData, AsyncCallback<ThingsData> callback);
  public void deleteThing(OAuthTokenData accessToken, ThingData thingData, AsyncCallback<Boolean> callback);
  
  
  public void getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter, AsyncCallback<ThingStuffTypesData> callback);
  public void saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter, ThingStuffTypeData[] thingStuffTypeData, AsyncCallback<ThingStuffTypesData> callback);
  public void deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData, AsyncCallback<Boolean> callback);
  public void deleteThingStuffAboutData(OAuthTokenData accessToken, long thingStuffAboutId, AsyncCallback<Boolean> callback);
  
  
  public void getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, AsyncCallback<ThingStuffsData> callback);
  public void saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData, AsyncCallback<ThingStuffsData> callback);
  public void deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId, AsyncCallback<Boolean> callback);
  
  public void getHierarchy(OAuthTokenData accessToken, ThingLinkFilterData filter, AsyncCallback<ThingLinksData> callback);
}
