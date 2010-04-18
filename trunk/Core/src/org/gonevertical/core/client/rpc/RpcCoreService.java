package org.gonevertical.core.client.rpc;

import org.gonevertical.core.client.account.ChangePasswordData;
import org.gonevertical.core.client.account.UserData;
import org.gonevertical.core.client.account.thing.ThingData;
import org.gonevertical.core.client.account.thing.ThingFilterData;
import org.gonevertical.core.client.account.thing.ThingsData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffFilterData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.client.account.thingtype.ThingTypeData;
import org.gonevertical.core.client.account.thingtype.ThingTypeFilterData;
import org.gonevertical.core.client.account.thingtype.ThingTypesData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.feedback.FeedbackData;

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
  public boolean deleteThingStuffAboutData(OAuthTokenData accessToken, long thingStuffAboutId);
 
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter);
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData);
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId);
  
  //public ThingLinksData getHierarchy(OAuthTokenData accessToken, ThingLinkFilterData filter);
  
  public boolean saveFeedBack(FeedbackData feedbackData);
}
