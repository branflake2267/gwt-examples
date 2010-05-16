package com.gawkat.demo.client;

import org.gonevertical.core.client.BreadCrumbs;
import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.Footer;
import org.gonevertical.core.client.Track;
import org.gonevertical.core.client.account.AccountWidget;
import org.gonevertical.core.client.account.thingtype.ThingTypes;
import org.gonevertical.core.client.account.ui.LoginUi;
import org.gonevertical.core.client.account.ui.LoginWidget;

import com.gawkat.demo.client.layout.FooterWidget;
import com.gawkat.demo.client.layout.LinksWidget;
import com.gawkat.demo.client.layout.LogoWidget;
import com.gawkat.demo.client.layout.ContentWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
  	wLogin = new LoginWidget(cp);
    wLogin.initSession();
    wLogin.setUi(LoginUi.LOGIN_HORIZONTAL);

    // draw the overall layout
    drawLayout();
    
  	// observe history so we can track every querystring change
  	History.addValueChangeHandler(this);
  	
    // move to home if no historyToken
    setHomeHistoryToken();
  }
  
	private void drawLayout() {
		
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
		
		pLayout.addStyleName("dce_layout");
		
		// add the entire layout to html div
	  RootPanel.get("content").add(pLayout);
	  
		pLayout.addStyleName("dce_layout");
	  pLayout.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
	  
	  wBreadCrumbs.setBreadCrumbHome("Home", "dce_home");
	  wBreadCrumbs.setBreadCrumb("Test", "dce_test");
	  
	  // middle domain.tld?[historyToken]_.*
	  wBreadCrumbs.setBreadCrumbCategory("Content", "dce");
	  
	  wBreadCrumbs.draw();
	  
	  wBreadCrumbs.addStyleName("dce_layout_breadcrumbs");
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
