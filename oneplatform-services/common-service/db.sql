DROP TABLE IF EXISTS `static_region`;
CREATE TABLE `static_region` (
  `id` int(10)  NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `name_index` char(1) DEFAULT NULL,
  `name_pinyin` varchar(100) DEFAULT NULL,
  `name_pinyin_short` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省市地区表';


DROP TABLE IF EXISTS `upload_files`;
CREATE TABLE `upload_files` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `app_id` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `provider` varchar(32) DEFAULT NULL,
  `file_name` varchar(32) DEFAULT NULL,
  `file_url` varchar(200) DEFAULT NULL,
  `file_location` varchar(100) DEFAULT NULL,
  `mime_type` varchar(32) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `file_url_index` (`file_url`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='文件上传信息';

