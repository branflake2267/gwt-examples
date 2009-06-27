package com.tribling.gwt.test.mysqlconn.server;

public class RunMeToTestQuery {

	/**
	 * Debug/Run me as a java application to try out the query before you build the client connection
	 * Right Click on me > Hit Debug As > Java Application
	 * 
	 * Make sure you have a JDBC connector in your JVM classpath, or goto Build Path and add external jar. 
	 * You will need to change the JDBC connector path in the build path 
	 * 
	 */
	public static void main(String[] args) {

		//This will test the query with out to many classes in the way
				
		//setup the object
		DB_QueryStuff db = new DB_QueryStuff();
		//run my method
		db.queryMyDB();
			

		

	}

}
