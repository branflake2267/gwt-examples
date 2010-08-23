package org.gonevertical.core.server.db;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.server.ServerPersistence;

public class Db_Access {
	
	private ServerPersistence sp;

	public Db_Access(ServerPersistence sp) {
		this.sp = sp;
	}
	
	public boolean getAccess(OAuthTokenData accessToken) {
		// TODO - need some kind of identifier to delienate the widget requesting
		return true;
	}
	
}
