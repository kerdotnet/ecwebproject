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
  `password` char(68) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `flag_enabled` tinyint(1) NOT NULL,
  
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
  REFERENCES `user` (`id`),
  
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
  `name` varchar(255) NOT NULL,
  `full_name` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `flag_enabled` boolean NOT NULL default TRUE,
  
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
  `description` varchar(1000) NOT NULL,
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
  `author_id` int(11) NOT NULL,
  `bookcatalog_id` int(11) NOT NULL,
  
  PRIMARY KEY (`author_id`, `bookcatalog_id`),
  
  KEY `FK_AUTHOR_idx` (`author_id`),
  KEY `FK_BOOKCATALOG_idx` (`bookcatalog_id`),
  
  CONSTRAINT `FK_AUTHOR` FOREIGN KEY (`author_id`) 
  REFERENCES `author` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_BOOKCATALOG` FOREIGN KEY (`bookcatalog_id`) 
  REFERENCES `bookcatalog` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keywords`
--

DROP TABLE IF EXISTS `keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `bookcatalog_id` int(11) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_BOOKCATALOG_KEYWORDS_idx` (`bookcatalog_id`),
  CONSTRAINT `FK_BOOKCATALOG_KEYWORDS` FOREIGN KEY (`bookcatalog_id`) 
  REFERENCES `bookcatalog` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookshelf`
--

DROP TABLE IF EXISTS `bookshelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookshelf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  
  PRIMARY KEY (`id`)
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
  `description` varchar(1000) NOT NULL,
  `flag_enabled` boolean NOT NULL default TRUE,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_BOOKCATALOG_BOOKITEM_idx` (`bookcatalog_id`),
    
  CONSTRAINT `FK_BOOKCATALOG_BOOKITEM` 
  FOREIGN KEY (`bookcatalog_id`) 
  REFERENCES `bookcatalog` (`id`)
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
  `flag_enabled` boolean NOT NULL default TRUE,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_USER_USER_BOOKITEM_idx` (`user_id`),
  KEY `FK_BOOKTIME_USER_BOOKITEM_idx` (`bookitem_id`),
  
  CONSTRAINT `FK_USER_USER_BOOKITEM` 
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`),
  
  CONSTRAINT `FK_BOOKTIME_USER_BOOKITEM_` 
  FOREIGN KEY (`bookitem_id`) 
  REFERENCES `bookitem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookitem_bookshelf`
--

DROP TABLE IF EXISTS `bookitem_bookshelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookitem_bookshelf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookitem_id` int(11) NOT NULL,
  `bookshelf_id` int(11) NOT NULL,
  `bookshelf_adress` varchar(20) NOT NULL,
  `flag_enabled` boolean NOT NULL default TRUE,
  
  
  PRIMARY KEY (`id`),
  
  KEY `FK_BOOKITEM_BOOKSHEHLF_BOOKITEM_idx` (`bookitem_id`),
  KEY `FK_BOOKITEM_BOOKSHEHLF_BOOKSHELF_idx` (`bookshelf_id`),
    
  CONSTRAINT `FK_BOOKITEM_BOOKSHEHLF_BOOKITEM` 
  FOREIGN KEY (`bookitem_id`) 
  REFERENCES `bookitem` (`id`),
  
  CONSTRAINT `FK_BOOKITEM_BOOKSHEHLF_BOOKSHELF` 
  FOREIGN KEY (`bookshelf_id`) 
  REFERENCES `bookshelf` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `bookitem_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `bookshelf_id` int(11) NOT NULL,
  `bookshelf_adress` varchar(20) NOT NULL,
  `flag_enabled` boolean NOT NULL default TRUE,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_TRANSACTION_idx` (`bookitem_id`),
  KEY `FK_TRANSACTION_BOOKSHEHLF_BOOKSHELF_idx` (`bookshelf_id`),
  KEY `FK_TRANSACTION_USER_idx` (`user_id`),
    
  CONSTRAINT `FK_TRANSACTION` 
  FOREIGN KEY (`bookitem_id`) 
  REFERENCES `bookitem` (`id`),
  
  CONSTRAINT `FK_TRANSACTION_BOOKSHEHLF_BOOKSHELF` 
  FOREIGN KEY (`bookshelf_id`) 
  REFERENCES `bookshelf` (`id`),
  
  CONSTRAINT `FK_TRANSACTION_USER` 
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`)
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
	('kerdotnet','{bcrypt}$2y$10$SqJfO2qBQ7oy3PzWNEVwUeNXc12SijvCudykYFbt7VVEGJRMA7tyK','evgenys.ivanov@gmail.com','Евгений','Иванов', '+380952711261', 1),
	('marylogin','{bcrypt}$2y$10$rGwm3NiPYLtp7RhNx7ChIeGhimxm.Sz06GdzHYexbnK2x9T4.6Ydy','mary_ecproj@gmail.com','Mary','Grunvald', '+380951112233', 1),
	('susanlogin','{bcrypt}$2y$10$Ja.P9VEbf9rVB7JjHDfO6.sPbMaSlCaelFhjS0gL5wuO3tVJjMCka','susan_ecproj@gmail.com','Susan','Zimmerman', '+380951114433', 1),
    ('piterlogin','{bcrypt}$2y$10$Ja.P9VEbf9rVB7JjHDfO6.sPbMaSlCaelFhjS0gL5wuO3tVJjMCka','piter_ecproj@gmail.com','Piter','Sipula', '+380951112200', 1);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;

