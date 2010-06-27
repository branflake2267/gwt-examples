package org.gonevertical.core.client.ui.profile;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTML;

public class EmailWidget extends Composite {

	public EmailWidget() {
		
		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("287px", "33px");
		
		TextBox tbEmail = new TextBox();
		flexTable.setWidget(0, 0, tbEmail);
		flexTable.getCellFormatter().setWidth(0, 0, "240px");
		tbEmail.setWidth("100%");
		
		HTML htmlnbspnbsp = new HTML("&nbsp;", true);
		flexTable.setWidget(0, 1, htmlnbspnbsp);
		
		PushButton bDelete = new PushButton("X");
		flexTable.setWidget(0, 2, bDelete);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
	}

}
