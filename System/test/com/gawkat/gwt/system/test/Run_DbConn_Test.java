package com.gawkat.gwt.system.test;

import com.gawkat.gwt.system.server.db.Db_Conn;
import com.gawkat.gwt.system.server.db.Db_User;

public class Run_DbConn_Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    Db_Conn db = new Db_Conn();
    
    String sql = "SELECT ThingId FROM thing LIMIT 0,1;";
    db.getQueryInt(sql);
  }

}
