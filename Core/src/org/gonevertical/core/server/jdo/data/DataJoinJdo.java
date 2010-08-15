package org.gonevertical.core.server.jdo.data;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;

/**
 * supplemental join system, until one is born from gae
 *    Until then, this will be redundant data storage, but I have to build it somehow. This seems to work in theory sor far.
 *    Joins are basically flat tables of both left and right tables, so this will be specific and excatly what I need for now.
 *    I hope the data storage doesn't go out of control, guess I don't know until I try
 * 
 * This Join represents ThingJdo and Thing StuffJdo, and maybe thingStuffAboutJdo
 * 
 * @author branflake2267
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class DataJoinJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(DataJoinJdo.class.getName());
	
	@NotPersistent
	private ServerPersistence sp;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	
	

	@Persistent
	private long thingId; 

	//what type of thing is this, user, group, object?
	@Persistent
	private long thingTypeId;

	// username is represented here
	@Persistent
	private String thingKey;

	// password sha1 hash is represented here
	@Persistent
	private String thingSecret;

	// when did this start in time
	@Persistent
	private Date thingStartOf;

	// when did this end in time
	@Persistent
	private Date thingEndOf;
	
	// order the list by this
	@Persistent
	private Double thingRank;

	// when this object was created
	@Persistent
	private Date thingDateCreated;

	// when this object was updated last
	@Persistent
	private Date thingDateUpdated;

	// who created this object
	@Persistent
	private long thingCreatedByThingId;

	// who last updated this object
	@Persistent
	private long thingUpdatedByThingId;
	
	// assign ownership of this thing to this thing
	@Persistent
	private long[] thingOwnerThingIds;


	
	

	@Persistent
	private long stuffThingStuffId;

	@Persistent
	private long stuffThingStuffTypeId;
	
	// who is the parent
	@Persistent
	private long stuffParentThingId;
	
	// values that can be stored
	@Persistent
	private String stuffValue;

	@Persistent
	private Boolean stuffValueBol;

	@Persistent
	private Double stuffValueDouble;

	@Persistent
	private Long stuffValueLong;

	@Persistent 
	private Date stuffValueDate;

	// when did this start in time
	@Persistent
	private Date stuffStartOf;

	// when did this end in time
	@Persistent
	private Date stuffEndOf;
	
	// order the list by this
	@Persistent
	private Double stuffRank;

	// when this object was created
	@Persistent
	private Date stuffDateCreated;

	// when this object was updated
	@Persistent
	private Date stuffDateUpdated;

	// who created this object
	@Persistent
	private long stuffCreatedByThingId;

	// who updated this object
	@Persistent
	private long stuffUpdatedByThingId;
	
	// assign ownership of this thing to this thing
	@Persistent
	private long[] stuffOwnerThingIds;

	
	
	
	
	@Persistent
	private long stuffAboutId;
	
	//why kind of stuff, defined as type, is this type of stuff
	@Persistent
	private long stuffAboutThingStuffTypeId;

	// values that can be stored
	@Persistent
	private String stuffAboutValue;

	@Persistent
	private Boolean stuffAboutValueBol;

	@Persistent
	private Double stuffAboutValueDouble;

	@Persistent
	private Long stuffAboutValueLong;

	// TODO - could stick this in valueLong, but wondering how to deal with timezone
	@Persistent 
	private Date stuffAboutValueDate;

	// when did this start in time
	@Persistent
	private Date stuffAboutStartOf;

	// when did this end in time
	@Persistent
	private Date stuffAboutEndOf;

	// order the list by this
	@Persistent
	private Double stuffAboutRank;

	// when this object was created
	@Persistent
	private Date stuffAboutDateCreated;

	// when this object was updated
	@Persistent
	private Date stuffAboutDateUpdated;

	// who created this object
	@Persistent
	private long stuffAboutCreatedByThingId;

	// who updated this object
	@Persistent
	private long stuffAboutUpdatedByThingId;

	// assign ownership of this thing to this thing
	@Persistent
	private long[] stuffAboutOwnerThingIds;
	
	
	
	/**
	 * constructor - nothing to do
	 */
	public DataJoinJdo(ServerPersistence sp) {
		this.sp = sp;
	}

	private void setData(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
		
	
	}

	private void setData(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo, ThingStuffJdo thingStuffAboutJdo) {
		

	}

}




