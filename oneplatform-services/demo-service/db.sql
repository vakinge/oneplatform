DROP TABLE IF EXISTS `demo_order`;
CREATE TABLE `demo_order` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) DEFAULT NULL,
  `seller_id` int(10) DEFAULT NULL,
  `seller_name` varchar(32) DEFAULT NULL,
  `buyer_id` int(10) DEFAULT NULL,
  `buyer_name` varchar(32) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `product_name` varchar(64) DEFAULT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  `status` ENUM('NEW', 'PAID','DELIVERED','RECEIVED','CANCEL') DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(10) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
   UNIQUE INDEX `orderno_uq_index` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='订单表(演示功能用)';