package org.gonevertical.core.client.ui.admin.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeDataFilter implements IsSerializable {
  
	private long start;
  private long limit = 100;
  
  private long[] valueTypeId = null;
  
  public void setLimit(long start, long limit) {
  	this.start = start;
  	this.limit = limit;
  }
  
  public void setValueTypeId(long[] valueTypeIds) {
  	this.valueTypeId = valueTypeIds;
  }
  
  public long[] getValueTyepId() {
  	return valueTypeId;
  }
  
  public String getFilter_Or() {
  	if (valueTypeId == null || valueTypeId.length == 0) {
  		return null;
  	}
  	String s = "";
		if (valueTypeId != null && valueTypeId.length > 0) {
			for (int i=0; i < valueTypeId.length; i++) {
				s += "valueTypeId==" + valueTypeId[i] + "";
				if (i < valueTypeId.length -1) {
					s += " || ";
				}
			}
		}
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
