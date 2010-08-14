package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingDataFilter implements IsSerializable {

  private long start = 0;
  private long limit = 0;

  /**
   * filter by one thingIds in the stuff under links
   */
	private long thingId;

  /**
   * filter by type
   */
  private long thingTypeId[] = null;

  
  private long thingIdLink;

  /**
   * constructor - nothing to do
   */
  public ThingDataFilter() {
  }
  
  public void setThingTypeId(long[] thingTypeId) {
  	this.thingTypeId = thingTypeId;
  }
  
  public long[] getThingTypeId() {
  	return thingTypeId;
  }
  
  public void setLimit(long start, long limit) {
  	this.start = start;
  	this.limit = limit;
  }
  
  /**
   * get start of range
   * @return
   */
	public long getRangeStart() {
	  return start;
  }

	/**
	 * get end of range finish - this is a work around for the offset
	 * @return
	 */
	public long getRangeFinish() {
		long finish = start + limit;
	  return finish;
  }

	public String getFilter_Or() {

		String s = "";
		if (thingTypeId != null && thingTypeId.length > 0) {
			for (int i=0; i < thingTypeId.length; i++) {
				s += "thingTypeId==" + thingTypeId[i] + "";
				if (i < thingTypeId.length -1) {
					s += " || ";
				}
			}
		}
		
		if (s != null && s.length() > 0) {
			s += " || ";
		}
		
		// TODO - not sure how to do a one to many unowned relationship, it doesn't look easy at moment, dang
		if (s != null && thingIdLink > 0) {
			//s += " "; 
		}
		
		if (s != null && s.trim().length() == 0) {
			s = null;
		}
			
	  return s;
  }

	public void setThingId(long thingId) {
	  this.thingId = thingId;
  }
	
	public long getThingId() {
	  return thingId;
  }

	public void setThingIdLink(long thingIdLink) {
	  this.thingIdLink = thingIdLink;
  }

	public long getThingIdLink() {
		return thingIdLink;
	}
  
}
