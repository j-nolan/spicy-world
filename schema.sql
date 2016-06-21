CREATE DATABASE  IF NOT EXISTS `Spicy_world` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Spicy_world`;
-- MySQL dump 10.13  Distrib 5.5.49, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: Spicy_world
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1

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
-- Table structure for table `Note`
--

DROP TABLE IF EXISTS `Note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Note` (
  `idNote` int(11) NOT NULL AUTO_INCREMENT,
  `value` int(11) NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  PRIMARY KEY (`idNote`),
  CONSTRAINT `fk_Note_1` FOREIGN KEY (`idNote`) REFERENCES `Sauce` (`idSauce`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Note`
--

LOCK TABLES `Note` WRITE;
/*!40000 ALTER TABLE `Note` DISABLE KEYS */;
/*!40000 ALTER TABLE `Note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pays`
--

DROP TABLE IF EXISTS `Pays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pays` (
  `idPays` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  PRIMARY KEY (`idPays`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pays`
--

LOCK TABLES `Pays` WRITE;
/*!40000 ALTER TABLE `Pays` DISABLE KEYS */;
/*!40000 ALTER TABLE `Pays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Piment`
--

DROP TABLE IF EXISTS `Piment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Piment` (
  `idPiment` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  `idPays` int(11) NOT NULL,
  PRIMARY KEY (`idPiment`),
  CONSTRAINT `fk_Piment_1` FOREIGN KEY (`idPays`) REFERENCES `Pays` (`idPays`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Piment`
--

LOCK TABLES `Piment` WRITE;
/*!40000 ALTER TABLE `Piment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Piment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sauce`
--

DROP TABLE IF EXISTS `Sauce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sauce` (
  `idSauce` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `strength` enum('doux','moyen','fort','très fort','extrême') NOT NULL,
  `image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idSauce`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sauce`
--

LOCK TABLES `Sauce` WRITE;
/*!40000 ALTER TABLE `Sauce` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sauce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sauce_Piment`
--

DROP TABLE IF EXISTS `Sauce_Piment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sauce_Piment` (
  `idSauce` int(11) NOT NULL,
  `idPiment` int(11) NOT NULL,
  CONSTRAINT `fk_Sauce_Piment_1` FOREIGN KEY (`idSauce`) REFERENCES `Sauce` (`idSauce`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sauce_Piment_2` FOREIGN KEY (`idPiment`) REFERENCES `Piment` (`idPiment`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sauce_Piment`
--

LOCK TABLES `Sauce_Piment` WRITE;
/*!40000 ALTER TABLE `Sauce_Piment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sauce_Piment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sauce_Saveur`
--

DROP TABLE IF EXISTS `Sauce_Saveur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sauce_Saveur` (
  `idSauce` int(11) NOT NULL,
  `idSaveur` int(11) NOT NULL,
  CONSTRAINT `fk_Sauce_Saveur_1` FOREIGN KEY (`idSaveur`) REFERENCES `Saveur` (`idSaveur`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sauce_Saveur_2` FOREIGN KEY (`idSauce`) REFERENCES `Sauce` (`idSauce`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sauce_Saveur`
--

LOCK TABLES `Sauce_Saveur` WRITE;
/*!40000 ALTER TABLE `Sauce_Saveur` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sauce_Saveur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Saveur`
--

DROP TABLE IF EXISTS `Saveur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Saveur` (
  `idSaveur` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSaveur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Saveur`
--

LOCK TABLES `Saveur` WRITE;
/*!40000 ALTER TABLE `Saveur` DISABLE KEYS */;
/*!40000 ALTER TABLE `Saveur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-01 11:47:53
