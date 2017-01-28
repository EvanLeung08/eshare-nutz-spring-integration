/*
Navicat MySQL Data Transfer

Source Server         : evanshare
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : evanshare

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-01-28 21:10:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_at_profile
-- ----------------------------
DROP TABLE IF EXISTS `tb_at_profile`;
CREATE TABLE `tb_at_profile` (
  `uid` int(32) NOT NULL DEFAULT '0',
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_checked` tinyint(1) DEFAULT NULL,
  `avatar` mediumblob,
  `gender` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
