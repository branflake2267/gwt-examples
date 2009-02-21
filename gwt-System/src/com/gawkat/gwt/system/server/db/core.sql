#updated feb 21 2009

CREATE TABLE `session_accesstoken` (
  `Id` int(11) NOT NULL auto_increment,
  `ThingTypeId` int(11) NOT NULL default '0',
  `ThingId` int(11) default '0',
  `AccessToken` varbinary(100) NOT NULL,
  `AccessTokenSecret` varbinary(100) NOT NULL,
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `DateUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  USING BTREE (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=151 DEFAULT CHARSET=latin1

CREATE TABLE `session_nonce` (
  `NonceId` int(11) NOT NULL auto_increment,
  `Url` text,
  `ThingTypeId` int(11) default '0',
  `ThingId` int(11) default '0',
  `Nonce` varbinary(30) default NULL,
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `DateUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  USING BTREE (`NonceId`)
) ENGINE=MyISAM AUTO_INCREMENT=212 DEFAULT CHARSET=latin1

CREATE TABLE `thing` (
  `ThingId` int(11) NOT NULL auto_increment,
  `ThingTypeId` int(11) NOT NULL default '0',
  `Key` varchar(150) NOT NULL,
  `Secret` varbinary(150) NOT NULL,
  `AcceptTerms` tinyint(1) default '0',
  `DateCreated` datetime NOT NULL,
  `LastUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ThingId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1

CREATE TABLE `thing_attr_profile_application` (
  `ApplicationProfileId` int(11) NOT NULL auto_increment,
  `Name` varchar(100) NOT NULL,
  `DateCreated` datetime NOT NULL,
  `DateUpdated` datetime default NULL,
  PRIMARY KEY  (`ApplicationProfileId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_attr_profile_group` (
  `GroupProfileId` int(11) NOT NULL auto_increment,
  `Name` varchar(100) NOT NULL,
  `Description` text,
  `DateCreated` datetime NOT NULL,
  `DateUpdated` datetime default NULL,
  PRIMARY KEY  (`GroupProfileId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_attr_profile_user` (
  `UserProfileId` int(11) NOT NULL auto_increment,
  `ThingId` int(11) NOT NULL default '0',
  `Alais` varchar(20) default NULL,
  `NameFirst` varchar(100) default NULL,
  `NameMiddle` varchar(100) default NULL,
  `NameLast` varchar(100) default NULL,
  `DateCreated` datetime NOT NULL,
  `DateUpdated` datetime default NULL,
  PRIMARY KEY  (`UserProfileId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_permission` (
  `ThingPermissionId` int(11) NOT NULL auto_increment,
  `ThingId_Left` int(11) NOT NULL default '0' COMMENT 'Left to Right',
  `ThingId_Right` int(11) NOT NULL default '0' COMMENT 'Left to Right',
  `V` tinyint(1) default '0' COMMENT 'View',
  `I` tinyint(1) default '0' COMMENT 'Insert',
  `U` tinyint(1) default '0' COMMENT 'Update',
  `D` tinyint(1) default '0' COMMENT 'Delete',
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `DateUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ThingPermissionId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_relationship` (
  `ThingRelationshipId` int(11) NOT NULL auto_increment,
  `ThingId_Left` int(11) NOT NULL default '0' COMMENT 'Left to Right',
  `ThingId_Right` int(11) NOT NULL default '0' COMMENT 'Left to Right',
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `LastUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ThingRelationshipId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_share` (
  `ThingShareId` int(11) NOT NULL auto_increment,
  `ThingRelationshipId` int(11) NOT NULL default '0',
  `V` tinyint(1) default '0' COMMENT 'View',
  `I` tinyint(1) default '0' COMMENT 'Insert',
  `U` tinyint(1) default '0' COMMENT 'update',
  `D` tinyint(1) default '0' COMMENT 'delete',
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `DateUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ThingShareId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `thing_type` (
  `ThingTypeId` int(11) NOT NULL auto_increment,
  `Name` varchar(100) NOT NULL,
  `Description` text,
  `DateCreated` datetime NOT NULL default '0000-00-00 00:00:00',
  `LastUpdated` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ThingTypeId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1

