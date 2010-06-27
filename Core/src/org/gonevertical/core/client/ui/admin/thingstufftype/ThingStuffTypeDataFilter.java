package org.gonevertical.core.client.ui.admin.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeDataFilter implements IsSerializable {
  
	private long start;
  private long limit = 100;
  
  public void setLimit(long start, long limit) {
  	this.start = start;
  	this.limit = limit;
  }
  
  public String getFilter() {
  	String s = null;
  	
  	return s;
  }
  
  public long getRangeStart() {
	  return start;
  }

	public long getRangeFinish() {
		long finish = start + limit;
	  return finish;
  }

}
