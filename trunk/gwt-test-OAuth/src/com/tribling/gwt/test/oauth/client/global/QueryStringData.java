package com.tribling.gwt.test.oauth.client.global;

import java.util.HashMap;
import java.util.Iterator;

public class QueryStringData {

  private String historyToken;
  
  private HashMap<String, String> parameters;
  
  public void setQueryStringData(String historyToken, HashMap<String, String> parameters) {
    this.historyToken = historyToken;
    this.parameters = parameters;
  }
  
  /**
   * get historyToken domain.tld#historyToken?param1=a&param2=b
   * @return
   */
  public String getHistoryToken() {
    return this.historyToken;
  }
  
  public HashMap<String, String> getParameters() {
    return this.parameters;
  }
  
  /**
   * get query string parameter and return a integer
   * 
   * @param key
   * @return
   */
  public int getParameter_Int(String key) {

    if (parameters == null) {
      return -1;
    }

    if (key == null) {
      return -1;
    }

    if (key.length() == 0) {
      return -1;
    }

    Object o = parameters.get(key);
    if (o == null) {
      return -1;
    }

    int rtn = -1;
    if (o.toString().length() > 0) {
      rtn = Integer.parseInt(o.toString());
    }

    return rtn;
  }

  /**
   * get a query string parameter and return a String
   * 
   * @param parameters
   * @param key
   * @return
   */
  public String getParameter_String(String key) {

    if (parameters == null) {
      return null;
    }

    if (key == null) {
      return null;
    }

    if (key.length() == 0) {
      return null;
    }

    String rtn = null;
    if (parameters.get(key).toString().length() > 0) {
      rtn = parameters.get(key).toString().trim();
    }

    return rtn;
  }
  
  /**
   * get HashMap parameters in url concatenated form 
   * like [param1=a&param2=b&param3=c]
   * 
   * @return
   */
  public String getParametersConcatenated() {

    if (parameters == null) {
      return "";
    }

    String qs = "";

    Iterator<String> iterator = parameters.keySet().iterator();
    int i = 0;
    while (iterator.hasNext()) {

      String key = iterator.next().toString();

      qs += key + "=" + parameters.get(key).toString();

      if (i < parameters.size() - 1) {
        qs += "&";
      }
      i++;
    }

    return qs;
  }
  
}
