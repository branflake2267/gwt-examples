package org.gonevertical.core.client;

public class Track {

	private String accountGoogleAnalytics;

  public Track() {
  }
  
	public void setGoogleAnalyticsAccount(String googleAnalyticsAccount) {
		this.accountGoogleAnalytics = googleAnalyticsAccount;
  }
  
  /**
   * track an event
   * 
   * @param historyToken
   */
  public void setTrack(String historyToken) {
  	if (accountGoogleAnalytics == null) {
  		System.out.println("Track.setTrack(): Error: Did you forget to set the google analytics account?");
  		return;
  	}
  	
  	if (historyToken == null) {
  		historyToken = "historyToken_null";
  	}
  	
  	historyToken = "/" + historyToken;
  	
  	trackGoogleAnalytics(accountGoogleAnalytics, historyToken);
  }
  
  public void setTrack(String category, String historyToken) {
  	if (accountGoogleAnalytics == null) {
  		return;
  	}
  	
  	if (historyToken == null) {
  		historyToken = "historyToken_null";
  	}
  	
  	historyToken = "/" + category + "/" + historyToken;
  	
  	trackGoogleAnalytics(accountGoogleAnalytics, historyToken);
  }
    
  /**
   * trigger google analytic native js - included in the build
   *   CHECK - DemoGoogleAnalytics.gwt.xml for -> <script src="../ga.js"/>
   * 
   *   http://code.google.com/intl/en-US/apis/analytics/docs/gaJS/gaJSApiEventTracking.html
   * 
   * @param historyToken
   */
  public native void trackGoogleAnalytics(String account, String historyToken) /*-{
        
    try {
    	
    	// setup tracking object with account
    	var pageTracker = $wnd._gat._getTracker(account); // change account please!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	
    	pageTracker._setRemoteServerMode();
    	
    	// turn on anchor observing
    	pageTracker._setAllowAnchor(true)
    	
    	// send event to google server
    	pageTracker._trackPageview(historyToken);
    	    
    } catch(err) {
      
      // debug
      alert('FAILURE: to send in event to google analytics: ' + err);
    }

	}-*/;


  
  
  
}
