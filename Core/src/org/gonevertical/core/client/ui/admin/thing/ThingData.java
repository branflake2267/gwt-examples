package org.gonevertical.core.client.ui.admin.thing;

import java.util.Date;

import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingData implements IsSerializable {
  
	public static final int THING_APPLICATION = 1;
	public static final int THING_USER_ADMIN = 2;
	public static final int THING_USER_DEMO = 3;
	public static final int THING_PERMISSION_OPEN = 4;
	public static final int THING_PERMISSION_CLOSED = 5;
	public static final int THING_WIDGET_CORETHINGTYPES = 6;
	public static final int THING_WIDGET_CORETHINGSTUFFTYPES = 7;
	public static final int THING_WIDGET_CORETHINGS = 8;
	public static final int THING_WIDGET_COREEDITTHING = 9;
	public static final int THING_WIDGET_CORE_ACCOUNT_ABOUTME = 10;
	
	// type of thing, app, user, group...
  private long thingTypeId = 0;
  
  // identity 
  private long thingId = 0;
  
  // username
  private String key = null;

  // start of the thing
	private Date startOf;
	
	// end of the thing
	private Date endOf;
	
	private Double rank;
	
	private long createdBy;
	
	// when database created object
	private Date dateCreated;
	
	private long updatedBy;
	
	// when database updated object
	private Date dateUpdated;
  
	// multi dem of the thingstuff
	private ThingStuffsData thingStuffsData;
	
	private long[] ownerThingIds;
	
	/**
	 * constructor
	 */
  public ThingData() {
  }
  
  /**
   * constructor
   * 
   * @param thingTypeId
   * @param thingId
   */
  public ThingData(long thingTypeId, long thingId) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
  }
  
  public void setData(long thingTypeId, long thingId) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
  }
  
  public void setData(
  		long thingTypeId, 
  		long thingId, 
  		String key, 
  		Date startOf, 
  		Date endOf,
  		Double rank,
  		long createdBy,
  		Date dateCreated,
  		long updatedBy,
  		Date dateUpdated,
  		long[] ownerThingIds) {
  	
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
    this.key = key;
    this.startOf = startOf;
    this.endOf = endOf;
    this.rank = rank;
    this.createdBy = createdBy;
    this.dateCreated = dateCreated;
    this.updatedBy = updatedBy;
    this.dateUpdated = dateUpdated;
    this.ownerThingIds = ownerThingIds;
  }
  
  public long getThingTypeId() {
    return thingTypeId;
  }
  
  public long getThingId() {
    return thingId;
  }
  
  /**
   * get username
   * @return
   */
  public String getKey() {
    return key;
  }
  
  /**
   * set username
   * 
   * @param key
   */
  public void setKey(String key) {
  	this.key = key;
  }

  public void setStartOf(Date startOf) {
  	this.startOf = startOf;
  }
  
	public Date getStartOf() {
	  return startOf;
  }

	public void setEndOf(Date endOf) {
		this.endOf = endOf;
	}
	
	public Date getEndOf() {
	  return endOf;
  }
	
	public void setRank(Double rank) {
		this.rank = rank;
	}
	
	public Double getRank() {
		return rank;
	}
	
	public long getCreatedBy() {
		return createdBy;
	}
  
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public long getUpdatedBy() {
		return updatedBy;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}

	public ThingStuffsData getThingStuffsData() {
	  return thingStuffsData;
  }
	
	public void setThingStuffsData(ThingStuffsData thingStuffsData) {
		this.thingStuffsData = thingStuffsData;
	}

	public void setThingTypeId(long thingTypeId) {
	  this.thingTypeId = thingTypeId;
  }

	public void setOwners(long[] ownerThingIds) {
		this.ownerThingIds = ownerThingIds;
	}

	public long[] getOwners() {
		return ownerThingIds;
	}
	
}
