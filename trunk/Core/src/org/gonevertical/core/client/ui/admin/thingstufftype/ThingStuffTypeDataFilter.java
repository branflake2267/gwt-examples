package org.gonevertical.core.client.ui.admin.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeDataFilter implements IsSerializable {
  
	private long start;
  private long limit = 100;
  
  private long[] valueTypeId = null;
  
  private String[] name = null;
  
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
  
	public void setName(String name) {
		String[] s = new String[1];
		s[0] = name;
		this.name = s;
  }
  
  public String getFilter_Or() {
  	String s = "";
		
  	if (valueTypeId != null && valueTypeId.length > 0) {
			for (int i=0; i < valueTypeId.length; i++) {
				s += "valueTypeId==" + valueTypeId[i] + "";
				if (i < valueTypeId.length -1) {
					s += " || ";
				}
			}
		}
		
		if (name != null && name.length > 0) {
			for (int i=0; i < name.length; i++) {
				s += "name==\"" + name[i] + "\"";
				if (i < name.length - 1) {
					s += " || ";
				}
			}
		}
		
		if (s != null && s.trim().length() == 0) {
			s = null;
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
