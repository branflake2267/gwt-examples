package org.gonevertical.core.client.ui.account;

import java.util.ArrayList;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.login.ChangePassword;
import org.gonevertical.core.client.ui.login.ChangeUsername;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.user.client.ui.Widget;

public class AboutMe extends Composite implements ClickHandler {

	private ClientPersistence cp;
	private TextBox tbAlias;
	private TextBox tbNameFirst;
	private TextBox tbNameLast;
	private HTML htmlUserName;
	private EmailsWidget emailsWidget;
	private ThingStuffData tsd_alias;
	private ThingStuffData tsd_Namefirst;
	private ThingStuffData tsd_Namelast;
	private PushButton bSave;
	private RpcCoreServiceAsync rpc;
	private ThingData thingData;
	private HTML pNotify;
	private PushButton bChangePassword;
	private PushButton bChangeUsername;


	public AboutMe(ClientPersistence cp) {
		this.cp = cp;
	
		FlexTable flexTable = new FlexTable();
		flexTable.setSize("596px", "55px");
		
		VerticalPanel vp = new VerticalPanel();
		vp.setSize("100%", "130px");
		vp.add(flexTable);
		
		initWidget(vp);
		
		HorizontalPanel hpButtonsTop = new HorizontalPanel();
		flexTable.setWidget(0, 0, hpButtonsTop);
		
		bSave = new PushButton("Save");
		bSave.addClickHandler(this);
		hpButtonsTop.add(bSave);
		
		pNotify = new HTML("&nbsp;", false);
		pNotify.addStyleName("core-account-notify");
		hpButtonsTop.add(pNotify);
		
		HTML htmlAlias = new HTML("Alias", true);
		htmlAlias.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 0, htmlAlias);
		
