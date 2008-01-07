package com.tribling.gwt.RPC.adv.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.RPC.adv.client.Person;
import com.tribling.gwt.RPC.adv.client.RPCService;


public class RPCServiceImpl<People> extends RemoteServiceServlet implements RPCService {


	
	
	/**
	 * example method that can return a string
	 * @param s
	 * @return
	 */
	public String myMethod(String s) {
		return "From Server: You sent us '" + s + "'";
	}
	

	/**
	 * example method to define one person
	 * @return
	 */
	public Person defineMe() {
	
		Person Me = new Person();
		
		//Me.setName(new String("Aaron"));
		//Me.setDescription(new String("My middle Name."));
		
		return Me;
	}
	
	
	/**
	 * example method to return an object person
	 */
	public Person myMethodObject(String s) {
		
		Person Me = this.defineMe();
		
		return Me;
	}
	
	
	/**
	 * example method to return an object person with arraylist references
	 */
	public Person[] myMethodObject2(String s) {
		
		Person[] Mes = new Person[2];
		
		Mes[0] = new Person();
		Mes[0].name = new String("Brandon");
		Mes[1] = new Person();
		Mes[1].name = new String("Angie");
		
		return Mes;
	}
	
	
	
	/**
	* Write the serialized response out to stdout. This is a very unusual thing
	* to do, but it allows us to create a static file version of the response
	* without deploying a servlet.
	* 
	* I love this, b/c it helps with debugging in eclipse Console by outputing the serialized vars
	*/
	  protected void onAfterResponseSerialized(String serializedResponse) {
	    System.out.println(serializedResponse);
	  }
}