INSERT INTO `authority`  (`name`)
VALUES 
	('USER'),
	('ADMINISTRATOR');

/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;

INSERT INTO `user_authority` (`user_id`, `authority_id`) 
VALUES 
(1,2),
(1,1),
(2,1),
(3,1),
(4,1);

/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookcatalog`
--

LOCK TABLES `bookcatalog` WRITE;

INSERT INTO `bookcatalog` (`name`, `full_name`, `description`) 
VALUES 
('Great at Work','Great at Work: How Top Performers Do Less, Work Better, and Achieve More', 'Wall Street Journal Business Bestseller. A Financial Times Business Book of the Month. Named by The Washington Post as One of the 11 Leadership Books to Read in 2018'),
('Great by Choice', 'Great by Choice: Uncertainty, Chaos, and Luck--Why Some Thrive Despite Them All', 'Ten years after the worldwide bestseller Good to Great, Jim Collins returns withanother groundbreaking work, this time to ask: why do some companies thrive inuncertainty, even chaos, and others do not? Based on nine years of research,buttressed by rigorous analysis and infused with engaging stories, Collins andhis colleague Morten Hansen enumerate the principles for building a truly greatenterprise in unpredictable, tumultuous and fast-moving times. This book isclassic Collins: contrarian, data-driven and uplifting.'),
('Чапаев и Пустота', 'Чапаев и Пустота (Russian Edition)', 'Роман «Чапаев и Пустота» сам автор характеризует так «Это первое произведение в мировой литературе, действие которого происходит в абсолютной пустоте». На самом деле оно происходит в 1919 году в дивизии Чапаева, в которой главный герой, поэт-декадент Петр Пустота, служит комиссаром, а также в наши дни, а также, как и всегда у Пелевина, в виртуальном пространстве, где с главным героем встречаются Кавабата, Шварценеггер, «просто Мария» По мнению критиков, «Чапаев и Пустота» является «первым серьезным дзэн-буддистским романом в русской литературе». Файл электронной книги подготовлен в Агентстве ФТМ, Лтд., 2013.'),
('Желтая стрела', 'Желтая стрела (Russian Edition)', 'Поезд, идущий в никуда и в никогда, — место действия повести «Желтая стрела». Этот поезд — единственное живое пространство, в котором существуют ее (повести) персонажи. Фантастика? Антиутопия? Игровой прием? И то, и другое, и третье, впрочем, как всегда у Пелевина. Файл электронной книги подготовлен в Агентстве ФТМ, Лтд., 2013.')
;

/*!40000 ALTER TABLE `bookcatalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookcatalog`
--

LOCK TABLES `author` WRITE;

INSERT INTO `author` (`name`, `description`) 
VALUES 
('Виктор Пелевин', 'Русский современный фантаст'),
('Morten Hansen', 'Morten T. Hansen is a management professor at University of California, Berkeley. He was previously a professor at Harvard Business School and INSEAD (France). He obtained his Ph.D. from the Graduate School of Business at Stanford University. Hansen has also been a senior management consultant with the Boston Consulting Group in London, Stockholm and San Francisco. He is ranked among the top 50 most influential management thinkers in the world by Thinkers50.'),
('Jim Collins', 'im Collins is a student and teacher of leadership and what makes great companies tickHaving invested a quarter century of research into the topic, he has authored or co-authored six books that have sold in total more than ten million copies worldwide. They include: GOOD TO GREAT, the #1 bestseller, which examines why some companies and leaders make the leap to superior results, along with its companion work GOOD TO GREAT AND THE SOCIAL SECTORS; the enduring classic BUILT TO LAST, which explores how some leaders build companies that remain visionary for generations; HOW THE MIGHTY FALL, which delves into how once-great companies can self-destruct; and most recently, GREAT BY CHOICE, which is about thriving in chaos – why some do, and others don''t – and the leadership behaviors needed in a world beset by turbulence, disruption, uncertainty, and dramatic change.'),
('Frank Herbert','Frank Herbert (1920-86) was born in Tacoma, Washington and worked as a reporter and later editor of a number of West Coast newspapers before becoming a full-time writer. His first sf story was published in 1952 but he achieved fame more than ten years later with the publication in Analog of Dune World and The Prophet of Dune that were amalgamated in the novel Dune in 1965.')
;

/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookcatalog_author`
--

