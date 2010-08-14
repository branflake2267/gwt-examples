package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingLinkers extends Composite implements OpenHandler<DisclosurePanel> {

	private ClientPersistence cp;
	
	private DisclosurePanel pWidget;

	private VerticalPanel pThings = new VerticalPanel();

	private long thingId;

	public ThingLinkers(ClientPersistence cp) {
		this.cp = cp;
		initWidget(getDisclosurePanel());
		
		pWidget.addOpenHandler(this);
	}

	public void setThingData(ThingData thingData) {
		if (thingData != null) {
			thingId = thingData.getThingId();
		}
	}
	
	public void draw() {
		pThings.clear();
		
		if (thingId == 0) {
			return;
		}
		
		Things wThings = new Things(cp);
		
		pThings.add(wThings);
		
		wThings.setThingLinkers(thingId);
		wThings.draw();
	}
	
	private DisclosurePanel getDisclosurePanel() {
		
		if (pWidget == null) {
			pWidget = new DisclosurePanel("Linkers");
			pWidget.add(pThings);
		}
		
		return pWidget;
	}

  public void onOpen(OpenEvent<DisclosurePanel> event) {
  	draw();
  }

	public void close() {
	  pWidget.setOpen(false);
	  pThings.clear();
  }
	
}
