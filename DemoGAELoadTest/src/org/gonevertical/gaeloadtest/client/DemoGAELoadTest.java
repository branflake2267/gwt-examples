package org.gonevertical.gaeloadtest.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.rpc.server.RPC;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGAELoadTest implements EntryPoint {


  private RpcServiceAsync rpc;

	public void onModuleLoad() {
  	Date d = new Date();
  	HTML html = new HTML(d.getTime() + " - " + d.toString() + " - GWT has Loaded");
  	RootPanel.get().add(html);
  	
  	rpc = RpcInit.initRpc();
  	d = new Date();
  	HTML html2 = new HTML(d.getTime() + " - " + d.toString() + " - Rpc Ready - to calling");
  	RootPanel.get().add(html2);
  	
  	testRpc(1);
  	testRpc(2);
  	testRpc(3);
  	testRpc(4);
  }

	private void testRpc(final int tryCount) {
		rpc.saveTest(new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				Date d = new Date();
		  	HTML html = new HTML(d.getTime() + " - " + d.toString() + " - try#: " + tryCount +" - Saved something to TestJdo");
		  	RootPanel.get().add(html);
			}
			public void onFailure(Throwable caught) {
				Window.alert("Problem with rpc");
			}
		});
  }
	
}
