CREATE DATABASE  IF NOT EXISTS `scrumvy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `scrumvy`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: scrumvy
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `daily_scrum_record`
--

DROP TABLE IF EXISTS `daily_scrum_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_scrum_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `description` varchar(150) NOT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `daily_scrum_record_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_scrum_record`
--

LOCK TABLES `daily_scrum_record` WRITE;
/*!40000 ALTER TABLE `daily_scrum_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_scrum_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invites`
--

DROP TABLE IF EXISTS `invites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invites` (
  `invite_id` bigint NOT NULL AUTO_INCREMENT,
  `receiving_user_id` int NOT NULL,
  `project_role_id` int NOT NULL,
  `project_id` bigint NOT NULL,
  `accepted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`invite_id`),
  KEY `project_role_id` (`project_role_id`),
  KEY `project_id` (`project_id`),
  KEY `receiving_user_id` (`receiving_user_id`),
  CONSTRAINT `invites_ibfk_1` FOREIGN KEY (`project_role_id`) REFERENCES `project_roles` (`project_role_id`),
  CONSTRAINT `invites_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE,
  CONSTRAINT `invites_ibfk_3` FOREIGN KEY (`receiving_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invites`
--

LOCK TABLES `invites` WRITE;
/*!40000 ALTER TABLE `invites` DISABLE KEYS */;
INSERT INTO `invites` VALUES (77,14,3,71,0);
/*!40000 ALTER TABLE `invites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_roles`
--

DROP TABLE IF EXISTS `project_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_roles` (
  `project_role_id` int NOT NULL AUTO_INCREMENT,
  `role_description` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`project_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_roles`
--

LOCK TABLES `project_roles` WRITE;
/*!40000 ALTER TABLE `project_roles` DISABLE KEYS */;
INSERT INTO `project_roles` VALUES (1,'Product Owner'),(2,'Scrum Master'),(3,'Development Team');
/*!40000 ALTER TABLE `project_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_team`
--

DROP TABLE IF EXISTS `project_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_team` (
  `project_team_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `project_role_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`project_team_id`),
  KEY `fk_prTeamId_proj` (`project_id`),
  KEY `fk_prTeamId_role` (`project_role_id`),
  KEY `fk_User_id` (`user_id`),
  CONSTRAINT `fk_prTeamId_proj` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_prTeamId_role` FOREIGN KEY (`project_role_id`) REFERENCES `project_roles` (`project_role_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_User_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_team`
--

LOCK TABLES `project_team` WRITE;
/*!40000 ALTER TABLE `project_team` DISABLE KEYS */;
INSERT INTO `project_team` VALUES (102,70,1,12),(103,71,1,13),(104,72,1,14),(105,73,1,15),(106,72,3,12),(107,71,2,12),(108,73,3,12),(109,73,3,13),(110,70,3,15),(112,74,1,16);
/*!40000 ALTER TABLE `project_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `project_id` bigint NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) NOT NULL,
  `project_description` varchar(150) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (70,'Creation of denverparts.com Website','Creation of eshop for client Denver Parts.','2020-05-31','2020-09-29'),(71,'Migrate site from wiz to client Server','Migration of website to Client\'s Server','2020-06-01','2020-07-30'),(72,'Pricing Project','Create different Pricing Pages','2020-05-31','2020-06-30'),(73,'Blog site for Ariadne Switcher','Create a Blog page - Connect to private server','2020-06-01','2020-08-30'),(74,'Shopping Website','Shopping website for allthethingsyouneed.com','2020-05-01','2020-06-28');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retrospective`
--

DROP TABLE IF EXISTS `retrospective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retrospective` (
  `story_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`story_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `retrospective_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retrospective`
--

LOCK TABLES `retrospective` WRITE;
/*!40000 ALTER TABLE `retrospective` DISABLE KEYS */;
/*!40000 ALTER TABLE `retrospective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_SIMPLEUSER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sprint_tasks`
--

DROP TABLE IF EXISTS `sprint_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sprint_tasks` (
  `sprint_id` bigint NOT NULL,
  `task_id` bigint NOT NULL,
  PRIMARY KEY (`sprint_id`,`task_id`),
  KEY `FK_SPRINT_ID` (`task_id`),
  CONSTRAINT `FK_SPRINT_ID` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`),
  CONSTRAINT `FK_TASK_ID` FOREIGN KEY (`sprint_id`) REFERENCES `sprints` (`sprint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sprint_tasks`
--

LOCK TABLES `sprint_tasks` WRITE;
/*!40000 ALTER TABLE `sprint_tasks` DISABLE KEYS */;
INSERT INTO `sprint_tasks` VALUES (3,49),(3,50),(5,51),(7,52),(7,53),(6,54),(4,55),(5,55),(6,56),(5,57),(6,57),(4,58),(6,58),(7,59),(4,60),(7,60),(8,63),(8,64),(8,65),(9,66),(8,67),(9,68),(9,69),(8,70),(11,71),(10,72),(11,73),(10,75),(11,76),(10,77),(12,78),(12,79),(13,80),(13,81),(13,82),(13,83),(14,84),(14,85),(15,85),(14,87),(15,87),(15,88),(15,89),(16,90),(16,91),(17,91),(16,92),(17,92),(18,93),(19,97),(19,98),(18,99),(19,99);
/*!40000 ALTER TABLE `sprint_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sprints`
--

DROP TABLE IF EXISTS `sprints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sprints` (
  `sprint_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `sprint_start_date` date DEFAULT NULL,
  `sprint_end_date` date DEFAULT NULL,
  PRIMARY KEY (`sprint_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `sprints_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sprints`
--

LOCK TABLES `sprints` WRITE;
/*!40000 ALTER TABLE `sprints` DISABLE KEYS */;
INSERT INTO `sprints` VALUES (3,70,'2020-05-31','2020-05-30'),(4,70,'2020-05-31','2020-06-29'),(5,70,'2020-06-30','2020-07-30'),(6,70,'2020-07-31','2020-08-30'),(7,70,'2020-07-31','2020-09-29'),(8,71,'2020-06-01','2020-06-29'),(9,71,'2020-06-29','2020-07-29'),(10,72,'2020-05-31','2020-06-09'),(11,72,'2020-05-10','2020-05-19'),(12,72,'2020-06-20','2020-06-29'),(13,73,'2020-06-02','2020-06-29'),(14,73,'2020-07-01','2020-07-30'),(15,73,'2020-08-01','2020-08-29'),(16,74,'2020-05-01','2020-05-08'),(17,74,'2020-05-09','2020-05-18'),(18,74,'2020-05-19','2020-05-30'),(19,74,'2020-05-31','2020-06-16');
/*!40000 ALTER TABLE `sprints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status_state` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'To Do'),(2,'In Progress'),(3,'Completed');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `task_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(150) DEFAULT NULL,
  `status_id` int NOT NULL DEFAULT '1',
  `project_id` bigint NOT NULL,
  `task_start_date` date DEFAULT NULL,
  `task_end_date` date DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FK_STATUS_ID` (`status_id`),
  KEY `FK_PROJECT_ID` (`project_id`),
  CONSTRAINT `FK_PROJECT_ID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `FK_STATUS_ID` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (49,'Create Database',2,70,'2020-05-31','2020-05-30'),(50,'Create Relations In Database',1,70,'2020-05-31','2020-05-30'),(51,'Create UX/UI',1,70,'2020-06-30','2020-07-30'),(52,'Alpha Version Release/Testing',1,70,'2020-07-31','2020-09-29'),(53,'Beta Version Release/Testing',1,70,'2020-07-31','2020-09-29'),(54,'Migrate Old Data to New DB',1,70,'2020-07-31','2020-08-30'),(55,'Implement JS Functions',1,70,'2020-06-30','2020-07-30'),(56,'Create Support Chat',1,70,'2020-07-31','2020-08-30'),(57,'Integration with Paypal',1,70,'2020-07-31','2020-08-30'),(58,'Migrate to new server',1,70,'2020-07-31','2020-08-30'),(59,'Crash Test',1,70,'2020-07-31','2020-09-29'),(60,'Create Interface',1,70,'2020-07-31','2020-09-29'),(63,'Analysis',1,71,'2020-05-31','2020-06-28'),(64,'Compare Costs',1,71,'2020-05-31','2020-06-28'),(65,'Implement new Business Logic',1,71,'2020-05-31','2020-06-28'),(66,'Create Automated Tests',1,71,'2020-06-28','2020-07-28'),(67,'Create new UX/IU',1,71,'2020-05-31','2020-06-28'),(68,'Server Migration',1,71,'2020-06-28','2020-07-28'),(69,'Server Testing',1,71,'2020-06-28','2020-07-28'),(70,'Design Solution',1,71,'2020-05-31','2020-06-28'),(71,'Publish Pricing Page',1,72,'2020-05-09','2020-05-18'),(72,'Design Feedback Form',1,72,'2020-05-30','2020-06-08'),(73,'Release Code for Feedback form',1,72,'2020-05-09','2020-05-18'),(74,'Write Code form feedback form',1,72,'2020-06-07','2020-06-18'),(75,'Design Pricing Page',1,72,'2020-05-30','2020-06-08'),(76,'Update code for Search feature',1,72,'2020-05-09','2020-05-18'),(77,'Discuss with CTA for pricing page',1,72,'2020-05-30','2020-06-08'),(78,'Complete Automatic Testing',1,72,'2020-06-19','2020-06-28'),(79,'Identify Solution for Bugs',1,72,'2020-06-19','2020-06-28'),(80,'Create Account',1,73,'2020-06-01','2020-06-28'),(81,'Delete Comments',1,73,'2020-06-01','2020-06-28'),(82,'Login Page',1,73,'2020-06-01','2020-06-28'),(83,'View Member\'s Profile',1,73,'2020-06-01','2020-06-28'),(84,'Generate incoming trafic report',1,73,'2020-06-30','2020-07-29'),(85,'Follow Updates',1,73,'2020-07-31','2020-08-28'),(86,'Contact Server',1,73,'2020-08-26','2020-08-26'),(87,'Suggest Improvements',1,73,'2020-07-31','2020-08-28'),(88,'Share Content',1,73,'2020-07-31','2020-08-28'),(89,'Add new Keywords',1,73,'2020-07-31','2020-08-28'),(90,'Database Creation',1,74,'2020-05-01','2020-05-08'),(91,'Login Page',1,74,'2020-05-08','2020-05-17'),(92,'Category Page',1,74,'2020-05-08','2020-05-17'),(93,'Filters',1,74,'2020-05-19','2020-05-30'),(94,'Unit Testing',1,74,'2020-05-28','2020-06-09'),(95,'Banner Area',1,74,'2020-06-16','2020-06-18'),(96,'Payment Process',1,74,'2020-06-23','2020-06-25'),(97,'UX/UI',1,74,'2020-05-31','2020-06-16'),(98,'Crash Test',1,74,'2020-05-31','2020-06-16'),(99,'New Features',1,74,'2020-05-31','2020-06-16'),(100,'Integration with Paypal',1,74,'2020-06-23','2020-06-28');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `premium` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (12,'Jenny','$2a$10$/x2m8qDa6o7CtjASl1RlLOw7826wLsW1iMVXBBUWC8MwZqMqWCJ8e','Jenny','Loren','jennyloren@gmail.com',0),(13,'Arthur82','$2a$10$FajOxwzT8JTnOusaD9ouyeg6.Ei.x0XFZdIa/bxSHoC5rjq.2Oypa','Arthur','Milles','arthurmilles@gmail.com',0),(14,'Martino','$2a$10$Q/TNCRx2V5XGR7zgrXRfSORkqdUT11ooNAAnQWwroJnP7bwEP2MES','John','Martin','johnmartin@gmail.com',0),(15,'Klaus','$2a$10$uSlodG0suzfv/j2dq17aLeYapiZQOVcw2iXck5L4RbAKu/.NF3FIG','Klaus','Swaider','klausswaider@gmail.com',1),(16,'Lora','$2a$10$LVnwK0rDzGAhMnT0/ANmLeCIMCv7qwc9RrZPA1.R7ffytZJk5wtFu','Lora','Gonzales','loragonzales@gmail.com',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project`
--

DROP TABLE IF EXISTS `user_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_project` (
  `user_id` int NOT NULL,
  `project_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`project_id`),
  KEY `FK_PROJECT_idx` (`project_id`),
  CONSTRAINT `FK_PROJECT` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `FK_USER_01` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_project`
--

LOCK TABLES `user_project` WRITE;
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
INSERT INTO `user_project` VALUES (12,70),(15,70),(12,71),(13,71),(12,72),(14,72),(12,73),(13,73),(15,73),(16,74);
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_ROLE_idx` (`role_id`),
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (12,1),(13,1),(14,1),(15,1),(16,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-25 16:03:34
