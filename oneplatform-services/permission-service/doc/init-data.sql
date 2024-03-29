INSERT INTO `function_resource` (`id`,`parent_id`,`type`, `name`, `code`, `router`, `view_path`, `icon`, `client_type`, `is_open_access`, `is_default`, `is_display`, `sort`, `enabled`, `deleted`, `created_at`, `created_by`) VALUES 
(1103,1100,'menu', '数据角色管理', '/system/dataRole', 'dataRole', '/system/dataRole/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1104,1100,'menu', '字典管理', '/system/dict', 'dict', '/system/dict/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1105,1100,'menu', '系统配置管理', '/system/config', 'config', '/system/config/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1106,1100,'menu', '账号管理', '/system/account', 'account', '/system/account/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1200,NULL,'catalog', '组织架构管理', 'organization', '/organization', NULL, 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1201,1200,'menu', '部门管理', '/organization/dept', 'dept', '/organization/dept/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1202,1200,'menu', '职位管理', '/organization/position', 'position', '/organization/position/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1203,1200,'menu', '员工管理', '/organization/staff', 'staff', '/organization/staff/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1300,NULL,'catalog', '系统监控', 'monitor', '/dashboard', NULL, 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1301,1300,'menu', '操作日志', '/monitor/log', 'log', '/monitor/log/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统'),
(1302,1300,'menu', '定时任务', '/monitor/scheduler', 'scheduler', '/monitor/scheduler/index', 'icon', 'pc', '0', '1', '1', '99', '1', '0', '1970-01-01', '系统');
