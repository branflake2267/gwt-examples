package org.gonevertical.core.server;

import java.util.Date;
import java.util.logging.Logger;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.client.oauth.Sha1;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
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

	public SetDefaults(ServerPersistence sp) {
		this.sp = sp;
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

		createStuffType(SetDefaultsData.THINGSTUFFTYPE_NAME, "Name", ThingStuffTypeData.VT_STRING);
		createStuffType(SetDefaultsData.THINGSTUFFTYPE_LINK, "Thing Link", ThingStuffTypeData.VT_LINK);
		createStuffType(SetDefaultsData.THINGSTUFFTYPE_ADMIN, "Is Site Admin", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(4, "Can View", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(5, "Can Edit", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(6, "Can Add", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(7, "Can't View", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(8, "Can't Edit", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(9, "Can't Add", ThingStuffTypeData.VT_BOOLEAN);
		createStuffType(10, "Alias", ThingStuffTypeData.VT_STRING);
		createStuffType(11, "Nick Name", ThingStuffTypeData.VT_STRING);
		createStuffType(12, "First Name", ThingStuffTypeData.VT_STRING);
		createStuffType(13, "Middle Name", ThingStuffTypeData.VT_STRING);		
		createStuffType(14, "Last Name", ThingStuffTypeData.VT_STRING);
		createStuffType(15, "Email", ThingStuffTypeData.VT_EMAIL);
		createStuffType(16, "Mobile", ThingStuffTypeData.VT_PHONE);
		createStuffType(17, "Phone", ThingStuffTypeData.VT_PHONE);
		createStuffType(18, "Description", ThingStuffTypeData.VT_STRING);

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

		createThing(SetDefaultsData.THING_APPLICATION, ThingTypeData.TYPE_APPLICATION, "demo_application", "password");
		createThing(SetDefaultsData.THING_ADMINISTRATOR, ThingTypeData.TYPE_USER, "administrator", "password");
		createThing(SetDefaultsData.THING_DEMOUSER, ThingTypeData.TYPE_USER, "demo_user", "password");

		createThing(SetDefaultsData.THING_PERMISSION_OPEN, ThingTypeData.TYPE_PERMISSION, null, null); // open
		createThing(SetDefaultsData.THING_PERMISSION_CLOSED, ThingTypeData.TYPE_PERMISSION, null, null); // closed

		createThing(SetDefaultsData.THING_WIDGET_THINGTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingTypes
		createThing(SetDefaultsData.THING_WIDGET_THINGSTUFFTYPES, ThingTypeData.TYPE_WIDGET, null, null); // ThingStuffTypes
		createThing(SetDefaultsData.THING_WIDGET_THINGS, ThingTypeData.TYPE_WIDGET, null, null); // Things
		createThing(SetDefaultsData.THING_WIDGET_EDITTHING, ThingTypeData.TYPE_WIDGET, null, null); // EditThing

	}

	private void createThingsStuff() {

		createThingStuff(4, 1, "Open By Default"); // open name
		createThingStuff(5, 1, "Closed By Default"); // closed name

		createThingStuff(6, 1, "Core Thing Types"); // ThingTypes name
		createThingStuff(7, 1, "Core Thing Stuff Types"); // ThingStuffTypes name
		createThingStuff(8, 1, "Core Things Name"); // Things name
		createThingStuff(9, 1, "Core Edit Thing"); // EditThings name

		// admin is admin
		createThingStuff(2, 3, true);

		// open by default
		createThingStuff(4, 7, false); // open can't view
		createThingStuff(4, 8, false); // open can't edit
		createThingStuff(4, 9, false); // open can't add

		// closed
		createThingStuff(5, 4, false); // closed can view
		createThingStuff(5, 5, false); // closed can edit
		createThingStuff(5, 6, false); // closed can add

		// widgets are closed - restricted
		createThingStuff(6, 2, 5); // 6 ThingTypes closed
		createThingStuff(7, 2, 5); // 7 ThingStypes closed
		createThingStuff(8, 2, 5); // 8 things closed
		createThingStuff(9, 2, 5); // 9 edit things closed

		// demo_user link, 6,7,8,9 
		createThingStuff(3, 2, 6); // links the widgets into place
		createThingStuff(3, 2, 7);
		createThingStuff(3, 2, 8);
		createThingStuff(3, 2, 9);

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

	public void createThingStuff(int thingId, int thingStuffTypeId, long value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff(int thingId, int thingStuffTypeId, String value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff(int thingId, int thingStuffTypeId, boolean value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThing(int id, int thingTypeId, String key, String password) {
		Sha1 sha = new Sha1();

		String secret = null;
		if (password != null) {
			secret = sha.hex_hmac_sha1(ClientPersistence.PASSWORD_SALT, password);
		}

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
