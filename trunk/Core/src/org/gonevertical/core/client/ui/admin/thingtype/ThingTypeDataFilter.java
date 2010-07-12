package org.gonevertical.core.client.ui.admin.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeDataFilter implements IsSerializable {

  private long start;
  private long limit;
	
	private String[] name;
	
  /**
   * constructor
   */
  public ThingTypeDataFilter() {
  }

	public void setName(String name) {
		String[] s = new String[1];
		s[0] = name;
		this.name = s;
  }
  
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

	public String getFilter_Or() {
		if (name == null) {
			return null;
		}
		String s = "";
		
		if (name != null && name.length > 0) {
			for (int i=0; i < name.length; i++) {
				s += "name==\"" + name + "\"";
				if (i < name.length -1) {
					s += " || ";
				}
			}
		}
		
		return s;
	}
  
}
