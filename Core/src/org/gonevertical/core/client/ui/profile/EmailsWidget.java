package org.gonevertical.core.client.ui.profile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EmailsWidget extends Composite {

	private PushButton bAdd;
	private VerticalPanel pEmails;

	public EmailsWidget() {
		
		FlexTable flexTable = new FlexTable();
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
				addEmail();
			}
		});
		
		hpTop.add(bAdd);
		hpTop.setCellHorizontalAlignment(bAdd, HasHorizontalAlignment.ALIGN_CENTER);
		hpTop.setCellVerticalAlignment(bAdd, HasVerticalAlignment.ALIGN_MIDDLE);
		
		pEmails = new VerticalPanel();
		flexTable.setWidget(1, 0, pEmails);
		
		EmailWidget pEmailWidget = new EmailWidget();
		pEmails.add(pEmailWidget);
	}

	protected void addEmail() {
		EmailWidget pEmailWidget = new EmailWidget();
		pEmails.add(pEmailWidget);
  }

}
