package com.tribling.gwt.test.oauth.server.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * DB connection methods
 * 
 * @author branflake2267
 *
 */
public class DB_Conn {
	
	protected int serverLocation = 0;

	private DbConnData dbConnRead = null;
	private DbConnData dbConnWrite = null;
	
	// which computers are we using to run hosted mode from
	// list the computers you design on
	private String[] hostedModehosts;
	
	/**
	 * Constructor
	 */
	public DB_Conn() {

		hostedModehosts = new String[2];
		
		// setup design db write (hosted mode)
		DbConnData w1 = new DbConnData();
		w1.host = "192.168.10.91";
		w1.port = 3306;
		w1.driver = "com.mysql.jdbc.Driver";
		w1.username = "oAuth";
		w1.password = "oAuth*7";
		w1.database = "accounts";
		
		// setup design db read (hosted mode)
		DbConnData r1 = new DbConnData();
		r1.host = "192.168.10.81";
		r1.port = 3306;
		r1.driver = "com.mysql.jdbc.Driver";
		r1.username = "oAuth";
		r1.password = "oAuth*7";
		r1.database = "accounts";
		
		
		// setup production db write
		DbConnData w2 = new DbConnData();
		w2.host = "192.168.12.81";
		w2.port = 3306;
		w2.driver = "com.mysql.jdbc.Driver";
		w2.username = "oAuth";
		w2.password = "oAuth*7";
		w2.database = "accounts";
		
		// setup production db read
		DbConnData r2 = new DbConnData();
		r2.host = "192.168.12.81";
		r2.port = 3306;
		r2.driver = "com.mysql.jdbc.Driver";
		r2.username = "oAuth";
		r2.password = "oAuth*7";
		r2.database = "accounts";
		
	}

	private void getHost() {

		String hostname = null;

		try {
			// Execute command
			String command = "hostname";
			Process child = Runtime.getRuntime().exec(command);

			// Get the input stream and read from it
			java.io.InputStream in = child.getInputStream();

			hostname = "";
			int c;
			while ((c = in.read()) != -1) {
				hostname += (char) c;
			}
			in.close();
			child.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}


		if (hostname.startsWith("de")) { 
			
			this.serverLocation = 1; 
		
		} else if (hostname.startsWith("la")) { 
			
			this.serverLocation = 2; 
			
		} else {
			
			this.serverLocation = 0; 
			
		}

	}
	
	private String getServerURL() {
		String url = null;
		if (serverLocation == 1) { //home office desktop brandon
			
			url = "jdbc:mysql://192.168.10.91:3306/";
			
		} else if (serverLocation == 2) { //labtop
			
			url = "jdbc:mysql://localhost:3306/";
			
		} else { //production server
			
			url = "jdbc:mysql://192.168.12.81:3306/";
			
		}
		
		//debug
		//System.out.println("url:" + url);
		
		return url;
	}

	protected Connection getDbConnRead() {
		
		String jdbc = dbConnRead.driver;
		String url = dbConnRead.getUrl();
		String username = dbConnRead.username;
		String password = dbConnRead.password;
		
		
		try {
			Class.forName(jdbc).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println("Connection Error: " + url);
			e.printStackTrace();
			System.exit(1);
		}
		return conn;
	}
	
	protected Connection getDbConnWrite() {
		
		String jdbc = dbConnWrite.driver;
		String url = dbConnWrite.getUrl();
		String username = dbConnWrite.username;
		String password = dbConnWrite.password;
		
		try {
			Class.forName(jdbc).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println("Connection Error: " + url);
			e.printStackTrace();
			System.exit(1);
		}
		return conn;
	}

	/**
	 * get row count
	 * @param resultSet
	 * @return
	 */
	protected static int getResultSetSize(ResultSet resultSet) {
		int size = -1;

		try {
			resultSet.last();
			size = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (SQLException e) {
			return size;
		}

		return size;
	}



}
