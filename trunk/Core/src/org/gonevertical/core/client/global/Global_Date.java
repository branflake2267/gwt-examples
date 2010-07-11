package org.gonevertical.core.client.global;

import java.util.Date;

public class Global_Date {

	public static String getDate_Eng(Date date) {
		
		if (date == null) {
			return "";
		}
		
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		
		String s = month + "/" + day + "/" + year;
		
		return s;
	}
	
}
