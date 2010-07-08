package org.gonevertical.core.client.ui.admin.thing;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingDataFilter implements IsSerializable {

  private long start = 0;
  private long limit = 0;

  /**
   * filter by one thing
   */
	private long thingId;

  /**
   * filter by type
   */
  private long thingTypeId[] = null;

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
		if (thingTypeId == null || thingTypeId.length == 0) {
			return null;
		}
		String s = "";
		if (thingTypeId != null && thingTypeId.length > 0) {
			for (int i=0; i < thingTypeId.length; i++) {
				s += "thingTypeId==" + thingTypeId[i] + "";
				if (i < thingTypeId.length -1) {
					s += " || ";
				}
			}
		}
	  return s;
  }

	public long getThingId() {
	  return thingId;
  }

	public void setThingId(long thingId) {
	  this.thingId = thingId;
  }
  
}
