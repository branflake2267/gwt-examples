package org.gonevertical.core.client.ui.admin.thingstuff;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffDataFilter implements IsSerializable {
  
  private long start = 0;
  private long limit = 100;
  
  // primary key(id) (when saved)
  private long thingStuffId = 0;
  
  // when dealing with about 
  private long thingStuffAboutId = 0;
  
  // parent
  private long thingId = 0;
    
  // get multiple thing stuff type ids 1,18,19,4
  private long[] thingStuffTypeId = null;
  
  private String[] value = null;
  private Boolean[] valueBol = null;
  private Double[] valueDouble = null;
  private Long[] valueLong = null;
  private Date[] valueDate = null;
  
  
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
  	String s = null;
  	
  	s = "thingId==" + thingId + " ";
  	
  	if (thingStuffId > 0) { // parent owner
  		s += " && thingStuffId==" + thingStuffId + " ";
  	}
  	
  	// I don't want to do this here, I can get it by object id
  	//if (thingStuffAboutId > 0) { // this is for the about thing stuff data
  		//s += " && thingStuffAboutId==" + thingStuffAboutId + " ";
  	//}
		
  	// filter by multiple stuffTypeIds
		if (thingStuffTypeId != null && thingStuffTypeId.length > 0) {
			s += "&&";
			for (int i=0; i < thingStuffTypeId.length; i++) {
				s += " thingStuffTypeId==" + thingStuffTypeId[i] + " ";
				if (i < thingStuffTypeId.length - 1) {
					s += "&&";
				}
			}
		}
		
		// value string
		if (value != null && value.length > 0) {
			s += "&&";
			for (int i=0; i < value.length; i++) {
				s += " value==\"" + value[i] + "\" ";
				if (i < value.length - 1) {
					s += "&&";
				}
			}
		}
		
		// value boolean
		if (valueBol != null && valueBol.length > 0) {
			s += "&&";
			for (int i=0; i < valueBol.length; i++) {
				s += " valueBol==" + valueBol[i] + " ";
				if (i < valueBol.length - 1) {
					s += "&&";
				}
			}
		}
		
		// value double
		if (valueDouble != null && valueDouble.length > 0) {
			s += "&&";
			for (int i=0; i < valueDouble.length; i++) {
				s += " valueDouble==" + valueDouble[i] + " ";
				if (i < valueDouble.length - 1) {
					s += "&&";
				}
			}
		}
		
		// value long
		if (valueLong != null && valueLong.length > 0) {
			s += "&&";
			for (int i=0; i < valueLong.length; i++) {
				s += " valueLong==" + valueLong[i] + " ";
				if (i < valueLong.length - 1) {
					s += "&&";
				}
			}
		}
		
		if (valueDate != null && valueDate.length > 0) {
			s += "&&";
			for (int i=0; i < valueDate.length; i++) {
				s += " valueDate==" + valueDate[i] + " ";
				if (i < valueDate.length - 1) {
					s += "&&";
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
