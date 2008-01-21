package com.tribling.gwt.test.loginmanager.server;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	protected Connection getConn() {
	
	    Connection conn	= null;
	    
	    String db 		= "hostdb";
	    String driver 	= "com.mysql.jdbc.Driver";
	    String user 	= "Branflake2267";
	    String pass 	= "83kg923m**89";
	    
	    String url 		= "jdbc:mysql://192.168.12.81:3306/hostdb?user=Branflake2267&password=83kg923m**89";
		
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
        		
        		conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        		System.err.println("Mysql Connection Error: ");
                e.printStackTrace();
        }
		return conn;
	}
	


	
	/**
	 * db conn2 - for my Bible DB
	 * 
	 * Make sure you add a reference library (external jar in build path) JDBC Connector - 
	 * You will see I put it in /opt/gwt-linux/mysql-connector-java-5.0.8-bin.jar
	 *
	 * 
	 * @return Connection
	 */
	protected Connection getConn2() {
	
		    Connection conn	= null;
		    String url 		= "jdbc:mysql://192.168.12.81:3306/";
		    String db 		= "Bible";
		    String driver 	= "com.mysql.jdbc.Driver";
		    String user 	= "Replace Me";
		    String pass 	= "Replace Me";
		    
		    try {
		      
		    	Class.forName(driver).newInstance();
		    	conn = DriverManager.getConnection(url+db, user, pass);
		      
		    } catch (Exception e) {
		    	
		    	//error
		    	System.err.println("Mysql Connection Error: ");
		    	//e.printStackTrace();
		    }
		
		    return conn;
	}
	
	
	/*
	 * get row count
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
		

}