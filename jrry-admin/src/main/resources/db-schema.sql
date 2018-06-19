CREATE TABLE `sys_version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `current_version` varchar(16) NOT NULL,
  `crtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `current_version` (`current_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
