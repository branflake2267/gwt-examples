package com.tribling.gwt.test.oauth.client.global;

public class Global_Domain {

	/**
	 * get realm = http://host.domain.tld:8180
	 * @param url
	 * @return
	 */
	public static String getRealm(String url) {
		
		url = url.replaceFirst("([#?].*)", "");
		
		String s = "";
		if (url.contains("/")) {
			int end = indexOfslash(url);
			s = url.substring(0, end);
		} else {
			s = url;
		}
		
		return s;
	}
	
	/**
	 * find the index of the slash that separates the domain with the page/folder
	 * @param s
	 * @return
	 */
	private static int indexOfslash(String s) {
		int match = 0;
		for(int i=0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '/') {
				match++;
				if (match == 3) {
					return i;
				}
			}
		}
		return 0;
	}
	
}
