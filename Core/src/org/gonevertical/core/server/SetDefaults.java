package org.gonevertical.core.server;

import java.util.Date;
import java.util.logging.Logger;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.oauth.Sha1;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.server.db.Db_ThingStuff;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffAboutJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffTypeJdo;
import org.gonevertical.core.server.jdo.data.ThingTypeJdo;

/**
 * TODO - link of the defaults static constants
 * 
 * @author branflake2267
 *
 */
public class SetDefaults {

	private static final Logger log = Logger.getLogger(SetDefaults.class.getName());

	private ServerPersistence sp = null;
	private Db_ThingStuff dbTs;

	public SetDefaults(ServerPersistence sp) {
		this.sp = sp;
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

		createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_NAME, "Name", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_LINK, "Thing Link", ThingStuffTypeData.VALUETYPE_LINK);
		createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_ADMIN, "Is Site Admin", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_CANVIEW, "Can View", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(5, "Can Edit", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(6, "Can Add", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(7, "Can't View", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(8, "Can't Edit", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(9, "Can't Add", ThingStuffTypeData.VALUETYPE_BOOLEAN);
		createStuffType(ThingStuffTypeData.THINGSTUFFTYPE_ALIAS, "Alias", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(11, "Nick Name", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(12, "First Name", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(13, "Middle Name", ThingStuffTypeData.VALUETYPE_STRING);		
		createStuffType(14, "Last Name", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(15, "Email", ThingStuffTypeData.VALUETYPE_EMAIL);
		createStuffType(16, "Mobile", ThingStuffTypeData.VALUETYPE_PHONE);
		createStuffType(17, "Phone", ThingStuffTypeData.VALUETYPE_PHONE);
		createStuffType(18, "Description", ThingStuffTypeData.VALUETYPE_STRING);
		createStuffType(19, "Accept Terms", ThingStuffTypeData.VALUETYPE_BOOLEAN);

	}

	private void setThingTypes() {

		createThingType(ThingTypeData.TYPE_APPLICATION, "Application"); 
		createThingType(ThingTypeData.TYPE_USER, "Person");
		createThingType(ThingTypeData.TYPE_GROUP, "Group");
		createThingType(ThingTypeData.TYPE_WIDGET, "Widget");
		createThingType(ThingTypeData.TYPE_PERMISSION, "Permission");
		createThingType(ThingTypeData.TYPE_STUFFTYPETEMPLATE, "Thing Stuff Template");
		createThingType(ThingTypeData.TYPE_TASK, "Task");
		createThingType(ThingTypeData.TYPE_REMINDER, "Reminder");
		createThingType(ThingTypeData.TYPE_LOCATION, "Location");
		createThingType(ThingTypeData.TYPE_DEVICE, "Device");

	}

	private void createThings() {

		createThing(ThingData.THING_APPLICATION_DEMO, ThingTypeData.TYPE_APPLICATION, "demo_application", "password");
		createThing(ThingData.THING_USER_ADMIN, ThingTypeData.TYPE_USER, "administrator", "password");
		createThing(ThingData.THING_USER_DEMO, ThingTypeData.TYPE_USER, "demo_user", "password");

		createThing(ThingData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, null, null); // open
		createThing(ThingData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, null, null); // closed

		createThing(ThingData.THING_WIDGET_CORETHINGTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingTypes
		createThing(ThingData.THING_WIDGET_CORETHINGSTUFFTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingStuffTypes
		createThing(ThingData.THING_WIDGET_CORETHINGS, ThingTypeData.TYPE_WIDGET, null, null); // Things
		createThing(ThingData.THING_WIDGET_COREEDITTHING, ThingTypeData.TYPE_WIDGET, null, null); // EditThing

	}

	private void createThingsStuff() {

		dbTs.createThingStuff_Unique(4, 1, "Open By Default"); // open name
		dbTs.createThingStuff_Unique(5, 1, "Closed By Default"); // closed name

		dbTs.createThingStuff_Unique(6, 1, "Core Thing Types"); // ThingTypes name
		dbTs.createThingStuff_Unique(7, 1, "Core Thing Stuff Types"); // ThingStuffTypes name
		dbTs.createThingStuff_Unique(8, 1, "Core Things Name"); // Things name
		dbTs.createThingStuff_Unique(9, 1, "Core Edit Thing"); // EditThings name

		// admin is admin
		dbTs.createThingStuff_Unique(2, 3, true);

		// open by default
		dbTs.createThingStuff_Unique(4, 7, false); // open can't view
		dbTs.createThingStuff_Unique(4, 8, false); // open can't edit
		dbTs.createThingStuff_Unique(4, 9, false); // open can't add

		// closed
		dbTs.createThingStuff_Unique(5, 4, false); // closed can view
		dbTs.createThingStuff_Unique(5, 5, false); // closed can edit
		dbTs.createThingStuff_Unique(5, 6, false); // closed can add

		// widgets are closed - restricted
		dbTs.createThingStuff_Unique(6, 2, 5); // 6 ThingTypes closed
		dbTs.createThingStuff_Unique(7, 2, 5); // 7 ThingStypes closed
		dbTs.createThingStuff_Unique(8, 2, 5); // 8 things closed
		dbTs.createThingStuff_Unique(9, 2, 5); // 9 edit things closed

		// demo_user link, 6,7,8,9 
		dbTs.createThingStuff_Unique(3, 2, 6); // links the widgets into place
		dbTs.createThingStuff_Unique(3, 2, 7);
		dbTs.createThingStuff_Unique(3, 2, 8);
		dbTs.createThingStuff_Unique(3, 2, 9);
		
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_ALIAS, "Deem");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_FIRSTNAME, "Firsty");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_LASTNAME, "Lastious");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "test@gonevertical.org");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "deem@gonevertical.com");
		dbTs.createThingStuff_Unique(ThingData.THING_USER_DEMO, ThingStuffTypeData.THINGSTUFFTYPE_EMAIL, "4253739212@tmobile.net");

		// create multi-demo about the link
		// link 3,2,6 - 4(canview)=true
		createThingStuffMulti(3, 2, 6, 4, new Boolean(true));
		createThingStuffMulti(3, 2, 7, 4, new Boolean(true));
		createThingStuffMulti(3, 2, 8, 4, new Boolean(true));
		createThingStuffMulti(3, 2, 9, 4, new Boolean(true));
	}

	public void createThingStuffMulti(
			long thingId, long parentThingTypeId, long parentLinkThingId, long thingStuffTypeId, Boolean valueBol) {
		
		String value = null;
		//Boolean valueBol = null;
		Double valueDouble = null;
		Long valueLong = null;
		Date valueDate = null;
		Date startOf = null;
		Date endOf = null;
		Date dateCreated = new Date();
		Date dateUpdated = null;

		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setThingId(thingId);
		filter.setThingStuffTypeId(parentThingTypeId);
		filter.setValueLong(parentLinkThingId);
		
		ThingStuffJdo tsj = new ThingStuffJdo(sp);
		ThingStuffData[] r = tsj.query(filter);

		long stuffId = r[0].getStuffId();
		
		ThingStuffData tsd = new ThingStuffData();
		tsd.setStuffId(stuffId);
		tsd.setData(thingId, stuffId, 0, thingStuffTypeId, value, 
				valueBol, valueDouble, valueLong, valueDate, startOf, endOf, dateCreated, dateUpdated);
		
		ThingStuffAboutJdo tsaj = new ThingStuffAboutJdo(sp);
		tsaj.saveUnique(tsd);
	}

	

	public void createThing(int id, int thingTypeId, String key, String password) {
		Sha1 sha = new Sha1();

		String secret = null;
		if (password != null) {
			secret = sha.hex_hmac_sha1(ClientPersistence.PASSWORD_SALT, password);
		}
		
		//debug
		System.out.println("SetDefaults.createThing(): key=" + key + " password=" + password);

		ThingJdo a = new ThingJdo(sp);
		a.setThingId(id);
		a.insertUnique(thingTypeId, key, secret);
	}

	/**
	 * create stuff type
	 * 
	 * @param id
	 * @param name
	 * @param valueTypeId
	 */
	public void createStuffType(int id, String name, int valueTypeId) {
		ThingStuffTypeData tstd = new ThingStuffTypeData();
		tstd.setData(id, name, valueTypeId, new Date(), null, null, null);

		ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
		tstj.setData(tstd);
		tstj.insertUnique();
	}

	/**
	 * create thing type
	 * 
	 * @param id
	 * @param name
	 */
	public void createThingType(int id, String name) {
		ThingTypeData at = new ThingTypeData();
		at.setKey(id);
		at.setName(name);

		ThingTypeJdo a = new ThingTypeJdo(sp);
		a.setData(at);
		a.insertUnique();
	}

}
