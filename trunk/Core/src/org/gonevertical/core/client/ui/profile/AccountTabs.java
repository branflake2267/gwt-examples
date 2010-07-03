package org.gonevertical.core.client.ui.account;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.gonevertical.core.client.ui.login.CreateUserAccount;
import org.gonevertical.core.client.ui.login.LoginUi;
import org.gonevertical.core.client.ui.login.LoginWidget;

public class AccountTabs extends Composite {

	private ClientPersistence cp;

	private RpcCoreServiceAsync rpc;

	private TabPanel tabPanel;
	private AboutMe aboutMe;
	private LoginWidget loginWidget;
	private CreateUserAccount createUser;

	public AccountTabs(ClientPersistence cp) {
		this.cp = cp;

		VerticalPanel vp = new VerticalPanel();

		initWidget(vp);
		vp.setWidth("100%");

		vp.add(getTabPanel());
		vp.add(getLoginWidget_Vertical());
		vp.add(getCreateUser());

		// register bread crumbs that are used in this widget
		setCrumbs();

		rpc = RpcCore.initRpc();

		cp.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				ClientPersistence wcp = (ClientPersistence) event.getSource();
				if (wcp.getChangeEvent() == EventManager.USER_LOGGEDIN) {
					drawLoggedIn();
				} else if (wcp.getChangeEvent() == EventManager.USER_LOGGEDOUT) {
					drawLoggedOut();
				}
			}
		});

	}

	private void setCrumbs() {
		if (cp != null) {
			cp.setBreadCrumbCategory("Account", "account");
			cp.setBreadCrumb("Create", "core_account_create");
			cp.setBreadCrumb("About Me", "core_account_aboutme");
		}
	}

	private Widget getCreateUser() {
		if (createUser == null) {
			createUser = new CreateUserAccount(cp);
		}
		return createUser;
	}

	private void drawLoggedOut() {
		loginWidget.setVisible(true);
		tabPanel.setVisible(false);
	}

	private void drawLoggedIn() {
		if (History.getToken().matches("^core_acount.*") == false) { // make sure were focused on this widget first
			return;
		}
		
		draw();
	}

	public void draw() {

		if (History.getToken().contains("core_account_create") == true) {
			loginWidget.setVisible(false);
			tabPanel.setVisible(false);
			createUser.setVisible(true);
			return;

		} else if (History.getToken().contains("core_account_forgotusername") == true) {
			createUser.setVisible(false);
			aboutMe.setVisible(false);
			tabPanel.setVisible(false);
			loginWidget.setVisible(true);

			loginWidget.drawForgotUsername();
			return;

		} else if (History.getToken().contains("core_account_forgotpassword") == true) {
			createUser.setVisible(false);
			aboutMe.setVisible(false);
			tabPanel.setVisible(false);
			loginWidget.setVisible(true);

			loginWidget.drawForgotPassword();
			return;

		}

		createUser.setVisible(false);

		// need to be logged in to go on
		if (cp.getLoginStatus() == false) {
			aboutMe.setVisible(false);
			tabPanel.setVisible(false);
			loginWidget.setVisible(true);
			loginWidget.draw();
			return;
		}

		loginWidget.setVisible(false);
		tabPanel.setVisible(true);

		getAccountData();
	}

	private LoginWidget getLoginWidget_Vertical() {
		if (loginWidget == null) {
			loginWidget = new LoginWidget(cp, LoginUi.LOGIN_VERTICAL);
		}
		return loginWidget;
	}

	public boolean getMatchHistoryToken() {
		boolean b = false;
		String historyToken = History.getToken();
		if (historyToken.matches("^core_account.*?$") == true) {
			b = true;
		}
		return b;
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

	protected void process(AccountData accountData) {
		aboutMe.setAccountData(accountData);
	}

	private void getAccountData() {
		aboutMe.setVisible(false);

		cp.showLoading(true);

		rpc.getAccountData(cp.getAccessToken(), 0, new AsyncCallback<AccountData>() {
			public void onSuccess(AccountData accountData) {
				aboutMe.setVisible(true);
				process(accountData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});

	}




}
