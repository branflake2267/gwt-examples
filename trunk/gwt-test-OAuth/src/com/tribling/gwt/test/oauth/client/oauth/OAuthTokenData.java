package com.tribling.gwt.test.oauth.client.oauth;

import java.util.Date;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * oAuth data to pass around from client to server and server to client
 * 
 * @author branflake2267
 *
 */
public class OAuthTokenData implements IsSerializable {
	
	/*
	 * 
	Service Provider:
        A web application that allows access via OAuth. 
    User:
        An individual who has an account with the Service Provider. 
    Consumer:
        A website or application that uses OAuth to access the Service Provider on behalf of the User. 
    Protected Resource(s):
        Data controlled by the Service Provider, which the Consumer can access through authentication. 
    Consumer Developer:
        An individual or organization that implements a Consumer. 
    Consumer Key:
        A value used by the Consumer to identify itself to the Service Provider. 
    Consumer Secret:
        A secret used by the Consumer to establish ownership of the Consumer Key. 
    Request Token:
        A value used by the Consumer to obtain authorization from the User, and exchanged for an Access Token. 
    Access Token:
        A value used by the Consumer to gain access to the Protected Resources on behalf of the User, instead of using the User’s Service Provider credentials. 
    Token Secret:
        A secret used by the Consumer to establish ownership of a given Token. 
    OAuth Protocol Parameters:
        Parameters with names beginning with oauth_. 
     *
	 */
	
	/* 
	 * example authorization request
    Authorization: OAuth realm="http://sp.example.com/",
    oauth_consumer_key="0685bd9184jfhq22",
    oauth_token="ad180jjd733klru7",
    oauth_signature_method="HMAC-SHA1",
    oauth_signature="wOJIO9A2W5mFwDgiDvZbTSMK%2FPY%3D",
    oauth_timestamp="137131200",
    oauth_nonce="4572616e48616d6d65724c61686176",
    oauth_version="1.0"
	 *
	 */
	
	public String oauth_callback;
	
	public String oauth_consumer_key;
	
	public String oauth_nounce;
	
	public String oauth_signature;
	
	public String oauth_signature_method;

	public int oauth_timestamp;
	
	public String oauth_token;
	
	public String oauth_token_secret;
	
	public String oauth_version = "1.0";

	/**
	 * constructor - init
	 */
	public OAuthTokenData() {
		oauth_nounce = getNounce();
		oauth_timestamp = getTimeStamp();
		oauth_version = "1.0";
	}
	
	/**
	 * generate a string to sign
	 * 
	 * a signing request is built by sorted keys concatenated string separated by &, my chosen method
	 * 
	 * Reference SPEC A.5.1 
	 * 
	 * @param url
	 */
	public String getSignatureBaseString(String url) {
		
		if (url == null) {
			System.out.println("need request url");
		}

		if (url.length() == 0) {
			System.out.println("need request url");
		}
		
		// skip signature, thats what this does
		String s = "";
		s += "RPC&";
		s += url + "&";
		s += "oauth_callback=" + oauth_callback+ "&";
		s += "oauth_consumer_key=" + oauth_consumer_key+ "&";
		s += "oauth_nounce=" + oauth_nounce+ "&";
		s += "oauth_signautre_method="+ oauth_signature_method+ "&";
		s += "oauth_timestamp=" + oauth_timestamp+ "&";
		s += "oauth_token=" + oauth_token+ "&";
		s += "oauth_token_secret=" + oauth_token_secret+ "&";
		s += "oauth_version=" + oauth_version;
		
		s = encode(s);
		
		return s;
	}
	
	/**
	 * make a random string - prevents replay attack
	 * 
	 * @return
	 */
	private String getNounce() {
		int nounceLength = 6;
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
		
		String s = "";
	    for (int i=0; i < nounceLength; i++) {
	    	int rnum = (int) Math.floor(Math.random() * chars.length());
            s += chars.substring(rnum, rnum+1);
	    }
	    return s;
	}
	
	/**
	 * create a timestamp, seconds since the epoch. 
	 * 
	 * @return
	 */
	private int getTimeStamp() {
		Date date = new Date();
		int iTimeStamp = (int) (date.getTime() * .001);
		return iTimeStamp;
	}
		
	/**
	 * encode signature base (url)
	 * 
     * encode ignores: - _ . ! ~ * ' ( )
     * OAuth dictates the only ones you can ignore are: - _ . ~
     * Source: http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Functions:encodeURIComponent
     *
	 * @param s
	 * @return
	 */
	private String encode(String s) {
		if (s == null) {
			return "";
		}
		
		s = URL.encode(s);
		
        s = s.replace("!", "%21");
        s = s.replace("*", "%2A");
        s = s.replace("'", "%27");
        s = s.replace("(", "%28");
        s = s.replace(")", "%29");
		
		return s;
	}
	
}
