package com.tribling.gwt.RPC.adv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;



public class ContactServer extends RPCadv {

	
	public ContactServer() {
	}
	
	

	
	public void getServerData() {

		//debug
		String debugRPC = "debug rpc path: " + GWT.getModuleBaseURL() + "AdvService";
		RootPanel.get().add(new Label(debugRPC));
		RootPanel.get().add(new HTML("<br><br>"));
		
   
    	
        // Initialize the service.
		RPCServiceAsync svc = (RPCServiceAsync) GWT.create(RPCService.class);;
        ServiceDefTarget target = (ServiceDefTarget) svc;

        // Use a module-relative URLs to ensure that this client code can find
        // its way home, even when the URL changes (as might happen when you
        // deploy this as a webapp under an external servlet container).
        String moduleRelativeURL = GWT.getModuleBaseURL() + "AdvService";
        target.setServiceEntryPoint(moduleRelativeURL);
		
		
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
				
				//get name out of object
				String myName = people[0].name;
				//String MyDesc = people[0].getDescription();
				
				String sAll = myName.toString();
				//String sAll = new String("success");
				
				//say what happens
				String SayWhatHappens = "If you see my Name it came from a person object from the rpc from the server.";
				RootPanel.get().add(new Label(SayWhatHappens));
				
				//show
				RootPanel.get().add(new HTML(sAll.toString()));
			}
			
		};

		String SendString = "get person object";
		
		// execute the service
		svc.myMethodObject2(SendString, callback);
	}
	
	
}//end class
