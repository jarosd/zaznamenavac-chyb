CREATE DATABASE  IF NOT EXISTS `vpa_jarosd_bugs` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `vpa_jarosd_bugs`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 40.114.227.18    Database: vpa_jarosd_bugs
-- ------------------------------------------------------
-- Server version	5.6.44

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
-- Table structure for table `chyby`
--

DROP TABLE IF EXISTS `chyby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chyby` (
  `ID_chyby` int(11) NOT NULL AUTO_INCREMENT,
  `nazov_chyby` varchar(100) COLLATE utf8_slovak_ci NOT NULL,
  `popis_chyby` text COLLATE utf8_slovak_ci NOT NULL,
  `autor` varchar(50) COLLATE utf8_slovak_ci NOT NULL,
  `datum_vytvorenia` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `datum_ukoncenia` datetime DEFAULT NULL,
  `dolezitost` int(11) NOT NULL,
  `umiestnenie` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  PRIMARY KEY (`ID_chyby`),
  KEY `fk_dolezitost_idx` (`dolezitost`),
  CONSTRAINT `fk_dolezitost` FOREIGN KEY (`dolezitost`) REFERENCES `dolezitost` (`level`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chyby`
--

LOCK TABLES `chyby` WRITE;
/*!40000 ALTER TABLE `chyby` DISABLE KEYS */;
INSERT INTO `chyby` VALUES (11,'nová chyba','popis novej chyby','tester','2019-05-28 15:49:23',NULL,7,'doma'),(12,'zmena farby horného panela','Bolo by vhodné, aby bolo možné zmeniť farbu menu - nie každý má rád tmavú kombináciu.','tester','2019-05-28 16:48:29',NULL,5,'táto aplikácia'),(13,'systém zlyhal','Pri testovaní systému došlo k jeho zlyhaniu na stránke s registráciou.','tester','2019-05-28 16:48:55','2019-05-28 16:49:08',1,'');
/*!40000 ALTER TABLE `chyby` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`chyby_AFTER_INSERT` AFTER INSERT ON `chyby` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'INSERT', 'chyba');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`docker`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`chyby_AFTER_UPDATE` AFTER UPDATE ON `chyby` FOR EACH ROW
BEGIN
	INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'UPDATE', 'chyba');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`docker`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`chyby_BEFORE_DELETE` BEFORE DELETE ON `chyby` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(pouzivatel, typ_zmeny, typ_akcie) VALUES(old.autor, 'DELETE', 'chyba');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `dolezitost`
--

DROP TABLE IF EXISTS `dolezitost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dolezitost` (
  `level` int(11) NOT NULL,
  `sk_popis` varchar(50) NOT NULL,
  `farba` varchar(7) NOT NULL DEFAULT '#000000',
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dolezitost`
--

LOCK TABLES `dolezitost` WRITE;
/*!40000 ALTER TABLE `dolezitost` DISABLE KEYS */;
INSERT INTO `dolezitost` VALUES (1,'Fatálna chyba','#F93E2F'),(2,'Kritická chyba','#F96D30'),(3,'Vážna chyba','#F8A348'),(4,'Výstraha','#F9CD48'),(5,'Upozornenie','#EBFA49'),(6,'Informácia','#A7E2FF'),(7,'Poznámka','#2AB9FB');
/*!40000 ALTER TABLE `dolezitost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `komentare`
--

DROP TABLE IF EXISTS `komentare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `komentare` (
  `ID_komentara` int(11) NOT NULL AUTO_INCREMENT,
  `ID_chyby` int(11) NOT NULL,
  `autor` varchar(50) COLLATE utf8_slovak_ci NOT NULL,
  `text_komentara` varchar(255) COLLATE utf8_slovak_ci NOT NULL,
  `cas_vlozenia` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_komentara`),
  KEY `fk_bug_idx` (`ID_chyby`),
  CONSTRAINT `fk_id_chyby_komentare` FOREIGN KEY (`ID_chyby`) REFERENCES `chyby` (`ID_chyby`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komentare`
--

LOCK TABLES `komentare` WRITE;
/*!40000 ALTER TABLE `komentare` DISABLE KEYS */;
INSERT INTO `komentare` VALUES (17,12,'tester','Zatiaľ táto aktivita nie je v pláne.','2019-05-28 16:51:56'),(18,12,'tester','A keď by som vám s tým pomohol?','2019-05-28 16:52:17');
/*!40000 ALTER TABLE `komentare` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`komentare_AFTER_INSERT` AFTER INSERT ON `komentare` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'INSERT', 'komentár');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`komentare_AFTER_UPDATE` AFTER UPDATE ON `komentare` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'UPDATE', 'komentár');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`komentare_BEFORE_DELETE` BEFORE DELETE ON `komentare` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(old.ID_chyby, old.autor, 'DELETE', 'komentár');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `obrazky`
--

DROP TABLE IF EXISTS `obrazky`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `obrazky` (
  `ID_obrazka` int(11) NOT NULL AUTO_INCREMENT,
  `ID_chyby` int(11) NOT NULL,
  `nazov_obrazka` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `autor` varchar(50) CHARACTER SET latin1 NOT NULL,
  `cesta_obrazok` varchar(100) COLLATE utf8_slovak_ci NOT NULL,
  PRIMARY KEY (`ID_obrazka`),
  KEY `fk_bug_idx` (`ID_chyby`),
  CONSTRAINT `fk_bug_obrazky` FOREIGN KEY (`ID_chyby`) REFERENCES `chyby` (`ID_chyby`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obrazky`
--

LOCK TABLES `obrazky` WRITE;
/*!40000 ALTER TABLE `obrazky` DISABLE KEYS */;
INSERT INTO `obrazky` VALUES (6,13,'Prvky systému','tester','tester_1559055018892_prvky_systemu.png'),(7,13,'EER diagram pre prihlasovanie','tester','tester_1559055041417_eer_diagram_postgres.png');
/*!40000 ALTER TABLE `obrazky` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`obrazky_AFTER_INSERT` AFTER INSERT ON `obrazky` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'INSERT', 'obrázok');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`obrazky_AFTER_UPDATE` AFTER UPDATE ON `obrazky` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(new.ID_chyby, new.autor, 'INSERT', 'obrázok');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `vpa_jarosd_bugs`.`obrazky_BEFORE_DELETE` BEFORE DELETE ON `obrazky` FOR EACH ROW
BEGIN
INSERT INTO zaznamy_zmien(ID_bug, pouzivatel, typ_zmeny, typ_akcie) VALUES(old.ID_chyby, old.autor, 'DELETE', 'obrázok');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `zaznamy_zmien`
--

DROP TABLE IF EXISTS `zaznamy_zmien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zaznamy_zmien` (
  `ID_zmena` int(11) NOT NULL AUTO_INCREMENT,
  `ID_bug` int(11) DEFAULT NULL,
  `pouzivatel` varchar(50) COLLATE utf8_slovak_ci NOT NULL,
  `typ_zmeny` varchar(10) CHARACTER SET latin1 NOT NULL,
  `typ_akcie` varchar(20) CHARACTER SET latin1 NOT NULL,
  `cas_vykonania` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_zmena`),
  KEY `fk_bug_zaznamy_zmien_idx` (`ID_bug`),
  CONSTRAINT `fk_bug_zaznamy_zmien` FOREIGN KEY (`ID_bug`) REFERENCES `chyby` (`ID_chyby`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zaznamy_zmien`
--

LOCK TABLES `zaznamy_zmien` WRITE;
/*!40000 ALTER TABLE `zaznamy_zmien` DISABLE KEYS */;
INSERT INTO `zaznamy_zmien` VALUES (94,NULL,'jarosd','INSERT','chyba','2019-05-28 15:43:19'),(95,NULL,'jarosd','UPDATE','chyba','2019-05-28 15:44:35'),(96,NULL,'jarosd','UPDATE','chyba','2019-05-28 15:45:50'),(97,NULL,'jarosd','DELETE','chyba','2019-05-28 15:46:14'),(98,11,'jarosd','INSERT','chyba','2019-05-28 15:49:23'),(99,11,'jarosd','INSERT','komentár','2019-05-28 15:50:45'),(100,11,'jarosd','UPDATE','komentár','2019-05-28 15:54:01'),(101,11,'jarosd','DELETE','komentár','2019-05-28 15:56:09'),(102,11,'jarosd','INSERT','obrázok','2019-05-28 16:16:16'),(103,11,'jarosd','DELETE','obrázok','2019-05-28 16:21:12'),(104,11,'tester','UPDATE','chyba','2019-05-28 16:47:08'),(105,12,'tester','INSERT','chyba','2019-05-28 16:48:29'),(106,13,'tester','INSERT','chyba','2019-05-28 16:48:55'),(107,13,'tester','UPDATE','chyba','2019-05-28 16:49:08'),(108,13,'tester','INSERT','obrázok','2019-05-28 16:50:18'),(109,13,'tester','INSERT','obrázok','2019-05-28 16:50:41'),(110,12,'tester','INSERT','komentár','2019-05-28 16:51:56'),(111,12,'tester','INSERT','komentár','2019-05-28 16:52:17');
/*!40000 ALTER TABLE `zaznamy_zmien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'vpa_jarosd_bugs'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-28 17:03:50
