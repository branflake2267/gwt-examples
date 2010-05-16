package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Home extends Composite implements ClickHandler {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	private PushButton bLogin = new PushButton("Demo SignIn");
	
	public Home(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
		
		bLogin.addClickHandler(this);
	}

	public void draw() {
	  
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bLogin);
		hp.add(new HTML("&nbsp;Try the demo sign in."));
		
		pWidget.add(new HTML("&nbsp;"));
		pWidget.add(new HTML("home widget"));
		pWidget.add(hp);
		
		
  }

  public void onClick(ClickEvent event) {
  	
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == bLogin) {
  		cp.fireChange(EventManager.LOGIN_DEMO);
  	}
  	
  }
	
}
