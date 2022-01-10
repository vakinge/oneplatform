DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` varchar(32) NOT NULL COMMENT '应用系统中的员工ID',
  `code` varchar(32) DEFAULT NULL COMMENT '员工编码',
  `name` varchar(32) DEFAULT NULL COMMENT '员工名称',
  `gender` enum('male','female') NOT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_type` int(1) DEFAULT 1 COMMENT '证件类型',
  `id_no` varchar(100) DEFAULT NULL COMMENT '证件号码',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号',
  `is_leader` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否部门负责人',
  `employ_type` int(1) DEFAULT '1' COMMENT '雇佣类型(1: 正编，2：兼职，3：实习)',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `account_id` varchar(32) DEFAULT NULL COMMENT '管理员账号id',
  `tenant_id` varchar(32) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0，离职；1，在职）',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code_index` (`code`),
  KEY `tenant_id_index` (`tenant_id`),
  KEY `email_index` (`email`),
  KEY `mobile_index` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工基本信息';



DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` varchar(20) NOT NULL COMMENT '主数据ID',
  `code` varchar(32) NOT NULL COMMENT '主数据编码',
  `name` varchar(32) DEFAULT NULL COMMENT '组织名称',
  `parent_id` varchar(255) DEFAULT NULL COMMENT '父级ID',
  `org_type` varchar(45) DEFAULT 'default' COMMENT '组织类型',
  `full_code` varchar(255) DEFAULT NULL COMMENT '组织全编码',
  `full_name` varchar(255) DEFAULT NULL COMMENT '组织全称',
  `sort` int(3) DEFAULT '255' COMMENT '排序',
  `is_virtual` tinyint(1) DEFAULT '0' COMMENT '是否虚拟组织 （0:非虚拟 ，1:虚拟）',
  `leader_id` varchar(32) DEFAULT NULL COMMENT '部门负责人id',
  `tenant_id` varchar(32)  DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code_index` (`code`),
  KEY `tenant_id_index` (`tenant_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门信息';



DROP TABLE IF EXISTS `staff_departments`;
CREATE TABLE `staff_departments` (
  `id` varchar(45)  NOT NULL,
  `staff_id` varchar(32)  NOT NULL,
  `department_id` varchar(64)  NOT NULL,
  `position_id` varchar(64) NOT NULL COMMENT '岗位id',
  `employee_type` varchar(20) DEFAULT NULL COMMENT  '雇佣关系',
  `is_primary` tinyint(1) DEFAULT 1 COMMENT '是否主部门',
  `effect_date` date DEFAULT NULL COMMENT '生效时间',
  `invalid_date` date DEFAULT NULL COMMENT '失效时间',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  `local_update_at` timestamp(3) NULL DEFAULT NULL COMMENT '本地更新时间',
  PRIMARY KEY (`id`),
  KEY `staff_index` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工部门关系';


DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` varchar(32) NOT NULL COMMENT '主数据ID',
  `name` varchar(32) DEFAULT NULL COMMENT '岗位名称',
  `department_id` varchar(45) DEFAULT NULL COMMENT '部门ID',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
   KEY `department_index` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位表';

