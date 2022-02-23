-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rxapp
-- ------------------------------------------------------
-- Server version	5.7.36-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `druginfo`
--

DROP TABLE IF EXISTS `druginfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `druginfo` (
  `DrugName` varchar(20) NOT NULL,
  `UserName` varchar(20) NOT NULL,
  `Dosage` varchar(40) NOT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `BadInteractions` varchar(50) DEFAULT NULL,
  `SideEffects` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DrugName`,`UserName`),
  KEY `UserName` (`UserName`),
  CONSTRAINT `druginfo_ibfk_1` FOREIGN KEY (`UserName`) REFERENCES `userpassword` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timesheet`
--

DROP TABLE IF EXISTS `timesheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timesheet` (
  `DrugName` varchar(20) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `LastDose` time NOT NULL,
  PRIMARY KEY (`DrugName`,`LastDose`),
  KEY `Username` (`Username`),
  CONSTRAINT `timesheet_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `userpassword` (`UserName`),
  CONSTRAINT `timesheet_ibfk_2` FOREIGN KEY (`DrugName`) REFERENCES `druginfo` (`DrugName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userpassword`
--

DROP TABLE IF EXISTS `userpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userpassword` (
  `ID` int(100) unsigned NOT NULL AUTO_INCREMENT,
  `UserName` varchar(25) NOT NULL,
  `Password` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-23 13:09:09
