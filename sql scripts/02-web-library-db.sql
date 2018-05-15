DROP DATABASE IF EXISTS `web_library`;
CREATE DATABASE  IF NOT EXISTS `web_library` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `web_library`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: web_library
-- ------------------------------------------------------
-- Server version	6.3.10
SET FOREIGN_KEY_CHECKS = 0;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `username` varchar(50) NOT NULL,
  `password` char(60) NOT NULL,
  `email` varchar(50) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `flag_enabled` tinyint(1) NOT NULL DEFAULT TRUE,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_USER_USERNAME_idx`(`username`),
  UNIQUE KEY `FK_USER_EMAIL_idx`(`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `name` varchar(50) NOT NULL,
  `flag_user` tinyint(1) NOT NULL DEFAULT FALSE,
  `flag_administrator` tinyint(1) NOT NULL DEFAULT FALSE,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `authority_id` int(11) NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_USERAUTHORITY_idx` (`user_id`, `authority_id`),
  KEY `FK_USER_AUTHORITY_idx` (`user_id`),
  KEY `FK_AUTHORITY_idx` (`authority_id`),
  
  CONSTRAINT `FK_USER_AUTHORITY` 
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_AUTHORITY` 
  FOREIGN KEY (`authority_id`)
  REFERENCES `authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookcatalog`
--

DROP TABLE IF EXISTS `bookcatalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookcatalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `full_name` varchar(250),
  `description` varchar(1000),
  `key_words` varchar(500),
  `flag_enabled` boolean NOT NULL DEFAULT TRUE,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000),
  `flag_enabled` boolean NOT NULL default TRUE,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookcatalog_author`
--

DROP TABLE IF EXISTS `bookcatalog_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookcatalog_author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` int(11) NOT NULL,
  `bookcatalog_id` int(11) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_AUTHOR_idx` (`author_id`),
  KEY `FK_BOOKCATALOG_idx` (`bookcatalog_id`),
  
  CONSTRAINT `FK_AUTHOR` FOREIGN KEY (`author_id`) 
  REFERENCES `author` (`id`) 
  ON DELETE  NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_BOOKCATALOG` FOREIGN KEY (`bookcatalog_id`) 
  REFERENCES `bookcatalog` (`id`) 
  ON DELETE  NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookitem`
--

DROP TABLE IF EXISTS `bookitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookcatalog_id` int(11) NOT NULL,
  `description` varchar(500),
  `bookshelf_address` varchar(30),
  `flag_enabled` boolean NOT NULL DEFAULT TRUE,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_BOOKCATALOG_BOOKITEM_idx` (`bookcatalog_id`),
    
  CONSTRAINT `FK_BOOKCATALOG_BOOKITEM` 
  FOREIGN KEY (`bookcatalog_id`) 
  REFERENCES `bookcatalog` (`id`)
  ON DELETE  NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookitem_user`
--

DROP TABLE IF EXISTS `bookitem_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookitem_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookitem_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` DATE NOT NULL,
  `flag_enabled` BOOLEAN NOT NULL DEFAULT TRUE,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_USER_USER_BOOKITEM_idx` (`user_id`),
  KEY `FK_BOOKTIME_USER_BOOKITEM_idx` (`bookitem_id`),
  
  CONSTRAINT `FK_USER_USER_BOOKITEM` 
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`)
  ON DELETE  NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_BOOKTIME_USER_BOOKITEM` 
  FOREIGN KEY (`bookitem_id`) 
  REFERENCES `bookitem` (`id`)
  ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Working with Data ****************************************************************

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user`  (`username`, `password`, `email`, `first_name`, `last_name`, `mobile`, `flag_enabled`)
VALUES 
    ('root','$2a$04$5XxD10VHo6pjdZA7V4p5xOeo808w1AVzeHs2/lbCW4BMLH3oM0nVC','root@gmail.com','Root','Root', '+380500000000', 1);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;

INSERT INTO `authority`  (`name`,`flag_user`, `flag_administrator`)
VALUES 
	('USER', true, false),
	('ADMINISTRATOR', false, true);

/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;

INSERT INTO `user_authority` (`user_id`, `authority_id`) 
VALUES 
(1,2),
(1,1);

/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;


SET FOREIGN_KEY_CHECKS = 1;
-- Dump completed on 2016-09-24 21:50:59
