package com.gawkat.flashcard.client;

public class Track {
  
  public static void track(String event) {
    trackNative(event); 
  }
  
  /**
   * use google analytics (integrated) for tracking
   * 
   * @param event
   */
  private static native void trackNative(String event) /*-{
    var pageTracker = $wnd._gat._getTracker("UA-2862268-22");
    pageTracker._trackPageview("/" + event);
  }-*/;

}
