package com.gawkat.core.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.rpc.RpcCoreService;
import com.gawkat.core.server.db.ThingType;
import com.gawkat.core.server.db.User;
import com.gawkat.core.server.db.oauth.OAuthServer;
import com.gawkat.core.server.jdo.SetDefaults;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RpcCoreServiceImpl extends RemoteServiceServlet implements RpcCoreService {

	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * get request url
	 * 
	 * @return
	 */
	private String getRequestUrl() {

		HttpServletRequest request = getThreadLocalRequest();
		String host = request.getRemoteHost();
		String path = request.getPathInfo();
		int port = request.getRemotePort();
		
		String url = "";
		if (port == 80) {
			url = "http://" + host + path;
		} else if (port == 443) {
			url = "https://" + host + path;
		} else {
			url = "http://" + host + ":" + port + path;
		}
		
		return url;
	}
	
	/**
	 * get request url path for oauth - minus port
	 * 
	 * ajax requests come in on different ports, as far as I can tell
	 * maybe its only in hosted mode
	 * 
	 * @return
	 */
	private String getRequestUrlOAuth() {

		HttpServletRequest request = getThreadLocalRequest();
		String host = getHost(request);
		String path = request.getRequestURI();
		
		// take off the servlet context path
		String newPath = "";
		if (path != null) {
	    String re = "(.*/)";
	    Pattern p = Pattern.compile(re);
	    Matcher m = p.matcher(path);
	    boolean found = m.find();
	    if (found == true) {
	      newPath = m.group(1);
	    }
		}

		String url = host + newPath;
		
		return url;		
	}
	
	private String getHost(HttpServletRequest request) {
	  String s = "";
	  
	  StringBuffer url = request.getRequestURL();
	  
	  int sep = 0;
	  int col = 0;
	  for (int i=0; i < url.length(); i++) {
	    
	    String c = Character.toString(url.charAt(i));
	    
	    if (c.equals(":")) {
	      col++;
	      if (col == 2) {
	        break;
	      }
	    }
	    
	    if (c.equals("/")) {
	      sep++;
	      if (sep == 3) {
	        break;
	      }
	    }
	    
	    s += c;
	  }
	  
	  return s;
	}
	
	/**
	 * test method for rpc
	 */
	public String testMethod(String s) {
		s += " was modified on the server.";
		return s;
	}

	/**
	 * A. ->(B.?) grant request token?
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
	  
	  System.out.println("1. Impl: requestToken(): ");
		
	  String url = getRequestUrlOAuth();
		
	  System.out.println("2. Impl: requestToken(): ");
	  
		OAuthServer oauth = new OAuthServer();
		OAuthTokenData rtnToken = oauth.requestToken(tokenData, url);
		
		System.out.println("3. Impl: requestToken(): ");
		
		return rtnToken;
	}

  public UserData createUser(UserData userData) {
    
    // used to sign the new user
    String url = getRequestUrlOAuth();
    
    User db = new User();
    return db.createUser(userData, url);
  }
  
  public UserData isUserNameExist(UserData userData) {
    User db = new User();
    return db.isUserNameExist(userData);
  }
  
  public UserData forgotPassword(UserData userData) {
    User db = new User();
    return db.forgotPassword(userData);
  }
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken) {
    
    // used to sign the new user
    String url = getRequestUrlOAuth();
    
    OAuthServer oauth = new OAuthServer();
    OAuthTokenData rtnToken = oauth.getUserAccessToken(appAccessToken, url);
    return rtnToken;
  }
	
  /**
   * get thing types
   * 
   * @param filter
   * @return
   */
  public ThingTypeData[] getThingTypes(ThingTypeFilterData filter) {
    ThingType thingType = new ThingType();
    return thingType.getThingTypes(filter);
  }
  
  public boolean setDefaults(int defaultType) {
    SetDefaults sd = new SetDefaults();
    return sd.setDefaults(defaultType);
  }
  
}
