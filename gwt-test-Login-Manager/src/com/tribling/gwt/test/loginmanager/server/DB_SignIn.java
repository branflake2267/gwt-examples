package com.tribling.gwt.test.loginmanager.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import com.tribling.gwt.test.loginmanager.client.SignInStatus;




/**
 * I was building a class to get the books of the bible from my mysql database
 *
 * - here is where i got the bible sql database
 * - http://sourceforge.net/project/showfiles.php?group_id=186532&package_id=249676&release_id=548680
 *
 * @author branflake2267
 *
 */
public class DB_SignIn extends DB_Conn {
	
		//submitted via method (rpc method)
		private String UserName = null;
		
		//get these from db
		private String dbPassword_Hash = null;
		private int dbUserID = 0;
		
		//response to checking hash
		private boolean isLegitUser = false;
		private String SessionID = null;
		
		//send this error back for display
		private String DisplayError = null;
		
		/**
		 * constructor
		 */
		public DB_SignIn() {
		}
		
		
		
		/**
		 * 
		 * @param UserName
		 * @param Password
		 * @return SessionID - 0 for no login - SessionID for correct login
		 */
		public SignInStatus processSignIn(String UserName, String Password) {
			
			//set the class var UserName for use in the methods
			if (UserName.equals("")) {
				UserName = "None";
			}
			this.UserName = UserName;
			
			//get UserName hash from db to compare to the password submitted
			this.getUserHashFromDB();
			
			
			//this will happen if there is no password_hash for this user
			if (this.dbPassword_Hash == null) {
				System.out.println("No Password_Hash exists in db. Returning");
				
				SignInStatus ls = new SignInStatus();
				ls.setDisplayError(this.DisplayError);
				
				return ls;
			}
			
			
			//check if the user has submitted the correct password against the database hash
			this.checkPassword(Password);
			
			//if the user is not legit return null
			if (this.isLegitUser == false) {
				System.out.println("Passwords do not match. Returning");
				
				SignInStatus ls = new SignInStatus();
				ls.setDisplayError(this.DisplayError);
				
				return ls;
			}
			
			
			//If got this far -> create a session for this user and send it back
			this.createSessionID();
			
			//debug
			System.out.println("processSignIn method successfull. Returning");
			
			//if we made it this far, we must have a SessionID - which is a row in a session table
			SignInStatus ls = new SignInStatus();
			ls.setSessionID(this.SessionID);
			
			return ls;
		}
		
		

		
		
		/**
		 * get users stored password hash from table
		 * 
		 * stores in class
		 * this.dbUserID
		 * this.dbPassword_Hash
		 */
		private void getUserHashFromDB() {
		
			
			
			String Query = "SELECT UserID, Password_Hash FROM `user` WHERE (UserName='" + this.UserName + "');";
			
	        try {
	        	Connection connection = this.getConn();
	            Statement select = connection.createStatement();
	            ResultSet result = select.executeQuery(Query);

	            //debug
	            System.out.println("Select user Query:" + Query);
	            
	            //get db fields from record
	            while (result.next ()) {
	            	this.dbUserID = result.getInt(1);
	            	this.dbPassword_Hash = result.getString(2); 
	            }

	            result.close();
	            connection.close();
	        } catch(Exception e) {
	        	//debug out output this way
	        	System.err.println("Mysql Statement Error: " + Query);
	        	e.printStackTrace();
	        }
	        
	        System.out.println("User:" + this.dbUserID);
	       
           //display error to send back
           if (this.dbUserID == 0) {
            	this.DisplayError = "User does not exist.";
           } else if (this.dbPassword_Hash == null) {
            	this.DisplayError = "Password does not exist.";
           }

	       
		}//end queryUser
		
		

		
		/**
		 * check passwords
		 * @param Password
		 * @param dbPassword_Hash
		 */
		private void checkPassword(String Password) {
			
			// Check that an unencrypted password matches one that has
			// previously been hashed
			if (BCrypt.checkpw(Password, this.dbPassword_Hash)) {
			
				this.isLegitUser = true;

				//debug
				System.out.println("Password matches db");
				
			} else {
				
				this.DisplayError = "Passwords do not match.";
				this.isLegitUser = false;
				this.SessionID = null;
				
				//debug
				System.out.println("Password do not match");
			}
		}
		
		/**
		 * create session using session table to create unique id
		 * 
		 * stores in class
		 * this.SessionID
		 */
		private void createSessionID() {
			
			//double check 
			if (this.isLegitUser == false) {
				return;
			}
			
			//create a unique session id to store
			UUID uID = UUID.randomUUID();
			this.SessionID = uID.toString();
			
			
			//save session data to table 
			String Query = "INSERT INTO `session` (UserID, SessionID, LastAccessed, DateCreated) " +
						   "VALUES ('" + this.dbUserID + "', '" + this.SessionID + "' , UNIX_TIMESTAMP(), UNIX_TIMESTAMP());";
			
	        try {
	        	Connection connection = this.getConn();
	            Statement update = connection.createStatement();
	            update.executeUpdate(Query);
	            
	            //get last id
	            ResultSet rs = update.getGeneratedKeys(); 

	            if (rs != null && rs.next()) { 
	            	int rsID = rs.getInt(1);  //unique record to which the session is stored
	            }

	            //debug
	            System.out.println("SessionID Created: " + this.SessionID);
	            
	            rs.close();
	            connection.close();
	            
	        } catch(Exception e) {
	        	//debug out output this way
	        	System.err.println("Mysql Statement Error: " + Query);
	        	e.printStackTrace();
	        	
	        }
		}
		
		
		/**
		 * look up sessionid see if its within 2 weeks
		 * @param SessionID
		 * @return
		 */
		public SignInStatus checkSessionIsStillLegal(String SessionID) {
			
			int UserID = 0;
			
			SignInStatus sis = new SignInStatus();
			
			//String Query = "SELECT UserID FROM `session` WHERE (SessionID='" + SessionID + "');"; //add 2 weeks into this query
			String Query = "SELECT UserID FROM `session` s WHERE (SessionID='" + SessionID + "') AND DateCreated < s.DateCreated + " + this.getSessionIntervalEnd() + ";";
	        
			try {
	        	Connection connection = this.getConn();
	        	Statement select = connection.createStatement();
	        	ResultSet result = select.executeQuery(Query);

	        	//debug
	        	System.out.println("Select session Query:" + Query);
	            
	        	//get db fields from record
	        	while (result.next ()) {
	            	UserID = result.getInt(1);
	        	}

	            result.close();
	            connection.close();
	        } catch(Exception e) {
	        	
	        	//debug out output this way
	        	System.err.println("Mysql Statement Error: " + Query);
	        	e.printStackTrace();
	        }
			
	        if (UserID > 0) {
	        	sis.setSessionID(SessionID);
	        } else {
	        	sis.setSessionID(null);
	        }
			
			return sis;
		}
		
		/**
		 * get the Session end interval - here it is 2 weeks
		 * @return
		 */
		private int getSessionIntervalEnd() {
			
			int EndInterval = 14 * 24 * 60 * 60; //14days * 24hours * 60min * 60sec = 2 weeks in seconds
			
			return EndInterval;
		}
		
		  /**
		   * debug output
		   * 
		   * Write the serialized response out to stdout. This is a very unusual thing
		   * to do, but it allows us to create a static file version of the response
		   * without deploying a servlet.
		   */
		  protected void onAfterResponseSerialized(String serializedResponse) {
			  System.out.println(serializedResponse);
		  }
}//end class
