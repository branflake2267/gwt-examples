package com.tribling.gwt.test.displaydate.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DisplayDate implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

	  
	  	/* 
	  	 * gwt ubuntu amd64 linux eclipse debug
	  	 * 
	  	 * Date won't render the right time zone in my eclipse debugger
	  	 * You will need to compile/browse in browser 
	  	 * 
	  	 */
	  
	  	//get the date stuff to work with
		Date date = new Date();
		int Month = date.getMonth();
		int Day = date.getDate();
		int Year = date.getYear();
		int Hour = date.getHours();
		int min = date.getMinutes();
		int sec = date.getSeconds();
		int tz = date.getTimezoneOffset();
		int UnixTimeStamp = (int) (date.getTime() * .001); //unix time stamp
		
		//get unix time stamp (seconds)
		long lTimeStamp = date.getTime(); //time in milleseconds since the epoch
		int iTimeStamp = (int) (lTimeStamp * .001); //(Cast) to Int from Long, Seconds since epoch
		String sTimeStamp = Integer.toString(iTimeStamp); //seconds to string
	 
		
		
		
		
		/* Labels */
		
		//Date To String - will show tz offset in string in browser, not eclipse debug window
		Label labelGMTDate = new Label("GMT Date as String: " + date.toString());
	  
		//Unix Time Stamp
		Label labelUnixTimeStamp = new Label("Unix Time Stamp: " + sTimeStamp);
		
		
		/* make panel */
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(labelGMTDate);
		vp.add(labelUnixTimeStamp);
		
		
		
		RootPanel.get().add(vp);
    
  	}
}
