package com.tribling.gwt.sandbox.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SandBox implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		String html = "<html>did this work?</HTML><script> my script</script>";

		System.out.println("before:" + html);
		
		html = stripHtml(html);
		
		System.out.println("after:" + html);

	}
	
	
	private String stripHtml(String html) {
		String regex = "(<([^>]+)>)";
		html = html.replaceAll(regex, "");
		return html;
	}
}
