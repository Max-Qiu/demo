
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mybatis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mybatis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_dept` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
INSERT INTO `tbl_dept` VALUES (1,'开发部');
INSERT INTO `tbl_dept` VALUES (2,'测试部');
INSERT INTO `tbl_dept` VALUES (3,'运维部');
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `d_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
INSERT INTO `tbl_employee` VALUES (1,'Tom','Tom@126.com','0',1);
INSERT INTO `tbl_employee` VALUES (2,'Jerry','Jerry@126.com','1',1);
INSERT INTO `tbl_employee` VALUES (3,'Max','Max@126.com','1',2);
INSERT INTO `tbl_employee` VALUES (4,'Vicky','Vicky@126.com','0',3);
INSERT INTO `tbl_employee` VALUES (8,'smith0x1','smith0x1@atguigu.com','1',1);
INSERT INTO `tbl_employee` VALUES (9,'allen0x1','allen0x1@atguigu.com','0',1);
INSERT INTO `tbl_employee` VALUES (10,'smith0x1','smith0x1@atguigu.com','1',1);
INSERT INTO `tbl_employee` VALUES (11,'allen0x1','allen0x1@atguigu.com','0',1);
INSERT INTO `tbl_employee` VALUES (12,'smith0x1','smith0x1@atguigu.com','1',1);
INSERT INTO `tbl_employee` VALUES (13,'allen0x1','allen0x1@atguigu.com','0',1);
