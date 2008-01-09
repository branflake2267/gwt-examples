package com.tribling.gwt.test.loginmanager.server;

import java.util.UUID;



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
		DB_SignIn db = new DB_SignIn();
		
		//run my method
		db.processSignIn("branflake2267", "tribling*7"); //these are fake!
			
		
		
		//test making a hash for the password
		//String Password = "tribling*7";
		//String Password_Hash = BCrypt.hashpw(Password, BCrypt.gensalt());
		//System.out.println("Hash Created: " + Password_Hash);
		
		//test unique id
		//UUID idOne = UUID.randomUUID();
		//System.out.println("randomClass: " + idOne);
	}
	
	
	

	/**
	 * hash the password for storage in db
	 * 
	 * (create new user entry in db storing ONLY username and hash, *NOT* the password).
	 */
	public void hashPassword(String Password) {
		String Password_Hash = BCrypt.hashpw(Password, BCrypt.gensalt());
		
		System.out.println("Hash Created: " + Password_Hash);
	}
		
	
	
	
}
