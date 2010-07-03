package org.gonevertical.core.server.db;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.ui.profile.ProfileData;
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
	
	public ProfileData getProfileData(OAuthTokenData accessToken, long thingId) {
		
		// TODO - does user have permission to see this
		
		if (thingId <= 0) {
			thingId = dbU.getUser(accessToken).getThingId();
		}
		
		ThingDataFilter filter = new ThingDataFilter();
		filter.setThingId(thingId);
		ThingData td = dbT.getThing(accessToken, filter);
		
		ProfileData pd = new ProfileData();
		pd.setThingData(td);
		
		return pd;
	}
	
	 public ProfileData saveProfileData(OAuthTokenData accessToken, ThingDataFilter filter, ProfileData profileData) {
		 
		 return null;
	 }
	 
	 
}
