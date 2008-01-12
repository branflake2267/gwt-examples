package com.tribling.gwt.test.displaydate.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
		int UnixTimeStamp = (int) (date.getTime() * .001);
		
		//get unix time stamp (seconds)
		Long lTimeStamp = date.getTime(); //time in milleseconds since the epoch
		int iTimeStamp = (int) (lTimeStamp * .001); //(Cast) to Int from Long, Seconds since epoch
		String sTimeStamp = Integer.toString(iTimeStamp); //seconds to string
	 
		
		//get the gmt date - will show tz offset in string in browser, not eclipse debug window
		String TheDate = date.toString();
	  
		//render date
		Label label = new Label(TheDate);   
		RootPanel.get().add(label);
    
  	}
}
