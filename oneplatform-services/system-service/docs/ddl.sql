DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(32)  NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `password` char(128) DEFAULT NULL,
  `avatar` char(200) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1,
  `deleted` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统账号表';


DROP TABLE IF EXISTS `account_scope`;
CREATE TABLE `account_scope` (
  `id` varchar(32)  NOT NULL,
  `account_id` varchar(32)  NOT NULL ,
  `tenant_id` varchar(32) DEFAULT NULL ,
  `principal_type` varchar(32)  NOT NULL ,
  `principal_id` varchar(32)  NOT NULL ,
  `is_admin` tinyint(1) DEFAULT 0  COMMENT '是否管理员',
  `is_default` tinyint(1) DEFAULT 0  COMMENT '是否默认',
  `enabled` tinyint(1) DEFAULT 1,
  `deleted` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统账号范围';


DROP TABLE IF EXISTS `open_account_binding`;
CREATE TABLE `open_account_binding` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32)  NOT NULL, 
  `open_type` varchar(32)  NOT NULL COMMENT '范围类型',
  `open_id` varchar(128) DEFAULT NULL,
  `union_id` varchar(128) DEFAULT NULL,
  `open_app_id` varchar(128) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `ao_uq_index` (`account_id`,`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方账号绑定';

DROP TABLE IF EXISTS `action_logs`;
CREATE TABLE `action_logs` (
  `id` varchar(32) NOT NULL,
  `app_id` varchar(32)  NOT NULL, 
  `tenant_id` varchar(32)  DEFAULT NULL,
  `action_name` varchar(64) NOT NULL,
  `action_key` varchar(128) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `client_type` varchar(32) DEFAULT NULL,
  `platform_type` varchar(32) DEFAULT NULL,
  `module_id` varchar(32) DEFAULT NULL,
  `request_ip` varchar(20) DEFAULT NULL, 
  `request_id` varchar(32) DEFAULT NULL,
  `request_at` datetime DEFAULT NULL,
  `query_parameters` varchar(500) DEFAULT NULL,
  `request_data` TEXT DEFAULT NULL,
  `response_data` TEXT DEFAULT NULL,
  `response_code` int(3) DEFAULT NULL,
  `use_time` int(6) DEFAULT NULL,
  `exceptions` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';
