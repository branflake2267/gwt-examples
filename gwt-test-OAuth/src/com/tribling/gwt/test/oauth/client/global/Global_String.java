package com.tribling.gwt.test.oauth.client.global;

public class Global_String {

	public static String removeHtmlTags(String html) {
		String regex = "(<([^>]+)>)";
		html = html.replaceAll(regex, "");
		return html;
	}
	
	public static String removeBadHtmlTags(String html) {	
		html = html.replaceAll("(<(?i:/script|script)[^>]*>)", "");
		
		html = html.replaceAll("(<(?i:/embed|embed)[^>]*>)", "");
		html = html.replaceAll("(<(?i:/applet|applet)[^>]*>)", "");
		html = html.replaceAll("(<(?i:/object|object)[^>]*>)", "");
		
		html = html.replaceAll("(<(?i:/html|html)[^>]*>)", "");
		html = html.replaceAll("(<(?i:/body|body)[^>]*>)", "");

		html = html.replaceAll("(<(?i:/frame|frame)[^>]*>)", "");
		html = html.replaceAll("(<(?i:/frameset|frameset)[^>]*>)", "");
		html = html.replaceAll("(<(?i:/iframe|iframe)[^>]*>)", "");
		
		// options
		//html = html.replaceAll("(<(?i:/img|img)[^>]*>)", "");
		//html = html.replaceAll("(<(?i:/style|style)[^>]*>)", "");
		//html = html.replaceAll("(<(?i:/layer|layer)[^>]*>)", "");
		//html = html.replaceAll("(<(?i:/link|link)[^>]*>)", "");
		//html = html.replaceAll("(<(?i:/ilayer|ilayer)[^>]*>)", "");
		//html = html.replaceAll("(<(?i:/meta|meta)[^>]*>)", "");
		
		return html;
	}
	
}
