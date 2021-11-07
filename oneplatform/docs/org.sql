DROP TABLE IF EXISTS `omp_tenant`;
CREATE TABLE `omp_tenant` (
  `id` varchar(32)  NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `telephone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `province_id` varchar(32) COMMENT '省份ID',
  `city_id`  varchar(32) COMMENT '城市ID',
  `area_id`  varchar(32) COMMENT '地区ID',
  `locations` varchar(100) DEFAULT NULL COMMENT '省市区(,隔开)',
  `address` varchar(100) DEFAULT NULL COMMENT '联系地址',
  `contact_name` varchar(32) DEFAULT NULL COMMENT '联系人',
  `website` varchar(32) DEFAULT NULL COMMENT '官网',
  `status` varchar(32) DEFAULT 'actived',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除',
  `effect_date` date DEFAULT NULL COMMENT '生效时间',
  `invalid_date` date DEFAULT NULL COMMENT '失效时间',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户信息';


DROP TABLE IF EXISTS `mdm_staff`;
CREATE TABLE `mdm_staff` (
  `id` varchar(45) NOT NULL COMMENT '应用系统中的员工ID',
  `code` varchar(45) DEFAULT NULL COMMENT '员工编码',
  `name` varchar(45) DEFAULT NULL COMMENT '员工名称',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_type` int(1) DEFAULT 1 COMMENT '证件类型',
  `id_no` varchar(100) DEFAULT NULL COMMENT '证件号码',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号',
  `is_leader` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否部门负责人',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0，离职；1，在职）',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `serialno_index` (`serial_no`) USING BTREE,
  KEY `email_index` (`email`) USING BTREE,
  KEY `mobile_index` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工基本信息';



DROP TABLE IF EXISTS `mdm_department`;
CREATE TABLE `mdm_department` (
  `id` varchar(45) NOT NULL COMMENT '主数据ID',
  `code` varchar(45) NOT NULL COMMENT '主数据编码',
  `name` varchar(45) DEFAULT NULL COMMENT '组织名称',
  `parent_id` varchar(255) DEFAULT NULL COMMENT '父级ID',
  `org_type` varchar(45) DEFAULT NULL COMMENT '组织类型',
  `full_code` varchar(255) DEFAULT NULL COMMENT '组织全编码',
  `full_name` varchar(255) DEFAULT NULL COMMENT '组织全称',
  `sort_index` int(3) DEFAULT NULL COMMENT '排序索引',
  `is_virtual` tinyint(1) DEFAULT NULL COMMENT '是否虚拟组织 （0:非虚拟 ，1:虚拟）',
  `is_leaf` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点(0:不是 ， 1：是)',
  `leader_id` varchar(32) DEFAULT NULL COMMENT '部门负责人id',
  `tenant_id` varchar(32)  NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除状态（0：否，1：已删除）',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `serialno_index` (`serial_no`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门信息';



DROP TABLE IF EXISTS `mdm_staff_departments`;
CREATE TABLE `mdm_staff_departments` (
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


DROP TABLE IF EXISTS `mdm_position`;
CREATE TABLE `mdm_position` (
  `id` varchar(45) NOT NULL COMMENT '主数据ID',
  `name` varchar(45) DEFAULT NULL COMMENT '岗位名称',
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


