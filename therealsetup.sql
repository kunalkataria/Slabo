DROP DATABASE IF EXISTS ProjectManagement;

CREATE DATABASE ProjectManagement;

CREATE TABLE `ProjectManagement`.`LoginInfo` 
(
	`username` VARCHAR(10) NOT NULL,
	`password` VARCHAR(32) NOT NULL,
	`email` VARCHAR(40),
  	PRIMARY KEY (`username`));

CREATE TABLE `ProjectManagement`.`Projects` 
(
	`projectID` INT(6) NOT NULL auto_increment,
	`title` VARCHAR(75) NOT NULL,
	`description` VARCHAR(1000),
	`duedate` VARCHAR(10),
  	PRIMARY KEY (`projectID`)
);

CREATE TABLE `ProjectManagement`.`ProjectAssignments`
(
	`username` VARCHAR(10) NOT NULL,
	`projectID` INT(6) NOT NULL,
  	FOREIGN KEY (`username`) REFERENCES `ProjectManagement`.`LoginInfo`(`username`),
    FOREIGN KEY (`projectID`) REFERENCES `ProjectManagement`.`Projects`(`projectID`)
);

CREATE TABLE `ProjectManagement`.`TodoItems`
(
	`projectID` INT(6) NOT NULL,
	`todoID` INT(6) NOT NULL auto_increment,
	`title` VARCHAR(100) NOT NULL,
	`completed` BOOL NOT NULL,
	PRIMARY KEY (`todoID`),
  	FOREIGN KEY (`projectID`) REFERENCES `ProjectManagement`.`Projects`(`projectID`)
);

CREATE TABLE `ProjectManagement`.`ProjectFiles`
(
	`projectID` INT(6) NOT NULL,
	`filename` VARCHAR(100) NOT NULL,
	`description` VARCHAR(1000),
	`username` VARCHAR(10) NOT NULL,
  	FOREIGN KEY (`projectID`) REFERENCES `ProjectManagement`.`Projects`(`projectID`)
);

CREATE TABLE `ProjectManagement`.`Calendar`
(
	`projectID` INT(6) NOT NULL,
	`eventID` INT(6) NOT NULL auto_increment,
	`description` VARCHAR(1000),
	`username` VARCHAR(10) NOT NULL,
	`title` VARCHAR(75) NOT NULL,
	`date` VARCHAR(10) NOT NULL,
	`row` INT(2) NOT NULL,
	`col` INT(2) NOT NULL,

	PRIMARY KEY (`eventID`),
  	FOREIGN KEY (`projectID`) REFERENCES `ProjectManagement`.`Projects`(`projectID`)
);

CREATE TABLE `ProjectManagement`.`EventRSVP`
(
	`eventID` INT(6) NOT NULL,
	`RSVPStatus` INT(1), -- 0 for no, 1 for maybe or 2 for yes
	`username` VARCHAR(10) NOT NULL,
  	FOREIGN KEY (`eventID`) REFERENCES `ProjectManagement`.`Calendar`(`eventID`),
  	FOREIGN KEY (`username`) REFERENCES `ProjectManagement`.`LoginInfo`(`username`)
);

CREATE TABLE `ProjectManagement`.`Polls` 
(
	`projectID` INT(6) NOT NULL,
	`pollID` INT(6) NOT NULL auto_increment,
	`title` VARCHAR(75) NOT NULL,
	`username` VARCHAR(10) NOT NULL,
	FOREIGN KEY (`projectID`) REFERENCES `ProjectManagement`.`Projects`(`projectID`),
    FOREIGN KEY (`username`) REFERENCES `ProjectManagement`.`LoginInfo`(`username`),
    PRIMARY KEY (`pollID`)
);

CREATE TABLE `ProjectManagement`.`PollItems` 
(
	`pollID` INT(6) NOT NULL,
	`item` VARCHAR(50) NOT NULL,
	`votes` INT(4) NOT NULL,
	FOREIGN KEY (`pollID`) REFERENCES `ProjectManagement`.`Polls`(`pollID`)
);

CREATE TABLE `ProjectManagement`.`PollVoteHistory`
(
	`pollID` INT(6) NOT NULL,
	`username` VARCHAR(10) NOT NULL,
	FOREIGN KEY (`pollID`) REFERENCES `ProjectManagement`.`Polls`(`pollID`),
	FOREIGN KEY (`username`) REFERENCES `ProjectManagement`.`LoginInfo`(`username`)
);