package com.gawkat.projectb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class WidgetB extends Composite {
	
  private final RpcServiceAsync rpcService = GWT.create(RpcService.class);
  
  private HorizontalPanel pWidget = new HorizontalPanel();
  
  public WidgetB() {
    
    pWidget.add(new HTML("ProjectB: WidgetB says from server:&nbsp;"));
    
    initWidget(pWidget);
    
    getRpc("WIDGET B SENT");
  }
  
  private void process(String s) {
    pWidget.add(new HTML(" BackFromServer: " + s) );
  }

  private void getRpc(String input) {
    
    rpcService.greetServer(input, new AsyncCallback<String>() {
      public void onSuccess(String s) {
        process(s); 
      }
      public void onFailure(Throwable caught) {
        
      }
    });
  }
  
}