LOCK TABLES `bookcatalog_author` WRITE;

INSERT INTO `bookcatalog_author` 
VALUES 
(1,2),
(2,2),
(2,3),
(3,1),
(4,2);

/*!40000 ALTER TABLE `bookcatalog_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `keywords`
--

LOCK TABLES `keywords` WRITE;

INSERT INTO `keywords` (`name`, `bookcatalog_id`) 
VALUES 
('productivity',1),
('work',1),
('choice',2),
('bestseller',2),
('Пеллевин',3),
('Fiction',3),
('Стрела',4),
('Fiction',4)
;

/*!40000 ALTER TABLE `keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookshelf`
--

LOCK TABLES `bookshelf` WRITE;

INSERT INTO `bookshelf` (`name`) 
VALUES 
('M_BS_1'),
('M_BS_2'),
('M_BS_3')
;

/*!40000 ALTER TABLE `bookshelf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookitem`
--

LOCK TABLES `bookitem` WRITE;

INSERT INTO `bookitem` (`bookcatalog_id`, `description`) 
VALUES 
(1, 'The first edition'),
(1, 'Потрепанный переплет'),
(2, 'SN 000123'),
(3, 'Издание 2017'),
(4, 'Издание 2018'),
(4, 'Издание 2015');

/*!40000 ALTER TABLE `bookitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookitem_user`
--

LOCK TABLES `bookitem_user` WRITE;

INSERT INTO `bookitem_user` (`bookitem_id`, `user_id`, `date`)
VALUES 
(1, 1, "2018-02-18"),
(2, 1, "2018-04-12");

/*!40000 ALTER TABLE `bookitem_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookitem_bookshelf`
--

LOCK TABLES `bookitem_bookshelf` WRITE;

INSERT INTO `bookitem_bookshelf` (`bookitem_id`, `bookshelf_id`, `bookshelf_adress`) 
VALUES 
(1, 1, "A1"),
(2, 2, "A1"),
(3, 3, "A1"),
(4, 1, "A2"),
(5, 2, "A2"),
(6, 3, "A2");

/*!40000 ALTER TABLE `bookitem_bookshelf` ENABLE KEYS */;
UNLOCK TABLES;

SET FOREIGN_KEY_CHECKS = 1;
-- Dump completed on 2016-09-24 21:50:59
