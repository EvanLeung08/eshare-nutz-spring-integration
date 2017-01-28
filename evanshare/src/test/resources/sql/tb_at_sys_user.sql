/*
Navicat MySQL Data Transfer

Source Server         : evanshare
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : evanshare

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-01-28 21:10:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_at_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_at_sys_user`;
CREATE TABLE `tb_at_sys_user` (
  `userId` int(32) NOT NULL AUTO_INCREMENT,
  `realName` varchar(50) NOT NULL,
  `revision` int(32) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `realName` (`realName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
