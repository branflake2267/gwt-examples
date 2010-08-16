package org.gonevertical.core.client.ui.admin;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.admin.join.JoinDataFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdminHome extends Composite implements ClickHandler {

	private ClientPersistence cp;
	private RpcCoreServiceAsync rpc = null;

	private VerticalPanel pWidget = new VerticalPanel();
	private VerticalPanel pMenu = new VerticalPanel();

	private PushButton bDefault = new PushButton("Add Account Defaults");
	private PushButton bBuildJoin = new PushButton("reBuild Thing_ThinStuff Join");
	private PushButton bLogin = new PushButton("Demo SignIn");

	public AdminHome(ClientPersistence cp) {
		this.cp = cp;

		pWidget.add(pMenu);

		initWidget(pWidget);

		bDefault.addClickHandler(this);
		bBuildJoin.addClickHandler(this);
		bLogin.addClickHandler(this);

		rpc = RpcCore.initRpc();

	}

	public void draw() {
		drawMenu();
	}

	public void drawMenu() {
		pMenu.clear();

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bDefault);
		hp.add(new HTML("&nbsp; Set up defaults"));
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.add(bBuildJoin);
		hp2.add(new HTML("Rebuild the Thing=ThingStuff Join"));

		pMenu.add(hp);
		pMenu.add(hp2);
		pMenu.add(new HTML("&nbsp;"));
		pMenu.add(new HTML("<a href=\"http://gwt-examples.googlecode.com\">gwt-examples.googlecode.com</a> Find my code here for this."));
	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == bDefault) {
			setDefaults();

		} else if (sender == bLogin) {
			cp.fireChange(EventManager.LOGIN_DEMO);
			
		} else if (sender == bBuildJoin) {
			setBuildJoin();
		}

	}

	private void setDefaults() {
		cp.showLoading(true);
		rpc.setDefaults(cp.getAccessToken(), SetDefaultsData.DEFAULT_ALL, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				Window.alert("Created Defaults");
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) { 
				cp.setRpcFailure(caught);
			}
		});
	}
	
	private void setBuildJoin() {
		JoinDataFilter filter = new JoinDataFilter();
		filter.setType(JoinDataFilter.TYPE_THING_THINGSTUFF);
		cp.showLoading(true);
		rpc.buildDataJoin(cp.getAccessToken(), filter, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean success) {
				Window.alert("Finished building Join. Success=" + success);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
			}
		});
		
	}

}
