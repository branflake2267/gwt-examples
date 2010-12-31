package org.gonevertica.adsense.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

public class Layout extends Composite {
  private VerticalPanel vpAdsenseScript;
  private HTML hadsensediv;
  private AdsWidget adsWidget;

  /**
   * built with gwt designer
   */
  public Layout() {
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HTML htmlTheExamplesBelow = new HTML("The examples below are how I integrate adsense into my GWT applicatoins<br/><a href=\"http://c.gawkat.com\">See it in the works on my Gawkat.com</a><br/><br/><br/>", true);
    verticalPanel.add(htmlTheExamplesBelow);
    
    VerticalPanel vpFrame = new VerticalPanel();
    verticalPanel.add(vpFrame);
    
    HTML htmlThisIsLayout = new HTML("This is layout is GWT rendered and the adsense below is loaded by iframe.", true);
    vpFrame.add(htmlThisIsLayout);
    
    adsWidget = new AdsWidget();
    vpFrame.add(adsWidget);
    
    VerticalPanel vpSpacer = new VerticalPanel();
    verticalPanel.add(vpSpacer);
    
    HTML htmlnbsp = new HTML("&nbsp;<br/></br></br>", true);
    vpSpacer.add(htmlnbsp);
    
    VerticalPanel vpScript = new VerticalPanel();
    verticalPanel.add(vpScript);
    
    HTML htmlTestScriptInclude = new HTML("<br/>This is an example of the javascript included into Java code. (This will erase the rest of the page, but does render ad)", true);
    vpScript.add(htmlTestScriptInclude);
    
    vpAdsenseScript = new VerticalPanel();
    vpScript.add(vpAdsenseScript);
    
    setup();
   
  }

  /**
   * setup manual stuff
   */
  private void setup() {
    
    /**
     * tell the iframe to draw the ad
     */
    adsWidget.draw();
    
    setupAdsenseVars();
    //setupAdsenseScript();
  }
  
  public static native void setupAdsenseVars() /*-{
      $wnd.google_ad_client = "ca-pub-0032065764310410";
      $wnd.google_ad_slot = "4089409905";
      $wnd.google_ad_width = 728;
      $wnd.google_ad_height = 90;
  }-*/;

  private void setupAdsenseScript() {
    Document doc = Document.get();
    ScriptElement script = doc.createScriptElement();
    script.setSrc("http://pagead2.googlesyndication.com/pagead/show_ads.js");
    script.setType("text/javascript");
    script.setLang("javascript");
    vpAdsenseScript.getElement().appendChild(script);
  }

  public VerticalPanel getVpAdsenseScript() {
    return vpAdsenseScript;
  }
  public HTML getHadsensediv() {
    return hadsensediv;
  }
  public AdsWidget getAdsWidget() {
    return adsWidget;
  }
}
