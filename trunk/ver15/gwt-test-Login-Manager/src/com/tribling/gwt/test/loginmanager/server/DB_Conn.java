package com.tribling.gwt.test.loginmanager.server;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * create a db_conn - this is just an example of one way to do this.
 * NOTE: "abstract" - you have to create subclasses from this class to use this class. Delete abstract if you want to use this class directly.
 * @author branflake2267
 *
 */
public abstract class DB_Conn {
	
	/**
	 * Constructor
	 */
	public DB_Conn() {
	}
	
	
	
	/**
	 * db conn
	 * 
	 * Make sure you add a reference library (external jar in build path) JDBC Connector - 
	 * You will see I put it in /opt/gwt-linux/mysql-connector-java-5.0.8-bin.jar
	 * 
	 * @return Connection
	 */
	protected static Connection getConn() {
	
	    Connection conn	= null;
	    
	    String db 		= "gwt";
	    String driver 	= "com.mysql.jdbc.Driver";
	    String user 	= "test";
	    String pass 	= "password*7";
	    String url 		= "jdbc:mysql://192.168.12.81:3306/";
		
        try {
        	Class.forName(driver).newInstance(); 
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
        	conn = DriverManager.getConnection(url+db, user, pass);
        } catch (SQLException e) {
        	System.err.println("Mysql Connection Error:");
            e.printStackTrace();
        }
		return conn;
	}
	


	/**
	 * Get row count from resultset
	 * @param Resultset
	 * @return size
	 */
	protected static int getResultSetSize(ResultSet resultSet) {
	    int size = -1;
	    try {
	        resultSet.last();
	        size = resultSet.getRow();
	        resultSet.beforeFirst();
	    } catch(SQLException e) {
	        return size;
	    }
	    return size;
	}

	/**
	 * Get userID from session
	 * @param SessionID
	 * @return UserID
	 */
	protected static int getUserID(String SessionID) {
		int UserID = 0;
		String Query = "SELECT UserID FROM `session` WHERE (SessionID = '" + SessionID + "')";
		try {
        	//SQL connection
        	Connection connection = getConn();
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery(Query);
            while (result.next()) {
               UserID = result.getInt(1);
            }
            connection.close();
        } catch(Exception e) {
        	System.err.println("Mysql Statement Error: ");
        	e.printStackTrace();
        }
		return UserID;
	}

	
	/**
	 * get a unix time stamp for current day
	 */
	protected static int getUnixTimeStamp() {
		Date date = new Date();
		int TimeStamp = (int) (date.getTime() * .001);
		return TimeStamp;
	}
	
}