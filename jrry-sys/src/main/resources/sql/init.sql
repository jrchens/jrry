/*
MySQL Backup
Source Server Version: 5.6.39
Source Database: smart_park
Date: 2018/3/23 10:53:17
*/

/*
CREATE DATABASE `mpms_dev` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE, INDEX, SHOW VIEW ON `mpms_dev`.* TO 'develop'@'%';
FLUSH PRIVILEGES;

USE `mpms_dev`;
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cfg_code` varchar(36) NOT NULL,
  `cfg_type` tinyint(4) unsigned NOT NULL DEFAULT '1',
  `cfg_name` varchar(50) NOT NULL,
  `cfg_value` varchar(200) NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `cruser` varchar(50) NOT NULL,
  `crtime` datetime NOT NULL,
  `mduser` varchar(50) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cfg_code` (`cfg_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `viewname` varchar(50) NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `cruser` varchar(50) NOT NULL,
  `crtime` datetime NOT NULL,
  `mduser` varchar(50) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name` (`group_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  `permission` varchar(200) NOT NULL,
  `viewname` varchar(50) NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `cruser` varchar(50) NOT NULL,
  `crtime` datetime NOT NULL,
  `mduser` varchar(50) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `viewname` varchar(50) NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `cruser` varchar(50) NOT NULL,
  `crtime` datetime NOT NULL,
  `mduser` varchar(50) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_role_permission_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_relation`;
CREATE TABLE `sys_role_permission_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `permission` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `viewname` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  `password_salt` varchar(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `is_disabled` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_locked` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `cruser` varchar(50) NOT NULL,
  `crtime` datetime NOT NULL,
  `mduser` varchar(50) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING HASH,
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_user_group_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group_relation`;
CREATE TABLE `sys_user_group_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `is_def` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `sys_user_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relation`;
CREATE TABLE `sys_user_role_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `is_def` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `_table_template`
-- ----------------------------
/*
CREATE TABLE `_table_template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `cruser` varchar(50) NOT NULL COMMENT '创建用户',
  `crtime` datetime NOT NULL COMMENT '创建时间',
  `mduser` varchar(50) DEFAULT NULL COMMENT '更新用户',
  `mdtime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
*/

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1','6e1352df-2c9d-4811-8359-ac0d68e2291e','2','默认用户组','guest','0','sys','2010-10-10 10:10:10',NULL,NULL), ('2','292d8ffc-e394-49ce-8aba-71499f35fa55','2','默认角色','guest','0','sys','2010-10-10 10:10:10',NULL,NULL), ('3','UPLOAD_BASE_PATH','1','上传路径','/usr/local/var/upload','0','sys','2010-10-10 10:10:10',NULL,NULL), ('4','ASSETS_DOMAIN_NAME','1','静态资源域名','http://192.168.201.130/res','0','sys','2010-10-10 10:10:10',NULL,NULL);
INSERT INTO `sys_group` VALUES ('1','sys','管理员','0','sys','2010-10-10 10:10:10',NULL,NULL), ('2','user','注册用户','0','sys','2010-10-10 10:10:10',NULL,NULL), ('3','guest','游客','0','sys','2010-10-10 10:10:10',NULL,NULL);
INSERT INTO `sys_role` VALUES ('1','sys','管理员','0','sys','2010-10-10 10:10:10',NULL,NULL), ('2','user','注册用户','0','sys','2010-10-10 10:10:10',NULL,NULL), ('3','guest','游客','0','sys','2010-10-10 10:10:10',NULL,NULL);
INSERT INTO `sys_user` VALUES ('1','sys','Admin','d39c172995e5e51bb9f2d30fee1c3fa09c7f26f15f8b991f629d7306a333ca388b5dbe807d571bc43442d549d71ae1335c3741cd8ae255be50b4c6e1a6d9fd93','ZuVQXbee','jrchens@126.com','0','0','0','sys','2010-10-10 10:10:10',NULL,NULL), ('2','jason','Jason','efafe4276436b21d03b62a75495a9e632317baa0494ab423c6ba6a2119acef0026d04dd178eea3c3b967db04be71d7bc4631900cb6172dcfb835acd355ef789a','w0N3N6OL','jrchens@163.com','0','0','0','sys','2010-10-10 10:10:10',NULL,NULL), ('3','mason','Mason','35403eb3d9a683a86d09a5d6b964e22d0efe0907ed386c10ebee89c1a44e2ee00ff27c258607f2fb7c4a922daf1b29bfa3373986d8bbab6fb1f6527170a95e9f','sPxiGWxX','jrchens@foxmail.com','0','0','0','sys','2010-10-10 10:10:10',NULL,NULL);
INSERT INTO `sys_user_group_relation` VALUES ('1','sys','sys','1'), ('2','jason','user','1'), ('3','mason','guest','1');
INSERT INTO `sys_user_role_relation` VALUES ('1','sys','sys','1'), ('2','jason','user','1'), ('3','mason','guest','1');
