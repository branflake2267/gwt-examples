package org.gonevertical.demo.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

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
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.util.ServiceException;
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

  /**
   * NOTE:Get your key and secret here: https://www.google.com/accounts/ManageDomains
   */
  private String consumerKey = "demogwtgdataoauth.appspot.com";
  
  /**
   * NOTE:Get your key and secret here: https://www.google.com/accounts/ManageDomains
   */
  private String consumerSecret = null;

  /**
   * http://www.blogger.com/feeds/default/blogs - blob feed
   */
  private String scope = "http://www.blogger.com/feeds/"; 

  /**
   * call back to this servlet
   */
  private String callBackUrl = null;

  /**
   * google user account service for app engine
   */
  private UserService userService = UserServiceFactory.getUserService();

  /**
   * constructor - init nothing so far
   */
  public Servlet_AskForAccess() {
  }
  
  /**
   * hide my consumer secret from svn
   */
  private void initProperties() {
    
    InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/app.properties");
    Properties props = new Properties();
    try {
      props.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    consumerSecret = props.getProperty("consumerSecret");
    
    System.out.println("consumerSecret=" + consumerSecret);
    
  }
  
  /**
   * set the call back url to this servlet
   * 
   * @param request
   */
  private void initCallbackUrl(HttpServletRequest request) {
    callBackUrl = request.getRequestURL().toString();
  }

  /**
   * goto /askforaccess (configuration is in web.xml)
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    initProperties();
    initCallbackUrl(request);
    
    // use in querystring do=[what]
    String qs = request.getQueryString();
    
    // debug
    System.out.println("QueryString: " + qs);

    if (qs.contains("do=ask") == true) { // 1. first ask for unauthorized token.
      setRequestTokenFirst(request, response);

      
    } else if (qs.contains("do=upgrade") == true) { // 2. then upgrade the token, then send them to ask for granting access on remote site.
      setGrant(request, response);
      
      
    } else if (qs.contains("do=grant") == true) {
      
    }

    System.out.println("test");
  }

  /**
   * 1. First get a request token for upgrade
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  private void setRequestTokenFirst(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String url = callBackUrl + "?do=grant";
    
    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);
    oauthParameters.setScope(scope);
    oauthParameters.setOAuthCallback(url);

    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    try {
      oauthHelper.getUnauthorizedRequestToken(oauthParameters);
    } catch (OAuthException e) {
      e.printStackTrace();
    }
    
    String token = oauthParameters.getOAuthTokenSecret();
    
    System.out.println("Retrieved token from remote site: " + token + " now go upgrade it.");
    
    // REDIRECT to remote site and ask for approval
    
    
  }

  /**
   * 3. grant access? This happens after remote site approval
   * 
   * @param request
   * @param response 
   * @return
   */
  private boolean setGrant(HttpServletRequest request, HttpServletResponse response) {
    
    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);

    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    oauthHelper.getOAuthParametersFromCallback(request.getQueryString(), oauthParameters);

    String accessToken = null;
    try {
      accessToken = oauthHelper.getAccessToken(oauthParameters);
    } catch (OAuthException e) {
      e.printStackTrace();
    }
    String accessTokenSecret = oauthParameters.getOAuthTokenSecret();
    
    // debug
    System.out.println("OAuth Access Token: " + accessToken);
    System.out.println("OAuth Access Token's Secret: " + accessTokenSecret);

    
    // save a new app token - we can use it for our gdata calls later
    saveAppToken(accessToken, accessTokenSecret);

    boolean r = false;
    if (accessToken != null) {
      r = true;
    }
    return r;
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
  
  private void saveAppToken(String accessTokenKey, String accessTokenSecret) {
    if ((accessTokenKey == null || accessTokenKey.trim().length() == 0) || 
        (accessTokenSecret == null || accessTokenSecret.trim().length() == 0)) {
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
