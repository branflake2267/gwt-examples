package com.gawkat.core.client.global;

/**
 * instead of keeping events in text, better in int, take less space
 * Also, since events can be changed deep into a widgets hierarchy, this keeps consistency
 * I can manage the same events structure here
 * 
 * @author branflake2267
 *
 */
public class EventManager {

  // clicked on login
  final public static int LOGIN = 1;
  
  // clicked on logged out
  final public static int LOGOUT = 2;
  
  // clicked on forgot pass
  final public static int FORGOT_PASSWORD = 3;
  
  // rpc logged in succesfully
  final public static int LOGGEDIN = 4;
  
  // log out was pushed
  final public static int LOGGEDOUT = 5;
  
  // a new user was created
  final public static int NEW_USER_CREATED = 6;
  
  // show account profile
  final public static int PROFILE = 7;
}
