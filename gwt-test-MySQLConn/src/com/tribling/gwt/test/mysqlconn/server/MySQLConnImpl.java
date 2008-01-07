package com.tribling.gwt.test.mysqlconn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.mysqlconn.client.MySQLConnService;


/**
 * server side class for Async calls
 * 
 * 
 * Make sure you add a reference library (external jar in build path) JDBC Connector - 
 * You will see I put it in /opt/gwt-linux/mysql-connector-java-5.0.8-bin.jar
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
	 * use RunMeTOTestQuery to test query - its much easier to test
	 */
	public void testQuery() {
		
		//setup the object
		DB_QueryStuff db = new DB_QueryStuff();
		//run my method
		db.queryMyDB();
		
	}
	
}
