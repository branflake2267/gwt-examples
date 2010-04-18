package org.gonevertical.core.server.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
  	
  // GAE xml
  private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
  
  // tomcat6 txt file settings
  //private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");


  private PMF() {
  }

  public static PersistenceManagerFactory get() {
      return pmfInstance;
  }
  
}
