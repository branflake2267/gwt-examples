package com.gawkat.core.client.oauth;

import java.util.Date;

import com.gawkat.core.client.global.Global_Domain;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * oAuth data to pass around from client to server and server to client
 * 
 * @author branflake2267
 * 
 * TODO - get rid of Consumer key - 
 *
 */
public class OAuthTokenData implements IsSerializable {
	
	/*
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
	*/
	
	/* 
	example authorization request
    Authorization: OAuth realm="http://sp.example.com/",
    oauth_consumer_key="0685bd9184jfhq22",
    oauth_token="ad180jjd733klru7",
    oauth_signature_method="HMAC-SHA1",
    oauth_signature="wOJIO9A2W5mFwDgiDvZbTSMK%2FPY%3D",
    oauth_timestamp="137131200",
    oauth_nonce="4572616e48616d6d65724c61686176",
    oauth_version="1.0"
	*/
	
	// domain
	private String realm;
	
	// call back url - in the case of using rpc for this application
	// this will be excluded for now
	private String oauth_callback;
	
	// application id, or user id
	private String oauth_consumer_key;
	

	// random string - protects against replay attacks
	private String oauth_nounce;
	
	// this objects signature - sha1 hash
	private String oauth_signature;
	
	// what this object used to sign itself
	private String oauth_signature_method = "HMAC_SHA1";

	// timestamp this object was created
	private int oauth_timestamp;
	
	
	// token(id) given from server on return
	private String oauth_token;
	
	 // token secret
  private String oauth_token_secret;
	
  
	// version used, another var added for signature uniqueness
	private String oauth_version = "1.0";
	
	
	// what is this objects purpose, what are we requesting?
	public static final int REQUEST_REQUEST_TOKEN = 1;
	public static final int OBTAIN_USER_AUTHORIZATION = 2;
	private int requesting = REQUEST_REQUEST_TOKEN;
	
	// result of request
	public static final int SUCCESS = 1;
	public static final int ERROR = 2;
	public static final int ERROR_NOUSERMATCH = 3; // user can't get logged in
	private int resultOfRequest = 0;
	
	// when need of console output, turn this on
	boolean debug = true;
	int debugIndex = 0;

	/**
	 * constructor - init
	 */
	public OAuthTokenData() {
		this.oauth_nounce = getNonce();
		this.oauth_timestamp = getTimeStamp();
		this.oauth_version = "1.0";
	}
	
	public void setConsumerKey(String consumerKey) {
		this.oauth_consumer_key = consumerKey;
	}
	
	public String getConsumerKey() {
		return this.oauth_consumer_key;
	}
	
	/**
	 * sign the token
	 * 
	 * signature = concat(consumerSecret+tokenSecret)
	 * 
	 * @param url
	 * @param secret
	 */
	public void sign(String url, String secret) {
		
		//debug
		debug("sign: url: " + url + " secret: " + secret);
		
		// removing port for now, rpc method different
		url = url.replaceAll(":[0-9]+", "");
		
		String key = getConcatConsumerSecretAndTokenSecret(secret);
		String data = getSignatureBaseString(url);
		
		Sha1 sha = new Sha1();
		this.oauth_signature = sha.b64_hmac_sha1(key, data);
		
		debug("sign: auth_signature: " + oauth_signature);
	}
	
	/**
	 * verify the token signature
	 * 
	 * @param url
	 * @param consumerSecret
	 * @return
	 */
	public boolean verify(String url, String consumerSecret) {
		
	  debug("verify: url: " + url + " consumerSecret: " + consumerSecret);
		
		// removing port for now, rpc method is different
		url = url.replaceAll(":[0-9]+", ""); 
		
		String verify = this.oauth_signature;
		sign(url, consumerSecret);
		boolean bol = false;
		if (this.oauth_signature.equals(verify)) {
			bol = true;
		}
		
		debug("verify: signature: [true|false]: " + bol);
		
		return bol;
	}
	
	/**
	 * set the request purpose
	 * 
	 * @param requesting
	 */
	public void setRequest(int requesting) {
		this.requesting = requesting;
	}
	
	/**
	 * get the purpose of requesting
	 * @return
	 */
	public int getRequesting() {
		return this.requesting;
	}
	
	/**
	 * what is the result of the request from client, tell the client what happend on the server
	 * 
	 * @param result
	 */
	public void setResult(int result) {
		this.resultOfRequest = result;
	}
	
