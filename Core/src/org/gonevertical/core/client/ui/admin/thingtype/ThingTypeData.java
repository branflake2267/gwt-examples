package org.gonevertical.core.client.ui.admin.thingtype;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeData implements IsSerializable {

  // required thing types
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_USER = 2;
  public static final int TYPE_GROUP = 3;
  public static final int TYPE_WIDGET = 4;
  public static final int TYPE_PERMISSION = 5;
  public static final int TYPE_STUFFTYPETEMPLATE = 6;
  public static final int TYPE_TASK = 7;
  public static final int TYPE_REMINDER = 8;
  public static final int TYPE_LOCATION = 9;
  public static final int TYPE_DEVICE = 10;
  public static final int TYPE_DATA = 11;
  
  // to set the defaults use this int
  public static final int DEFAULT_TYPE = 1;
  
  // identity
  private long id;
  
  // type's name
  private String name;
  
  // when this started in time
  private Date startOf;
  
  // when this ended in time
  private Date endOf;
  
  private Double rank;
  
  // who created this object
	private long createdByThingId;

	// who updated this object
	private long updatedByThingId;
  
  // when this object was created
  private Date dateCreated;
  
  // when dhtis object was updated
  private Date dateUpdated;
  
  private long[] ownerThingIds;
  
  /**
   * constructor
   */
  public ThingTypeData() {
  }
  
  /**
   * set data
   * 
   * @param id
   * @param name
   */
  public void setData(
  		long id, 
  		String name, 
  		Date startOf, 
  		Date endOf,
  		Double rank,
  		long createdBy,
  		Date dateCreated,
  		long updatedBy,
  		Date dateUpdated,
  		long[] ownerThignIds) {
  	
    this.id = id;
    this.name = name;
    this.startOf = startOf;
    this.endOf = endOf;
    this.rank = rank;
    this.createdByThingId = createdBy;
    this.dateCreated = dateCreated;
    this.updatedByThingId = updatedBy;
    this.dateUpdated = dateUpdated;
    this.ownerThingIds = ownerThignIds;
  }
  
  public long getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

	public void setKey(int id) {
	  this.id = id;
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
	
	public void setOwners(long[] ownerThingIds) {
		this.ownerThingIds = ownerThingIds;
	}
	
	public long[] getOwners() {
		return ownerThingIds;
	}
	
}
