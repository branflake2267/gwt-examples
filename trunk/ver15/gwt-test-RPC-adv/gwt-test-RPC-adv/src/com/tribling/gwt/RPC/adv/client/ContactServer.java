package com.tribling.gwt.RPC.adv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;


public class ContactServer extends RPCadv {

	
	public ContactServer() {
	}
	
	

	
	public void getServerData() {
		
		
		// define the service you want to call
		RPCServiceAsync svc = (RPCServiceAsync) GWT.create(RPCService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) svc;
		// "/basicService" path matches that in the xml configuration file
		endpoint.setServiceEntryPoint("/AdvService"); 

		
		// define a handler for what to do when the
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//on error
			public void onFailure(Throwable ex) {
				RootPanel.get().add(new HTML("Error:: " + ex.toString() + " :: End"));
			}

			//when we get the rpc back send the result to the root panel
			public void onSuccess(Object result) {
				
				//cast 
				Person[] people = (Person[]) result;
				
				String myName = people[0].name;
				//String MyDesc = people[0].getDescription();
				
				String sAll = myName.toString();
				//String sAll = new String("success");
				
				RootPanel.get().add(new HTML(sAll.toString()));
			}
			
		};

		// execute the service
		svc.myMethodObject2("get object", callback);
	}
	
	
}//end class
