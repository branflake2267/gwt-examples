package org.gonevertical.demo.server.db;

import org.apache.log4j.Logger;

public class ServerPersistence {

	private Logger logger;

	/**
	 * constructor - keep track of stuff on the server side during a call
	 */
	public ServerPersistence() {
		
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
}
