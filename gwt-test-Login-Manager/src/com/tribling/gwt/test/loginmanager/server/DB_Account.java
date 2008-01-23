package com.tribling.gwt.test.loginmanager.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tribling.gwt.test.loginmanager.client.Account;
import com.tribling.gwt.test.loginmanager.client.SignInStatus;

public class DB_Account extends DB_Conn {

	/**
	 * constructor
	 */
	public DB_Account() {
	}
	
	/**
	 * Save Account information
	 * @param account
	 * @return
	 */
	public Account saveAccount(Account account) {
		
		System.out.println("Saving Account Logic Running");
		
		String sDisplayError = null;
		String Query = null;
		int UserID = 0;
		int rsUserID = 0;
		String sPassword_Hash = null;
		String sPassAdd = "";
		boolean UserNameExists = false;
		
		//get the account object vars
		String sDateCreated = Integer.toString(account.getTimeStamp());
		String sFirstName = account.getFirstName();
		String sLastName = account.getLastName();
		String sUserName = account.getUserName();
		String sPassword = account.getPassword();
		
		
		//Is the user logged in?
		if (account.getSessionID() != null) {
			System.out.println("Has Session ID:" + account.getSessionID());
			UserID = getUserID(account.getSessionID());
		}
		
		
		//check UserName exists, if new user 
		if (UserID < 0) {
			UserNameExists = checkUserNameExists(sUserName);
		
			System.out.println("User Exists");
			
			if (UserNameExists == true) {
				sDisplayError = "User name already exists.";
			}
		}
		
		//make new hash for password
		if (sPassword != null) {
			
			System.out.println("making password hash");
			
			sPassword_Hash = hashPassword(sPassword);
			
			sPassAdd = " ,Password='"+sPassword_Hash+"' ";
		}
		
		
		if (UserNameExists == false) {
			if (UserID > 0) { //update
				Query = "UPDATE `user` SET LastUpdated='"+sDateCreated+"', FirstName='"+sFirstName+"', LastName='"+sLastName+"' " +
						" "+sPassAdd+" WHERE (UserID='"+ UserID +"');";
			} else { //insert
				Query = "INSERT INTO `user` SET FirstName='"+sFirstName+"', LastName='"+sLastName+"', UserName='"+sUserName+"', " +
						"Password_Hash='"+sPassword_Hash+"', DateCreated='"+sDateCreated+"';";
			}
			
			System.out.println("Saving User: " + Query);
			try {
	        	Connection connection = getConn();
	            Statement update = connection.createStatement();
	            update.executeUpdate(Query);
	            
	            ResultSet rs = update.getGeneratedKeys(); 
	            if (rs != null && rs.next()) { 
	            	rsUserID = rs.getInt(1);  //unique record to which the session is stored
	            }

	            connection.close();
	        } catch(Exception e) { //debug out output this way
	        	System.err.println("Mysql Statement Error: " + Query);
	        	e.printStackTrace();
	        }
		}
        
		//rpc object for return
		Account accountReturn = new Account();
		
        //send back sessionID rsUserID = sign in user, UserID = get current sessionid
        if (rsUserID > 0) {
        	DB_SignIn SignIn = new DB_SignIn();
        	SignInStatus sis = SignIn.processSignIn(sUserName, sPassword);
        	
        	accountReturn.setSessionID(sis.getSessionID());
        }
		
        
		if (sDisplayError != null) {
			accountReturn.setDisplayError(sDisplayError);
		}
        
		return accountReturn;
	}
	
	
	public boolean checkUserNameExists(String UserName) {
        
		String Query = "SELECT UserID FROM `user` WHERE (UserName='"+UserName+"')";
		
		boolean Flag = false;
		
		try {
        	Connection connection = getConn();
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery(Query);
            while (result.next()) {
              Flag = true; //username exists
            }
            result.close();
            connection.close();
        } catch(Exception e) {
        	System.err.println("Mysql Statement Error: UserName Exists: " + Query);
        	e.printStackTrace();
        	Flag = false;
        }
        
        return Flag;
	}
	
	/**
	 * hash the password for storage in db
	 * 
	 * (create new user entry in db storing ONLY username and hash, *NOT* the password).
	 */
	public String hashPassword(String Password) {
		String Password_Hash = BCrypt.hashpw(Password, BCrypt.gensalt());
		
		System.out.println("Hash Created: " + Password_Hash);
		
		return Password_Hash;
	}
	
	
	
}
