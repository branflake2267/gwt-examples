package org.gonevertical.loc.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TrackData implements IsSerializable {

  public static final int TYPE_START = 1;
  public static final int TYPE_STOP = 2;
  public static final int TYPE_WIN_TIE = 3;
  public static final int TYPE_WIN_P1 = 4;
  public static final int TYPE_WIN_P2 = 5;
  
  
  public int type = 0;

}
