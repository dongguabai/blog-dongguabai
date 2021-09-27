-- 简易订单表
CREATE TABLE `demo1_order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_state` varchar(200) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;