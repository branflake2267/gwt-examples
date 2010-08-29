package org.gonevertical.core.server;

import java.util.Date;
import java.util.logging.Logger;

import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.server.db.Db_Thing;
import org.gonevertical.core.server.db.Db_ThingStuff;
import org.gonevertical.core.server.db.Db_ThingStuffType;
import org.gonevertical.core.server.db.Db_ThingType;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

/**
 * TODO - link of the defaults static constants
 * 
 * @author branflake2267
 *
 */
public class SetDefaults {

	private static final Logger log = Logger.getLogger(SetDefaults.class.getName());

	private ServerPersistence sp = null;
	
	private Db_ThingType dbTy;
	private Db_Thing dbT;
	private Db_ThingStuffType dbTst;
	private Db_ThingStuff dbTs;

	public SetDefaults(ServerPersistence sp) {
		this.sp = sp;
		dbTy = new Db_ThingType(sp);
		dbT = new Db_Thing(sp);
		dbTst = new Db_ThingStuffType(sp);
		dbTs = new Db_ThingStuff(sp);
	}

	public boolean setDefaults(int defaultType) {

		if (defaultType == SetDefaultsData.DEFAULT_ALL) { // setup all defaults

			setThingTypes();
			createStuffTypes();
			createThings();
			createThingsStuff();
		} 

		return true;
	}

