package org.gonevertical.core.test;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.oauth.Sha1;

public class Run_Create_Application_Password {

	public static void main(String[] args) {
		
		String password = "password";
		
		Sha1 sha = new Sha1();

		String secret = null;
		if (password != null) {
			secret = sha.hex_hmac_sha1(ClientPersistence.PASSWORD_SALT, password);
		}
		
		//debug
		System.out.println("password=" + password + " secret=" + secret);
		
	}
	

		
	
}
