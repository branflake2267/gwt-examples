package org.gonevertical.core.client.account.ui.profile;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class ProfileTabs extends Composite {

	private ClientPersistence cp;
	
	private TabPanel wTabs = new TabPanel();
	
	public ProfileTabs(ClientPersistence cp) {
		this.cp = cp;
		
		// About Me
		
		
		initWidget(wTabs);
	}
}
