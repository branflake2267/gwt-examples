package org.gonevertical.demo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * create a db_conn - this is just an example of one way to do this.
 * NOTE: on class "abstract" - you have to create subclasses from this class to use this class in other classes. 
 * Delete abstract if you want to use this class directly.
 * @author branflake2267
 *
 */
public abstract class DB_Conn {

	private int serverLocation = 0;

	/**
	 * Constructor
	 */
	public DB_Conn() {

		// figure out what server this application is being hosted on
		getServerMysqlOn();
	}

	/**
	 * What Server are we on? 
	 * 
	 * I use a design and production server, and hate switching everytime before I compile.
	 * I use this to make switching to my production server automatic.
	 */
	private void getServerMysqlOn() {

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

		// debug
		//System.out.println("hostname: " + hostname);

		if (hostname.startsWith("de")) { // for design[0-9]+

			this.serverLocation = 1; // desktop home office

		} else if (hostname.startsWith("la")) { // for labtop -> localhost dbs

			this.serverLocation = 2; // laptop

		} else {

			this.serverLocation = 0; // production server

		}

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
		String url 		= getServerURL();

		String db 		= "Bible";
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

	/**
	 * get string url for server location
	 * find out what computer this is own, then make the db url string
	 * 
	 * @return
	 */
	private String getServerURL() {
		String url = null;
		if (serverLocation == 1) { // home office desktop brandon

			url = "jdbc:mysql://192.168.10.100:3306/";

		} else if (serverLocation == 2) { // labtop - portable office

			url = "jdbc:mysql://192.168.10.100:3306/";

		} else { // production server

			url = "jdbc:mysql://192.168.10.100:3306/";

		}

		// debug
		//System.out.println("url:" + url);

		return url;
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