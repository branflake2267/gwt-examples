package org.gonevertical.core.server.db;

import java.util.HashMap;
import java.util.logging.Logger;

import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.widget.WidgetAttrData;
import org.gonevertical.core.client.widget.WidgetAttrDataFilter;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingStuffAboutJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_WidgetAttr {
	
	private static final Logger log = Logger.getLogger(Db_WidgetAttr.class.getName());

	private ServerPersistence sp = null;

	private ThingStuffJdo tsj;

	private ThingStuffAboutJdo tsJa;

  public Db_WidgetAttr(ServerPersistence sp) {
    this.sp  = sp;
    tsj = new ThingStuffJdo(sp);
    tsJa = new ThingStuffAboutJdo(sp);
  }
  
  public WidgetAttrData getWidgetAttributes(OAuthTokenData accessToken, WidgetAttrDataFilter widgetAttrDataFilter) {
		
  	long thingId_Widget = widgetAttrDataFilter.getThingId_Widget();
  	
  	// is the user logged in? 
  	// TODO should I get the items anyway, so I can bring back parameters
  	boolean canView = false;
  	boolean canEdit = false;
  	boolean canAdd = false;
  	boolean canAdmin = false;
  	if (accessToken != null) {
  	
    	// process access token, was it signed, did it 
  		// TODO - work out the token nonce too
    	ThingData userThingData = new Db_User(sp).getUser(accessToken);
    	long thingId_Person = 0;
    	if (userThingData != null) {
    		thingId_Person = userThingData.getThingId();
    	}
    	
    	// is the widget open, otherwise its closed by default
    	boolean isOpen = getOpenPermission(thingId_Widget);
    	
    	// get if the user can View
    	canView = getCanView(isOpen, thingId_Person, thingId_Widget);
    	
    	// get if the user can Add
    	canAdd = getCanAdd(isOpen, thingId_Person, thingId_Widget);
    	
    	// get if the user can Edit
    	canEdit = getCanEdit(isOpen, thingId_Person, thingId_Widget);
    	
    	// is administrator
    	canAdmin = getCanAdmin();
  	}
  	
  	// get widget parameters
  	HashMap<String,String> params = null;
  	
  	WidgetAttrData wad = new WidgetAttrData();
  	wad.setData(canView, canAdd, canEdit);
  	wad.setCanAdmin(canAdmin);
  	wad.setParameters(params);
  	return wad;	
  }
  
  /**
   * get if the widget is open by default
   * 
   * @param thingId_Widget
   * @return
   */
  private boolean getOpenPermission(long thingId_Widget) {
  	
  	// open by default is ThingId=4, closed by default is ThingId=5
  	long open = SetDefaultsData.THING_PERMISSION_OPEN;
  	long thingStuffTypeId = SetDefaultsData.THINGSTUFFTYPE_LINK;
  	
  	// find if the widget contains open
  	ThingStuffDataFilter filter = new ThingStuffDataFilter();
  	filter.setThingId(thingId_Widget);
  	filter.setThingStuffTypeId(thingStuffTypeId);
  	filter.setValueLong(open);
  	
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
  	ThingStuffData[] tsd = tsj.query(filter);
  	
  	boolean isOpen = false;
  	if (tsd == null || tsd.length == 0) {
  		isOpen = false;
  	} else {
  		isOpen = true;
  	}
	  
	  return isOpen;
  }
  
  
  
  
  // TODO ***********************
  private boolean getCanAdmin() {
  	// TODO - get this
  	return false;
  }
  
  
  
  

	private boolean getCanView(boolean isOpen, long thingId_Person, long thingId_Widget) {
	  
		int stuffTypeId_open = SetDefaultsData.THINGSTUFFTYPE_CANTVIEW;
		int stuffTypeId_closed = SetDefaultsData.THINGSTUFFTYPE_CANVIEW;
		boolean can = getCan(isOpen, thingId_Person, thingId_Widget, stuffTypeId_open, stuffTypeId_closed);
		
	  return can;
  }

	private boolean getCanEdit(boolean isOpen, long thingId_Person, long thingId_Widget) {

		int stuffTypeId_open = SetDefaultsData.THINGSTUFFTYPE_CANTEDIT;
		int stuffTypeId_closed = SetDefaultsData.THINGSTUFFTYPE_CANEDIT;
		boolean can = getCan(isOpen, thingId_Person, thingId_Widget, stuffTypeId_open, stuffTypeId_closed);
		
	  return can;
  }
	
	private boolean getCanAdd(boolean isOpen, long thingId_Person, long thingId_Widget) {
	  
		int stuffTypeId_open = SetDefaultsData.THINGSTUFFTYPE_CANTADD;
		int stuffTypeId_closed = SetDefaultsData.THINGSTUFFTYPE_CANADD;
		boolean can = getCan(isOpen, thingId_Person, thingId_Widget, stuffTypeId_open, stuffTypeId_closed);
		
	  return can;
  }
	
	/**
	 * 
	 *  TODO - take into account recursion of group permissions
	 *  TODO - build unit testing for all types
	 *  TODO - determine what happens when both can and can't are there?
	 * 
	 * 
	 * @param isOpen
	 * @param thingId_Person
	 * @param thingId_Widget
	 * @param stuffTypeId_open
	 * @param stuffTypeId_closed
	 * @return
	 */
	private boolean getCan(boolean isOpen, long thingId_Person, long thingId_Widget, int stuffTypeId_open, int stuffTypeId_closed) {
	  
		boolean canView = false;
		
		if (isOpen == false) {
  		long thingStuffTypeId = SetDefaultsData.THINGSTUFFTYPE_LINK;
  		
  		// find if the widget contains open
    	ThingStuffDataFilter filter = new ThingStuffDataFilter();
    	filter.setThingId(thingId_Person); // filter by person
    	filter.setThingStuffTypeId(thingStuffTypeId); // filter by the linking
    	filter.setValueLong(thingId_Widget);
    	
  		ThingStuffData[] tsds = tsj.query(filter);
  		
  		if (tsds != null && tsds.length > 0) {
  			long thingStuffId = tsds[0].getStuffId();
  			
  			ThingStuffDataFilter filter2 = new ThingStuffDataFilter();
  			filter2.setThingStuffId(thingStuffId); // parent owner
  	  	filter2.setThingId(thingId_Person);
  	  	filter2.setThingStuffTypeId(stuffTypeId_closed);
  	  	ThingStuffData[] tsdsa = tsJa.query(filter2);
  	  	if (tsdsa != null && tsdsa.length > 0) {
  	  		Boolean bb = tsdsa[0].getValueBol();
  	  		if (bb != null) {
  	  			canView = bb.booleanValue();
  	  		}
  	  	}
  		}
  		
		} else {
			
			long thingStuffTypeId = SetDefaultsData.THINGSTUFFTYPE_LINK;
  		
  		// find if the widget contains open
    	ThingStuffDataFilter filter = new ThingStuffDataFilter();
    	filter.setThingId(thingId_Person); // filter by person
    	filter.setThingStuffTypeId(thingStuffTypeId); // filter by the linking
    	filter.setValueLong(thingId_Widget);
    	
  		ThingStuffData[] tsds = tsj.query(filter);
  		
  		if (tsds != null && tsds.length > 0) {
  			long thingStuffId = tsds[0].getStuffId();
  			
  			ThingStuffDataFilter filter2 = new ThingStuffDataFilter();
  			filter2.setThingStuffId(thingStuffId); // parent owner
  	  	filter2.setThingId(thingId_Person);
  	  	filter2.setThingStuffTypeId(stuffTypeId_open);
  	  	ThingStuffData[] tsdsa = tsJa.query(filter2);
  	  	if (tsdsa != null && tsdsa.length > 0) {
  	  		Boolean bb = tsdsa[0].getValueBol();
  	  		if (bb != null) {
  	  			canView = bb.booleanValue();
  	  		}
  	  	}
  		}
		}
		
	  return canView;
  }

	
}
