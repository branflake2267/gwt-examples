package com.gawkat.flashcard.client.rpc;

import com.gawkat.flashcard.client.card.MathData;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {
  
  public void getMathData(MathData mathData, AsyncCallback<MathData> callback);
  
}
