CREATE DATABASE  IF NOT EXISTS `smart_contact` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `smart_contact`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: smart_contact
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `cid` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `work` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `UK_gnqwbwwcn7x0m5jlt4158dass` (`email`),
  UNIQUE KEY `UK_n07fa8c8nqso88ftmqd0t50uh` (`phone`),
  KEY `FKe07k4jcfdophemi6j1lt84b61` (`user_id`),
  CONSTRAINT `FKe07k4jcfdophemi6j1lt84b61` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (23,'Rangpur','<p>He is a student. He is very good.</p>','mahin@gmail.com','275276089_5262485790449766_5727544776531509900_n.png','Rakibul Hasan Rakib','Mahin','01709199936','Employeer',14),(27,'Shathibari, Mithapukur, Rangpur','He is a student.','limon@gmail.com',NULL,'Ahsan Habib','Limon','01750663065','Student',16),(39,'Shathibari, Mithapukur, Rangpur','She is good.','lamiya@gmail.com','black_Panzabi.jpg','Lamiya Jahan','Arthi','01923999022','Student',14),(41,'Shathibari, Mithapukur, Rangpur','He is a student.','didar@gmail.com',NULL,'Didar Hossain','Didar','01893993939','Student',14),(48,'Bogura','<p>Student</p>','sabbir@gmai',NULL,'Sabbir Ahmed','Sabbir','01399049439','Student',14),(49,'Bogura','','tarik@gmail.com',NULL,'Tarik Jaman','Trarik','01384939302','Teacher',14),(51,'Rangpur','<p>He is a <strong>student</strong>.</p>','rion@gmail.com','blue_panzabi.png','Rion Islam','Rion','01784938473','Student',16),(52,'Dhaka','','samia@gmail.com','peacock-henna.jpg','Samia Jahan','Sam','01892823909','Student',14),(59,'Rangpur','<p>Lorem ipsum dolor sit emmet. Lorem ipsum dolor sit emmet. Lorem ipsum dolor sit emmet. Lorem ipsum dolor sit emmet. Lorem ipsum dolor sit emmet. vv</p>','nokib@gmail.com','contact.png','Nokib Ahmed','Nokib','01309199939','Student',14),(60,'Dhaka','						    		Good student','zahid@gmail.com','peacock-henna.jpg','Zahid','Hasan','0189383927','Student',14),(61,'Bogura','He is a good boy.		    		','omar@gmail.com','20191030042521_IMG_4945.jpg','Md Omar Faruk','Faruk','01801987546','Software Engineer',14);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (62);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (14,'Hi ! I am Mehedi Hasan.','mehedihasan111941@gmail.com',_binary '',NULL,'Mehedi Hasan','$2a$10$kNxYiWdJlP0fhf6WNpZ/6uNOIZ3J/nmwX1h5ok6GDhqfXzY4ma9cO','ROLE_USER'),(15,'Hi ! I am Mehedi Hasan.','rakibul@gmail.com',_binary '',NULL,'Rakibul Hasan','$2a$10$Zn5.ndwMcHJCfJivfcUzO.fqWvL1vR8qXMX.5DpiNWP/nMkl0JSHO','ROLE_USER'),(16,'Hi! I am fariya','fariya@gmail.com',_binary '',NULL,'Fariya Richie','$2a$10$.pFHUH5qLteoeWgYYST1S.NgEJOpoykE3EhVN0CzQ5BZlRAZ5bUca','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-01 17:28:38
