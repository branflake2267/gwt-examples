package org.gonevertical.core.client.account.ui;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.oauth.Sha1;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Login inputs
 * 
 * @author branflake2267
 *
 */
public class LoginUi extends Composite implements ChangeHandler {

	private ClientPersistence cp = null;

	// ui types
	public static final int LOGIN_HORIZONTAL = 1;
	public static final int LOGIN_VERTICAL = 2;

	// which ui type was choosen to display
	private int uiType = LOGIN_HORIZONTAL;

	private VerticalPanel pWidget = new VerticalPanel();

	// possible ui types (widget)
	private LoginUiInputs loginUi = null;

	/**
	 * constructor - init composite widget
	 */
	public LoginUi(ClientPersistence cp) {
		this.cp = cp;

		initWidget(pWidget);
	}

	/**
	 * what type of ui are we going to use?
	 * uiType = [LOGIN_HORIZONTAL | LOGIN_VERTICAL]
	 * 
	 * @param uiType
	 */
	protected void setUi(int uiType) {
		this.uiType = uiType;

		loginUi = new LoginUiInputs(cp, uiType);
		pWidget.add(loginUi);

		if (uiType == LOGIN_HORIZONTAL) {
			pWidget.setCellHorizontalAlignment(loginUi, HorizontalPanel.ALIGN_RIGHT);
			
		} else if (uiType == LOGIN_VERTICAL) {
			pWidget.setCellHorizontalAlignment(loginUi, HorizontalPanel.ALIGN_CENTER);
		}
	}

	/**
	 * get if the cookie should be set
	 * @return
	 */
	protected boolean getRememberMe() {
		boolean b = loginUi.getRememberMe();
		return b;
	}

	/**
	 * draw widget
	 */
	protected void draw() {
		loginUi.draw();
	}

	protected void drawError(String error) {
		if (error == null) {
			return;
		}
		loginUi.drawError(error);
	}

	protected String getConsumerKey() {
		String s = loginUi.getConsumerKey();
		return s;
	}

	protected String getConsumerSecret() {
		String s = loginUi.getConsumerSecret();

		// create digest of password, before sending it to the server
		Sha1 sha = new Sha1();
		String hash = sha.b64_sha1(s);

		return hash;
	}

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();

	}

}
