package org.gonevertical.core.client.rpc;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.account.AccountData;
import org.gonevertical.core.client.ui.admin.join.JoinDataFilter;
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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcCoreServiceAsync {

	// login session system
	public void requestToken(OAuthTokenData tokenData, AsyncCallback<OAuthTokenData> callback);
  public void createUser(UserData userData, AsyncCallback<UserData> callback);
  public void doesUserNameExist(UserData userData, AsyncCallback<UserData> callback);
  public void forgotPassword(UserData userData, AsyncCallback<UserData> callback);
  public void changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData, AsyncCallback<Boolean> callback);
  public void getUserAccessToken(OAuthTokenData appAccessToken, AsyncCallback<OAuthTokenData> callback);
  
  // setup defaults
  public void setDefaults(OAuthTokenData accessToken, int defaultType, AsyncCallback<Boolean> callback);
  
  // thing types
  public void getThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter, AsyncCallback<ThingTypesData> callback);
  public void saveThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter, ThingTypeData[] thingTypeData, AsyncCallback<ThingTypesData> callback);
  public void deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData, AsyncCallback<Boolean> callback);
  
  // things
  public void getThings(OAuthTokenData accessToken, ThingDataFilter filter, AsyncCallback<ThingsData> callback);
  public void saveThing(OAuthTokenData accessToken, ThingDataFilter filter, ThingData thingData, AsyncCallback<ThingData> callback);
  public void saveThings(OAuthTokenData accessToken, ThingDataFilter filter, ThingData[] thingData, AsyncCallback<ThingsData> callback);
  public void deleteThing(OAuthTokenData accessToken, ThingData thingData, AsyncCallback<Boolean> callback);
  
  // thing types
  public void getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter, AsyncCallback<ThingStuffTypesData> callback);
  public void saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter, ThingStuffTypeData[] stuffTypeData, AsyncCallback<ThingStuffTypesData> callback);
  public void deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData stuffTypeData, AsyncCallback<Boolean> callback);
 
  // thing stuff data
  public void getThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter, AsyncCallback<ThingStuffsData> callback);
  public void saveThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter, ThingStuffData[] stuffData, AsyncCallback<ThingStuffsData> callback);
  public void deleteThingStuffData(OAuthTokenData accessToken, long stuffId, AsyncCallback<Boolean> callback);
    
  // account data
  public void getAccountData(OAuthTokenData accessToken, long thingId, AsyncCallback<AccountData> callback);
  public void saveAccountData(OAuthTokenData accessToken, ThingDataFilter filter, AccountData accountData, AsyncCallback<AccountData> callback);
  public void getWidgetAttributes(OAuthTokenData accessToken, WidgetAttrDataFilter widgetAttrDataFilter, AsyncCallback<WidgetAttrData> callback);
  
  // misc
  public void saveFeedBack(FeedbackData feedbackData, AsyncCallback<Boolean> callback);
  public void buildDataJoin(OAuthTokenData accessToken, JoinDataFilter filter, AsyncCallback<Boolean> callback);
  
  
  
  
}
