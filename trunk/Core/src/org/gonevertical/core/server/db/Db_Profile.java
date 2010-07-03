package org.gonevertical.core.server.db;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.account.AccountData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.server.ServerPersistence;

public class Db_Profile {

	private ServerPersistence sp;
	private Db_Thing dbT;
	private Db_User dbU;

	public Db_Profile(ServerPersistence sp) {
		this.sp = sp;
		dbT = new Db_Thing(sp);
		dbU = new Db_User(sp);
	}

	public AccountData getProfileData(OAuthTokenData accessToken, long thingId) {

		// TODO - does user have permission to see this

		if (thingId <= 0) {
			thingId = dbU.getUser(accessToken).getThingId();
		}

		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingId(thingId);
		ThingData td = dbT.getThing(accessToken, filter);

		AccountData pd = new AccountData();
		pd.setThingData(td);

		return pd;
	}

	public AccountData saveProfileData(OAuthTokenData accessToken, ThingDataFilter filter, AccountData profileData) {

		// TODO - does user have permission to do this?
		// TODO - make sure no permission stuff types come in? Only stuff types can come in here??

		ThingData td = dbT.saveThing(accessToken, filter, profileData.getThingData());

		AccountData pd = new AccountData();
		pd.setThingData(td);

		return pd;
	}


}
