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
	
	/**
	 * loading started
	 */
	public static final int LOADING_STARTED = 10;
	
	/**
	 * loading finished
	 */
	public static final int LOADING_FINISHED = 11;

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
 
  public static final int DELETE_YES = 20;
  public static final int DELETE_NO = 21;
  
  public static final int THING_EDIT = 30;
  public static final int THING_VIEW = 31;
  
  public static final int THINGSTUFF_TYPECHANGE = 40;
  
  public static final int ACCOUNT_CREATE = 100;
  public static final int ACCOUNT_PROFILE = 101;
  
  public static final int ROW_OVER = 50;
  public static final int ROW_OUT = 51;
  
  
  /**
   * redraw thingstuff in thingedit
   */
  public static final int THINGSTUFF_REDRAW = 60;

  /**
   * moused over - put about thing stuff in right to edit
   */
	public static final int THINGSTUFFABOUT_MOUSEOVER = 61;

	/**
	 * hide about thing stuff - TODO not sure if I need this yet
	 */
	public static final int THINGSTUFFABOUT_HIDE = 62;
  
	/**
	 * when changing to a different about thing stuff
	 *   On mouse over before we move to different index, get the data that could be changed
	 */
	public static final int THINGSTUFFABOUT_PREMOUSEOVER = 63;

	/**
	 * paging 
	 */
	public static final int PAGE_CHANGESTART = 10;

	/**
	 * paging
	 */
	public static final int PAGE_CHANGELIMIT = 11;
}
