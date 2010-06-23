package org.gonevertical.core.client.ui.profile;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class ProfileTabs extends Composite {

	private ClientPersistence cp;
	private TabPanel tabPanel;
	private AboutMe aboutMe;

	public ProfileTabs(ClientPersistence cp) {
		this.cp = cp;
		initWidget(getTabPanel());
		
		 // register bread crumbs that are used in this widget
    setCrumbs();
	}
	
	private void setCrumbs() {
		if (cp != null) {
			cp.setBreadCrumbCategory("Profile", "profile");
    	cp.setBreadCrumb("AboutMe", "core_profile_aboutme");
		}
	}
	
	public boolean getMatchHistoryToken() {
	  boolean b = false;
	  String historyToken = History.getToken();
	  if (historyToken.matches("^core_profile.*?$") == true) {
	  	b = true;
	  }
	  return b;
  }
	
	public void draw() {

  }
	
	private TabPanel getTabPanel() {
		if (tabPanel == null) {
			tabPanel = new TabPanel();
			tabPanel.add(getAboutMe(), "About Me", false);
			tabPanel.selectTab(0);
			
			tabPanel.setWidth("100%");
		}
		return tabPanel;
	}
	private AboutMe getAboutMe() {
		if (aboutMe == null) {
			aboutMe = new AboutMe(cp);
			aboutMe.setSize("5cm", "3cm");
		}
		return aboutMe;
	}

}
