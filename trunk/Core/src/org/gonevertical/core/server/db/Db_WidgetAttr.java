package org.gonevertical.core.server.db;

import java.util.HashMap;
import java.util.logging.Logger;

import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.account.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.widget.WidgetAttrData;
import org.gonevertical.core.client.widget.WidgetAttrDataFilter;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.SessionAccessTokenJdo;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffAboutJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_WidgetAttr {
	
	private static final Logger log = Logger.getLogger(Db_WidgetAttr.class.getName());

	private ServerPersistence sp = null;

  public Db_WidgetAttr(ServerPersistence sp) {
    this.sp  = sp;
  }
  
  public WidgetAttrData getWidgetAttributes(OAuthTokenData accessToken, WidgetAttrDataFilter widgetAttrDataFilter) {
		
  	long thingId_Widget = widgetAttrDataFilter.getThingId_Widget();
  	
  	// is the user logged in? 
  	// TODO should I get the items anyway, so I can bring back parameters
  	boolean canView = false;
  	boolean canEdit = false;
  	boolean canAdd = false;
  	if (accessToken == null) {
  	
    	// process access token, was it signed, did it 
    	long thingId_Person = new SessionAccessTokenJdo(sp).getThingIdFromSession(accessToken);
    	
    	// is the widget open, otherwise its closed by default
    	boolean isOpen = getOpenPermission(thingId_Widget);
    	
    	// get if the user can View
    	canView = getCanView(isOpen, thingId_Person, thingId_Widget);
    	
    	// get if the user can Add
    	canAdd = getCanAdd(isOpen, thingId_Person, thingId_Widget);
    	
    	// get if the user can Edit
    	canEdit = getCanEdit(isOpen, thingId_Person, thingId_Widget);
    	
  	}
  	
  	// get widget parameters
  	HashMap<String,String> params = null;
  	
  	WidgetAttrData wad = new WidgetAttrData();
  	wad.setData(canView, canAdd, canEdit);
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

	private boolean getCanView(boolean isOpen, long thingId_Person, long thingId_Widget) {
	  
		long thingStuffTypeId = SetDefaultsData.THINGSTUFFTYPE_LINK;
		
		// find if the widget contains open
  	ThingStuffDataFilter filter = new ThingStuffDataFilter();
  	filter.setThingId(thingId_Person); // filter by person
  	filter.setThingStuffTypeId(thingStuffTypeId); // filter by the linking
  	filter.setValueLong(thingId_Widget);
  	
		ThingStuffAboutJdo tsj = new ThingStuffAboutJdo(sp);
		ThingStuffData[] tsds = tsj.query(filter);
		
		boolean b = false;
		if (tsds == null || tsds.length == 0) {
			b = tsds[0].getValueBol();
		}
		
	  return b;
  }

	private boolean getCanEdit(boolean isOpen, long thingId_Person, long thingId_Widget) {

		if (isOpen == true) { // find if user can't edit
	  	
	  } else {
	  	
	  }
		
	  return false;
  }
	
	private boolean getCanAdd(boolean isOpen, long thingId_Person, long thingId_Widget) {
	  
		if (isOpen == true) { // find if users can't add
	  	
	  } else {
	  	
	  }
		
	  return false;
  }
	
}
