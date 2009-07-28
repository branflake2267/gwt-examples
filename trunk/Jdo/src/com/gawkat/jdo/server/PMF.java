package com.gawkat.jdo.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {

  //PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
  private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

  private PMF() {}
  
  public static PersistenceManagerFactory get() {
      return pmfInstance;
  }

}
