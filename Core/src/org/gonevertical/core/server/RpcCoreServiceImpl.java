package org.gonevertical.core.server;

import java.util.logging.Logger;

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
import org.gonevertical.core.client.rpc.RpcCoreService;
import org.gonevertical.core.client.ui.feedback.FeedbackData;
import org.gonevertical.core.server.db.Db_Feedback;
import org.gonevertical.core.server.db.Db_Thing;
import org.gonevertical.core.server.db.Db_ThingStuff;
import org.gonevertical.core.server.db.Db_ThingStuffType;
import org.gonevertical.core.server.db.Db_ThingType;
import org.gonevertical.core.server.db.Db_User;
import org.gonevertical.core.server.db.SetDefaults;
import org.gonevertical.core.server.db.oauth.OAuthServer;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RpcCoreServiceImpl extends RemoteServiceServlet implements RpcCoreService {

	private static final Logger log = Logger.getLogger(RpcCoreServiceImpl.class.getName());
	
	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * store server persistence items
	 */
	private ServerPersistence sp = new ServerPersistence();
	
	/**
	 * test method for rpc
	 */
	public String testMethod(String s) {
	  sp.start(getThreadLocalRequest());
		s += " was modified on the server.";
		sp.end();
		return s;
	}

	/**
	 * A. ->(B.?) grant request token?
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
	  sp.start(getThreadLocalRequest());
		OAuthServer oauth = new OAuthServer(sp);
		OAuthTokenData r = oauth.requestToken(tokenData);
		sp.end();
		return r;
	}

  public UserData createUser(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.createUser(userData);
    sp.end();
    return r;
  }
  
  public UserData doesUserNameExist(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.getUserExist(userData);
    sp.end();
    return r;
  }
  
  public UserData forgotPassword(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.forgotPassword(userData);
    sp.end();
    return r;
  }
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken) {
    sp.start(getThreadLocalRequest());
    OAuthServer oauth = new OAuthServer(sp);
    OAuthTokenData rtnToken = oauth.getUserAccessToken(appAccessToken);
    sp.end();
    return rtnToken;
  }
	
  
  
  /**
   * get thing types
   * 
   * @param filter
   * @return
   */
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter) {
    sp.start(getThreadLocalRequest());
    Db_ThingType thingType = new Db_ThingType(sp);
    ThingTypesData r = thingType.getThingTypes(filter);
    sp.end();
    return r;
  }
  
  /**
   * set default items in db
   */
  public boolean setDefaults(OAuthTokenData accessToken, int defaultType) {
    sp.start(getThreadLocalRequest());
    SetDefaults sd = new SetDefaults(sp);
    boolean r = sd.setDefaults(defaultType);
    sp.end();
    return r;
  }
  
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingType t = new Db_ThingType(sp);
    ThingTypesData r = t.saveThingTypes(filter, thingTypeData);
    sp.end();
    return r;
  }
  
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingType t = new Db_ThingType(sp);
    boolean r = t.delete(accessToken, thingTypeData);
    sp.end();
    return r;
  }
  
  public ThingsData getThings(OAuthTokenData accessToken, ThingFilterData filter) {
    sp.start(getThreadLocalRequest());
    Db_Thing t = new Db_Thing(sp);
    ThingsData r = t.getThings(accessToken, filter);
    sp.end();
    return r;
  }
  
  public ThingData saveThing(OAuthTokenData accessToken, ThingFilterData filter, ThingData thingData) {
    sp.start(getThreadLocalRequest());
    Db_Thing t = new Db_Thing(sp);
    ThingData r = t.saveThing(accessToken, filter, thingData);
    sp.end();
    return r;
  }
  
  public ThingsData saveThings(OAuthTokenData accessToken, ThingFilterData filter, ThingData[] thingData) {
    sp.start(getThreadLocalRequest());
    Db_Thing t = new Db_Thing(sp);
    ThingsData r = t.saveThings(accessToken, filter, thingData);
    sp.end();
    return r;
  }
  
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData) {
    sp.start(getThreadLocalRequest());
    Db_Thing t = new Db_Thing(sp);
    boolean r = t.deleteThing(accessToken, thingData);
    sp.end();
    return r;
  }

  public boolean deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    boolean r = t.delete(accessToken, thingStuffTypeData);
    sp.end();
    return r;
  }

  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData r = t.getThingStuffTypes(accessToken, filter);
    sp.end();
    return r;
  }

  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter, ThingStuffTypeData[] thingStuffTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData r = t.saveThingStuffTypes(accessToken, filter, thingStuffTypeData);
    sp.end();
    return r;
  }
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuff db = new Db_ThingStuff(sp);
    ThingStuffsData r = db.getThingStuffData(accessToken, filter);
    sp.end();
    return r;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuff db = new Db_ThingStuff(sp);
    ThingStuffsData r = db.saveThingStuffData(accessToken, filter, thingStuffData);
    sp.end();
    return r;
  }
  
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId) {
    sp.start(getThreadLocalRequest());
    Db_ThingStuff db = new Db_ThingStuff(sp);
    boolean r = db.deleteThingStuffData(accessToken, thingStuffId);
    sp.end();
    return r;
  }
  
  public boolean changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    boolean r = db.changePassword(accessToken, changePassswordData);
    sp.end();
    return r;
  }

  public boolean deleteThingStuffAboutData(OAuthTokenData accessToken, long thingStuffAboutId) {
  	sp.start(getThreadLocalRequest());
  	Db_ThingStuff db = new Db_ThingStuff(sp);
  	boolean r = db.deleteThingStuffAboutData(accessToken, thingStuffAboutId);
  	sp.end();
	  return r;
  }

  public boolean saveFeedBack(FeedbackData feedbackData) {
  	sp.start(getThreadLocalRequest());
	  Db_Feedback db = new Db_Feedback(sp);
	  boolean r = db.sendFeedback(feedbackData);
	  sp.end();
	  return r;
  }

}
