package org.gonevertical.demo.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * I use this class to store my mysql recordset in an object that is an array.
 * This will give an example of how I pass data from the server to client in an object, 
 * one of my favourites for its simplicity.
 * 
 * @author branflake2267
 *
 */
public class BibleData implements IsSerializable {

	// fields to store and transfer data
	public String book;
	public int howManyChapters;
	public int howManyVerses;

}
