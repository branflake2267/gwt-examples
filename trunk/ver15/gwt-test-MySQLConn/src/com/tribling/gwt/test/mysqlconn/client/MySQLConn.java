package com.tribling.gwt.test.mysqlconn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MySQLConn implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// NOTE:
		// You will need to change the Build Path vars to match your
		// gwt-user.jar and MySQL.jar
		// You will need to make sure a JDBC connector is added to your class
		// path.
		// I added my jdbc jar to
		// /opt/classpath/mysql-connector-java-5.1.5/mysql
		// -connector-java-5.1.5-bin.jar

		// you can run RunMeToTestQuery as java application for testing your
		// query (in server package)

		
		
		// load bible info widget for example sql query and using RPC to get the data/info
		BibleInfoWidget bibleInfo = new BibleInfoWidget();
		RootPanel.get("BibleInfo").add(bibleInfo);
		
		
		
	}
}
