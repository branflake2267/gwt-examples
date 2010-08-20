package org.gonevertical.core.client.ui.admin.thing;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingDataFilter implements IsSerializable {

  private long start = 0;
  private long limit = 0;

  /**
   * filter by one thingIds in the stuff under links
   */
	private long thingId;
	private long[] thingIds;

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

	/**
	 * I need to add a dimension to this so I can add the expression dynamcially
	 * @return
	 */
	public String getFilter_Or() {

		ArrayList<String> w = new ArrayList<String>();
		
		if (thingTypeId != null && thingTypeId.length > 0) {
			for (int i=0; i < thingTypeId.length; i++) {
				String s = "thingTypeId==" + thingTypeId[i];
				w.add(s);
			}
		}
		
		if (thingIds != null && thingIds.length > 0) {
			for (int i=0; i < thingIds.length; i++) {
				String s2 = "thingIdKey==" + thingIds[i];
				w.add(s2);
			}
		}
		
		
		if (w == null || w.size() == 0) {
			return null;
		}
		
		String s = "";
		for (int i=0; i < w.size(); i++) {
			s += w.get(i);
			if (i < w.size() -1) {
				s += " || ";
			}
		}
		
	  return s;
  }

	public void setThingId(long thingId) {
	  this.thingId = thingId;
  }
	
	public long getThingId() {
	  return thingId;
  }
	
	public void setThingIds(long[] thingIds) {
		this.thingIds = thingIds;
  }

	public void setThingIdLink(long thingIdLink) {
	  this.thingIdLink = thingIdLink;
  }

	public long getThingIdLink() {
		return thingIdLink;
	}


  
}
