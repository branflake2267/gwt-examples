package org.gonevertical.core.client.ui.login;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.account.AccountData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChangeUsername extends DialogBox implements ClickHandler, KeyUpHandler {

	private ClientPersistence cp;

	private RpcCoreServiceAsync rpc = null;

	private VerticalPanel pWidget = new VerticalPanel();

	private VerticalPanel pNotification = new VerticalPanel();

	// when notification is needed for key error
	private FlowPanel pKeyError = new FlowPanel();

	// key container
	private VerticalPanel pKey = new VerticalPanel();

	// Key (username)
	private TextBox tbK1 = new TextBox();
	private TextBox tbK2 = new TextBox();

	// key character count
	private FlowPanel pKeyCount1 = new FlowPanel();
	private FlowPanel pKeyCount2 = new FlowPanel();

	private PushButton bClose = new PushButton("Cancel");
	private PushButton bSave = new PushButton("Save");

	// after all checks are processed, then we can create a user
	private boolean canChangeUsername = false;

	// minumum lengths for consumerKey and Secret
	private final int consumerKey_Len = 6;

	private ThingData thingData = null;

	private int changeEvent = 0;

	private String userName;

	public ChangeUsername(ClientPersistence cp) {
		this.cp = cp;

		setText("Change Username");

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bClose);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(bSave);
		hp.add(new HTML("&nbsp;"));
		hp.add(pNotification);

		pWidget.add(hp);

		setWidget(pWidget);

		bClose.addClickHandler(this);
		bSave.addClickHandler(this);

		rpc = RpcCore.initRpc();

		pWidget.setStyleName("CreateUserAccount");
		pNotification.setWidth("100%");
		pNotification.setStyleName("core-CreateUserAccount-Notification");

		pKeyError.addStyleName("core-CreateUserAccount-Error");

		tbK1.addKeyUpHandler(this);
		tbK2.addKeyUpHandler(this);

		draw();
	}

	public void setThingData(ThingData thingData) {
		this.thingData = thingData;
	}

	private void processKeyExist(UserData userData) {

		if (userData.getErrorInt() > 0) {
			canChangeUsername = false;
			drawNotification(userData.getNotification());
			return;
		}
		
		canChangeUsername = true;

		// create account if there is no duplication
		save();
	}

	public void draw(ThingData thingData) {
		this.thingData = thingData;
	}

	public void draw() {

		tbK1.setWidth("300px");
		tbK2.setWidth("300px");

		String un = cp.getInputLabel_ConsumerKey();
		Label lk1 = new Label(un);
		Label lk2 = new Label("Verify " + un);

		pKeyCount1.add(new HTML("0"));
		pKeyCount2.add(new HTML("0"));

		// key 1
		HorizontalPanel hpK1 = new HorizontalPanel();
		hpK1.setSpacing(4);
		hpK1.add(lk1);
		hpK1.add(tbK1);
		hpK1.add(pKeyCount1);

		// key 2
		HorizontalPanel hpK2 = new HorizontalPanel();
		hpK2.setSpacing(4);
		hpK2.add(lk2);
		hpK2.add(tbK2);
		hpK2.add(pKeyCount2);

		// key container
		pKey.add(pKeyError);
		pKey.add(hpK1);
		pKey.add(hpK2);

		pWidget.add(pKey);


		// fields
		lk1.setStyleName("core-CreateUserAccount-Field");
		lk2.setStyleName("core-CreateUserAccount-Field");

		hpK1.setCellVerticalAlignment(lk1, VerticalPanel.ALIGN_MIDDLE);
		hpK2.setCellVerticalAlignment(lk2, VerticalPanel.ALIGN_MIDDLE);
		hpK1.setCellVerticalAlignment(pKeyCount1, VerticalPanel.ALIGN_MIDDLE);
		hpK2.setCellVerticalAlignment(pKeyCount2, VerticalPanel.ALIGN_MIDDLE);

		pKeyCount1.setStyleName("core-CreateUserAccount-CharCountError");
		pKeyCount2.setStyleName("core-CreateUserAccount-CharCountError");

	}

	private void changeUsernameStart() {

		// verify the inputs have data, and has what is needed.
		checkForErrors();

		if (canChangeUsername == false) {
			System.out.println("createUserStart(): checkerrors failed");
			return;
		}

		// check to see if the user is in the database?
		doesConsumerExistAlready();
	}

	/**
	 * check if the user exist in the system already?
	 */
	private void doesConsumerExistAlready() {

		UserData userData = new UserData();
		userData.setAccessToken(cp.getAccessToken());
		userData.setConsumerKey(getKey());
		//userData.setConsumerSecret(wSecret.getPasswordHash()); // TODO disabled for this
		userData.sign();

		doesUserExistRpc(userData);
	}

	private void drawKeyNotify(boolean bol, int error) {
		pKeyError.clear();
		if (bol == true) {
			pKey.setStyleName("core-CreateUserAccount-ErrorInput");
			if (error > 0) {
				pKeyError.add(new HTML(UserData.getError(error)));
			}
		} else {
			pKey.removeStyleName("core-CreateUserAccount-ErrorInput");
		}
	}

	private void drawNotification(String s) {
		pNotification.clear();
		pNotification.setVisible(true);

		HTML html = new HTML(s);
		html.addStyleName("core-Notification");
		html.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		pNotification.add(html);

		Timer t = new Timer() {
			public void run() {
				pNotification.setVisible(false);
			}
		};
		t.schedule(5000);
	}

	private void checkForErrors() {

		System.out.println("check for errors"); 

		drawKeyNotify(false, 0);

		// are things blank?
		String k = tbK1.getText().trim();

		if (k.length() < consumerKey_Len) {
			drawKeyNotify(true, UserData.KEYS_SHORT);
			return;
		}
		if (k.length() < consumerKey_Len) {
			drawKeyNotify(true, UserData.KEYS_SHORT);

			return;
		}

		// Matching

		// do keys match
		boolean keysMatch = doesKeysMatch();

		int error = 0;

		// keys match?
		if (keysMatch == false) {
			error = UserData.KEYS_DONTMATCH;
			drawKeyNotify(true, error);
		} 

		// there is an error to deal with
		if (error > 0) {
			return;
		}

		// reset the errors
		drawKeyNotify(false, 0);

		// we can check to see if user exists now.
		canChangeUsername = true;
	}

	private boolean doesKeysMatch() {

		String u1 = tbK1.getText().trim();
		String u2 = tbK2.getText().trim();

		// TODO - no spaces in middle?

		boolean pass = false;
		if (u1.equals(u2)) {
			pass = true;
		}

		return pass;
	}

	private String getKey() {
		String key = tbK1.getText().trim();
		return key;
	}

	private void processChange(boolean b, AccountData profileData) {
		// TODO I don't really need profileData here, but maybe usefull for something else someday

		if (b == true) {
			drawNotify("Saved");
			fireChange(EventManager.USER_CHANGEDUSERNAME);
			this.userName = thingData.getKey();
		} else if (b == false) {
			drawNotify("I wasn't able to change your username, try again");
		}

		hide();
		removeFromParent();
	}

	public String getUserName() {
		return thingData.getKey();
	}

	private void drawNotify(String s) {
		pNotification.clear();
		pNotification.setVisible(true);

		HTML html = new HTML(s);
		html.addStyleName("core-Notification");
		html.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		pNotification.add(html);

		Timer t = new Timer() {
			public void run() {
				hide();
				removeFromParent();
			}
		};
		t.schedule(4000);

	}

	private void countCharacters(int input, TextBox tb) {

		int ilen = tb.getText().length();
		String len = Integer.toString(ilen);

		switch (input) {
		case 1: // key 1
			pKeyCount1.clear();
			pKeyCount1.add(new HTML(len));
			if (ilen > consumerKey_Len) {
				pKeyCount1.removeStyleName("core-CreateUserAccount-CharCountError");
				pKeyCount1.setStyleName("core-CreateUserAccount-CharCountPass");
			} else {
				pKeyCount1.setStyleName("core-CreateUserAccount-CharCountError");
				pKeyCount1.removeStyleName("core-CreateUserAccount-CharCountPass");
			}
			break;
		case 2: // key 2;
			pKeyCount2.clear();
			pKeyCount2.add(new HTML(len));
			if (ilen > consumerKey_Len) {
				pKeyCount2.removeStyleName("core-CreateUserAccount-CharCountError");
				pKeyCount2.setStyleName("core-CreateUserAccount-CharCountPass");
			} else {
				pKeyCount2.setStyleName("core-CreateUserAccount-CharCountError");
				pKeyCount2.removeStyleName("core-CreateUserAccount-CharCountPass");
			}
			break;
		}

	}

	private void save() {

		if (canChangeUsername == false) {
			System.out.println("ChangeUsername.save() Something went wrong, to get here.");
			return;
		}

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingId(thingData.getThingId());

		// set up the new username
		thingData.setKey(getKey());

		// skip saving any stuff - it will reload on return
		thingData.setThingStuffsData(null); 

		AccountData profileData = new AccountData();
		profileData.setThingData(thingData);

		saveTsdRpc(filter, profileData);
	}

	public void onKeyUp(KeyUpEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == tbK1) {
			countCharacters(1, tbK1);

		} else if (sender == tbK2) {
			countCharacters(2, tbK2);
		} 

	}

	public int getChangeEvent() {
		return changeEvent;
	}

	private void fireChange(int changeEvent) {
		this.changeEvent = changeEvent;
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == bClose) {
			hide();
			removeFromParent();
		} else if (sender == bSave) {
			changeUsernameStart();
		}

	}

	/**
	 * check to see if the user already exists in the database
	 * 
	 * @param userData
	 */
	private void doesUserExistRpc(UserData userData) {
		cp.showLoading(true);

		AsyncCallback<UserData> callback = new AsyncCallback<UserData>() {
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
				cp.showLoading(false);
			}
			public void onSuccess(UserData userData) {
				cp.showLoading(false);
				processKeyExist(userData);
			}
		};
		rpc.doesUserNameExist(userData, callback);
	}

	private void saveTsdRpc(ThingDataFilter filter, AccountData profileData) {

		cp.showLoading(true);

		rpc.saveAccountData(cp.getAccessToken(), filter, profileData, new AsyncCallback<AccountData>() {
			public void onSuccess(AccountData pd) {
				cp.showLoading(false);
				processChange(true, pd);
			}

			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});
	}





}
