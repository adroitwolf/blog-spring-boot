/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-08-07 07:52:22
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
  `content` longtext NOT NULL COMMENT '博文主体内容html格式',
  `content_md` longtext NOT NULL COMMENT '博文主题内容md格式',
  `summary` varchar(400) NOT NULL COMMENT '博文摘要',
  `release_date` datetime NOT NULL COMMENT '首次发布日期',
  `nearest_modify_date` datetime NOT NULL COMMENT '最后一次修改时间',
  `tag_title` varchar(20) DEFAULT NULL COMMENT '博文标签',
  PRIMARY KEY (`id`),
  UNIQUE KEY `blogger_id` (`blogger_id`,`title`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`blogger_id`) REFERENCES `blogger_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('2', '1', 'PUBLISHED', '测试专用', '<h1><a id=\"_0\"></a>测试专用</h1>\n<p>本篇博客仅供测试专用 +1s</p>', '# 测试专用\n本篇博客仅供测试专用 +1s', '测试专用', '2019-07-26 08:14:53', '2019-07-27 13:59:26', '');
INSERT INTO `blog` VALUES ('4', '1', 'PUBLISHED', '2222', '<h1><a id=\"2222_0\"></a>2222</h1>', '# 2222', '123131', '2019-07-28 14:33:14', '2019-07-28 14:33:14', null);
INSERT INTO `blog` VALUES ('5', '1', 'PUBLISHED', '111', '<h1><a id=\"11111_0\"></a>11111</h1>\n<hr />\n<p>测试</p>', '# 11111\n----\n测试', '111111', '2019-07-28 19:58:56', '2019-07-30 07:36:25', null);
INSERT INTO `blog` VALUES ('6', '1', 'PUBLISHED', '333', '<h1><a id=\"333333_0\"></a>333333</h1>', '# 333333', '333333', '2019-07-28 19:59:04', '2019-07-28 19:59:04', '');
INSERT INTO `blog` VALUES ('7', '1', 'PUBLISHED', '444444', '<h1><a id=\"444444_0\"></a>444444</h1>', '# 444444', '444444', '2019-07-28 19:59:13', '2019-07-28 19:59:13', '');
INSERT INTO `blog` VALUES ('8', '1', 'PUBLISHED', '555555', '<h1><a id=\"555555_0\"></a>555555</h1>', '# 555555', '55555', '2019-07-28 19:59:28', '2019-07-28 19:59:28', '');
INSERT INTO `blog` VALUES ('9', '1', 'PUBLISHED', '666666', '<h1><a id=\"666666_0\"></a>666666</h1>', '# 666666', '666666', '2019-07-28 19:59:39', '2019-07-28 19:59:39', '');
INSERT INTO `blog` VALUES ('10', '1', 'PUBLISHED', '67777777', '<h1><a id=\"777777_0\"></a>777777</h1>', '# 777777', '777777', '2019-07-28 19:59:48', '2019-07-28 19:59:48', '');
INSERT INTO `blog` VALUES ('11', '1', 'PUBLISHED', '888888', '<h1><a id=\"888888_0\"></a>888888</h1>', '# 888888', '888888', '2019-07-28 19:59:57', '2019-07-28 19:59:57', '');
INSERT INTO `blog` VALUES ('12', '1', 'PUBLISHED', '999999', '<h1><a id=\"999999_0\"></a>999999</h1>', '# 999999', '999999', '2019-07-28 20:00:07', '2019-07-28 20:00:07', null);
INSERT INTO `blog` VALUES ('13', '1', 'PUBLISHED', '000000', '<h1><a id=\"000000_0\"></a>000000</h1>\n<hr />\n<p>测试专用</p>', '# 000000\n\n---------\n\n\n测试专用', '000000', '2019-07-28 20:00:20', '2019-07-29 20:26:55', '');

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
-- Records of blogger_account
-- ----------------------------
INSERT INTO `blogger_account` VALUES ('1', 'test', '098f6bcd4621d373cade4e832627b4f6', '2019-07-23 08:26:14');

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
-- Records of blogger_profile
-- ----------------------------
INSERT INTO `blogger_profile` VALUES ('1', '1', '13012403735', '13012403735@163.com', '没有安全的系统', 'WHOAMI', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_log
-- ----------------------------
INSERT INTO `blog_log` VALUES ('4', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:47:07', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('5', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:47:28', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('6', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:49:17', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('7', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:49:17', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('8', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:49:23', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('9', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:49:26', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('10', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:49:29', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('11', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:06', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('12', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:06', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('13', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:09', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('14', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:16', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('15', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:18', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('16', '0:0:0:0:0:0:0:1', 'test', '2019-07-30 16:52:23', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('17', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:30:58', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('18', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:30:58', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('19', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:31:04', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('20', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:31:08', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('21', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:31:09', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('22', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:31:21', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('23', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:31:22', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('24', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:35:14', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('25', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:35:14', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('26', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:37:13', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('27', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:37:13', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('28', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:37:23', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('29', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 09:37:23', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('30', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:30:40', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('31', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:30:40', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('32', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:30:46', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('33', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:31:44', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('34', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:31:44', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('35', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 14:31:46', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('36', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 17:07:30', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('37', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 17:07:30', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('38', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:51:54', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('39', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:51:54', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('40', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:51:57', 'run.app.controller.api.UserController.logout');
INSERT INTO `blog_log` VALUES ('41', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:51:59', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('42', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:51:59', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('43', '0:0:0:0:0:0:0:1', 'test', '2019-07-31 19:52:03', 'run.app.controller.api.UserController.logout');
INSERT INTO `blog_log` VALUES ('44', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 16:26:46', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('45', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 16:26:46', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('46', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 16:26:50', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('47', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 16:27:18', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('48', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 16:27:18', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('49', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:29:03', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('50', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:29:03', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('51', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:30:56', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('52', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:30:56', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('53', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:34:35', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('54', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:34:35', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('55', '0:0:0:0:0:0:0:1', 'test', '2019-08-06 17:36:17', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('56', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:21:38', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('57', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:21:38', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('58', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:22:05', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('59', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:32:03', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('60', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:32:03', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('61', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:32:16', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('62', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:32:16', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('63', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:37:44', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('64', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:37:44', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('65', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:37:53', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('66', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:37:53', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('67', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:38:41', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('68', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:38:41', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('69', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:38:46', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('70', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:38:46', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('71', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:43:16', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('72', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:43:16', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('73', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:40', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('74', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:45', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('75', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:49', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('76', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:51', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('77', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:52', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('78', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:51:53', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('79', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:52:25', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('80', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:52:31', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('81', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:52:31', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('82', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:52:37', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('83', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:54:06', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('84', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:54:06', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('85', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:54:20', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('86', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:54:20', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('87', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:57:05', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('88', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:57:05', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('89', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:58:01', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('90', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:58:01', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('91', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:59:15', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('92', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 06:59:15', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('93', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:03:46', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('94', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:03:46', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('95', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:10:17', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('96', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:10:17', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('97', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:13', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('98', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:13', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('99', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:48', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('100', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:48', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('101', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:53', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('102', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:13:53', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('103', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:15:49', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('104', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:15:49', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('105', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:17:31', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('106', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:17:31', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('107', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:23:37', 'run.app.controller.api.BlogController.getList');
INSERT INTO `blog_log` VALUES ('108', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:23:37', 'run.app.controller.api.UserController.getUserDetail');
INSERT INTO `blog_log` VALUES ('109', '0:0:0:0:0:0:0:1', 'test', '2019-08-07 07:23:39', 'run.app.controller.api.UserController.logout');
