package com.gawkat.core.client.account;

import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyAccount extends Composite {

  private OAuthTokenData accessToken = null;
  
  private int changeEvent = 0;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public MyAccount() {
    
    HorizontalPanel pmenu = new HorizontalPanel();
    pmenu.add(new HTML("&nbsp;"));

    pWidget.add(pmenu);
    
    initWidget(pWidget);
    
    pWidget.addStyleName("");
  }
  
  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }


  
}
