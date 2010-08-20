package org.gonevertical.core.server.jdo.data.build;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.NotPersistent;

import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.DataJoinJdo;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

import com.google.appengine.api.datastore.Key;

public class BuildDataJoin {

	private static final Logger log = Logger.getLogger(DataJoinJdo.class.getName());

	private ServerPersistence sp;
	private ThingJdo tj;
	private ThingStuffJdo tsj;
	private DataJoinJdo dataJoin;

	private Date buildDate;

	/**
	 * constructor - use the one with sp
	 * @throws NullPointerException
	 */
	public BuildDataJoin() throws NullPointerException {
		// don't use this one
	}
	
	/**
	 * constructor 
	 * 
	 * @param sp
	 */
	public BuildDataJoin(ServerPersistence sp) {
		this.sp = sp;
		tj = new ThingJdo(sp);
		tsj = new ThingStuffJdo(sp);
		buildDate = new Date();
	}

	public boolean buildDataJoin() {
		
		boolean b = false;
		
		// build my flat table - virtual join
		b = loopJoin();
		
		// purge records
		b = new DataJoinJdo(sp).deleteRecordsBefore(buildDate);
		
		return b;
	}
	
	@SuppressWarnings("unchecked")
  private boolean loopJoin() {
		setBuildDate();
		
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select stuffIdKey from " + ThingStuffJdo.class.getName());
	    List<Key> ids = (List<Key>) q.execute();
	    Iterator<Key> itr = ids.iterator();
	    while(itr.hasNext()) {
	    	Key stuffIdkey = itr.next();
	    	long stuffId = stuffIdkey.getId();
	    	processJoinBuild(stuffId);
	    }
	    q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "loopJoin(): ", e);

		} finally {
			pm.close();
		}
		
		return false;
	}
	
	private void processJoinBuild(long stuffId) {
		
		if (dataJoin == null) {
			dataJoin = new DataJoinJdo(sp);
		}
		
		dataJoin.setBuildDate(buildDate);
		dataJoin.save(stuffId);
  }
	
	private void setBuildDate() {
		buildDate = new Date();
	}
}
