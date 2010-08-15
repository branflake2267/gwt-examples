package org.gonevertical.core.client.ui.admin.thingstuff;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.ui.admin.thing.Things;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchForThingLink extends DialogBox {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget;
	private HorizontalPanel horizontalPanel;
	private PushButton bClose;
	
	private Things wThings;
	
	private long selectedThingId;

	private int changeEvent;
	
	public SearchForThingLink(ClientPersistence cp) {
		this.cp = cp;
		
		setHTML("Search For Thing Link");
		setWidget(getPWidget());
		
	}

	public void draw() {
		wThings.setSelection(true);
		wThings.draw();
	}
	
	private VerticalPanel getPWidget() {
		if (pWidget == null) {
			wThings = new Things(cp);
			pWidget = new VerticalPanel();
			pWidget.setSize("100%", "100%");
			pWidget.add(getHorizontalPanel());
			pWidget.add(wThings);
			
			wThings.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					Things t = (Things) event.getSource();
					int e = t.getChangeEvent();
					if (e == EventManager.THING_SELECT) {
					setSelectedThingId(t.getSelectedThingId());
					}
				}
			});
		}
		return pWidget;
	}
	
	private void setSelectedThingId(long selectedThingId) {
		this.selectedThingId = selectedThingId;
		hide();
		fireChange(EventManager.THING_SELECT);
  }
	
	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getClose());
		}
		return horizontalPanel;
	}
	
	private PushButton getClose() {
		if (bClose == null) {
			bClose = new PushButton("New button");
			bClose.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			bClose.setHTML("Cancel");
		}
		return bClose;
	}
	
	public long getSelectedThingId() {
		return selectedThingId;
  }
	
  public int getChangeEvent() {
    return changeEvent;
  }

	private void fireChange(int changeEvent) {
		this.changeEvent  = changeEvent;
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}


}
