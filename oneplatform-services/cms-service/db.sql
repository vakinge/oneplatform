DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `pid` int(10)  DEFAULT NULL, 
  `item_nums` int(10)  DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='分类';

DROP TABLE IF EXISTS `cms_tag`;
CREATE TABLE `cms_tag` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `item_nums` int(10)  DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='标签';

DROP TABLE IF EXISTS `cms_tag_relations`;
CREATE TABLE `cms_tag_relations` (
  `artcle_id` int(10) NOT NULL,
  `tag_id` int(10)  NOT NULL,
  PRIMARY KEY (`artcle_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章与标签关系';

DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL COMMENT '标题',
  `cover` varchar(200) DEFAULT NULL,
  `summary` varchar(500) DEFAULT NULL,
  `content` text COMMENT '内容',
  `category_id` int(10)  DEFAULT NULL,
  `view_count` int(10) DEFAULT '0' COMMENT '浏览数',
  `comment_count` int(10) DEFAULT '0' COMMENT '评论数',
  `like_count` int(10) DEFAULT '0' COMMENT '点赞数',
  `audited` tinyint(1) DEFAULT '0' COMMENT '是否审核',
  `created_by` int(10) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='文章';

DROP TABLE IF EXISTS `cms_spec_relations`;
CREATE TABLE `cms_spec_relations` (
  `artcle_id` int(10) NOT NULL,
  `target_type` varchar(32)   NOT NULL,
  `target_id` int(10)  NOT NULL,
  PRIMARY KEY (`artcle_id`,`target_type`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专题文章关系';
