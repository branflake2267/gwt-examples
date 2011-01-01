package org.gonevertical.gdata.server.login;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;


public class Db_SpreadSheet {

  private SpreadsheetService service;
  
  private FeedURLFactory factory;
  
  public Db_SpreadSheet() {
    /**
     * what are we doing?
     */
    service = new SpreadsheetService("Testing Api, Brandon Donnelson");
    
  }
  
  public void login() {
    String username = "branflake2267";
    String password = "Hawaii*7";
    try {
      service.setUserCredentials(username, password);

    } catch (AuthenticationException e) {
      e.printStackTrace();
    }
  }
}
