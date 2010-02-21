package com.gawkat.core.client.account.thingstuff;

import java.util.Date;

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffData implements IsSerializable {
  
	// identity
  private long thingStuffId;

  // what type of data is it?
  private long thingStuffTypeId;

  // who is the owner, what thing
  private long thingId;
  
  // store in string format
  private String value;
  
  // store in boolean format
  private boolean valueBol;
  
  // store in double format - remember this is unsigned
  // recommendation to store decimal values into string, and use BigDecimal on the server side to process them
  public double valueDouble = 0.0;
  
  // store in integer format (long), Im calling everything int for the sake of easy recongition
  public long valueInt = 0;
  
  // define the link type - adjetives of the relationship
  private ThingStuffsData thingStuffIdsAbout;
  
  // when did this start in time
  private Date startOf;
  
  // when did this end in time
  private Date endOf;
  
  // when this object was created
  private Date dateCreated;
  
  // when this object was updated
  private Date dateUpdated;
   
  /**
   * constructor
   */
  public ThingStuffData() {
  }
  
  public void setData(
  		long thingId, 
  		long thingStuffId, 
      long thingStuffTypeId, 
      String value, 
      boolean valueBol, 
      double valueDouble, 
      long valueInt,
      ThingStuffsData thingStuffTypeIdsAbout,
      Date startOf,
      Date endOf,
      Date dateCreated,
      Date dateUpdated) {
  	
    this.thingId = thingId;
    this.thingStuffId = thingStuffId;
    this.thingStuffTypeId = thingStuffTypeId;
    this.value = value;
    this.valueBol = valueBol;
    this.valueDouble = valueDouble;
    this.valueInt = valueInt;
    this.thingStuffIdsAbout = thingStuffTypeIdsAbout;
    
    this.startOf = startOf;
    this.endOf = endOf;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
  }
  
  public long getId() {
    return thingStuffId;
  }
  
  public void setId(long thingStuffId) {
    this.thingStuffId = thingStuffId;
  }
  
  public long getThingId() {
    return thingId;
  }
  
  public void setThingId(long thingId) {
    this.thingId = thingId;
  }
  
  public long getThingStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public void setThingStuffTypeId(long thingStuffTypeId) {
    this.thingStuffTypeId = thingStuffTypeId;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void setValue(boolean value) {
    this.valueBol = value;
  }
  
  public void setValue(double value) {
    this.valueDouble = value;
  }
  
  public String getValue() {
    return value;
  }
  
  public boolean getValueBol() {
    return valueBol;
  }
  
  public double getValueDouble() {
    return valueDouble;
  }
  
  public void setValueInt(long valueInt) {
    this.valueInt = valueInt;
  }
  
  public long getValueInt() {
    return valueInt;
  }

	public ThingStuffsData getThingStuffIdsAbout() {
	  return thingStuffIdsAbout;
  }
  
	public void setThingStuffIdsAbout(ThingStuffsData thingStuffIdsAbout) {
		this.thingStuffIdsAbout = thingStuffIdsAbout;
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
