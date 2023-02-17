/*
 Navicat Premium Data Transfer

 Source Server         : lanhaierp
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:30325
 Source Schema         : lanhe_sms

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 12/02/2023 14:55:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '优惠券id',
  `type` int(0) NULL DEFAULT NULL COMMENT '优惠券类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '优惠券名称',
  `platform` int(0) NULL DEFAULT NULL COMMENT '使用平台：0->全部；1->移动；2->PC',
  `count` int(0) NULL DEFAULT NULL COMMENT '数量',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `per_limit` int(0) NULL DEFAULT NULL COMMENT '每人限领张数',
  `min_point` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用门槛；0表示无门槛',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `use_type` int(0) NULL DEFAULT NULL COMMENT '使用类型：0->全场通用；1->指定分类；2->指定商品',
  `note` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `publish_count` int(0) NULL DEFAULT NULL COMMENT '发行数量',
  `use_count` int(0) NULL DEFAULT NULL COMMENT '已使用数量',
  `receive_count` int(0) NULL DEFAULT NULL COMMENT '领取数量',
  `enable_time` datetime(0) NULL DEFAULT NULL COMMENT '可以领取的日期',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '优惠码',
  `member_level` int(0) NULL DEFAULT NULL COMMENT '可领取的会员类型：0->无限时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (2, 0, '全品类通用券', 0, 92, 10.00, 1, 100.00, '2018-08-27 16:40:47', '2018-11-23 16:40:47', 1, '满100减10', 100, 0, 8, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (3, 0, '小米手机专用券', 0, 92, 50.00, 1, 1000.00, '2018-08-27 16:40:47', '2018-11-16 16:40:47', 2, '小米手机专用优惠券', 100, 0, 8, '2018-08-27 16:40:47', NULL, NULL);
INSERT INTO `sms_coupon` VALUES (8, 0, '新优惠券', 0, 100, 100.00, 1, 1000.00, '2018-11-08 00:00:00', '2018-11-27 00:00:00', 0, '测试', 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (9, 0, '全品类通用券', 0, 100, 5.00, 1, 100.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (10, 0, '全品类通用券', 0, 100, 15.00, 1, 200.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 1, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (11, 0, '全品类通用券', 0, 1000, 50.00, 1, 1000.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 1000, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (12, 0, '移动端全品类通用券', 1, 1, 10.00, 1, 100.00, '2018-11-08 00:00:00', '2018-11-10 00:00:00', 0, NULL, 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (19, 0, '手机分类专用', 0, 100, 100.00, 1, 1000.00, '2018-11-09 00:00:00', '2018-11-17 00:00:00', 1, '手机分类专用', 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (20, 0, '小米手机专用', 0, 100, 200.00, 1, 1000.00, '2018-11-09 00:00:00', '2018-11-24 00:00:00', 2, '小米手机专用', 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (21, 0, 'xxx', 0, 100, 10.00, 1, 100.00, '2018-11-09 00:00:00', '2018-11-30 00:00:00', 2, NULL, 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (22, 0, 'string', 0, 0, 0.00, 0, 0.00, '2019-08-18 15:36:11', '2019-08-18 15:36:11', 0, 'string', 0, 0, 0, '2019-08-18 15:36:11', 'string', 0);
INSERT INTO `sms_coupon` VALUES (23, 0, '有效期测试', 0, 100, 10.00, 1, 100.00, '2019-10-05 00:00:00', '2019-10-09 00:00:00', 0, NULL, 100, 0, 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon` VALUES (24, 0, '234234', 0, 1, 1.00, 1, 1.00, NULL, NULL, 0, NULL, 1, 0, 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '优惠券历史id',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '优惠卷id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `coupon_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '优惠码',
  `member_nickname` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '领取人昵称',
  `get_type` int(0) NULL DEFAULT NULL COMMENT '获取类型：0->后台赠送；1->主动获取',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `use_status` int(0) NULL DEFAULT NULL COMMENT '使用状态：0->未使用；1->已使用；2->已过期',
  `use_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '订单编号',
  `order_sn` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_member_id`(`member_id`) USING BTREE,
  INDEX `idx_coupon_id`(`coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '优惠券使用、领取历史表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_history
-- ----------------------------
INSERT INTO `sms_coupon_history` VALUES (2, 2, 1, '4931048380330002', 'windir', 1, '2018-08-29 14:04:12', 1, '2018-11-12 14:38:47', 12, '201809150101000001');
INSERT INTO `sms_coupon_history` VALUES (3, 3, 1, '4931048380330003', 'windir', 1, '2018-08-29 14:04:29', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (4, 4, 1, '4931048380330004', 'windir', 1, '2018-08-29 14:04:32', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (11, 7, 1, '4931048380330001', 'windir', 1, '2018-09-04 16:21:50', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (12, 2, 4, '0340981248320004', 'zhensan', 1, '2018-11-12 14:16:50', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (13, 3, 4, '0342977234360004', 'zhensan', 1, '2018-11-12 14:17:10', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (14, 4, 4, '0343342928830004', 'zhensan', 1, '2018-11-12 14:17:13', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (15, 2, 5, '0351883832180005', 'lisi', 1, '2018-11-12 14:18:39', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (16, 3, 5, '0352201672680005', 'lisi', 1, '2018-11-12 14:18:42', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (17, 4, 5, '0352505810180005', 'lisi', 1, '2018-11-12 14:18:45', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (18, 2, 6, '0356114588380006', 'wangwu', 1, '2018-11-12 14:19:21', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (19, 3, 6, '0356382856920006', 'wangwu', 1, '2018-11-12 14:19:24', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (20, 4, 6, '0356656798470006', 'wangwu', 1, '2018-11-12 14:19:27', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (21, 2, 3, '0363644984620003', 'windy', 1, '2018-11-12 14:20:36', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (22, 3, 3, '0363932820300003', 'windy', 1, '2018-11-12 14:20:39', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (23, 4, 3, '0364238275840003', 'windy', 1, '2018-11-12 14:20:42', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (24, 2, 7, '0385034833070007', 'lion', 1, '2018-11-12 14:24:10', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (25, 3, 7, '0385350208650007', 'lion', 1, '2018-11-12 14:24:13', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (26, 4, 7, '0385632733900007', 'lion', 1, '2018-11-12 14:24:16', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (27, 2, 8, '0388779132990008', 'shari', 1, '2018-11-12 14:24:48', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (28, 3, 8, '0388943658810008', 'shari', 1, '2018-11-12 14:24:49', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (29, 4, 8, '0389069398320008', 'shari', 1, '2018-11-12 14:24:51', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (30, 2, 9, '0390753935250009', 'aewen', 1, '2018-11-12 14:25:08', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (31, 3, 9, '0390882954470009', 'aewen', 1, '2018-11-12 14:25:09', 0, NULL, NULL, NULL);
INSERT INTO `sms_coupon_history` VALUES (32, 4, 9, '0391025542810009', 'aewen', 1, '2018-11-12 14:25:10', 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sms_coupon_product_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_category_relation`;
CREATE TABLE `sms_coupon_product_category_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '优惠券和产品分类关系id',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '优惠卷id',
  `product_category_id` bigint(0) NULL DEFAULT NULL COMMENT '商品分类id',
  `product_category_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品分类名称',
  `parent_category_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '父分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '优惠券和产品分类关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_product_category_relation
-- ----------------------------
INSERT INTO `sms_coupon_product_category_relation` VALUES (4, 19, 30, '手机配件', '手机数码');
INSERT INTO `sms_coupon_product_category_relation` VALUES (6, 2, 29, '男鞋', '服装');

-- ----------------------------
-- Table structure for sms_coupon_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_relation`;
CREATE TABLE `sms_coupon_product_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '优惠券和产品的关系id',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '优惠卷id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_sn` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '优惠券和产品的关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_product_relation
-- ----------------------------
INSERT INTO `sms_coupon_product_relation` VALUES (9, 21, 33, '小米（MI）小米电视4A ', '4609652');
INSERT INTO `sms_coupon_product_relation` VALUES (10, 2, 6, '银色星芒刺绣网纱底裤6', 'No86582');

-- ----------------------------
-- Table structure for sms_flash_promotion
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion`;
CREATE TABLE `sms_flash_promotion`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '限时购id',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '限时购标题',
  `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `status` int(0) NULL DEFAULT NULL COMMENT '上下线状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '秒杀时间段名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '限时购表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_flash_promotion
-- ----------------------------
INSERT INTO `sms_flash_promotion` VALUES (2, '春季家电家具疯狂秒杀13', '2018-11-14', '2018-11-25', 0, '2018-11-16 11:12:13');
INSERT INTO `sms_flash_promotion` VALUES (3, '手机特卖', '2018-11-03', '2018-11-10', 1, '2018-11-16 11:11:31');
INSERT INTO `sms_flash_promotion` VALUES (4, '春季家电家具疯狂秒杀3', '2018-11-24', '2018-11-25', 1, '2018-11-16 11:12:19');
INSERT INTO `sms_flash_promotion` VALUES (5, '春季家电家具疯狂秒杀4', '2018-11-16', '2018-11-16', 1, '2018-11-16 11:12:24');
INSERT INTO `sms_flash_promotion` VALUES (6, '春季家电家具疯狂秒杀5', '2018-11-16', '2018-11-16', 1, '2018-11-16 11:12:31');
INSERT INTO `sms_flash_promotion` VALUES (7, '春季家电家具疯狂秒杀6', '2018-11-16', '2018-11-16', 1, '2018-11-16 11:12:35');
INSERT INTO `sms_flash_promotion` VALUES (8, '春季家电家具疯狂秒杀7', '2018-11-16', '2018-11-16', 0, '2018-11-16 11:12:39');
INSERT INTO `sms_flash_promotion` VALUES (9, '春季家电家具疯狂秒杀8', '2018-11-16', '2018-11-16', 0, '2018-11-16 11:12:42');
INSERT INTO `sms_flash_promotion` VALUES (13, '测试', '2018-11-01', '2018-11-30', 0, '2018-11-19 10:34:24');

-- ----------------------------
-- Table structure for sms_flash_promotion_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_log`;
CREATE TABLE `sms_flash_promotion_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '限时购通知记录id',
  `member_id` int(0) NULL DEFAULT NULL COMMENT '会员id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `member_phone` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '会员手机号',
  `product_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `subscribe_time` datetime(0) NULL DEFAULT NULL COMMENT '会员订阅时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '限时购通知记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_flash_promotion_log
-- ----------------------------

-- ----------------------------
-- Table structure for sms_flash_promotion_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_product_relation`;
CREATE TABLE `sms_flash_promotion_product_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `flash_promotion_id` bigint(0) NULL DEFAULT NULL COMMENT '限时购id',
  `flash_promotion_session_id` bigint(0) NULL DEFAULT NULL COMMENT '限时购场次id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `flash_promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '限时购价格',
  `flash_promotion_count` int(0) NULL DEFAULT NULL COMMENT '限时购数量',
  `flash_promotion_limit` int(0) NULL DEFAULT NULL COMMENT '每人限购数量',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品限时购与商品关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_flash_promotion_product_relation
-- ----------------------------
INSERT INTO `sms_flash_promotion_product_relation` VALUES (1, 2, 1, 26, 3000.00, 10, 1, 0);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (2, 2, 1, 27, 2000.00, 10, 1, 20);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (3, 2, 1, 28, 599.00, 19, 1, 0);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (4, 2, 1, 29, 4999.00, 10, 1, 100);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (9, 2, 2, 26, 29991.00, 1001, 11, 1);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (10, 2, 2, 27, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (11, 2, 2, 28, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (12, 2, 2, 29, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (13, 2, 2, 30, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (14, 2, 3, 31, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (15, 2, 3, 32, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (16, 2, 4, 33, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (17, 2, 4, 34, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (18, 2, 5, 36, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (19, 2, 6, 33, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (20, 2, 6, 34, NULL, NULL, NULL, NULL);
INSERT INTO `sms_flash_promotion_product_relation` VALUES (25, 2, 2, 36, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sms_flash_promotion_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_session`;
CREATE TABLE `sms_flash_promotion_session`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '场次名称',
  `start_time` time(0) NULL DEFAULT NULL COMMENT '每日开始时间',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '每日结束时间',
  `status` int(0) NULL DEFAULT NULL COMMENT '启用状态：0->不启用；1->启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '限时购场次表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_flash_promotion_session
-- ----------------------------
INSERT INTO `sms_flash_promotion_session` VALUES (1, '8:00', '08:00:29', '09:00:33', 0, '2018-11-16 13:22:17');
INSERT INTO `sms_flash_promotion_session` VALUES (2, '10:00', '10:00:33', '11:00:33', 1, '2018-11-16 13:22:34');
INSERT INTO `sms_flash_promotion_session` VALUES (3, '12:00', '12:00:00', '13:00:22', 1, '2018-11-16 13:22:37');
INSERT INTO `sms_flash_promotion_session` VALUES (4, '14:00', '14:00:00', '15:00:00', 1, '2018-11-16 13:22:41');
INSERT INTO `sms_flash_promotion_session` VALUES (5, '16:00', '16:00:00', '17:00:00', 1, '2018-11-16 13:22:45');
INSERT INTO `sms_flash_promotion_session` VALUES (6, '18:00', '18:00:00', '19:00:00', 1, '2018-11-16 13:21:34');
INSERT INTO `sms_flash_promotion_session` VALUES (7, '20:00', '20:00:33', '21:00:33', 0, '2018-11-16 13:22:55');

-- ----------------------------
-- Table structure for sms_home_advertise
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_advertise`;
CREATE TABLE `sms_home_advertise`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页轮播广告id',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '首页轮播广告名称',
  `type` int(0) NULL DEFAULT NULL COMMENT '轮播位置：0->PC首页轮播；1->app首页轮播',
  `pic` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` int(0) NULL DEFAULT NULL COMMENT '上下线状态：0->下线；1->上线',
  `click_count` int(0) NULL DEFAULT NULL COMMENT '点击数',
  `order_count` int(0) NULL DEFAULT NULL COMMENT '下单数',
  `url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `note` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '首页轮播广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_home_advertise
-- ----------------------------
INSERT INTO `sms_home_advertise` VALUES (2, '夏季大热促销', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '2018-11-01 14:01:37', '2018-11-15 14:01:37', 1, 0, 0, NULL, '夏季大热促销', 0);
INSERT INTO `sms_home_advertise` VALUES (3, '夏季大热促销1', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '2018-11-13 14:01:37', '2018-11-13 14:01:37', 0, 0, 0, NULL, '夏季大热促销1', 0);
INSERT INTO `sms_home_advertise` VALUES (4, '夏季大热促销2', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '2018-11-13 14:01:37', '2018-11-13 14:01:37', 1, 0, 0, NULL, '夏季大热促销2', 0);
INSERT INTO `sms_home_advertise` VALUES (9, '电影推荐广告', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20181113/movie_ad.jpg', '2018-11-01 00:00:00', '2018-11-24 00:00:00', 1, 0, 0, 'www.baidu.com', '电影推荐广告', 100);
INSERT INTO `sms_home_advertise` VALUES (10, '汽车促销广告', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20181113/car_ad.jpg', '2018-11-13 00:00:00', '2018-11-24 00:00:00', 1, 0, 0, 'xxx', NULL, 99);
INSERT INTO `sms_home_advertise` VALUES (11, '汽车推荐广告', 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20181113/car_ad2.jpg', '2018-11-13 00:00:00', '2018-11-30 00:00:00', 1, 0, 0, 'xxx', NULL, 98);

-- ----------------------------
-- Table structure for sms_home_brand
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_brand`;
CREATE TABLE `sms_home_brand`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页推荐品牌id',
  `brand_id` bigint(0) NULL DEFAULT NULL COMMENT '品牌id',
  `brand_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `recommend_status` int(0) NULL DEFAULT NULL COMMENT '推荐状态',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '首页推荐品牌表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_home_brand
-- ----------------------------
INSERT INTO `sms_home_brand` VALUES (1, 1, '万和', 1, 200);
INSERT INTO `sms_home_brand` VALUES (2, 2, '三星', 1, 0);
INSERT INTO `sms_home_brand` VALUES (6, 6, '小米', 1, 300);
INSERT INTO `sms_home_brand` VALUES (8, 5, '方太', 1, 100);
INSERT INTO `sms_home_brand` VALUES (31, 49, '七匹狼', 0, 0);
INSERT INTO `sms_home_brand` VALUES (32, 50, '海澜之家', 1, 0);
INSERT INTO `sms_home_brand` VALUES (33, 51, '苹果', 1, 0);
INSERT INTO `sms_home_brand` VALUES (34, 2, '三星', 1, 0);
INSERT INTO `sms_home_brand` VALUES (35, 3, '华为', 1, 1);
INSERT INTO `sms_home_brand` VALUES (36, 4, '格力', 1, 2);
INSERT INTO `sms_home_brand` VALUES (37, 5, '方太', 1, 0);
INSERT INTO `sms_home_brand` VALUES (38, 1, '万和', 1, 0);
INSERT INTO `sms_home_brand` VALUES (39, 21, 'OPPO', 1, 0);
INSERT INTO `sms_home_brand` VALUES (40, 1, '万和', 1, 0);
INSERT INTO `sms_home_brand` VALUES (41, 2, '三星', 1, 0);
INSERT INTO `sms_home_brand` VALUES (42, 3, '华为', 1, 0);
INSERT INTO `sms_home_brand` VALUES (43, 4, '格力', 1, 0);
INSERT INTO `sms_home_brand` VALUES (44, 5, '方太', 1, 0);

-- ----------------------------
-- Table structure for sms_home_new_product
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_new_product`;
CREATE TABLE `sms_home_new_product`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '新鲜好物id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `recommend_status` int(0) NULL DEFAULT NULL COMMENT '推荐状态',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '新鲜好物表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_home_new_product
-- ----------------------------
INSERT INTO `sms_home_new_product` VALUES (2, 27, '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 1, 200);
INSERT INTO `sms_home_new_product` VALUES (8, 26, '华为 HUAWEI P20 ', 1, 0);
INSERT INTO `sms_home_new_product` VALUES (9, 27, '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 1, 0);
INSERT INTO `sms_home_new_product` VALUES (10, 28, '小米 红米5A 全网通版 3GB+32GB 香槟金 移动联通电信4G手机 双卡双待', 1, 0);
INSERT INTO `sms_home_new_product` VALUES (11, 29, 'Apple iPhone 8 Plus 64GB 红色特别版 移动联通电信4G手机', 1, 1);
INSERT INTO `sms_home_new_product` VALUES (12, 30, 'HLA海澜之家简约动物印花短袖T恤', 1, 0);
INSERT INTO `sms_home_new_product` VALUES (14, 4, '银色星芒刺绣网纱底裤44', 1, 0);
INSERT INTO `sms_home_new_product` VALUES (16, 59, '444444444444444', 1, 0);

-- ----------------------------
-- Table structure for sms_home_recommend_product
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_recommend_product`;
CREATE TABLE `sms_home_recommend_product`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '人气推荐商品id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `recommend_status` int(0) NULL DEFAULT NULL COMMENT '推荐状态',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '人气推荐商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_home_recommend_product
-- ----------------------------
INSERT INTO `sms_home_recommend_product` VALUES (3, 26, '华为 HUAWEI P20 ', 1, 0);
INSERT INTO `sms_home_recommend_product` VALUES (4, 27, '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 1, 0);
INSERT INTO `sms_home_recommend_product` VALUES (5, 28, '小米 红米5A 全网通版 3GB+32GB 香槟金 移动联通电信4G手机 双卡双待', 1, 0);
INSERT INTO `sms_home_recommend_product` VALUES (6, 29, 'Apple iPhone 8 Plus 64GB 红色特别版 移动联通电信4G手机', 1, 1);
INSERT INTO `sms_home_recommend_product` VALUES (7, 30, 'HLA海澜之家简约动物印花短袖T恤', 0, 100);

-- ----------------------------
-- Table structure for sms_home_recommend_subject
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_recommend_subject`;
CREATE TABLE `sms_home_recommend_subject`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页推荐专题id',
  `subject_id` bigint(0) NULL DEFAULT NULL COMMENT '专题id',
  `subject_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专题名称',
  `recommend_status` int(0) NULL DEFAULT NULL COMMENT '推荐状态',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '首页推荐专题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_home_recommend_subject
-- ----------------------------
INSERT INTO `sms_home_recommend_subject` VALUES (14, 1, 'polo衬衫的也时尚', 1, 0);
INSERT INTO `sms_home_recommend_subject` VALUES (15, 2, '大牌手机低价秒', 1, 0);
INSERT INTO `sms_home_recommend_subject` VALUES (16, 3, '晓龙845新品上市', 1, 0);
INSERT INTO `sms_home_recommend_subject` VALUES (17, 4, '夏天应该穿什么', 1, 0);
INSERT INTO `sms_home_recommend_subject` VALUES (18, 5, '夏季精选', 0, 100);
INSERT INTO `sms_home_recommend_subject` VALUES (19, 6, '品牌手机降价', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
