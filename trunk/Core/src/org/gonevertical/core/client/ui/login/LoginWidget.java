package org.gonevertical.core.client.ui.login;

import java.util.Date;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * resources on a remote server
 * 
 * @author branflake2267
 * 
 */
public class LoginWidget extends LoginWidget_Abs  {

	public LoginWidget(ClientPersistence cp, int uiType) {
		super(cp);

		setUi(uiType);

		initWidget(wloginUi);

	}
	
	public void draw() {
		wloginUi.draw();
	}


}
