package org.gonevertical.core.client.ui.admin.thing;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingDataFilter implements IsSerializable {

  private long start = 0;
  private long limit = 0;
  
  /**
   * filter by type
   */
  private long thingTypeId = 0;

  public void setThingTypeId(long thingTypeId) {
  	this.thingTypeId = thingTypeId;
  }
  
  public long getThingTypeId() {
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

	public long getRangeFinish() {
		long finish = start + limit;
	  return finish;
  }

	public String getFilter() {
		
		String s = null;
		if (thingTypeId > 0) {
			s = "thingTypeId==" + thingTypeId + "";
		}

	  return s;
  }
  
}
