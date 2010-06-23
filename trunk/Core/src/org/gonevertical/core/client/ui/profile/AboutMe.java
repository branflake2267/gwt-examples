package org.gonevertical.core.client.ui.profile;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.ui.login.ChangePassword;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AboutMe extends Composite {

	private ClientPersistence cp;

	public AboutMe(ClientPersistence cp) {
		this.cp = cp;
	
		FlexTable flexTable = new FlexTable();
		flexTable.setSize("596px", "55px");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHeight("130px");
		vp.add(flexTable);
		
		initWidget(vp);
		
		HorizontalPanel hpButtonsTop = new HorizontalPanel();
		flexTable.setWidget(0, 0, hpButtonsTop);
		
		PushButton pshbtnSave = new PushButton("Save");
		hpButtonsTop.add(pshbtnSave);
		
		HTML htmlNickName = new HTML("Nick Name", true);
		htmlNickName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 0, htmlNickName);
		
		HTML htmlFirstName = new HTML("First Name", true);
		htmlFirstName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 1, htmlFirstName);
		
		HTML htmlLastName = new HTML("Last Name", true);
		htmlLastName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 2, htmlLastName);
		
		TextBox tbNameNick = new TextBox();
		tbNameNick.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		flexTable.setWidget(2, 0, tbNameNick);
		tbNameNick.setWidth("200");
		
		TextBox tbNameFirst = new TextBox();
		flexTable.setWidget(2, 1, tbNameFirst);
		tbNameFirst.setWidth("200");
		
		TextBox tbNameLast = new TextBox();
		flexTable.setWidget(2, 2, tbNameLast);
		tbNameLast.setWidth("200");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);
		
		HTML htmlUsername = new HTML("UserName", true);
		flexTable.setWidget(3, 0, htmlUsername);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		TextBox textBox = new TextBox();
		flexTable.setWidget(4, 0, textBox);
		textBox.setWidth("200");
		flexTable.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel hpButtons = new HorizontalPanel();
		flexTable.setWidget(4, 1, hpButtons);
		
		PushButton pshbtnChangePassword = new PushButton("Change Password");
		hpButtons.add(pshbtnChangePassword);
		pshbtnChangePassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				changePassword();
			}
		});
		pshbtnChangePassword.setHTML("Change My Password");
		
		HTML htmlnbsp_1 = new HTML("&nbsp;", true);
		vp.add(htmlnbsp_1);
		
		EmailsWidget emailsWidget = new EmailsWidget();
		vp.add(emailsWidget);
		
		HTML htmlnbsp = new HTML("&nbsp;", true);
		vp.add(htmlnbsp);
	}

	protected void changePassword() {
	  ChangePassword wcp = new ChangePassword(cp);
	  wcp.center();
  }

}
