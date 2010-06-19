package org.gonevertical.core.client.admin.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeDataFilter implements IsSerializable {

  private long start;
  
  private long limit;

	public void setLimit(long start, long limit) {
	  this.start = start;
	  this.limit = limit;
  }
	
	public long getRangeStart() {
	  return start;
  }

	public long getRangeFinish() {
		long finish = start + limit;
	  return finish;
  }
  
}
