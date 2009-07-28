package com.gawkat.jdo.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

public class TestJdo {

  public TestJdo() {
  
  }
  
  public void test(String s) {

    //File file = new File("/home/branflake2267/workspace/TestJdo/datanucleus.properties");
  
    //PersistenceManagerFactory pmf = PMF.get();
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
  
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
  
      pm.makePersistent(new com.gawkat.jdo.server.data.Employee("test", s, 2589));
  
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }
  
}
