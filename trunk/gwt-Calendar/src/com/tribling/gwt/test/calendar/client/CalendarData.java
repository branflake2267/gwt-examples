package com.tribling.gwt.test.calendar.client;



import com.google.gwt.user.client.rpc.IsSerializable;

public class CalendarData implements IsSerializable {

	public int day = 0;
	public int month = 0;
	public int year = 0;
	public String label = null;
	
	public int desiredResponse = 0; //0,1=yes, 2=no, for main todo, expecting yes or no 
	public String yesno = null;
	
	/**
	 * constructor - empty
	 */
	public CalendarData() {
	}
	

}
