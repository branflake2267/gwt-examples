package com.gawkat.flashcard.server;

import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.client.rpc.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  public MathData getMathData(MathData mathData) {
    DB_MathData process = new DB_MathData();
    return process.getMathData(mathData);
  }
  
}
