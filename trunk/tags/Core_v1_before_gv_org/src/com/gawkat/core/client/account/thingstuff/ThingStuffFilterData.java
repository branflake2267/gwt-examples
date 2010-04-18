package com.gawkat.core.client.account.thingstuff;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffFilterData implements IsSerializable {
  
  public long start = 0;
  public long limit = 0;
  
  // parent
  public long thingId = 0;
  
  // primary key(id) (when saved)
  public long thingStuffId = 0;
  
  

}
