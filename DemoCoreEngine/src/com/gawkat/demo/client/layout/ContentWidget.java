package com.gawkat.demo.client.layout;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import org.gonevertical.core.client.global.QueryString;
import org.gonevertical.core.client.global.QueryStringData;
import org.gonevertical.core.client.ui.account.AccountTabs;
import org.gonevertical.core.client.ui.admin.AdminTabs;

import com.gawkat.demo.client.layout.widgets.Home;
import com.gawkat.demo.client.layout.widgets.TestWidget;

public class ContentWidget extends Composite implements ValueChangeHandler<String> {
	private ClientPersistence cp;
	private VerticalPanel verticalPanel;
	private AccountTabs accountTabs;
	private AdminTabs adminTabs;
	private Home home;
	private TestWidget testWidget;

	public ContentWidget(ClientPersistence cp) {
		this.cp = cp;
		initWidget(getVerticalPanel());
		
		History.addValueChangeHandler(this);
		
		changeState(History.getToken());
	}

	public void changeState(String historyToken) {
		if (historyToken == null) {
			return;
		}
		
		if (adminTabs.getMatchHistoryToken() == true) {
  		drawState_Admin();
  		
  	} else if (accountTabs.getMatchHistoryToken() == true) {
  		drawState_Account();
  		
  	} else if (historyToken.matches("^dce_.*?$") == true ) {
  		drawState_Dce();
  		
  	} else {
  		drawState_Dce();
  	}
		
	}
	
	private void drawState_Dce() {
	
		QueryStringData qsd = QueryString.getQueryStringData();
		
		if (qsd == null || qsd.getHistoryToken() == null) {
			drawState_HomePage();
			
		} else if (qsd.getHistoryToken().equals("dce_home") == true) {
			drawState_HomePage();
			
		} else if (qsd.getHistoryToken().equals("dce_test") == true) {
			drawState_Test();
			
		} else if (adminTabs.getMatchHistoryToken() == true) {
			drawState_Admin();
			
		} else if (accountTabs.getMatchHistoryToken() == true) {
			drawState_Account();
			
		}
		
  }
	
	public void drawState_HomePage() {
		home.setVisible(true);
		home.draw();
		
		adminTabs.setVisible(false);
		accountTabs.setVisible(false);
		testWidget.setVisible(false);
	}
	
	private void drawState_Test() {
		testWidget.setVisible(true);
		testWidget.draw();
		
		home.setVisible(false);
		adminTabs.setVisible(false);
		accountTabs.setVisible(false);
  }

	private void drawState_Admin() {
		adminTabs.setVisible(true);
		adminTabs.draw();
		
		home.setVisible(false);
		testWidget.setVisible(false);
		accountTabs.setVisible(false);
  }
	
	private void drawState_Account() {
		accountTabs.setVisible(true);
		accountTabs.draw();
		
		home.setVisible(false);
		testWidget.setVisible(false);
		adminTabs.setVisible(false);
  }
	
	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getHome());
			verticalPanel.add(getTestWidget());
			verticalPanel.add(getAccountTabs());
			verticalPanel.add(getAdminTabs());
			
			verticalPanel.setWidth("100%");
		}
		return verticalPanel;
	}
	private AccountTabs getAccountTabs() {
		if (accountTabs == null) {
			accountTabs = new AccountTabs(cp);
		}
		return accountTabs;
	}
	private AdminTabs getAdminTabs() {
		if (adminTabs == null) {
			adminTabs = new AdminTabs(cp);
		}
		return adminTabs;
	}
	
	private Home getHome() {
		if (home == null) {
			home = new Home(cp);
		}
		return home;
	}

	private TestWidget getTestWidget() {
		if (testWidget == null) {
			testWidget = new TestWidget(cp);
		}
		return testWidget;
	}
	
  public void onValueChange(ValueChangeEvent<String> event) {
		String historyToken = event.getValue();
  	changeState(historyToken);
  }
  
}
