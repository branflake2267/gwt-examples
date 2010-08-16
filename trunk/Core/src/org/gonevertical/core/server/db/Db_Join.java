package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.join.JoinDataFilter;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.DataJoinJdo;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;
import org.gonevertical.core.server.jdo.data.build.BuildDataJoin;

public class Db_Join {
	
	private static final Logger log = Logger.getLogger(Db_Join.class.getName());
	 
	private ServerPersistence sp;
	private BuildDataJoin dataJoin;
	
	public Db_Join(ServerPersistence sp) {
		this.sp = sp;
		dataJoin = new BuildDataJoin(sp);
	}
	
	public boolean buildDataJoin(OAuthTokenData accessToken, JoinDataFilter filter) {
		
		// TODO add authorization
		
		if (filter == null) {
			return false;
		}
		
		boolean success = false;
		if (filter.getType() == JoinDataFilter.TYPE_THING_THINGSTUFF) {
			success = buildJoin1();
		}
		
		return success;
	}

	private boolean buildJoin1() {
		boolean success = false;
		
		success = dataJoin.buildDataJoin();
		
		return success;
  }
	
}
