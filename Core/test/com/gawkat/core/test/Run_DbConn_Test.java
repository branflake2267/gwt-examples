package com.gawkat.core.test;

import com.gawkat.core.server.db.Db_Conn;
import com.gawkat.core.server.db.Db_User;

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