		HTML htmlFirstName = new HTML("First Name", true);
		htmlFirstName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 1, htmlFirstName);
		
		HTML htmlLastName = new HTML("Last Name", true);
		htmlLastName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 2, htmlLastName);
		
		tbAlias = new TextBox();
		tbAlias.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		flexTable.setWidget(2, 0, tbAlias);
		tbAlias.setWidth("200");
		
		tbNameFirst = new TextBox();
		flexTable.setWidget(2, 1, tbNameFirst);
		tbNameFirst.setWidth("200");
		
		tbNameLast = new TextBox();
		flexTable.setWidget(2, 2, tbNameLast);
		tbNameLast.setWidth("200");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);
		
		HTML htmlUsername = new HTML("UserName", true);
		flexTable.setWidget(3, 0, htmlUsername);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		htmlUserName = new HTML("", true);
		flexTable.setWidget(4, 0, htmlUserName);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		flexTable.setWidget(4, 1, horizontalPanel);
		
		bChangeUsername = new PushButton();
		bChangeUsername.setHTML("Change Username");
		bChangeUsername.addClickHandler(this);
		horizontalPanel.add(bChangeUsername);
		
		HorizontalPanel hpButtons = new HorizontalPanel();
		flexTable.setWidget(4, 2, hpButtons);
		
		bChangePassword = new PushButton("Change Password");
		hpButtons.add(bChangePassword);
		bChangePassword.addClickHandler(this);
		bChangePassword.setHTML("Change Password");
		
		flexTable.getCellFormatter().setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(4, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		HTML htmlnbsp_1 = new HTML("&nbsp;", true);
		vp.add(htmlnbsp_1);
		
		emailsWidget = new EmailsWidget(cp);
		vp.add(emailsWidget);
		
		HTML htmlnbsp = new HTML("&nbsp;", true);
		vp.add(htmlnbsp);
		
		rpc = RpcCore.initRpc();
	}

	private void changePassword() {
	  ChangePassword wcp = new ChangePassword(cp);
	  wcp.center();
  }
	
	private void changeUsername() {
		ChangeUsername w = new ChangeUsername(cp);
		w.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				ChangeUsername ww = (ChangeUsername) event.getSource();
				int e = ww.getChangeEvent();
				if (e == EventManager.USER_CHANGEDUSERNAME) {
					String un = ww.getUserName();
					htmlUserName.setText(un);
				}
			}
		});
		w.setThingData(thingData);
		w.center();
	}

	public void setAccountData(AccountData accountData) {
		this.thingData = accountData.getThingData();
		
		drawNames(accountData);
		
		drawEmails(accountData);
		
  }
	
	private void drawNames(AccountData accountData) {

		if (accountData == null) {
			return;
		}
		
		tsd_alias = accountData.getAlias();
		tbAlias.setText(tsd_alias.getValue_ForTextBox());
		
		tsd_Namefirst = accountData.getFirstName();
		tbNameFirst.setText(tsd_Namefirst.getValue_ForTextBox());
		
		tsd_Namelast = accountData.getLastName();
		tbNameLast.setText(tsd_Namelast.getValue_ForTextBox());
		
		String userName = accountData.getThingData().getKey();
		htmlUserName.setText(userName);
		
  }

	private void drawEmails(AccountData accountData) {
		
		ThingStuffData[] tsd_Emails = accountData.getEmails();
	  if (tsd_Emails == null) {
	  	return;
	  }
	  
	  emailsWidget.drawEmails(tsd_Emails);
  }

	public HTML getHtmlUserName() {
		return htmlUserName;
	}
	
	public void save() {
		
		ThingStuffData[] thingStuffData = getThingStuffData();
		
		ThingStuffsData thingStuffsData = new ThingStuffsData();
		thingStuffsData.setThingStuffData(thingStuffData);

		thingData.setThingStuffsData(thingStuffsData);

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingId(thingData.getThingId());
		
		AccountData accountData = new AccountData();
		accountData.setThingData(thingData);
		
		saveTsdRpc(filter, accountData);
	}
	
	private void drawNotify(String s) {
		
		Timer t = new Timer() {
			public void run() {
				pNotify.setHTML("&nbsp;&nbsp;");
			}
		};
		t.schedule(4000);
		
		pNotify.setHTML("&nbsp;&nbsp;&nbsp;" + s);
	}
	
	public void processSaved(AccountData accountData) {
		
		drawNotify("Saved");
		
		setAccountData(accountData);
		
	}
	
	private ThingStuffData[] getThingStuffData() {
		
		ThingStuffData[] tsds_emails = emailsWidget.getThingStuffData();
		int emailSize = 0;
		if (tsds_emails != null) {
			emailSize = tsds_emails.length;
		}
		
		ArrayList<ThingStuffData> atsds = new ArrayList<ThingStuffData>();
		atsds.add(getTsd_Alias());
		atsds.add(getTsd_NameFirst());
		atsds.add(getTsd_NameLast());
		for (int i=0; i < emailSize; i++) {
			atsds.add(tsds_emails[i]);
		}
		
		ThingStuffData[] tsds = new ThingStuffData[atsds.size()];
		atsds.toArray(tsds);
		
		return tsds;
	}

	private ThingStuffData getTsd_NameLast() {
		String value = tbNameLast.getText().trim();
		tsd_Namelast.setValue(value);
	  return tsd_Namelast;
  }

	private ThingStuffData getTsd_NameFirst() {
		String value = tbNameLast.getText().trim();
		tsd_Namefirst.setValue(value);
	  return tsd_Namefirst;
  }

	private ThingStuffData getTsd_Alias() {
	  String value = tbAlias.getText().trim();
	  tsd_alias.setValue(value);
	  return tsd_alias;
  }

  public void onClick(ClickEvent event) {
  	Widget sender = (Widget) event.getSource();
	  
  	if (sender == bSave) {
  		save();
  		
  	} else if (sender == bChangePassword) {
  		changePassword();
  		
  	} else if (sender == bChangeUsername) {
  		changeUsername();
  	}
  	
  }
  
  private void saveTsdRpc(ThingDataFilter filter, AccountData accountData) {
  	
  	cp.showLoading(true);
  	
		rpc.saveAccountData(cp.getAccessToken(), filter, accountData, new AsyncCallback<AccountData>() {
			public void onSuccess(AccountData accountData) {
				cp.showLoading(false);
				processSaved(accountData);
			}
			
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});
  }
	
}
