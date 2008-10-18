package com.tribling.gwt.test.oauth.client.oauth;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TokenData implements IsSerializable {

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
	
	
	public String oauth_consumer_key;
	
	public String oauth_token;
	
	public String oauth_token_secret;
	
	public String oauth_signature_method;
	
	public String oauth_signature;
	
	public int oauth_timestamp;
	
	public String oauth_nounce;
	
	public String oauth_callback;

	public String oauth_version;
		
	/**
	 * constructor - nothing to do here
	 */
	public TokenData() {
		// nothing
	}
	
	/**
	 * A. Consumer Requests Request Token
	 * 
	 * @param oauth_consumer_key
	 * @param oauth_signature_method
	 * @param oauth_signature
	 * @param oauth_timestamp
	 * @param oauth_nonce
	 * @param oauth_version - [optional]
	 */
	public void setConsumerRequestsRequestToken(
			String oauth_consumer_key, 
			String oauth_signature_method, 
			String oauth_signature, 
			int oauth_timestamp, 
			String oauth_nonce,
			String oauth_version) {
		this.oauth_consumer_key = oauth_consumer_key;
		this.oauth_signature_method = oauth_signature_method;
		this.oauth_signature = oauth_signature;
		this.oauth_timestamp = oauth_timestamp;
		this.oauth_nounce = oauth_nonce;
		this.oauth_version = oauth_version; 
	}
	
	/**
	 * B. Service Provider Grants Request Token
	 * 
	 * @param oauth_token
	 * @param oauth_token_secret
	 */
	public void setServiceProviderGrantsRequestToken(
			String oauth_token, 
			String oauth_token_secret) {
		this.oauth_token = oauth_token;
		this.oauth_token_secret = oauth_token_secret;
	}
	
	/**
	 * C. Consumer directs user to service provider
	 * 
	 * @param oauth_token - [optional]
	 * @param oauth_callback - [optional]
	 */
	public void setConsumerDirectUserToServiceProvider(
			String oauth_token, 
			String oauth_callback) {
		this.oauth_token = oauth_token;
		this.oauth_callback = oauth_callback;
	}
	
	/**
	 * D. Service provider directs user to consumer
	 * 
	 * @param oauth_token - [optional]
	 */
	public void setServiceProviderDirectsUserToConsuer(
			String oauth_token) {
		this.oauth_token = oauth_token;
	}

	/**
	 * E. Consumer requests access token
	 * 
	 * @param oauth_consumer_key
	 * @param oauth_token
	 * @param oauth_signature_method
	 * @param oauth_signature
	 * @param oauth_timestamp
	 * @param oauth_nonce
	 * @param oauth_version - [optional]
	 */
	public void setConsumerRequestsAccessToken(
			String oauth_consumer_key, 
			String oauth_token,
			String oauth_signature_method,
			String oauth_signature,
			int oauth_timestamp,
			String oauth_nonce,
			String oauth_version) {
		this.oauth_consumer_key = oauth_consumer_key;
		this.oauth_token = oauth_token;
		this.oauth_signature_method = oauth_signature_method;
		this.oauth_signature = oauth_signature;
		this.oauth_timestamp = oauth_timestamp;
		this.oauth_nounce = oauth_nonce;
		this.oauth_version = oauth_version; 
	}
	
	/**
	 * F. Service Provider Grants Access Token
	 *
	 * @param oauth_token
	 * @param oauth_token_secret
	 */
	public void setServiceProviderGrantsAccessToken(
			String oauth_token, 
			String oauth_token_secret) {
		this.oauth_token = oauth_token;
		this.oauth_token_secret = oauth_token_secret;
	}
	
	/**
	 * G. Consumer accesses protected resources  
	 * 
	 * @param oauth_consumer_key
	 * @param oauth_token
	 * @param oauth_signature_method
	 * @param oauth_signature
	 * @param oauth_timestamp
	 * @param oauth_nonce
	 * @param oauth_version
	 */
	public void setConsumerAccessesProtectedResources(
			String oauth_consumer_key, 
			String oauth_token,
			String oauth_signature_method,
			String oauth_signature,
			int oauth_timestamp,
			String oauth_nonce,
			String oauth_version) {
		this.oauth_consumer_key = oauth_consumer_key;
		this.oauth_token = oauth_token;
		this.oauth_signature_method = oauth_signature_method;
		this.oauth_signature = oauth_signature;
		this.oauth_timestamp = oauth_timestamp;
		this.oauth_nounce = oauth_nonce;
		this.oauth_version = oauth_version; 
	}
	
}