	/**
	 * what was the result from server side
	 * 
	 * @return
	 */
	public int getResult() {
		return this.resultOfRequest;
	}
	
	/**
	 * set access token. 6.1.2
	 * 
	 * @param token
	 * @param secret
	 */
	public void  setAccessToken(String token, String secret) {
	  this.oauth_token = token;
	  this.oauth_token_secret = secret;
	}
	
	public String getAccessToken_key() {
	  return this.oauth_token;
	}
	
	public String getAccessToken_secret() {
	  return this.oauth_token_secret;
	}
	
	/**
	 * combine the oauth_token_secret and consumer secret
	 * 
	 * @param consumerSecret
	 * @return
	 */
	private String getConcatConsumerSecretAndTokenSecret(String consumerSecret) {
		
		if (consumerSecret == null) {
			consumerSecret = "";
		}
		
		if (oauth_token_secret == null) {
			oauth_token_secret = "";
		}
		
		String concat = "";
		concat += encode(oauth_token_secret) + "&";
		concat += encode(consumerSecret);
		
		return concat;
	}
	
	/**
	 * make a random string - prevents replay attack
	 * 
	 * @return
	 */
	public String getNonce() {
		int nounceLength = 30;
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
		String s = "";
	    for (int i=0; i < nounceLength; i++) {
	    	int rnum = (int) Math.floor(Math.random() * chars.length());
            s += chars.substring(rnum, rnum + 1);
	    }
	    return s;
	}
	
	public int getTimeStamped() {
		return this.oauth_timestamp;
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
	 * generate a string to sign
	 * 
	 * a signing request is built by sorted keys concatenated string separated by &, my chosen method
	 * 
	 * Reference SPEC A.5.1 
	 * 
	 * @param url
	 */
	private String getSignatureBaseString(String url) {
		
		if (url == null) {
			System.out.println("need request url");
		}

		if (url.length() == 0) {
			System.out.println("need request url");
		}
		
		// set realm like http://host.domain.tld:8180
		this.realm = Global_Domain.getRealm(url) + "/";
		
		// make base string
		String s = "";
		s += "RPC&";
		s += encode(url) + "&";
		s += "oauth_callback=" + encode(oauth_callback) + "&";
		s += "oauth_consumer_key=" + encode(oauth_consumer_key) + "&";
		s += "oauth_nounce=" + oauth_nounce + "&";
		s += "oauth_signature_method=" + oauth_signature_method + "&";
		s += "oauth_timestamp=" + oauth_timestamp + "&";
		s += "oauth_token=" + oauth_token + "&";
		s += "oauth_token_secret=" + oauth_token_secret + "&";
		s += "oauth_version=" + oauth_version;
		
		//System.out.println("base:" + s);
		
		return s;
	}
	
	/**
	 * encode String
	 * 
	 * 5.1
	 * 
     * OAuth dictates the only ones you can ignore are: - _ . ~
     * NOTE: http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Functions:encodeURIComponent
     *
	 * @param s
	 * @return
	 */
	private String encode(String s) {
		if (s == null) {
			return "";
		}
		
		String r = "";
		for (int i=0; i < s.length(); i++) {
			r += encodeChar(s.charAt(i));
		}
		
		return r;
	}

	/**
	 * encode char to there asii table using % as the delimiter
	 * 
	 * @param c
	 * @return
	 */
	private String encodeChar(char c) {
		String cS = Character.toString(c);
		
		// Note: I don't encode & or =, b/c those are valid url querystring chars
		String s = "";
		if (cS.matches("[a-zA-Z0-9_.\\-~&=]") == false) {
			s = "%" + Byte.toString((byte) c);
		} else {
			s = cS;
		}
		
		return s;
	}

  public void setDebug(boolean debug) {
    this.debug = debug;
  }
  
  private void debug(String s) {
    if (debug == true) {
      System.out.println(debugIndex + ": " + s);
      debugIndex++;
    }
  }
  
  /**
   * TODO 
   * 
   * @return
   */
  public String getResultMessage() {
    String s = "";
    switch (resultOfRequest) {
    case ERROR:
      s = "Error";
      break;
    case ERROR_NOUSERMATCH:
      s = "I can't find you. Try agian.";
      break;
    }
    return s;
  }
  

}
