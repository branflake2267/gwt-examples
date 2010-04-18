package org.gonevertical.core.client.account.thing;

import java.util.Date;

import org.gonevertical.core.client.account.thingstuff.ThingStuffsData;

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
  
	// multi dem of the thingstuff
	private ThingStuffsData thingStuffsData;
	
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

	public ThingStuffsData getThingStuffsData() {
	  return thingStuffsData;
  }
	
	public void setThingStuffsData(ThingStuffsData thingStuffsData) {
		this.thingStuffsData = thingStuffsData;
	}

	public void setThingTypeId(long thingTypeId) {
	  this.thingTypeId = thingTypeId;
  }


}
