package org.gonevertical.demo.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class AppTokenJdo {

  /**
   * user id - userId + scope
   */
  @PrimaryKey
  private String id;
  
  /**
   * Google UserId
   */
  @Persistent
  private String userId;
  
  /**
   * scope's Token
   */
  @Persistent
  private String scope;

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
   * @param userId - unique/distinct user id
   * @param accessTokenKey
   * @param accessTokenSecret
   * @param scope 
   */
  public AppTokenJdo(String userId, String accessTokenKey, String accessTokenSecret, String scope) {
    this.id = userId + "_" + scope;
    this.userId = userId;
    this.scope = scope;
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
