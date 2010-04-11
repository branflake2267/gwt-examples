package org.gonevertical.loc.server;

import org.gonevertical.csv2sql.data.DatabaseData;
import org.gonevertical.csv2sql.lib.sql.MySqlQueryUtil;
import org.gonevertical.loc.client.TrackData;

public class DB_Track {

  public DB_Track() {
  }
  
  public void track(TrackData td) {
    
    int type = td.type;
    
    String sql = "INSERT INTO track SET type='" + type + "', DateCreated=NOW();";
    MySqlQueryUtil.update(getdd(), sql);
  }
  
  
  private DatabaseData getdd() {
    
    int databaseType = DatabaseData.TYPE_MYSQL;
    String host = "localhost";
    String port = "3306";
    String username = "root";
    String password = "83kg923m";
    String database = "track";
    DatabaseData dd = new DatabaseData(databaseType, host, port, username, password, database);
    
    return dd;
  }
}
