package org.gonevertical.core.client.ui.admin.thingstuff;

import java.util.Date;

import javax.jdo.annotations.Persistent;

import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffData implements IsSerializable {
	  
	// identity for just stuff
  private long thingStuffId;
  
  // identity for about stuff
  private long thingStuffAboutId;

  // what type of data is it?
  private long stuffTypeId;

  // who is the parent, what thing
  private long parentThingId;
  
  // store in string format
  private String value;
  
  // store in boolean format
  private Boolean valueBol;
  
  // store in double format - remember this is unsigned
  // recommendation to store decimal values into string, and use BigDecimal on the server side to process them
  private Double valueDouble;
  
  // store in integer format (long), can represent null for nothing
  private Long valueLong;
  
  //TODO - could stick this in valueLong, but wondering how to deal with timezone
  // store in date formate (milliseconds)
  private Date valueDate;
  
  // define the link type - adjetives of the relationship
  private ThingStuffsData thingStuffsAbout;
  
  // when did this start in time
  private Date startOf;
  
  // when did this end in time
  private Date endOf;
  
  // order the list by this
  private Double rank;
  
  // when this object was created
  private Date dateCreated;
  
  // when this object was updated
  private Date dateUpdated;
  
  // who created this object
	private long createdByThingId;

	// who updated this object
	private long updatedByThingId;
	
	// assign ownership of this thing to this thing
	private long[] ownerThingIds;
   
  /**
   * constructor
   */
  public ThingStuffData() {
  }
  
  /**
   * set stuff
   * 
   * @param thingId
   * @param thingStuffId
   * @param thingStuffTypeId
   * @param value
   * @param valueBol
   * @param valueDouble
   * @param valueLong
   * @param startOf
   * @param endOf
   * @param dateCreated
   * @param dateUpdated
   */
  public void setData(
  		long thingId,
  		long thingStuffId, 
      long thingStuffTypeId, 
      String value, 
      Boolean valueBol, 
      Double valueDouble, 
      Long valueLong,
      Date valueDate,
      Date startOf,
      Date endOf,
      Double rank,
      long createdBy,
      Date dateCreated,
      long updatedBy,
      Date dateUpdated,
      long[] ownerThingIds) {
  	
    this.parentThingId = thingId;
    this.thingStuffId = thingStuffId;
    this.stuffTypeId = thingStuffTypeId;
    
    this.value = value;
    this.valueBol = valueBol;
    this.valueDouble = valueDouble;
    this.valueLong = valueLong;
    this.valueDate = valueDate;
   
    this.startOf = startOf;
    this.endOf = endOf;
    
    this.rank = rank;
    this.ownerThingIds = ownerThingIds;
    
    this.createdByThingId = createdBy;
    this.dateCreated = dateCreated;
    this.updatedByThingId = updatedBy;
    this.dateUpdated = dateUpdated;
  }
  
  /**
   * set stuff "about"
   *   Note: this one has ThingStuffAboutId
   * 
   * @param thingId
   * @param thingStuffId
   * @param thingStuffAboutId
   * @param thingStuffTypeId
   * @param value
   * @param valueBol
   * @param valueDouble
   * @param valueLong
   * @param startOf
   * @param endOf
   * @param dateCreated
   * @param dateUpdated
   */
  public void setData(
  		long thingId, 
  		long thingStuffId, 
  		long thingStuffAboutId,
      long thingStuffTypeId, 
      String value, 
      Boolean valueBol, 
      Double valueDouble, 
      Long valueLong,
      Date valueDate,
      Date startOf,
      Date endOf,
      Double rank,
      long createdBy,
      Date dateCreated,
      long updatedBy,
      Date dateUpdated,
      long[] ownerThingIds) {
  	
    this.parentThingId = thingId;
    this.thingStuffId = thingStuffId;
    this.thingStuffAboutId = thingStuffAboutId;
    this.stuffTypeId = thingStuffTypeId;
    
    this.value = value;
    this.valueBol = valueBol;
    this.valueDouble = valueDouble;
    this.valueLong = valueLong;
    this.valueDate = valueDate;
   
    this.startOf = startOf;
    this.endOf = endOf;
    
    this.rank = rank;
    this.ownerThingIds = ownerThingIds;
    
    this.createdByThingId = createdBy;
    this.dateCreated = dateCreated;
    this.updatedByThingId = updatedBy;
    this.dateUpdated = dateUpdated;
  }
  
  public long getStuffId() {
    return thingStuffId;
  }
  
  public void setStuffId(long thingStuffId) {
    this.thingStuffId = thingStuffId;
  }
  
	public void setStuffAboutId(long thingStuffAboutId) {
		this.thingStuffAboutId = thingStuffAboutId;
  }
	
	public long getStuffAboutId() {
		return thingStuffAboutId;
	}
  
  public long getThingId() {
    return parentThingId;
  }

  public void setThingId(long thingId) {
    this.parentThingId = thingId;
  }
	
  public long getStuffTypeId() {
    return stuffTypeId;
  }
  
  public void setStuffTypeId(long thingStuffTypeId) {
    this.stuffTypeId = thingStuffTypeId;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void setValue(Boolean value) {
    this.valueBol = value;
  }
  
  public void setValue(Double value) {
    this.valueDouble = value;
  }
  
  public void setValue(Long value) {
  	this.valueLong = value;
  }
  
  public void setValue(Date value) {
  	this.valueDate = value;
  }
  
  public String getValue() {
    return value;
  }
  
	public String getValue_ForTextBox() {
		String s = "";
		if (value != null) {
			s = value;
		}
		return s;
	}
  
  public Boolean getValueBol() {
    return valueBol;
  }
  
  public Double getValueDouble() {
    return valueDouble;
  }
  
  public Long getValueLong() {
    return valueLong;
  }
  
  public Date getValueDate() {
  	return valueDate;
  }

	public ThingStuffsData getThingStuffsAbout() {
	  return thingStuffsAbout;
  }
	
	public void setThingStuffsAbout(ThingStuffsData thingStuffAbout) {
		this.thingStuffsAbout = thingStuffAbout;
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
	
	public long getCreatedBy() {
	  return createdByThingId;
  }
	
	public Date getDateCreated() {
		return dateCreated;
	}
  
	public long getUpdatedBy() {
		return updatedByThingId;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public void setRank(Double rank) {
		this.rank = rank;
	}
	
	public Double getRank() {
		return rank;
	}
	
	public void setOwners(long[] ownerThingIds) {
		this.ownerThingIds = ownerThingIds;
	}
	
	public long[] getOwners() {
	  return ownerThingIds;
  }
	
	public String toString() {
		String s = "";
		s += "thingId=" + parentThingId + " ";
		s += "thingStuffId=" + thingStuffId + " ";
		s += "thingStuffAboutId=" + thingStuffAboutId + " ";
		s += "thingStuffTypeId=" + stuffTypeId + " ";
		s += "value=" + value + " ";
		s += "valueBol=" + valueBol + " ";
		s += "valueDouble=" + valueDouble + " ";
		s += "valueLong=" + valueLong + " ";
		s += "valueDate=" + valueDate + " ";
		s += "startOf=" + startOf + " "; 
		s += "endOf=" + endOf + " ";
		s += "rank=" + rank + " ";
		
		return s;
	}







}
