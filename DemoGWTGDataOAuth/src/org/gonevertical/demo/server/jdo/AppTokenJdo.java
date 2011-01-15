package org.gonevertical.demo.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class AppTokenJdo {

  /**
   * user id - unique/distinct user id
   */
  @PrimaryKey
  private String id;

  /**
   * oauth access token
   */
  @Persistent
  private String accessTokenKey;
  
  /**
   * oauth access token secret
   */
  @Persistent
  private String accessTokenSecret;

  /**
   * constructor - init access token
   * 
   * @param id - unique/distinct user id
   * @param accessTokenKey
   * @param accessTokenSecret
   */
  public AppTokenJdo(String id, String accessTokenKey, String accessTokenSecret) {
    this.id = id;
    this.accessTokenKey = accessTokenKey;
    this.accessTokenSecret = accessTokenSecret;
  }

  public String getAccessTokenKey() {
    return accessTokenKey;
  }
  
  public String getAccessTokenSecret() {
    return accessTokenSecret;
  }
}
