package org.gonevertical.democanvas.client;

import java.util.HashMap;


public class Global_String {

  public static String removeHtmlTags(String html) {
    String regex = "(<([^>]+)>)";
    html = html.replaceAll(regex, "");
    return html;
  }

  /**
   * encode the string so we can use it as a value in the query string
   *  notes: http://www.w3schools.com/TAGS/ref_urlencode.asp
   * @param s
   * @return
   */
  public static String encodeUrlDelimiters(String s) {

    if (s == null) {
      return null;
    }

    s = s.replaceAll(";", "%2F");
    s = s.replaceAll("/", "%2F");
    s = s.replaceAll(":", "%3A");
    s = s.replaceAll("\\?", "%3F");
    s = s.replaceAll("&", "%26");
    s = s.replaceAll("\\=", "%3D");
    s = s.replaceAll("\\+", "%2B");
    s = s.replaceAll("\\$", "%24");
    s = s.replaceAll(",", "%2C");
    s = s.replaceAll("#", "%23");

    return s;
  }

  public  static HashMap<String, String> getParameters(String delimiter, String s) {
    if (s == null || s.length() == 0 || delimiter == null || delimiter.length() == 0 || s.contains(delimiter) == false) {
      return null;
    }
    String[] arStr = s.split(delimiter);
    HashMap<String, String> params = new HashMap<String, String>();
    for (int i = 0; i < arStr.length; i++) {
      String[] substr = arStr[i].split("=");
      params.put(substr[0], substr[1]);
    }
    return params;
  }
  
}
