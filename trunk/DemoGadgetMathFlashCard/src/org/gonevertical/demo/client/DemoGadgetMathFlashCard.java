package org.gonevertical.demo.client;

import com.gawkat.flashcard.client.Navigation;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Math Flash Cards", //
    directory_title = "Math Flash Cards", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVerical.org", //
    description = "Practice your times tables", //
    thumbnail = "/images/FlashCard_sm.png", //
    height = 210, //
    screenshot = "/images/FlashCard_lg.png")
public class DemoGadgetMathFlashCard extends Gadget<FlashCardPreferences> {

	/**
	 * init gadget
	 */
  protected void init(FlashCardPreferences preferences) {
	 
  	 VerticalPanel vp = new VerticalPanel();
     vp.setWidth("100%");
     RootPanel.get().add(vp);
     
     Navigation nav = new Navigation();
     vp.add(nav);
     nav.start();
     //vp.setHeight("210px"); // TODO take this away???
     
     vp.setCellHorizontalAlignment(nav, HorizontalPanel.ALIGN_CENTER);
	  
  }

	/* used to test the project include of flash card
  public void onModuleLoad() {
	  
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    RootPanel.get("content").add(vp);
    
    Navigation nav = new Navigation();
    vp.add(nav);
    nav.start();
    
    vp.setCellHorizontalAlignment(nav, HorizontalPanel.ALIGN_CENTER);
  	
  }
  */
	
}
