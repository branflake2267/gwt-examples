package com.tribling.gwt.test.mysqlconn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.mysqlconn.client.BibleData;
import com.tribling.gwt.test.mysqlconn.client.MySQLConnService;


/**
 * server side class for Async calls
 * 
 * 
 * Make sure you add a reference library (external jar in build path) JDBC Connector - 
 * You will see I put it in /opt/classpath/mysql-connector-java-5.1.5/mysql-connector-java-5.1.5-bin.jar
 * 
 * @author branflake2267
 *
 */
public class MySQLConnImpl extends RemoteServiceServlet implements MySQLConnService {

	/**
	 * constructor
	 */
	public MySQLConnImpl() {
	}
	

	/**
	 * get the books of the bible and info in an object array
	 * This is my favorite way to get recordset data
	 * 
	 * @return bibleData (array)
	 */
	public BibleData[] getBibleInfo() {
		
		DB_Bible db = new DB_Bible();
		BibleData[] bibleData = db.getBibleInfo();
		
		return bibleData;
	}
	
	
	
	
	/**
	 * use RunMeTOTestQuery to test a query - its much easier to test
	 */
	public void testQuery() {
		
		//setup the object
		DB_QueryStuff db = new DB_QueryStuff();
		//run my method
		db.queryMyDB();
		
	}
	
	
	
	
}
