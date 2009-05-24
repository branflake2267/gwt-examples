package com.gawkat.flashcard.server;

import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.client.login.LoginData;
import com.gawkat.flashcard.client.rpc.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  public MathData getMathData(MathData mathData) {
    MathDataServer process = new MathDataServer();
    return process.getMathData(mathData);
  }
  
  public LoginData getLoginData(String requestUri) {
    LoginServer login = new LoginServer();
    return login.getLoginData(requestUri);
  }
  
}
