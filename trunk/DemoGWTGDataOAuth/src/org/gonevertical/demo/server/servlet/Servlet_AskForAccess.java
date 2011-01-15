package org.gonevertical.demo.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

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
import com.google.gdata.client.authn.oauth.OAuthParameters;

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
  
  private static final Logger log = Logger.getLogger(Servlet_AskForAccess.class.getName());

  /**
   * NOTE:Get your key and secret here: https://www.google.com/accounts/ManageDomains
   */
  private String consumerKey;
  
  /**
   * NOTE:Get your key and secret here: https://www.google.com/accounts/ManageDomains
   */
  private String consumerSecret;

  /**
   * gdata feed
   */
  private String scope; 

  /**
   * call back to this servlet
   */
  private String callBackUrl;

  /**
   * google user account service for app engine
   */
  private UserService userService = UserServiceFactory.getUserService();

  /**
   * constructor
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
    consumerKey = props.getProperty("consumerKey");
    consumerSecret = props.getProperty("consumerSecret");
    
    System.out.println("consumerKey=" + consumerKey + " consumerSecret=" + consumerSecret);
    
  }
  
  /**
   * init parameters from servlet 
   * 
   * @param request
   */
  private void initParams(HttpServletRequest request) {
    callBackUrl = request.getRequestURL().toString();
    scope = request.getParameter("scope");
    
    System.out.println("scope=" + scope);
  }

  /**
   * goto /askforaccess (configuration is in web.xml)
   * 
   * TODO more error controls
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    initProperties();
    initParams(request);
    
    // TODO deal with previous token
    boolean b = loadPreviousAppToken(request, response);
    if (b == true) {
      //return; // for now, lets always ask
    }
    
    // use in querystring do=[what]
    String qs = request.getQueryString();
    
    // debug
    System.out.println("QueryString: " + qs);

    if (qs.contains("do=ask") == true) { // 1. first ask for unauthorized token.
      setRequestTokenFirst(request, response);

    } else if (qs.contains("do=grant") == true) {// 3. remote response, did we get a good token?
      setGrantResponse(request, response);
      
    } else if (qs.contains("do=revoke") == true) {
      setRevoke(request, response);
    }

  }

  private boolean loadPreviousAppToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
    AppTokenJdo token = getAppToken();
    boolean b = false;
    if (token != null) {
      response.getWriter().println("Token already has been granted. You can close this window."); // TODO auto close later
      b = true;
    }
    return b;
  }

  /**
   * 1. First get a request token
   * 
   * @param request
   * @param response
   * @throws IOException
   */
  private void setRequestTokenFirst(HttpServletRequest request, HttpServletResponse response) throws IOException {

    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);
    oauthParameters.setScope(scope);
    
    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    try {
      oauthHelper.getUnauthorizedRequestToken(oauthParameters);
    } catch (OAuthException e) {
      e.printStackTrace();
    }
    
    String token = oauthParameters.getOAuthToken();
    String secret = oauthParameters.getOAuthTokenSecret();
    
    System.out.println("Retrieved a remote token: Token: " + token + " Secret: " + secret);
    
    String url = callBackUrl + "?do=grant" ;
    
    // for some reason, I need to stick the secret on querystring, could save this and load it so we don't have to send it on.
    // I'm not sure why yet. But this allows the next step to work. 
    url = url + "&oauth_token_secret=" + java.net.URLEncoder.encode(secret, "UTF-8") + "&scope=" + scope;
    
    oauthParameters.setOAuthCallback(url);
    
    setRedirect(oauthHelper, oauthParameters, response); 
  }

  /**
   * 2. REDIRECT to remote site and ask for approval and then callback
   *  
   * @param oauthHelper
   * @param oauthParameters
   * @param response
   * @throws IOException
   */
  private void setRedirect(GoogleOAuthHelper oauthHelper, OAuthParameters oauthParameters, HttpServletResponse response) throws IOException {
    String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
    System.out.println(approvalPageUrl);
    response.sendRedirect(approvalPageUrl);
  }

  /**
   * 3. grant access? This happens after remote site approval
   * 
   * TODO deal with nongrant
   * 
   * @param request
   * @param response 
   * @return
   */
  private boolean setGrantResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
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
    
    saveAppToken(accessToken, accessTokenSecret);
   
    System.out.println("Retrieved a good accessToken: " + accessToken);
    boolean b = false;
    if (accessToken != null) {
      response.getWriter().println("Success, Token Granted: " + accessToken); // TODO auto close
      response.getWriter().println("You can close this to move on.");
      b = true;
    }
    return b;
  }
  
  /**
   * get previous token
   * 
   * @return
   */
  private AppTokenJdo getAppToken() {
    AppTokenJdo appToken = null;
    if (userService.isUserLoggedIn()) {
      appToken = AppTokenStore.getToken(scope);
    }
    return appToken;
  }
  
  /**
   * save the authorized access token for use when I make GData calls.
   * 
   * @param accessTokenKey
   * @param accessTokenSecret
   */
  private void saveAppToken(String accessTokenKey, String accessTokenSecret) {
    if ((accessTokenKey == null || accessTokenKey.trim().length() == 0) || 
        (accessTokenSecret == null || accessTokenSecret.trim().length() == 0)) {
      return;
    }

    System.out.println("Servlet_AskForAccess.saveAppToken(): Saved: accessTokenKey=" + accessTokenKey + " accessTokenSecret=" + accessTokenSecret);

    if (userService.isUserLoggedIn()) {
      User user = userService.getCurrentUser();
      AppTokenJdo appToken = new AppTokenJdo(user.getUserId(), accessTokenKey, accessTokenSecret, scope);
      AppTokenStore.saveToken(appToken);
    }

  }

  /**
   * revoke token - not tested yet
   * 
   * @param request
   * @param response
   */
  private void setRevoke(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    AppTokenJdo appToken = AppTokenStore.getToken(scope);
    if (appToken == null) {
      response.getWriter().println("There is no access token to revoke.");
      return;
    }
    
    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);
    oauthParameters.setOAuthToken(appToken.getAccessTokenKey());
    oauthParameters.setOAuthTokenSecret(appToken.getAccessTokenSecret());

    GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
    oauthHelper.getOAuthParametersFromCallback(request.getQueryString(), oauthParameters);
    
    try {
      oauthHelper.revokeToken(oauthParameters);
      
      AppTokenStore.deleteToken(scope); // get rid of stored app token
    } catch (OAuthException e) {
      e.printStackTrace();
    }
    
  }



}
