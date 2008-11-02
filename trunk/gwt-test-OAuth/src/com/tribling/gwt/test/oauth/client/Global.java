package com.tribling.gwt.test.oauth.client;

public class Global {

	public static String removeHtmlTags(String html) {
		String regex = "(<([^>]+)>)";
		html = html.replaceAll(regex, "");
		return html;
	}
	
}
