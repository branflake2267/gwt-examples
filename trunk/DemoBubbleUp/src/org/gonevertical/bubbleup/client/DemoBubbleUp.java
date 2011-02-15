package org.gonevertical.bubbleup.client;

import org.gonevertical.bubbleup.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
public class DemoBubbleUp implements EntryPoint {


  public void onModuleLoad() {

    // global widget observation widget
    ClientPersistence cp = new ClientPersistence();
    
    // my parent widget example
    ParentTopWidget w = new ParentTopWidget(cp);
    
    // add to root panel
    RootPanel.get().add(w);
    
    // global app observation
    cp.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        
      }
    });
    
  }
  
}
