package org.gonevertical.core.client.ui.admin.thingstufftype;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeData implements IsSerializable {

	// NOTE: app engine storage types - str, int32, int64, double, bool, safest to store in text, so its exact when decimal
	
  public static final int VT_STRING = 1;
  public static final int VT_BOOLEAN = 2;
  public static final int VT_DOUBLE = 3;
  public static final int VT_INT = 4;
  
  public static final int VT_STRING_LARGE = 5; // text area
  public static final int VT_STRING_CASE = 6; // text box case sensitive
  public static final int VT_STRING_LARGE_CASE = 7; // text area case sensitive
  
  public static final int VT_HTML = 8;
  public static final int VT_URL = 9;
  public static final int VT_EMAIL = 10;
  public static final int VT_PHONE = 11;
  
  /**
   * Thing Link - link to another thing - thing relationships
   */
	public static final int VT_LINK = 12;
	
	/**
	 * file types
	 */
	public static final int VT_FILE = 13;
	
  // identity
  private long stuffTypeId;
  
  // type of value storage format
  private int valueTypeId = VT_STRING;
  
  // name of the stuff item
  private String name;
  
  // when did this start in time
  private Date startOf;
  
  // when this end in time
  private Date endOf;
  
  // when this object was created
  private Date dateCreated;
  
  // when this object was last updated
  private Date dateUpdated;
  
  /**
   * constructor
   */
  public ThingStuffTypeData() {
  }
  
  public void setData(long id, String name, int valueTypeId, 
  		Date startOf, Date endOf, Date dateCreated, Date dateUpdated) {
  	
    this.stuffTypeId = id;
    this.name = name;
    this.valueTypeId = valueTypeId;
    
    this.startOf = startOf;
    this.endOf = endOf;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
    
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
  
  public void setValueTypeId(int valueTypeId) {
    this.valueTypeId = valueTypeId;
  }
  
  public int getValueTypeId() {
    return valueTypeId;
  }

	public long getId() {
	  return stuffTypeId;
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
