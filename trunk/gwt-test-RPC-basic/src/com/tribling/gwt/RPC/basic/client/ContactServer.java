package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactServer extends RPCbasic {

		
	public ContactServer() {
	}
	
	
	public void getServerData() {
		
		// define the service you want to call
		BasicServiceAsync svc = (BasicServiceAsync) GWT.create(BasicService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) svc;
		// "/BasicService" path matches that in the xml configuration file
		endpoint.setServiceEntryPoint("/BasicService"); 

		
		// define a handler for what to do when the
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//on error
			public void onFailure(Throwable ex) {
				RootPanel.get().add(new HTML(ex.toString()));
			}

			//when we get the rpc back send the result to the root panel
			public void onSuccess(Object result) {
				RootPanel.get().add(new HTML(result.toString()));
			}
			
		};

		// execute the service
		svc.myMethod("Do Stuff", callback);
		
	}
	
}
