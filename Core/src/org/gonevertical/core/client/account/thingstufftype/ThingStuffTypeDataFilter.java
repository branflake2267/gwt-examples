package org.gonevertical.core.client.account.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeDataFilter implements IsSerializable {
  
	private long start;
  private long limit;
  
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
