package com.bellanovascoffee.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BellanovasCoffee implements EntryPoint {


  public void onModuleLoad() {
     
    addHistory();
    
    History.newItem("home");
  }

  private void clearContent() {
    RootPanel.get("content").clear();
  }
  
  private void addContent(Widget w) {
    clearContent();
    RootPanel.get("content").add(w);
  }
  
  private void addImage(Widget w) {
    RootPanel.get("images").clear();
    RootPanel.get("images").add(new HTML("<br>"));
    RootPanel.get("images").add(w);
  }
  
  private void drawHome() {
    String page = "/home.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawHomePics();
  }
  
  private void drawCoffees() {
    String page = "/coffees.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawCoffeesPics();
  }
  
  private void drawAbout() {
    String page = "/aboutus.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawAboutPics();
  }
  
  private void drawFundraising() {
    String page = "/fundraising.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawFundraisingPics();
  }
  
  private void drawEndorsements() {
    String page = "/endorsements.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawEndorsementPics();
  }
  
  private void drawHistory() {
    String page = "/history.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawHistoryPics();
  }
  
  private void drawContact() {
    String page = "/contactus.html";
    HTMLInclude h = new HTMLInclude(page);
    addContent(h);
    drawContactUsPics();
  }
  
  private void drawHomePics() {
    Image i1 = new Image("images/2297t.jpg");
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    addImage(vp);
  }
  
  private void drawCoffeesPics() {
    Image i1 = new Image("images/2298t.jpg");//pic 2298
    Image i2 = new Image("images/2299t.jpg");//pic 2299
    Image i3 = new Image("images/2301t.jpg");//pic 2301
    Image i4 = new Image("images/2300t.jpg");//pic 2300
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    vp.add(new HTML("&nbsp;"));
    vp.add(i2);
    vp.add(new HTML("&nbsp;"));
    vp.add(i3);
    vp.add(new HTML("&nbsp;"));
    vp.add(i4);
    addImage(vp);
    vp.addStyleName("pic");
  }
  
  private void drawAboutPics() {
    Image i1 = new Image("images/2296t.jpg");//2296
    Image i2 = new Image("images/2295t.jpg");//2295
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    vp.add(new HTML("&nbsp;"));
    vp.add(i2);
    addImage(vp);
    vp.addStyleName("pic");
  }
  
  private void drawFundraisingPics() {
    Image i1 = new Image("images/coffee-roaster.jpg"); // coffee-roaser?
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    addImage(vp);
    vp.addStyleName("pic");
  }
  
  private void drawEndorsementPics() {
    Image i1 = new Image("images/coffeebeansphoto.jpg");//coffeebeansphoto.jpg
    Image i2 = new Image("images/beans.jpg");//beans.jpg
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    vp.add(new HTML("&nbsp;"));
    vp.add(i2);
    addImage(vp);
    vp.addStyleName("pic");
    vp.setCellHorizontalAlignment(i1, HorizontalPanel.ALIGN_CENTER);
    vp.setCellHorizontalAlignment(i2, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void drawHistoryPics() {
    HTML h1 = new HTML("The Farm in 1957");
    HTML h2 = new HTML("Today");
    
    Image i1 = new Image("images/2294t.jpg");//2294
    Image i2 = new Image("images/2303t.jpg");//2303
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    vp.add(h1);
    vp.add(new HTML("&nbsp;"));
    vp.add(i2);
    vp.add(h2);
    addImage(vp);
    vp.addStyleName("pic");
    vp.setCellHorizontalAlignment(h1, HorizontalPanel.ALIGN_CENTER);
    vp.setCellHorizontalAlignment(h2, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void drawContactUsPics() {
    Image i1 = new Image("images/2304t.jpg");// 2304
    Image i2 = new Image("images/plantation-web.jpg");// plantation-web.jpg
    VerticalPanel vp = new VerticalPanel();
    vp.add(i1);
    vp.add(new HTML("&nbsp;"));
    vp.add(i2);
    addImage(vp);
    vp.addStyleName("pic");
    vp.setCellHorizontalAlignment(i1, HorizontalPanel.ALIGN_CENTER);
    vp.setCellHorizontalAlignment(i2, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void addHistory() {
   
    History.addValueChangeHandler(new ValueChangeHandler<String>() {
      public void onValueChange(ValueChangeEvent<String> event) {
        String historyToken = event.getValue();

        if (historyToken.contains("home")) {
          drawHome();
        } else if (historyToken.contains("coffees")) {
          drawCoffees();
        } else if (historyToken.contains("aboutus")) {
          drawAbout();
        } else if (historyToken.contains("fundraising")) {
          drawFundraising();
        } else if (historyToken.contains("endorsements")) {
          drawEndorsements();
        } else if (historyToken.contains("history")) {
          drawHistory();
        } else if (historyToken.contains("contactus")) {
          drawContact();
        } 
        
      }
    });

    
  }

  
}
