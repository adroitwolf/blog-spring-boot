/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-08-14 16:50:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '博文id',
  `blogger_id` int(11) unsigned NOT NULL COMMENT '博文所属博主id',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '博文状态',
  `title` varchar(80) NOT NULL COMMENT '博文标题',
  `summary` varchar(400) NOT NULL COMMENT '博文摘要',
  `release_date` datetime NOT NULL COMMENT '首次发布日期',
  `nearest_modify_date` datetime NOT NULL COMMENT '最后一次修改时间',
  `tag_title` varchar(20) DEFAULT NULL COMMENT '博文标签',
  PRIMARY KEY (`id`),
  UNIQUE KEY `blogger_id` (`blogger_id`,`title`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`blogger_id`) REFERENCES `blogger_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_account
-- ----------------------------
DROP TABLE IF EXISTS `blogger_account`;
CREATE TABLE `blogger_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '博主id',
  `username` varchar(50) NOT NULL COMMENT '博主用户名',
  `password` varchar(100) NOT NULL COMMENT '博主密码',
  `register_date` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_profile
-- ----------------------------
DROP TABLE IF EXISTS `blogger_profile`;
CREATE TABLE `blogger_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '博主资料id',
  `blogger_id` int(11) unsigned NOT NULL COMMENT '博主id',
  `phone` varchar(20) NOT NULL COMMENT '电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `about_me` text NOT NULL COMMENT '关于我',
  `intro` text NOT NULL COMMENT '一句话简介',
  `avatar_id` int(10) unsigned DEFAULT NULL COMMENT '博主头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `blogger_id` (`blogger_id`),
  UNIQUE KEY `phone` (`phone`),
  KEY `avatar_id` (`avatar_id`),
  CONSTRAINT `blogger_profile_ibfk_2` FOREIGN KEY (`blogger_id`) REFERENCES `blogger_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_content
-- ----------------------------
DROP TABLE IF EXISTS `blog_content`;
CREATE TABLE `blog_content` (
  `id` int(11) NOT NULL COMMENT '博客的id',
  `content` longtext NOT NULL COMMENT '博文主要html格式内容',
  `content_md` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `romoteIp` varchar(20) NOT NULL COMMENT '远程访问的ip',
  `username` varchar(255) DEFAULT NULL COMMENT '用户登陆账户',
  `romoteTime` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `operation` varchar(255) DEFAULT NULL COMMENT '用户操作',
  PRIMARY KEY (`id`),
  KEY `userId` (`username`),
  CONSTRAINT `userId` FOREIGN KEY (`username`) REFERENCES `blogger_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;
