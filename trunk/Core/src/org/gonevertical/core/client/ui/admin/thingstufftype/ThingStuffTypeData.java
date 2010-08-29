package org.gonevertical.core.client.ui.admin.thingstufftype;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeData implements IsSerializable {
	
	public static final int THINGSTUFFTYPE_NAME = 1;
	public static final int THINGSTUFFTYPE_LINK = 2;
	public static final int THINGSTUFFTYPE_ADMIN = 3;
	public static final int THINGSTUFFTYPE_CANVIEW = 4;
	public static final int THINGSTUFFTYPE_CANEDIT = 5;
	public static final int THINGSTUFFTYPE_CANADD = 6;
	public static final int THINGSTUFFTYPE_CANTVIEW = 7;
	public static final int THINGSTUFFTYPE_CANTEDIT = 8;
	public static final int THINGSTUFFTYPE_CANTADD = 9;
	public static final int THINGSTUFFTYPE_ALIAS = 10;
	public static final int THINGSTUFFTYPE_NICKNAME = 11;
	public static final int THINGSTUFFTYPE_FIRSTNAME = 12;
	public static final int THINGSTUFFTYPE_MIDDLENAME = 13;
	public static final int THINGSTUFFTYPE_LASTNAME = 14;
	public static final int THINGSTUFFTYPE_EMAIL = 15;
	public static final int THINGSTUFFTYPE_MOBILE = 16;
	public static final int THINGSTUFFTYPE_PHONE = 17;
	public static final int THINGSTUFFTYPE_DESC = 18;
	public static final int THINGSTUFFTYPE_ACCEPTTERMS = 19;
	
	public static final int THINGSTUFFTYPE_BIBLEBOOKNAME = 20;
	public static final int THINGSTUFFTYPE_BIBLEBOOKNUM = 21;
	public static final int THINGSTUFFTYPE_BIBLEBOOKLINK = 22;
	public static final int THINGSTUFFTYPE_BIBLEBOOKCHAPTERNUM = 23;
	public static final int THINGSTUFFTYPE_BIBLEBOOKVERSENUM = 24;
	public static final int THIGNSTUFFTYPE_BIBLEBOOKVERSECONTENT = 25;
	
	
	// NOTE: app engine storage types - str, int32, int64, double, bool, safest to store in text, so its exact when decimal
  public static final int VALUETYPE_STRING = 1;
  public static final int VALUETYPE_BOOLEAN = 2;
  public static final int VALUETYPE_DOUBLE = 3;
  public static final int VALUETYPE_INT = 4;
  
  public static final int VALUETYPE_STRING_LARGE = 5; // text area
  public static final int VALUETYPE_STRING_CASE = 6; // text box case sensitive
  public static final int VALUETYPE_STRING_LARGE_CASE = 7; // text area case sensitive
  
  public static final int VALUETYPE_HTML = 8;
  public static final int VALUETYPE_URL = 9;
  public static final int VALUETYPE_EMAIL = 10;
  public static final int VALUETYPE_PHONE = 11;
	public static final int VALUETYPE_LINK = 12;
	public static final int VALUETYPE_FILE = 13;
	
	// added - question is should I keep it basic here, and more defined in custome app builder logic
	public static final int VALUETYPE_DECIMAL = 14; // decimal
	public static final int VALUETYPE_NUMBER = 15; // number
	public static final int VALUETYPE_MONEY = 16; // money
	
	
	
  // identity
  private long stuffTypeId;
  
  // type of value storage format
  private long valueTypeId = VALUETYPE_STRING;
  
  // name of the stuff item
  private String name;
  
  // when did this start in time
  private Date startOf;
  
  // when this end in time
  private Date endOf;
  
  // order the list by this
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
  public ThingStuffTypeData() {
  }
  
  public void setData(
  		long id, 
  		String name, 
  		long valueTypeId, 
  		Date startOf, 
  		Date endOf,
  		Double rank,
  		long createdBy,
  		Date dateCreated,
  		long updatedBy,
  		Date dateUpdated,
  		long[] ownerThingIds) {
  	
    this.stuffTypeId = id;
    this.name = name;
    this.valueTypeId = valueTypeId;
    
    this.startOf = startOf;
    this.endOf = endOf;
    
    this.rank = rank;
    this.ownerThingIds = ownerThingIds;
    
    this.createdByThingId = createdBy;
    this.dateCreated = dateCreated;
    this.updatedByThingId = updatedBy;
    this.dateUpdated = dateUpdated;
    this.ownerThingIds = ownerThingIds;
  }
  
  public long getStuffTypeId() {
    return stuffTypeId;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setValueTypeId(long valueTypeId) {
    this.valueTypeId = valueTypeId;
  }
  
  public long getValueTypeId() {
    return valueTypeId;
  }

	public long getId() {
	  return stuffTypeId;
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
