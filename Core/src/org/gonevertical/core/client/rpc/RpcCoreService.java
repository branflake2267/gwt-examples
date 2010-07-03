package org.gonevertical.core.client.rpc;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.account.AccountData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.ui.admin.thing.ThingsData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;
import org.gonevertical.core.client.ui.feedback.FeedbackData;
import org.gonevertical.core.client.ui.login.ChangePasswordData;
import org.gonevertical.core.client.ui.login.UserData;
import org.gonevertical.core.client.widget.WidgetAttrData;
import org.gonevertical.core.client.widget.WidgetAttrDataFilter;

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
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter);
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter, ThingTypeData[] thingTypeData);
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData);
  
  
  public ThingsData getThings(OAuthTokenData accessToken, ThingDataFilter filter);
  public ThingData saveThing(OAuthTokenData accessToken, ThingDataFilter filter, ThingData thingData);
  public ThingsData saveThings(OAuthTokenData accessToken, ThingDataFilter filter, ThingData[] thingData);
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData);
  
  
  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter);
  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter, ThingStuffTypeData[] thingStuffTypeData);
  public boolean deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData);
  public boolean deleteThingStuffAboutData(OAuthTokenData accessToken, long thingStuffAboutId);
 
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter);
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter, ThingStuffData[] thingStuffData);
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId);
  
  //public ThingLinksData getHierarchy(OAuthTokenData accessToken, ThingLinkFilterData filter);
  
  public boolean saveFeedBack(FeedbackData feedbackData);
  
  public WidgetAttrData getWidgetAttributes(OAuthTokenData accessToken, WidgetAttrDataFilter widgetAttrDataFilter);
  
  // account data
  public AccountData getAccountData(OAuthTokenData accessToken, long thingId);
  public AccountData saveAccountData(OAuthTokenData accessToken, ThingDataFilter filter, AccountData accountData);
}
