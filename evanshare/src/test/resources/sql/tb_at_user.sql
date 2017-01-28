/*
Navicat MySQL Data Transfer

Source Server         : evanshare
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : evanshare

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-01-28 21:11:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_at_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_at_user`;
CREATE TABLE `tb_at_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
