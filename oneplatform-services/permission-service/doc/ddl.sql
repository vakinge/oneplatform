DROP TABLE IF EXISTS `system_module`;
CREATE TABLE `system_module` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `service_id` varchar(64) DEFAULT NULL,
  `proxy_url` varchar(100) DEFAULT NULL,
  `route_name` varchar(32) DEFAULT NULL,
  `anonymous_uris` varchar(500) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `enabled` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统服务模块';


DROP TABLE IF EXISTS `system_portal`;
CREATE TABLE `system_portal` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `client_type` varchar(32) NOT NULL COMMENT '用户端类型',
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户',
  `index_path` varchar(100) DEFAULT NULL COMMENT '主页',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用状态：0=禁用; 1=启用',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统门户';

DROP TABLE IF EXISTS `function_resource`;
CREATE TABLE `function_resource` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) DEFAULT NULL COMMENT '父ID',
  `type` enum('catalog','menu','button') NOT NULL COMMENT '类型',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `code` varchar(64) NOT NULL COMMENT '编码',
  `router` varchar(64) NOT NULL COMMENT '路由',
  `view_path` varchar(256) DEFAULT NULL COMMENT '前端页面路径',
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `client_type` enum('pc','mobile','pad') DEFAULT 'pc' COMMENT '用户端类型',
  `is_open_access` tinyint(1) DEFAULT '0' COMMENT '是否开放访问',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否系统默认',
  `is_display` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `sort` int(2) DEFAULT '99' COMMENT '排序',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用状态(0:不可用;1:可用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='功能资源';

DROP TABLE IF EXISTS `api_resource`;
CREATE TABLE `api_resource` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `module_id` int(10) NOT NULL COMMENT '关联系统模块',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `uri` varchar(200) DEFAULT NULL COMMENT 'uri',
  `http_method` enum('GET','POST') DEFAULT 'GET',
  `grant_type` enum('Anonymous','LoginRequired','PermissionRequired') DEFAULT NULL COMMENT '授权类型',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用状态(0:不可用;1:可用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='接口资源';



DROP TABLE IF EXISTS `grant_relations`;
CREATE TABLE `grant_relations` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `source_type` enum('system','function','api') NOT NULL COMMENT '授权资源类型',
  `source_id` varchar(64) NOT NULL COMMENT '资源ID',
  `target_type` enum('user','userGroup') NOT NULL COMMENT '授权目标类型',
  `target_id` varchar(64) NOT NULL COMMENT '目标ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='授权关系';



DROP TABLE IF EXISTS `subordinate_relations`;
CREATE TABLE `subordinate_relations` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` varchar(64) NOT NULL COMMENT '父级ID',
  `child_id` varchar(64) NOT NULL COMMENT '子级ID',
  `child_name` varchar(255) DEFAULT NULL COMMENT '显示名',
  `relation_type` enum('userToGroup','apiToButton','apiToMenu') NOT NULL COMMENT '关系类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='从属关系';


DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '用户组名称',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `role_type` enum('function','data') NOT NULL COMMENT '类型',
  `tenant_id` varchar(64) DEFAULT NULL COMMENT '租户id',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '关联部门id',
  `permissions` TEXT DEFAULT NULL COMMENT '权限',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否系统默认',
  `is_display` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否对外显示',
  `tags` varchar(200) DEFAULT NULL COMMENT '标签',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用状态(0:不可用;1:可用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='功能角色';