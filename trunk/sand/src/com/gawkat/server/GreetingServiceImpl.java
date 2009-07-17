package com.gawkat.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.gawkat.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input) {
    String serverInfo = getServletContext().getServerInfo();
    
    
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");
    
    

    tryConnection();
   
    

    
    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }
  
  private void tryConnection() {
    
    String serverType = getServletContext().getServerInfo();
    System.out.println("serverType: " + serverType);
    
    
    Context initContext = null;
    try {
      initContext = new InitialContext();
    } catch (NamingException e1) {
      System.out.println("NO context");
      e1.printStackTrace();
    }
    Context envContext = null;
    try {
      envContext = (Context) initContext.lookup("java:/comp/env");
    } catch (NamingException e1) {
      System.out.println("NO initContext");
      e1.printStackTrace();
    }
    
    DataSource ds = null;
    try {
      ds = (DataSource) envContext.lookup("jdbc/TestDB");
    } catch (NamingException e1) {
      System.out.println("NO datasource");
      e1.printStackTrace();
    }



    if ( ds == null ) {
       System.out.println("Data source not found!");
    }

    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (SQLException e) {
      System.out.println("couldn't get connection");
      e.printStackTrace();
    }
    
    System.out.println("trying query");
   
    String sql = "SELECT t.`myTxt` FROM test t where id=1;";
    try {
      Statement select = conn.createStatement();
      ResultSet result = select.executeQuery(sql);
     
      while (result.next()) {
        String s = result.getString(1);
        System.out.println("result: " + s);
      }
      result.close();
      result = null;
      select.close();
      select = null;
    } catch (SQLException e) {
      System.err.println("Error: queryColumns(): " + sql);
      e.printStackTrace();
    }
    
  }
  
}
