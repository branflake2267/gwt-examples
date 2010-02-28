package com.gawkat.core.client.account.thing;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingData implements IsSerializable {
  
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
	
	// when database created object
	private Date dateCreated;
	
	// when database updated object
	private Date dateUpdated;
  
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
  
  public void setData(long thingTypeId, long thingId, String key, Date startOf, Date endOf, Date dateCreated, Date dateUpdated) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
    this.key = key;
    this.startOf = startOf;
    this.endOf = endOf;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
  }
  
  public long getThingTypeId() {
    return thingTypeId;
  }
  
  public long getThingId() {
    return thingId;
  }
  
  public String getKey() {
    return key;
  }

	public Date getStartOf() {
	  return startOf;
  }

	public Date getEndOf() {
	  return endOf;
  }
  
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}


}
