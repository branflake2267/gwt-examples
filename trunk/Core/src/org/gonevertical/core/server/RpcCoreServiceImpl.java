package org.gonevertical.core.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.rpc.RpcCoreService;
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
import org.gonevertical.core.server.db.Db_Feedback;
import org.gonevertical.core.server.db.Db_Profile;
import org.gonevertical.core.server.db.Db_Thing;
import org.gonevertical.core.server.db.Db_ThingStuff;
import org.gonevertical.core.server.db.Db_ThingStuffType;
import org.gonevertical.core.server.db.Db_ThingType;
import org.gonevertical.core.server.db.Db_User;
import org.gonevertical.core.server.db.Db_WidgetAttr;
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
	  sp.start(getThreadLocalRequest(), null);
		s += " was modified on the server.";
		sp.end();
		return s;
	}

	/**
	 * A. ->(B.?) grant request token?
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
	  sp.start(getThreadLocalRequest(), tokenData);
		OAuthTokenData r = null;
    try {
	    OAuthServer oauth = new OAuthServer(sp);
	    r = oauth.requestToken(tokenData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.requestToken(): Error:", e);
    }
		sp.end();
		return r;
	}

  public UserData createUser(UserData userData) {
    sp.start(getThreadLocalRequest(), null);
    UserData r = null;
    try {
	    Db_User db = new Db_User(sp);
	    r = db.createUser(userData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.createUser(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public UserData doesUserNameExist(UserData userData) {
    sp.start(getThreadLocalRequest(), null);
    UserData r = null;
    try {
	    Db_User db = new Db_User(sp);
	    r = db.getUserExist(userData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.doesUserNameExist(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public UserData forgotPassword(UserData userData) {
    sp.start(getThreadLocalRequest(), null);
    Db_User db = new Db_User(sp);
    UserData r = db.forgotPassword(userData);
    sp.end();
    return r;
  }
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken) {
    sp.start(getThreadLocalRequest(), appAccessToken);
    OAuthTokenData rtnToken = null;
    try {
	    OAuthServer oauth = new OAuthServer(sp);
	    rtnToken = oauth.getUserAccessToken(appAccessToken);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getUserAccessToken(): Error:", e);
    }
    sp.end();
    return rtnToken;
  }
	
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingTypesData r = null;
    try {
	    Db_ThingType thingType = new Db_ThingType(sp);
	    r = thingType.getThingTypes(filter);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getThingTypes(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  /**
   * set default items in db
   */
  public boolean setDefaults(OAuthTokenData accessToken, int defaultType) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    SetDefaults sd = new SetDefaults(sp);
	    r = sd.setDefaults(defaultType);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.setDefaults(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeDataFilter filter, ThingTypeData[] thingTypeData) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingTypesData r = null;
    try {
	    Db_ThingType t = new Db_ThingType(sp);
	    r = t.saveThingTypes(filter, thingTypeData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveThingTypes(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    Db_ThingType t = new Db_ThingType(sp);
	    r = t.delete(accessToken, thingTypeData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.deleteThingTypes(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingsData getThings(OAuthTokenData accessToken, ThingDataFilter filter) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingsData r = null;
    try {
	    Db_Thing t = new Db_Thing(sp);
	    r = t.getThings(accessToken, filter);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getThings(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingData saveThing(OAuthTokenData accessToken, ThingDataFilter filter, ThingData thingData) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingData r = null;
    try {
	    Db_Thing t = new Db_Thing(sp);
	    r = t.saveThing(accessToken, filter, thingData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveThing(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingsData saveThings(OAuthTokenData accessToken, ThingDataFilter filter, ThingData[] thingData) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingsData r = null;
    try {
	    Db_Thing t = new Db_Thing(sp);
	    r = t.saveThings(accessToken, filter, thingData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveThings(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    Db_Thing t = new Db_Thing(sp);
	    r = t.deleteThing(accessToken, thingData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.deleteThing(): Error:", e);
    }
    sp.end();
    return r;
  }

  public boolean deleteThingStuffType(OAuthTokenData accessToken, ThingStuffTypeData stuffTypeData) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    Db_ThingStuffType t = new Db_ThingStuffType(sp);
	    r = t.delete(accessToken, stuffTypeData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.deleteThingStuff(): Error:", e);
    }
    sp.end();
    return r;
  }

  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingStuffTypesData r = null;
    try {
	    Db_ThingStuffType t = new Db_ThingStuffType(sp);
	    r = t.getThingStuffTypes(accessToken, filter);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getThingStuffTypes(): Error:", e);
    }
    sp.end();
    return r;
  }

  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter, ThingStuffTypeData[] stuffTypeData) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingStuffTypesData r = null;
    try {
	    Db_ThingStuffType t = new Db_ThingStuffType(sp);
	    r = t.saveThingStuffTypes(accessToken, filter, stuffTypeData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveThingStuffTypes(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingStuffsData r = null;
    try {
	    Db_ThingStuff db = new Db_ThingStuff(sp);
	    r = db.getThingStuffsData(accessToken, filter);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getThingStuffData(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter, ThingStuffData[] stuffData) {
    sp.start(getThreadLocalRequest(), accessToken);
    ThingStuffsData r = null;
    try {
	    Db_ThingStuff db = new Db_ThingStuff(sp);
	    r = db.saveThingStuffData(accessToken, filter, stuffData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveThingStuffData(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long stuffId) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    Db_ThingStuff db = new Db_ThingStuff(sp);
	    r = db.deleteThingStuffData(accessToken, stuffId);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.deleteThingStuffData(): Error:", e);
    }
    sp.end();
    return r;
  }
  
  public boolean changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData) {
    sp.start(getThreadLocalRequest(), accessToken);
    boolean r = false;
    try {
	    Db_User db = new Db_User(sp);
	    r = db.changePassword(accessToken, changePassswordData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.changePassword(): Error:", e);
    }
    sp.end();
    return r;
  }

  // add accessToken to this
  public boolean saveFeedBack(FeedbackData feedbackData) {
  	sp.start(getThreadLocalRequest(), null);
	  boolean r = false;
    try {
	    Db_Feedback db = new Db_Feedback(sp);
	    r = db.sendFeedback(feedbackData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveFeedBack(): Error:", e);
    }
	  sp.end();
	  return r;
  }

  public WidgetAttrData getWidgetAttributes(OAuthTokenData accessToken, WidgetAttrDataFilter widgetAttrDataFilter) {
  	sp.start(getThreadLocalRequest(), accessToken);
  	WidgetAttrData r = null;
    try {
	    r = new Db_WidgetAttr(sp).getWidgetAttributes(accessToken, widgetAttrDataFilter);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getWidgetAttributes(): Error:", e);
    }
  	sp.end();
	  return r;
  }
  
  public AccountData getAccountData(OAuthTokenData accessToken, long thingId) {
   	sp.start(getThreadLocalRequest(), accessToken);
  	AccountData r = null;
    try {
	    r = new Db_Profile(sp).getProfileData(accessToken, thingId);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.getProfileData(): Error:", e);
    }
  	sp.end();
  	return r;
  }

  public AccountData saveAccountData(OAuthTokenData accessToken, ThingDataFilter filter, AccountData profileData) {
  	sp.start(getThreadLocalRequest(), accessToken);
  	AccountData r = null;
    try {
	    r = new Db_Profile(sp).saveProfileData(accessToken, filter, profileData);
    } catch (Exception e) {
    	log.log(Level.SEVERE, "RpcCoreServiceImpl.saveProfileData(): Error:", e);
    }
  	sp.end();
  	return r;
  }

}
