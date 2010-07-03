package org.gonevertical.core.client.ui.account;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffs;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EmailsWidget extends Composite {

	private ClientPersistence cp;
	
	private PushButton bAdd;
	private VerticalPanel pEmails;
	private FlexTable flexTable;
	
	public EmailsWidget(ClientPersistence cp) {
		this.cp = cp;

		flexTable = new FlexTable();
		flexTable.setHeight("67px");
		initWidget(flexTable);

		HorizontalPanel hpTop = new HorizontalPanel();
		flexTable.setWidget(0, 0, hpTop);

		HTML htmlTitle = new HTML("Email", true);
		htmlTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hpTop.add(htmlTitle);
		htmlTitle.setWidth("249px");
		hpTop.setCellVerticalAlignment(htmlTitle, HasVerticalAlignment.ALIGN_MIDDLE);
		hpTop.setCellHorizontalAlignment(htmlTitle, HasHorizontalAlignment.ALIGN_CENTER);

		bAdd = new PushButton("Add");
		bAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addEmail(null);
			}
		});

		hpTop.add(bAdd);
		hpTop.setCellHorizontalAlignment(bAdd, HasHorizontalAlignment.ALIGN_CENTER);
		hpTop.setCellVerticalAlignment(bAdd, HasVerticalAlignment.ALIGN_MIDDLE);

		pEmails = new VerticalPanel();
		flexTable.setWidget(1, 0, pEmails);

		EmailWidget pEmailWidget = new EmailWidget(cp);
		pEmails.add(pEmailWidget);
		
		
	}

	protected void addEmail(ThingStuffData tsd) {
		EmailWidget emailWidget = new EmailWidget(cp);
		emailWidget.setData(tsd);
		pEmails.add(emailWidget);
	}

	public void drawEmails(ThingStuffData[] tsd) {
		pEmails.clear();

		if (tsd == null) {
			return;
		}

		for (int i=0; i < tsd.length; i++) {
			addEmail(tsd[i]);
		}

	}
	
	public ThingStuffData[] getThingStuffData() {
		
		int c = pEmails.getWidgetCount();
		
		if (c == 0) {
			return null;
		}
		
		ThingStuffData[] tsds = new ThingStuffData[c];
		for (int i=0; i < c; i++) {
			EmailWidget ew = (EmailWidget) pEmails.getWidget(i);
			tsds[i]	= ew.getThingStuffData();
		}
	
		return tsds;
	}
	

}