	private void createStuffTypes() {

		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Name", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_LINK, "Thing Link", ThingStuffTypeData.VALUETYPE_LINK);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_ADMIN, "Is Site Admin", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANVIEW, "Can View", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANEDIT, "Can Edit", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANADD, "Can Add", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANVIEW, "Can't View", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANTEDIT, "Can't Edit", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANTADD, "Can't Add", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_ALIAS, "Alias", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_NICKNAME, "Nick Name", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_FIRSTNAME, "First Name", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_MIDDLENAME, "Middle Name", ThingStuffTypeData.VALUETYPE_STRING);		
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_LASTNAME, "Last Name", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "Email", ThingStuffTypeData.VALUETYPE_EMAIL);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_MOBILE, "Mobile", ThingStuffTypeData.VALUETYPE_PHONE);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_PHONE, "Phone", ThingStuffTypeData.VALUETYPE_PHONE);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_DESC, "Description", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_ACCEPTTERMS, "Accept Terms", ThingStuffTypeData.VALUETYPE_BOOLEAN);
    
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_BIBLEBOOKNAME, "Bible Book Name", ThingStuffTypeData.VALUETYPE_STRING);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_BIBLEBOOKCHAPTERNUM, "Bible Book Num", ThingStuffTypeData.VALUETYPE_INT);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_BIBLEBOOKLINK, "Bible Book Link", ThingStuffTypeData.VALUETYPE_LINK);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_BIBLEBOOKCHAPTERNUM, "Bible Book Chapter Num", ThingStuffTypeData.VALUETYPE_INT);
		dbTst.createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_BIBLEBOOKVERSENUM, "Bible Book Verse Num", ThingStuffTypeData.VALUETYPE_INT);
		dbTst.createStuffType(ThingStuffTypeData.THIGNSTUFFTYPE_BIBLEBOOKVERSECONTENT, "Bible Book Verse Content", ThingStuffTypeData.VALUETYPE_STRING);
	}

	private void setThingTypes() {

		dbTy.createThingType(ThingTypeData.TYPE_APPLICATION, "Application"); 
		dbTy.createThingType(ThingTypeData.TYPE_USER, "Person");
		dbTy.createThingType(ThingTypeData.TYPE_GROUP, "Group");
		dbTy.createThingType(ThingTypeData.TYPE_WIDGET, "Widget");
		dbTy.createThingType(ThingTypeData.TYPE_PERMISSION, "Permission");
		dbTy.createThingType(ThingTypeData.TYPE_STUFFTYPETEMPLATE, "Thing Stuff Template");
		dbTy.createThingType(ThingTypeData.TYPE_TASK, "Task");
		dbTy.createThingType(ThingTypeData.TYPE_REMINDER, "Reminder");
		dbTy.createThingType(ThingTypeData.TYPE_LOCATION, "Location");
		dbTy.createThingType(ThingTypeData.TYPE_DEVICE, "Device");
		dbTy.createThingType(ThingTypeData.TYPE_DATA, "Task Data");
		dbTy.createThingType(ThingTypeData.TYPE_BIBLES, "Bibles");
		dbTy.createThingType(ThingTypeData.TYPE_BIBLEBOOKS, "Bible Books");

	}

	private void createThings() {

		dbT.createThing(ThingData.THING_APPLICATION, ThingTypeData.TYPE_APPLICATION, "demo_application", "password");
		dbT.createThing(ThingData.THING_USER_ADMIN, ThingTypeData.TYPE_USER, "administrator", "password");
		dbT.createThing(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, "demo_user", "password");

		dbT.createThing(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, null, null); // open
		dbT.createThing(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, null, null); // closed

		dbT.createThing(ThingData.THING_WIDGET_CORETHINGTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingTypes
		dbT.createThing(ThingData.THING_WIDGET_CORETHINGSTUFFTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingStuffTypes
		dbT.createThing(ThingData.THING_WIDGET_CORETHINGS, ThingTypeData.TYPE_WIDGET, null, null); // Things
		dbT.createThing(ThingData.THING_WIDGET_COREEDITTHING, ThingTypeData.TYPE_WIDGET, null, null); // EditThing
		dbT.createThing(ThingData.THING_WIDGET_CORE_ACCOUNT_ABOUTME, ThingTypeData.TYPE_WIDGET, null, null); // EditThing

	}

	private void createThingsStuff() {

		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Open By Default"); // open name
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Closed By Default"); // closed name

		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGTYPES, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Core Thing Types"); // ThingTypes name
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGSTUFFTYPES, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Core Thing Stuff Types"); // ThingStuffTypes name
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGS, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Core Things Name"); // Things name
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_COREEDITTHING, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Core Edit Thing"); // EditThings name

		// admin is admin
		dbTs.createThingStuff_Unique(ThingData.THING_USER_ADMIN, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_ADMIN, true);

		// open by default
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANVIEW, false); // open can't view
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANTEDIT, false); // open can't edit
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANTADD, false); // open can't add

		// closed
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANVIEW, false); // closed can view
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANEDIT, false); // closed can edit
		dbTs.createThingStuff_Unique(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, ThingStuffTypeData.THINGSTUFFTYPE_CANADD, false); // closed can add

		// widgets are closed - restricted
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGTYPES, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 5); // 6 ThingTypes closed
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGSTUFFTYPES, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 5); // 7 ThingStypes closed
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_CORETHINGS, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 5); // 8 things closed
		dbTs.createThingStuff_Unique(ThingData.THING_WIDGET_COREEDITTHING, ThingTypeData.TYPE_WIDGET, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 5); // 9 edit things closed

		// demo_user link, 6,7,8,9 
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 6); // links the widgets into place
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 7);
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 8);
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_LINK, 9);
		
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_ALIAS, "Deem");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_FIRSTNAME, "Firsty");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_LASTNAME, "Lastious");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "test@gonevertical.org");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "deem@gonevertical.com");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "4253739212@tmobile.net");

		// create multi-demo about the link
		// link 3,2,6 - 4(canview)=true
		//createStuffChildren(ThingData.THING_USER_DEMO, 6, 4, new Boolean(true));
		//createStuffChildren(ThingData.THING_USER_DEMO, 7, 4, new Boolean(true));
		//createStuffChildren(ThingData.THING_USER_DEMO, 8, 4, new Boolean(true));
		//createStuffChildren(ThingData.THING_USER_DEMO, 9, 4, new Boolean(true));
	}

	// This needs to be fixed
	public void createStuffChildren(
			long parentThingId,
			long parentThingTypeId,
			long stuffTypeId, 
			Boolean valueBol) {
		
		String value = null;
		//Boolean valueBol = null;
		Double valueDouble = null;
		Long valueLong = null;
		Date valueDate = null;
		Date startOf = null;
		Date endOf = null;
		Date dateCreated = new Date();
		Date dateUpdated = null;

		// get the parent
		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setParentThingId(parentThingId);
		filter.setStuffTypeId(stuffTypeId);
		filter.setValueLong(parentThingId);
		
		ThingStuffJdo tsj = new ThingStuffJdo(sp);
		ThingStuffData[] r = tsj.query(filter);

		long parentStuffId = r[0].getStuffId();
		
		Double rank = Double.parseDouble("0");
		long[] ownerThingIds = null;
		
		long createdBy = 0;
		long updatedBy = 0;
		
		ThingStuffData tsd = new ThingStuffData();
		tsd.setData(
				parentThingId,
				parentStuffId,
				
				parentStuffId, 
				stuffTypeId, 
				
				value, 
				valueBol, 
				valueDouble, 
				valueLong, 
				valueDate, 
				
				startOf, 
				endOf, 
				rank, 
				
				createdBy, 
				dateCreated, 
				updatedBy, 
				dateUpdated, 
				ownerThingIds);
		
		ThingStuffJdo tsaj = new ThingStuffJdo(sp);
		tsaj.saveUnique(tsd);
	}

	



}
