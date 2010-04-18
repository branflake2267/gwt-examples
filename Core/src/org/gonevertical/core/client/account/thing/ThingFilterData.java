package org.gonevertical.core.client.account.thing;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingFilterData implements IsSerializable {

  public long start = 0;
  
  public long limit = 0;
  
  /**
   * filter by type
   */
  public long thingTypeId = 0;

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
  
}
