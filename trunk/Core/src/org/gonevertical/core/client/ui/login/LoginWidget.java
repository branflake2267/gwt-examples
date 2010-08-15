package org.gonevertical.core.client.ui.login;

import org.gonevertical.core.client.ClientPersistence;

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

	public void drawForgotUsername() {
		wloginUi.drawForgotUsername();
	}
	
	public void drawForgotPassword() {
		wloginUi.drawForgotPassword();    
	}

}
