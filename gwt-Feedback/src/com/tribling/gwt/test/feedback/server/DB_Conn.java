package com.tribling.gwt.test.feedback.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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

	protected static boolean Design = true;

	/**
	 * Constructor
	 */
	public DB_Conn() {

		//System.out.println("");
		//System.out.println("***DB_Conn: New***");
		
		//figure out what server mysql is on
		getServerMysqlOn();

		//manual method
		//Design = false;
	}

	/**
	 * What Server are we on? -> tell mysql
	 * 
	 * I use a design database server and a production database server.
	 * This allows me to test things outside the production enviroment with 
	 * out production database errors.
	 * 
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

		//debug
		//System.out.println("hostname: " + hostname);
		
		if (hostname.startsWith("br")) {
			this.Design = true;
			//System.out.println("design = true");
		} else {
			this.Design = false;		
		}

	}

	/**
	 * start db conn gwt Slave - localhost when designing, (tigres when
	 * production) select from any server
	 * 
	 * @return Connection
	 */
	protected static Connection getConnSlave() {
		
		Connection conn = null;
		String url = null;
		if (Design == true) {
			url = "jdbc:mysql://192.168.10.91:3306/";
		} else {
			url = "jdbc:mysql://192.168.12.81:3306/";
		}
		String db = "gwt";
		String driver = "com.mysql.jdbc.Driver";
	    String user = "gwt-examples";
	    String pass = "password";

		//System.out.println("getConnSlave: url:" + url);
		
		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + db, user, pass);

		} catch (Exception e) {

			//error
			System.err.println("Slave gwt: Mysql Connection Error: ");
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * start db conn - always tigres!!!!!!
	 * Write to tigres
	 * @return Connection
	 */
	protected static Connection getConnMaster() {

		Connection conn = null;
		String url = null;
		if (Design == true) {
			url = "jdbc:mysql://192.168.10.91:3306/";
		} else {
			url = "jdbc:mysql://192.168.12.81:3306/";
		}
		String db = "gwt";
		String driver = "com.mysql.jdbc.Driver";
		String user = "";
		String pass = "";

		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + db, user, pass);

		} catch (Exception e) {

			//error
			System.err.println("Master gwt: Mysql Connection Error: ");
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * start db conn
	 * @return Connection
	 */
	protected static Connection getConn2() {

		Connection conn = null;
		String url = null;
		if (Design == true) {
			url = "jdbc:mysql://192.168.10.91:3306/";
		} else {
			url = "jdbc:mysql://192.168.12.81:3306/";
		}
		String db = "Bible";
		String driver = "com.mysql.jdbc.Driver";
		String user = "";
		String pass = "";

		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + db, user, pass);

		} catch (Exception e) {

			//error
			System.err.println("Bible: Mysql Connection Error: ");
			e.printStackTrace();
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

	/**
	 * Get userID from session
	 * @param SessionID
	 * @return
	 */
	protected static int getUserID(String SessionID) {
		int UserID = 0;

		String Query = "SELECT UserID FROM `session` WHERE (SessionID = '" + SessionID + "');";

		//System.out.println("Geting UserID from session Query: " + Query);

		try {
			//SQL connection
			Connection connection = getConnSlave();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(Query);
			while (result.next()) {
				UserID = result.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.err.println("Mysql Statement Error: ");
			e.printStackTrace();
		}

		return UserID;
	}

	/**
	 * get the NickName or Email
	 * @param SessionID
	 * @return
	 */
	protected static String getUserNickName(String SessionID) {

		int UserID = getUserID(SessionID);
		String sUserID = Integer.toString(UserID);

		String Query = "SELECT UserName, Nickname FROM `user` WHERE (UserID='" + sUserID + "');";

		String rsUserName = null;
		String rsNickName = null;
		String rtnNickName = null;

		try {
			Connection connection = getConnSlave();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(Query);
			while (result.next()) {
				rsUserName = result.getString(1);
				rsNickName = result.getString(2);
			}

			result.close();
			connection.close();
		} catch (Exception e) {
			System.err.println("Mysql Statement Error: UserName Exists: " + Query);
			e.printStackTrace();

		}

		if (rsNickName.equals("") == false) {
			rtnNickName = rsNickName;
		} else {
			rtnNickName = rsUserName;
		}

		return rtnNickName;
	}

	/**
	 * get Start of Day Time Stamp
	 * @param int TimeStamp - unix time stamp
	 * @return unix time stamp (seconds)
	 */
	protected static String getDateStart_TimeStamp(int TimeStamp) {

		long ltime = (long) (TimeStamp / .001); //my unix time stamp in seconds

		//Figure out Unix time stamps for the start of the day and the end of the day
		Date DC = new Date(ltime); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month
		int day = DC.getDate();// current Day

		Date dateStart = new Date(); //Today @ 12:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year
		dateStart.setMonth(month); //set month current Month
		dateStart.setDate(day); //set current day of month
		dateStart.setMinutes(0); // minute 0
		dateStart.setHours(0); // hour 0
		dateStart.setSeconds(0); // second 0
		int iTimeStamp = (int) (dateStart.getTime() * .001);
		String sStart_TimeStamp = Integer.toString(iTimeStamp);

		//helps debugging
		//System.out.println("this.Time::: " + this.TimeStamp);
		//System.out.println("Start Time::: " + sStart_TimeStamp);

		return sStart_TimeStamp;
	}

	/**
	 * Get End of Day Time Stamp
	 * @param int TimeStamp - unix time stamp
	 * @return unix time stamp (seconds)
	 */
	protected static String getDateEnd_TimeStamp(int TimeStamp) {

		long ltime = (long) (TimeStamp / .001); //my unix time stamp in seconds

		//Figure out Unix time stamps for the start of the day and the end of the day
		Date DC = new Date(ltime); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month
		int day = DC.getDate();// current Day

		Date dateEnd = new Date(); //Today @ 11:59:59 to unix time stamp 
		dateEnd.setYear(year); //set year current Year
		dateEnd.setMonth(month); //set month current Month
		dateEnd.setDate(day); //set current day of month
		dateEnd.setHours(23); // hour 11
		dateEnd.setMinutes(59); // minute 59
		dateEnd.setSeconds(59); // second 59
		int iTimeStamp = (int) (dateEnd.getTime() * .001);
		String sEnd_TimeStamp = Integer.toString(iTimeStamp);

		return sEnd_TimeStamp;
	}

	/**
	 * get a unix time stamp for current day
	 * @return unixTimeStamp
	 */
	protected static int getUnixTimeStamp() {
		Date date = new Date();
		int TimeStamp = (int) (date.getTime() * .001);
		return TimeStamp;
	}

	/**
	 * get Date object with UnixTimeStamp
	 * @param UnixTimeStamp
	 * @return date object
	 */
	protected static Date getJavaDate(int UnixTimeStamp) {
		long longJavaTime = (long) (UnixTimeStamp / .001);
		Date date = new Date(longJavaTime);
		return date;
	}

	/**
	 * Get UnixTimeStamp for a Day (12:00:00 24hr)
	 * @param Year
	 * @param Month
	 * @param Day
	 * @return int unixTimeStamp
	 */
	protected static int getUnixTimeStampFromDay(int Year, int Month, int Day) {
		Date date = new Date();
		date.setYear(Year - 1900);
		date.setMonth(Month);
		date.setDate(Day);
		date.setHours(12);
		date.setMinutes(0);
		date.setSeconds(0);
		long lTimeStamp = date.getTime();
		int iTimeStamp = (int) (lTimeStamp * .001);
		return iTimeStamp;
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
	 * unescape string from db
	 * 
	 * TODO - apply this to all string retrieved from mysql
	 * 
	 * @param s
	 * @return
	 */
	protected static String unescapeFromSql(String s) {

		return s;
	}
	

	public void setDesign(boolean bol){
		this.Design = bol;
	}


	
}//end class
