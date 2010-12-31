package org.gonevertical.demo.client;

import com.google.gwt.gadgets.client.AdsFeature;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.gadgets.client.GadgetAds;
import com.google.gwt.gadgets.client.GoogleAnalyticsFeature;
import com.google.gwt.gadgets.client.NeedsAds;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.NeedsGoogleAnalytics;
import com.google.gwt.gadgets.client.NeedsSetPrefs;
import com.google.gwt.gadgets.client.NeedsSetTitle;
import com.google.gwt.gadgets.client.NeedsViews;
import com.google.gwt.gadgets.client.SetPrefsFeature;
import com.google.gwt.gadgets.client.SetTitleFeature;
import com.google.gwt.gadgets.client.ViewFeature;
import com.google.gwt.gadgets.client.osapi.NeedsOsapi;
import com.google.gwt.gadgets.client.osapi.OsapiFeature;
import com.google.gwt.gadgets.client.rpc.NeedsRpc;
import com.google.gwt.gadgets.client.rpc.RpcFeature;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Spreadsheet Gadget", //
    directory_title = "Testing GWT Spreadsheet Docs Gadget", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVertical.org", //
    height = 610, //
    width = 400)
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
public class SpreadsheetGadget extends Gadget<SpreadsheetGadgetPreferences> implements 
    NeedsDynamicHeight, NeedsAds, NeedsGoogleAnalytics, NeedsOsapi, NeedsSetTitle, NeedsViews, 
    NeedsRpc,NeedsSetPrefs {

  private GoogleAnalyticsFeature analyFeature;
  private AdsFeature adsFeature;
  private DynamicHeightFeature heightFeature;
  private OsapiFeature osapiFeature;
  private ViewFeature viewFeature;
  private SetTitleFeature titleFeature;
  private SetPrefsFeature setPreFeature;
  private RpcFeature rpcFeature;
  
  /**
   * init gadget
   */
  protected void init(SpreadsheetGadgetPreferences preferences) {
    preferences.aiturl();
	  
  	HTML html = new HTML("Demo GWT Gadget has loaded.");
  	
  	VerticalPanel pWidget = new VerticalPanel();
	  pWidget.add(html);
	  
  	RootPanel.get().add(pWidget);
  	
  	//analyFeature.createTracker(domainId);
  	
  }

  public void initializeFeature(GoogleAnalyticsFeature feature) {
    this.analyFeature = feature;
  }

  public void initializeFeature(AdsFeature feature) {
    this.adsFeature = feature;
  }

  public void initializeFeature(DynamicHeightFeature feature) {
    this.heightFeature = feature;
  }

  public void initializeFeature(OsapiFeature feature) {
    this.osapiFeature = feature;
  }
  
  public void initializeFeature(ViewFeature feature) {
    this.viewFeature = feature;
  }

  public void initializeFeature(SetTitleFeature feature) {
    this.titleFeature = feature;
  }

  public void initializeFeature(SetPrefsFeature feature) {
    this.setPreFeature = feature;
  }

  public void initializeFeature(RpcFeature feature) {
    this.rpcFeature = feature;
  }
	
}
