/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-09-24 23:29:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) unsigned NOT NULL COMMENT '????id',
  `blogger_id` bigint(20) unsigned NOT NULL COMMENT '????????????id',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '????״̬',
  `title` varchar(80) NOT NULL COMMENT '???ı??',
  `picture_id` bigint(20) DEFAULT NULL COMMENT '????Ӧ?õ???Ƭ',
  `summary` varchar(400) NOT NULL COMMENT '????ժҪ',
  `release_date` datetime NOT NULL COMMENT '?״η??????',
  `nearest_modify_date` datetime NOT NULL COMMENT '????һ???޸?ʱ?',
  `tag_title` varchar(80) DEFAULT NULL COMMENT '???ı?ǩ  ?м???.?ָ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `blogger_id` (`blogger_id`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_account
-- ----------------------------
DROP TABLE IF EXISTS `blogger_account`;
CREATE TABLE `blogger_account` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户账号',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `register_date` datetime NOT NULL COMMENT '注册日期',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_picture
-- ----------------------------
DROP TABLE IF EXISTS `blogger_picture`;
CREATE TABLE `blogger_picture` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `blogger_id` bigint(20) unsigned NOT NULL COMMENT '照片所属博主id',
  `path` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '物理硬盘地址',
  `title` varchar(200) NOT NULL COMMENT '照片标题',
  `upload_date` datetime NOT NULL COMMENT '照片上传日期',
  `suffx` varchar(50) NOT NULL COMMENT '照片后缀',
  `size` bigint(20) DEFAULT NULL COMMENT '照片规格大小',
  `width` int(11) DEFAULT NULL COMMENT '照片宽度',
  `height` int(11) DEFAULT NULL COMMENT '照片高度',
  `update_date` datetime NOT NULL COMMENT '更新日期',
  `cite_num` int(11) NOT NULL DEFAULT '0' COMMENT '被引用次数',
  `thumb_path` varchar(2048) DEFAULT NULL COMMENT '文章缩略图',
  `media_type` varchar(50) NOT NULL COMMENT '媒体类型',
  `file_key` varchar(200) NOT NULL COMMENT '物理磁盘的名称',
  PRIMARY KEY (`id`),
  KEY `blogger_id` (`blogger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=373248458343055361 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_profile
-- ----------------------------
DROP TABLE IF EXISTS `blogger_profile`;
CREATE TABLE `blogger_profile` (
  `blogger_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `about_me` varchar(1024) DEFAULT NULL COMMENT '个人介绍',
  `nickname` varchar(20) NOT NULL COMMENT '用户名',
  `avatar_id` varchar(255) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`blogger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_content
-- ----------------------------
DROP TABLE IF EXISTS `blog_content`;
CREATE TABLE `blog_content` (
  `id` bigint(20) NOT NULL COMMENT '???͵?id',
  `content` longtext NOT NULL COMMENT '??????Ҫhtml??ʽ???',
  `content_md` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_label
-- ----------------------------
DROP TABLE IF EXISTS `blog_label`;
CREATE TABLE `blog_label` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '??ǩid',
  `title` varchar(20) NOT NULL COMMENT '??ǩ??',
  `create_date` datetime NOT NULL COMMENT '??ǩ????ʱ?',
  `cite_num` int(11) NOT NULL COMMENT '???ô???',
  PRIMARY KEY (`id`),
  UNIQUE KEY `blogger_id_2` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=373248471727079425 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `id` bigint(20) NOT NULL,
  `romoteIp` varchar(20) NOT NULL COMMENT 'Զ?̷??ʵ?ip',
  `username` varchar(255) DEFAULT NULL COMMENT '?û???½?˻?',
  `romoteTime` datetime NOT NULL,
  `operation` varchar(255) DEFAULT NULL COMMENT '?û?????',
  PRIMARY KEY (`id`),
  KEY `userId` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag_map`;
CREATE TABLE `blog_tag_map` (
  `tag_id` bigint(20) NOT NULL COMMENT '??ǩid',
  `blog_id` bigint(20) NOT NULL COMMENT '????id',
  PRIMARY KEY (`tag_id`,`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
