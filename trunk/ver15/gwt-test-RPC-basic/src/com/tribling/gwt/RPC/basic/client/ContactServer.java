package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactServer {

	private BasicServiceAsync callProvider;

	/**
	 * constructor
	 */
	public ContactServer() {
		
		// get ready for talking to the server
		rpcInit();
		
	}
	
	/**
	 * Init the RPC provider
	 * 
	 * NOTE: ./public/RPCbasic.gwt.xml - determines the servlet context
	 * read more of my gwtTomcat documentation in http://gwt-examples.googlecode.com
	 */
    private void rpcInit() {
    	
    	callProvider = (BasicServiceAsync) GWT.create(BasicService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;
        
        String moduleRelativeURL = GWT.getModuleBaseURL() + "BasicService";
        target.setServiceEntryPoint(moduleRelativeURL);
        
    }
	
    /**
     * call the server and ask it to do something
     */
	public void getServerData() {
		
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			
			// on error
			public void onFailure(Throwable ex) {
				RootPanel.get().add(new HTML(ex.toString()));
			}

			// on success
			public void onSuccess(String result) {
				
				// show the returned string on the screen
				RootPanel.get().add(new HTML(result.toString()));
				
			}
		};

		String s = "(client side string)";
		
		// execute the service
		callProvider.myMethod(s, callback);
		
	}
	
}
