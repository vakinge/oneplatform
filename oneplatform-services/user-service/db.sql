DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `mobile` char(11) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `realname` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `gender` ENUM('male', 'female') DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `area_id` int(10) DEFAULT NULL  COMMENT '所在行政区域id',
  `location` varchar(32) DEFAULT NULL  COMMENT '所在地',
  `mobile_verified` bit(1) DEFAULT b'0',
  `deleted` bit(1) DEFAULT b'0',
  `enabled` bit(1) DEFAULT b'1',
  `created_at` datetime DEFAULT NULL,
  `last_login_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `mobile_uq_index` (`mobile`) 
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户信息表';



DROP TABLE IF EXISTS `user_assets`;
CREATE TABLE `user_assets` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `user_id` int(10)  NOT NULL, 
  `asset_type` ENUM('balance', 'coin','point') DEFAULT NULL COMMENT '余额，平台币，积分',
  `available` DECIMAL(10,2) DEFAULT 0 COMMENT '可用的',
  `frozen` DECIMAL(10,2) DEFAULT 0 COMMENT '冻结的',
  `sign` char(32) DEFAULT NULL,
  `enabled` bit(1) DEFAULT b'1',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `ao_uq_index` (`user_id`,`asset_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户资产';


DROP TABLE IF EXISTS `user_asset_log`;
CREATE TABLE `user_asset_log` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `user_id` int(10)  NOT NULL, 
  `user_asset_id` int(10)  NOT NULL, 
  `order_no` varchar(32) DEFAULT NULL,
  `trade_name` varchar(64) DEFAULT NULL,
  `asset_type` ENUM('balance', 'coin','point') DEFAULT NULL COMMENT '余额，平台币，积分',
  `sub_type` ENUM('available', 'frozen') DEFAULT NULL COMMENT '可用的，冻结的',
  `trade_type` ENUM('out', 'in','freeze','unfreeze') DEFAULT NULL COMMENT '支出，收入，冻结，解冻',
  `amount` decimal(12,2) DEFAULT NULL,
  `current_available` DECIMAL(9,2) DEFAULT 0 COMMENT '当前可用的',
  `current_frozen` DECIMAL(10,2) DEFAULT 0 COMMENT '当前冻结的',
  `sign` char(32) DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏(不对外显示)',
  `trade_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operator` varchar(32) DEFAULT NULL COMMENT '经办人',
  PRIMARY KEY (`id`),
   UNIQUE INDEX `ao_uq_index` (`user_id`,`asset_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户资产流水记录';


