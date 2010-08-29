package org.gonevertical.core.client.global;

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
	 * holder to identify where an event should go
	 */
	public static final int ZERO = 0;
	
	/**
	 * loading started
	 */
	public static final int LOADING_STARTED = 1;
	
	/**
	 * loading finished
	 */
	public static final int LOADING_FINISHED = 2;

	
	
  public static final int ACCOUNT = 10;
	
  public static final int ACCOUNT_CREATE = 11;
	
	/**
	 * login buttun clicked, try logging in
	 */
	public static final int ACCOUNT_LOGINBUTTONCLICKED = 12;
	
  /**
   * clicked on logged out
   */
  public static final int ACCOUNT_LOGOUT = 13;
  
  /**
   * clicked on forgot pass
   */
  public static final int ACCOUNT_FORGOT_PASSWORD = 14;
  
  /**
   * show account profile
   */
  public static final int ACCOUNT_DISPLAY = 15; // DUPLICATE OF ACCOUNT
  
  /**
   * trigger a demo login
   */
  public static final int ACCOUNT_LOGIN_DEMO = 16;
  
  
  
  
  /**
   * rpc logged in succesfully
   */
  public static final int USER_LOGGEDIN = 20;
  
  /**
   * log out was pushed
   */
  public static final int USER_LOGGEDOUT = 21;
  
  /**
   * a new user was created
   */
  public static final int USER_NEW_CREATED = 22;
  
  /**
   * changed username widget throws this
   */
  public static final int USER_CHANGEDUSERNAME = 23;
  
  
  
  
  /**
   * application has finished loading
   */
  public static final int APPLICATION_LOADED = 30; 
  
  /**
   * defaults need to be set- can be used to trigger button for the choice
   */
  public static int APPLICATION_NEEDSDEFAULTSET = 31;
  
  
  
  /**
   * hover over row event
   */
  public static final int ROW_OVER = 40;
  
  /**
   * hover over row event
   */
  public static final int ROW_OUT = 41;

  /**
   * delete confirmation dialog
   */
  public static final int DELETE_YES = 50;
  
  /**
   * delete confirmation dialog
   */
  public static final int DELETE_NO = 51;
  
  
	/**
	 * paging 
	 */
	public static final int PAGE_CHANGESTART = 60;

	/**
	 * paging
	 */
	public static final int PAGE_CHANGELIMIT = 61;

	
  
  
  public static final int THING_EDIT = 70;
  public static final int THING_VIEW = 71;
  public static final int THING_SELECT = 72;
  
  public static final int THINGSTUFF_TYPECHANGE = 73;
  
  /**
   * redraw thingstuff in thingedit
   */
  public static final int THINGSTUFF_REDRAW = 74;

  /**
   * moused over - put about thing stuff in right to edit
   */
	public static final int THINGSTUFFABOUT_MOUSEOVER = 75;

	/**
	 * hide about thing stuff - TODO not sure if I need this yet
	 */
	public static final int THINGSTUFFABOUT_HIDE = 76;
  
	/**
	 * when changing to a different about thing stuff
	 *   On mouse over before we move to different index, get the data that could be changed
	 */
	public static final int THINGSTUFFABOUT_PREMOUSEOVER = 77;

  
  
 

 


	

	

	
}
