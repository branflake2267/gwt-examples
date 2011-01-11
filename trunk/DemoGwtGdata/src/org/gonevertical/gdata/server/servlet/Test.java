package org.gonevertical.gdata.server.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.util.AuthenticationException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

import org.gonevertical.gdata.server.jdo.TokenStore;

/**
 * this is from google's gdata example
 * 
 *
 */
public class Test extends HttpServlet {
  
  private UserService userService = UserServiceFactory.getUserService();
  
  private static final Logger log = Logger.getLogger(FetcherServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    //log.warning(request.toString());
    log.log(Level.ALL, request.toString());
    
    response.getWriter().print(request.toString());
  }
  
  
}
