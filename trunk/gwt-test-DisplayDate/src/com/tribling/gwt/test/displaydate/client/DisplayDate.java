package com.tribling.gwt.test.displaydate.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DisplayDate implements EntryPoint {

	
  private int TimeStamp;

  
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
	  
		//Get Date Values
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
		 
			//convert Unix Time Stamp (seconds) back to Java Time Stamp (milliseconds)
		long longJavaTime =  (long) (iTimeStamp / .001);
		
		//get start and end of day (seconds)
		this.TimeStamp = UnixTimeStamp; //this will allow u to change the start and end day
		String sStartOfDayTimeStamp = this.getDateStart_TimeStamp();
		String sEndOfDayTimeStamp = this.getDateEnd_TimeStamp();
		
		
		//proof the Start of Day timestamp to date - Today 12:00:00 am
		long StartDateStamp = (long) (Integer.parseInt(sStartOfDayTimeStamp) / .001); //have to change back to milli for java time stamp
		Date StartDate = new Date(StartDateStamp);
		
		//proof the End of Day timestamp to date - Today 11:59:59 pm
		long EndDateStamp = (long) (Integer.parseInt(sEndOfDayTimeStamp) / .001); //have to change back to milli for java time stamp
		Date EndDate = new Date(EndDateStamp);
		
		
		/* HTML */
		//Date To String - will show tz offset in string in browser, not eclipse debug window
		HTML htmlGMTDate = new HTML("<b>GMT Date as String:</b> " + date.toString());
		  
			//java time (milliseconds since epoch)
		HTML htmlJavaTime = new HTML("<b>Java Date/Time (milliseconds since epoch):</b> " + date.getTime());
		
		//Unix Time Stamp (seconds)
		HTML htmlUnixTimeStamp = new HTML("<b>Unix Time Stamp (seconds sinc epoch):</b> " + sTimeStamp);
		
		//Java Time Stamp from Unix Time Stamp
		HTML htmlTimeMilliSeconds2TimeSeconds = new HTML("<b>Convert back to Time in milliseconds to time in seconds:</b> " + Long.toString(longJavaTime));
		
		//Start of Day Time Stamp (seconds)
		HTML htmlStartOfDay = new HTML("<b>Start Of Day Time Stamp Today 12:00:00 am (seconds since epoch):</b> " + sStartOfDayTimeStamp);
		
		//End of Day Time Stamp (seconds)
		HTML htmlEndOfDay = new HTML("<b>End of Day Time Stamp 11:59:59 pm (seconds since epoch):</b> " + sEndOfDayTimeStamp);
		
		//proof Start of Day Time Stamp
		HTML htmlProofStartOfDay = new HTML("<b>Proof Start of Day Time Stamp (Today 12:00:00 am):</b> " + StartDate.toString());
		
		//proof End of Day Time Stamp
		HTML htmlProofEndOfDay = new HTML("<b>Proof Start of Day Time Stamp (Today 11:59:59 pm):</b> " + EndDate.toString());
		
		/* make panel */
		VerticalPanel vp = new VerticalPanel();
		vp.add(htmlGMTDate);
		vp.add(htmlJavaTime);
		vp.add(htmlUnixTimeStamp);
		vp.add(htmlTimeMilliSeconds2TimeSeconds);
		
		vp.add(htmlStartOfDay);
		vp.add(htmlEndOfDay);
		vp.add(htmlProofStartOfDay);
		vp.add(htmlProofEndOfDay);
		
		RootPanel.get().add(vp);

    
  	}
  
  
  	/**
	 * get Start of Day Time Stamp
	 * @return unix time stamp (seconds)
	 */
	private String getDateStart_TimeStamp() {
		
		long ltime = (long) (this.TimeStamp / .001); //my unix time stamp in seconds
		
		//Figure out Unix time stamps for the start of the day and the end of the day
		Date DC = new Date(ltime); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month
		int day = DC.getDate();// current Day
		
		Date dateStart = new Date(); //Today @ 12:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year
		dateStart.setMonth(month); //set month current Month
		dateStart.setDate(day); //set current day of month
		dateStart.setMinutes(0); // minute 0
		dateStart.setHours(0);	 // hour 0
		dateStart.setSeconds(0); // second 0
		int iTimeStamp = (int) (dateStart.getTime() * .001);
		String sStart_TimeStamp = Integer.toString(iTimeStamp);
		
		//helps debugging
		System.out.println("this.Time::: " + this.TimeStamp);
		System.out.println("Start Time::: " + sStart_TimeStamp);
		
		return sStart_TimeStamp;
	}
	
	/**
	 * Get End of Day Time Stamp
	 * @return unix time stamp (seconds)
	 */
	private String getDateEnd_TimeStamp() {
		
		long ltime = (long) (this.TimeStamp / .001); //my unix time stamp in seconds
		
		//Figure out Unix time stamps for the start of the day and the end of the day
		Date DC = new Date(ltime); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month
		int day = DC.getDate();// current Day
		
		Date dateEnd = new Date(); //Today @ 11:59:59 to unix time stamp 
		dateEnd.setYear(year); //set year current Year
		dateEnd.setMonth(month); //set month current Month
		dateEnd.setDate(day); //set current day of month
		dateEnd.setHours(11);	// hour 11
		dateEnd.setMinutes(59); // minute 59
		dateEnd.setSeconds(59); // second 59
		int iTimeStamp = (int) (dateEnd.getTime() * .001);
		String sEnd_TimeStamp = Integer.toString(iTimeStamp);
		
		return sEnd_TimeStamp;
	}
  
}
