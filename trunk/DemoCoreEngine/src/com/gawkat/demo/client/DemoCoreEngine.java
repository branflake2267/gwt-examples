package com.gawkat.demo.client;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.ui.breadcrumbs.BreadCrumbs;
import org.gonevertical.core.client.ui.login.LoginUi;
import org.gonevertical.core.client.ui.login.LoginWidget;

import com.gawkat.demo.client.layout.ContentWidget;
import com.gawkat.demo.client.layout.FooterWidget;
import com.gawkat.demo.client.layout.LinksWidget;
import com.gawkat.demo.client.layout.LogoWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoCoreEngine implements EntryPoint, ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private LoginWidget wLogin = null;
   
  /**
   * entry point
   */
  public void onModuleLoad() {

  	String appConsumerKey = "demo_application";
  	String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6"; //salt:password  
  	
  	// setup the client persistence, something that flows throughout the site
  	cp = new ClientPersistence();
  	cp.init("UA-2862268-9", "DemoCoreEngine", appConsumerKey, appConsumerSecret);
  
  	// set up login inputs top right
  	wLogin = new LoginWidget(cp, LoginUi.LOGIN_HORIZONTAL);
  	wLogin.initSession();
   
  	// observe history so we can track every querystring change
  	History.addValueChangeHandler(this);
  	
    // move to home if no historyToken
    setHomeHistoryToken();
    
    cp.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				ClientPersistence wcp = (ClientPersistence) event.getSource();
				if (wcp.getChangeEvent() == EventManager.APPLICATION_LOADED) {
					drawLayout();
				} else if (wcp.getChangeEvent() == EventManager.USER_LOGGEDIN) {
					// Nothing to do here
				} else if (wcp.getChangeEvent() == EventManager.USER_LOGGEDOUT) {
					// Nothing to do here
				}
			}
		});
  }
  
	private void drawLayout() {
		hideApplicationLoading();
		
		LogoWidget wLogo = new LogoWidget(cp);
		LinksWidget wLinks = new LinksWidget(cp);
		BreadCrumbs wBreadCrumbs = new BreadCrumbs(cp);
		ContentWidget wMiddle = new ContentWidget(cp);
		FooterWidget wFooter = new FooterWidget(cp);

		VerticalPanel pLayout = new VerticalPanel();
		pLayout.add(wLogin);
		pLayout.add(wLogo);
		pLayout.add(wLinks);
		pLayout.add(wBreadCrumbs);
		pLayout.add(wMiddle);
		pLayout.add(wFooter);
		
		// add the entire layout to html div
	  RootPanel.get("content").add(pLayout);
	  
	  // style
		pLayout.addStyleName("dce_layout");
		wBreadCrumbs.addStyleName("dce_layout_breadcrumbs");
		
	  pLayout.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
	  
	  wBreadCrumbs.setBreadCrumbHome("Home", "dce_home");
	  wBreadCrumbs.setBreadCrumb("Test", "dce_test");
	  
	  // middle domain.tld?[historyToken]_.*
	  wBreadCrumbs.setBreadCrumbCategory("Content", "dce");
	  
	  wBreadCrumbs.draw();
	  
  }

	private void hideApplicationLoading() {
		// hide the div that has the loading icon and info. war/DemoCoreEngine.html
		try {
	    Widget w = RootPanel.get("loading");
	    w.setVisible(false);
    } catch (Exception e) {
	    e.printStackTrace();
    }
  }

	/**
	 * always use a historyToken to set home, set the home historyToken
	 */
  private void setHomeHistoryToken() {
    String historyToken = History.getToken();
    if (historyToken.length() == 0) {
      History.newItem("dce_home", true);
    }
  }

  /**
   * track all the historytoken querystring events
   */
  public void onValueChange(ValueChangeEvent<String> event) {
  	String historyToken = History.getToken();
  	cp.setTrack(historyToken);
  }
	
}
