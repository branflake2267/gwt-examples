package com.gawkat.core.server.jdo.data;

import java.util.Date;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.server.jdo.PMF;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Thing_TypeJdo {

  // these are reserved
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_USER = 2;
  public static final int TYPE_GROUP = 3;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long thingTypeId;
  
  @Persistent
  private String name;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  public Long getId() {
    return this.thingTypeId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void insert(String name) {
    this.name = name;
    this.dateCreated = new Date();
    
    // if the name already exists
    Thing_TypeJdo[] tt = query(name);
    if (tt != null && tt.length > 0) {
      return;
    }
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(this);
    } finally {
      pm.close();
    }
    
    System.out.println("saved: thingTypeId:" + getId());
  }
  
  public static Thing_TypeJdo[] query(String name) {
    
    Thing_TypeJdo[] things = null;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {

      String filter = "name==\"" + name + "\"";
      Query query = pm.newQuery(ThingJdo.class, filter);

      try {
        List<Thing_TypeJdo> results = null;
        try {
          results = (List<Thing_TypeJdo>) query.execute();
        } catch (Exception e) {
        }
        if (results != null && results.iterator().hasNext()) {
          things = new Thing_TypeJdo[results.size()];
          int i=0;
          for (Thing_TypeJdo o : results) {
            things[i] = new Thing_TypeJdo();
            things[i] = o;
            i++;
          }
        } else {
          things = null;
        }
      } finally {
        query.closeAll();
      }

    } finally {
      pm.close();
    }

    return things;
  }
  

  public static ThingTypeData[] query(ThingTypeFilterData filter) {
    
    // TODO configure drill to setup filters
    
    ThingTypeData[] t = null;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {

      //String qfilter = "thingTypeId>0";
      Query query = pm.newQuery(ThingJdo.class);
      query.setRange(0,10);

      try {
        List<Thing_TypeJdo> results = (List<Thing_TypeJdo>) query.execute();
        if (results.iterator().hasNext()) {
          t = new ThingTypeData[results.size()];
          int i=0;
          for (Thing_TypeJdo o : results) {
            t[i] = new ThingTypeData();
            t[i].set(o.getId(), o.getName());
            i++;
          }
        } else {
          t = null;
        }
      } finally {
        query.closeAll();
      }

    } finally {
      pm.close();
    }

    // TODO  Testing another type
    query2();
    
    return t;
  }
  
  public static Thing_TypeJdo[] query2() {
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Extent<Thing_TypeJdo> extent = pm.getExtent(Thing_TypeJdo.class, false); // this needs comments in 
    
    System.out.println("pause");
    
    for (Thing_TypeJdo t : extent) {
      System.out.println("pause");
    }
    
    
    extent.closeAll();
    
    return null;
  }
  
  
}
