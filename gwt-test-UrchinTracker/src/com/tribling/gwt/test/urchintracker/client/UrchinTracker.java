package com.tribling.gwt.test.urchintracker.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Using gwt-linux milestone 2
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UrchinTracker implements EntryPoint, HistoryListener, ClickListener {

	private Hyperlink h1 = new Hyperlink("View It", "urchinView");
	private Hyperlink h2 = new Hyperlink("Edit It", "urchinEdit");
	private Hyperlink h3 = new Hyperlink("Test", "urchinAnchor");
	
	//Querystring / URI parameters to do something with
	private String id; 
	private String add;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		// set up hyper links 
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(5);
		hp.add(h1);
		hp.add(h2);
		hp.add(h3);
		
		RootPanel.get().add(hp);
		
		//listen for clickscheck the source code out if your interested.
		h1.addClickListener(this);
		h2.addClickListener(this);
		h3.addClickListener(this);
		
		//watch changes in history
		initHistorySupport();
	}
	
	/**
	 * Send tracking to google analytics
	 * 
	 * http://www.google.com/analytics/InstallingGATrackingCode.pdf - how to with reference
	 * http://www.google.com/support/analytics/bin/answer.py?hl=en&answer=76305 - reference for urchin.js, ga.js
	 * 
	 * This works great using the urchin.js for the JSNI
	 * You can see how things can work here, and run with it somewhere else
	 * 
	 * Verify your get requests in firefox to Google Analytics with tamper data
	 */
	private static native void track(String s) /*-{

		// urchin.js is included in UrchinTracker.gwt.xml for use here
		// cool, this works - Make sure you include the urchin.js in the xml file
		$wnd._uacct = "UA-2862268-12";
		$wnd._uanchor = 1; //use anchors with campaigns
		$wnd.urchinTracker("/UrchinTracker/" + s); // I had to have pages after first request
		
		
		 
		// I can not find out why this will not work yet. 
		// I have been trying to reverse engineer ga.js, but not sure I will go much further
		// since I got urchin to work for now. Its taking to much time to figure out.
		// I think there logic that resides in ga.js returns before the get request.
		// I can get the functions to work, but for some reason not the right vars
		// To tired now, try again later.
		//
		// var pageTracker = $wnd._gat._getTracker("UA-2862268-12"); //account //utmac
		// $wnd.pageTracker._setDomainName("gawkat.com"); //domain
		// $wnd.pageTracker._setVar("urchin_Testing"); //segmenting
		// $wnd.pageTracker._setAllowAnchor(true); //anchor
		// $wnd.pageTracker._initData();
		// $wnd.pageTracker._trackPageview();


		// tried my script using the trackers
		// I wasn't able to get the includes in the right order
		// my track.js included in UrchinTracker.gwt.xml
		// $wnd.trackGA();


		// debug (Alert)
		// let us know something happened here
		$wnd.alert("Sent tracking info to Goolge Anayltics");
		
	}-*/;
	
	
	/**
	 * init history support, start watching for changes in history
	 * 
	 * observe history changes (tokens)
	 */
	public void initHistorySupport() {
	
		History.addHistoryListener(this);
	
		// check for history token / anchor tag in  domain.tld#anchor/historytoken
		String token = History.getToken();
		if (token.length() == 0) {
			
			// navigate with anchors
			History.newItem("home"); //default anchor
			
		} else {
			onHistoryChanged(token);
		}
	}
	
	/**
	 * parse history token ?[var=1&var2=2&var3=3]
	 * 
	 * Parse the history token like querystring - domain.tld#historyToken?params
	 * 
	 * @param historyToken
	 * @return
	 */
	private static HashMap parseHistoryTokenParameters(String historyToken) {
	
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
			
			//debug
			//System.out.println("param[" + i + "]=" + arStr[i]);
		}
	
		//debug
		//System.out.println("map: " + params);
	
		return params;
	}
	
	/**
	 * get anchor tag by it self when there are parameters
	 * 
	 * @param historyToken
	 * @return
	 */
	private String getHistoryTokenWithParameters(String historyToken) {
		
		//skip if there is no question mark
		if (!historyToken.contains("?")) {
			return null;
		}

		String[] arStr = historyToken.split("\\?");
	
		historyToken = arStr[0];
	
		//debug
		//System.out.println("historyToken: " + historyToken);
	
		return historyToken;
	}
	
	/**
	 * check history token for parameters to process after #history/anchor?var=1&var3=2&var3=3
	 * 
	 * @param historyToken
	 */
	private String getAnchorTagParameters(String historyToken) {
		
		if (historyToken == null) {
			return "";
		}
		
		if (historyToken.contains("?")) {
		
			//get parameters from history token
			HashMap params = parseHistoryTokenParameters(historyToken);
			
			if (params.get("id") != null) {
				this.id = (String) params.get("id");
			}
			
			if (params.get("add") != null) {
				this.add  = (String) params.get("add");
			}
			
			historyToken = getHistoryTokenWithParameters(historyToken);
		} 
	
		//debug
		return historyToken;
	}
	
	/**
	 * History observing
	 * 
	 * forward, back, load(b/c of set token)
	 * 
	 * TODO - finish this
	 */
	public void onHistoryChanged(String historyToken) {
		
		historyToken = getAnchorTagParameters(historyToken);
		
		// "view" anchor used  - do this
		if (historyToken.equals("urchinView")) {
			System.out.println("historyToken: " + historyToken);
		}
		
		// "edit" anchor used - do this
		if (historyToken.equals("urchinEdit")) {
			System.out.println("historyToken: " + historyToken);
		}
		
		//"archive" anchor used - do this
		if (historyToken.equals("urchinAnchor")) {
			System.out.println("historyToken: " + historyToken);
			
		}
		
		track(historyToken);
		
		//debug
		System.out.println("history event ::: " + historyToken);
	}
	
	

	/**
	 * Observe
	 */
	public void onClick(Widget sender) {
		
		if (sender == h1) {
			RootPanel.get().add(new Label("view"));
		}
		
		if (sender == h2) {
			RootPanel.get().add(new Label("edit"));
		}
		
		if (sender == h3) {
			RootPanel.get().add(new Label("test"));
		}
		
	}
	
}
