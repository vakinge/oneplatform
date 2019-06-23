DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `user_id` int(10)  NOT NULL ,
  `enabled` tinyint(1) DEFAULT 1,
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `username_uq_index` (`username`),
   UNIQUE INDEX `email_uq_index` (`email`),
   UNIQUE INDEX `mobile_uq_index` (`mobile`) 
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统账号表';

DROP TABLE IF EXISTS `sys_biz_platform`;
CREATE TABLE `sys_biz_platform` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `platform_code` varchar(32) DEFAULT NULL,
  `domains` varchar(300) DEFAULT NULL  COMMENT '前端域名',
  `remark` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='业务平台';


DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `service_id` varchar(32) DEFAULT NULL,
  `route_name` varchar(32) DEFAULT NULL,
  `cors_uris` varchar(200) DEFAULT NULL  COMMENT '可跨域uri集合(多个;隔开)',
  `internal` tinyint(1) DEFAULT 0  COMMENT '是否内部服务模块',
  `enabled` tinyint(1) DEFAULT 1,
  `apidoc_url` varchar(100) DEFAULT NULL,
  `module_type` ENUM('plugin', 'service') DEFAULT 'service' COMMENT '模块类型',
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统业务模块';

DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64)  NOT NULL, 
  `platform_type` varchar(32) NOT NULL COMMENT '平台类型',
  `enabled` tinyint(1) DEFAULT 1,
  `is_default` tinyint(1) DEFAULT 0 COMMENT '是否系统默认',
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户组';


