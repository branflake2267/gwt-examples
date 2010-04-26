package com.gawkat.flashcard.client.rpc;

import com.google.gwt.core.client.GWT;

public class Rpc {

  public static RpcServiceAsync init() {
    return GWT.create(RpcService.class);
  }
  
}
