package com.tribling.gwt.test.history.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * testing anchor tag navigation
 * 
 * HistoryToken and AnchorTag are one in the same
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HistoryAnchor implements EntryPoint, HistoryListener, TabListener {

	// main menu
	private TabBar mainMenu = new TabBar();
	
	
	// panels
	private VerticalPanel pWidget = new VerticalPanel();
	private VerticalPanel pHeader = new VerticalPanel();
	private VerticalPanel pContent = new VerticalPanel();
	private VerticalPanel pFooter = new VerticalPanel();
	
	
	// query string parameter we may use
	private String id = null;
	private String add = null;
	

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		pWidget.add(pHeader);
		pWidget.add(pContent);
		pWidget.add(pFooter);
		
		// draw my panels to screen, get ready to use them
		RootPanel.get("Content").add(pWidget);
		
		drawMenu();
		
		// init history listener
		initHistorySupport();
		
		
		// after loaded, hide the loading
	    RootPanel.get("loading").setVisible(false);
	}
	
	/**
	 * draw a menu for our content
	 */
	private void drawMenu() {
		mainMenu.addTab("Home");
		mainMenu.addTab("Blog");
		mainMenu.addTab("Wiki");
		mainMenu.addTab("Friends");
		mainMenu.selectTab(0);
		mainMenu.setWidth("400px");
		
		pHeader.add(mainMenu);
		
		mainMenu.addTabListener(this);
	}
	
	/**
	 * draw content - draw the widget I want, via anchor designation
	 * 
	 * prolly could use a switch to be more effecient, using words to see easier
	 * 
	 * @param type
	 */
	private void drawContent(String type) {
		
		if (type == null) {
			return;
		}
		
		//start clean
		pContent.clear();
		
		if (type.equals("home")) {
			Home home = new Home();
			pContent.add(home);
		}
		
		if (type.equals("blog")) {
			Blog blog = new Blog();
			pContent.add(blog);
			
		}
		
		if (type.equals("wiki")) {
			Wiki wiki = new Wiki();
			pContent.add(wiki);
		}
		
		if (type.equals("friends")) {
			Friends friends = new Friends();
			
			//set the param to where we need it
			if (id != null) {
				friends.setID(Integer.parseInt(id));
			}
			
			//set the param to where we need it
			if (add != null) {
				friends.setAnotherParam(add);
			}
			
			pContent.add(friends);
		}
		
	}
	
	/**
	 * track with google analytics
	 */
	private void track(String s) {
		
		trackGA(s);
		
		//debug
		System.out.println("track - google analytics - finished");
	}
	
	/**
	 * track with google analytics
	 * 
	 * Uses JSNI - accesses native javascript in urchin.js and sends a gif request to google analytics
	 */
	private static native void trackGA(String s) /*-{
	 	$wnd._uacct = "UA-2862268-12";
		$wnd._uanchor = 1;
		$wnd.urchinTracker("/HistoryAnchor/" + s); 
	}-*/;

	/**
	 * init history support, start watching for changes in history
	 * 
	 * observe history changes (tokens)
	 */
	private void initHistorySupport() {
	
		History.addHistoryListener(this);
	
		// check to see if there are any tokens passed at startup via the anchor tag
		String token = History.getToken();
		if (token.length() == 0) {
			
			// navigate with anchors
			History.newItem("home");
			
		} else {
			
			onHistoryChanged(token);
			
		}
	}
	
	/**
	 * check history token for parameters to process after #history/anchor?var=1&var3=2&var3=3
	 * 
	 * @param historyToken
	 */
	private String parseHistoryToken(String historyToken) {
		
		if (historyToken == null) {
			return "";
		}
		
		//get parameters from history token
		if (historyToken.contains("?")) {
			HashMap params = getHistoryTokenParameters(historyToken);

			//use the parameters
			setParams(params);

			//get just the history token / anchor tag , not with paramenters
			historyToken = getHistoryToken(historyToken);
		} 
	
		return historyToken;
	}
	
	/**
	 * parse history token ?[var=1&var2=2&var3=3]
	 * 
	 * Parse the history token like querystring - domain.tld#historyToken?params
	 * 
	 * @param historyToken
	 * @return
	 */
	private static HashMap getHistoryTokenParameters(String historyToken) {
	
		//skip if there is no question mark
		if (!historyToken.contains("?")) {
			return null;
		}
		
		//debug
		//System.out.println("parse historyToken: " + historyToken);
		
		// ? position
		int questionMarkIndex = historyToken.indexOf("?") + 1;
		
		//get the sub string of parameters var=1&var2=2&var3=3...
		String[] arStr = historyToken.substring(questionMarkIndex, historyToken.length()).split("&");
		HashMap params = new HashMap();
		for (int i = 0; i < arStr.length; i++) {
			String[] substr = arStr[i].split("=");
			params.put(substr[0], substr[1]);
		}
	
		//debug
		//System.out.println("map: " + params);
	
		return params;
	}
	
	/**
	 * get history token when there are parameters
	 * 
	 * like domain.tld#[historyToken]?params=1
	 *  
	 * @param historyToken
	 * @return
	 */
	private String getHistoryToken(String historyToken) {
		
		//skip if there is no question mark
		if (!historyToken.contains("?")) {
			return "";
		}

		//get just the historyToken/anchor tag
		String[] arStr = historyToken.split("\\?");
		historyToken = arStr[0];
	
		return historyToken;
	}
	

	
	/**
	 * use the parameters
	 * @param params
	 */
	private void setParams(HashMap params) {
		
		if (params == null) {
			return;
		}
			
		if (params.get("id") != null) {
			this.id = (String) params.get("id");
		}
		
		if (params.get("add") != null) {
			this.add  = (String) params.get("add");
		}
		
	}
	
	/**
	 * are there params in historyToken
	 * 
	 * @return
	 */
	private boolean isParamsInHistoryToken() {
		String s = History.getToken();
		
		if (s.contains("?")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * observe tabs
	 * 
	 * navigate with the anchor tag / history token in the URL(QueryString/URI)
	 */
	public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
		
		if (sender == mainMenu) {
			
			switch (tabIndex) {
			
			case 0: 
				//Navigate with anchors
				History.newItem("home");
				break;
			case 1: 
				History.newItem("blog");
				break;
			case 2:
				History.newItem("wiki");
				break;
			case 3: 
				
				// the menu select creates an event too
				// skip a duplicate event when there are parameters using the tabbar
				if (isParamsInHistoryToken()) {
					//skip
				} else {
					History.newItem("friends");
				}
				
				break;
			default: 
				History.newItem("home");
				break;
			}
			
		}
		
	}
	
	/**
	 * observe tabs
	 */
	public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
		return true;
	}
	
	/**
	 * This is where we Navigate from !!!! 
	 * This observes History Change - Observes Anchors
	 * 
	 * forward, back, load(b/c of set token)
	 * 
	 */
	public void onHistoryChanged(String historyToken) {
		
		//just in case? I hate the null errors poppin up on the comparison objects
		if (historyToken == null) {
			return;
		}
		
		// are there any parameters in the history token/Anchor we need to use?
		historyToken = parseHistoryToken(historyToken);
		
		
		// main home page without login
		if (historyToken.equals("home")) {
			drawContent("home");
			mainMenu.selectTab(0);
		}
		
		if (historyToken.equals("blog")) {
			drawContent("blog");
			mainMenu.selectTab(1);
		}
		
		if (historyToken.equals("wiki")) {
			drawContent("wiki");
			mainMenu.selectTab(2);
		}
		
		if (historyToken.equals("friends")) {
			drawContent("friends");
			mainMenu.selectTab(3);
		}
		
		track(historyToken);
		
		System.out.println("history event ::: " + historyToken);
	}
	

	
}
