package com.gawkat.core.server.jdo;

import java.util.Date;

import com.gawkat.core.client.SetDefaultsData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingStuffJdo;
import com.gawkat.core.server.jdo.data.ThingStuffTypeJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class SetDefaults {
	
  private ServerPersistence sp = null;
 
  public SetDefaults(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public boolean setDefaults(int defaultType) {
    
  	if (defaultType == SetDefaultsData.DEFAULT_ALL) { // setup all defaults
      setThingTypes();
      createStuffTypes();
      createThing1();
      createUsers();
    
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_TYPES) { // thing types
      setThingTypes();
    
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_APPLICATIONS) { // thing
      createThing1();
    
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_USERS) { // thing
      createUsers();
      
    } else if (defaultType == SetDefaultsData.DEFAULT_THINGS) { // things
    	 createThing1();
    	 createUsers();
    	 
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_STUFF_TYPES) {
    	createStuffTypes();
    }
    
    return true;
  }
  
  private void createStuffTypes() {
  	
  	// insert blanks to set key increment
  	//ThingStuffTypeJdo aa = new ThingStuffTypeJdo(sp);
  	//aa.setData(new ThingStuffTypeData());
  	//aa.insert();
  	//aa.insert();
  	
  	// can be administrator
  	ThingStuffTypeData a = new ThingStuffTypeData();
  	a.setData(0, "Is site Admin", ThingStuffTypeData.VT_BOOLEAN, new Date(), null, null, null);
  	
  	ThingStuffTypeJdo jst1 = new ThingStuffTypeJdo(sp);
  	jst1.setKey(1);
  	jst1.setData(a);
  	jst1.insertUnique();
  	
  	// Thing Link
  	ThingStuffTypeData b = new ThingStuffTypeData();
  	b.setData(0, "Thing Link", ThingStuffTypeData.VT_LINK, new Date(), null, null, null);
  	
  	ThingStuffTypeJdo jst2 = new ThingStuffTypeJdo(sp);
  	jst2.setKey(2);
  	jst2.setData(b);
  	jst2.insertUnique();
  	
  	// requried
  	// can login to site
  	/*
  	ThingStuffTypeData a2 = new ThingStuffTypeData();
  	a2.setData(0, "Can login to site", ThingStuffTypeData.VT_BOOLEAN);
  	
  	ThingStuffTypeJdo ja0 = new ThingStuffTypeJdo();
  	ja2.setKey(1);
  	ja2.setData(a0);
  	ja2.insertUnique();
  	*/
  	

  	
/*  	
  	ThingStuffTypeData a = new ThingStuffTypeData();
  	a.setData(0, "Text", ThingStuffTypeData.VT_STRING);
  	
  	ThingStuffTypeData b = new ThingStuffTypeData();
  	b.setData(0, "CheckBox", ThingStuffTypeData.VT_BOOLEAN);
  	
  	ThingStuffTypeData c = new ThingStuffTypeData();
  	c.setData(0, "Decimal", ThingStuffTypeData.VT_DOUBLE);
  	
  	ThingStuffTypeData d = new ThingStuffTypeData();
  	d.setData(0, "Number", ThingStuffTypeData.VT_INT);

  	ThingStuffTypeData e = new ThingStuffTypeData();
  	e.setData(0, "Large Text Box", ThingStuffTypeData.VT_STRING_LARGE);
  	
  	ThingStuffTypeData f = new ThingStuffTypeData();
  	f.setData(0, "Text Case Sensitive", ThingStuffTypeData.VT_STRING_CASE);
  	
  	ThingStuffTypeData g = new ThingStuffTypeData();
  	g.setData(0, "Large Text Area Case Sensitive", ThingStuffTypeData.VT_STRING_LARGE_CASE);
  	
  	ThingStuffTypeData i = new ThingStuffTypeData();
  	i.setData(0, "HTML", ThingStuffTypeData.VT_HTML);
  	
  	ThingStuffTypeData j = new ThingStuffTypeData();
  	j.setData(0, "URL", ThingStuffTypeData.VT_URL);
  	
  	ThingStuffTypeData k = new ThingStuffTypeData();
  	k.setData(0, "Email", ThingStuffTypeData.VT_EMAIL);
  	
  	ThingStuffTypeData l = new ThingStuffTypeData();
  	l.setData(0, "Phone", ThingStuffTypeData.VT_PHONE);
  	
  	ThingStuffTypeData m = new ThingStuffTypeData();
  	m.setData(0, "Money", ThingStuffTypeData.VT_STRING);
  	
  	
  	
  	ThingStuffTypeData o = new ThingStuffTypeData();
  	o.setData(0, "File", ThingStuffTypeData.VT_FILE);
  	
  	ThingStuffTypeJdo j1 = new ThingStuffTypeJdo();
  	j1.setData(a);
  	j1.insertUnique();
  	
  	ThingStuffTypeJdo j2 = new ThingStuffTypeJdo();
  	j2.setData(b);
  	j2.insertUnique();
  	
  	ThingStuffTypeJdo j3 = new ThingStuffTypeJdo();
  	j3.setData(c);
  	j3.insertUnique();
  	
  	ThingStuffTypeJdo j4 = new ThingStuffTypeJdo();
  	j4.setData(d);
  	j4.insertUnique();
  	
  	ThingStuffTypeJdo j5 = new ThingStuffTypeJdo();
  	j5.setData(e);
  	j5.insertUnique();
  	
  	ThingStuffTypeJdo j6 = new ThingStuffTypeJdo();
  	j6.setData(f);
  	j6.insertUnique();
  	
  	ThingStuffTypeJdo j7 = new ThingStuffTypeJdo();
  	j7.setData(g);
  	j7.insertUnique();
  	
  	ThingStuffTypeJdo j8 = new ThingStuffTypeJdo();
  	j8.setData(i);
  	j8.insertUnique();
  	
  	ThingStuffTypeJdo j9 = new ThingStuffTypeJdo();
  	j9.setData(j);
  	j9.insertUnique();
  	
  	ThingStuffTypeJdo j10 = new ThingStuffTypeJdo();
  	j10.setData(k);
  	j10.insertUnique();
  	
  	ThingStuffTypeJdo j11 = new ThingStuffTypeJdo();
  	j11.setData(l);
  	j11.insertUnique();
  	
  	ThingStuffTypeJdo j12 = new ThingStuffTypeJdo();
  	j12.setData(m);
  	j12.insertUnique();
  	

  	
  	ThingStuffTypeJdo j14 = new ThingStuffTypeJdo();
  	j14.setData(o);
  	j14.insertUnique();
  	*/
  	
  }

	/**
   * set default things
   */
  private void setThingTypes() {
  	
  	// insert blanks to set key incremen
  	/*
  	ThingTypeJdo aa = new ThingTypeJdo(sp);
    aa.insert();
    ThingTypeJdo bb = new ThingTypeJdo(sp);
    bb.insert();
    ThingTypeJdo cc = new ThingTypeJdo(sp);
    cc.insert();
    ThingTypeJdo dd = new ThingTypeJdo(sp);
    dd.insert();
    ThingTypeJdo ee = new ThingTypeJdo(sp);
    ee.insert();
    ThingTypeJdo ff = new ThingTypeJdo(sp);
    ff.insert();
    */

    
    ServerPersistence sp = new ServerPersistence();
    
    ThingTypeData at = new ThingTypeData();
    at.setKey(1);
    at.setName("Application");
    
    ThingTypeJdo a = new ThingTypeJdo(sp);
    a.setData(at);
    a.insertUnique();
    
    
    ThingTypeData bt = new ThingTypeData();
    bt.setKey(2);
    bt.setName("Person");
    
    ThingTypeJdo b = new ThingTypeJdo(sp);
    b.setData(bt);
    b.insertUnique();
    
    
    ThingTypeData ct = new ThingTypeData();
    ct.setKey(3);
    ct.setName("Group");
    
    ThingTypeJdo c = new ThingTypeJdo(sp);
    c.setData(ct);
    c.insertUnique();
    
    
    ThingTypeData dt = new ThingTypeData();
    dt.setKey(4);
    dt.setName("Widget");
    
    ThingTypeJdo d = new ThingTypeJdo(sp);
    d.setData(dt);
    d.insertUnique();
    
    
    ThingTypeData et = new ThingTypeData();
    et.setKey(5);
    et.setName("Permission");
    
    ThingTypeJdo e = new ThingTypeJdo(sp);
    e.setData(et);
    e.insertUnique();
    
    
    ThingTypeData ft = new ThingTypeData();
    ft.setKey(6);
    ft.setName("Thing Stuff Template");

    ThingTypeJdo f = new ThingTypeJdo(sp);
    f.setData(ft);
    f.insertUnique();
    
  }
  
  private void createUsers() {
  	
  	// admin
  	createThing2();
  	
  	// demo user
  	createThing3();
  	
  }
  
  /**
   * set default application
   */
  private void createThing1() {
  	
  	// insert blanks to set key increment 
  	ThingJdo aa = new ThingJdo(sp);
    aa.insert();
    aa.insert();
    //aa.insert();
    
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_APPLICATION;
    String key = "demo_application";
    String secret = sha.hex_hmac_sha1("salt", "password");
    
    ThingJdo a = new ThingJdo(sp);
    a.setThingId(SetDefaultsData.THING_APPLICATION);
    a.insertUnique(thingTypeId, key, secret);
    
  }
  
  /**
   * create administrator
   */
  private void createThing2() {
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_USER;
    String key = "Administrator";
    String password = "password";
    String secret = sha.b64_sha1(password);
    //String secret = sha.hex_hmac_sha1("salt", "password"); // would need to do this on the client side too.
    
    ThingJdo a = new ThingJdo(sp);
    a.setThingId(SetDefaultsData.THING_ADMINISTRATOR);
    a.insertUnique(thingTypeId, key, secret);
    
    // set admin true
    ThingStuffData ts2 = new ThingStuffData();
    ts2.setThingId(SetDefaultsData.THING_ADMINISTRATOR);
    ts2.setThingStuffTypeId(SetDefaultsData.THINGSTUFFTYPE_ADMIN);
    ts2.setValue(true);
    
    ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
    tsj2.save(ts2);
    
    // TODO add default stuff types

  }
  
  /**
   * create demo user
   */
  private void createThing3() {
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_USER;
    String key = "demo_user";
    String password = "password";
    String secret = sha.b64_sha1(password);
    //String secret = sha.hex_hmac_sha1("salt", "password"); // would need to do this on the client side too.
    
    ThingJdo a = new ThingJdo(sp);
    a.setThingId(SetDefaultsData.THING_DEMOUSER);
    a.insertUnique(thingTypeId, key, secret);
    
    // TODO add default stuff types
  }
  
  
}
