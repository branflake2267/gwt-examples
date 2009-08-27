package com.gawkat.core.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import com.gawkat.core.server.jdo.PMF;

public class ServerPersistence {

  private HttpServletRequest request = null;
  
  public ServerPersistence() {
  }
  
  public void start(HttpServletRequest request) {
    this.request = request;
  }
  
  public void end() {
     
  }
  
  public void getUrl() {
    
  }
  

  /**
   * get request url
   * 
   * @return
   */
  public String getRequestUrl() {
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
  public String getRequestUrlOAuth() {
    String host = getHost();
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
  
  /**
   * get hostname
   * 
   * @param request
   * @return
   */
  public String getHost() {
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
}
