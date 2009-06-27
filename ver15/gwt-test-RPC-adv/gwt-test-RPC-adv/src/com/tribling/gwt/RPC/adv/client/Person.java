package com.tribling.gwt.RPC.adv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Hold relevant data for Person. This class is meant to be serialized in RPC
 * calls.
 */
public class Person implements IsSerializable {

	/**
	* This field is a Set that must always contain Strings.
	* 
	* @gwt.typeArgs <java.lang.String>
	*/
	public String description;

	/**
	* This field is a Set that must always contain Strings.
	* 
	* @gwt.typeArgs <java.lang.String>
	*/
	public String name;


    
    
}