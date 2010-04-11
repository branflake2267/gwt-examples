package org.gonevertical.demo.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * I was building a example class to get the books of the bible from my mysql database
 *
 * Download Bible Database
 * here is where i got the bible sql database
 * http://sourceforge.net/project/showfiles.php?group_id=186532&package_id=249676&release_id=548680
 *
 * @author branflake2267
 *
 */
public class DB_QueryStuff extends DB_Conn {

	//class vars
	private String BookQuery;
	private String[] StartBook;

	/**
	 * constructor
	 */
	public DB_QueryStuff() {
		this.BookQuery = "SELECT en FROM `book`;";
	}


	/**
	 * method to test query
	 * 
	 * This is an example query, not used in rpc
	 * This is run in "RunMeToTestQuery.java" as a java application
	 */
	public void queryMyDB() {

		String Query = this.BookQuery;

		try {
			Connection connection = this.getConn();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(Query);

			//get count so I can set my array to the right length
			int rsSize = getResultSetSize(result); 
			this.StartBook = new String[rsSize];

			int i = 0;
			while (result.next()) {

				this.StartBook[i] = result.getString(1);

				//this is a great way to see what is going on in the Eclipse Console or using a JVM
				System.out.println("Book: " + result.getString(1));

				i++;
			}
			connection.close();
		} catch(Exception e) {

			//debug out output this way
			System.err.println("Mysql Statement Error: StartBook: " + Query);
			e.printStackTrace();

		}

	}

}
