package org.gonevertical.core.client.account;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author branflake2267
 *
 */
public class PermissionData implements IsSerializable {

  // can't see or write a thing
  public static final int PNO_ACCESS = 0;

  // read only
  public static final int PREADONLY = 1;

  // read / write
  public static final int PREADWRITE = 2;
  
  // write only
  public static final int PWRITEONLY = 3;
  
  // what type of permission - CONSTANT
  private int access = PNO_ACCESS;
  
  /**
   * constructor
   */
  public PermissionData() {
  }



  
}
