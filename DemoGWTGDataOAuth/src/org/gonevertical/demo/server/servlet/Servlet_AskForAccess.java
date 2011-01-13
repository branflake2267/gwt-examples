package org.gonevertical.demo.server.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gonevertical.demo.server.jdo.AppTokenJdo;
import org.gonevertical.demo.server.jdo.AppTokenStore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gwt.core.client.GWT;

/**
 * http://code.google.com/apis/gdata/docs/auth/oauth.html - need gdata jars
 * 
 * Be sure to get your key and secret @ https://www.google.com/accounts/ManageDomains
 * http://code.google.com/apis/accounts/docs/RegistrationForWebAppsAuto.html#new
 * 
 * The goal of this is to goto remote OAuth request page, thus asking for access, and see if the person will grant us access, 
 *   then the remote site will give us a token to store, once where done, this page will close
 * 
 * @author branflake2267
 *
 */
public class Servlet_AskForAccess extends HttpServlet {

  // NOTE: GET YOUR SECRET HERE!!!!: https://www.google.com/accounts/ManageDomains
  private static final String CONSUMER_KEY = "demogwtgdataoauth.appspot.com";
  private static final String CONSUMER_SECRET = "4LczkVvFm4+zkWqRR5l4eJKf";

  // i'll get my blogs list
  // http://www.blogger.com/feeds/default/blogs
  private static final String SCOPE = "http://www.blogger.com/feeds/"; 

  private String callBackUrl = null;


  private UserService userService = UserServiceFactory.getUserService();
  private GoogleOAuthParameters oauthParameters;
  private GoogleOAuthHelper oauthHelper;

  public Servlet_AskForAccess() {
  }

  /**
   * goto /askforaccess (configuration is in web.xml)
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    callBackUrl = request.getRequestURL().toString();

    if (getAsk(request)) { // querystring must have do=ask
      askFirst(request, response);
      
    } else {
      processResponse(request, response);
    }

  }

  /**
   * ask first
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  private void askFirst(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Previous Reponse - has a token already been granted?
    //AppTokenJdo appToken = getAppToken();
    //if (appToken != null) {
      //response.getWriter().print("App Access Token has already been granted");
      //return;
    //}

    // first setup oauth params
    System.out.println("Servlet_AskForAccess.setupOAuthParams Settup OAuth ");

    // my oauth parameters. Go get your consumer key from google, link above.
    // Be sure to get your key and secret @ https://www.google.com/accounts/ManageDomains
    oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(CONSUMER_KEY);
    oauthParameters.setOAuthConsumerSecret(CONSUMER_SECRET);
    oauthParameters.setScope(SCOPE);
    oauthParameters.setOAuthCallback(callBackUrl);

    // sign them - so we can use them
    oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    try {
      oauthHelper.getUnauthorizedRequestToken(oauthParameters);
    } catch (OAuthException e) {
      e.printStackTrace();
    }
    
    // direct to remote site for authorization for scope
    String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
    if (oauthHelper.getAccessTokenUrl() != null) {
      response.sendRedirect(approvalPageUrl);
    }
    
  }
  
  /**
   * callback response - is the approval response coming back, then we should extract token
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  private void processResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    boolean b = getAccessToken(request);
    if (b == true) {
      response.getWriter().print("New App Access Token has already been granted");
      return;
    }

  }

  private boolean getAsk(HttpServletRequest request) {
    String qs = request.getQueryString();
    boolean b = false;
    if (qs.contains("do=ask")) {
      b = true;
    }
    return b;
  }
  
  /**
   * get session token
   * 
   * @return
   */
  private AppTokenJdo getAppToken() {
    AppTokenJdo appToken = null;
    if (userService.isUserLoggedIn()) {
      User user = userService.getCurrentUser();
      appToken = AppTokenStore.getToken(user.getUserId());
    }
    return appToken;
  }

  /**
   * this happens in callback(response), we can extract the accesstoken
   * 
   * @param request
   * @return
   */
  private boolean getAccessToken(HttpServletRequest request) {

    System.out.println("QueryString: " + request.getQueryString());

    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(CONSUMER_KEY);
    oauthParameters.setOAuthConsumerSecret(CONSUMER_SECRET);

    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    oauthHelper.getOAuthParametersFromCallback(request.getQueryString(), oauthParameters);
    
    String accessToken = null;
    try {
      accessToken = oauthHelper.getAccessToken(oauthParameters); // TODO why does this not work?
    } catch (OAuthException e) {
      e.printStackTrace();
    }
   // You can also pull the OAuth token string from the oauthParameters:
   // String accessToken = oauthParameters.getOAuthToken();
   System.out.println("OAuth Access Token: " + accessToken);
  
   String accessTokenSecret = oauthParameters.getOAuthTokenSecret();
   System.out.println("OAuth Access Token's Secret: " + accessTokenSecret);

    // save a new app token - we can use it for our gdata calls later
    //saveAppToken(accessTokenKey, accessTokenSecret);

    boolean r = false;
    if (accessToken != null) {
      r = true;
    }
    return r;
  }


  private void saveAppToken(String accessTokenKey, String accessTokenSecret) {
    if (accessTokenKey == null || accessTokenSecret == null) {
      return;
    }

    System.out.println("Servlet_AskForAccess.saveAppToken(): Saved: accessTokenKey=" + accessTokenKey + " accessTokenSecret=" + accessTokenSecret);

    if (userService.isUserLoggedIn()) {
      User user = userService.getCurrentUser();
      AppTokenJdo appToken = new AppTokenJdo(user.getUserId(), accessTokenKey, accessTokenSecret);
      AppTokenStore.saveToken(appToken);
    }

  }





}
