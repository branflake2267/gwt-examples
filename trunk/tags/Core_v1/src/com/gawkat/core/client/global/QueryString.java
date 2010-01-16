package com.gawkat.core.client.global;

import java.util.HashMap;

import com.google.gwt.user.client.History;

/**
 * @author branflake2267
 * 
 */
public class QueryString {

  public QueryString() {
  }

  /**
   * get query string parameters anywhere
   * @return
   */
  public static QueryStringData getQueryStringData() {

    String historyToken = History.getToken();
    
    String ht = getHistoryToken_Anchor(historyToken);
    HashMap<String, String> params = getHistoryToken_Parameters(historyToken);
    
    QueryStringData queryStringData = new QueryStringData();
    queryStringData.setQueryStringData(ht, params);

    return queryStringData;
  }

  /**
   * get historyToken by itself
   * 
   * like domain.tld#[historyToken]?params1=a&params2=b
   * 
   * @param historyToken
   * @return
   */
  private static String getHistoryToken_Anchor(String historyToken) {

    // skip if there is no question mark
    if (!historyToken.contains("?")) {
      return historyToken;
    }

    // get just the historyToken/anchor tag
    String[] arStr = historyToken.split("\\?");
    historyToken = arStr[0];

    return historyToken;
  }

  /**
   * get historyToken parameters
   * 
   * like domain.tld#historyToken?[?params1=a&params2=b]
   * 
   * @param historyToken (anchor tag)
   * @return HashMap of the parameters ([varName, var] OR [key, value])
   */
  private static HashMap<String, String> getHistoryToken_Parameters(String historyToken) {

    // skip if there is no question mark
    if (!historyToken.contains("?")) {
      return null;
    }
    int questionMarkIndex = historyToken.indexOf("?") + 1;

    // get the sub string of parameters var=1&var2=2&var3=3... params1=a&params2=b...
    String[] arStr = historyToken.substring(questionMarkIndex, historyToken.length()).split("&");
    HashMap<String, String> params = new HashMap<String, String>();
    for (int i = 0; i < arStr.length; i++) {
      String[] substr = arStr[i].split("=");
      params.put(substr[0], substr[1]);
    }

    return params;
  }

}
