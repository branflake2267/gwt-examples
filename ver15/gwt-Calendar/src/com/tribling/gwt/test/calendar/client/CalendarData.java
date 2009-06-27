package com.tribling.gwt.test.calendar.client;



import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * I used this to put labels on the calendar. 
 * I use this to transport the calendar labels around, like from an rpc call
 * @author branflake2267
 *
 */
public class CalendarData implements IsSerializable {

	public int day = 0;
	public int month = 0;
	public int year = 0;
	public String label = null;
	
	
	/**
	 * constructor - empty
	 */
	public CalendarData() {
	}
	

}
