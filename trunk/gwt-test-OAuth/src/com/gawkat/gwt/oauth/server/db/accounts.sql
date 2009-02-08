#updated feb 7 2008

CREATE TABLE `application` (
  `ApplicationId` int(11) NOT NULL auto_increment,
  `ConsumerKey` varchar(50) NOT NULL,
  `ConsumerSecret` varbinary(150) NOT NULL COMMENT 'case sensitive',
  `DateCreated` int(11) NOT NULL default '0',
  `DateUpdated` int(11) default '0',
  PRIMARY KEY  (`ApplicationId`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1

CREATE TABLE `group` (
  `GroupId` int(11) NOT NULL auto_increment,
  `UserId` int(11) NOT NULL default '0',
  `Name` varchar(50) NOT NULL,
  `Description` text,
  `DateCreated` int(11) NOT NULL default '0',
  `DateUpdated` int(11) default '0',
  PRIMARY KEY  (`GroupId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `session_accesstoken` (
  `Id` int(11) NOT NULL auto_increment,
  `ApplicationId` int(11) NOT NULL default '0',
  `UserId` int(11) default '0',
  `AccessToken` varbinary(100) NOT NULL,
  `AccessTokenSecret` varbinary(100) NOT NULL,
  `DateCreated` int(11) NOT NULL default '0',
  `DateUpdated` int(11) default '0',
  PRIMARY KEY  USING BTREE (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=147 DEFAULT CHARSET=latin1

CREATE TABLE `session_nonce` (
  `NonceId` int(11) NOT NULL auto_increment,
  `Url` text,
  `UserId` int(11) default '0',
  `ApplicationId` int(11) default '0',
  `Nonce` varbinary(30) default NULL,
  `DateCreated` int(11) NOT NULL default '0',
  `DateUpdated` int(11) default '0',
  PRIMARY KEY  USING BTREE (`NonceId`)
) ENGINE=MyISAM AUTO_INCREMENT=206 DEFAULT CHARSET=latin1

CREATE TABLE `user` (
  `UserId` int(11) NOT NULL auto_increment,
  `ConsumerKey` varbinary(150) NOT NULL COMMENT 'case sensitive hash',
  `ConsumerSecret` varbinary(150) NOT NULL COMMENT 'case sensitive hash',
  `AcceptTerms` tinyint(1) default NULL,
  `DateCreated` int(11) NOT NULL default '0',
  `DateUpdated` int(11) default '0',
  PRIMARY KEY  (`UserId`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1