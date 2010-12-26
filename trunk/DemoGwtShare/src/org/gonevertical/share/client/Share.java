package org.gonevertical.share.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;

public class Share extends Composite {
  private HTML hBuzz;
  private HTML hFacebook;

  public Share() {
    
    VerticalPanel vpMain = new VerticalPanel();
    initWidget(vpMain);
    
    HorizontalPanel vpShare = new HorizontalPanel();
    vpMain.add(vpShare);
    
    hBuzz = new HTML("&nbsp;", true);
    vpShare.add(hBuzz);
    
    HTML htmlnbsp = new HTML("&nbsp;", true);
    vpShare.add(htmlnbsp);
    
    hFacebook = new HTML("&nbsp;", true);
    vpShare.add(hFacebook);
    
    setup();
  }

  private void setup() {

    setupBuzzScript();
    
    setupFacebookScript();
    
    drawBuzzButton();
    
    drawFacebookButton();
  }

  private void drawBuzzButton() {
    String s = "<a " +
    "href=\"http://www.google.com/buzz/post\" " +
    "class=\"google-buzz-button\" " +
    "title=\"Check out this GWT Share Demo\" " +
    "data-message=\"I made this to test out some sharing buttons.\" " +
    "data-url=\"http://demogwtshare.appspot.com\" " +
    "data-imageurl=\"http://demogwtshare.appspot.com/images/preview.jpg\" " +
    "data-locale=\"en\" " +
    "data-button-style=\"normal-count\"></a>";
    
    getHBuzz().setHTML(s);
  }
  
  private void drawFacebookButton() {
    String s = "<fb:like " +
    "href=\"http://demogwtshare.appspot.com\" " +
    "layout=\"box_count\" " +
    "show_faces=\"true\" " +
    "width=\"50\">" +
    "</fb:like>";
    
    getHFacebook().setHTML(s);
  }
  
  private void setupBuzzScript() {
    Document doc = Document.get();
    ScriptElement script = doc.createScriptElement();
    script.setSrc("http://www.google.com/buzz/api/button.js");
    script.setType("text/javascript");
    script.setLang("javascript");
    doc.getBody().appendChild(script);
  }
  
  private void setupFacebookScript() {
    Document doc = Document.get();
    ScriptElement script = doc.createScriptElement();
    script.setSrc("http://connect.facebook.net/en_US/all.js#xfbml=1");
    script.setType("text/javascript");
    script.setLang("javascript");
    doc.getBody().appendChild(script);
  }

  public HTML getHBuzz() {
    return hBuzz;
  }
  public HTML getHFacebook() {
    return hFacebook;
  }
}
