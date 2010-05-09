package org.gonevertical.core.client;

import java.util.HashMap;

import org.gonevertical.core.client.global.QueryString;
import org.gonevertical.core.client.global.QueryStringData;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumbs extends Composite implements ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private HorizontalPanel pWidget = new HorizontalPanel();
  
  private HorizontalPanel pCrumbs = new HorizontalPanel();
  
  private HashMap<String,String> breadCrumbs = new HashMap<String,String>();
  
  private HashMap<String, String> breadCrumbsCategory = new HashMap<String,String>();

  /**
   * home history token
   */
	private String homeHistoryToken = "home";
  
  /**
   * init bread crumbs widget
   *  
   * use of breadCrumbs.put("historyToken", "breadCrumbName");
   * 
   * @param cp
   */
  public BreadCrumbs(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pCrumbs);
    
    initWidget(pWidget);
    
    // set a reference for the entire application to access this widget
    cp.setBreadCrumbsWidgetReference(this);
    
    // observe the querystring historyToken events
    History.addValueChangeHandler(this);
    
    pWidget.addStyleName("core-Account-Breadcrumbs"); 
    
  }
  
  public void setLoadingWidget(Widget w) {
  	if (w == null) {
  		System.out.println("BreadCrumbs.setLoadingWidget(): Error, you set a widget that was null");
  		return;
  	}
  	pWidget.add(new HTML("&nbsp;"));
  	pWidget.add(w);
  }
  
  /**
   * Show a bread crumb for this historyToken
   * 
   * @param forHistoryToken
   * @param showCrumbName
   */
  public void setBreadCrumb(String showCrumbName, String forHistoryToken) {
  	if (forHistoryToken == null || showCrumbName == null || forHistoryToken.trim().length() == 0 || showCrumbName.trim().length() == 0) {
  		System.out.println("Error: BreadCrumbs.addBreadCrumb(): Did you forget to add the name or value to the bread crumb");
  		return;
  	}
  	breadCrumbs.put(forHistoryToken, showCrumbName);
  }
  
  public void setBreadCrumbCategory(String showCrumbName, String forHistoryToken) {
  	if (forHistoryToken == null || showCrumbName == null || forHistoryToken.trim().length() == 0 || showCrumbName.trim().length() == 0) {
  		System.out.println("Error: BreadCrumbs.addBreadCrumbMiddle(): Did you forget to add the name or value to the bread crumb");
  		return;
  	}
  	breadCrumbsCategory.put(forHistoryToken, showCrumbName);
  }
  
  public void setBreadCrumbHome(String showCrumbName, String forHistoryToken) {
  	setBreadCrumb(showCrumbName, forHistoryToken);
  	this.homeHistoryToken = forHistoryToken;
  }
    
  public void draw() {
    pCrumbs.clear();
    
    QueryStringData qsd = QueryString.getQueryStringData();
    
    // draw home
    drawHome();
    
    drawMiddle(qsd);
        
    drawEnd(qsd);
  }
  
  /**
   * draw home bread token
   */
  private void drawHome() {
  	String breadCrumbName = breadCrumbs.get(homeHistoryToken);
    BreadCrumb b = new BreadCrumb(breadCrumbName, homeHistoryToken);
    pCrumbs.add(b);
  }
  
  /**
   * bread crumb the domain.tld?[historyToken_.*]
   */
  private void drawMiddle(QueryStringData qsd) {
  	
  	String historyToken = qsd.getHistoryToken_ToUnderScore();
  	if (historyToken == null) {
  		return;
  	}
  	
  	String breadCrumbName = breadCrumbsCategory.get(historyToken);
  	
  	if (breadCrumbName == null || breadCrumbName.trim().length() == 0) {
  		return;
  	}
  	
    BreadCrumb b = new BreadCrumb(breadCrumbName, homeHistoryToken);
    pCrumbs.add(b);
  }

  private void drawEnd(QueryStringData qsd) {
    
  	// get name from hashmap
  	String historyToken = qsd.getHistoryToken();
  	String name = breadCrumbs.get(historyToken);
  	
  	if (historyToken.equals(homeHistoryToken) || name == null || name.trim().length() == 0) {
  		return;
  	}
  	
    BreadCrumb b = new BreadCrumb(name);
    pCrumbs.add(b);
  }
  
  public void onValueChange(ValueChangeEvent<String> event) {
    draw(); 
  }
  
  
  
}
