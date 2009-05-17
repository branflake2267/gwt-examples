package com.gawkat.gwt.example_handler.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Combine extends Composite implements ClickHandler, ChangeHandler {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private BoxLeft wLeft = new BoxLeft();
  
  private BoxRight wRight = new BoxRight();
  
  public Combine() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(wLeft);
    hp.add(new HTML("&nbsp;&nbsp;"));
    hp.add(wRight);
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    handlers();
    
    pWidget.setStyleName("combine");
    
    track("Load");
  }

  
  private void handlers() {
    
    // observes all clicks on the entire left box widget
    wLeft.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        // doing nothing here for now. this also is like-> wLeft.addClickHandler(this);
      }
    });
    
    // observes all clicks on the entire left box widget
    wLeft.addClickHandler(this);
    
    // observes changes on left box widget
    wLeft.addChangeHandler(this);
    
    // observes all clicks on the entire left box widget
    wRight.addClickHandler(this);
    
    // observes changes on left box widget
    wRight.addChangeHandler(this);
    
  }


  private void setLeft() {
    wRight.setColor(Color.getRandomColor());
  }
  
  private void setRight() {
    wLeft.setColor(Color.getRandomColor());
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    //System.out.println("click");

    // click handlers send in the clicks on the left and right widgets to here
    
  }

  public void onChange(ChangeEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == wLeft) {
      setLeft();
      track("LeftClick");
    } else if (sender == wRight) {
      setRight();
      track("LeftClick");
    }
    
  }

  /**
   * use google analytics (integrated) for tracking
   * 
   * @param event
   */
  private static native void track(String event) /*-{
    
    var pageTracker = $wnd._gat._getTracker("UA-2862268-9");
    //pageTracker._trackPageview();
    pageTracker._trackPageview("/GWTExample/Combine/" + event);
    
    //alert("track");
  }-*/;
  
}
