DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `mobile` char(11) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `realname` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `age` int(3)  DEFAULT 0,
  `gender` ENUM('male', 'female') DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `enabled` bit(1) DEFAULT b'1',
  `reg_ip` varchar(15) DEFAULT NULL COMMENT '注册ip',
  `reg_at` datetime DEFAULT NULL,
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `mobile_uq_index` (`mobile`) 
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户表';
