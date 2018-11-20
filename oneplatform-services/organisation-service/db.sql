DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `address` varchar(32) DEFAULT NULL,
  `telephone` char(12) DEFAULT NULL,
  `is_branch` tinyint(1) DEFAULT 0 COMMENT '是否分公司',
  `contact_employee_id` int(10) DEFAULT 0 COMMENT '联系人员工ID',
  `contact_name` varchar(16) DEFAULT NULL COMMENT '联系人',
  `in_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='公司信息表';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `telephone` char(12) DEFAULT NULL,
  `company_id` int(10) DEFAULT 0,
  `contact_employee_id` int(10) DEFAULT 0 COMMENT '联系人员工ID',
  `contact_name` varchar(16) DEFAULT NULL COMMENT '联系人',
  `in_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='部门表';


DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='职位表';

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `contact_name` varchar(16) DEFAULT NULL COMMENT '联系人',
  `contact_tel` varchar(12) DEFAULT NULL COMMENT '联系人电话',
  `account_id` int(10) DEFAULT 0,
  `company_id` int(10) DEFAULT NULL,
  `in_active` tinyint(1) DEFAULT 1,
  `joined_at` date DEFAULT NULL,
  `left_at` date DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='员工表';

DROP TABLE IF EXISTS `employee_departments`;
CREATE TABLE `employee_departments` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `employee_id` int(10)  NOT NULL,
  `department_id` int(10)  NOT NULL,
  `positon_name` varchar(32) DEFAULT NULL,
  `in_active` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='员工部门关系';

