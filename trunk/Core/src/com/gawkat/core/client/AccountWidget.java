package com.gawkat.core.client;

import java.util.HashMap;

import com.gawkat.core.client.account.AccountManagementNavigation;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.google.gwt.user.client.ui.Composite;

public class AccountWidget extends Composite {

  // have ready the accounts management after init
  private AccountManagementNavigation accountManagement = null;
  
  
  public AccountWidget() {
    
  }
  
  /**
   * account managment setup
   * 
   * @param qsd
   */
  private void drawAccountsManagement(QueryStringData qsd) {
    if (accountManagement == null) {
      accountManagement = new AccountManagementNavigation();
    }
    accountManagement.setQueryStringData(qsd);
  }
  
  /**
   * observe the url for changes
   */
  public void onHistoryChanged(String historyToken) {
    
    // this allows us to get the parameters in the historyToken domain.tld/page.html#anchor?params
    QueryString parse = new QueryString();
    QueryStringData qsd = parse.getQueryStringData();
    historyToken = qsd.getHistoryToken();
    HashMap<String, String> params = qsd.getParameters();
    
    // url navigation - with parameters
    if (historyToken.matches("account_.*")) { // draw account mangement with anything that has account_ in the anchor
      //drawAccountsManagement(qsd);
    } 
    
  }
}
