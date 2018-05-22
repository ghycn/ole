/*
 Navicat Premium Data Transfer

 Source Server         : 123.57.156.171
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : 123.57.156.171:3306
 Source Schema         : myole

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 13/09/2017 10:03:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `ACTIVITY_CODE` varchar(255) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`ACTIVITY_CODE`),
  KEY `FKCBF1E30FAF7E2D75` (`ACTIVITY_CODE`),
  KEY `FKCBF1E30FEAF737FF` (`ACTIVITY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES ('000', 'Olé 對其他公司公關費');
INSERT INTO `activity` VALUES ('001', 'Olé 日常雜支（文具、茶水、無案件依據的快遞）');
INSERT INTO `activity` VALUES ('002', 'Olé 電子設備支出');
INSERT INTO `activity` VALUES ('003', 'Olé 員工聚餐費');
INSERT INTO `activity` VALUES ('004', 'Olé 員工旅遊');
INSERT INTO `activity` VALUES ('005', 'Olé 辦公室相關費用（租金、裝潢、物業、家具、網路、水電）');
INSERT INTO `activity` VALUES ('006', 'Olé 會計費用');
INSERT INTO `activity` VALUES ('007', 'Olé 法務費用');
INSERT INTO `activity` VALUES ('008', 'Olé 車費（停車費、打車費）');
INSERT INTO `activity` VALUES ('009', '------');
INSERT INTO `activity` VALUES ('010', '-------');
INSERT INTO `activity` VALUES ('100', '設計');
INSERT INTO `activity` VALUES ('101', 'Big Idea');
INSERT INTO `activity` VALUES ('102', '記者發佈會');
INSERT INTO `activity` VALUES ('103', '首映會');
INSERT INTO `activity` VALUES ('104', '看片會');
INSERT INTO `activity` VALUES ('105', '落地活動');
INSERT INTO `activity` VALUES ('106', '衍伸品製作');
INSERT INTO `activity` VALUES ('107', '紙類印刷費');
INSERT INTO `activity` VALUES ('108', '陳列搭建製作');
INSERT INTO `activity` VALUES ('109', '視頻廣告製作');
INSERT INTO `activity` VALUES ('110', '網路數位廣告和軟件製作');
INSERT INTO `activity` VALUES ('111', '商務合作');
COMMIT;

-- ----------------------------
-- Table structure for bad_account
-- ----------------------------
DROP TABLE IF EXISTS `bad_account`;
CREATE TABLE `bad_account` (
  `BAD_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '坏账ID',
  `REASON` varchar(255) NOT NULL COMMENT '坏账原因',
  `TOTAL` double(10,2) DEFAULT NULL COMMENT '金额',
  `PEOPLE` varchar(255) DEFAULT NULL COMMENT '填写人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `STATUS` int(11) DEFAULT NULL COMMENT '审批状态',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `projectCode` varchar(255) DEFAULT NULL COMMENT '案件编号',
  `QUOTATION_ID` int(11) DEFAULT NULL,
  `APPLICANT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BAD_ID`),
  KEY `FK3CF807B3B549ECDE` (`projectCode`),
  KEY `FK3CF807B3BEA5AFDB` (`QUOTATION_ID`),
  KEY `FK3CF807B3D48DBD1B` (`APPLICANT`),
  CONSTRAINT `FK3CF807B3B549ECDE` FOREIGN KEY (`projectCode`) REFERENCES `pr_project` (`PROJECT_CODE`),
  CONSTRAINT `FK3CF807B3BEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`),
  CONSTRAINT `FK3CF807B3D48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `BORROW_CODE` int(11) NOT NULL AUTO_INCREMENT COMMENT '借用编号',
  `BORROW_TIME` date DEFAULT NULL COMMENT '借出时间',
  `RETURN_TIME` date DEFAULT NULL COMMENT '归还时间',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1归还, 0未归还)',
  `APPLY_TIME` date DEFAULT NULL COMMENT '申请时间',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `LENDNUM` int(11) DEFAULT NULL COMMENT '申请数量',
  `APPLICANT` varchar(255) DEFAULT NULL COMMENT '申请人',
  `DEVICE_ID` int(11) DEFAULT NULL COMMENT '设备编号',
  PRIMARY KEY (`BORROW_CODE`),
  KEY `FK751F9DF5B7E15BF9` (`DEVICE_ID`),
  KEY `FK751F9DF5D48DBD1B` (`APPLICANT`),
  CONSTRAINT `FK751F9DF5B7E15BF9` FOREIGN KEY (`DEVICE_ID`) REFERENCES `device` (`DEVICE_CODE`),
  CONSTRAINT `FK751F9DF5D48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of borrow
-- ----------------------------
BEGIN;
INSERT INTO `borrow` VALUES (1, '2016-04-25', '2016-04-25', 0, '2016-04-25', NULL, 1, '1026', 5);
COMMIT;

-- ----------------------------
-- Table structure for collect_money
-- ----------------------------
DROP TABLE IF EXISTS `collect_money`;
CREATE TABLE `collect_money` (
  `COLLECT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '收款编号',
  `PROJECT_CODE` varchar(100) DEFAULT NULL COMMENT '项目编号',
  `INVOICE` int(1) DEFAULT NULL COMMENT '是否开具发票',
  `AMOUNT` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `DATE` date DEFAULT NULL COMMENT '日期',
  `TO_ACCOUNT` int(1) DEFAULT NULL COMMENT '是否到帐',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `QUOTATION_ID` int(11) DEFAULT NULL,
  `APPLICANT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`COLLECT_ID`),
  KEY `FK_Reference_31` (`PROJECT_CODE`),
  KEY `FKAE3A092BBEA5AFDB` (`QUOTATION_ID`),
  KEY `FKAE3A092BD48DBD1B` (`APPLICANT`),
  CONSTRAINT `FKAE3A092BBEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`),
  CONSTRAINT `FKAE3A092BD48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`),
  CONSTRAINT `FK_Reference_31` FOREIGN KEY (`PROJECT_CODE`) REFERENCES `pr_project` (`PROJECT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_analysis
-- ----------------------------
DROP TABLE IF EXISTS `cost_analysis`;
CREATE TABLE `cost_analysis` (
  `COST_ANALYSIS_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '成本分析单ID',
  `PROJECT_CODE` varchar(100) NOT NULL COMMENT '项目编号',
  `QUOTATION_ID` int(11) NOT NULL COMMENT '报价ID',
  `TOTAL_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `TOTAL_COST` decimal(10,2) DEFAULT NULL COMMENT '总成本',
  `GROSS_PROFIT` decimal(10,2) DEFAULT NULL COMMENT '毛利',
  `PROFIT_RATE` varchar(20) DEFAULT NULL COMMENT '利润比',
  `NO_TAX_QUOTATION` decimal(10,2) DEFAULT NULL COMMENT '未含税报价',
  `TAX_QUOTATION` decimal(10,2) DEFAULT NULL COMMENT '含税报价',
  `OUT_COST` decimal(10,2) DEFAULT NULL COMMENT '外部成本',
  `IN_COST` decimal(10,2) DEFAULT NULL COMMENT '内部成本',
  `INVOICE_TAX` decimal(10,2) DEFAULT NULL COMMENT '发票税',
  `ENTERPRISE_TAX` decimal(10,2) DEFAULT NULL COMMENT '企业税',
  `DESIGN_DAYS` int(11) DEFAULT NULL COMMENT '设计费天数',
  `DESIGN_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '设计费用',
  `ACTIVE_SUPPORT_DAYS` int(11) DEFAULT NULL COMMENT '活动支援天数',
  `ACTIVE_SUPPORT_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '活动支援费用',
  `OTHER_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '杂支费用',
  `RELATION_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '公关费用',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(审批)',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `CONTRACT_NO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`COST_ANALYSIS_ID`),
  KEY `FK3157230EBEA5AFDB` (`QUOTATION_ID`),
  KEY `FK3157230E25414BAB` (`PROJECT_CODE`),
  CONSTRAINT `FK3157230E25414BAB` FOREIGN KEY (`PROJECT_CODE`) REFERENCES `pr_project` (`PROJECT_CODE`),
  CONSTRAINT `FK3157230EBEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='成本分析单';

-- ----------------------------
-- Records of cost_analysis
-- ----------------------------
BEGIN;
INSERT INTO `cost_analysis` VALUES (1, '20168195-80002', 63, NULL, 985.80, 491.84, '0.33%', 1394.00, 1477.64, 985.80, 0.00, 83.64, 102.05, NULL, NULL, NULL, NULL, NULL, NULL, 4, NULL, '');
INSERT INTO `cost_analysis` VALUES (2, '20178203-80013', 71, NULL, 282415.00, 327761.28, '0.54%', 575638.00, 610176.28, 282402.00, 13.00, 34538.28, 73305.75, NULL, NULL, NULL, NULL, NULL, NULL, 4, NULL, '');
INSERT INTO `cost_analysis` VALUES (3, '20178201-80009', 68, NULL, 0.00, 8480.00, '1.00%', 8000.00, 8480.00, 0.00, 0.00, 480.00, 2000.00, NULL, NULL, NULL, NULL, NULL, NULL, 4, NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for cost_analysis_item
-- ----------------------------
DROP TABLE IF EXISTS `cost_analysis_item`;
CREATE TABLE `cost_analysis_item` (
  `COST_ANALYSIS_ITEM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '成本分析单子项ID',
  `COST_ANALYSIS_ID` int(11) NOT NULL COMMENT '成本分析单ID',
  `QUOTATION_LIST_ID` int(30) NOT NULL COMMENT '报价项ID',
  `TAX_QUOTATION` decimal(10,2) DEFAULT NULL COMMENT '含税报价',
  PRIMARY KEY (`COST_ANALYSIS_ITEM_ID`),
  KEY `FKE3E7C1A41369C474` (`COST_ANALYSIS_ID`),
  KEY `FKE3E7C1A43A7A9A14` (`QUOTATION_LIST_ID`),
  CONSTRAINT `FKE3E7C1A41369C474` FOREIGN KEY (`COST_ANALYSIS_ID`) REFERENCES `cost_analysis` (`COST_ANALYSIS_ID`),
  CONSTRAINT `FKE3E7C1A43A7A9A14` FOREIGN KEY (`QUOTATION_LIST_ID`) REFERENCES `quotation_list` (`QUOTATION_LIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成本分析单子项';

-- ----------------------------
-- Table structure for cost_analysis_item_fee
-- ----------------------------
DROP TABLE IF EXISTS `cost_analysis_item_fee`;
CREATE TABLE `cost_analysis_item_fee` (
  `COST_ANALYSIS_ITEM_FEE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '成本分析单子项请款',
  `COST_ANALYSIS_ITEM_ID` int(11) NOT NULL COMMENT '成本分析单子项ID',
  `PROJECT_FEE_ID` int(11) NOT NULL COMMENT '项目款ID',
  PRIMARY KEY (`COST_ANALYSIS_ITEM_FEE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成本分析单子项请款';

-- ----------------------------
-- Table structure for cost_list
-- ----------------------------
DROP TABLE IF EXISTS `cost_list`;
CREATE TABLE `cost_list` (
  `COST_LIST_CODE` varchar(255) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL COMMENT '案件名称',
  `EXETIME` datetime DEFAULT NULL COMMENT '案件执行时间',
  `TOTAL` double DEFAULT NULL COMMENT '总金额',
  `PERSONLIABLE` varchar(255) DEFAULT NULL COMMENT '案件主负责人',
  `SECOND_PERSON` varchar(255) DEFAULT NULL COMMENT '案件次负责人',
  `INVOICE_TITLE` varchar(255) DEFAULT NULL COMMENT '对方发票抬头',
  `INVOICE_ADDRESS` varchar(255) DEFAULT NULL COMMENT '对方公司地址',
  `DOCKING_NAME` varchar(255) DEFAULT NULL COMMENT '对接人姓名',
  `DOCKING_TEL` varchar(255) DEFAULT NULL COMMENT '对接人电话',
  PRIMARY KEY (`COST_LIST_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_list_item
-- ----------------------------
DROP TABLE IF EXISTS `cost_list_item`;
CREATE TABLE `cost_list_item` (
  `ITEM_CODE` varchar(255) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL COMMENT '报价项目名',
  `QUOTATION` double DEFAULT NULL COMMENT '报价',
  `PRODUC_FACTORY` varchar(255) DEFAULT NULL COMMENT '制作厂家',
  `PERSON` varchar(255) DEFAULT NULL COMMENT '对接人',
  `TAX_QUOTATION` double DEFAULT NULL COMMENT '含税报价',
  PRIMARY KEY (`ITEM_CODE`),
  KEY `FK173F3C22C5DE13E3` (`ITEM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `CUSTOMER_CODE` varchar(50) NOT NULL DEFAULT '80001' COMMENT '客户编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '分类',
  `ADDRESS` varchar(500) DEFAULT NULL COMMENT '地址',
  `OFFICIAL_WEBSITE` varchar(255) DEFAULT NULL COMMENT '官网',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `REMOVE` bit(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`CUSTOMER_CODE`),
  KEY `FK52C76FDED74EA4F1` (`CUSTOMER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户';

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES ('80001', '二十世纪福斯（北京）咨询有限公司', '', '電影', '北京东城区东直门来福士写字楼2006', 'http://www.foxmovies.com', 1, b'1');
INSERT INTO `customer` VALUES ('80002', '爱麦克斯（上海）多媒体技术有限公司', '', '電影', '北京市朝阳区朝阳门丰联广场写字楼A502', 'https://www.imax.com', 1, b'1');
INSERT INTO `customer` VALUES ('80003', '北京奥乐库营销策划有限公司', '', '奧樂', '北京市朝阳区东大桥路9号侨福芳草地写字楼a座1543室', 'www.ole-ad.com', 1, b'1');
INSERT INTO `customer` VALUES ('80004', '暫時性使用', '', '暫時', '', '', 1, b'1');
INSERT INTO `customer` VALUES ('80005', '锐地（上海）三维设备租赁有限公司', '', '電影', '北京市朝阳区东三环北路8号亮马河大厦1座1611', 'http://www.reald.com', 1, b'1');
INSERT INTO `customer` VALUES ('80006', '郑州市奥斯卡升龙国际影城有限公司 ', '账号：920730120102027933  \r\n开户行：郑州银行政通路支行', '電影', '河南省郑州市二七区大学路与政通路升龙国际中心A区2楼奥斯卡升龙国际影城', '', 1, b'1');
INSERT INTO `customer` VALUES ('80007', '北京十方宇拓文化传媒有限公司', '收款人账户：410100171160423\r\n收款行名称：锦州银行北京国贸支行', '媒体', '北京市朝阳区工体北路21号楼（永利国际）2单元526', '', 1, b'1');
INSERT INTO `customer` VALUES ('80008', '北京华数达科技有限公司', '华鼎天下\r\n数达九州', '品牌', '北京上地', 'https://www.huashuda.com', 1, b'1');
INSERT INTO `customer` VALUES ('80009', 'Universal Pictures (Singapore) Holdings Pte. Ltd.', '公司在北京，请款都用新加坡分公司的帐户', '電影', '北京', '', 2, b'0');
INSERT INTO `customer` VALUES ('80010', 'Universal Pictures (Singapore) Holdings Pte. Ltd.', '', '電影', '北京市朝阳区天泽路16号院润世中心2号楼1705室', '', 1, b'1');
INSERT INTO `customer` VALUES ('80011', '北京创世星国际影视文化有限公司', '', '電影', '北京市朝阳区北苑路170号凯旋中心1601室', '', 1, b'1');
INSERT INTO `customer` VALUES ('80012', '华联大时代（北京）营销顾问有限公司', '', '電影', '北京市朝阳区八里庄西里61号，远洋商务楼1003室', '', 1, b'1');
INSERT INTO `customer` VALUES ('80013', 'Sony Pictures Television (HK) Limited', '', '電影', '香港', '', 1, b'1');
COMMIT;

-- ----------------------------
-- Table structure for customer_contact
-- ----------------------------
DROP TABLE IF EXISTS `customer_contact`;
CREATE TABLE `customer_contact` (
  `CUSTOMER_CONTACT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户联络ID',
  `NAME` varchar(255) NOT NULL COMMENT '联络人名称',
  `TEL` varchar(255) DEFAULT NULL COMMENT '座机',
  `MOBILE` varchar(255) DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `POST_CODE` varchar(255) DEFAULT NULL COMMENT '邮编',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CUSTOMER_CODE` varchar(255) DEFAULT NULL COMMENT '客户编号',
  `EXTENSION` varchar(255) DEFAULT NULL COMMENT '分机',
  PRIMARY KEY (`CUSTOMER_CONTACT_ID`),
  KEY `FKCB53089F32DAFCB` (`CUSTOMER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_contact
-- ----------------------------
BEGIN;
INSERT INTO `customer_contact` VALUES (1, 'Sunnie(申慧）', '010-59776187', '13910397658', 'Sunnie.Shen@fox.com', NULL, NULL, '2015-07-30 10:07:04', '80001', '1');
INSERT INTO `customer_contact` VALUES (2, '杨梦瑶', '010-57996181', '18610068591', 'mengyao.yang@fox.com', NULL, NULL, '2015-07-30 10:07:04', '80001', '2');
INSERT INTO `customer_contact` VALUES (3, 'Tiger（梅养清）', '010-59776186', '18611143714', 'Tiger.Mei@fox.com', NULL, NULL, '2015-07-30 10:07:04', '80001', '3');
INSERT INTO `customer_contact` VALUES (4, '司拓', '021-23157163', '18610295656', 'tsi@imax.com', NULL, NULL, '2015-06-25 15:33:33', '80002', NULL);
INSERT INTO `customer_contact` VALUES (5, 'Sabrina（毕婷）', '021-23157163', '13718648560', 'sbi@imax.com', NULL, NULL, '2015-06-25 15:33:33', '80002', NULL);
INSERT INTO `customer_contact` VALUES (6, '凯南', '021-23157163', '18601040234', 'kainans@outlook.com', NULL, NULL, '2015-06-25 15:33:33', '80002', NULL);
INSERT INTO `customer_contact` VALUES (7, 'Kyle（曹增恺）', '021-23157000', '13918531972', 'ktsao@imax.com', NULL, NULL, '2015-06-25 15:33:33', '80002', NULL);
INSERT INTO `customer_contact` VALUES (8, '陳立展', '010-57306063', '18601044109', 'lee@ole-ad.com', NULL, NULL, '2015-06-29 19:10:46', '80003', NULL);
INSERT INTO `customer_contact` VALUES (9, '俞圣伦', '010-65906386', '13911519673', 'syu@reald.com', NULL, NULL, '2015-07-01 14:41:02', '80005', NULL);
INSERT INTO `customer_contact` VALUES (10, 'Sophia（崔子扬）', '010-65906386', '18811037101', 'scui@reald.com', NULL, NULL, '2015-07-01 14:41:02', '80005', NULL);
INSERT INTO `customer_contact` VALUES (11, 'Leon（李昂）', '010-65906386', '18610472419', '568690774@qq.com', NULL, NULL, '2015-07-01 14:41:02', '80005', NULL);
INSERT INTO `customer_contact` VALUES (22, '陈玉婷', '0371-68866538', '18625533214', '371459880@qq.com', NULL, NULL, '2015-07-09 11:40:05', '80006', NULL);
INSERT INTO `customer_contact` VALUES (23, '张敏', '010-57490469', '18500186894', 'zhangm@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (24, '王燕', '010-57490469', '18500609866', 'wangyan@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (25, 'Tim（田鹏）', '010-57490469', '15010279256', 'tim@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (26, '杜黎', '010-57490469', '18614078225', 'duli@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (27, 'Alice（李雪菲） ', '010-57490469', '18519017551', 'lixuefei@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (28, 'Alice（李雪菲）', '010-57490469', '15827638881', 'lixuefei@chinacrossmedia.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (29, '张琦', '010-57490469', '13810293614', 'qzhangqi@foxmail.com', NULL, NULL, '2015-07-09 11:47:55', '80007', NULL);
INSERT INTO `customer_contact` VALUES (30, 'Ada', ' 010- 61840450 ', '13611380056', 'ada.zhao@nbcuni.com', NULL, NULL, '2015-10-19 18:25:52', '80010', '0000');
INSERT INTO `customer_contact` VALUES (31, '何巍', '010-5927-3022', '18911061086', '26294061@qq.com', NULL, NULL, '2015-12-03 17:36:23', '80011', '');
INSERT INTO `customer_contact` VALUES (32, '陈威宏', '10-59519545', '15618689870', 'garyoppa@foxmail.com', NULL, NULL, '2016-01-27 11:49:41', '80012', '');
COMMIT;

-- ----------------------------
-- Table structure for customer_type
-- ----------------------------
DROP TABLE IF EXISTS `customer_type`;
CREATE TABLE `customer_type` (
  `TYPE_ID` int(255) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称',
  `REMOVE` bit(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_type
-- ----------------------------
BEGIN;
INSERT INTO `customer_type` VALUES (1, '電影', b'1');
INSERT INTO `customer_type` VALUES (2, '商場', b'1');
INSERT INTO `customer_type` VALUES (3, '品牌', b'1');
INSERT INTO `customer_type` VALUES (4, '奧樂', b'1');
INSERT INTO `customer_type` VALUES (5, '暫時', b'1');
INSERT INTO `customer_type` VALUES (6, '媒体', b'1');
COMMIT;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `DEVICE_CODE` int(255) NOT NULL AUTO_INCREMENT COMMENT '设备编号',
  `NAME` varchar(255) NOT NULL COMMENT '名称',
  `TOTAL` int(11) DEFAULT NULL COMMENT '总数量',
  `STAFFHOLD_NUM` int(11) DEFAULT NULL COMMENT '员工持有数量',
  `STOCK_NUM` int(11) DEFAULT NULL COMMENT '库存数量',
  `REMOVE` bit(1) DEFAULT NULL COMMENT '删除状态',
  `BUYING_TIME` date DEFAULT NULL COMMENT '购买时间',
  `EXPIRATION_DATE` date DEFAULT NULL COMMENT '到期日期',
  PRIMARY KEY (`DEVICE_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
BEGIN;
INSERT INTO `device` VALUES (4, 'MacPro 13寸 20160401', 1, 0, 1, b'0', '2016-04-20', '2019-04-20');
INSERT INTO `device` VALUES (5, 'MacPro 13寸 20160301', 1, 0, 1, b'0', '2016-03-01', '2019-03-01');
INSERT INTO `device` VALUES (6, 'MP13 20140501', 2, 0, 2, b'0', '2016-04-01', '2019-04-01');
INSERT INTO `device` VALUES (7, 'iMAC 20140501', 2, 0, 2, b'1', '2014-05-01', '2017-05-01');
INSERT INTO `device` VALUES (8, 'Mac book pro 13 20140501', 2, 0, 2, b'1', '2014-05-01', '2017-05-01');
INSERT INTO `device` VALUES (9, 'Mac book pro 15 20140501', 1, 0, 1, b'1', '2014-05-01', '2017-05-01');
INSERT INTO `device` VALUES (10, 'Mac book pro 13 20150301', 1, 0, 1, b'1', '2015-03-01', '2018-03-01');
INSERT INTO `device` VALUES (11, 'Mac book pro 13 20150501', 1, 0, 1, b'1', '2015-05-01', '2018-05-01');
INSERT INTO `device` VALUES (12, 'Mac book pro 13 20160501', 2, 0, 2, b'1', '2016-05-01', '2019-05-01');
INSERT INTO `device` VALUES (13, 'Mac book pro 13 20160301', 1, 0, 1, b'1', '2016-03-01', '2019-03-01');
INSERT INTO `device` VALUES (14, 'Mac book pro 13 20160701', 1, 0, 1, b'1', '2016-06-01', '2019-06-01');
INSERT INTO `device` VALUES (15, 'Mac book pro 13 20160401', 1, 0, 1, b'1', '2016-04-01', '2019-04-01');
INSERT INTO `device` VALUES (16, 'iMac 20160701', 1, 0, 1, b'1', '2016-07-01', '2019-07-01');
INSERT INTO `device` VALUES (17, 'Mac book pro 13 20160801', 1, 0, 1, b'1', '2016-08-01', '2019-08-01');
INSERT INTO `device` VALUES (18, 'Mac book pro 13 20160901', 6, 0, 6, b'1', '2016-09-01', '2019-09-01');
INSERT INTO `device` VALUES (19, 'AC PC Notebook', 1, 0, 1, b'1', '2016-01-01', '2019-01-01');
INSERT INTO `device` VALUES (20, '工讀生 PC NOTE BOOK', 1, 0, 1, b'1', '2016-11-01', '2019-11-01');
COMMIT;

-- ----------------------------
-- Table structure for other_pf_project_fee
-- ----------------------------
DROP TABLE IF EXISTS `other_pf_project_fee`;
CREATE TABLE `other_pf_project_fee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `otherTypeId` int(11) DEFAULT NULL,
  `PF_PROJECT_FEE_ID` int(11) DEFAULT NULL,
  `QUOTATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK87404186CE6CCCA5` (`PF_PROJECT_FEE_ID`),
  KEY `FK87404186BEA5AFDB` (`QUOTATION_ID`),
  CONSTRAINT `FK87404186BEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`),
  CONSTRAINT `FK87404186CE6CCCA5` FOREIGN KEY (`PF_PROJECT_FEE_ID`) REFERENCES `pf_project_fee` (`PROJECT_FEE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for other_pf_project_fee_type
-- ----------------------------
DROP TABLE IF EXISTS `other_pf_project_fee_type`;
CREATE TABLE `other_pf_project_fee_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型编号',
  `name` varchar(255) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other_pf_project_fee_type
-- ----------------------------
BEGIN;
INSERT INTO `other_pf_project_fee_type` VALUES (1, '设计费');
INSERT INTO `other_pf_project_fee_type` VALUES (2, '活动支援');
INSERT INTO `other_pf_project_fee_type` VALUES (3, '交通费');
INSERT INTO `other_pf_project_fee_type` VALUES (4, '住宿');
INSERT INTO `other_pf_project_fee_type` VALUES (5, '餐费');
INSERT INTO `other_pf_project_fee_type` VALUES (6, '公关');
INSERT INTO `other_pf_project_fee_type` VALUES (7, '杂支');
COMMIT;

-- ----------------------------
-- Table structure for pf_project_fee
-- ----------------------------
DROP TABLE IF EXISTS `pf_project_fee`;
CREATE TABLE `pf_project_fee` (
  `PROJECT_FEE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目款编号',
  `NAME` varchar(255) NOT NULL COMMENT '名称',
  `AMOUNT` double DEFAULT NULL COMMENT '金额',
  `APPLY_DATE` datetime NOT NULL COMMENT '申请日期',
  `PAY_STATUS` int(11) DEFAULT NULL COMMENT '付款状态',
  `STATUS` int(11) NOT NULL COMMENT '状态(0已申请,1已批准,2已关账)',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `PROJECT_CODE` varchar(255) DEFAULT NULL,
  `SUPPLIER_CODE` varchar(255) DEFAULT NULL,
  `APPLICANT` varchar(255) DEFAULT NULL,
  `CREATE_STAFF` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL COMMENT '类别',
  `QUOTATION_ID` int(11) DEFAULT NULL,
  `TYPE_ID` int(11) DEFAULT NULL COMMENT '其它类型编号',
  `TYPE_NAME` varchar(255) DEFAULT NULL COMMENT '其它类型名称',
  PRIMARY KEY (`PROJECT_FEE_ID`),
  KEY `FKF0711857C117B7F6` (`CREATE_STAFF`),
  KEY `FKF071185725414BAB` (`PROJECT_CODE`),
  KEY `FKF0711857D48DBD1B` (`APPLICANT`),
  KEY `FKF0711857BEA5AFDB` (`QUOTATION_ID`),
  CONSTRAINT `FKF071185725414BAB` FOREIGN KEY (`PROJECT_CODE`) REFERENCES `pr_project` (`PROJECT_CODE`),
  CONSTRAINT `FKF0711857BEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`),
  CONSTRAINT `FKF0711857C117B7F6` FOREIGN KEY (`CREATE_STAFF`) REFERENCES `sys_user` (`USER_CODE`),
  CONSTRAINT `FKF0711857D48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pf_project_fee
-- ----------------------------
BEGIN;
INSERT INTO `pf_project_fee` VALUES (4, '', 985.8, '2016-09-20 00:00:00', NULL, 1, '', '2016-09-20 16:21:58', '20168195-80002', '4', '1038', '1038', NULL, 63, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (5, '《异形》伦敦出差机场车资 ', 176, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:08:18', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (6, '《异形》玉笛厂商专车直送车资', 300, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:10:28', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (7, '《异形》带Vicky 谷大 上伦敦眼 ', 627, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:11:14', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (8, '《异形》伦敦行程 纪念品送客户', 720, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:11:59', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (9, '请客户颐堤港吃饭', 262, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:13:33', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (10, '伦敦请客户吃饭', 4651, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:14:17', '20178196-80001', '23', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (11, '表框', 404, '2017-05-24 00:00:00', NULL, 1, '', '2017-05-24 18:16:00', '20178196-80001', '22', '1038', '1038', NULL, 64, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (12, '戲院製作物', 428800, '2017-06-23 00:00:00', NULL, 2, '', '2017-06-23 15:44:37', '20178201-80009', '5', '1038', '1038', NULL, 66, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (13, '', 428800, '2017-06-23 00:00:00', NULL, 1, '', '2017-06-23 16:24:01', '20178202-80009', '5', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (14, '', 4500, '2017-06-23 00:00:00', NULL, 1, '', '2017-06-23 16:25:58', '20178202-80009', '29', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (15, '活动支援', 1200, '2017-06-23 16:26:34', NULL, 1, NULL, '2017-06-23 16:26:34', '20178202-80009', NULL, '1038', '1038', NULL, 70, 2, '活动支援');
INSERT INTO `pf_project_fee` VALUES (16, '设计费', 2000, '2017-06-23 16:26:50', NULL, 1, NULL, '2017-06-23 16:26:50', '20178202-80009', NULL, '1038', '1038', NULL, 70, 1, '设计费');
INSERT INTO `pf_project_fee` VALUES (17, '《蓝精灵》人偶巡演活动预付款', 120000, '2017-06-23 00:00:00', NULL, 1, '', '2017-06-23 16:48:59', '20178203-80013', '31', '1038', '1038', NULL, 71, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (18, '《蓝精灵》人偶巡演 尾款', 159802, '2017-06-23 00:00:00', NULL, 1, '', '2017-06-23 16:49:32', '20178203-80013', '30', '1038', '1038', NULL, 71, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (19, '《蓝精灵》人偶巡演开发票', 2600, '2017-06-23 00:00:00', NULL, 1, '', '2017-06-23 16:53:10', '20178203-80013', '32', '1038', '1038', NULL, 71, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (20, '杂支', 13, '2017-06-23 16:53:27', NULL, 1, NULL, '2017-06-23 16:53:27', '20178203-80013', NULL, '1038', '1038', NULL, 71, 7, '杂支');
INSERT INTO `pf_project_fee` VALUES (21, '', 749, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 15:40:34', '20178202-80009', '23', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (22, '', 5348.59, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 15:41:23', '20178202-80009', '33', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (23, '', 107, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 15:41:44', '20178202-80009', '34', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (24, '杂支', 10827.27, '2017-06-28 15:42:51', NULL, 1, NULL, '2017-06-28 15:42:51', '20178202-80009', NULL, '1038', '1038', NULL, 70, 7, '杂支');
INSERT INTO `pf_project_fee` VALUES (25, '', 671954, '2017-06-28 00:00:00', NULL, 2, '', '2017-06-28 16:21:46', '20178204-80009', '35', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (26, '', 15023.26, '2017-06-28 00:00:00', NULL, 2, '', '2017-06-28 16:22:33', '20178204-80009', '27', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (27, '影片剪辑', 21500, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:31:18', '20178204-80009', '36', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (28, 'Mallevent建模', 12000, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:32:00', '20178204-80009', '37', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (29, '戲院製作物', 671954, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:37:46', '20178204-80009', '35', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (30, '媒體版位製作', 15023.26, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:38:28', '20178204-80009', '27', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (31, '藍寶堅尼保險費', 1000, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:39:43', '20178204-80009', '38', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (32, '主持人费用', 55000, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:41:59', '20178204-80009', '39', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (33, '妆发费用', 30000, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:45:12', '20178204-80009', '40', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (34, '包车费用', 54610, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:47:56', '20178204-80009', '41', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (35, '記者會背板搭建', 34000, '2017-06-28 00:00:00', NULL, 1, '', '2017-06-28 16:51:01', '20178204-80009', '42', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (36, '设计费', 2000, '2017-06-28 16:52:45', NULL, 1, NULL, '2017-06-28 16:52:45', '20178204-80009', NULL, '1038', '1038', NULL, 73, 1, '设计费');
INSERT INTO `pf_project_fee` VALUES (37, '活动支援', 300, '2017-06-28 16:52:57', NULL, 1, NULL, '2017-06-28 16:52:57', '20178204-80009', NULL, '1038', '1038', NULL, 73, 2, '活动支援');
INSERT INTO `pf_project_fee` VALUES (38, '交通费', 3842.9, '2017-06-28 16:53:30', NULL, 1, NULL, '2017-06-28 16:53:30', '20178204-80009', NULL, '1038', '1038', NULL, 73, 3, '交通费');
INSERT INTO `pf_project_fee` VALUES (39, '《蓝精灵》百丽宫影城场地费', 27680, '2017-06-30 00:00:00', NULL, 0, '', '2017-06-30 17:04:35', '20178200-80013', '24', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (40, '《蓝精灵》搭建费', 39232, '2017-06-30 00:00:00', NULL, 0, '', '2017-06-30 17:05:20', '20178200-80013', '4', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (41, '《蓝精灵》安保费', 5400, '2017-06-30 00:00:00', NULL, 0, '', '2017-06-30 17:06:02', '20178200-80013', '25', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (42, '《蓝精灵》主持人费用', 5000, '2017-06-30 00:00:00', NULL, 0, '', '2017-06-30 17:06:51', '20178200-80013', '26', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (43, '《蓝精灵》纸袋制作', 1720, '2017-06-30 00:00:00', NULL, 0, '', '2017-06-30 17:07:45', '20178200-80013', '5', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (44, '交通费', 491, '2017-06-30 17:10:38', NULL, 0, NULL, '2017-06-30 17:10:38', '20178200-80013', NULL, '1038', '1038', NULL, 72, 3, '交通费');
INSERT INTO `pf_project_fee` VALUES (45, '设计费', 200, '2017-06-30 17:11:00', NULL, 0, NULL, '2017-06-30 17:11:00', '20178200-80013', NULL, '1038', '1038', NULL, 72, 1, '设计费');
INSERT INTO `pf_project_fee` VALUES (46, '活动支援', 800, '2017-06-30 17:11:10', NULL, 0, NULL, '2017-06-30 17:11:10', '20178200-80013', NULL, '1038', '1038', NULL, 72, 2, '活动支援');
INSERT INTO `pf_project_fee` VALUES (47, '', 15819.35, '2017-07-14 00:00:00', NULL, 1, '', '2017-07-14 15:15:30', '20178202-80009', '27', '1038', '1038', NULL, 70, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (48, '', 34000, '2017-07-14 00:00:00', NULL, 1, '', '2017-07-14 15:36:09', '20178204-80009', '42', '1038', '1038', NULL, 73, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (49, '报批警官劳务费', 1000, '2017-07-14 00:00:00', NULL, 0, '', '2017-07-14 15:45:49', '20178200-80013', '43', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (50, '活动兼职人员', 500, '2017-07-14 00:00:00', NULL, 0, '', '2017-07-14 15:46:33', '20178200-80013', '43', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (51, '活动工作证制作', 741.6, '2017-07-14 00:00:00', NULL, 0, '', '2017-07-14 15:47:22', '20178200-80013', '43', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (52, '活动手卡桌卡制作', 75, '2017-07-14 00:00:00', NULL, 0, '', '2017-07-14 15:47:57', '20178200-80013', '43', '1038', '1038', NULL, 72, NULL, NULL);
INSERT INTO `pf_project_fee` VALUES (53, '小音响采买', 233.7, '2017-07-14 00:00:00', NULL, 0, '', '2017-07-14 15:48:37', '20178200-80013', '43', '1038', '1038', NULL, 72, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pr_project
-- ----------------------------
DROP TABLE IF EXISTS `pr_project`;
CREATE TABLE `pr_project` (
  `PROJECT_CODE` varchar(255) NOT NULL COMMENT '案件编号',
  `CUSTOMER_CODE` varchar(255) DEFAULT NULL COMMENT '客户编号',
  `NAME` varchar(255) NOT NULL COMMENT '案件名称',
  `CUSTOMER_CONTACT_ID` varchar(255) DEFAULT NULL COMMENT '客户联络ID',
  `OPEN_STAFF` varchar(255) DEFAULT NULL COMMENT '开案人',
  `OPEN_TIME` datetime NOT NULL COMMENT '接案时间',
  `CLOSE_TIME` datetime DEFAULT NULL,
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_STAFF` varchar(255) DEFAULT NULL COMMENT '创建日期',
  `UPDATE_STAFF` varchar(255) DEFAULT NULL COMMENT '修改日期',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改日期',
  `EXECUTION_TIME` datetime DEFAULT NULL COMMENT '执行时间',
  `COLLECTION_DAYS` datetime DEFAULT NULL COMMENT '收款日期',
  `INVOICE` int(255) DEFAULT NULL COMMENT '是否开具发票',
  `AMOUNT_RECEIVABLE` varchar(255) DEFAULT NULL COMMENT '收款金额',
  `TO_ACCOUNT` varchar(255) DEFAULT '' COMMENT '是否到账',
  `INVOICE_AMOUNT` double(20,2) DEFAULT NULL COMMENT '发票金额',
  `APPROVAL_OF_PERSONNEL` varchar(255) DEFAULT NULL COMMENT '审批人',
  `APPROVAL_OF_PERSONNEL_NAME` varchar(255) DEFAULT NULL COMMENT '审批人名称',
  `TAX_RATE` int(10) DEFAULT NULL COMMENT '税点',
  PRIMARY KEY (`PROJECT_CODE`),
  KEY `FK79CA65BC2D828203` (`UPDATE_STAFF`),
  KEY `FK79CA65BCC117B7F6` (`CREATE_STAFF`),
  KEY `FK79CA65BC7A548AC4` (`OPEN_STAFF`),
  KEY `FK79CA65BC32DAFCB` (`CUSTOMER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pr_project
-- ----------------------------
BEGIN;
INSERT INTO `pr_project` VALUES ('20168190-80012', '80012', '华联国际电影招商暨启动发布会', NULL, '1006', '2016-01-27 00:00:00', '2016-03-04 00:00:00', 3, '', NULL, NULL, '2016-01-27 14:35:36', '2016-01-27 14:35:36', '2016-03-04 00:00:00', NULL, NULL, NULL, NULL, 161600.00, '1006', '林家暉', 6);
INSERT INTO `pr_project` VALUES ('20168193-80002', '80002', 'IMAX《蝙蝠侠大战超人》零点蹲粉丝观影会', NULL, '1000', '2016-03-21 00:00:00', '2016-03-31 00:00:00', 3, '', NULL, NULL, '2016-03-28 18:12:30', '2016-03-28 18:12:30', '2016-03-25 00:00:00', NULL, NULL, NULL, NULL, 0.00, '1008', '陳立展', 6);
INSERT INTO `pr_project` VALUES ('20168195-80002', '80002', '20160129-103-104', NULL, '1038', '2016-01-28 00:00:00', '2016-01-29 00:00:00', 1, '', NULL, NULL, '2016-09-20 15:14:08', '2016-09-20 15:14:08', '2016-01-28 00:00:00', NULL, NULL, NULL, NULL, 1394.00, '1008', '陳立展', 6);
INSERT INTO `pr_project` VALUES ('20178196-80001', '80001', '20170804-100-104', NULL, '1038', '2017-05-24 00:00:00', '2017-05-24 00:00:00', 1, '', NULL, NULL, '2017-05-24 16:31:27', '2017-05-24 16:31:27', '2017-05-24 00:00:00', NULL, NULL, NULL, NULL, 23000.00, '1008', '陳立展', 6);
INSERT INTO `pr_project` VALUES ('20178200-80013', '80013', '20170407-113-102', NULL, '1038', '2017-03-15 00:00:00', '2017-06-06 00:00:00', 1, '', NULL, NULL, '2017-06-09 11:56:19', '2017-06-09 11:56:19', '2017-04-18 00:00:00', NULL, NULL, NULL, NULL, 160234.00, '1008', '陳立展', 6);
INSERT INTO `pr_project` VALUES ('20178201-80009', '80009', '20170609-102', NULL, '1038', '2017-04-01 00:00:00', '2017-06-23 00:00:00', 3, '', NULL, NULL, '2017-06-23 15:19:46', '2017-06-23 15:19:46', '2017-04-01 00:00:00', NULL, NULL, NULL, NULL, 1412350.00, '1038', '王漢卿', 6);
INSERT INTO `pr_project` VALUES ('20178202-80009', '80009', '20170609-102', NULL, '1038', '2017-04-01 00:00:00', '2017-06-23 00:00:00', 1, '', NULL, NULL, '2017-06-23 16:17:48', '2017-06-23 16:17:48', '2017-06-09 00:00:00', NULL, NULL, NULL, NULL, 1412350.00, '1038', '王漢卿', 6);
INSERT INTO `pr_project` VALUES ('20178203-80013', '80013', '20170407-113-105', NULL, '1038', '2017-03-27 00:00:00', '2017-06-23 00:00:00', 1, '', NULL, NULL, '2017-06-23 16:34:23', '2017-06-23 16:34:23', '2017-05-07 00:00:00', NULL, NULL, NULL, NULL, 575638.00, '1038', '王漢卿', 6);
INSERT INTO `pr_project` VALUES ('20178204-80009', '80009', '20170414-102', NULL, '1038', '2016-10-01 00:00:00', '2017-06-28 00:00:00', 1, '', NULL, NULL, '2017-06-28 16:12:12', '2017-06-28 16:12:12', '2017-02-01 00:00:00', NULL, NULL, NULL, NULL, 1548850.00, '1038', '王漢卿', 6);
COMMIT;

-- ----------------------------
-- Table structure for quotation
-- ----------------------------
DROP TABLE IF EXISTS `quotation`;
CREATE TABLE `quotation` (
  `QUOTATION_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '报价ID',
  `Contact` varchar(255) DEFAULT NULL COMMENT '联系人',
  `TEL` varchar(255) DEFAULT NULL COMMENT '座机',
  `PHONE` varchar(255) DEFAULT NULL COMMENT '电话',
  `CASETIME` datetime DEFAULT NULL COMMENT '案件时间',
  `TAXTOTAL` double(30,0) DEFAULT NULL COMMENT '小计',
  `DATE` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日期',
  `FAX` varchar(255) DEFAULT NULL COMMENT '传真',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `APPLICANT` varchar(255) DEFAULT NULL COMMENT '申请人',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `INVOICE_AMOUNT` double DEFAULT NULL COMMENT '发票金额',
  `customer` varchar(255) DEFAULT NULL,
  `PROJECT_CODE` varchar(255) DEFAULT NULL,
  `ACTIVITY_CODE` varchar(255) DEFAULT NULL COMMENT '活动Code',
  PRIMARY KEY (`QUOTATION_ID`),
  KEY `FK4D89A56CA71C0DFB` (`customer`),
  KEY `FK4D89A56C25414BAB` (`PROJECT_CODE`),
  KEY `FK4D89A56CBE5B066B` (`ACTIVITY_CODE`),
  KEY `FK4D89A56CD48DBD1B` (`APPLICANT`),
  CONSTRAINT `FK4D89A56C25414BAB` FOREIGN KEY (`PROJECT_CODE`) REFERENCES `pr_project` (`PROJECT_CODE`),
  CONSTRAINT `FK4D89A56CA71C0DFB` FOREIGN KEY (`customer`) REFERENCES `customer` (`CUSTOMER_CODE`),
  CONSTRAINT `FK4D89A56CBE5B066B` FOREIGN KEY (`ACTIVITY_CODE`) REFERENCES `activity` (`ACTIVITY_CODE`),
  CONSTRAINT `FK4D89A56CD48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='报价';

-- ----------------------------
-- Records of quotation
-- ----------------------------
BEGIN;
INSERT INTO `quotation` VALUES (60, NULL, NULL, NULL, '2016-01-27 00:00:00', 161600, '2016-01-27 14:36:16', NULL, NULL, '1006', '', 1, NULL, '80012', '20168190-80012', '102');
INSERT INTO `quotation` VALUES (61, NULL, NULL, NULL, '2016-03-21 00:00:00', NULL, '2016-03-28 18:32:36', NULL, NULL, '1000', '', 2, NULL, '80002', '20168193-80002', '000');
INSERT INTO `quotation` VALUES (62, NULL, NULL, NULL, '2016-03-28 00:00:00', NULL, '2016-03-28 18:34:36', NULL, NULL, '1000', '', 2, NULL, '80002', '20168193-80002', '000');
INSERT INTO `quotation` VALUES (63, '4', NULL, NULL, '2016-01-28 00:00:00', 1394, '2016-09-20 15:35:20', NULL, NULL, '1038', '', 1, NULL, '80002', '20168195-80002', '104');
INSERT INTO `quotation` VALUES (64, '2', NULL, NULL, '2017-05-24 00:00:00', 23000, '2017-05-24 17:52:23', NULL, NULL, '1038', '', 1, NULL, '80001', '20178196-80001', '104');
INSERT INTO `quotation` VALUES (65, NULL, NULL, NULL, '2017-04-01 00:00:00', 142200, '2017-06-23 15:23:10', NULL, NULL, '1038', '', 1, NULL, '80009', '20178201-80009', '100');
INSERT INTO `quotation` VALUES (66, NULL, NULL, NULL, '2017-04-01 00:00:00', 1217150, '2017-06-23 15:32:28', NULL, NULL, '1038', '', 1, NULL, '80009', '20178201-80009', '107');
INSERT INTO `quotation` VALUES (67, NULL, NULL, NULL, '2017-04-01 00:00:00', 45000, '2017-06-23 15:33:01', NULL, NULL, '1038', '', 1, NULL, '80009', '20178201-80009', '105');
INSERT INTO `quotation` VALUES (68, NULL, NULL, NULL, '2017-06-23 00:00:00', 8000, '2017-06-23 15:33:42', NULL, NULL, '1038', '', 1, NULL, '80009', '20178201-80009', '105');
INSERT INTO `quotation` VALUES (69, NULL, NULL, NULL, '2017-06-23 00:00:00', NULL, '2017-06-23 16:14:04', NULL, NULL, '1038', '', 1, NULL, '80009', '20178201-80009', '000');
INSERT INTO `quotation` VALUES (70, NULL, NULL, NULL, '2017-04-01 00:00:00', 1412350, '2017-06-23 16:18:49', NULL, NULL, '1038', '', 1, NULL, '80009', '20178202-80009', '107');
INSERT INTO `quotation` VALUES (71, NULL, NULL, NULL, '2017-03-27 00:00:00', 575638, '2017-06-23 16:37:32', NULL, NULL, '1038', '', 1, NULL, '80013', '20178203-80013', '105');
INSERT INTO `quotation` VALUES (72, NULL, NULL, NULL, '2017-03-15 00:00:00', 160234, '2017-06-23 17:13:22', NULL, NULL, '1038', '', 1, NULL, '80013', '20178200-80013', '102');
INSERT INTO `quotation` VALUES (73, NULL, NULL, NULL, '2017-06-28 00:00:00', 1548850, '2017-06-28 16:13:36', NULL, NULL, '1038', '', 1, NULL, '80009', '20178204-80009', '100');
COMMIT;

-- ----------------------------
-- Table structure for quotation_list
-- ----------------------------
DROP TABLE IF EXISTS `quotation_list`;
CREATE TABLE `quotation_list` (
  `QUOTATION_LIST_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '报价项ID',
  `PROJECT_CODE` varchar(255) DEFAULT NULL COMMENT '项目编号',
  `CATEGORY` varchar(255) DEFAULT NULL COMMENT '类别',
  `ITEM` varchar(255) DEFAULT NULL COMMENT '报价项',
  `SIZE` varchar(255) DEFAULT NULL COMMENT '尺寸',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `UNIT_PRICE` double DEFAULT NULL COMMENT '单价',
  `SUB_TOTAL` double DEFAULT NULL COMMENT '小计',
  `SPEC` varchar(255) DEFAULT NULL COMMENT '规格',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `QUOTATION_ID` int(11) DEFAULT NULL COMMENT '报价ID',
  `PARENT` int(11) DEFAULT NULL COMMENT '父级',
  `SUPPLIER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`QUOTATION_LIST_ID`),
  KEY `FKD5F3F914B9BD3D6` (`QUOTATION_LIST_ID`),
  KEY `FKD5F3F91BEA5AFDB` (`QUOTATION_ID`),
  KEY `FKD5F3F91109CF93B` (`SUPPLIER_ID`),
  CONSTRAINT `FKD5F3F91109CF93B` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `su_supplier` (`SUPPLIER_CODE`),
  CONSTRAINT `FKD5F3F91BEA5AFDB` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `quotation` (`QUOTATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of quotation_list
-- ----------------------------
BEGIN;
INSERT INTO `quotation_list` VALUES (74, NULL, '', '舞台板以及地毯', NULL, 1, 8500, 8500, '寬15m 深4m(舞台高 0.6m） ', '', 60, NULL, NULL);
INSERT INTO `quotation_list` VALUES (75, NULL, '', 'P3 LED屏', NULL, 1, 32300, 32300, '寬8米*高4.5米', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (76, NULL, '', 'LED兩側背景板', NULL, 1, 15300, 15300, 'LED屏左右两侧背景板', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (77, NULL, '', '灯光音响系统', NULL, 1, 20000, 20000, '灯光音响系统一套', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (78, NULL, '', '启动仪式台', NULL, 1, 10000, 10000, '启动仪式台', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (79, NULL, '', '主持人', NULL, 1, 10000, 10000, '主持人', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (80, NULL, '', '摄影/摄像', NULL, 1, 6500, 6500, '摄影两名，摄像一名全程录影', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (81, NULL, '', '消电检', NULL, 1, 8000, 8000, '消电检查项目', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (82, NULL, '', '设计费', NULL, 1, 15000, 15000, '主舞台/接待区/活动主画面/场地配置等', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (83, NULL, '', '服务费', NULL, 1, 15000, 15000, '活动报批，活动前置作业联系，活动执行', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (84, NULL, '', '活动杂支', NULL, 1, 2000, 2000, '包含活动其他小至作物如工作证，工作人员交通，餐费等支出', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (85, NULL, '', '进撤场', NULL, 1, 8000, 8000, '', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (86, NULL, '', '硬体其他', NULL, 1, 11000, 11000, '', '', 60, 0, NULL);
INSERT INTO `quotation_list` VALUES (87, NULL, '', '桌子', NULL, 1, 54, 54, '', '', 63, NULL, NULL);
INSERT INTO `quotation_list` VALUES (88, NULL, '', '桌布', NULL, 1, 40, 40, '', '', 63, 0, NULL);
INSERT INTO `quotation_list` VALUES (89, NULL, '', '椅子', NULL, 2, 45, 90, '', '', 63, 0, NULL);
INSERT INTO `quotation_list` VALUES (90, NULL, '', '门型展架', NULL, 1, 410, 410, '', '', 63, 0, NULL);
INSERT INTO `quotation_list` VALUES (91, NULL, '', '运输', NULL, 1, 800, 800, '', '', 63, 0, NULL);
INSERT INTO `quotation_list` VALUES (92, NULL, '', '总报价', NULL, 1, 23000, 23000, '', '', 64, NULL, NULL);
INSERT INTO `quotation_list` VALUES (93, NULL, '', '設計費', NULL, 1, 142200, 142200, '', '', 65, NULL, NULL);
INSERT INTO `quotation_list` VALUES (94, NULL, '戲院製作物', '戲院製作物', NULL, 1, 1217150, 1217150, '', '', 66, NULL, NULL);
INSERT INTO `quotation_list` VALUES (95, NULL, '投影活動Demo製作', '投影活動Demo製作', NULL, 1, 45000, 45000, '', '', 67, NULL, NULL);
INSERT INTO `quotation_list` VALUES (96, NULL, '上海差旅費', '上海差旅費', NULL, 1, 8000, 8000, '', '', 68, NULL, NULL);
INSERT INTO `quotation_list` VALUES (97, NULL, '戲院製作物', '戲院製作物', NULL, 1, 1217150, 1217150, '', '', 70, NULL, NULL);
INSERT INTO `quotation_list` VALUES (98, NULL, '設計費', '設計費', NULL, 1, 142200, 142200, '', '', 70, NULL, NULL);
INSERT INTO `quotation_list` VALUES (99, NULL, '投影活動Demo製作', '投影活動Demo製作', NULL, 1, 45000, 45000, '', '', 70, NULL, NULL);
INSERT INTO `quotation_list` VALUES (100, NULL, '上海差旅費', '上海差旅費', NULL, 1, 8000, 8000, '', '', 70, NULL, NULL);
INSERT INTO `quotation_list` VALUES (101, NULL, '人偶巡演团队', '人偶巡演团队', NULL, 1, 533638, 533638, '', '', 71, NULL, NULL);
INSERT INTO `quotation_list` VALUES (102, NULL, '服务费', '服务费', NULL, 1, 42000, 42000, '', '', 71, NULL, NULL);
INSERT INTO `quotation_list` VALUES (103, NULL, '平面設計費', '平面設計費', NULL, 1, 15000, 15000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (104, NULL, '场地费', '场地费', NULL, 1, 30448, 30448, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (105, NULL, '搭建', '搭建', NULL, 1, 69350, 69350, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (106, NULL, '安保', '安保', NULL, 1, 8000, 8000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (107, NULL, '主持人', '主持人', NULL, 1, 11000, 11000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (108, NULL, '纸袋制作', '纸袋制作', NULL, 1, 3000, 3000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (109, NULL, '报批（警官劳务费）', '报批（警官劳务费）', NULL, 1, 5000, 5000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (110, NULL, '兼职', '兼职', NULL, 1, 1600, 1600, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (111, NULL, '工作证', '工作证', NULL, 1, 1200, 1200, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (112, NULL, '数码制作', '数码制作', NULL, 1, 186, 186, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (113, NULL, '采买小音响', '采买小音响', NULL, 1, 450, 450, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (114, NULL, '服务费', '服务费', NULL, 1, 15000, 15000, '', '', 72, NULL, NULL);
INSERT INTO `quotation_list` VALUES (115, NULL, '戏院制作物', '戏院制作物', NULL, 1, 1243280, 1243280, '', '', 73, NULL, NULL);
INSERT INTO `quotation_list` VALUES (116, NULL, '设计费和活动费用', '设计费和活动费用', NULL, 1, 305570, 305570, '', '', 73, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for quotation_list_projectfees
-- ----------------------------
DROP TABLE IF EXISTS `quotation_list_projectfees`;
CREATE TABLE `quotation_list_projectfees` (
  `PROJECT_FEE_ID` int(11) NOT NULL,
  `QUOTATION_LIST_ID` int(11) NOT NULL,
  `uuid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`QUOTATION_LIST_ID`,`PROJECT_FEE_ID`),
  KEY `FK70F25C784B90D73C` (`PROJECT_FEE_ID`),
  KEY `FK70F25C783A7A9A14` (`QUOTATION_LIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of quotation_list_projectfees
-- ----------------------------
BEGIN;
INSERT INTO `quotation_list_projectfees` VALUES (4, 87, 'de128ca3-97ad-422a-8a7f-91b5877144a2');
INSERT INTO `quotation_list_projectfees` VALUES (4, 88, 'de128ca3-97ad-422a-8a7f-91b5877144a2');
INSERT INTO `quotation_list_projectfees` VALUES (4, 89, 'de128ca3-97ad-422a-8a7f-91b5877144a2');
INSERT INTO `quotation_list_projectfees` VALUES (4, 90, 'de128ca3-97ad-422a-8a7f-91b5877144a2');
INSERT INTO `quotation_list_projectfees` VALUES (4, 91, 'de128ca3-97ad-422a-8a7f-91b5877144a2');
INSERT INTO `quotation_list_projectfees` VALUES (5, 92, 'd574eebd-4c4f-4299-bd79-a6ca439f8475');
INSERT INTO `quotation_list_projectfees` VALUES (6, 92, '08f7e903-b58a-4be4-bb4d-0fed1b7bfd32');
INSERT INTO `quotation_list_projectfees` VALUES (7, 92, 'f53e9bdb-374a-45ee-8f76-667204a12bae');
INSERT INTO `quotation_list_projectfees` VALUES (8, 92, '8fca1730-80a4-4ef4-8014-61225ad97a16');
INSERT INTO `quotation_list_projectfees` VALUES (9, 92, 'b1994582-09ed-4fc4-b89e-5e22df588fe2');
INSERT INTO `quotation_list_projectfees` VALUES (10, 92, 'ae11b40d-2952-4f64-8686-bd6fa1ed26b6');
INSERT INTO `quotation_list_projectfees` VALUES (11, 92, '4ea20fb5-95a1-4e47-ac39-c5a12c3fbbf0');
INSERT INTO `quotation_list_projectfees` VALUES (12, 94, '8b9247e4-6ab7-46d0-a4fe-24a7248e8b9e');
INSERT INTO `quotation_list_projectfees` VALUES (13, 97, 'b8889f75-a43a-46f8-9c3d-90d2f237cbcc');
INSERT INTO `quotation_list_projectfees` VALUES (47, 98, '984afae6-6bcf-4df4-b75c-4b413667261e');
INSERT INTO `quotation_list_projectfees` VALUES (14, 99, 'f789aeb6-0e05-4ba6-a1f7-1b177d0894d8');
INSERT INTO `quotation_list_projectfees` VALUES (21, 100, '7163647a-8f5c-4b0f-b29e-70bf2c756f39');
INSERT INTO `quotation_list_projectfees` VALUES (22, 100, '1332f77a-1cdf-4648-aa07-fc9189bd0bc8');
INSERT INTO `quotation_list_projectfees` VALUES (23, 100, 'db1b0a27-367f-4a15-981f-68f900c92cc6');
INSERT INTO `quotation_list_projectfees` VALUES (17, 101, '08c6864e-67f1-4d99-bac1-26bf54a0dc46');
INSERT INTO `quotation_list_projectfees` VALUES (18, 101, '250f6a89-a676-4fc9-96bd-f7e59b7a443c');
INSERT INTO `quotation_list_projectfees` VALUES (19, 101, 'b2016150-49f7-48e6-924c-140ac2f201fd');
INSERT INTO `quotation_list_projectfees` VALUES (39, 104, 'e431d8c6-13c3-4868-968a-0a15a1bbc57e');
INSERT INTO `quotation_list_projectfees` VALUES (40, 105, '828be1b0-bd19-4b18-918f-2d9bff1fc76d');
INSERT INTO `quotation_list_projectfees` VALUES (41, 106, '8caab7f4-fcaa-4c7e-8a5e-53d31acf664f');
INSERT INTO `quotation_list_projectfees` VALUES (42, 107, '900bd146-c1ec-478b-9ff8-d23535244843');
INSERT INTO `quotation_list_projectfees` VALUES (43, 108, '2a9fd909-77c7-42a4-a506-997d93376cf8');
INSERT INTO `quotation_list_projectfees` VALUES (49, 109, '2845e97f-e5a6-4216-a105-e98a2fe6c5af');
INSERT INTO `quotation_list_projectfees` VALUES (50, 110, 'fe2ad377-d3cc-40a1-ae4d-5237e91c3f80');
INSERT INTO `quotation_list_projectfees` VALUES (51, 111, '9ede3fa9-64b1-4f39-b11a-e21b3c0ede87');
INSERT INTO `quotation_list_projectfees` VALUES (52, 112, 'aec12b4b-fff8-48a3-a310-3ffafa4a7d1a');
INSERT INTO `quotation_list_projectfees` VALUES (53, 113, 'ea3fe32b-d476-4393-9178-191643ff1a66');
INSERT INTO `quotation_list_projectfees` VALUES (25, 115, 'bb3eec7e-81fa-4890-8b11-0baa48cc3518');
INSERT INTO `quotation_list_projectfees` VALUES (29, 115, 'f9678123-f569-4b35-95e1-b8f7168d8e88');
INSERT INTO `quotation_list_projectfees` VALUES (26, 116, 'e7883f2e-40bb-44d5-a586-b0b5012985f6');
INSERT INTO `quotation_list_projectfees` VALUES (27, 116, '9f29c78b-8dd5-4e1c-8a12-c1506782278d');
INSERT INTO `quotation_list_projectfees` VALUES (28, 116, 'ed0d69c8-8de6-45a9-97d9-1b9430134164');
INSERT INTO `quotation_list_projectfees` VALUES (30, 116, 'b2ea69f9-6c0e-44ff-a6d9-c11c3eb7acc1');
INSERT INTO `quotation_list_projectfees` VALUES (31, 116, 'c666a9f5-d71d-4a49-b03d-ec74894b9776');
INSERT INTO `quotation_list_projectfees` VALUES (32, 116, 'c6687ab5-b33e-4e16-9df1-812ec0be03c9');
INSERT INTO `quotation_list_projectfees` VALUES (33, 116, 'db1856df-fb85-4be2-9483-cc92a270d1be');
INSERT INTO `quotation_list_projectfees` VALUES (34, 116, '3441479b-e1fd-4ad4-b6d8-570aad82be59');
INSERT INTO `quotation_list_projectfees` VALUES (35, 116, 'db048b78-0225-4a84-9a9f-036cbfd3e070');
INSERT INTO `quotation_list_projectfees` VALUES (48, 116, 'a9c49d84-6743-48c6-b035-204cd06ecf4c');
COMMIT;

-- ----------------------------
-- Table structure for serial_number
-- ----------------------------
DROP TABLE IF EXISTS `serial_number`;
CREATE TABLE `serial_number` (
  `ID` int(6) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8197 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of serial_number
-- ----------------------------
BEGIN;
INSERT INTO `serial_number` VALUES (8205);
COMMIT;

-- ----------------------------
-- Table structure for serial_number_user
-- ----------------------------
DROP TABLE IF EXISTS `serial_number_user`;
CREATE TABLE `serial_number_user` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1052 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of serial_number_user
-- ----------------------------
BEGIN;
INSERT INTO `serial_number_user` VALUES (1067);
COMMIT;

-- ----------------------------
-- Table structure for su_supplier
-- ----------------------------
DROP TABLE IF EXISTS `su_supplier`;
CREATE TABLE `su_supplier` (
  `SUPPLIER_CODE` int(255) NOT NULL AUTO_INCREMENT COMMENT '厂商编号',
  `NAME` varchar(255) NOT NULL COMMENT '名称',
  `BANK_ACCOUNT` varchar(255) DEFAULT NULL COMMENT '银行账号',
  `ACCOUNT_NAME` varchar(255) DEFAULT NULL COMMENT '银行户名',
  `BANK_NAME` varchar(255) DEFAULT NULL COMMENT '开户行',
  `BRANCH_NAME` varchar(255) DEFAULT NULL COMMENT '支行名称',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `OFFICIAL_WEBSITE` varchar(255) DEFAULT NULL COMMENT '官网',
  `NOTE` varchar(255) DEFAULT NULL COMMENT '备注',
  `TYPE` varchar(255) DEFAULT NULL COMMENT '分类',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `remove` bit(1) DEFAULT NULL,
  PRIMARY KEY (`SUPPLIER_CODE`),
  KEY `FK336F9F09AB1C7313` (`SUPPLIER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of su_supplier
-- ----------------------------
BEGIN;
INSERT INTO `su_supplier` VALUES (4, '北京华创汇智国际会展有限公司', '110913062510202', '北京华创汇智国际会展有限公司', '招商银行', '北京崇文门支行', '北京市朝阳区金盏乡马各庄工业区C区8号', 'https://www.baidu.com', '', '搭建', 1, b'1');
INSERT INTO `su_supplier` VALUES (5, '北京千朗文化传媒有限公司 ', '10060437018010049217', '北京千朗文化传媒有限公司', '交通银行', '北京马甸支行', '北京市西城区德外新风街2号天成科技大厦A座1006 ', '', '', '印刷', 1, b'1');
INSERT INTO `su_supplier` VALUES (6, '北京菲力欧国际广告制作有限公司', '042901040008152', '北京菲力欧国际广告制作有限公司', '中国农业银行', '北京市分行英家坟支行', '北京大兴区青云店南大红门建材城K5栋', 'www.feiliou.com', '', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (7, '北京花更美商贸有限公司  ', '9558820200020195162', '杨小丽', '中国工商银行', '北京分行紫竹院支行 ', '北京市朝阳区 呼家楼北街17号楼底商 ', 'www.huagengmei.com', '', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (8, '北京金友盛世商贸有限公司 ', '11200501040038957', '北京金友盛世商贸有限公司 ', '中国农业银行', '北京龙潭分理处', '崇文夕照寺街14号-5-11B', 'www.jinyouzhika.net', '个人账户：李正汉\r\n农业银行北京骡马市支行\r\n6228480010261791213', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (9, '东莞市肯维实业有限公司', '070060190010012538', '东莞市肯维实业有限公司', '东莞农村商业银行', '虎门北栅支行', '东莞市虎门镇北栅团结路29号501', ' www.canwaychina.com', '制作品项：硅胶冰格\r\n税务登记号：441900334803145\r\n工厂地址：广东省东莞市虎门镇怀德路口仁和工业区仁兴二路\r\n工厂名称: 东莞柯技电子厂\r\n个人账号：中国农业银行东莞虎门北栅支行\r\n6228480608635707672陈英华', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (10, '北京千百度服装有限公司', '0200003409201013503', '北京千百度服装有限公司', '工商银行', '朝阳支行营业部', '北京市朝阳区王四营乡道口村东京忱润业工业园E单元1-2号千百度服装', 'http://bjbeixue.1688.com/', '制作品项：棒球帽、渔夫帽、防风衣、T恤\r\n纳税人识别号：110105633021231', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (11, '北京浩海润鹏礼品包装用品加工厂', '0923 0201 0302 0006 530', '北京浩海润鹏礼品包装用品加工厂', '北京农村商业银行', '金星支行', '北京市大兴区团南工业园北二路33号', 'www.bjhhrp.com.cn', '制作品项：T恤、帆布包', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (12, '深圳市北德检测技术有限公司', '4000027409200715844', '深圳市北德检测技术有限公司', '中国工商银行', '西丽支行', '深圳市南山区西丽镇平山一路大园工业区北区10栋4楼', 'http://www.atllab.org', '', '质检', 1, b'1');
INSERT INTO `su_supplier` VALUES (13, '温州坚瑞印业有限公司', '3961 5833 3734', '温州坚瑞印业有限公司', '中国银行', '苍南县支行', '浙江苍南龙港镇孙店工业区', 'http://xiangkanglipin.1688.com/', 'L形夹\r\n纳税人识别号：330327562381482', '制作制造', 1, b'1');
INSERT INTO `su_supplier` VALUES (14, '大地航空公司', '', '', '', '', '北京', '', '', '航空', 2, b'0');
INSERT INTO `su_supplier` VALUES (15, '中保（北京）保安服务有限公司第一分公司', '11031201040001270', '', '中国农业银行', '农行北京苹果园支行', '北京', 'http://bnt.fk369.com', '公司法人：赵秋', '安保公司', 2, b'0');
INSERT INTO `su_supplier` VALUES (16, '北京信德利广商贸有限公司', '11001071600053016630', '', '北京建设银行', '右安门支行', '北京', '', '铁马等安保硬件设备、志愿者', '活動公司', 2, b'0');
INSERT INTO `su_supplier` VALUES (17, '北京航天海泉测试技术有限公司', '1001016200053012673', '', '中国建设银行', '建行北京丰台支行', '北京', 'http://www.beijingfire.com/news.aspx?ID=8999', '', '安全检测公司', 2, b'0');
INSERT INTO `su_supplier` VALUES (18, '待定', '待定', '待定', '待定', '待定', '待定', '', '', '待定', 2, b'0');
INSERT INTO `su_supplier` VALUES (19, '12243', '1241235', '23456', '142323', '143356', '213343', '454546', '3245', '印刷', 2, b'0');
INSERT INTO `su_supplier` VALUES (20, 'Kate', '6214850107232818', '林家晖', '招商银行', '', '', '', '', '差旅', 2, b'0');
INSERT INTO `su_supplier` VALUES (21, 'lining', '6214830159197457', '李宁', '招商银行', '', '', '', '', '差旅', 2, b'0');
INSERT INTO `su_supplier` VALUES (22, 'lining', '6214830159197457', '李宁', '招商银行', '', '', '', '', '公司同事', 1, b'1');
INSERT INTO `su_supplier` VALUES (23, 'Kate', '6214850107232818', '林家晖', '招商银行', '', '北京', '', '', '公司同事', 1, b'1');
INSERT INTO `su_supplier` VALUES (24, '北京百丽宫影院有限公司', '333 757 398 962', '北京百丽宫影院有限公司', '中行北京东安门支行', '', '北京', '', '', '场地', 1, b'1');
INSERT INTO `su_supplier` VALUES (25, '北京安泰保安服务有限公司', '110 827 010 4000 6870', '北京安泰保安服务有限公司', '中国农业银行宏福支行', '', '北京', '', '', '安保和工读生', 1, b'1');
INSERT INTO `su_supplier` VALUES (26, '闫学新', '6212260200005836370', '闫学新', '中国工商银行（高碑店支行）', '', '北京', '', '', '主持人', 1, b'1');
INSERT INTO `su_supplier` VALUES (27, '禾意数位创意有限公司', '04977500269', 'catch design creative Co.', 'union bank of taiwan', 'SWIFT  UBOTTWTP', '台湾台北', '', '', '设计', 1, b'1');
INSERT INTO `su_supplier` VALUES (28, '杨凯崴', '002756090537', 'KAI  WEI YANG', 'taishin international bank', 'SWIFT  TSIBTWTP', '台湾台北', '', '', '设计', 1, b'1');
INSERT INTO `su_supplier` VALUES (29, '永大', '', '', '', '', '', '', '', '设计', 1, b'1');
INSERT INTO `su_supplier` VALUES (30, '宁海燕', '6227 0000 1090 0271 711', '宁海燕', '建行北京市丰台区芳群园储蓄所', '建行北京市丰台区芳群园储蓄所', '北京', '', '', '安保和工读生', 1, b'1');
INSERT INTO `su_supplier` VALUES (31, '吴仕涛', '6212 2602 0001 9791 371', '吴仕涛', '中国工商银行 北京九龙山支行', '中国工商银行 北京九龙山支行', '北京', '', '', '安保和工读生', 1, b'1');
INSERT INTO `su_supplier` VALUES (32, '黄雪琴', '6212260200104562679', '黄雪琴', '中国工商银行海淀成府路支行', '中国工商银行海淀成府路支行', '北京', '', '', '发票', 1, b'1');
INSERT INTO `su_supplier` VALUES (33, 'Dada', '6214830169215539', '张伟达', '招商银行', '招商银行', '北京', '', '', '公司同事', 1, b'1');
INSERT INTO `su_supplier` VALUES (34, 'Maya', '6214850109075231', '杜倩文', '招商银行', '招商银行', '北京', '', '', '公司同事', 1, b'1');
INSERT INTO `su_supplier` VALUES (35, '霍尔果斯视彩文化传媒有限公司', '107661781005', '霍尔果斯视彩文化传媒有限公司', '中国银行伊宁市红旗路支行', '中国银行伊宁市红旗路支行', '新疆', '', '徐亚辉', '印刷', 1, b'1');
INSERT INTO `su_supplier` VALUES (36, '边国华', '', '', '', '', '', '', '', '设计', 1, b'1');
INSERT INTO `su_supplier` VALUES (37, '王嘉荣', '6217001210071903114', '王嘉荣', '中国建设银行股份有限公司上海五角场支行', '中国建设银行股份有限公司上海五角场支行', '台北', '', 'Tommy', '设计', 1, b'1');
INSERT INTO `su_supplier` VALUES (38, '太平财产保险有限公司北京分公司', '0200216819200018804', '太平财产保险有限公司北京分公司', '中国工商银行股份有限公司北京金融街支行营业室', '中国工商银行股份有限公司北京金融街支行营业室', '北京', '', '', '保险', 1, b'1');
INSERT INTO `su_supplier` VALUES (39, '北京梦田创意文化传播有限公司', '91410154740002857', '北京梦田创意文化传播有限公司', '上海浦东发展银行股份有限公司北京大望路支行', '上海浦东发展银行股份有限公司北京大望路支行', '北京', '', '', '主持人', 1, b'1');
INSERT INTO `su_supplier` VALUES (40, '北京美岱服装设计有限公司', '91200154800020609', '北京美岱服装设计有限公司', '上海浦东发展银行北京电子城支行', '上海浦东发展银行北京电子城支行', '北京', '', '', '妆发', 1, b'1');
INSERT INTO `su_supplier` VALUES (41, '北京龙泰凯阳汽车租赁有限公司一分公司', '91040154800006273', '北京龙泰凯阳汽车租赁有限公司一分公司', '上海浦东发展银行北京黄寺支行', '上海浦东发展银行北京黄寺支行', '北京', '', '', '汽车租赁', 1, b'1');
INSERT INTO `su_supplier` VALUES (42, '北京优驰展览服务有限公司', '01091248900120108004644', '北京优驰展览服务有限公司', '北京银行石园支行', '城市商业银行北京银行石园支行', '北京', 'www.123.com', '', '搭建', 1, b'1');
INSERT INTO `su_supplier` VALUES (43, 'Maggie', '6225880159347955', '李金璇', '招商银行', '', '北京', '', '', '公司同事', 0, b'1');
COMMIT;

-- ----------------------------
-- Table structure for su_supplier_contact
-- ----------------------------
DROP TABLE IF EXISTS `su_supplier_contact`;
CREATE TABLE `su_supplier_contact` (
  `SUPPLIER_CONTACT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '厂商联络ID',
  `NAME` varchar(255) NOT NULL COMMENT '联络人名称',
  `TEL` varchar(255) DEFAULT NULL COMMENT '座机',
  `MOBILE` varchar(255) DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `POST_CODE` varchar(255) DEFAULT NULL COMMENT '邮编',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `SUPPLIER_CODE` int(11) DEFAULT NULL COMMENT '厂商编号',
  `EXTENSION` varchar(255) DEFAULT NULL COMMENT '分机',
  PRIMARY KEY (`SUPPLIER_CONTACT_ID`),
  KEY `FKFBDADCCAAA07114D` (`SUPPLIER_CODE`),
  CONSTRAINT `FKFBDADCCAAA07114D` FOREIGN KEY (`SUPPLIER_CODE`) REFERENCES `su_supplier` (`SUPPLIER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of su_supplier_contact
-- ----------------------------
BEGIN;
INSERT INTO `su_supplier_contact` VALUES (1, '徐亚辉', '010-56219970', '13466627605', '452451920@qq.com', NULL, NULL, '2015-07-01 16:22:38', 5, '888');
INSERT INTO `su_supplier_contact` VALUES (2, '徐亚辉2', '010-56219970', '18910309751', '452451920@qq.com', NULL, NULL, '2015-07-01 16:22:38', 5, '999');
INSERT INTO `su_supplier_contact` VALUES (3, '陈晓梅', '010-56219970', '13501335131', '872110859@qq.com', NULL, NULL, '2015-07-01 16:22:38', 5, '777');
INSERT INTO `su_supplier_contact` VALUES (4, '朱康飞', '010-56219970', '13520940012', '897317508@qq.com', NULL, NULL, '2015-07-01 16:22:38', 5, '555');
INSERT INTO `su_supplier_contact` VALUES (5, '王健', '010-51265557', '13366478959', '51265557@163.com', NULL, NULL, '2016-04-25 12:10:43', 6, '22');
INSERT INTO `su_supplier_contact` VALUES (6, '李正汉', '010-83518044', '15801228090', '275357153@qq.com', NULL, NULL, '2016-04-25 12:11:03', 8, '');
INSERT INTO `su_supplier_contact` VALUES (7, '刘丹丹', '0769-86035801', '13923887630', 'daisy@canwaychina.com  ', NULL, NULL, '2016-04-25 12:11:20', 9, '');
INSERT INTO `su_supplier_contact` VALUES (8, '姚英楠', '010-64890815-815', '13311269178', '2764709716@qq.com', NULL, NULL, '2016-04-25 12:11:27', 10, '');
INSERT INTO `su_supplier_contact` VALUES (9, '魏炳婕', '010-61280022', '13901313117', 'bjhhrp@aliyun.com', NULL, NULL, '2016-04-25 12:11:31', 11, '');
INSERT INTO `su_supplier_contact` VALUES (10, '惠海鹏', '010-61280022', '13321191364', 'bjhhrp@aliyun.com', NULL, NULL, '2016-04-25 12:11:31', 11, '');
INSERT INTO `su_supplier_contact` VALUES (11, '王聪聪', '010-65425036-000', '13321160010', '1580146423@qq.com', NULL, NULL, '2016-04-25 12:10:28', 4, '1');
INSERT INTO `su_supplier_contact` VALUES (12, '王聪聪', '010-65425036', '15301373983', '1580146423@qq.com', NULL, NULL, '2016-04-25 12:10:28', 4, '2');
INSERT INTO `su_supplier_contact` VALUES (13, '吕松松', '010-65425036', '13331126320', '1580146423@qq.com', NULL, NULL, '2016-04-25 12:10:28', 4, '3');
INSERT INTO `su_supplier_contact` VALUES (14, '冯志斌', '010-65425036', '13311340071', '1580146423@qq.com', NULL, NULL, '2016-04-25 12:10:28', 4, '4');
INSERT INTO `su_supplier_contact` VALUES (15, '李玲', '0755-26909822', '13632751011', 'lilingszp@126.com', NULL, NULL, '2016-04-25 12:11:38', 12, '');
INSERT INTO `su_supplier_contact` VALUES (16, '傅经理', '0577-68518659', '18968748659 ', '1329352992@qq.com', NULL, NULL, '2016-04-25 12:11:44', 13, '');
INSERT INTO `su_supplier_contact` VALUES (17, '王大地', '010-65428888', '13388888888', '888@888.com', NULL, NULL, '2015-08-04 16:41:04', 14, '800');
INSERT INTO `su_supplier_contact` VALUES (18, '刘晓磊', '010-51375028', '13911786917', 'liutm09@vip.sina.com', NULL, NULL, '2015-12-03 17:25:57', 15, '');
INSERT INTO `su_supplier_contact` VALUES (19, '刘晓磊', '010-51375028', '13911786917', 'liutm09@vip.sina.com', NULL, NULL, '2015-12-03 17:30:30', 16, '');
INSERT INTO `su_supplier_contact` VALUES (20, '李培', 'tbc', '13321107007', 'tbc', NULL, NULL, '2015-12-03 17:35:07', 17, '');
COMMIT;

-- ----------------------------
-- Table structure for susupplier_type
-- ----------------------------
DROP TABLE IF EXISTS `susupplier_type`;
CREATE TABLE `susupplier_type` (
  `TYPE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `NAME` varchar(255) NOT NULL COMMENT '分类名称',
  `REMOVE` bit(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of susupplier_type
-- ----------------------------
BEGIN;
INSERT INTO `susupplier_type` VALUES (1, '印刷', b'0');
INSERT INTO `susupplier_type` VALUES (2, '活動公司', b'0');
INSERT INTO `susupplier_type` VALUES (3, '贈品製造商', b'0');
INSERT INTO `susupplier_type` VALUES (4, '新媒體設計相關', b'0');
INSERT INTO `susupplier_type` VALUES (5, '互動設計', b'0');
INSERT INTO `susupplier_type` VALUES (6, '平面設計', b'0');
INSERT INTO `susupplier_type` VALUES (7, '展場設計', b'0');
INSERT INTO `susupplier_type` VALUES (8, '商品設計', b'0');
INSERT INTO `susupplier_type` VALUES (9, '影片拍攝製作', b'0');
INSERT INTO `susupplier_type` VALUES (10, '影片後製', b'0');
INSERT INTO `susupplier_type` VALUES (11, '搭建公司', b'0');
INSERT INTO `susupplier_type` VALUES (12, '广告牌制作', b'0');
INSERT INTO `susupplier_type` VALUES (13, '广告牌制作', b'0');
INSERT INTO `susupplier_type` VALUES (14, '活动花卉', b'0');
INSERT INTO `susupplier_type` VALUES (15, '活动工作证', b'0');
INSERT INTO `susupplier_type` VALUES (16, '安全检测公司', b'0');
INSERT INTO `susupplier_type` VALUES (17, '航空', b'0');
INSERT INTO `susupplier_type` VALUES (18, '安保公司', b'0');
INSERT INTO `susupplier_type` VALUES (19, '待定', b'0');
INSERT INTO `susupplier_type` VALUES (20, '制作制造', b'1');
INSERT INTO `susupplier_type` VALUES (21, '搭建', b'1');
INSERT INTO `susupplier_type` VALUES (22, '设计', b'1');
INSERT INTO `susupplier_type` VALUES (23, '印刷', b'1');
INSERT INTO `susupplier_type` VALUES (24, '活动', b'1');
INSERT INTO `susupplier_type` VALUES (25, '质检', b'1');
INSERT INTO `susupplier_type` VALUES (26, '安保和工读生', b'1');
INSERT INTO `susupplier_type` VALUES (27, '差旅', b'1');
INSERT INTO `susupplier_type` VALUES (28, '经费', b'1');
INSERT INTO `susupplier_type` VALUES (29, '公司同事', b'1');
INSERT INTO `susupplier_type` VALUES (30, '场地', b'1');
INSERT INTO `susupplier_type` VALUES (31, '主持人', b'1');
INSERT INTO `susupplier_type` VALUES (32, '发票', b'1');
INSERT INTO `susupplier_type` VALUES (33, '保险', b'1');
INSERT INTO `susupplier_type` VALUES (34, '妆发', b'1');
INSERT INTO `susupplier_type` VALUES (35, '汽车租赁', b'1');
COMMIT;

-- ----------------------------
-- Table structure for sys_configuration
-- ----------------------------
DROP TABLE IF EXISTS `sys_configuration`;
CREATE TABLE `sys_configuration` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `TAX_RATE` varchar(10) DEFAULT NULL,
  `IS_USED` int(11) NOT NULL COMMENT '1在用，0不再用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_configuration
-- ----------------------------
BEGIN;
INSERT INTO `sys_configuration` VALUES (1, '6%', 1);
INSERT INTO `sys_configuration` VALUES (2, '8%', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPERATOR` varchar(255) DEFAULT NULL COMMENT '操作人',
  `OPERATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `OPERTATE_IP` varchar(255) DEFAULT NULL COMMENT '操作IP',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '操作内容',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(30) NOT NULL COMMENT '菜单ID',
  `MENU_NAME` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `URL` varchar(255) NOT NULL DEFAULT '' COMMENT 'URL',
  `PARENT_ID` int(30) NOT NULL COMMENT '父菜单ID',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  `SORT` int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 'global.index', '/index', 0, 'fa-dashboard', 0);
INSERT INTO `sys_menu` VALUES (2, 'menu.pendingInstructions', '/approving/project/list', 0, 'fa-pencil', 1);
INSERT INTO `sys_menu` VALUES (3, 'menu.financialManagement', '/cost/coseAnalysis/list', 0, 'fa-dollar', 4);
INSERT INTO `sys_menu` VALUES (4, 'menu.caseManagement', '/project/list', 0, 'fa-file-text-o', 2);
INSERT INTO `sys_menu` VALUES (5, 'menu.quoteManagement', '/quotation/list', 0, 'fa-file-text', 3);
INSERT INTO `sys_menu` VALUES (6, 'menu.applicationManagement', '/wkLeave/applyList', 0, 'fa-upload', 5);
INSERT INTO `sys_menu` VALUES (7, 'menu.manufacturerContactForm', '/supplier/list', 0, 'fa-phone', 6);
INSERT INTO `sys_menu` VALUES (8, 'menu.customerContactForm', '/custom/list', 0, 'fa-phone', 7);
INSERT INTO `sys_menu` VALUES (9, 'menu.personalInformationManagement', '/personalData/basicInfo', 0, 'fa-user', 8);
INSERT INTO `sys_menu` VALUES (10, 'menu.leaveManagement', '/wkLeave/list', 0, 'fa-home', 9);
INSERT INTO `sys_menu` VALUES (11, 'menu.equipmentManagement', '/device/list', 0, 'fa-laptop', 10);
INSERT INTO `sys_menu` VALUES (12, 'menu.contactsColleague', '/contact/list', 0, 'fa-phone', 11);
INSERT INTO `sys_menu` VALUES (13, 'menu.reportManager', '/wkLeave/report', 0, 'fa-bar-chart-o', 12);
INSERT INTO `sys_menu` VALUES (14, 'menu.rightsManagement', '/user/list', 0, 'fa-wrench', 13);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_item`;
CREATE TABLE `sys_menu_item` (
  `MENU_ITEM_ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '子菜单ID',
  `MENU_ID` int(11) NOT NULL COMMENT '菜单ID',
  `URL` varchar(255) NOT NULL DEFAULT '' COMMENT 'URL',
  `SORT` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`MENU_ITEM_ID`),
  KEY `FK3B7A2AE189C233A8` (`MENU_ID`),
  CONSTRAINT `FK3B7A2AE189C233A8` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_item` VALUES (1, 1, '/index', 1);
INSERT INTO `sys_menu_item` VALUES (2, 2, '/approving/project/list', 1);
INSERT INTO `sys_menu_item` VALUES (3, 2, '/approving/quotation/list', 2);
INSERT INTO `sys_menu_item` VALUES (4, 2, '/approving/projectFee/list', 3);
INSERT INTO `sys_menu_item` VALUES (5, 2, '/approving/wkLeave/list', 4);
INSERT INTO `sys_menu_item` VALUES (6, 2, '/approving/wkOvertime/list', 5);
INSERT INTO `sys_menu_item` VALUES (7, 2, '/approving/badAccount/list', 6);
INSERT INTO `sys_menu_item` VALUES (8, 2, '/approving/edSupplier/list', 7);
INSERT INTO `sys_menu_item` VALUES (9, 2, '/approving/customer/list', 8);
INSERT INTO `sys_menu_item` VALUES (10, 3, '/cost/coseAnalysis/list', 1);
INSERT INTO `sys_menu_item` VALUES (11, 3, '/badAccount/applyList', 2);
INSERT INTO `sys_menu_item` VALUES (12, 3, '/collectMoney/list', 3);
INSERT INTO `sys_menu_item` VALUES (13, 4, '/project/list', 1);
INSERT INTO `sys_menu_item` VALUES (14, 5, '/quotation/list', 1);
INSERT INTO `sys_menu_item` VALUES (15, 5, '/quotation/addQuotation', 2);
INSERT INTO `sys_menu_item` VALUES (16, 6, '/wkLeave/applyList', 1);
INSERT INTO `sys_menu_item` VALUES (17, 6, '/wkOvertime/applyList', 2);
INSERT INTO `sys_menu_item` VALUES (18, 6, '/projectFee/applyList', 3);
INSERT INTO `sys_menu_item` VALUES (19, 7, '/supplier/list', 1);
INSERT INTO `sys_menu_item` VALUES (20, 7, '/supplier/add', 2);
INSERT INTO `sys_menu_item` VALUES (21, 8, '/custom/list', 1);
INSERT INTO `sys_menu_item` VALUES (22, 8, '/custom/add', 2);
INSERT INTO `sys_menu_item` VALUES (23, 9, '/personalData/basicInfo', 1);
INSERT INTO `sys_menu_item` VALUES (24, 9, '/device/usedList', 2);
INSERT INTO `sys_menu_item` VALUES (25, 9, '/personalData/leaveSubtotal', 3);
INSERT INTO `sys_menu_item` VALUES (26, 9, '/personalData/caseList', 4);
INSERT INTO `sys_menu_item` VALUES (27, 9, '/personalData/changePassword', 5);
INSERT INTO `sys_menu_item` VALUES (28, 10, '/wkLeave/list', 1);
INSERT INTO `sys_menu_item` VALUES (29, 11, '/device/list', 1);
INSERT INTO `sys_menu_item` VALUES (30, 11, '/device/borrowRecord', 2);
INSERT INTO `sys_menu_item` VALUES (31, 12, '/contact/list', 1);
INSERT INTO `sys_menu_item` VALUES (32, 13, '/wkLeave/report', 1);
INSERT INTO `sys_menu_item` VALUES (33, 13, '/wkOvertime/report', 2);
INSERT INTO `sys_menu_item` VALUES (34, 13, '/project/report', 3);
INSERT INTO `sys_menu_item` VALUES (35, 14, '/user/list', 1);
INSERT INTO `sys_menu_item` VALUES (36, 14, '/role/list', 2);
INSERT INTO `sys_menu_item` VALUES (37, 14, '/activity/list', 3);
INSERT INTO `sys_menu_item` VALUES (38, 14, '/config/info', 4);
INSERT INTO `sys_menu_item` VALUES (39, 14, '/approving/device/list', 5);
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `PERMISSION_ID` varchar(255) NOT NULL DEFAULT '' COMMENT '权限ID',
  `PARENT_ID` varchar(255) NOT NULL DEFAULT '' COMMENT '父权限ID',
  `NAME` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL',
  PRIMARY KEY (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('01', '0', '主页', NULL, '');
INSERT INTO `sys_permission` VALUES ('02', '0', '待批示', NULL, '');
INSERT INTO `sys_permission` VALUES ('03', '0', '账务管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('04', '0', '案件管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('05', '0', '报价管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('06', '0', '申请管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('07', '0', '厂商联络表', NULL, '');
INSERT INTO `sys_permission` VALUES ('08', '0', '客户联络表', NULL, '');
INSERT INTO `sys_permission` VALUES ('09', '0', '个人资料管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('10', '0', '休假管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('11', '0', '设备管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('12', '0', '同事通讯录', NULL, '');
INSERT INTO `sys_permission` VALUES ('13', '0', '报表管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('14', '0', '系统设定', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0201', '02', '待批示的案件', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0202', '02', '待批示的报价', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0203', '02', '请款申请', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0204', '02', '请假申请', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0205', '02', '加班申请单', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0206', '02', '坏账管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0207', '02', '编辑厂商', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0208', '02', '编辑客户', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0209', '02', '成本分析单', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0301', '03', '成本分析单', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0302', '03', '坏账管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0303', '03', '收款小计', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0401', '04', '案件一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0501', '05', '报价一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0502', '05', '新增报价', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0503', '05', '报价项', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0601', '06', '请假申请', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0602', '06', '加班申请单', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0603', '06', '请款申请', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0701', '07', '厂商一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0702', '07', '新增厂商', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0801', '08', '客户一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0802', '08', '新增客户', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0901', '09', '员工基本资料', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0902', '09', '所使用的设备列表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0903', '09', '休假小计', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0904', '09', '案件一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X0905', '09', '修改密码', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1001', '10', '休假一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1101', '11', '设备一览', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1102', '11', '设备借出记录', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1201', '12', '同事通讯录', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1301', '13', '员工请假报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1302', '13', '员工加班报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1303', '13', '年度开案数量报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1304', '13', '开案总成本报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1305', '13', '开案日平均成本报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1306', '13', '开案总利润报表', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1401', '14', '用户管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1402', '14', '活动管理', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1403', '14', '参数配置', NULL, '');
INSERT INTO `sys_permission` VALUES ('X1404', '14', '设备管理', '', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission_point
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_point`;
CREATE TABLE `sys_permission_point` (
  `PERMISSION_POINT_ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限点ID',
  `VALUE` varchar(255) DEFAULT NULL COMMENT '权限点值',
  `PERMISSION_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PERMISSION_POINT_ID`),
  KEY `FKD6A3CA52FD4FAB28` (`PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission_point
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission_point` VALUES (1, '/index', '01');
INSERT INTO `sys_permission_point` VALUES (2, 'global.index', '01');
INSERT INTO `sys_permission_point` VALUES (3, 'menu.pendingInstructions', '02');
INSERT INTO `sys_permission_point` VALUES (4, '/approving/project/list', 'X0201');
INSERT INTO `sys_permission_point` VALUES (5, '/approving/project/data', 'X0201');
INSERT INTO `sys_permission_point` VALUES (8, 'menu.financialManagement', '03');
INSERT INTO `sys_permission_point` VALUES (9, 'menu.caseManagement', '04');
INSERT INTO `sys_permission_point` VALUES (10, 'menu.quoteManagement', '05');
INSERT INTO `sys_permission_point` VALUES (11, 'menu.applicationManagement', '06');
INSERT INTO `sys_permission_point` VALUES (12, 'menu.manufacturerContactForm', '07');
INSERT INTO `sys_permission_point` VALUES (13, 'menu.customerContactForm', '08');
INSERT INTO `sys_permission_point` VALUES (14, 'menu.personalInformationManagement', '09');
INSERT INTO `sys_permission_point` VALUES (15, 'menu.leaveManagement', '10');
INSERT INTO `sys_permission_point` VALUES (16, 'menu.equipmentManagement', '11');
INSERT INTO `sys_permission_point` VALUES (17, 'menu.contactsColleague', '12');
INSERT INTO `sys_permission_point` VALUES (18, 'menu.reportManager', '13');
INSERT INTO `sys_permission_point` VALUES (19, 'menu.rightsManagement', '14');
INSERT INTO `sys_permission_point` VALUES (20, '/project/add', 'X0301');
INSERT INTO `sys_permission_point` VALUES (21, '/cost/coseAnalysis/data', 'X0301');
INSERT INTO `sys_permission_point` VALUES (23, '/project/list', 'X0401');
INSERT INTO `sys_permission_point` VALUES (24, '/project/data', 'X0401');
INSERT INTO `sys_permission_point` VALUES (25, '/quotation/list', 'X0501');
INSERT INTO `sys_permission_point` VALUES (26, '/quotation/data', 'X0501');
INSERT INTO `sys_permission_point` VALUES (27, '/wkLeave/applyList', 'X0601');
INSERT INTO `sys_permission_point` VALUES (28, '/wkLeave/applyListData', 'X0601');
INSERT INTO `sys_permission_point` VALUES (29, '/supplier/list', 'X0701');
INSERT INTO `sys_permission_point` VALUES (30, '/supplier/data', 'X0701');
INSERT INTO `sys_permission_point` VALUES (31, '/custom/list', 'X0801');
INSERT INTO `sys_permission_point` VALUES (32, '/custom/data', 'X0801');
INSERT INTO `sys_permission_point` VALUES (33, '/personalData/basicInfo', 'X0901');
INSERT INTO `sys_permission_point` VALUES (34, '/personalData/basicInfoEdit', 'X0901');
INSERT INTO `sys_permission_point` VALUES (35, '/wkLeave/list', 'X1001');
INSERT INTO `sys_permission_point` VALUES (36, '/wkLeave/data', 'X1001');
INSERT INTO `sys_permission_point` VALUES (37, '/device/list', 'X1101');
INSERT INTO `sys_permission_point` VALUES (38, '/device/data', 'X1101');
INSERT INTO `sys_permission_point` VALUES (39, '/contact/list', 'X1201');
INSERT INTO `sys_permission_point` VALUES (40, '/contact/data', 'X1201');
INSERT INTO `sys_permission_point` VALUES (41, '/wkLeave/report', 'X1301');
INSERT INTO `sys_permission_point` VALUES (42, '/wkLeave/report/data', 'X1301');
INSERT INTO `sys_permission_point` VALUES (43, '/user/list', 'X1401');
INSERT INTO `sys_permission_point` VALUES (44, '/user/data', 'X1401');
INSERT INTO `sys_permission_point` VALUES (56, 'menu.userManagement', 'X1401');
INSERT INTO `sys_permission_point` VALUES (57, '/approving/quotation/list\r\n', 'X0202');
INSERT INTO `sys_permission_point` VALUES (58, '/approving/quotation/data', 'X0202');
INSERT INTO `sys_permission_point` VALUES (59, '/approving/project/approve', 'X0201');
INSERT INTO `sys_permission_point` VALUES (60, '/approving/project/reject', 'X0201');
INSERT INTO `sys_permission_point` VALUES (61, '/approving/quotation/list', 'X0202');
INSERT INTO `sys_permission_point` VALUES (62, '/approving/quotation/data', 'X0202');
INSERT INTO `sys_permission_point` VALUES (63, '/approving/quotation/approve', 'X0202');
INSERT INTO `sys_permission_point` VALUES (64, '/approving/quotation/reject', 'X0202');
INSERT INTO `sys_permission_point` VALUES (65, '/approving/projectFee/list', 'X0203');
INSERT INTO `sys_permission_point` VALUES (66, '/approving/projectFee/data', 'X0203');
INSERT INTO `sys_permission_point` VALUES (67, '/approving/projectFee/approve', 'X0203');
INSERT INTO `sys_permission_point` VALUES (68, '/approving/projectFee/reject', 'X0203');
INSERT INTO `sys_permission_point` VALUES (69, '/approving/wkLeave/list', 'X0204');
INSERT INTO `sys_permission_point` VALUES (70, '/approving/wkLeave/data', 'X0204');
INSERT INTO `sys_permission_point` VALUES (71, '/approving/wkLeave/approve', 'X0204');
INSERT INTO `sys_permission_point` VALUES (72, '/approving/wkLeave/reject', 'X0204');
INSERT INTO `sys_permission_point` VALUES (73, '/approving/wkOvertime/list', 'X0205');
INSERT INTO `sys_permission_point` VALUES (74, '/approving/wkOvertime/data', 'X0205');
INSERT INTO `sys_permission_point` VALUES (75, '/approving/wkOvertime/approve', 'X0205');
INSERT INTO `sys_permission_point` VALUES (76, '/approving/wkOvertime/reject', 'X0205');
INSERT INTO `sys_permission_point` VALUES (77, '/approving/badAccount/list', 'X0206');
INSERT INTO `sys_permission_point` VALUES (78, '/approving/badAccount/data', 'X0206');
INSERT INTO `sys_permission_point` VALUES (79, '/approving/badAccount/approve', 'X0206');
INSERT INTO `sys_permission_point` VALUES (80, '/approving/badAccount/reject', 'X0206');
INSERT INTO `sys_permission_point` VALUES (81, '/approving/edSupplier/list', 'X0207');
INSERT INTO `sys_permission_point` VALUES (82, '/approving/edSupplier/data', 'X0207');
INSERT INTO `sys_permission_point` VALUES (83, '/approving/edSupplier/approve', 'X0207');
INSERT INTO `sys_permission_point` VALUES (84, '/approving/edSupplier/reject', 'X0207');
INSERT INTO `sys_permission_point` VALUES (85, '/approving/customer/list', 'X0208');
INSERT INTO `sys_permission_point` VALUES (86, '/approving/customer/data', 'X0208');
INSERT INTO `sys_permission_point` VALUES (87, '/approving/customer/approve', 'X0208');
INSERT INTO `sys_permission_point` VALUES (88, '/approving/customer/reject', 'X0208');
INSERT INTO `sys_permission_point` VALUES (89, '/badAccount/applyList', 'X0302');
INSERT INTO `sys_permission_point` VALUES (90, '/badAccount/applyListData', 'X0302');
INSERT INTO `sys_permission_point` VALUES (91, '/badAccount/apply/add', 'X0302');
INSERT INTO `sys_permission_point` VALUES (92, '/badAccount/apply/edit', 'X0302');
INSERT INTO `sys_permission_point` VALUES (93, '/badAccount/apply/addOrUpdate', 'X0302');
INSERT INTO `sys_permission_point` VALUES (94, '/badAccount/invalid', 'X0302');
INSERT INTO `sys_permission_point` VALUES (95, '/collectMoney/list', 'X0303');
INSERT INTO `sys_permission_point` VALUES (96, '/collectMoney/data', 'X0303');
INSERT INTO `sys_permission_point` VALUES (97, '/collectMoney/add', 'X0303');
INSERT INTO `sys_permission_point` VALUES (98, '/collectMoney/save', 'X0303');
INSERT INTO `sys_permission_point` VALUES (99, '/collectMoney/edit', 'X0303');
INSERT INTO `sys_permission_point` VALUES (100, '/collectMoney/update', 'X0303');
INSERT INTO `sys_permission_point` VALUES (101, '/quotation/addQuotation', 'X0502');
INSERT INTO `sys_permission_point` VALUES (102, '/quotation/save', 'X0502');
INSERT INTO `sys_permission_point` VALUES (103, '/quotation/edit', 'X0501');
INSERT INTO `sys_permission_point` VALUES (104, '/cost/coseAnalysis/list', 'X0301');
INSERT INTO `sys_permission_point` VALUES (105, '/cost/coseAnalysis', 'X0301');
INSERT INTO `sys_permission_point` VALUES (106, '/cost/coseAnalysis/save', 'X0301');
INSERT INTO `sys_permission_point` VALUES (107, '/quotation/update', 'X0501');
INSERT INTO `sys_permission_point` VALUES (108, '/quotation/quotationItem', 'X0501');
INSERT INTO `sys_permission_point` VALUES (109, '/quotationItem/data', 'X0501');
INSERT INTO `sys_permission_point` VALUES (110, '/quotationItem/editItem', 'X0501');
INSERT INTO `sys_permission_point` VALUES (111, '/quotationItem/update', 'X0501');
INSERT INTO `sys_permission_point` VALUES (112, '/quotationItem/doDelete', 'X0501');
INSERT INTO `sys_permission_point` VALUES (113, '/quotationItem/add', 'X0501');
INSERT INTO `sys_permission_point` VALUES (114, '/quotationItem/saveItem', 'X0501');
INSERT INTO `sys_permission_point` VALUES (115, '/wkLeave/apply/add', 'X0601');
INSERT INTO `sys_permission_point` VALUES (116, '/wkLeave/apply/edit', 'X0601');
INSERT INTO `sys_permission_point` VALUES (117, '/wkLeave/apply/addOrUpdate', 'X0601');
INSERT INTO `sys_permission_point` VALUES (118, '/wkLeave/invalid', 'X0601');
INSERT INTO `sys_permission_point` VALUES (119, '/wkOvertime/applyList', 'X0602');
INSERT INTO `sys_permission_point` VALUES (120, '/wkOvertime/applyListData', 'X0602');
INSERT INTO `sys_permission_point` VALUES (121, '/wkOvertime/apply/add', 'X0602');
INSERT INTO `sys_permission_point` VALUES (122, '/wkOvertime/apply/edit', 'X0602');
INSERT INTO `sys_permission_point` VALUES (123, '/wkOvertime/apply/addOrUpdate', 'X0602');
INSERT INTO `sys_permission_point` VALUES (124, '/wkOvertime/invalid', 'X0602');
INSERT INTO `sys_permission_point` VALUES (125, '/projectFee/applyList', 'X0603');
INSERT INTO `sys_permission_point` VALUES (126, '/projectFee/applyListData', 'X0603');
INSERT INTO `sys_permission_point` VALUES (127, '/projectFee/apply/add', 'X0603');
INSERT INTO `sys_permission_point` VALUES (128, '/projectFee/apply/edit', 'X0603');
INSERT INTO `sys_permission_point` VALUES (129, '/projectFee/apply/addOrUpdate', 'X0603');
INSERT INTO `sys_permission_point` VALUES (130, '/supplier/info', 'X0701');
INSERT INTO `sys_permission_point` VALUES (131, '/supplier/exportPdf', 'X0701');
INSERT INTO `sys_permission_point` VALUES (132, '/supplier/add', 'X0702');
INSERT INTO `sys_permission_point` VALUES (133, '/supplier/edit', 'X0701');
INSERT INTO `sys_permission_point` VALUES (134, '/supplier/update', 'X0701');
INSERT INTO `sys_permission_point` VALUES (135, '/supplier/doDelete', 'X0701');
INSERT INTO `sys_permission_point` VALUES (136, '/supplier/deleteContact', 'X0702');
INSERT INTO `sys_permission_point` VALUES (137, '/custom/info', 'X0801');
INSERT INTO `sys_permission_point` VALUES (138, '/custom/exportPdf', 'X0801');
INSERT INTO `sys_permission_point` VALUES (139, '/custom/add', 'X0802');
INSERT INTO `sys_permission_point` VALUES (140, '/custom/edit', 'X0801');
INSERT INTO `sys_permission_point` VALUES (141, '/custom/update', 'X0801');
INSERT INTO `sys_permission_point` VALUES (142, '/custom/doDelete', 'X0801');
INSERT INTO `sys_permission_point` VALUES (143, '/custom/deleteContact', 'X0802');
INSERT INTO `sys_permission_point` VALUES (144, '/device/usedList', 'X0902');
INSERT INTO `sys_permission_point` VALUES (145, '/personalData/leaveSubtotal', 'X0903');
INSERT INTO `sys_permission_point` VALUES (146, '/personalData/caseList', 'X0904');
INSERT INTO `sys_permission_point` VALUES (147, '/personalData/changePassword', 'X0905');
INSERT INTO `sys_permission_point` VALUES (148, '/personalData/editPassword', 'X0905');
INSERT INTO `sys_permission_point` VALUES (149, '/device/add', 'X1101');
INSERT INTO `sys_permission_point` VALUES (150, '/device/save', 'X1101');
INSERT INTO `sys_permission_point` VALUES (151, '/device/edit', 'X1101');
INSERT INTO `sys_permission_point` VALUES (152, '/device/update', 'X1101');
INSERT INTO `sys_permission_point` VALUES (153, '/device/doDelete', 'X1101');
INSERT INTO `sys_permission_point` VALUES (154, '/device/borrowRecord', 'X1102');
INSERT INTO `sys_permission_point` VALUES (155, '/deviceBorrow/data', 'X1102');
INSERT INTO `sys_permission_point` VALUES (156, '/device/revert', 'X1102');
INSERT INTO `sys_permission_point` VALUES (157, '/device/applyBorrow', 'X1101');
INSERT INTO `sys_permission_point` VALUES (158, '/wkOvertime/report', 'X1302');
INSERT INTO `sys_permission_point` VALUES (159, '/wkOvertime/report/data', 'X1302');
INSERT INTO `sys_permission_point` VALUES (160, '/project/report', 'X1303');
INSERT INTO `sys_permission_point` VALUES (161, '/project/report/data', 'X1303');
INSERT INTO `sys_permission_point` VALUES (162, '/user/exportPdf', 'X1401');
INSERT INTO `sys_permission_point` VALUES (163, '/user/add', 'X1401');
INSERT INTO `sys_permission_point` VALUES (164, '/user/save', 'X1401');
INSERT INTO `sys_permission_point` VALUES (165, '/user/edit', 'X1401');
INSERT INTO `sys_permission_point` VALUES (166, '/user/update', 'X1401');
INSERT INTO `sys_permission_point` VALUES (167, '/user/changeStatus', 'X1401');
INSERT INTO `sys_permission_point` VALUES (170, 'menu.casesPendingInstructions', 'X0201');
INSERT INTO `sys_permission_point` VALUES (171, 'menu.beInstructedQuote', 'X0202');
INSERT INTO `sys_permission_point` VALUES (172, 'menu.applyForFunds', 'X0203');
INSERT INTO `sys_permission_point` VALUES (174, 'wkOvertime.application', 'X0204');
INSERT INTO `sys_permission_point` VALUES (175, 'menu.badManagement', 'X0206');
INSERT INTO `sys_permission_point` VALUES (176, 'supplier.editSupplier', 'X0207');
INSERT INTO `sys_permission_point` VALUES (177, 'customer.editCustomer', 'X0208');
INSERT INTO `sys_permission_point` VALUES (178, 'menu.costAnalysisOfSingle', 'X0209');
INSERT INTO `sys_permission_point` VALUES (179, 'menu.costAnalysisOfSingle', 'X0301');
INSERT INTO `sys_permission_point` VALUES (180, 'menu.badManagement', 'X0302');
INSERT INTO `sys_permission_point` VALUES (181, 'menu.collectMoney', 'X0303');
INSERT INTO `sys_permission_point` VALUES (182, 'menu.casesList', 'X0401');
INSERT INTO `sys_permission_point` VALUES (183, 'menu.quoteList', 'X0501');
INSERT INTO `sys_permission_point` VALUES (184, 'quotation.addPrice', 'X0502');
INSERT INTO `sys_permission_point` VALUES (185, 'wkLeave.ApplicationForLeave', 'X0601');
INSERT INTO `sys_permission_point` VALUES (186, 'wkOvertime.application', 'X0602');
INSERT INTO `sys_permission_point` VALUES (187, 'menu.applyForFunds', 'X0603');
INSERT INTO `sys_permission_point` VALUES (188, 'menu.manufacturerList', 'X0701');
INSERT INTO `sys_permission_point` VALUES (189, 'supplier.add', 'X0702');
INSERT INTO `sys_permission_point` VALUES (190, 'menu.customerList', 'X0801');
INSERT INTO `sys_permission_point` VALUES (191, 'menu.customerContact', 'X0802');
INSERT INTO `sys_permission_point` VALUES (192, 'menu.staffProfile', 'X0901');
INSERT INTO `sys_permission_point` VALUES (193, 'menu.equipmentUsedList', 'X0902');
INSERT INTO `sys_permission_point` VALUES (194, 'menu.leaveSubtotal', 'X0903');
INSERT INTO `sys_permission_point` VALUES (195, 'menu.casesList', 'X0904');
INSERT INTO `sys_permission_point` VALUES (196, 'menu.changePassword', 'X0905');
INSERT INTO `sys_permission_point` VALUES (197, 'menu.leaveGlance', 'X1001');
INSERT INTO `sys_permission_point` VALUES (198, 'menu.equipmentList', 'X1101');
INSERT INTO `sys_permission_point` VALUES (199, 'device.borrow.borrowRecord', 'X1102');
INSERT INTO `sys_permission_point` VALUES (200, 'sys.userContact', 'X1201');
INSERT INTO `sys_permission_point` VALUES (201, 'menu.employeesLeaveReports', 'X1301');
INSERT INTO `sys_permission_point` VALUES (202, 'menu.overtimeReports', 'X1302');
INSERT INTO `sys_permission_point` VALUES (203, 'menu.annualReportsTheNumberOfOpenCases', 'X1303');
INSERT INTO `sys_permission_point` VALUES (204, 'menu.theTotalCostToOpenTheCaseReport', 'X1304');
INSERT INTO `sys_permission_point` VALUES (205, 'menu.openDailyAverageCaseCostReport', 'X1305');
INSERT INTO `sys_permission_point` VALUES (206, 'menu.grossProfitOpenCaseReports', 'X1306');
INSERT INTO `sys_permission_point` VALUES (207, 'quotation.item', 'X0503');
INSERT INTO `sys_permission_point` VALUES (209, '/costAnalysis/totalCost/report', 'X1304');
INSERT INTO `sys_permission_point` VALUES (210, '/costAnalysis/totalCost/report/data', 'X1304');
INSERT INTO `sys_permission_point` VALUES (211, '/costAnalysis/dailyaverage/report', 'X1305');
INSERT INTO `sys_permission_point` VALUES (212, '/costAnalysis/dailyaverage/report/data', 'X1305');
INSERT INTO `sys_permission_point` VALUES (213, '/costAnalysis/grossProfit/report', 'X1306');
INSERT INTO `sys_permission_point` VALUES (214, '/costAnalysis/grossProfit/report/data', 'X1306');
INSERT INTO `sys_permission_point` VALUES (215, '/custom/type', 'X0802');
INSERT INTO `sys_permission_point` VALUES (216, '/custom/cusTypeSave', 'X0802');
INSERT INTO `sys_permission_point` VALUES (217, '/customType/deleteType', 'X0802');
INSERT INTO `sys_permission_point` VALUES (218, '/cost/coseAnalysis/reportpdf', 'X0301');
INSERT INTO `sys_permission_point` VALUES (219, '/getHeadIcon', 'X0901');
INSERT INTO `sys_permission_point` VALUES (220, '/getHeadIcon', 'X0802');
INSERT INTO `sys_permission_point` VALUES (221, '/approving/costAnalysis/list', 'X0209');
INSERT INTO `sys_permission_point` VALUES (222, '/approving/costAnalysis/data', 'X0209');
INSERT INTO `sys_permission_point` VALUES (223, '/approving/costAnalysis/approve', 'X0209');
INSERT INTO `sys_permission_point` VALUES (224, '/approving/costAnalysis/reject', 'X0209');
INSERT INTO `sys_permission_point` VALUES (225, '/supplier/save', 'X0702');
INSERT INTO `sys_permission_point` VALUES (226, '/susupplier/type', 'X0701');
INSERT INTO `sys_permission_point` VALUES (227, '/supplier/supplierTypeSave', 'X0701');
INSERT INTO `sys_permission_point` VALUES (228, '/supplierType/deleteType', 'X0701');
INSERT INTO `sys_permission_point` VALUES (229, '/project/save', 'X0401');
INSERT INTO `sys_permission_point` VALUES (230, '/quotation/getQuotationId', 'X0401');
INSERT INTO `sys_permission_point` VALUES (231, '/cost/closed', 'X0401');
INSERT INTO `sys_permission_point` VALUES (232, '/quotation/vlidateSave', 'X0502');
INSERT INTO `sys_permission_point` VALUES (233, '/project/info', 'X0301');
INSERT INTO `sys_permission_point` VALUES (234, '/device/applyBorrowSubmit', 'X1101');
INSERT INTO `sys_permission_point` VALUES (235, '/device/returnBack', 'X1102');
INSERT INTO `sys_permission_point` VALUES (236, '/device/borrowRecordEach', 'X1102');
INSERT INTO `sys_permission_point` VALUES (237, '/custom/inputCusType', 'X0802');
INSERT INTO `sys_permission_point` VALUES (238, '/supplier/inputSupType', 'X0702');
INSERT INTO `sys_permission_point` VALUES (239, '/custom/save', 'X0802');
INSERT INTO `sys_permission_point` VALUES (240, '/user/doDelete', 'X1401');
INSERT INTO `sys_permission_point` VALUES (242, '/activity/data', 'X1402');
INSERT INTO `sys_permission_point` VALUES (243, 'activity.define', 'X1402');
INSERT INTO `sys_permission_point` VALUES (244, '/activity/list', 'X1402');
INSERT INTO `sys_permission_point` VALUES (245, '/activity/add', 'X1402');
INSERT INTO `sys_permission_point` VALUES (246, '/activity/save', 'X1402');
INSERT INTO `sys_permission_point` VALUES (247, '/activity/edit', 'X1402');
INSERT INTO `sys_permission_point` VALUES (248, '/activity/update', 'X1402');
INSERT INTO `sys_permission_point` VALUES (249, '/project/edit', 'X0301');
INSERT INTO `sys_permission_point` VALUES (250, '/project/update', 'X0301');
INSERT INTO `sys_permission_point` VALUES (251, '/projectFee/apply/info', 'X0603');
INSERT INTO `sys_permission_point` VALUES (252, '/quotation/export', 'X0502');
INSERT INTO `sys_permission_point` VALUES (253, '/config/info', 'X1403');
INSERT INTO `sys_permission_point` VALUES (254, 'global.parameterConfig', 'X1403');
INSERT INTO `sys_permission_point` VALUES (255, '/config/update', 'X1403');
INSERT INTO `sys_permission_point` VALUES (256, '/personalData/uploadHeadIcon', 'X0901');
INSERT INTO `sys_permission_point` VALUES (257, 'wkLeave.ApplicationForLeave', 'X0205');
INSERT INTO `sys_permission_point` VALUES (258, '/projectFee/applyFeeList', 'X0603');
INSERT INTO `sys_permission_point` VALUES (259, '/projectFee/applyFeeListData', 'X0603');
INSERT INTO `sys_permission_point` VALUES (260, '/projectFee/apply/treeNodes', 'X0603');
INSERT INTO `sys_permission_point` VALUES (261, '/quotation/listData', 'X0603');
INSERT INTO `sys_permission_point` VALUES (263, '/approving/device/list', 'X1404');
INSERT INTO `sys_permission_point` VALUES (264, 'menu.equipmentManagement', 'X1404');
INSERT INTO `sys_permission_point` VALUES (265, '/approving/deviceBorrow/data', 'X1404');
INSERT INTO `sys_permission_point` VALUES (266, '/approving/deviceBorrow/add', 'X1404');
INSERT INTO `sys_permission_point` VALUES (267, '/approving/deviceBorrow/saveOrUpdate', 'X1404');
INSERT INTO `sys_permission_point` VALUES (269, '/approving/deviceBorrow/delete', 'X1404');
INSERT INTO `sys_permission_point` VALUES (270, '/approving/deviceBorrow/retuBack', 'X1404');
INSERT INTO `sys_permission_point` VALUES (271, '/approving/deviceBorrow/info', 'X1404');
INSERT INTO `sys_permission_point` VALUES (272, '/quotation/quotationItem/data', 'X0501');
INSERT INTO `sys_permission_point` VALUES (273, '/project/checkHistory', 'X0401');
INSERT INTO `sys_permission_point` VALUES (274, '/project/getHistoryData', 'X0401');
INSERT INTO `sys_permission_point` VALUES (275, '/projectFee/apply/doDelete', 'X0603');
INSERT INTO `sys_permission_point` VALUES (276, '/deviceBorrow/data', 'X0902');
INSERT INTO `sys_permission_point` VALUES (277, '/supplier/chooseYear', 'X0702');
INSERT INTO `sys_permission_point` VALUES (278, '/custom/chooseYear', 'X0801');
INSERT INTO `sys_permission_point` VALUES (279, '/project/closeProject', 'X0401');
INSERT INTO `sys_permission_point` VALUES (280, '/config/setIsUsed', 'X1401');
INSERT INTO `sys_permission_point` VALUES (281, '/projectFee/otherApply/add', 'X0603');
INSERT INTO `sys_permission_point` VALUES (282, '/projectFee/otherApply/addOrUpdate', 'X0603');
INSERT INTO `sys_permission_point` VALUES (283, '/project/queryCount', 'X0401');
INSERT INTO `sys_permission_point` VALUES (284, '/projectFee/queryQuotationListAmount', 'X0601');
INSERT INTO `sys_permission_point` VALUES (285, '/approving/projectFee/pass', 'X0201');
INSERT INTO `sys_permission_point` VALUES (286, '/project/queryCustomers', 'X0401');
INSERT INTO `sys_permission_point` VALUES (287, '/quotationItem/querySuppliers', 'X0401');
INSERT INTO `sys_permission_point` VALUES (288, '/project/closed', 'X0401');
INSERT INTO `sys_permission_point` VALUES (289, '/projectFee/otherApply/edit', 'X0601');
INSERT INTO `sys_permission_point` VALUES (290, '/wkOvertime/apply/info', 'X0601');
INSERT INTO `sys_permission_point` VALUES (291, '/wkOvertime/apply/info', 'X0201');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `NAME` varchar(255) NOT NULL COMMENT '名称',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '系統管理者', '');
INSERT INTO `sys_role` VALUES (2, '企劃&活動部', '');
INSERT INTO `sys_role` VALUES (3, '設計部', '');
INSERT INTO `sys_role` VALUES (5, '普通员工', '');
INSERT INTO `sys_role` VALUES (6, '主管', '');
INSERT INTO `sys_role` VALUES (7, '銷售部', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `PERMISSION_ID` varchar(255) NOT NULL DEFAULT '' COMMENT '权限ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`PERMISSION_ID`,`ROLE_ID`),
  KEY `FK7D9867A6A330C848` (`ROLE_ID`),
  KEY `FK7D9867A6FD4FAB28` (`PERMISSION_ID`),
  CONSTRAINT `FK7D9867A6A330C848` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `FK7D9867A6FD4FAB28` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `sys_permission` (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('01', 1);
INSERT INTO `sys_role_permission` VALUES ('02', 1);
INSERT INTO `sys_role_permission` VALUES ('03', 1);
INSERT INTO `sys_role_permission` VALUES ('04', 1);
INSERT INTO `sys_role_permission` VALUES ('05', 1);
INSERT INTO `sys_role_permission` VALUES ('06', 1);
INSERT INTO `sys_role_permission` VALUES ('07', 1);
INSERT INTO `sys_role_permission` VALUES ('08', 1);
INSERT INTO `sys_role_permission` VALUES ('09', 1);
INSERT INTO `sys_role_permission` VALUES ('10', 1);
INSERT INTO `sys_role_permission` VALUES ('11', 1);
INSERT INTO `sys_role_permission` VALUES ('12', 1);
INSERT INTO `sys_role_permission` VALUES ('13', 1);
INSERT INTO `sys_role_permission` VALUES ('14', 1);
INSERT INTO `sys_role_permission` VALUES ('X0201', 1);
INSERT INTO `sys_role_permission` VALUES ('X0202', 1);
INSERT INTO `sys_role_permission` VALUES ('X0203', 1);
INSERT INTO `sys_role_permission` VALUES ('X0204', 1);
INSERT INTO `sys_role_permission` VALUES ('X0205', 1);
INSERT INTO `sys_role_permission` VALUES ('X0206', 1);
INSERT INTO `sys_role_permission` VALUES ('X0207', 1);
INSERT INTO `sys_role_permission` VALUES ('X0208', 1);
INSERT INTO `sys_role_permission` VALUES ('X0209', 1);
INSERT INTO `sys_role_permission` VALUES ('X0301', 1);
INSERT INTO `sys_role_permission` VALUES ('X0302', 1);
INSERT INTO `sys_role_permission` VALUES ('X0303', 1);
INSERT INTO `sys_role_permission` VALUES ('X0401', 1);
INSERT INTO `sys_role_permission` VALUES ('X0501', 1);
INSERT INTO `sys_role_permission` VALUES ('X0502', 1);
INSERT INTO `sys_role_permission` VALUES ('X0503', 1);
INSERT INTO `sys_role_permission` VALUES ('X0601', 1);
INSERT INTO `sys_role_permission` VALUES ('X0602', 1);
INSERT INTO `sys_role_permission` VALUES ('X0603', 1);
INSERT INTO `sys_role_permission` VALUES ('X0701', 1);
INSERT INTO `sys_role_permission` VALUES ('X0702', 1);
INSERT INTO `sys_role_permission` VALUES ('X0801', 1);
INSERT INTO `sys_role_permission` VALUES ('X0802', 1);
INSERT INTO `sys_role_permission` VALUES ('X0901', 1);
INSERT INTO `sys_role_permission` VALUES ('X0902', 1);
INSERT INTO `sys_role_permission` VALUES ('X0903', 1);
INSERT INTO `sys_role_permission` VALUES ('X0904', 1);
INSERT INTO `sys_role_permission` VALUES ('X0905', 1);
INSERT INTO `sys_role_permission` VALUES ('X1001', 1);
INSERT INTO `sys_role_permission` VALUES ('X1101', 1);
INSERT INTO `sys_role_permission` VALUES ('X1102', 1);
INSERT INTO `sys_role_permission` VALUES ('X1201', 1);
INSERT INTO `sys_role_permission` VALUES ('X1301', 1);
INSERT INTO `sys_role_permission` VALUES ('X1302', 1);
INSERT INTO `sys_role_permission` VALUES ('X1303', 1);
INSERT INTO `sys_role_permission` VALUES ('X1304', 1);
INSERT INTO `sys_role_permission` VALUES ('X1305', 1);
INSERT INTO `sys_role_permission` VALUES ('X1306', 1);
INSERT INTO `sys_role_permission` VALUES ('X1401', 1);
INSERT INTO `sys_role_permission` VALUES ('X1402', 1);
INSERT INTO `sys_role_permission` VALUES ('X1403', 1);
INSERT INTO `sys_role_permission` VALUES ('X1404', 1);
INSERT INTO `sys_role_permission` VALUES ('01', 2);
INSERT INTO `sys_role_permission` VALUES ('03', 2);
INSERT INTO `sys_role_permission` VALUES ('04', 2);
INSERT INTO `sys_role_permission` VALUES ('05', 2);
INSERT INTO `sys_role_permission` VALUES ('06', 2);
INSERT INTO `sys_role_permission` VALUES ('07', 2);
INSERT INTO `sys_role_permission` VALUES ('08', 2);
INSERT INTO `sys_role_permission` VALUES ('09', 2);
INSERT INTO `sys_role_permission` VALUES ('10', 2);
INSERT INTO `sys_role_permission` VALUES ('11', 2);
INSERT INTO `sys_role_permission` VALUES ('12', 2);
INSERT INTO `sys_role_permission` VALUES ('13', 2);
INSERT INTO `sys_role_permission` VALUES ('X0301', 2);
INSERT INTO `sys_role_permission` VALUES ('X0302', 2);
INSERT INTO `sys_role_permission` VALUES ('X0303', 2);
INSERT INTO `sys_role_permission` VALUES ('X0401', 2);
INSERT INTO `sys_role_permission` VALUES ('X0501', 2);
INSERT INTO `sys_role_permission` VALUES ('X0502', 2);
INSERT INTO `sys_role_permission` VALUES ('X0503', 2);
INSERT INTO `sys_role_permission` VALUES ('X0601', 2);
INSERT INTO `sys_role_permission` VALUES ('X0602', 2);
INSERT INTO `sys_role_permission` VALUES ('X0603', 2);
INSERT INTO `sys_role_permission` VALUES ('X0701', 2);
INSERT INTO `sys_role_permission` VALUES ('X0702', 2);
INSERT INTO `sys_role_permission` VALUES ('X0801', 2);
INSERT INTO `sys_role_permission` VALUES ('X0802', 2);
INSERT INTO `sys_role_permission` VALUES ('X0901', 2);
INSERT INTO `sys_role_permission` VALUES ('X0902', 2);
INSERT INTO `sys_role_permission` VALUES ('X0903', 2);
INSERT INTO `sys_role_permission` VALUES ('X0904', 2);
INSERT INTO `sys_role_permission` VALUES ('X0905', 2);
INSERT INTO `sys_role_permission` VALUES ('X1001', 2);
INSERT INTO `sys_role_permission` VALUES ('X1101', 2);
INSERT INTO `sys_role_permission` VALUES ('X1102', 2);
INSERT INTO `sys_role_permission` VALUES ('X1201', 2);
INSERT INTO `sys_role_permission` VALUES ('X1301', 2);
INSERT INTO `sys_role_permission` VALUES ('X1302', 2);
INSERT INTO `sys_role_permission` VALUES ('X1303', 2);
INSERT INTO `sys_role_permission` VALUES ('X1304', 2);
INSERT INTO `sys_role_permission` VALUES ('X1305', 2);
INSERT INTO `sys_role_permission` VALUES ('X1306', 2);
INSERT INTO `sys_role_permission` VALUES ('X1401', 2);
INSERT INTO `sys_role_permission` VALUES ('X1402', 2);
INSERT INTO `sys_role_permission` VALUES ('X1403', 2);
INSERT INTO `sys_role_permission` VALUES ('01', 3);
INSERT INTO `sys_role_permission` VALUES ('03', 3);
INSERT INTO `sys_role_permission` VALUES ('04', 3);
INSERT INTO `sys_role_permission` VALUES ('05', 3);
INSERT INTO `sys_role_permission` VALUES ('06', 3);
INSERT INTO `sys_role_permission` VALUES ('07', 3);
INSERT INTO `sys_role_permission` VALUES ('08', 3);
INSERT INTO `sys_role_permission` VALUES ('09', 3);
INSERT INTO `sys_role_permission` VALUES ('10', 3);
INSERT INTO `sys_role_permission` VALUES ('11', 3);
INSERT INTO `sys_role_permission` VALUES ('12', 3);
INSERT INTO `sys_role_permission` VALUES ('13', 3);
INSERT INTO `sys_role_permission` VALUES ('X0301', 3);
INSERT INTO `sys_role_permission` VALUES ('X0302', 3);
INSERT INTO `sys_role_permission` VALUES ('X0303', 3);
INSERT INTO `sys_role_permission` VALUES ('X0401', 3);
INSERT INTO `sys_role_permission` VALUES ('X0501', 3);
INSERT INTO `sys_role_permission` VALUES ('X0502', 3);
INSERT INTO `sys_role_permission` VALUES ('X0503', 3);
INSERT INTO `sys_role_permission` VALUES ('X0601', 3);
INSERT INTO `sys_role_permission` VALUES ('X0602', 3);
INSERT INTO `sys_role_permission` VALUES ('X0603', 3);
INSERT INTO `sys_role_permission` VALUES ('X0701', 3);
INSERT INTO `sys_role_permission` VALUES ('X0702', 3);
INSERT INTO `sys_role_permission` VALUES ('X0801', 3);
INSERT INTO `sys_role_permission` VALUES ('X0802', 3);
INSERT INTO `sys_role_permission` VALUES ('X0901', 3);
INSERT INTO `sys_role_permission` VALUES ('X0902', 3);
INSERT INTO `sys_role_permission` VALUES ('X0903', 3);
INSERT INTO `sys_role_permission` VALUES ('X0904', 3);
INSERT INTO `sys_role_permission` VALUES ('X0905', 3);
INSERT INTO `sys_role_permission` VALUES ('X1001', 3);
INSERT INTO `sys_role_permission` VALUES ('X1101', 3);
INSERT INTO `sys_role_permission` VALUES ('X1102', 3);
INSERT INTO `sys_role_permission` VALUES ('X1201', 3);
INSERT INTO `sys_role_permission` VALUES ('X1301', 3);
INSERT INTO `sys_role_permission` VALUES ('X1302', 3);
INSERT INTO `sys_role_permission` VALUES ('X1303', 3);
INSERT INTO `sys_role_permission` VALUES ('X1304', 3);
INSERT INTO `sys_role_permission` VALUES ('X1305', 3);
INSERT INTO `sys_role_permission` VALUES ('X1306', 3);
INSERT INTO `sys_role_permission` VALUES ('X1401', 3);
INSERT INTO `sys_role_permission` VALUES ('X1402', 3);
INSERT INTO `sys_role_permission` VALUES ('X1403', 3);
INSERT INTO `sys_role_permission` VALUES ('01', 5);
INSERT INTO `sys_role_permission` VALUES ('03', 5);
INSERT INTO `sys_role_permission` VALUES ('04', 5);
INSERT INTO `sys_role_permission` VALUES ('05', 5);
INSERT INTO `sys_role_permission` VALUES ('06', 5);
INSERT INTO `sys_role_permission` VALUES ('07', 5);
INSERT INTO `sys_role_permission` VALUES ('08', 5);
INSERT INTO `sys_role_permission` VALUES ('09', 5);
INSERT INTO `sys_role_permission` VALUES ('10', 5);
INSERT INTO `sys_role_permission` VALUES ('11', 5);
INSERT INTO `sys_role_permission` VALUES ('12', 5);
INSERT INTO `sys_role_permission` VALUES ('13', 5);
INSERT INTO `sys_role_permission` VALUES ('X0301', 5);
INSERT INTO `sys_role_permission` VALUES ('X0302', 5);
INSERT INTO `sys_role_permission` VALUES ('X0303', 5);
INSERT INTO `sys_role_permission` VALUES ('X0401', 5);
INSERT INTO `sys_role_permission` VALUES ('X0501', 5);
INSERT INTO `sys_role_permission` VALUES ('X0502', 5);
INSERT INTO `sys_role_permission` VALUES ('X0503', 5);
INSERT INTO `sys_role_permission` VALUES ('X0601', 5);
INSERT INTO `sys_role_permission` VALUES ('X0602', 5);
INSERT INTO `sys_role_permission` VALUES ('X0603', 5);
INSERT INTO `sys_role_permission` VALUES ('X0701', 5);
INSERT INTO `sys_role_permission` VALUES ('X0702', 5);
INSERT INTO `sys_role_permission` VALUES ('X0801', 5);
INSERT INTO `sys_role_permission` VALUES ('X0802', 5);
INSERT INTO `sys_role_permission` VALUES ('X0901', 5);
INSERT INTO `sys_role_permission` VALUES ('X0902', 5);
INSERT INTO `sys_role_permission` VALUES ('X0903', 5);
INSERT INTO `sys_role_permission` VALUES ('X0904', 5);
INSERT INTO `sys_role_permission` VALUES ('X0905', 5);
INSERT INTO `sys_role_permission` VALUES ('X1001', 5);
INSERT INTO `sys_role_permission` VALUES ('X1101', 5);
INSERT INTO `sys_role_permission` VALUES ('X1102', 5);
INSERT INTO `sys_role_permission` VALUES ('X1201', 5);
INSERT INTO `sys_role_permission` VALUES ('X1301', 5);
INSERT INTO `sys_role_permission` VALUES ('X1302', 5);
INSERT INTO `sys_role_permission` VALUES ('X1303', 5);
INSERT INTO `sys_role_permission` VALUES ('X1304', 5);
INSERT INTO `sys_role_permission` VALUES ('X1305', 5);
INSERT INTO `sys_role_permission` VALUES ('X1306', 5);
INSERT INTO `sys_role_permission` VALUES ('X1401', 5);
INSERT INTO `sys_role_permission` VALUES ('X1402', 5);
INSERT INTO `sys_role_permission` VALUES ('X1403', 5);
INSERT INTO `sys_role_permission` VALUES ('01', 6);
INSERT INTO `sys_role_permission` VALUES ('02', 6);
INSERT INTO `sys_role_permission` VALUES ('03', 6);
INSERT INTO `sys_role_permission` VALUES ('04', 6);
INSERT INTO `sys_role_permission` VALUES ('05', 6);
INSERT INTO `sys_role_permission` VALUES ('06', 6);
INSERT INTO `sys_role_permission` VALUES ('07', 6);
INSERT INTO `sys_role_permission` VALUES ('08', 6);
INSERT INTO `sys_role_permission` VALUES ('09', 6);
INSERT INTO `sys_role_permission` VALUES ('10', 6);
INSERT INTO `sys_role_permission` VALUES ('X0201', 6);
INSERT INTO `sys_role_permission` VALUES ('X0202', 6);
INSERT INTO `sys_role_permission` VALUES ('X0203', 6);
INSERT INTO `sys_role_permission` VALUES ('X0204', 6);
INSERT INTO `sys_role_permission` VALUES ('X0205', 6);
INSERT INTO `sys_role_permission` VALUES ('X0209', 6);
INSERT INTO `sys_role_permission` VALUES ('X0301', 6);
INSERT INTO `sys_role_permission` VALUES ('X0302', 6);
INSERT INTO `sys_role_permission` VALUES ('X0303', 6);
INSERT INTO `sys_role_permission` VALUES ('X0401', 6);
INSERT INTO `sys_role_permission` VALUES ('X0501', 6);
INSERT INTO `sys_role_permission` VALUES ('X0502', 6);
INSERT INTO `sys_role_permission` VALUES ('X0503', 6);
INSERT INTO `sys_role_permission` VALUES ('X0601', 6);
INSERT INTO `sys_role_permission` VALUES ('X0602', 6);
INSERT INTO `sys_role_permission` VALUES ('X0603', 6);
INSERT INTO `sys_role_permission` VALUES ('X0701', 6);
INSERT INTO `sys_role_permission` VALUES ('X0702', 6);
INSERT INTO `sys_role_permission` VALUES ('X0801', 6);
INSERT INTO `sys_role_permission` VALUES ('X0802', 6);
INSERT INTO `sys_role_permission` VALUES ('X0901', 6);
INSERT INTO `sys_role_permission` VALUES ('X0902', 6);
INSERT INTO `sys_role_permission` VALUES ('X0903', 6);
INSERT INTO `sys_role_permission` VALUES ('X0904', 6);
INSERT INTO `sys_role_permission` VALUES ('X0905', 6);
INSERT INTO `sys_role_permission` VALUES ('X1001', 6);
INSERT INTO `sys_role_permission` VALUES ('01', 7);
INSERT INTO `sys_role_permission` VALUES ('03', 7);
INSERT INTO `sys_role_permission` VALUES ('04', 7);
INSERT INTO `sys_role_permission` VALUES ('05', 7);
INSERT INTO `sys_role_permission` VALUES ('06', 7);
INSERT INTO `sys_role_permission` VALUES ('07', 7);
INSERT INTO `sys_role_permission` VALUES ('08', 7);
INSERT INTO `sys_role_permission` VALUES ('09', 7);
INSERT INTO `sys_role_permission` VALUES ('10', 7);
INSERT INTO `sys_role_permission` VALUES ('11', 7);
INSERT INTO `sys_role_permission` VALUES ('12', 7);
INSERT INTO `sys_role_permission` VALUES ('13', 7);
INSERT INTO `sys_role_permission` VALUES ('14', 7);
INSERT INTO `sys_role_permission` VALUES ('X0301', 7);
INSERT INTO `sys_role_permission` VALUES ('X0302', 7);
INSERT INTO `sys_role_permission` VALUES ('X0303', 7);
INSERT INTO `sys_role_permission` VALUES ('X0401', 7);
INSERT INTO `sys_role_permission` VALUES ('X0501', 7);
INSERT INTO `sys_role_permission` VALUES ('X0502', 7);
INSERT INTO `sys_role_permission` VALUES ('X0503', 7);
INSERT INTO `sys_role_permission` VALUES ('X0601', 7);
INSERT INTO `sys_role_permission` VALUES ('X0602', 7);
INSERT INTO `sys_role_permission` VALUES ('X0603', 7);
INSERT INTO `sys_role_permission` VALUES ('X0701', 7);
INSERT INTO `sys_role_permission` VALUES ('X0702', 7);
INSERT INTO `sys_role_permission` VALUES ('X0801', 7);
INSERT INTO `sys_role_permission` VALUES ('X0802', 7);
INSERT INTO `sys_role_permission` VALUES ('X0901', 7);
INSERT INTO `sys_role_permission` VALUES ('X0902', 7);
INSERT INTO `sys_role_permission` VALUES ('X0903', 7);
INSERT INTO `sys_role_permission` VALUES ('X0904', 7);
INSERT INTO `sys_role_permission` VALUES ('X0905', 7);
INSERT INTO `sys_role_permission` VALUES ('X1001', 7);
INSERT INTO `sys_role_permission` VALUES ('X1101', 7);
INSERT INTO `sys_role_permission` VALUES ('X1102', 7);
INSERT INTO `sys_role_permission` VALUES ('X1201', 7);
INSERT INTO `sys_role_permission` VALUES ('X1301', 7);
INSERT INTO `sys_role_permission` VALUES ('X1302', 7);
INSERT INTO `sys_role_permission` VALUES ('X1303', 7);
INSERT INTO `sys_role_permission` VALUES ('X1304', 7);
INSERT INTO `sys_role_permission` VALUES ('X1305', 7);
INSERT INTO `sys_role_permission` VALUES ('X1306', 7);
INSERT INTO `sys_role_permission` VALUES ('X1401', 7);
INSERT INTO `sys_role_permission` VALUES ('X1402', 7);
INSERT INTO `sys_role_permission` VALUES ('X1403', 7);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_CODE` varchar(255) NOT NULL COMMENT '用户编号',
  `NAME` varchar(255) NOT NULL COMMENT '名称',
  `PASSWORD` varchar(255) NOT NULL COMMENT '密码',
  `SEX` int(11) NOT NULL COMMENT '性别',
  `BIRTHDAY` varchar(255) DEFAULT NULL COMMENT '生日',
  `ID_NUM` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `TEL` varchar(30) DEFAULT NULL COMMENT '座机',
  `CELL_PHONE` varchar(255) DEFAULT NULL COMMENT '手机',
  `MAIL` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  `TYPE` varchar(255) NOT NULL COMMENT '类别(0老板, 1非设计类员工, 2设计类员工)',
  `STATUS` int(11) NOT NULL COMMENT '状态(1启用, 0锁定)',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ZIP_CODE` varchar(255) DEFAULT NULL COMMENT '邮编',
  `HEAD_ICON` varchar(255) DEFAULT NULL COMMENT '头像',
  `BASE_SALARY` double(10,2) DEFAULT NULL COMMENT '基本工资',
  `ENTRY_DATE` datetime DEFAULT NULL COMMENT '入职日期',
  `WORK_DATE` datetime DEFAULT NULL COMMENT '到职日期',
  `WORK_AGE` int(11) DEFAULT NULL COMMENT '工龄',
  `VACATION_DAYS` int(11) DEFAULT NULL,
  `STATE` int(11) NOT NULL COMMENT '状态',
  `ADMIN` int(11) DEFAULT NULL,
  `PROJECT_CODE` varchar(255) DEFAULT NULL COMMENT '项目编号',
  PRIMARY KEY (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1000', '李紫阳', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1988-11-09 00:00:00', NULL, NULL, '15810762146', 'zi.yang@ole-ad.com', NULL, '企劃&活動部', 1, '2016-11-04 15:00:14', '2015-06-10 17:00:38', NULL, '', NULL, '2014-05-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1001', '崔琴琴', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1988-10-07 00:00:00', NULL, NULL, '15101042817', 'qinqin.cui@ole-ad.com', NULL, '設計部', 1, '2016-11-04 15:00:19', '2015-06-10 17:04:26', NULL, '', NULL, '2014-05-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1002', '趙冬晴', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1988-05-25 00:00:00', NULL, NULL, '15101193370', 'xiaiobai.zhao@ole-ad.com', NULL, '設計部', 1, '2016-11-04 15:00:31', '2015-06-10 17:07:02', NULL, NULL, NULL, '2015-02-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1003', '劉興玟', '609CEE41FC0B7584ECA0FD7137C86206', 0, '1979-05-06 00:00:00', NULL, NULL, '18611438073', 'shawn.liu@ole-ad.com', NULL, '主管', 1, '2016-11-04 15:00:37', '2015-06-10 17:45:42', NULL, NULL, NULL, '2014-05-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1004', '參觀帳號', '29C3EEA3F305D6B823F562AC4BE35217', 0, '2015-06-10 00:00:00', NULL, NULL, '00000000000', 'guest@ole-ad.com', NULL, '主管', 1, '2016-11-18 14:49:04', '2015-06-10 17:46:55', NULL, NULL, NULL, '2015-06-10 00:00:00', NULL, NULL, 0, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1005', '杜麗', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1985-11-18 00:00:00', NULL, NULL, '15001281128', 'miko.du@ole-ad.com', NULL, '企劃&活動部', 1, '2016-11-04 15:00:41', '2015-06-10 17:49:02', NULL, NULL, NULL, '2014-05-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1006', '林家暉', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1979-11-24 00:00:00', NULL, NULL, '18516861392', 'kate.lin@ole-ad.com', NULL, '主管', 1, '2016-11-04 15:00:46', '2015-06-10 17:56:04', NULL, NULL, NULL, '2015-03-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1007', '任哲李', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1986-06-14 00:00:00', NULL, NULL, '18600598420', 'nate.ren@ole-ad.com', NULL, '企劃&活動部', 0, '2016-11-04 15:00:50', '2015-06-10 17:59:30', NULL, NULL, NULL, '2015-06-10 00:00:00', NULL, NULL, 0, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1008', '陳立展', '846D0F41CCCBE6556F806BF33CCE9840', 0, '1982-01-24 00:00:00', NULL, NULL, '18601044109', 'lee@ole-ad.com', NULL, '系統管理者', 1, '2016-11-04 15:00:58', '2015-06-10 18:02:25', NULL, NULL, NULL, '2014-05-01 00:00:00', NULL, NULL, 14, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1024', '趙欣萌', 'C85A023B2676A4226FA55A1E558072B6', 1, '1990-02-04 00:00:00', NULL, '010-57306067', '15801468518', 'diana.zhao@ole-ad.com', NULL, '企劃&活動部', 1, '2015-12-03 16:06:03', '2015-10-07 21:40:17', NULL, '', NULL, '2015-09-14 00:00:00', NULL, NULL, 17, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1025', '陳秋詩', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1978-11-04 00:00:00', NULL, NULL, '18515306606', 'lisa.chen@ole-ad.com', NULL, '普通员工', 0, '2016-11-03 16:38:45', '2016-03-02 17:31:36', NULL, NULL, NULL, '2016-03-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1026', '王儒慶', '839CEC9153319284058BE280CFA6AE85', 1, '1988-10-02 00:00:00', NULL, '', '17600853745', 'garfield.wang@ole-ad.com', NULL, '企劃&活動部', 1, '2016-03-17 17:26:19', '2016-03-02 17:33:40', NULL, '', NULL, '2016-03-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1027', '赵亮', 'E49850677BAC01EC4FAE3AF892DAB1E5', 0, '1981-09-02 00:00:00', NULL, NULL, '13910852589', 'eric.zhao@ole-ad.com', NULL, '業務部', 0, '2016-04-25 10:38:51', '2016-03-04 12:04:53', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1028', '武躍', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1977-07-05 00:00:00', NULL, NULL, '18910298921', 'wilson.wu@ole-ad.com', NULL, '業務部', 1, '2016-04-25 10:26:52', '2016-03-04 12:06:21', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1029', '黃露凝', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-05-26 00:00:00', NULL, NULL, '15901102906', 'luning.huang@ole-ad.com', NULL, '企劃&活動部', 1, '2016-04-21 13:18:27', '2016-03-04 12:07:14', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1030', '劉丹', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1978-05-10 00:00:00', NULL, NULL, '13802918898', 'fiona.liu@ole-ad.com', NULL, '業務部', 1, '2016-04-25 10:28:21', '2016-03-04 12:08:10', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1031', '黃穎', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1981-09-04 00:00:00', NULL, NULL, '13828411194', 'ving.wong@ole-ad.com', NULL, '業務部', 0, '2016-04-25 10:28:53', '2016-03-04 12:09:04', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 0, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1032', '杜倩文', 'BBA3192E728A8812C02FB051E368F08C', 1, '1990-06-16 00:00:00', NULL, NULL, '13810149616', 'maya.du@ole-ad.com', NULL, '主管', 1, '2017-06-02 11:46:16', '2016-03-17 17:37:30', NULL, '', NULL, '2016-03-17 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1033', '譚興宸', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1984-09-07 00:00:00', NULL, NULL, '18516838731', 'eason.tan@ole-ad.com', NULL, '企劃&活動部', 1, '2016-04-25 10:17:34', '2016-04-25 10:17:34', NULL, NULL, NULL, '2016-04-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1034', '王淵宏', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1972-08-09 00:00:00', NULL, NULL, '13901072098', 'daniel.wang@ole-ad.com', NULL, '主管', 1, '2016-11-03 16:39:12', '2016-04-25 10:20:04', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1035', '張磊', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1976-11-08 00:00:00', NULL, NULL, '13801391915', 'Leo.zhang@ole-ad.com', NULL, '業務部', 1, '2016-04-25 10:31:28', '2016-04-25 10:31:28', NULL, NULL, NULL, '2016-04-25 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1036', '魯建', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1976-04-26 00:00:00', NULL, NULL, '18616633581', 'rooney.lu@ole-ad.com', NULL, '業務部', 0, '2016-04-25 10:32:46', '2016-04-25 10:32:46', NULL, NULL, NULL, '2016-01-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1037', '陳亮霖', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1985-03-03 00:00:00', NULL, NULL, '13585676325', 'ryo.chen@ole-ad.com', NULL, '主管', 1, '2016-11-04 14:58:28', '2016-05-12 16:29:45', NULL, NULL, NULL, '2016-05-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1038', '王漢卿', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1985-04-07 00:00:00', NULL, NULL, '15210288439', 'hanqing.wang@ole-ad.com', NULL, '系統管理者', 1, '2017-06-21 16:02:49', '2016-09-20 14:29:40', NULL, '', NULL, '2016-09-20 00:00:00', NULL, NULL, 0, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1039', '刘丹', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1978-06-27 00:00:00', NULL, NULL, '13701382346', 'dalia.liu@ole-ad.com', NULL, '業務部', 1, '2016-10-12 13:23:18', '2016-10-12 13:18:41', NULL, NULL, NULL, '2016-09-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1040', '萧孜璇', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1987-02-07 00:00:00', NULL, NULL, '18301225802', 'Magi.Hsiao@ole-ad.com', NULL, '企劃&活動部', 0, '2016-10-12 13:47:26', '2016-10-12 13:47:26', NULL, NULL, NULL, '2016-08-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1041', '江明颖', 'C62D929E7B7E7B6165923A5DFC60CB56', 1, '1977-04-28 00:00:00', NULL, '', '13521579660', 'Ming.chiang@ole-ad.com', NULL, '主管', 1, '2016-10-12 16:44:51', '2016-10-12 13:50:41', NULL, '', NULL, '2016-09-01 00:00:00', NULL, NULL, 18, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1042', '童索疑', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1991-02-25 00:00:00', NULL, NULL, '18508435123', 'alice.tong@ole-ad.com', NULL, '企劃&活動部', 0, '2016-10-12 13:55:41', '2016-10-12 13:55:41', NULL, NULL, NULL, '2016-05-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1043', '王东哲', 'F747EA92CF5CFD434B30608628105416', 0, '1993-06-05 00:00:00', NULL, '57306023', '18684808889', 'Andy.wong@ole-ad.com', NULL, '企劃&活動部', 1, '2016-10-12 13:58:06', '2016-10-12 13:58:06', NULL, '', NULL, '2016-05-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1044', '徐薇', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-07-20 00:00:00', NULL, NULL, '15911186503', 'rene.xu@ole-ad.com', NULL, '企劃&活動部', 0, '2016-10-12 14:00:28', '2016-10-12 14:00:28', NULL, NULL, NULL, '2016-09-18 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1045', '李金璇', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-05-19 00:00:00', NULL, NULL, '18600595773', 'maggie.li@ole-ad.com', NULL, '企劃&活動部', 1, '2016-10-12 14:02:45', '2016-10-12 14:02:45', NULL, NULL, NULL, '2016-09-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1046', '付雅宁', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-08-23 00:00:00', NULL, NULL, '13521741909', 'Ceci.fu@ole-ad.com', NULL, '銷售部', 1, '2016-10-12 16:43:50', '2016-10-12 14:05:18', NULL, NULL, NULL, '2016-07-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1047', '钟新谨', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1982-08-29 00:00:00', NULL, NULL, '18513229358', 'green.zhong@ole-ad.com', NULL, '設計部', 1, '2016-10-12 14:08:20', '2016-10-12 14:08:20', NULL, NULL, NULL, '2016-07-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1048', '李宁', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '1993-08-13 00:00:00', NULL, '', '18004425193', 'arthur.li@ole-ad.com', NULL, '企劃&活動部', 1, '2016-10-12 14:10:19', '2016-10-12 14:10:06', NULL, '', NULL, '2016-09-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1049', '段会君', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-12-01 00:00:00', NULL, NULL, '13761287262', 'Duan.duan@ole-ad.com', NULL, '銷售部', 1, '2016-10-12 16:43:01', '2016-10-12 14:17:01', NULL, NULL, NULL, '2016-08-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1050', '姚一鸣', '6DBD25164AD8B0F01FC1B70BF0CAD336', 1, '1990-10-29 00:00:00', NULL, NULL, '18801951237', 'carrie.yao@ole-ad.com', NULL, '企劃&活動部', 1, '2016-10-12 14:21:09', '2016-10-12 14:21:09', NULL, NULL, NULL, '2016-06-01 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1051', '陳郁屏', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1979-10-18 00:00:00', NULL, NULL, '18614086646', 'wendy.chen@ole-ad.com', NULL, '主管', 1, '2017-04-06 17:00:40', '2017-03-13 17:58:53', NULL, NULL, NULL, '2017-02-13 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1052', '迟瑞喆', 'F3A872FC19DEF5F012BA1962DC4EE8F1', 1, '1992-06-16 00:00:00', NULL, '', '18813180616', 'riya.chi@ole-ad.com', NULL, '企劃&活動部', 1, '2017-03-13 18:12:35', '2017-03-13 18:12:35', NULL, '/headicon/cb6e5c75-9155-45ef-934a-469cab7137df-WechatIMG1.jpeg', NULL, '2017-03-06 00:00:00', NULL, NULL, 5, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1053', '路雨溪', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1988-10-24 00:00:00', NULL, NULL, '13911217558', 'Lucie.lu@ole-ad.com', NULL, '企劃&活動部', 1, '2017-03-13 18:16:13', '2017-03-13 18:16:13', NULL, NULL, NULL, '2017-02-06 00:00:00', NULL, NULL, 7, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1054', '李思萱', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1991-10-24 00:00:00', NULL, NULL, '18801024952', 'pinky.li@ole-ad.com', NULL, '企劃&活動部', 1, '2017-03-21 18:12:32', '2017-03-21 18:12:32', NULL, NULL, NULL, '2017-02-20 00:00:00', NULL, NULL, 8, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1055', '張偉達', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1991-12-17 00:00:00', NULL, NULL, '15201655116', 'aretha.zhang@ole-ad.com', NULL, '企劃&活動部', 1, '2017-04-18 18:59:32', '2017-04-18 18:59:32', NULL, NULL, NULL, '2017-03-01 00:00:00', NULL, NULL, 6, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1056', '張騏驥', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1990-05-03 00:00:00', NULL, NULL, '18513361613', 'kichy.zhang@ole-ad.com', NULL, '普通员工', 1, '2017-05-27 14:14:33', '2017-05-27 14:14:33', NULL, NULL, NULL, '2017-05-01 00:00:00', NULL, NULL, 4, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1057', '周藝', 'ACEA3EB6305A1A7759BB37E2DD6FE32F', 1, '1990-07-29 00:00:00', NULL, NULL, '15732644006', 'xiaoyi.zhou@ole-ad.com', NULL, '企劃&活動部', 1, '2017-06-02 11:51:55', '2017-06-02 11:51:55', NULL, NULL, NULL, '2017-05-01 00:00:00', NULL, NULL, 5, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1058', '楊勝梅', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1991-07-23 00:00:00', NULL, '', '15821607873', 'shengmei.yang@ole-ad.com', NULL, '普通员工', 1, '2017-06-02 11:53:47', '2017-06-02 11:53:47', NULL, '', NULL, '2017-04-01 00:00:00', NULL, NULL, 8, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1059', '馮維瑋', '277DEB439B97CFA197F590E382E10543', 1, '1989-02-11 00:00:00', NULL, '', '13488652547', 'vivian.feng@ole-ad.com', NULL, '企劃&活動部', 1, '2017-06-02 11:56:10', '2017-06-02 11:56:10', NULL, '', NULL, '2017-06-01 00:00:00', NULL, NULL, 4, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1060', '吴留芳', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1994-04-11 00:00:00', NULL, '', '18818219412', 'Chris.wu@ole-ad.com', NULL, '企劃&活動部', 1, '2017-06-19 17:35:20', '2017-06-19 17:35:20', NULL, '', NULL, '2017-06-19 00:00:00', NULL, NULL, NULL, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1061', '王杰', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1987-03-12 00:00:00', NULL, '', '13816697359', 'loki.wang@ole-ad.com', NULL, '企劃&活動部', 1, '2017-06-19 17:41:56', '2017-06-19 17:41:56', NULL, '', NULL, '2017-05-15 00:00:00', NULL, NULL, NULL, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1062', '李顏', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1984-06-23 00:00:00', NULL, NULL, '13401052207', 'yan.li@ole-ad.com', NULL, '企劃&活動部', 1, '2017-06-19 17:48:24', '2017-06-19 17:48:24', NULL, NULL, NULL, '2017-06-19 00:00:00', NULL, NULL, NULL, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1063', '陳芃熙', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1993-02-25 00:00:00', NULL, '', '15652922510', 'catherine.chen@ole-ad.com', NULL, '企劃&活動部', 1, '2017-07-24 13:43:53', '2017-07-24 13:43:25', NULL, '', NULL, '2017-05-01 00:00:00', NULL, NULL, 6, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1064', '趙雨辰', '833219E9BDF2DEE49D94A8D5CA887B88', 1, '1992-07-29 00:00:00', NULL, NULL, '15810990680', 'jane.zhao@ole-ad.com', NULL, '普通员工', 1, '2017-08-18 18:16:10', '2017-08-18 18:16:10', NULL, NULL, NULL, '2017-06-01 00:00:00', NULL, NULL, 4, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1065', '單明心', '670B14728AD9902AECBA32E22FA4F6BD', 1, '1982-04-05 00:00:00', NULL, NULL, '18811044540', 'star.shan@ole-ad.com', NULL, '企劃&活動部', 1, '2017-08-21 11:59:59', '2017-08-21 11:59:59', NULL, NULL, NULL, '2017-08-21 00:00:00', NULL, NULL, NULL, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('1066', '周嘉桐', '670B14728AD9902AECBA32E22FA4F6BD', 0, '1993-05-16 00:00:00', NULL, NULL, '17600937030', 'jason.zhou@ole-ad.com', NULL, '企劃&活動部', 1, '2017-09-08 17:11:47', '2017-09-08 17:11:47', NULL, NULL, NULL, '2017-07-01 00:00:00', NULL, NULL, NULL, 1, 0, NULL);
INSERT INTO `sys_user` VALUES ('admin', '管理员', '6DBD25164AD8B0F01FC1B70BF0CAD336', 0, '', '', '', '', '', '', '管理员', 1, '2015-05-13 17:49:02', '2015-05-13 17:49:04', '', '', NULL, '2015-05-13 17:49:13', '2015-05-13 17:49:16', NULL, NULL, 1, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_project`;
CREATE TABLE `sys_user_project` (
  `USER_CODE` varchar(255) NOT NULL,
  `PROJECT_CODE` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_CODE`,`PROJECT_CODE`),
  KEY `FKEBDF0ED76555BFFA` (`USER_CODE`),
  KEY `FKEBDF0ED725414BAB` (`PROJECT_CODE`),
  CONSTRAINT `FKEBDF0ED725414BAB` FOREIGN KEY (`PROJECT_CODE`) REFERENCES `pr_project` (`PROJECT_CODE`),
  CONSTRAINT `FKEBDF0ED76555BFFA` FOREIGN KEY (`USER_CODE`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  `USER_CODE` varchar(255) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`ROLE_ID`,`USER_CODE`),
  KEY `FKAABB7D586555BFFA` (`USER_CODE`),
  KEY `FKAABB7D58A330C848` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, '1008');
INSERT INTO `sys_user_role` VALUES (1, '1038');
INSERT INTO `sys_user_role` VALUES (1, '1044');
INSERT INTO `sys_user_role` VALUES (2, '1000');
INSERT INTO `sys_user_role` VALUES (2, '1005');
INSERT INTO `sys_user_role` VALUES (2, '1007');
INSERT INTO `sys_user_role` VALUES (2, '1026');
INSERT INTO `sys_user_role` VALUES (2, '1029');
INSERT INTO `sys_user_role` VALUES (2, '1033');
INSERT INTO `sys_user_role` VALUES (2, '1040');
INSERT INTO `sys_user_role` VALUES (2, '1042');
INSERT INTO `sys_user_role` VALUES (2, '1043');
INSERT INTO `sys_user_role` VALUES (2, '1045');
INSERT INTO `sys_user_role` VALUES (2, '1048');
INSERT INTO `sys_user_role` VALUES (2, '1050');
INSERT INTO `sys_user_role` VALUES (2, '1052');
INSERT INTO `sys_user_role` VALUES (2, '1053');
INSERT INTO `sys_user_role` VALUES (2, '1054');
INSERT INTO `sys_user_role` VALUES (2, '1055');
INSERT INTO `sys_user_role` VALUES (2, '1057');
INSERT INTO `sys_user_role` VALUES (2, '1060');
INSERT INTO `sys_user_role` VALUES (2, '1061');
INSERT INTO `sys_user_role` VALUES (2, '1062');
INSERT INTO `sys_user_role` VALUES (2, '1063');
INSERT INTO `sys_user_role` VALUES (2, '1065');
INSERT INTO `sys_user_role` VALUES (2, '1066');
INSERT INTO `sys_user_role` VALUES (3, '1001');
INSERT INTO `sys_user_role` VALUES (3, '1002');
INSERT INTO `sys_user_role` VALUES (5, '1025');
INSERT INTO `sys_user_role` VALUES (5, '1056');
INSERT INTO `sys_user_role` VALUES (5, '1058');
INSERT INTO `sys_user_role` VALUES (5, '1059');
INSERT INTO `sys_user_role` VALUES (5, '1064');
INSERT INTO `sys_user_role` VALUES (6, '1003');
INSERT INTO `sys_user_role` VALUES (6, '1004');
INSERT INTO `sys_user_role` VALUES (6, '1006');
INSERT INTO `sys_user_role` VALUES (6, '1024');
INSERT INTO `sys_user_role` VALUES (6, '1032');
INSERT INTO `sys_user_role` VALUES (6, '1034');
INSERT INTO `sys_user_role` VALUES (6, '1037');
INSERT INTO `sys_user_role` VALUES (6, '1041');
INSERT INTO `sys_user_role` VALUES (6, '1047');
INSERT INTO `sys_user_role` VALUES (6, '1051');
INSERT INTO `sys_user_role` VALUES (7, '1027');
INSERT INTO `sys_user_role` VALUES (7, '1028');
INSERT INTO `sys_user_role` VALUES (7, '1030');
INSERT INTO `sys_user_role` VALUES (7, '1031');
INSERT INTO `sys_user_role` VALUES (7, '1035');
INSERT INTO `sys_user_role` VALUES (7, '1046');
INSERT INTO `sys_user_role` VALUES (7, '1049');
COMMIT;

-- ----------------------------
-- Table structure for sysuser_borrow
-- ----------------------------
DROP TABLE IF EXISTS `sysuser_borrow`;
CREATE TABLE `sysuser_borrow` (
  `BORROW_CODE` int(11) NOT NULL COMMENT '出借编号',
  `SYSUSER_CODE` varchar(255) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`BORROW_CODE`,`SYSUSER_CODE`),
  KEY `FK9BEF2C1CB2F1A0A1` (`BORROW_CODE`),
  KEY `FK9BEF2C1CF73957CD` (`SYSUSER_CODE`),
  CONSTRAINT `FK9BEF2C1CB2F1A0A1` FOREIGN KEY (`BORROW_CODE`) REFERENCES `borrow` (`BORROW_CODE`),
  CONSTRAINT `FK9BEF2C1CF73957CD` FOREIGN KEY (`SYSUSER_CODE`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_project
-- ----------------------------
DROP TABLE IF EXISTS `user_project`;
CREATE TABLE `user_project` (
  `ID` int(11) NOT NULL,
  `USER_CODE` varchar(255) DEFAULT NULL COMMENT '客户编号',
  `PROJECT_CODE` varchar(255) DEFAULT NULL COMMENT '项目编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wk_leave
-- ----------------------------
DROP TABLE IF EXISTS `wk_leave`;
CREATE TABLE `wk_leave` (
  `LEAVE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假ID',
  `START_DATE` datetime NOT NULL COMMENT '开始日期',
  `END_DATE` datetime NOT NULL COMMENT '结束日期',
  `DURATION` double NOT NULL COMMENT '请假时长',
  `STATUS` varchar(11) NOT NULL COMMENT '状态',
  `NOTES` varchar(255) DEFAULT NULL COMMENT '备注',
  `APPLICANT` varchar(255) DEFAULT NULL COMMENT '申请人',
  `START_DATE_SLOT` bit(1) NOT NULL COMMENT '开始日期是上午还是下午',
  `END_DATE_SLOT` bit(1) DEFAULT NULL COMMENT '结束日期是上午还是下午',
  PRIMARY KEY (`LEAVE_ID`),
  KEY `FK7365556CD48DBD1B` (`APPLICANT`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wk_leave
-- ----------------------------
BEGIN;
INSERT INTO `wk_leave` VALUES (23, '2016-05-03 00:00:00', '2016-05-04 00:00:00', 0, '1', '', '1028', b'0', b'1');
INSERT INTO `wk_leave` VALUES (24, '2016-05-30 00:00:00', '2016-06-03 00:00:00', 0, '1', '年假', '1027', b'0', b'0');
INSERT INTO `wk_leave` VALUES (25, '2016-05-03 00:00:00', '2016-05-05 00:00:00', 0, '1', '私人外出', '1029', b'0', b'1');
INSERT INTO `wk_leave` VALUES (26, '2016-08-22 00:00:00', '2016-08-26 00:00:00', 0, '1', '返台', '1033', b'0', b'1');
INSERT INTO `wk_leave` VALUES (27, '2016-05-06 00:00:00', '2016-05-06 00:00:00', 0, '1', '回家', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (28, '2016-05-20 00:00:00', '2016-05-20 00:00:00', 0, '1', '参加朋友婚礼', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (29, '2016-04-08 00:00:00', '2016-04-08 00:00:00', 0, '3', '事假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (30, '2016-04-11 00:00:00', '2016-04-15 00:00:00', 0, '3', '事假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (31, '2016-04-18 00:00:00', '2016-04-22 00:00:00', 0, '3', '事假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (32, '2016-04-25 00:00:00', '2016-04-25 00:00:00', 0, '3', '事假', '1026', b'0', b'0');
INSERT INTO `wk_leave` VALUES (33, '2016-04-29 00:00:00', '2016-04-29 00:00:00', 0, '3', '事假', '1026', b'1', b'1');
INSERT INTO `wk_leave` VALUES (34, '2016-05-04 00:00:00', '2016-05-05 00:00:00', 0, '1', '休假', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (35, '2016-05-09 00:00:00', '2016-05-10 00:00:00', 0, '1', '休假', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (36, '2016-05-03 00:00:00', '2016-05-03 00:00:00', 0, '1', '', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (37, '2016-05-03 00:00:00', '2016-05-03 00:00:00', 0, '1', '补休一天', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (38, '2016-05-25 00:00:00', '2016-05-30 00:00:00', 0, '1', '调休', '1024', b'0', b'0');
INSERT INTO `wk_leave` VALUES (39, '2016-08-19 00:00:00', '2016-08-19 00:00:00', 0, '1', '', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (40, '2016-09-26 00:00:00', '2016-09-30 00:00:00', 0, '1', '补休', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (41, '2016-09-21 00:00:00', '2016-09-22 00:00:00', 0, '1', '事假', '1028', b'0', b'1');
INSERT INTO `wk_leave` VALUES (42, '2016-10-24 00:00:00', '2016-10-27 00:00:00', 0, '1', '年假', '1028', b'0', b'1');
INSERT INTO `wk_leave` VALUES (43, '2016-10-31 00:00:00', '2016-10-31 00:00:00', 0, '1', '病假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (44, '2016-08-01 00:00:00', '2016-08-01 00:00:00', 0, '1', '事假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (45, '2016-09-12 00:00:00', '2016-09-14 00:00:00', 0, '1', '休假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (46, '2016-09-18 00:00:00', '2016-09-18 00:00:00', 0, '1', '休假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (47, '2016-12-23 00:00:00', '2016-12-23 00:00:00', 0, '1', '返台假', '1026', b'1', b'1');
INSERT INTO `wk_leave` VALUES (48, '2016-12-26 00:00:00', '2016-12-30 00:00:00', 0, '1', '返台假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (49, '2016-11-11 00:00:00', '2016-11-11 00:00:00', 0, '1', '参加朋友婚礼', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (50, '2016-04-08 00:00:00', '2016-04-25 00:00:00', 0, '1', '不支薪私人请假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (51, '2016-04-29 00:00:00', '2016-04-29 00:00:00', 0, '1', '不支薪私人请假', '1026', b'1', b'1');
INSERT INTO `wk_leave` VALUES (52, '2016-09-30 00:00:00', '2016-09-30 00:00:00', 0, '1', '返台假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (53, '2016-10-28 00:00:00', '2016-10-28 00:00:00', 0, '1', '倒休', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (54, '2016-11-04 00:00:00', '2016-11-04 00:00:00', 0, '2', '下午3点先去柏悦场勘，之后有事请假不回公司。', '1024', b'1', b'1');
INSERT INTO `wk_leave` VALUES (55, '2016-10-09 00:00:00', '2016-10-13 00:00:00', 0, '1', '', '1037', b'0', b'0');
INSERT INTO `wk_leave` VALUES (56, '2016-08-19 00:00:00', '2016-08-19 00:00:00', 0, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (57, '2016-11-14 00:00:00', '2016-11-14 00:00:00', 0, '2', '私人出國', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (58, '2016-11-23 00:00:00', '2016-11-25 00:00:00', 0, '2', '私人回台灣祝壽', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (59, '2016-11-28 00:00:00', '2016-11-29 00:00:00', 0, '2', '私人回台灣祝壽', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (60, '2016-12-06 00:00:00', '2016-12-09 00:00:00', 0, '1', '喪假', '1026', b'1', b'1');
INSERT INTO `wk_leave` VALUES (61, '2016-11-17 00:00:00', '2016-11-17 00:00:00', 0, '3', '休息', '1001', b'1', b'1');
INSERT INTO `wk_leave` VALUES (62, '2016-10-28 00:00:00', '2016-10-28 00:00:00', 0, '3', '东京设计周', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (63, '2016-12-19 00:00:00', '2016-12-21 00:00:00', 0, '3', '休假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (64, '2016-12-29 00:00:00', '2016-12-31 00:00:00', 0, '1', '旅游', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (65, '2017-02-06 00:00:00', '2017-02-08 00:00:00', 0, '1', '年休假', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (66, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (67, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '1', '回家探亲', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (68, '2017-04-27 00:00:00', '2017-04-28 00:00:00', 0, '2', '', '1037', b'0', b'0');
INSERT INTO `wk_leave` VALUES (69, '2017-04-27 00:00:00', '2017-04-28 00:00:00', 0, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (70, '2017-01-23 00:00:00', '2017-01-23 00:00:00', 0, '1', '调休', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (71, '2017-01-24 00:00:00', '2017-01-24 00:00:00', 0, '1', '调休', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (72, '2017-01-22 00:00:00', '2017-01-22 00:00:00', 0, '1', 'ti', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (73, '2017-01-22 00:00:00', '2017-01-22 00:00:00', 0, '1', '倒休', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (74, '2017-01-24 00:00:00', '2017-01-24 00:00:00', 0, '1', '', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (75, '2017-02-03 00:00:00', '2017-02-05 00:00:00', 0, '1', '探亲', '1029', b'0', b'1');
INSERT INTO `wk_leave` VALUES (76, '2017-01-12 00:00:00', '2017-01-12 00:00:00', 0, '3', '倒休', '1029', b'0', b'1');
INSERT INTO `wk_leave` VALUES (77, '2017-04-24 00:00:00', '2017-04-28 00:00:00', 0, '1', '休假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (78, '2017-05-02 00:00:00', '2017-05-05 00:00:00', 0, '1', '個人請假', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (79, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '1', '過年返鄉', '1006', b'0', b'1');
INSERT INTO `wk_leave` VALUES (80, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 0, '2', '過年返鄉', '1006', b'0', b'1');
INSERT INTO `wk_leave` VALUES (81, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 0, '1', '春节只抢到23号的票所以提前走', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (82, '2017-02-04 00:00:00', '2017-02-06 00:00:00', 0, '1', '因家里事情，请假', '1048', b'0', b'1');
INSERT INTO `wk_leave` VALUES (83, '2017-01-12 00:00:00', '2017-01-13 00:00:00', 0, '2', '病假，有三甲医院假条', '1032', b'0', b'1');
INSERT INTO `wk_leave` VALUES (84, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 0, '1', '', '1032', b'0', b'1');
INSERT INTO `wk_leave` VALUES (85, '2017-02-15 00:00:00', '2017-02-15 00:00:00', 0, '1', '考试', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (86, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 0, '2', '返日', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (87, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '2', '返日', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (88, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 0, '2', '春節返台', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (89, '2017-02-03 00:00:00', '2017-02-03 00:00:00', 0, '2', '春節返台', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (90, '2017-02-03 00:00:00', '2017-02-03 00:00:00', 0, '1', '春节返京长途开车后申请休息一天', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (91, '2017-01-24 00:00:00', '2017-01-24 00:00:00', 0, '1', '春节提前返家', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (92, '2017-01-06 00:00:00', '2017-01-06 00:00:00', 0, '1', '病假', '1043', b'1', b'1');
INSERT INTO `wk_leave` VALUES (93, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 0, '2', '年假', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (94, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '1', '', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (95, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 0, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (96, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 0, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (97, '2017-02-06 00:00:00', '2017-02-08 00:00:00', 0, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (98, '2017-02-24 00:00:00', '2017-02-24 00:00:00', 1, '1', '事假', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (99, '2017-03-31 00:00:00', '2017-03-31 00:00:00', 1, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (100, '2017-03-09 00:00:00', '2017-03-10 00:00:00', 2, '1', '休假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (101, '2017-02-27 00:00:00', '2017-02-27 00:00:00', 0.5, '3', '', '1002', b'0', b'0');
INSERT INTO `wk_leave` VALUES (102, '2017-02-27 00:00:00', '2017-02-27 00:00:00', 0.5, '3', '', '1002', b'0', b'0');
INSERT INTO `wk_leave` VALUES (103, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 3, '1', '', '1032', b'0', b'1');
INSERT INTO `wk_leave` VALUES (104, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 3, '1', '返鄉', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (105, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '1', '休假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (106, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '1', '返鄉', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (107, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 3, '1', '返台', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (108, '2017-02-03 00:00:00', '2017-02-03 00:00:00', 1, '1', '返台', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (109, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 2, '1', '倒休', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (110, '2017-03-09 00:00:00', '2017-03-10 00:00:00', 2, '1', '返台看牙醫', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (111, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '2', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (112, '2017-02-06 00:00:00', '2017-02-08 00:00:00', 2.5, '1', '', '1037', b'0', b'0');
INSERT INTO `wk_leave` VALUES (113, '2017-04-27 00:00:00', '2017-04-28 00:00:00', 2, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (114, '2017-03-07 00:00:00', '2017-03-07 00:00:00', 0.5, '1', '', '1037', b'0', b'0');
INSERT INTO `wk_leave` VALUES (115, '2017-01-24 00:00:00', '2017-01-24 00:00:00', 1, '1', '春节提前返家', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (116, '2017-02-03 00:00:00', '2017-02-03 00:00:00', 1, '1', '春节返京长途开车后申请休息一天', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (117, '2017-03-13 00:00:00', '2017-03-13 00:00:00', 1, '1', '去医院拍CT 核查脑部肿块问题', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (118, '2017-03-03 00:00:00', '2017-03-03 00:00:00', 1, '1', '', '1043', b'0', b'1');
INSERT INTO `wk_leave` VALUES (119, '2017-03-23 00:00:00', '2017-03-24 00:00:00', 2, '1', '返台看牙醫', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (120, '2017-04-26 00:00:00', '2017-04-28 00:00:00', 3, '2', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (121, '2017-04-26 00:00:00', '2017-04-26 00:00:00', 1, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (122, '2017-03-29 00:00:00', '2017-03-30 00:00:00', 2, '1', '拔牙', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (123, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 3, '1', '过年前返台', '1033', b'0', b'1');
INSERT INTO `wk_leave` VALUES (124, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '1', '', '1033', b'0', b'1');
INSERT INTO `wk_leave` VALUES (125, '2017-04-12 00:00:00', '2017-04-14 00:00:00', 3, '1', '返台', '1033', b'0', b'1');
INSERT INTO `wk_leave` VALUES (126, '2017-04-14 00:00:00', '2017-04-14 00:00:00', 1, '1', '返台看牙 ', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (127, '2017-05-27 00:00:00', '2017-05-27 00:00:00', 1, '1', '休假', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (128, '2017-05-31 00:00:00', '2017-05-31 00:00:00', 1, '1', '休假', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (129, '2017-06-01 00:00:00', '2017-06-01 00:00:00', 1, '1', '休假', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (130, '2017-04-06 00:00:00', '2017-04-07 00:00:00', 2, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (131, '2017-02-06 00:00:00', '2017-02-08 00:00:00', 3, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (132, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (133, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 2, '1', '', '1040', b'0', b'1');
INSERT INTO `wk_leave` VALUES (134, '2017-04-20 00:00:00', '2017-04-21 00:00:00', 2, '1', '', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (135, '2017-01-22 00:00:00', '2017-01-24 00:00:00', 3, '1', '事', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (136, '2017-02-03 00:00:00', '2017-02-04 00:00:00', 2, '1', '返台過年', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (137, '2017-04-24 00:00:00', '2017-04-28 00:00:00', 5, '1', '因為要滿40歲，所以要去躲起來哭', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (138, '2017-04-11 00:00:00', '2017-04-11 00:00:00', 0.5, '1', '', '1047', b'1', b'1');
INSERT INTO `wk_leave` VALUES (139, '2017-04-05 00:00:00', '2017-04-05 00:00:00', 1, '1', '家里办事', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (140, '2017-03-17 00:00:00', '2017-03-17 00:00:00', 1, '1', '项目倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (141, '2017-03-06 00:00:00', '2017-03-06 00:00:00', 1, '1', '项目倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (142, '2017-05-09 00:00:00', '2017-05-09 00:00:00', 0.5, '1', '', '1033', b'0', b'0');
INSERT INTO `wk_leave` VALUES (143, '2017-05-31 00:00:00', '2017-06-02 00:00:00', 3, '1', '', '1033', b'0', b'1');
INSERT INTO `wk_leave` VALUES (144, '2017-05-19 00:00:00', '2017-05-19 00:00:00', 1, '1', '补休', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (145, '2017-05-19 00:00:00', '2017-05-19 00:00:00', 1, '1', '家事', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (146, '2017-06-09 00:00:00', '2017-06-09 00:00:00', 1, '1', '事假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (147, '2017-05-31 00:00:00', '2017-06-02 00:00:00', 3, '1', '旅遊', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (148, '2017-06-12 00:00:00', '2017-06-14 00:00:00', 3, '1', '旅游', '1050', b'0', b'1');
INSERT INTO `wk_leave` VALUES (149, '2017-06-22 00:00:00', '2017-06-22 00:00:00', 1, '1', '倒休', '1055', b'0', b'1');
INSERT INTO `wk_leave` VALUES (150, '2017-06-20 00:00:00', '2017-06-20 00:00:00', 1, '1', '《神奶》倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (151, '2017-06-20 00:00:00', '2017-06-20 00:00:00', 1, '1', '《神偷奶爸3》6月19日熬夜盯撤场，第二天在家休息', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (152, '2017-06-16 00:00:00', '2017-06-16 00:00:00', 1, '1', 'Back to Taiwan', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (153, '2017-06-27 00:00:00', '2017-06-27 00:00:00', 1, '1', '加班倒休', '1059', b'0', b'1');
INSERT INTO `wk_leave` VALUES (154, '2017-06-27 00:00:00', '2017-06-28 00:00:00', 2, '1', '《神奶》倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (155, '2017-07-03 00:00:00', '2017-07-04 00:00:00', 2, '1', '《神奶》倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (156, '2017-07-03 00:00:00', '2017-07-03 00:00:00', 1, '1', '事假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (157, '2017-07-04 00:00:00', '2017-07-04 00:00:00', 1, '1', '上海站出差后，请一天假在家休息', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (158, '2017-07-05 00:00:00', '2017-07-05 00:00:00', 1, '1', '', '1055', b'0', b'1');
INSERT INTO `wk_leave` VALUES (159, '2017-07-10 00:00:00', '2017-07-14 00:00:00', 5, '1', '', '1001', b'0', b'1');
INSERT INTO `wk_leave` VALUES (160, '2017-07-12 00:00:00', '2017-07-14 00:00:00', 3, '3', '《神奶》倒休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (161, '2017-07-13 00:00:00', '2017-07-13 00:00:00', 1, '1', '返台', '1051', b'0', b'1');
INSERT INTO `wk_leave` VALUES (162, '2017-07-14 00:00:00', '2017-07-14 00:00:00', 1, '1', '返台', '1051', b'0', b'1');
INSERT INTO `wk_leave` VALUES (163, '2017-07-17 00:00:00', '2017-07-21 00:00:00', 5, '1', '', '1051', b'0', b'1');
INSERT INTO `wk_leave` VALUES (164, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '环球影城快闪团', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (165, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', '环球影城快闪团', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (166, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '', '1032', b'0', b'1');
INSERT INTO `wk_leave` VALUES (167, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', '', '1032', b'0', b'1');
INSERT INTO `wk_leave` VALUES (168, '2017-07-12 00:00:00', '2017-07-12 00:00:00', 1, '1', '《神奶》出差调休', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (169, '2017-07-13 00:00:00', '2017-07-13 00:00:00', 1, '1', '', '1055', b'0', b'1');
INSERT INTO `wk_leave` VALUES (170, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '8月4日-8月7日日本环球影城快闪团', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (171, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', '8月4日-8月7日日本环球影城快闪团', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (172, '2017-02-04 00:00:00', '2017-02-06 00:00:00', 3, '1', '过年晚回公司', '1048', b'0', b'1');
INSERT INTO `wk_leave` VALUES (173, '2017-01-23 00:00:00', '2017-01-24 00:00:00', 2, '1', '过年回家', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (174, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', 'lee的大阪快闪团', '1048', b'0', b'1');
INSERT INTO `wk_leave` VALUES (175, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', 'lee的大阪快闪团', '1048', b'0', b'1');
INSERT INTO `wk_leave` VALUES (176, '2017-07-24 00:00:00', '2017-07-28 00:00:00', 5, '1', '申请年假', '1002', b'0', b'1');
INSERT INTO `wk_leave` VALUES (177, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (178, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', '', '1047', b'0', b'1');
INSERT INTO `wk_leave` VALUES (179, '2017-07-24 00:00:00', '2017-07-28 00:00:00', 5, '1', '休息', '1029', b'0', b'1');
INSERT INTO `wk_leave` VALUES (180, '2017-07-24 00:00:00', '2017-07-24 00:00:00', 1, '1', '《异星觉醒》休假', '1058', b'0', b'1');
INSERT INTO `wk_leave` VALUES (181, '2017-09-28 00:00:00', '2017-09-30 00:00:00', 3, '1', '', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (182, '2017-10-09 00:00:00', '2017-10-10 00:00:00', 2, '1', '', '1026', b'0', b'1');
INSERT INTO `wk_leave` VALUES (183, '2017-10-09 00:00:00', '2017-10-13 00:00:00', 4.5, '3', '', '1052', b'0', b'0');
INSERT INTO `wk_leave` VALUES (184, '2017-10-09 00:00:00', '2017-10-11 00:00:00', 3, '1', '', '1037', b'0', b'1');
INSERT INTO `wk_leave` VALUES (185, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '去日本', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (186, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 1, '1', '去日本', '1000', b'0', b'1');
INSERT INTO `wk_leave` VALUES (187, '2017-08-04 00:00:00', '2017-08-04 00:00:00', 1, '1', '事假', '1054', b'0', b'1');
INSERT INTO `wk_leave` VALUES (188, '2017-10-09 00:00:00', '2017-10-13 00:00:00', 5, '1', '', '1052', b'0', b'1');
INSERT INTO `wk_leave` VALUES (189, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 0.5, '1', '', '1003', b'0', b'0');
INSERT INTO `wk_leave` VALUES (190, '2017-08-07 00:00:00', '2017-08-07 00:00:00', 0.5, '1', '請假', '1003', b'0', b'0');
INSERT INTO `wk_leave` VALUES (191, '2017-06-30 00:00:00', '2017-06-30 00:00:00', 1, '1', '', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (192, '2017-08-08 00:00:00', '2017-08-08 00:00:00', 0.5, '1', '病假', '1045', b'0', b'0');
INSERT INTO `wk_leave` VALUES (193, '2017-08-18 00:00:00', '2017-08-18 00:00:00', 1, '1', '事假', '1063', b'0', b'1');
INSERT INTO `wk_leave` VALUES (194, '2017-08-11 00:00:00', '2017-08-11 00:00:00', 0.5, '1', '返台', '1051', b'1', b'1');
INSERT INTO `wk_leave` VALUES (195, '2017-08-14 00:00:00', '2017-08-14 00:00:00', 1, '1', '返台', '1051', b'0', b'1');
INSERT INTO `wk_leave` VALUES (196, '2017-08-10 00:00:00', '2017-08-11 00:00:00', 2, '1', '', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (197, '2017-08-11 00:00:00', '2017-08-11 00:00:00', 1, '1', '回老家办个手续', '1045', b'0', b'1');
INSERT INTO `wk_leave` VALUES (198, '2017-08-11 00:00:00', '2017-08-11 00:00:00', 1, '1', '身体不适', '1024', b'0', b'1');
INSERT INTO `wk_leave` VALUES (199, '2017-08-10 00:00:00', '2017-08-12 00:00:00', 3, '1', '家人去世', '1029', b'0', b'1');
INSERT INTO `wk_leave` VALUES (200, '2017-08-18 00:00:00', '2017-08-18 00:00:00', 0.5, '1', '去签证中心按指纹', '1057', b'0', b'0');
INSERT INTO `wk_leave` VALUES (201, '2017-08-25 00:00:00', '2017-08-25 00:00:00', 1, '1', '', '1003', b'0', b'1');
INSERT INTO `wk_leave` VALUES (202, '2017-08-25 00:00:00', '2017-08-25 00:00:00', 1, '1', '病假', '1056', b'0', b'1');
INSERT INTO `wk_leave` VALUES (203, '2017-08-28 00:00:00', '2017-08-28 00:00:00', 1, '1', '個人事務', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (204, '2017-09-25 00:00:00', '2017-09-29 00:00:00', 5, '1', '年假', '1028', b'0', b'1');
INSERT INTO `wk_leave` VALUES (205, '2017-10-09 00:00:00', '2017-10-13 00:00:00', 5, '1', '个人出游', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (206, '2017-10-16 00:00:00', '2017-10-18 00:00:00', 3, '1', '个人出游', '1057', b'0', b'1');
INSERT INTO `wk_leave` VALUES (207, '2017-09-14 00:00:00', '2017-09-15 00:00:00', 2, '1', '', '1055', b'0', b'1');
INSERT INTO `wk_leave` VALUES (208, '2017-09-25 00:00:00', '2017-09-30 00:00:00', 6, '1', '', '1055', b'0', b'1');
INSERT INTO `wk_leave` VALUES (209, '2017-09-12 00:00:00', '2017-09-12 00:00:00', 0.5, '1', '', '1024', b'1', b'1');
INSERT INTO `wk_leave` VALUES (210, '2017-09-13 00:00:00', '2017-09-13 00:00:00', 0.5, '1', '', '1024', b'0', b'0');
INSERT INTO `wk_leave` VALUES (211, '2017-09-08 00:00:00', '2017-09-08 00:00:00', 1, '0', '', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (212, '2017-09-11 00:00:00', '2017-09-11 00:00:00', 1, '0', '', '1041', b'0', b'1');
INSERT INTO `wk_leave` VALUES (213, '2017-09-29 00:00:00', '2017-09-30 00:00:00', 2, '0', '返台', '1051', b'0', b'1');
INSERT INTO `wk_leave` VALUES (214, '2017-09-09 00:00:00', '2017-09-10 00:00:00', 2, '0', '返台', '1051', b'0', b'1');
COMMIT;

-- ----------------------------
-- Table structure for wk_overtime
-- ----------------------------
DROP TABLE IF EXISTS `wk_overtime`;
CREATE TABLE `wk_overtime` (
  `OVERTIME_ID` int(11) NOT NULL AUTO_INCREMENT,
  `START_DATE` datetime NOT NULL COMMENT '开始日期',
  `END_DATE` datetime NOT NULL COMMENT '结束日期',
  `DURATION` double NOT NULL COMMENT '加班时长',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `NOTES` varchar(1000) DEFAULT NULL,
  `APPLICANT` varchar(255) DEFAULT NULL COMMENT '申请人',
  `START_DATE_SLOT` bit(1) NOT NULL COMMENT '开始日期是上午还是下午',
  `END_DATE_SLOT` bit(1) DEFAULT NULL COMMENT '结束日期是上午还是下午',
  `APPROVAL_OF_PERSONNEL` varchar(255) DEFAULT NULL COMMENT '审批人id',
  `APPROVAL_OF_PERSONNEL_NAME` varchar(255) DEFAULT NULL COMMENT '审批人名称',
  PRIMARY KEY (`OVERTIME_ID`),
  KEY `FK7EE00AACD48DBD1B` (`APPLICANT`),
  CONSTRAINT `FK7EE00AACD48DBD1B` FOREIGN KEY (`APPLICANT`) REFERENCES `sys_user` (`USER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wk_overtime
-- ----------------------------
BEGIN;
INSERT INTO `wk_overtime` VALUES (16, '2016-02-01 00:00:00', '2016-02-03 00:00:00', 3, 1, '2月1日，fox事务处理及飞鹰艾迪提案会；\r\n2月2日，fox事务处理及飞鹰艾迪看片会；\r\n2月3日，fox事务处理，年历邮寄。', '1024', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (17, '2016-04-24 00:00:00', '2016-04-24 00:00:00', 1, 1, '上海出差见客户（招商银行信用卡）', '1029', b'0', b'1', '1034', '王淵宏');
INSERT INTO `wk_overtime` VALUES (18, '2016-04-24 00:00:00', '2016-04-24 00:00:00', 0.5, 1, '《X战警》起亚车展进场', '1000', b'1', b'1', '1025', '陳秋詩');
INSERT INTO `wk_overtime` VALUES (19, '2016-04-23 00:00:00', '2016-04-23 00:00:00', 0.5, 1, '《 X战警》起亚车展看场地', '1000', b'1', b'1', '1025', '陳秋詩');
INSERT INTO `wk_overtime` VALUES (20, '2016-04-02 00:00:00', '2016-04-04 00:00:00', 3, 1, '电影《魔兽》展上海站清明节加班', '1000', b'0', b'1', '1025', '陳秋詩');
INSERT INTO `wk_overtime` VALUES (21, '2016-04-26 00:00:00', '2016-04-26 00:00:00', 0.5, 1, 'x战警相关事宜处理。', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (22, '2016-04-27 00:00:00', '2016-04-27 00:00:00', 0.5, 1, '19:00~21:00 for X-men', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (23, '2016-04-27 00:00:00', '2016-04-27 00:00:00', 0.5, 1, 'x战警', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (24, '2016-05-01 00:00:00', '2016-05-01 00:00:00', 0.5, 1, '跟郑州万象城开会 上午10:00-中午13:00', '1005', b'0', b'0', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (25, '2016-04-29 00:00:00', '2016-04-29 00:00:00', 0.5, 1, 'X战警相关事宜处理', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (26, '2016-05-02 00:00:00', '2016-05-02 00:00:00', 0.5, 1, 'x战警，片方临时要求整理媒体素材图情况介绍。', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (27, '2016-04-29 00:00:00', '2016-04-29 00:00:00', 0.5, 1, 'X-men 相关事宜', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (28, '2016-04-28 00:00:00', '2016-04-28 00:00:00', 0.5, 1, 'x-men 相关', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (29, '2016-05-04 00:00:00', '2016-05-04 00:00:00', 0.5, 1, 'x战警事宜相关', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (30, '2016-05-05 00:00:00', '2016-05-05 00:00:00', 0.5, 1, 'x战警、idr', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (31, '2016-05-06 00:00:00', '2016-05-06 00:00:00', 0.5, 1, 'x战警户外广告', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (32, '2016-05-13 00:00:00', '2016-05-13 00:00:00', 0.5, 1, 'X战警', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (33, '2016-05-12 00:00:00', '2016-05-12 00:00:00', 0.5, 1, 'X战警及独立日', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (34, '2016-05-09 00:00:00', '2016-05-09 00:00:00', 0.5, 1, 'X战警', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (35, '2016-05-13 00:00:00', '2016-05-13 00:00:00', 0.5, 1, 'x-men 相关', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (36, '2016-05-12 00:00:00', '2016-05-12 00:00:00', 0.5, 1, 'x-men相关', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (37, '2016-05-16 00:00:00', '2016-05-16 00:00:00', 0.5, 1, 'x战警、iacc预付款合同准备', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (38, '2016-05-18 00:00:00', '2016-05-18 00:00:00', 0.5, 1, '《x战警：天启》记者发布会帮忙 早上6点半到场 实际加班3个半小时', '1005', b'0', b'0', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (39, '2016-05-28 00:00:00', '2016-05-28 00:00:00', 1, 1, '从早上八点到下午五点', '1005', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (40, '2016-06-02 00:00:00', '2016-06-02 00:00:00', 0.5, 1, 'TRZN proposal 提案修改', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (41, '2016-06-03 00:00:00', '2016-06-03 00:00:00', 0.5, 1, 'iacc朝阳公园合同修改，trzn素材上传', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (44, '2016-10-12 00:00:00', '2016-10-12 00:00:00', 0.5, 1, '但丁密码', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (45, '2016-10-13 00:00:00', '2016-10-13 00:00:00', 0.5, 1, '但丁密码', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (46, '2016-09-22 00:00:00', '2016-09-22 00:00:00', 0.5, 1, '但丁密码', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (47, '2016-06-21 00:00:00', '2016-06-21 00:00:00', 0.5, 1, '爱宠大机密', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (48, '2016-07-11 00:00:00', '2016-07-11 00:00:00', 0.5, 1, '爱宠大机密', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (49, '2016-11-12 00:00:00', '2016-11-12 00:00:00', 1, 2, '给讨厌的华纳弄讨厌的手机号和买手机', '1024', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (50, '2016-12-09 00:00:00', '2016-12-09 00:00:00', 0.5, 2, '太空旅客活动', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (51, '2016-12-23 00:00:00', '2016-12-23 00:00:00', 0.5, 2, '太空旅客', '1002', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (52, '2017-01-03 00:00:00', '2017-01-03 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (53, '2017-01-04 00:00:00', '2017-01-04 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (54, '2017-01-05 00:00:00', '2017-01-05 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (55, '2017-01-06 00:00:00', '2017-01-06 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (56, '2017-01-09 00:00:00', '2017-01-09 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (57, '2017-01-10 00:00:00', '2017-01-10 00:00:00', 0.5, 2, '乐高蝙蝠侠+福斯财务结算资料', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (58, '2017-01-14 00:00:00', '2017-01-14 00:00:00', 0.5, 2, '乐高蝙蝠侠+处理福斯合同', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (59, '2017-01-13 00:00:00', '2017-01-13 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (60, '2017-01-11 00:00:00', '2017-01-11 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (61, '2017-01-12 00:00:00', '2017-01-12 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (62, '2016-12-26 00:00:00', '2016-12-26 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (63, '2016-12-30 00:00:00', '2016-12-30 00:00:00', 0.5, 2, '乐高蝙蝠侠', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (64, '2016-12-17 00:00:00', '2016-12-17 00:00:00', 1, 1, '《太空旅客》发布会现场支持', '1029', b'0', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (65, '2017-01-07 00:00:00', '2017-01-07 00:00:00', 0.5, 1, '周六跟搭建、Av去天桥剧场堪场地、开会', '1032', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (66, '2017-02-11 00:00:00', '2017-02-11 00:00:00', 0.5, 1, '《欢乐好声音》天桥剧场各方协调会', '1048', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (67, '2017-02-12 00:00:00', '2017-02-12 00:00:00', 0.5, 1, '天桥剧场开进场会议', '1032', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (68, '2017-02-13 00:00:00', '2017-02-13 00:00:00', 0.5, 1, '去房山看舞团彩排', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (69, '2017-02-12 00:00:00', '2017-02-12 00:00:00', 0.5, 1, '补填2月12日（周六）上午区天桥剧场走场', '1045', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (70, '2017-01-07 00:00:00', '2017-01-07 00:00:00', 0.5, 1, 'Lee让重新填，天桥剧场开会', '1032', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (71, '2017-02-12 00:00:00', '2017-02-12 00:00:00', 0.5, 1, 'Lee让重新填，天桥剧场进场会', '1032', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (72, '2017-02-13 00:00:00', '2017-02-13 00:00:00', 0.5, 1, 'Lee让重新填，房山看舞团彩排', '1032', b'1', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (73, '2017-03-04 00:00:00', '2017-03-05 00:00:00', 2, 1, '乐高大悦城活动', '1045', b'0', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (74, '2017-03-04 00:00:00', '2017-03-05 00:00:00', 2, 1, '乐高蝙蝠侠冒险乐园周六日全天加班', '1048', b'0', b'1', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (75, '2017-03-05 00:00:00', '2017-03-06 00:00:00', 1.5, 1, '乐高蝙蝠侠冒险乐园周五夜及六日全天加班', '1024', b'1', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (76, '2017-02-11 00:00:00', '2017-02-11 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》周报相关／稿件整理', '1052', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (77, '2017-02-18 00:00:00', '2017-02-18 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》周报相关／稿件整理', '1052', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (78, '2017-02-25 00:00:00', '2017-02-25 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》周报相关／稿件整理', '1052', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (79, '2017-03-04 00:00:00', '2017-03-04 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》乐高乐园活动／周报相关／稿件整理', '1052', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (80, '2017-03-05 00:00:00', '2017-03-05 00:00:00', 0.5, 1, '《一条狗的使命》PR概况整理', '1052', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (81, '2017-03-11 00:00:00', '2017-03-11 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》结案相关', '1052', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (82, '2017-02-22 00:00:00', '2017-02-22 00:00:00', 0.5, 1, '《乐高蝙蝠侠大电影》悠唐媒体看片／运剩余衍生品归司', '1052', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (83, '2017-03-01 00:00:00', '2017-03-01 00:00:00', 0.5, 1, '《乐高蝙蝠侠大电影》寄送媒体礼品', '1052', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (84, '2017-02-22 00:00:00', '2017-02-22 00:00:00', 0.5, 1, 'LEGO悠唐看片／物料整理', '1054', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (85, '2017-02-26 00:00:00', '2017-02-26 00:00:00', 1, 1, 'LEGO周报整理', '1054', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (86, '2017-03-01 00:00:00', '2017-03-01 00:00:00', 0.5, 1, 'LEGO寄送媒体礼品', '1054', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (87, '2017-03-04 00:00:00', '2017-03-04 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》大悦城活动／周报整理', '1054', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (88, '2017-03-05 00:00:00', '2017-03-05 00:00:00', 0.5, 1, '《一条狗的使命》发稿整理', '1054', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (89, '2017-03-08 00:00:00', '2017-03-08 00:00:00', 0.5, 1, '《乐高蝙蝠侠大电影》项目结案报告整理', '1054', b'1', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (90, '2017-03-11 00:00:00', '2017-03-11 00:00:00', 1, 1, '《乐高蝙蝠侠大电影》项目结案报告整理', '1054', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (91, '2017-03-19 00:00:00', '2017-03-19 00:00:00', 1, 1, '《表情奇幻冒险》报告整理', '1054', b'0', b'1', '1044', '徐薇');
INSERT INTO `wk_overtime` VALUES (92, '2017-04-16 00:00:00', '2017-04-16 00:00:00', 0.5, 1, '周六与福斯开会', '1032', b'0', b'0', '1006', '林家暉');
INSERT INTO `wk_overtime` VALUES (93, '2017-05-01 00:00:00', '2017-05-01 00:00:00', 1, 1, '蜘蛛侠x太古里 方案讨论修改', '1029', b'0', b'1', '1051', '陳郁屏');
INSERT INTO `wk_overtime` VALUES (94, '2017-04-29 00:00:00', '2017-04-29 00:00:00', 0.5, 1, '蜘蛛侠x太古里 方案讨论修改', '1029', b'1', b'1', '1051', '陳郁屏');
INSERT INTO `wk_overtime` VALUES (95, '2017-05-06 00:00:00', '2017-05-06 00:00:00', 1, 1, '实际时间5/6下午6点至5/7凌晨1点，共7hr', '1040', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (96, '2017-04-29 00:00:00', '2017-04-29 00:00:00', 1, 1, '', '1040', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (97, '2017-05-01 00:00:00', '2017-05-01 00:00:00', 1, 1, '蜘蛛侠x太古里 方案讨论修改', '1051', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (98, '2017-04-29 00:00:00', '2017-04-29 00:00:00', 1, 1, '《异星觉醒》结案贴图／稿件处理', '1052', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (99, '2017-05-06 00:00:00', '2017-05-06 00:00:00', 1, 1, '《异星觉醒》结案贴图／稿件处理', '1052', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (100, '2017-05-14 00:00:00', '2017-05-14 00:00:00', 1, 1, '《异星觉醒》结案贴图／稿件处理', '1052', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (101, '2017-05-20 00:00:00', '2017-05-20 00:00:00', 1, 2, '《异星觉醒》世纪佳缘&瑞丽包场活动事宜', '1052', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (102, '2017-05-21 00:00:00', '2017-05-21 00:00:00', 1, 2, '《异星觉醒》结案贴图／稿件处理', '1052', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (103, '2017-03-26 00:00:00', '2017-03-26 00:00:00', 0.5, 2, '徐薇离职工作交接事宜', '1052', b'1', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (104, '2017-03-26 00:00:00', '2017-03-26 00:00:00', 0.5, 2, '徐薇工作交接', '1054', b'1', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (105, '2017-04-29 00:00:00', '2017-04-29 00:00:00', 1, 1, '《异星觉醒》REPORT', '1054', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (106, '2017-05-06 00:00:00', '2017-05-06 00:00:00', 1, 1, '《异星觉醒》REPORT', '1054', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (107, '2017-05-14 00:00:00', '2017-05-14 00:00:00', 1, 1, '《异星觉醒》REPORT', '1054', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (108, '2017-05-20 00:00:00', '2017-05-20 00:00:00', 1, 2, '《异星觉醒》520线下观影会', '1054', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (109, '2017-05-21 00:00:00', '2017-05-21 00:00:00', 1, 2, '《异星觉醒》REPORT', '1054', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (110, '2017-05-20 00:00:00', '2017-05-20 00:00:00', 1, 1, '520活動', '1041', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (111, '2017-05-30 00:00:00', '2017-05-30 00:00:00', 1, 1, '《神偷奶爸3》端午节来公司加了一天班', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (112, '2017-06-11 00:00:00', '2017-06-11 00:00:00', 1, 1, '《神偷奶爸》上海站', '1045', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (113, '2017-06-11 00:00:00', '2017-06-11 00:00:00', 1, 1, '《神偷奶爸3》上海户外展览活动6月11日进场搭建', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (114, '2017-06-17 00:00:00', '2017-06-18 00:00:00', 2, 1, '《神偷奶爸3》北京颐堤港活动6月17、18日加班', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (115, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 2, 1, '《神偷奶爸3》南京中庭活动6月24、25日出差加班', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (116, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, '《神偷奶爸3》上海中庭活动7月1日、2日出差加班盯场', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (117, '2017-07-08 00:00:00', '2017-07-09 00:00:00', 2, 1, '《神偷奶爸3》杭州中庭活动7月8日、9日出差加班盯场', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (118, '2017-06-17 00:00:00', '2017-06-18 00:00:00', 2, 1, '《神偷奶爸3》北京颐堤港 展览', '1055', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (119, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, 'DM 3 上海中庭展', '1055', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (120, '2017-07-08 00:00:00', '2017-07-09 00:00:00', 2, 1, 'DM 3 杭州中庭展', '1055', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (121, '2017-06-17 00:00:00', '2017-06-18 00:00:00', 2, 1, '《神奶3》北京', '1045', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (122, '2017-06-17 00:00:00', '2017-06-17 00:00:00', 1, 1, '《神奶3》北京场', '1032', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (123, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 2, 1, '《神奶》南京站出差', '1045', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (124, '2017-06-17 00:00:00', '2017-06-18 00:00:00', 2, 1, '神偷奶爸3 卡车巡游-深圳站出差', '1059', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (125, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 2, 1, '《神偷奶爸3》 卡车巡游 重庆站出差', '1059', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (126, '2017-06-24 00:00:00', '2017-06-24 00:00:00', 1, 1, '《神奶3》南京', '1032', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (127, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 2, 1, '神偷奶爸3重庆活动出差', '1033', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (128, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, '《神奶》成都出差', '1045', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (129, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, '《神偷奶爸3》卡车巡游 成都站出差', '1059', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (130, '2017-05-01 00:00:00', '2017-05-01 00:00:00', 1, 1, '蜘蛛俠_三里屯方案合圖設計', '1047', b'0', b'1', '1051', '陳郁屏');
INSERT INTO `wk_overtime` VALUES (131, '2017-05-13 00:00:00', '2017-05-13 00:00:00', 0.5, 1, 'LIFE最終版海報漢化', '1047', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (132, '2017-06-18 00:00:00', '2017-06-18 00:00:00', 1, 1, 'EMOJI三里屯立牌點位合圖', '1047', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (133, '2017-06-25 00:00:00', '2017-06-25 00:00:00', 1, 1, '蜘蛛俠紅館內部設計', '1047', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (134, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, '神偷奶爸3成都活動出差', '1033', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (135, '2017-07-08 00:00:00', '2017-07-09 00:00:00', 2, 1, '神偷奶爸3活動武漢出差', '1033', b'0', b'1', '1003', '劉興玟');
INSERT INTO `wk_overtime` VALUES (136, '2017-06-17 00:00:00', '2017-06-18 00:00:00', 2, 1, '《神偷奶爸3》深圳站周六日加班', '1048', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (137, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 2, 1, '《神偷奶爸3》重庆站周六日加班', '1048', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (138, '2017-07-01 00:00:00', '2017-07-02 00:00:00', 2, 1, '《神偷奶爸3》成都站周六日加班', '1048', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (139, '2017-07-08 00:00:00', '2017-07-09 00:00:00', 2, 1, '《神偷奶爸3》武汉站周六日加班', '1048', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (140, '2017-06-24 00:00:00', '2017-06-25 00:00:00', 1, 1, '蜘蛛俠三里屯進場', '1051', b'1', b'0', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (141, '2017-06-25 00:00:00', '2017-06-26 00:00:00', 1, 1, '蜘蛛俠三里屯進場', '1051', b'1', b'0', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (142, '2017-03-04 00:00:00', '2017-03-04 00:00:00', 0.5, 1, '乐高蝙蝠侠加班', '1058', b'1', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (143, '2017-05-19 00:00:00', '2017-05-19 00:00:00', 1, 1, '《异星觉醒》加班', '1058', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (144, '2017-05-20 00:00:00', '2017-05-20 00:00:00', 1, 1, '《异星觉醒》观影会', '1056', b'0', b'1', '1041', '江明颖');
INSERT INTO `wk_overtime` VALUES (145, '2017-03-04 00:00:00', '2017-03-04 00:00:00', 0.5, 1, '樂高加班', '1041', b'1', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (146, '2017-09-02 00:00:00', '2017-09-03 00:00:00', 2, 1, '《蜘蛛侠》彩排', '1055', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (147, '2017-09-02 00:00:00', '2017-09-03 00:00:00', 2, 1, '蜘蛛侠发布会搭建', '1045', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (148, '2017-09-02 00:00:00', '2017-09-03 00:00:00', 2, 1, '《蜘蛛侠：英雄归来》活动', '1064', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (149, '2017-09-02 00:00:00', '2017-09-03 00:00:00', 2, 0, '《蜘蛛侠》加班', '1032', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (150, '2017-09-02 00:00:00', '2017-09-02 00:00:00', 1, 1, '《蜘蛛侠：英雄归来》发布会活动加班', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (151, '2017-09-03 00:00:00', '2017-09-03 00:00:00', 1, 1, '《蜘蛛侠：英雄归来》发布会活动加班', '1057', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (152, '2017-09-03 00:00:00', '2017-09-04 00:00:00', 2, 1, '《蜘蛛侠》周末加班', '1048', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (153, '2017-09-02 00:00:00', '2017-09-03 00:00:00', 2, 1, '《蜘蛛侠》发布会及红毯活动', '1059', b'0', b'1', '1032', '杜倩文');
INSERT INTO `wk_overtime` VALUES (154, '2017-09-02 00:00:00', '2017-09-02 00:00:00', 1, 0, '蜘蛛俠彩排上午11點到晚上11點', '1051', b'0', b'1', '1008', '陳立展');
INSERT INTO `wk_overtime` VALUES (155, '2017-09-03 00:00:00', '2017-09-03 00:00:00', 1, 0, '蜘蛛俠彩排下午一點到晚上一點', '1051', b'0', b'1', '1008', '陳立展');
COMMIT;

-- ----------------------------
-- Table structure for year_info
-- ----------------------------
DROP TABLE IF EXISTS `year_info`;
CREATE TABLE `year_info` (
  `year_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '年度id',
  `year_name` varchar(50) NOT NULL COMMENT '年度区间',
  `year_value` varchar(10) NOT NULL COMMENT '年度值',
  PRIMARY KEY (`year_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of year_info
-- ----------------------------
BEGIN;
INSERT INTO `year_info` VALUES (1, '2014年-2015年', '2014');
INSERT INTO `year_info` VALUES (2, '2015年-2016年', '2015');
INSERT INTO `year_info` VALUES (3, '2016年-2017年', '2016');
INSERT INTO `year_info` VALUES (4, '2017年-2018年', '2017');
INSERT INTO `year_info` VALUES (5, '2018年-2019年', '2018');
INSERT INTO `year_info` VALUES (6, '2019年-2020年', '2019');
INSERT INTO `year_info` VALUES (7, '2020年-2021年', '2020');
INSERT INTO `year_info` VALUES (8, '2021年-2022年', '2021');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
