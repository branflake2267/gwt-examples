package com.tribling.gwt.sandbox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SandBox implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		String testSplit = "\"1:a,b,c,d\",\"2,e,f,g,h\",\"3,i,j,k,l,m\",\"4,n,o,p,q\"";
		System.out.println("testSplit: " + testSplit);

		String[] arSplit = testSplit.split("\",\"");

		for (int i=0; i < arSplit.length; i++) {

			String s = arSplit[i]; 

			s = s.replaceFirst("^\"", "");
			s = s.replaceFirst("\"$", "");

			System.out.println(i + ". split: " + arSplit[i] + " Fixed:" + s);

		}



	}
}
