DROP TABLE IF EXISTS `weixin_bindings`;
CREATE TABLE `weixin_bindings` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `user_id` int(10)  NOT NULL, 
  `open_type` ENUM('mp', 'weapp') DEFAULT NULL,
  `open_id` varchar(32) DEFAULT NULL,
  `union_id` varchar(32) DEFAULT NULL,
  `source_app` varchar(32) DEFAULT NULL,
  `enabled` bit(1) DEFAULT b'1',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `ao_uq_index` (`user_id`,`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='微信账号账号绑定';

