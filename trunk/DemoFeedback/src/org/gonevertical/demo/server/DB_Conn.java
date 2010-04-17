package org.gonevertical.demo.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * ABSTRACT!!!! - this has to be extended
 * 
 * I am leaving a few non essential methods for feedback for examples of other types of uses I use this for.
 * 
 * @author branflake2267
 *
 *
 */
public abstract class DB_Conn {

	private int serverLocation;

	public DB_Conn() {
	}

	/**
	 * escape string to db
	 * 
	 * remove harmfull db content
	 * remove harmfull tags
	 * 
	 * future remove bad words
	 * 
	 * @param s
	 * @return
	 */
	protected static String escapeForSql(String s) {
		
		//bad??
		//applet,embed,frame,iframe
		
		//remove harmful HTML tags
		s = s.replaceAll("(?i)</?(HTML|SCRIPT|HEAD|CSS)\\b[^>]*>", "");
		
		
		//TODO - remove bad words
		
		String rtn = StringEscapeUtils.escapeSql(s);
		
		if (rtn == null) {
			rtn = "";
		}

		return rtn;
	}



	/**
	 * db conn
	 * 
	 * Make sure you add a reference library (external jar in build path) JDBC Connector - 
	 *   You will see I put it in /opt/classpath/mysql-connector-java-5.1.5/mysql-connector-java-5.1.12-bin.jar
	 * 
	 * Be sure the connector is in the buildpath!!!!! /war/WEB-INF/lib/mysql-connector.jar
	 * 
	 * To achieve speed of concurrent/multiple requests, 
	 *   theres some cost in authorization, which can be done away with using DBCP Pooling.
	 *   I have achieved huge, huge, huge, speed improvements in MySql requests using pooling. 
	 *   But, I would get the standard connection down first. You can layer in DBCP pooling later easily. 
	 *   I use pooling in csv2Sqlparsing project, you can find more there. I don't have an example here yet. 
	 * 
	 * @return Connection
	 */
	protected Connection getConn() {

		Connection conn	= null;

		// figure out what server this application is being hosted on
		String url 		= "jdbc:mysql://192.168.10.100:3306/";

		String db 		= "test";
		String driver = "com.mysql.jdbc.Driver";
		String user 	= "test";
		String pass 	= "test#";

		
		url = url + db;
		
		//System.out.println("connection url: " + url);
		
		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);

		} catch (Exception e) {

			// error
			System.err.println("Mysql Connection Error: ");

			// for debugging error
			e.printStackTrace();
		}

		if (conn == null)  {
			System.out.println("~~~~~~~~~~ can't get a Mysql connection");
		}
		
		return conn;
	}


	/*
	 * get recordset row count
	 * 
	 * static will allow you to use it independently, persay, 
	 * you don't have to init the class into an object to use this method
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
