-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: Medicle_Centre
-- ------------------------------------------------------
-- Server version	5.7.13

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
-- Table structure for table `MedicalCases`
--

DROP TABLE IF EXISTS `MedicalCases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MedicalCases` (
  `caseID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`caseID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MedicalCases`
--

LOCK TABLES `MedicalCases` WRITE;
/*!40000 ALTER TABLE `MedicalCases` DISABLE KEYS */;
INSERT INTO `MedicalCases` VALUES (1,'Fever',NULL),(2,'Cold',NULL),(3,'Cough',NULL);
/*!40000 ALTER TABLE `MedicalCases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MedicalCases-Medicines`
--

DROP TABLE IF EXISTS `MedicalCases-Medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MedicalCases-Medicines` (
  `caseID` int(11) NOT NULL,
  `medicineID` int(11) NOT NULL,
  PRIMARY KEY (`caseID`,`medicineID`),
  KEY `fk_medcineID2_idx` (`medicineID`),
  CONSTRAINT `fk_caseID2` FOREIGN KEY (`caseID`) REFERENCES `MedicalCases` (`caseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_medcineID2` FOREIGN KEY (`medicineID`) REFERENCES `Medicines` (`medicineID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MedicalCases-Medicines`
--

LOCK TABLES `MedicalCases-Medicines` WRITE;
/*!40000 ALTER TABLE `MedicalCases-Medicines` DISABLE KEYS */;
INSERT INTO `MedicalCases-Medicines` VALUES (1,1),(3,1),(1,2),(2,2),(3,2),(2,3),(1,4),(1,7),(2,7),(3,7),(1,10),(3,11);
/*!40000 ALTER TABLE `MedicalCases-Medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MedicalHistory`
--

DROP TABLE IF EXISTS `MedicalHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MedicalHistory` (
  `historyID` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` varchar(45) DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `sid` varchar(20) NOT NULL,
  `docID` varchar(30) NOT NULL,
  PRIMARY KEY (`historyID`),
  KEY `sid_idx` (`sid`),
  KEY `fk_docName_idx` (`docID`),
  CONSTRAINT `fk_docID` FOREIGN KEY (`docID`) REFERENCES `Users` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sid` FOREIGN KEY (`sid`) REFERENCES `Students` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MedicalHistory`
--

LOCK TABLES `MedicalHistory` WRITE;
/*!40000 ALTER TABLE `MedicalHistory` DISABLE KEYS */;
INSERT INTO `MedicalHistory` VALUES (10,'2016-12-13 03:49:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~@@@','PS/2014/123','rana123'),(11,'2016-12-13 04:44:PM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@','PS/2014/123','rana123'),(15,'2016-12-13 04:56:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@Heloo thios  is  the  first Description','PS/2014/123','rana123'),(17,'2016-12-13 06:51:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~ketoconazole cream(2%)~~~@@@Give \nthe \nMedicines\nASAP','SE/2014/022','rana123'),(18,'2016-12-13 07:43:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~coamoxiclav 625mg tds~~~dexamethasone 0.5mg bd~~~diclofenac na 50mg bd~~~@@@This \npatiernce is a series case\nneed Special attention.','PS/2014/087','rana123'),(19,'2016-12-14 02:28:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~vitamin c~~~piriton 4mg bd~~~ketoconazole cream(2%)~~~@@@bdhhvdhadb\nsdbcjgdvh\nkadvhkb','PS/2014/123','rana123'),(20,'2016-12-14 06:48:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~ketoconazole cream(2%)~~~dexamethasone 0.5mg bd~~~@@@jfgfjv\nngdngxfch\nchgvjhk','PS/2014/123','rana123'),(21,'2016-12-17 08:46:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~Paracetamol 1g tds~~~@@@','PS/2014/087','rana123'),(22,'2016-12-17 09:21:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~fluconazole 50mg bd~~~@@@','PS/2014/087','rana123'),(23,'2016-12-17 09:27:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~ketoconazole cream(2%)~~~@@@','PS/2014/087','rana123'),(24,'2016-12-17 10:07:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@','PS/2014/087','rana123'),(25,'2016-12-17 10:08:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~@@@sdb\ndkcjks','PS/2014/087','rana123'),(26,'2016-12-17 10:15:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@','PS/2014/087','rana123'),(27,'2016-12-17 10:18:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@ksjdbhk\nsdkvh','PS/2014/087','rana123'),(28,'2016-12-17 10:22:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@erg\nsg','PS/2014/087','rana123'),(29,'2016-12-17 10:39:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~fluconazole 50mg bd~~~@@@mxbvch\nxckvh\ncxlvbhj','PS/2014/087','rana123'),(30,'2016-12-17 10:45:AM','Amoxylin 600mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@This patient is very special.\npay extra attention on this patient','SE/2014/020','rana123'),(31,'2016-12-17 10:46:AM','Paracetamol 1g tds~~~piriton 4mg bd~~~fluconazole 50mg bd~~~@@@This patient is very special.\npay extra attention on this patient','SE/2014/020','rana123'),(32,'2016-12-17 10:56:AM','@@@df\ndf','PS/2014/087','rana123'),(33,'2016-12-17 10:56:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@df\ndf','PS/2014/087','rana123'),(34,'2016-12-17 10:57:AM','coamoxiclav 625mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@df\ndgfhn\ng','PS/2014/087','rana123'),(35,'2016-12-17 10:58:AM','coamoxiclav 625mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@df\ndgfhn\ng','PS/2014/087','rana123'),(36,'2016-12-17 10:58:AM','coamoxiclav 625mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@df\ndgfhn\ng','PS/2014/087','rana123'),(37,'2016-12-17 10:58:AM','coamoxiclav 625mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@df\ndgfhn\ng','PS/2014/087','rana123'),(38,'2016-12-17 10:58:AM','coamoxiclav 625mg tds~~~Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@df\ndgfhn\ng','PS/2014/087','rana123'),(39,'2016-12-17 10:59:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@sd\nfg\ngg','PS/2014/087','rana123'),(40,'2016-12-17 11:02:AM','piriton 4mg bd~~~Amoxylin 600mg tds~~~Paracetamol 1g tds~~~@@@sf\nqwe\nqqqq','PS/2014/087','rana123'),(41,'2016-12-17 11:44:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~cetrizine 10mg nocte~~~@@@','PS/2014/087','rana123'),(42,'2016-12-17 11:44:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~cetrizine 10mg nocte~~~Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~@@@thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','PS/2014/087','rana123'),(43,'2016-12-17 12:49:PM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@This students is very bad.\ncheck very well before issuing any medicines.','PS/2014/123','rana123'),(44,'2016-12-18 07:42:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~coamoxiclav 625mg tds~~~@@@this patient need a specia attention\ngive the medicdr','SE/2014/020','rana123'),(45,'2016-12-18 08:11:AM','Paracetamol 1g tds~~~Amoxylin 600mg tds~~~piriton 4mg bd~~~@@@jfg\nllk','PS/2014/087','rana123'),(46,'2016-12-18 08:18:AM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~ketoconazole cream(2%)~~~@@@the\nuythcxdfkj','PS/2014/087','rana123'),(47,'2016-12-18 01:38:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~dexamethasone 0.5mg bd~~~@@@give the\nSPC brand medicnieds.','PS/2014/087','rana123'),(48,'2016-12-18 01:43:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~dexamethasone 0.5mg bd~~~@@@give the\nSPC brand medicnieds.','PS/2014/087','rana123'),(49,'2016-12-18 01:45:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~coamoxiclav 625mg tds~~~@@@This is a series Patient..need to give the medicines from the\nbest brands...','PS/2014/087','rana123'),(50,'2016-12-18 03:48:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~fastrum gel LA bd~~~@@@GHJ\nOKJU','PS/2014/123','rana123'),(51,'2016-12-18 03:49:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~fastrum gel LA bd~~~Amoxylin 500mg tds~~~Paracetamol 1g tds~~~piriton 4mg bd~~~diclofenac na 50mg bd~~~@@@GHJ\nOKJU\nkjkh','PS/2014/123','rana123'),(52,'2016-12-18 04:29:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~@@@hbhsbcakls\nascbajsbcn','PS/2014/123','rana123'),(53,'2016-12-18 04:32:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~Amoxylin 600mg tds~~~fastrum gel LA bd~~~famotidine 20mg bd~~~cetrizine 10mg nocte~~~cephalexin 500mg tds~~~ketoconazole cream(2%)~~~dexamethasone 0.5mg bd~~~diclofenac na 50mg bd~~~@@@hbhsbcakls\nascbajsbcn','PS/2014/123','rana123'),(54,'2016-12-19 03:24:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~coamoxiclav 625mg tds~~~@@@this vsdb\nsdcg\nsndvg','PS/2014/087','rana123'),(55,'2016-12-20 12:20:PM','Amoxylin 500mg tds~~~Paracetamol 1g tds~~~fluconazole 50mg bd~~~piriton 4mg bd~~~vitamin c~~~cephalexin 500mg tds~~~@@@afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','PS/2014/087','rana123');
/*!40000 ALTER TABLE `MedicalHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MedicalHistory-MedicalCases`
--

DROP TABLE IF EXISTS `MedicalHistory-MedicalCases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MedicalHistory-MedicalCases` (
  `historyID` int(11) NOT NULL,
  `caseID` int(11) NOT NULL,
  PRIMARY KEY (`historyID`,`caseID`),
  KEY `fk_caseID_idx` (`caseID`),
  CONSTRAINT `fk_caseID` FOREIGN KEY (`caseID`) REFERENCES `MedicalCases` (`caseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_historyID2` FOREIGN KEY (`historyID`) REFERENCES `MedicalHistory` (`historyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MedicalHistory-MedicalCases`
--

LOCK TABLES `MedicalHistory-MedicalCases` WRITE;
/*!40000 ALTER TABLE `MedicalHistory-MedicalCases` DISABLE KEYS */;
/*!40000 ALTER TABLE `MedicalHistory-MedicalCases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MedicalHistory-Medicines`
--

DROP TABLE IF EXISTS `MedicalHistory-Medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MedicalHistory-Medicines` (
  `historyID` int(11) NOT NULL,
  `medicineID` int(11) NOT NULL,
  PRIMARY KEY (`historyID`,`medicineID`),
  KEY `fk_medicineID_idx` (`medicineID`),
  CONSTRAINT `fk_historyID` FOREIGN KEY (`historyID`) REFERENCES `MedicalHistory` (`historyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_medicineID` FOREIGN KEY (`medicineID`) REFERENCES `Medicines` (`medicineID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MedicalHistory-Medicines`
--

LOCK TABLES `MedicalHistory-Medicines` WRITE;
/*!40000 ALTER TABLE `MedicalHistory-Medicines` DISABLE KEYS */;
/*!40000 ALTER TABLE `MedicalHistory-Medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medicines`
--

DROP TABLE IF EXISTS `Medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Medicines` (
  `medicineID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`medicineID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medicines`
--

LOCK TABLES `Medicines` WRITE;
/*!40000 ALTER TABLE `Medicines` DISABLE KEYS */;
INSERT INTO `Medicines` VALUES (1,'Amoxylin 500mg tds'),(2,'Paracetamol 1g tds'),(3,'Amoxylin 600mg tds'),(4,'fluconazole 50mg bd'),(5,'ketoconazole cream(2%)'),(6,'coamoxiclav 625mg tds'),(7,'piriton 4mg bd'),(8,'dexamethasone 0.5mg bd'),(9,'cetrizine 10mg nocte'),(10,'vitamin c'),(11,'diclofenac na 50mg bd'),(12,'famotidine 20mg bd'),(13,'fastrum gel LA bd'),(14,'cephalexin 500mg tds');
/*!40000 ALTER TABLE `Medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Students`
--

DROP TABLE IF EXISTS `Students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Students` (
  `sid` varchar(20) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `PriviousSurgery` varchar(10000) DEFAULT 'No previous surgery records.',
  `NonInfectiousDiseases` varchar(10000) DEFAULT 'No non-infectious diseases',
  `EyeCondition` varchar(10000) DEFAULT NULL,
  `AllergicConditions` varchar(10000) DEFAULT 'No allergic condition',
  `BloodGroup` varchar(1000) DEFAULT NULL,
  `LastVisitDate` varchar(20) DEFAULT 'Today is first visit',
  `tempAddress` varchar(100) DEFAULT 'stays in the permanent address',
  `immergencyTelNumber` varchar(10) DEFAULT NULL,
  `personalTelNum` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Students`
--

LOCK TABLES `Students` WRITE;
/*!40000 ALTER TABLE `Students` DISABLE KEYS */;
INSERT INTO `Students` VALUES ('PS/2014/087','Bandara Thennakoon','1993-11-26','45/2 H Aheliyagoda Road,Parakaduwa.','bandaraThenn87@ymail.com','Have done a sugery in the leg after falling off a stair case.','Diabetics','Good','Plaster Allergic','AB+','2016-12-20','23/A/2 Francis Lane, Dalugama, Kelaniya.','0112672453',NULL),('PS/2014/123','Kamal Anuradha ','1995-08-23','23/W/ 1 Sumudu Mawatha, Balangoda.','kamal87@gmail.com','No previous surgery records.','No non-infectious diseases','Good','No allergic condition','O-','2016-12-18','University Hostal,\nWaragoda Road,\nThorana Junction,\nKelaniya.','0112567345','0764587564'),('SE/2014/020','Charith Madusanka','1994-07-08','27/A/2 Old Kaluthara Road, Aluthgama.','charith945@gmail.com','No previous surgery records.','No non-infectious diseases','Good','No allergic condition','A-','2016-12-18','stays in the permanent address','0112782346','0717865432'),('SE/2014/021','W.W.W.M.K.Madhuranga','1995-05-02','35/A/1 First Lane, Maharagama Road, Boralesgamuwa.','123kanishka@gmail.com','No previous surgery records.','No non-infectious diseases','Good','No allergic condition','A-','2016-12-11','stays in the permanent address','0112518161','0779825431'),('SE/2014/022','Pavan Madusha','1995-02-13','31/1 Araliya lane, Gampaha Road, Minuwangoda.','kppavanmadusha@gmail.com','No previous surgery records.','No non-infectious diseases','Good','No allergic condition','B-','2016-12-13','University Hostal(Room S6), Waragoda Road, Thorana Junction, Kelaniya.','0112678654','0785643271');
/*!40000 ALTER TABLE `Students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `uid` varchar(30) NOT NULL,
  `name` varchar(40) NOT NULL,
  `email` varchar(45) NOT NULL,
  `position` varchar(10) NOT NULL,
  `password` varchar(45) NOT NULL,
  `nic` varchar(12) NOT NULL,
  `SLMC_RegNo` varchar(45) DEFAULT '(this attribute is only for doctors)',
  `Address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('admin1','Kanishka Madhuranga','123kanishka@gmail.com','Admin','6088','951232807V','(this attribute is only for doctors)',NULL),('Banuka','Banuka','123kanishka@gmail.com','Doctor','8858','674578543V','6784',NULL),('Charith Madhusanka','Charith Madhusanka','charith945@gmail.com','Admin','8598','946712459V','(this attribute is only for doctors)',NULL),('Lakmal Perera','Lakmal Perera','123kanishka@gmail.com','Doctor','936','764536589V','6745',NULL),('Niroshan Kumaraswamy','Niroshan Kumaraswamy','knniroshan82@gmail.com','Admin','3374','945676543V','(this attribute is only for doctors)',NULL),('Pubudu Boteju','Pubudu Boteju','pubuduboteju95@gmail.com','Admin','6510','956734235V','(this attribute is only for doctors)',NULL),('rana123','Randhika Wijesekara','123kanishka@gmail.com','Doctor','2407','876545326V','7654',NULL),('Thilan Perera','Thilan Perera','123kanishka@gmail.com','Doctor','3011','675434567V','5678',NULL),('Yasiru Kavinda','Yasiru Kavinda','yasirukavinda89@gmail.com','Admin','8151','935634871V','(this attribute is only for doctors)',NULL);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users-Tel`
--

DROP TABLE IF EXISTS `Users-Tel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users-Tel` (
  `uid` varchar(30) NOT NULL,
  `telephoneNo` varchar(10) NOT NULL,
  PRIMARY KEY (`uid`,`telephoneNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users-Tel`
--

LOCK TABLES `Users-Tel` WRITE;
/*!40000 ALTER TABLE `Users-Tel` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users-Tel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescribedMedicine`
--

DROP TABLE IF EXISTS `prescribedMedicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescribedMedicine` (
  `prescribedHistoryID` int(11) NOT NULL,
  `prescribedMedicineName` varchar(45) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `docName` varchar(45) DEFAULT NULL,
  `age` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`prescribedHistoryID`,`prescribedMedicineName`),
  CONSTRAINT `fHID` FOREIGN KEY (`prescribedHistoryID`) REFERENCES `MedicalHistory` (`historyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescribedMedicine`
--

LOCK TABLES `prescribedMedicine` WRITE;
/*!40000 ALTER TABLE `prescribedMedicine` DISABLE KEYS */;
INSERT INTO `prescribedMedicine` VALUES (41,'Amoxylin 600mg tds','','Dr.Randhika Wijesekara','23'),(41,'cetrizine 10mg nocte','','Dr.Randhika Wijesekara','23'),(41,'Paracetamol 1g tds','','Dr.Randhika Wijesekara','23'),(41,'piriton 4mg bd','','Dr.Randhika Wijesekara','23'),(42,'Amoxylin 500mg tds','thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','Dr.Randhika Wijesekara','23'),(42,'Amoxylin 600mg tds','thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','Dr.Randhika Wijesekara','23'),(42,'cetrizine 10mg nocte','thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','Dr.Randhika Wijesekara','23'),(42,'Paracetamol 1g tds','thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','Dr.Randhika Wijesekara','23'),(42,'piriton 4mg bd','thdbihbsjhdvch\nsdkcvhsbd\nsdn,c hs3e423\n2341231','Dr.Randhika Wijesekara','23'),(43,'Amoxylin 600mg tds','This students is very bad.\ncheck very well before issuing any medicines.','Dr.Randhika Wijesekara','21'),(43,'Paracetamol 1g tds','This students is very bad.\ncheck very well before issuing any medicines.','Dr.Randhika Wijesekara','21'),(43,'piriton 4mg bd','This students is very bad.\ncheck very well before issuing any medicines.','Dr.Randhika Wijesekara','21'),(44,'Amoxylin 500mg tds','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(44,'coamoxiclav 625mg tds','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(44,'fluconazole 50mg bd','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(44,'Paracetamol 1g tds','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(44,'piriton 4mg bd','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(44,'vitamin c','this patient need a specia attention\ngive the medicdr','Dr.Randhika Wijesekara','22'),(45,'Amoxylin 600mg tds','jfg\nllk','Dr.Randhika Wijesekara','23'),(45,'Paracetamol 1g tds','jfg\nllk','Dr.Randhika Wijesekara','23'),(45,'piriton 4mg bd','jfg\nllk','Dr.Randhika Wijesekara','23'),(46,'Amoxylin 500mg tds','the\nuythcxdfkj','Dr.Randhika Wijesekara','23'),(46,'diclofenac na 50mg bd','the\nuythcxdfkj','Dr.Randhika Wijesekara','23'),(46,'ketoconazole cream(2%)','the\nuythcxdfkj','Dr.Randhika Wijesekara','23'),(46,'Paracetamol 1g tds','the\nuythcxdfkj','Dr.Randhika Wijesekara','23'),(46,'piriton 4mg bd','the\nuythcxdfkj','Dr.Randhika Wijesekara','23'),(47,'Amoxylin 500mg tds','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(47,'dexamethasone 0.5mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(47,'diclofenac na 50mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(47,'Paracetamol 1g tds','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(47,'piriton 4mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(48,'Amoxylin 500mg tds','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(48,'dexamethasone 0.5mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(48,'diclofenac na 50mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(48,'Paracetamol 1g tds','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(48,'piriton 4mg bd','give the\nSPC brand medicnieds.','Dr.Randhika Wijesekara','23'),(49,'Amoxylin 500mg tds','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(49,'coamoxiclav 625mg tds','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(49,'fluconazole 50mg bd','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(49,'Paracetamol 1g tds','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(49,'piriton 4mg bd','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(49,'vitamin c','This is a series Patient..need to give the medicines from the\nbest brands...','Dr.Randhika Wijesekara','23'),(50,'Amoxylin 500mg tds','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(50,'fastrum gel LA bd','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(50,'fluconazole 50mg bd','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(50,'Paracetamol 1g tds','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(50,'piriton 4mg bd','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(50,'vitamin c','GHJ\nOKJU','Dr.Randhika Wijesekara','21'),(51,'Amoxylin 500mg tds','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(51,'fastrum gel LA bd','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(51,'fluconazole 50mg bd','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(51,'Paracetamol 1g tds','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(51,'piriton 4mg bd','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(51,'vitamin c','GHJ\nOKJU\nkjkh','Dr.Randhika Wijesekara','21'),(52,'Amoxylin 500mg tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(52,'fluconazole 50mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(52,'Paracetamol 1g tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(52,'piriton 4mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(52,'vitamin c','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'Amoxylin 500mg tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'Amoxylin 600mg tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'cephalexin 500mg tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'cetrizine 10mg nocte','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'dexamethasone 0.5mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'diclofenac na 50mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'famotidine 20mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'fastrum gel LA bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'fluconazole 50mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'ketoconazole cream(2%)','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'Paracetamol 1g tds','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'piriton 4mg bd','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(53,'vitamin c','hbhsbcakls\nascbajsbcn','Dr.Randhika Wijesekara','21'),(54,'Amoxylin 500mg tds','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(54,'coamoxiclav 625mg tds','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(54,'fluconazole 50mg bd','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(54,'Paracetamol 1g tds','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(54,'piriton 4mg bd','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(54,'vitamin c','this vsdb\nsdcg\nsndvg','Dr.Randhika Wijesekara','23'),(55,'Amoxylin 500mg tds','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23'),(55,'cephalexin 500mg tds','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23'),(55,'fluconazole 50mg bd','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23'),(55,'Paracetamol 1g tds','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23'),(55,'piriton 4mg bd','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23'),(55,'vitamin c','afdjhchjsdgv\nsdcbgsdvhcs\nsdvcjsd','Dr.Randhika Wijesekara','23');
/*!40000 ALTER TABLE `prescribedMedicine` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-21 22:19:21