DROP TABLE IF EXISTS `sys_permission_resource`;
CREATE TABLE `sys_permission_resource` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) DEFAULT NULL COMMENT '父ID',
  `module_id` int(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `type` ENUM('menu', 'api') DEFAULT NULL,
  `uri` varchar(200) DEFAULT NULL COMMENT 'uri',
  `permission_code` varchar(200) DEFAULT NULL COMMENT '权限编码',
  `button_code` varchar(100) DEFAULT NULL COMMENT '关联按钮权限编码',
  `http_method` ENUM('GET', 'POST') DEFAULT 'GET',
  `grant_type` ENUM('Anonymous', 'Logined','Authorized') DEFAULT NULL COMMENT '授权类型',
  `icon` varchar(50) DEFAULT NULL COMMENT '资源图标',
  `enabled` tinyint(1) DEFAULT 1,
  `sort` int(2) DEFAULT 99 COMMENT '排序',
  `platform_type` varchar(32) DEFAULT NULL COMMENT '平台类型',
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='权限资源';


DROP TABLE IF EXISTS `sys_permission_group`;
CREATE TABLE `sys_permission_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '描述',
  `platform_type` varchar(32) DEFAULT NULL COMMENT '平台类型',
  `menu_id` int(10) DEFAULT NULL COMMENT '关联菜单id',
  `enabled` tinyint(1) DEFAULT 1,
  `is_default` tinyint(1) DEFAULT 0 COMMENT '是否系统默认',
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='权限组';


DROP TABLE IF EXISTS `sys_subordinate_relations`;
CREATE TABLE `sys_subordinate_relations` (
  `parent_id` int(10) NOT NULL,
  `child_id` int(10) NOT NULL,
  `relation_type` int(1) NOT NULL COMMENT '关系类型(1:用户-用户组,2:权限-权限组,3:菜单-接口)',
  `platform_type` varchar(32) NOT NULL COMMENT '平台类型',
  PRIMARY KEY (`parent_id`,`child_id`,`relation_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='从属关系';


DROP TABLE IF EXISTS `sys_grant_relations`;
CREATE TABLE `sys_grant_relations` (
  `source_id` int(10) NOT NULL,
  `target_id` int(10) NOT NULL,
  `relation_type` int(1) NOT NULL COMMENT '关系类型(1:用户-权限,2:用户-权限组,3:用户组-权限,4:用户组-权限组)',
  `platform_type` varchar(32) NOT NULL COMMENT '平台类型',
  PRIMARY KEY (`source_id`,`target_id`,`relation_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权关系';


DROP TABLE IF EXISTS `sys_logs`;
CREATE TABLE `sys_logs` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `request_id` varchar(100) DEFAULT NULL,
  `module_id` int(10) DEFAULT NULL,
  `module` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `uri` varchar(100) DEFAULT NULL,
  `origin` varchar(100) DEFAULT NULL,
  `request_datas` text DEFAULT NULL,
  `request_ip` varchar(15) DEFAULT NULL,
  `request_at` datetime DEFAULT NULL,
  `response_at` datetime DEFAULT NULL,
  `request_uid` int(10) DEFAULT NULL,
  `request_uname` varchar(32) DEFAULT NULL,
  `response_code` varchar(6) DEFAULT NULL,
  `response_msg` varchar(100) DEFAULT NULL,
  `exception` text DEFAULT NULL,
  `use_time` int(10) DEFAULT NULL,
  `entrylog` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';


INSERT INTO `sys_account` (`id`,`username`, `email`, `mobile`, `password`, `enabled`,`created_at`) VALUES ('1','sa', 'sa@oneplatform.com', '13800138001', '5d10ac96f72efe7c0b984e8283518ac6', '1','2018-03-03 12:55:30');
INSERT INTO `sys_module` (`id`,`name`, `service_id`, `route_name`, `internal`, `enabled`,`apidoc_url`,`module_type`) VALUES 
('1','基础平台', 'oneplatform', '/', '0', '1','/api/swagger-ui.html','service'),
('1001','通用服务', 'common-service', 'common', '0', '1','/api/common/swagger-ui.html','service'),
('1002','用户服务', 'user-service', 'user', '0', '1','/api/user/swagger-ui.html','service'),
('1003','微信服务', 'weixin-service', 'weixin', '0', '1','/api/weixin/swagger-ui.html','service'),
('1004','内容管理服务', 'cms-service', 'cms', '0', '1','/api/cms/swagger-ui.html','service'),
('1005','人事管理服务网', 'organisation-service', 'organisation', '0', '1','/api/organisation/swagger-ui.html','service'),
('1006','智能API', 'smart-api', '/', '0', '1',NULL,'plugin'),
('1007','系统监控', 'system-mgt', '/', '0', '1',NULL,'plugin');

INSERT INTO `sys_permission_resource` (`id`,`parent_id`, `module_id`, `name`, `type`, `uri`, `http_method`, `platform_type`) VALUES 
(1000,'0', '0', '系统管理', 'menu', NULL, NULL, 'admin'),
(1001,'1000', '1', '模块管理', 'menu', '/modules/module/list.html', 'GET', 'admin'),
(1002,'1000', '1', '日志管理', 'menu', '/modules/log/list.html', 'GET', 'admin'),
(1003,'1000', '1001', '文件管理', 'menu', '/modules/common/files.html', 'GET', 'admin'),
(2000,'0', '0', '权限管理', 'menu', NULL, NULL, 'admin'),
(2001,'2000', '1', 'API管理', 'menu', '/modules/resource/apis.html', 'GET', 'admin'),
(2002,'2000', '1', '菜单管理', 'menu', '/modules/resource/menus.html', 'GET', 'admin'),
(2003,'2000', '1', '权限组管理', 'menu', '/modules/permgroup/list.html', 'GET', 'admin'),
(2004,'2000', '1', '用户组管理', 'menu', '/modules/usergroup/list.html', 'GET', 'admin'),
(2005,'2000', '1', '快捷创建模式', 'menu', '/modules/resource/quick_create.html', 'GET', 'admin'),
(3000,'0', '0', '用户管理', 'menu', NULL, NULL, 'admin'),
(3001,'3000', '1002', '用户列表', 'menu', '/modules/user/list.html', 'GET', 'admin'),
(4000,'0', '0', '人事管理', 'menu', NULL, NULL, 'admin'),
(4001,'4000', '1005', '组织架构', 'menu', '/modules/organisation/structure.html', 'GET', 'admin'),
(4002,'4000', '1005', '员工管理', 'menu', '/modules/organisation/employee/list.html', 'GET', 'admin'),
(4003,'4000', '1005', '职位管理', 'menu', '/modules/organisation/positon/list.html', 'GET', 'admin'),
(5000,'0', '0', '智能API', 'menu', NULL, NULL, 'admin'),
(5001,'5000', '1006', '数据源管理', 'menu', '/modules/smartapi/datasource/list.html', 'GET', 'admin'),
(5002,'5000', '1006', '接口管理', 'menu', '/modules/smartapi/apiconfig/list.html', 'GET', 'admin'),
(6000,'0', '0', '系统监控', 'menu', NULL, NULL, 'admin'),
(6001,'6000', '1007', '定时任务监控', 'menu', '/modules/scm/schedule.html', 'GET', 'admin'),
(6002,'6000', '1007', 'kafka监控', 'menu', '/modules/scm/kafka.html', 'GET', 'admin');

