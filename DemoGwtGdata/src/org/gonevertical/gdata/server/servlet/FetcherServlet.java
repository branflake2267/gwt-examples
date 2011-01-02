package org.gonevertical.gdata.server.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.util.AuthenticationException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.*;

import org.gonevertical.gdata.server.jdo.TokenStore;

/**
 * this is from google's gdata example
 * 
 *
 */
public class FetcherServlet extends HttpServlet {
  
  private UserService userService = UserServiceFactory.getUserService();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Initialize a client to talk to Google Data API services.
    DocsService client = new DocsService("demogwtgdata.appspot.com");

    String sessionToken = null;
    
    // If a user is currently signed in to the application, attempt to retrieve
    // a previously stored session token associated with that account from App
    // Engine's datastore.
    if (userService.isUserLoggedIn()) {
      User user = userService.getCurrentUser();
      sessionToken = TokenStore.getToken(user.getUserId());
    }

    try {
      // Find the AuthSub token and upgrade it to a session token.
      String authToken = AuthSubUtil.getTokenFromReply(request.getQueryString());

      // Upgrade the single-use token to a multi-use session token.
      sessionToken = AuthSubUtil.exchangeForSessionToken(authToken, null);
    } catch (AuthenticationException e) {
      // Handle
    } catch (GeneralSecurityException e) {
      // Handle
    } catch (NullPointerException e) {
      // Ignore
    }

    if (sessionToken != null) {
      
      if (userService.isUserLoggedIn()) {
        User user = userService.getCurrentUser();
        TokenStore.addToken(user.getUserId(), sessionToken);
      }
      
      // Set the session token as a field of the Service object. Since a new
      // Service object is created with each get call, we don't need to
      // worry about the anonymous token being used by other users.
      client.setAuthSubToken(sessionToken);

      // Write token to response
      response.getWriter().print("<h3>A Google Data session token was found for your account!</h3>");
      response.getWriter().print("<p>Token: " + sessionToken + "</p>");
      
    } else {
      // If no session token is set, allow users to authorize this sample app
      // to fetch personal Google Data feeds by directing them to an
      // authorization page.

      // Generate AuthSub URL
      String nextUrl = request.getRequestURL().toString();
      String requestUrl = AuthSubUtil.getRequestUrl(nextUrl, "https://docs.google.com/feeds/", false, true);

      // Write AuthSub URL to response
      response.getWriter().print("<h3>A Google Data session token could not be found for your account.</h3>");
      response.getWriter().print("<p>In order to see your data, you must first authorize access to your personal feeds. Start this process by choosing a service from the list below:</p>");
      response.getWriter().print("<ul><li><a href=\"" + requestUrl + "\">Google Documents</a></li></ul>");
    }
    
    
  }
  
  
}
