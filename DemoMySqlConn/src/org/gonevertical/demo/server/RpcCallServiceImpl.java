package org.gonevertical.demo.server;

import org.gonevertical.demo.client.BibleData;
import org.gonevertical.demo.client.RpcCallService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcCallServiceImpl extends RemoteServiceServlet implements RpcCallService {

	/**
	 * get the books of the bible and info in an object array
	 * This is my favorite way to get recordset data
	 * 
	 * @return bibleData (array)
	 */
	public BibleData[] getBibleInfo() {
		
		DB_Bible db = new DB_Bible();
		BibleData[] bibleData = db.getBibleInfo();
		
		return bibleData;
	}
	
	
	
	
	/**
	 * use RunMeTOTestQuery to test a query - its much easier to test
	 */
	public void testQuery() {
		
		//setup the object
		DB_QueryStuff db = new DB_QueryStuff();
		//run my method
		db.queryMyDB();
		
	}
}
