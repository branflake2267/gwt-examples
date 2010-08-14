package org.gonevertical.core.client.ui.admin.thingstuff;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffDataFilter implements IsSerializable {
  
  private long start = 0;
  private long limit = 20;
  
  // primary key(id) (when saved)
  private long thingStuffId;
  
  // when dealing with about 
  private long thingStuffAboutId;
  
  // parent
  private long thingId;
    
  // get multiple thing stuff type ids 1,18,19,4
  private long[] thingStuffTypeId;
  
  private String[] value;
  private Boolean[] valueBol;
  private Double[] valueDouble;
  private Long[] valueLong;
  private Date[] valueDate;
  
  /**
   * where expression using and &&
   */
  private ArrayList<String> whereAnd;
  
  /**
   * where expression using or ||
   */
  private ArrayList<String> whereOr;
  
  public ThingStuffDataFilter() {
  }
  
  public void setLimit(long start, long limit) {
  	this.start = start;
  	this.limit = limit;
  }
  
  public long getStart() {
  	return start;
  }
  
  public long getLimit() {
  	return limit;
  }
  
  public void setThingStuffId(long thingStuffId) {
  	this.thingStuffId = thingStuffId;
  }
  
  public long getThingStuffId() {
  	return thingStuffId;
  }
  
  public void setThingStuffAboutId(long thingStuffAboutId) {
  	this.thingStuffAboutId = thingStuffAboutId;
  }
  
  public void setThingId(long thingId) {
  	this.thingId = thingId;
  }
  
  public long getThingId() {
  	return thingId;
  }
 
  public void setThingStuffTypeId(long thingStuffTypeId) {
  	this.thingStuffTypeId = new long[1];
  	this.thingStuffTypeId[0] = thingStuffTypeId;
  }
  
  public void setThingStuffTypeId(long[] thingStuffTypeId) {
  	this.thingStuffTypeId = thingStuffTypeId;
  }
  
  public long[] getThingStuffTypeId() {
  	return thingStuffTypeId;
  }
  
  public void setValueString(String[] values) {
  	this.value = values;
  }
  
  public void setValueString(String value) {
  	this.value = new String[1];
  	this.value[0] = value;
  }
  
  public void setValueBoolean(Boolean[] values) {
  	this.valueBol = values;
  }
  
  public void setValueBoolean(Boolean value) {
  	this.valueBol = new Boolean[1];
  	this.valueBol[0] = value;
  }
  
  public void setValueDouble(Double[] values) {
  	this.valueDouble = values;
  }
  
  public void setValueDouble(Double value) {
  	this.valueDouble = new Double[1];
  	this.valueDouble[0] = value;
  }
  
  public void setValueLong(Long[] values) {
  	this.valueLong = values;
  }
  
  public void setValueLong(Long value) {
  	this.valueLong = new Long[1];
  	this.valueLong[0] = value;
  }
  
  public void setValueDate(Date[] values) {
  	this.valueDate = values;
  }
  
  public void setValueDate(Date value) {
  	this.valueDate = new Date[1];
  	this.valueDate[0] = value;
  }
  
  public String getFilter_And() {
  	
  	if (whereAnd == null) {
  		whereAnd = new ArrayList<String>();
  	}
  	
  	if (thingId > 0) {
  		String s1 = "thingId==" + thingId;
  		whereAnd.add(s1);
  	}
  	
  	if (thingStuffId > 0) { // parent owner
  		String s2 = "thingStuffId==" + thingStuffId;
  		whereAnd.add(s2);
  	}
  	
  	// I don't want to do this here, I can get it by object id
  	//if (thingStuffAboutId > 0) { // this is for the about thing stuff data
  		//s += " && thingStuffAboutId==" + thingStuffAboutId + " ";
  	//}
		
  	// filter by multiple stuffTypeIds
		if (thingStuffTypeId != null && thingStuffTypeId.length > 0) {
			for (int i=0; i < thingStuffTypeId.length; i++) {
				String s3= " thingStuffTypeId==" + thingStuffTypeId[i];
				whereAnd.add(s3);
			}
		}
		
		// value string
		if (value != null && value.length > 0) {
			for (int i=0; i < value.length; i++) {
				String s4= "value==\"" + value[i] + "\"";
				whereAnd.add(s4);
			}
		}
		
		// value boolean
		if (valueBol != null && valueBol.length > 0) {
			for (int i=0; i < valueBol.length; i++) {
				String s5 = " valueBol==" + valueBol[i];
				whereAnd.add(s5);
			}
		}
		
		// value double
		if (valueDouble != null && valueDouble.length > 0) {
			for (int i=0; i < valueDouble.length; i++) {
				String s6 = " valueDouble==" + valueDouble[i];
				whereAnd.add(s6);
			}
		}
		
		// value long
		if (valueLong != null && valueLong.length > 0) {
			for (int i=0; i < valueLong.length; i++) {
				String s7 = " valueLong==" + valueLong[i];
				whereAnd.add(s7);
			}
		}
		
		if (valueDate != null && valueDate.length > 0) {
			for (int i=0; i < valueDate.length; i++) {
				String s8 = " valueDate==" + valueDate[i];
				whereAnd.add(s8);
			}
		}
		
		String s = null;
    for(int i=0; i < whereAnd.size(); i++) {
    	if (s == null) {
    		s = "";
    	}
    	s += whereAnd.get(i);
    	if (i < whereAnd.size() - 1) {
    		s += " && ";
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
