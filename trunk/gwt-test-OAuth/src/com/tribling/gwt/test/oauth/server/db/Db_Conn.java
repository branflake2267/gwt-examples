package com.tribling.gwt.test.oauth.server.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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
public class Db_Conn {
	
	// connection vars for read connection
	private Db_ConnData dbConnRead = null;
	
	// connection vars for write connection
	private Db_ConnData dbConnWrite = null;
	
	// which computers are we using to run hosted mode from
	// list the computers you design on
	private String[] hostedModehosts;
	
	/**
	 * Constructor
	 */
	public Db_Conn() {

		// set up the vars that will need for database connections
		setDbVars();
		
		// init this as an instance or extend this in another class
	}
	
	/**
	 * set up the connection vars that will be using to read the database
	 * depending on what server this runs on, can tell which database to connect
	 */
	private void setDbVars() {
		
		// host names that the will run gwt in hosted mode
		hostedModehosts = new String[2];
		hostedModehosts[0] = "design"; // my host name for my design pc
		hostedModehosts[1] = "laptop"; // my laptop host name
		
		// setup design db write (hosted mode)
		Db_ConnData w1 = new Db_ConnData();
		w1.host = "192.168.10.91";
		w1.port = 3306;
		w1.driver = "com.mysql.jdbc.Driver";
		w1.username = "oAuth";
		w1.password = "oAuth*7";
		w1.database = "accounts";
		
		// setup design db read (hosted mode)
		Db_ConnData r1 = new Db_ConnData();
		r1.host = "192.168.10.91";
		r1.port = 3306;
		r1.driver = "com.mysql.jdbc.Driver";
		r1.username = "oAuth";
		r1.password = "oAuth*7";
		r1.database = "accounts";
		
		
		// setup production db write
		Db_ConnData w2 = new Db_ConnData();
		w2.host = "192.168.12.81";
		w2.port = 3306;
		w2.driver = "com.mysql.jdbc.Driver";
		w2.username = "oAuth";
		w2.password = "oAuth*7";
		w2.database = "accounts";
		
		// setup production db read
		Db_ConnData r2 = new Db_ConnData();
		r2.host = "192.168.12.81";
		r2.port = 3306;
		r2.driver = "com.mysql.jdbc.Driver";
		r2.username = "oAuth";
		r2.password = "oAuth*7";
		r2.database = "accounts";

		if (getHostedMode() == true) {
			dbConnRead = r1;
			dbConnWrite = w1;
		} else {
			dbConnRead = r2;
			dbConnWrite = w2;
		}
	
	}
	
	/**
	 * is this class running in hosted mode
	 * 
	 * @return
	 */
	private boolean getHostedMode() {
		String hostname = getServerHostName();
		int host = -1;
		Arrays.sort(hostedModehosts);
		if (hostedModehosts != null) {
			host = Arrays.binarySearch(hostedModehosts, hostname);
		}
		
		// we are on the design server
		boolean hostedMode = false;
		if (host > -1) {
			hostedMode = true;
		}
		
		return hostedMode;
	}

	/**
	 * get this servers host name for use to figure out if we are running in hosted mode
	 * 
	 * @return
	 */
	private String getServerHostName() {
		String hostname = null;
		try {
			String command = "hostname";
			Process child = Runtime.getRuntime().exec(command);
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
		System.out.println("hostname: " + hostname);
		
		hostname = hostname.replace("\n", "").trim();
		
		return hostname;
	}
	
	/**
	 * get db connection for reading recordsets
	 * 
	 * @return
	 */
	protected Connection getDbConnRead() {
		
		if (dbConnRead == null) {
			setDbVars();
		}
		
		if (dbConnRead == null) {
			System.out.println("No Db_ConnData has been set for this. Error: getDbConnRead()");
			System.exit(1);
		}
		
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
	
	/**
	 * get db connection for writing data to database
	 * 
	 * @return
	 */
	protected Connection getDbConnWrite() {
		
		if (dbConnWrite == null) {
			setDbVars();
		}
		
		if (dbConnWrite == null) {
			System.out.println("No Db_ConnData has been set for this. Error: getDbConnWrite()");
			System.exit(1);
		}
		
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
			System.err.println("Verify connection variables");
			e.printStackTrace();
			System.exit(1);
		}
		return conn;
	}

	/**
	 * get row count for recordset
	 * 
	 * @param result
	 * @return
	 */
	protected int getResultSetSize(ResultSet result) {
		int size = -1;
		try {
			result.last();
			size = result.getRow();
			result.beforeFirst();
		} catch (SQLException e) {
			return size;
		}
		return size;
	}

	/**
	 * quick query an int value
	 * 
	 * @param sql
	 * @return
	 */
	public int getQueryInt(String sql) {

		if (sql == null) {
			System.out.println("sql query was null.");
			return 0;
		}
		
		if (sql.length() == 0) {
			System.out.println("no sq query existed.");
			return 0;
		}
		
		int i = 0;
		try {
			Connection conn = this.getDbConnRead();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(sql);
			while (result.next()) {
				i = result.getInt(1);
			}
			select.close();
			result.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Mysql Statement Error: " + sql);
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * quick query a boolean value
	 * 
	 * @param sql
	 * @return
	 */
	protected boolean getQueryBoolean(String sql) {
		
		if (sql == null) {
			System.out.println("sql query was null.");
			return false;
		}
		
		if (sql.length() == 0) {
			System.out.println("no sq query existed.");
			return false;
		}
		
		boolean b = false;
		try {
			Connection conn = this.getDbConnRead();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(sql);
			while (result.next()) {
				b = result.getBoolean(1);
			}
			select.close();
			result.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Mysql Statement Error: " + sql);
			e.printStackTrace();
		}
		return b;
	}
	
	/**
	 * quick update/insert query
	 * 
	 * @param sql
	 * @return insert id
	 */
	protected int setQuery(String sql) {
		
		if (sql == null) {
			System.out.println("sql query was null.");
			return -1;
		}
		
		if (sql.length() == 0) {
			System.out.println("no sq query existed.");
			return -1;
		}
		
		int id = -1;
		try {
		    Connection conn = this.getDbConnWrite();
		    Statement update = conn.createStatement();
		    update.executeUpdate(sql);
		    ResultSet result = update.getGeneratedKeys(); 
		    if (result != null && result.next()) { 
		        id = result.getInt(1);  
		    }
		    result.close();
		    update.close();
		    conn.close();
		} catch(SQLException e) {
		    System.err.println("Mysql Statement Error: " + sql);
		    e.printStackTrace();
		}
		return id;
	}
	
	
}
