package org.gonevertical.demo.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoggingWidget extends Composite implements ClickHandler {
	
	private RpcServiceAsync rpc;

	private VerticalPanel pWidget = new VerticalPanel();
	
	private PushButton bSendCall = new PushButton("Call Server");
	
	private PushButton bSendCallFail = new PushButton("Send Forced Failure");
	
	/**
	 * init widget
	 */
	public LoggingWidget() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bSendCall);
		hp.add(new HTML("&nbsp;"));
		hp.add(bSendCallFail);
		
		pWidget.add(hp);
		
		initWidget(pWidget);
		
		rpc = RpcInit.initRpc();
		
		bSendCall.addClickHandler(this);
		bSendCallFail.addClickHandler(this);
	}
	
  public void onClick(ClickEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
	  if (sender == bSendCall) {
	  	callServer();
	  
	  } else if (sender == bSendCallFail) {
	  	callServer_with_Error();
	  }
	  
  }
	
	private void callServer() {
	
		CallData callData = new CallData();
		callData.type = 1;
	  
		callServer(callData);
  }

	private void callServer_with_Error() {
		
		CallData callData = new CallData();
		callData.type = 2;
	  
		callServer(callData);
  }
	
	public void callServer(CallData callData) {
		
		rpc.callServer(callData, new AsyncCallback<CallData>() {
			
			public void onSuccess(CallData callData) {
				
				Window.alert("worked " + callData.note);
				
			}
			
			public void onFailure(Throwable caught) {
				
				System.out.println("Failure: " + caught.toString());
				
			}
		});
		
	}


	
}
