-- MySQL dump 10.13  Distrib 5.5.25a, for Linux (i686)
--
-- Host: localhost    Database: testarguments
-- ------------------------------------------------------
-- Server version	5.5.25a

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ActivePerspectives`
--

DROP TABLE IF EXISTS `ActivePerspectives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ActivePerspectives` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `UserID` int(10) NOT NULL,
  `PerspectiveID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ActivePerspectives_Perspective1` (`PerspectiveID`),
  KEY `fk_ActivePerspectives_User1` (`UserID`),
  CONSTRAINT `fk_ActivePerspectives_Perspective1` FOREIGN KEY (`PerspectiveID`) REFERENCES `Perspective` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ActivePerspectives_User1` FOREIGN KEY (`UserID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ActivePerspectives`
--

LOCK TABLES `ActivePerspectives` WRITE;
/*!40000 ALTER TABLE `ActivePerspectives` DISABLE KEYS */;
INSERT INTO `ActivePerspectives` VALUES (1,1,1),(2,4,4),(3,74,780);
/*!40000 ALTER TABLE `ActivePerspectives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Opinions`
--

DROP TABLE IF EXISTS `Opinions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Opinions` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `StatementID` int(10) DEFAULT NULL,
  `UserID` int(10) DEFAULT NULL,
  `Level` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Uniq_Statement_User` (`StatementID`,`UserID`),
  KEY `fk_Opinions_Statements1` (`StatementID`),
  KEY `fk_Opinions_Perspective1` (`UserID`),
  CONSTRAINT `fk_Opinions_Perspective1` FOREIGN KEY (`UserID`) REFERENCES `Perspective` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Opinions_Statements1` FOREIGN KEY (`StatementID`) REFERENCES `Statements` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1202 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Opinions`
--

LOCK TABLES `Opinions` WRITE;
/*!40000 ALTER TABLE `Opinions` DISABLE KEYS */;
INSERT INTO `Opinions` VALUES (552,1,1,0.5);
/*!40000 ALTER TABLE `Opinions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Patch`
--

DROP TABLE IF EXISTS `Patch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Patch` (
  `ID` int(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patch`
--

LOCK TABLES `Patch` WRITE;
/*!40000 ALTER TABLE `Patch` DISABLE KEYS */;
INSERT INTO `Patch` VALUES (0),(1),(2);
/*!40000 ALTER TABLE `Patch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Perspective`
--

DROP TABLE IF EXISTS `Perspective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Perspective` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Type` int(10) NOT NULL,
  `OwnerID` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Perspective_User1` (`OwnerID`),
  CONSTRAINT `fk_Perspective_User1` FOREIGN KEY (`OwnerID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1546 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Perspective`
--

LOCK TABLES `Perspective` WRITE;
/*!40000 ALTER TABLE `Perspective` DISABLE KEYS */;
INSERT INTO `Perspective` VALUES (1,'main',1,1),(3,'main',1,3),(4,'main',1,4),(13,'thesis_owner',2,NULL),(780,'main',1,74);
/*!40000 ALTER TABLE `Perspective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Relation`
--

DROP TABLE IF EXISTS `Relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Relation` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Statement1ID` int(10) NOT NULL,
  `Statement2ID` int(10) NOT NULL,
  `RelationTypeID` int(10) DEFAULT NULL,
  `Weight` double DEFAULT NULL,
  `OwnerID` int(10) NOT NULL,
  `implication21_` double DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `pk_User_S1_S2` (`OwnerID`,`Statement2ID`,`Statement1ID`),
  KEY `fk_Relation_Statements` (`Statement1ID`),
  KEY `fk_Relation_Statements1` (`Statement2ID`),
  KEY `fk_Relation_User1` (`OwnerID`),
  CONSTRAINT `fk_Relation_Statements` FOREIGN KEY (`Statement1ID`) REFERENCES `Statements` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Relation_Statements1` FOREIGN KEY (`Statement2ID`) REFERENCES `Statements` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Relation_User1` FOREIGN KEY (`OwnerID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Relation`
--

LOCK TABLES `Relation` WRITE;
/*!40000 ALTER TABLE `Relation` DISABLE KEYS */;
INSERT INTO `Relation` VALUES (13,13,1,NULL,0.1,1,0);
/*!40000 ALTER TABLE `Relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RelationType`
--

DROP TABLE IF EXISTS `RelationType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RelationType` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RelationType`
--

LOCK TABLES `RelationType` WRITE;
/*!40000 ALTER TABLE `RelationType` DISABLE KEYS */;
/*!40000 ALTER TABLE `RelationType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `State`
--

DROP TABLE IF EXISTS `State`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `State` (
  `UserID` int(10) NOT NULL,
  `StatementID` int(10) NOT NULL,
  PRIMARY KEY (`UserID`),
  KEY `fk_State_Statements1` (`StatementID`),
  CONSTRAINT `fk_State_Statements1` FOREIGN KEY (`StatementID`) REFERENCES `Statements` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_State_User1` FOREIGN KEY (`UserID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `State`
--

LOCK TABLES `State` WRITE;
/*!40000 ALTER TABLE `State` DISABLE KEYS */;
INSERT INTO `State` VALUES (1,1),(4,1),(74,1);
/*!40000 ALTER TABLE `State` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Statements`
--

DROP TABLE IF EXISTS `Statements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Statements` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Summary` varchar(255) DEFAULT NULL,
  `Comment` mediumtext,
  `Owner` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Statements_User1` (`Owner`),
  CONSTRAINT `fk_Statements_User1` FOREIGN KEY (`Owner`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=692 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Statements`
--

LOCK TABLES `Statements` WRITE;
/*!40000 ALTER TABLE `Statements` DISABLE KEYS */;
INSERT INTO `Statements` VALUES (1,'Thesis 1',NULL,1),(13,'Thesis 13',NULL,1),(156,'Thesis 156',NULL,1);
/*!40000 ALTER TABLE `Statements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `containerId` varchar(45) NOT NULL,
  `screen_name_` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `containerId_UNIQUE` (`containerId`),
  UNIQUE KEY `screen_name__UNIQUE` (`screen_name_`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'test_1@provider1.com','13302','testscreen1'),(2,'test_2@provider2.com','10002','testscreen2'),(3,'test_3@provider3.com','-1','testscreen3'),(4,'test_4@provider4.com','10004','testscreen4'),(7,'test_7@provider7.com','10007','testscreen7'),(53,'test_53@provider53.com','10053','testscreen53'),(74,'mirleau@bluewin.ch','10243','mirleau'),(161,'new@new.com','-27','newbee');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-18 21:21:59
