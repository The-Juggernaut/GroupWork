BEGIN TRANSACTION;
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`userId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`firstName`	TEXT,
	`lastName`	TEXT,
	`telephone`	TEXT,
	`address`	TEXT,
	`profile`	TEXT,
	`balance`	TEXT
);
INSERT INTO `users` VALUES (1,'Bob','Bobby','01234567','Nowhere','','0');
INSERT INTO `users` VALUES (2,'Tim','Timmy','223242','Elsewhere','','0');
INSERT INTO `users` VALUES (3,'Staff','Staffy','253325','Here','','0');
INSERT INTO `users` VALUES (4,'Staffy2','Staffy2y','575686','There','','0');
DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
	`ver`	INTEGER
);
INSERT INTO `system` VALUES (2);
DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
	`staffId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`userId`	INTEGER NOT NULL,
	`date`	TEXT
);
INSERT INTO `staff` VALUES (1,3,'19/11/18');
INSERT INTO `staff` VALUES (2,4,'1/1/73');
DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
	`rId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`title`	TEXT,
	`year`	INTEGER
);
DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
	`author`	TEXT,
	`publisher`	TEXT,
	`genre`		TEXT,
	`ISBN`		TEXT,
	`language`	TEXT,
	`rId`		INTEGER NOT NULL,
	FOREIGN KEY (rId) REFERENCES `resource`(`rId`)
);
DROP TABLE IF EXISTS `dvd`;
CREATE TABLE IF NOT EXISTS `dvd` (
	`director`	TEXT,
	`runtime`	INTEGER,
	`language`	TEXT,
	`languages`	TEXT,
	`rId`		INTEGER NOT NULL,
	FOREIGN KEY (rId) REFERENCES `resource`(`rId`)
);
DROP TABLE IF EXISTS `laptop`;
CREATE TABLE IF NOT EXISTS `laptop` (
	`manufacturer`	TEXT,
	`model`			INTEGER,
	`os`			TEXT,
	`rId`			INTEGER NOT NULL,
	FOREIGN KEY (rId) REFERENCES `resource`(`rId`)
);
INSERT INTO `resource` VALUES (0,'Sapiens', 2016);
INSERT INTO `book` VALUES ('Noah', 'penguin books', 'non-fiction', '111222333', 'English', 0);
DROP TABLE IF EXISTS `copies`;
CREATE TABLE IF NOT EXISTS `copies` (
	`CopyId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`RId`	INTEGER NOT NULL,
	`keeper`	INTEGER,
	`date`	TEXT
);
INSERT INTO `copies` VALUES (1,1,NULL,NULL);
INSERT INTO `copies` VALUES (2,1,NULL,NULL);
INSERT INTO `copies` VALUES (3,1,NULL,NULL);
INSERT INTO `copies` VALUES (4,1,NULL,NULL);
INSERT INTO `copies` VALUES (5,2,NULL,NULL);
INSERT INTO `copies` VALUES (6,2,NULL,NULL);
INSERT INTO `copies` VALUES (7,2,NULL,NULL);
INSERT INTO `copies` VALUES (8,2,NULL,NULL);
INSERT INTO `copies` VALUES (9,3,NULL,NULL);
INSERT INTO `copies` VALUES (10,3,NULL,NULL);
DROP TABLE IF EXISTS `borrowRecords`;
CREATE TABLE IF NOT EXISTS `borrowRecords` (
	`borrowId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`copyId`	INTEGER NOT NULL,
	`userId`	INTEGER NOT NULL,
	`description`	TEXT
);
COMMIT;