CREATE DATABASE  IF NOT EXISTS `awesome_video` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `awesome_video`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: awesome_video
-- ------------------------------------------------------
-- Server version	5.7.28

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
-- Table structure for table `search_records`
--

DROP TABLE IF EXISTS `search_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_records` (
  `id` varchar(64) NOT NULL,
  `content` varchar(255) NOT NULL COMMENT '搜索的内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='视频搜索的记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_records`
--

LOCK TABLES `search_records` WRITE;
/*!40000 ALTER TABLE `search_records` DISABLE KEYS */;
INSERT INTO `search_records` VALUES ('1','刘亦凡'),('18051309YBCMHYRP','17计算机3'),('1805130DAXX58ARP','刘亦凡'),('1805130DMG6P0ZC0','刘亦凡'),('1805130FNGHD3B0H','刘亦凡'),('180513C94W152Z7C','213'),('180513DXNT7SY04H','风景'),('190208938ZBZX0BC','风景'),('19020893SSCD5KP0','风景'),('19020894S5PXHF14','风景'),('19020894YTS49AFW','计算机'),('1902089508Z0C280','zookeeper'),('1902089529RCNN54','风景'),('2','计算机'),('3','计算机'),('4','加笋鸡'),('5','计算机'),('6','倾心'),('7','zookeeper'),('8','zookeeper'),('9','zookeeper');
/*!40000 ALTER TABLE `search_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-21 11:50:50
