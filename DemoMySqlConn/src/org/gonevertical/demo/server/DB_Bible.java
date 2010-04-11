package org.gonevertical.demo.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.gonevertical.demo.client.BibleData;

/**
 * I extend the DB_Conn abstract class, then I don't have to rewrite code
 * 
 * @author branflake2267
 *
 */
public class DB_Bible extends DB_Conn {

	/**
	 * constructor - nothing to do
	 */
	public DB_Bible() {
		// nothing to do
	}

	/**
	 * get the bible info.
	 * 
	 * I typically keep all method names in rpc request from client to server the same.
	 * This helps when your project gets huge
	 * 
	 * Download the Bible Database
	 * here is where i got the bible sql database
	 * http://sourceforge.net/project/showfiles.php?group_id=186532&package_id=249676&release_id=548680
	 * 
	 * @return bibleData - object array
	 */
	public BibleData[] getBibleInfo() {

		String query = "SELECT bid, en FROM book;";

		// prepare for rpc transport
		BibleData[] bibleData = null;

		try {
			Connection connection = getConn();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(query);

			// init object into the size we need, like a recordset
			int rsSize = getResultSetSize(result); //size the array
			bibleData = new BibleData[rsSize];

			int i = 0;
			while (result.next()) {

				// init each object in the array !!!!
				bibleData[i] = new BibleData(); // <-THIS IS CRITICAL TO REMEMBER!!!! init each array with the object type (I forget to do this so often)

				int bid = result.getInt(1);
				bibleData[i].book = result.getString(2);
				bibleData[i].howManyChapters = getHowManyChapters(bid);
				bibleData[i].howManyVerses = getHowManyVerses(bid);

				i++;
			}

			// clean up
			result.close();
			connection.close();

		} catch(Exception e) {

			System.err.println("Mysql Statement Error: " + query);
			e.printStackTrace();

		}

		// return the array
		return bibleData;
	}

	/**
	 * get how many chapters are in a book
	 * 
	 * @param book
	 * @return
	 */
	private int getHowManyChapters(int bid) {

		String sbid = Integer.toString(bid);

		String query = "SELECT Distinct chapter FROM bible where book = '" + sbid + "'";

		int rsSize = 0;
		try {
			Connection connection = getConn();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(query);

			rsSize = getResultSetSize(result); // size the array

			// clean up
			result.close();
			connection.close();

		} catch (Exception e) {

			System.err.println("Mysql Statement Error: " + query);
			e.printStackTrace();

		}

		return rsSize;
	}

	/**
	 * get how many verses are in a book
	 * 
	 * @param book
	 * @return
	 */
	private int getHowManyVerses(int bid) {

		String sbid = Integer.toString(bid);

		String query = "SELECT Verse FROM bible where book = '" + sbid + "'";

		int rsSize = 0;
		try {
			Connection connection = getConn();
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(query);

			rsSize = getResultSetSize(result); // size the array

			// clean up
			result.close();
			connection.close();

		} catch (Exception e) {

			System.err.println("Mysql Statement Error: " + query);
			e.printStackTrace();

		}

		return rsSize;

	}



}
