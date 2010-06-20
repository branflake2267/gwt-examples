package com.gawkat.demo.client.layout;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.admin.AdminWidget;
import org.gonevertical.core.client.global.QueryString;
import org.gonevertical.core.client.global.QueryStringData;

import com.gawkat.demo.client.layout.widgets.Home;
import com.gawkat.demo.client.layout.widgets.TestWidget;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentWidget extends Composite implements ValueChangeHandler<String> {

	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	private FlowPanel pContent = new FlowPanel();
	
	private AdminWidget wAccount = null;
	
	public ContentWidget(ClientPersistence cp) {
		this.cp = cp;
		
		wAccount = new AdminWidget(cp);
		
		pWidget.add(pContent);
		pWidget.add(wAccount);
		
		initWidget(pWidget);
		
		pWidget.setWidth("100%");
		pWidget.addStyleName("gv_layout_middle");
		
		History.addValueChangeHandler(this);
		
		changeState(History.getToken());
	}
	
	public void changeState(String historyToken) {
		if (historyToken == null) {
			return;
		}
		
		if (wAccount.getMatchHistoryToken() == true) {
  		drawState_Account();
  		
  	} else if (historyToken.matches("^dce_.*?$") == true ) {
  		drawState_Dce();
  		
  	} else {
  		drawState_Dce();
  	}
		
	}
	
	private void drawState_Dce() {
		pContent.setVisible(true);
		pContent.clear();
		
		QueryStringData qsd = QueryString.getQueryStringData();
		
		if (qsd == null || qsd.getHistoryToken() == null) {
			// TODO - does this happen?
			System.err.println("ContentWidget.drawState_Dce(): historyToken Null");
			
		} else if (qsd.getHistoryToken().equals("dce_home") == true) {
			drawState_HomePage();
			
		} else if (qsd.getHistoryToken().equals("dce_test") == true) {
			drawState_Test();
		}
		
  }
	
	public void drawState_HomePage() {
		Home home = new Home(cp);
		pContent.add(home);
		home.draw();
	}
	
	private void drawState_Test() {
		TestWidget wTest = new TestWidget(cp);
		pContent.add(wTest);
		wTest.draw();
  }

	private void drawState_Account() {
		pContent.setVisible(false);
		wAccount.draw();
  }

  public void onValueChange(ValueChangeEvent<String> event) {
    String historyToken = event.getValue();
  	changeState(historyToken);
  }
  
  
}
