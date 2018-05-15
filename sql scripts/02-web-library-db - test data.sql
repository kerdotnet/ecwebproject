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
	('kerdotnet','$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG','evgenys.ivanov@gmail.com','Евгений','Иванов', '+380952711261', 1),
	('marylogin','$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG','mary_ecproj@gmail.com','Mary','Grunvald', '+380951112233', 1),
	('susanlogin','$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG','susan_ecproj@gmail.com','Susan','Zimmerman', '+380951114433', 1),
    ('piterlogin','$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG','piter_ecproj@gmail.com','Piter','Sipula', '+380951112200', 1),
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
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(5,2);

/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookcatalog`
--

LOCK TABLES `bookcatalog` WRITE;

INSERT INTO `bookcatalog` (`name`, `full_name`, `description`, `key_words`) 
VALUES 
('Great at Work','Great at Work: How Top Performers Do Less, Work Better, and Achieve More', 'Wall Street Journal Business Bestseller. A Financial Times Business Book of the Month. Named by The Washington Post as One of the 11 Leadership Books to Read in 2018'
,'productivity, work'),
('Great by Choice', 'Great by Choice: Uncertainty, Chaos, and Luck--Why Some Thrive Despite Them All', 'Ten years after the worldwide bestseller Good to Great, Jim Collins returns withanother groundbreaking work, this time to ask: why do some companies thrive inuncertainty, even chaos, and others do not? Based on nine years of research,buttressed by rigorous analysis and infused with engaging stories, Collins andhis colleague Morten Hansen enumerate the principles for building a truly greatenterprise in unpredictable, tumultuous and fast-moving times. This book isclassic Collins: contrarian, data-driven and uplifting.'
,'business'),
('Чапаев и Пустота', 'Чапаев и Пустота (Russian Edition)', 'Роман «Чапаев и Пустота» сам автор характеризует так «Это первое произведение в мировой литературе, действие которого происходит в абсолютной пустоте». На самом деле оно происходит в 1919 году в дивизии Чапаева, в которой главный герой, поэт-декадент Петр Пустота, служит комиссаром, а также в наши дни, а также, как и всегда у Пелевина, в виртуальном пространстве, где с главным героем встречаются Кавабата, Шварценеггер, «просто Мария» По мнению критиков, «Чапаев и Пустота» является «первым серьезным дзэн-буддистским романом в русской литературе». Файл электронной книги подготовлен в Агентстве ФТМ, Лтд., 2013.'
,'fiction, фантастика, пелевин'),
('Желтая стрела', 'Желтая стрела (Russian Edition)', 'Поезд, идущий в никуда и в никогда, — место действия повести «Желтая стрела». Этот поезд — единственное живое пространство, в котором существуют ее (повести) персонажи. Фантастика? Антиутопия? Игровой прием? И то, и другое, и третье, впрочем, как всегда у Пелевина. Файл электронной книги подготовлен в Агентстве ФТМ, Лтд., 2013.'
,'Fiction, fantasy, pelevin, фантастика')
,('iPhuck 10 (Russian Edition)','iPhuck 10 (Russian Edition) (Russian) Paperback – November 8, 2017','','fiction, фантастика, пелевин')
,('Generation «П»','Generation «П» (Russian Edition)','','fiction, фантастика, пелевин')
,('Бэтман Аполло','Бэтман Аполло (Russian Edition)','','fiction, фантастика, пелевин')
,('Любовь к трем цукербринам','Любовь к трем цукербринам (Russian Edition)','','fiction, фантастика, пелевин')
,('Омон Ра','Омон Ра (Russian Edition) ','','fiction, фантастика, пелевин')
,('Смотритель.','Смотритель. Том 2. Железная бездна','','fiction, фантастика, пелевин')
,('П5','П5 —Прощальные песни политических пигмеев Пиндостана (Russian Edition)','','fiction, фантастика, пелевин')
,('Empire V','Empire V (Russian Edition)','','fiction, фантастика, пелевин')
,('Числа','Числа (Russian Edition)','','fiction, фантастика, пелевин')
,('t','t (Russian Edition)','','fiction, фантастика, пелевин')
,('Смотритель. Том 1.','Смотритель. Том 1. Орден желтого флага (Russian Edition)','','fiction, фантастика, пелевин')
,('Ананасная вода для прекрасной дамы','Ананасная вода для прекрасной дамы (Russian Edition)','','fiction, фантастика, пелевин')
,('Затворник и Шестипалый','Затворник и Шестипалый (Russian Edition)','','fiction, фантастика, пелевин')
,('Священная книга оборотня','Священная книга оборотня (Russian Edition)','','fiction, фантастика, пелевин')
,('Македонская критика французской мысли «Сборник»','Македонская критика французской мысли «Сборник» (Russian Edition)','','fiction, фантастика, пелевин')
,('Принц Госплана','Принц Госплана (Russian Edition)','','fiction, фантастика, пелевин')
,('Жизнь насекомых','Жизнь насекомых (Russian Edition)','Жизнь насекомых (Russian Edition)','fiction, фантастика, пелевин')
,('Зигмунд в кафе','Зигмунд в кафе (Russian Edition)','','fiction, фантастика, пелевин')
,('Виктор Пелевин спрашивает PRов','Виктор Пелевин спрашивает PRов (Russian Edition)','','fiction, фантастика, пелевин')
,('Диалектика Переходного Периода из Ниоткуда в Никуда ','Диалектика Переходного Периода из Ниоткуда в Никуда (сборник) (Russian Edition)','','fiction, фантастика, пелевин')
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

INSERT INTO `bookcatalog_author`  (`bookcatalog_id`, `author_id`)
VALUES 
(1,2),
(2,2),
(2,3),
(3,1),
(4,1),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1),
(10,1),
(11,1),
(12,1),
(13,1),
(14,1),
(15,1),
(16,1),
(17,1),
(18,1),
(19,1),
(20,1),
(21,1),
(22,1),
(23,1),
(24,1)
;

/*!40000 ALTER TABLE `bookcatalog_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bookitem`
--

LOCK TABLES `bookitem` WRITE;

INSERT INTO `bookitem` (`bookcatalog_id`, `description`, `bookshelf_address`) 
VALUES 
(1, 'The first edition', 'C1'),
(1, 'Потрепанный переплет', 'C1'),
(2, 'SN 000123', 'C1-1'),
(3, 'Издание 2017', 'C1-2'),
(4, 'Издание 2018', 'BC1-1'),
(4, 'Издание 2015', 'BC1-2');

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


SET FOREIGN_KEY_CHECKS = 1;
-- Dump completed on 2016-09-24 21:50:59
