package org.gonevertical.core.client.admin.thingtype;

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
  
  // when this object was created
  private Date dateCreated;
  
  // when dhtis object was updated
  private Date dateUpdated;
  
  /**
   * set data
   * 
   * @param id
   * @param name
   */
  public void setData(long id, String name, 
  		Date startOf, Date endOf, Date dateCreated, Date dateUpdated) {
    this.id = id;
    this.name = name;
    this.startOf = startOf;
    this.endOf = endOf;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
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
