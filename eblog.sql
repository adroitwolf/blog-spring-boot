/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : eblog

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-12-01 13:11:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `ID` bigint(20) unsigned NOT NULL COMMENT '博客id',
  `BLOGGER_ID` bigint(20) unsigned NOT NULL COMMENT '作者id',
  `STATUS` varchar(10) NOT NULL DEFAULT '' COMMENT '博客状态',
  `TITLE` varchar(80) NOT NULL COMMENT '博客标题',
  `PICTURE_ID` bigint(20) DEFAULT NULL COMMENT '图片id',
  `SUMMARY` varchar(400) NOT NULL COMMENT '博客总结',
  `RELEASE_DATE` datetime NOT NULL COMMENT '发布日期',
  `NEAREST_MODIFY_DATE` datetime NOT NULL COMMENT '最近修改时间',
  `TAG_TITLE` varchar(1024) DEFAULT NULL COMMENT '标签',
  `COLUMN_10` char(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BLOGGER_ID` (`BLOGGER_ID`,`TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_account
-- ----------------------------
DROP TABLE IF EXISTS `blogger_account`;
CREATE TABLE `blogger_account` (
  `ID` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `USERNAME` varchar(50) NOT NULL COMMENT '用户账号',
  `PASSWORD` varchar(100) NOT NULL COMMENT '用户密码',
  `REGISTER_DATE` datetime NOT NULL COMMENT '注册日期',
  `PHONE` varchar(20) DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `IS_ENABLED` varchar(5) DEFAULT NULL COMMENT '账号是否可用',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_picture
-- ----------------------------
DROP TABLE IF EXISTS `blogger_picture`;
CREATE TABLE `blogger_picture` (
  `ID` bigint(20) unsigned NOT NULL COMMENT '照片id',
  `BLOGGER_ID` bigint(20) DEFAULT NULL COMMENT '照片原作者id',
  `PATH` varchar(1024) NOT NULL COMMENT '物理硬盘地址',
  `TITLE` varchar(200) NOT NULL COMMENT '照片标题',
  `UPLOAD_DATE` datetime NOT NULL COMMENT '照片上传日期',
  `SUFFX` varchar(50) NOT NULL COMMENT '照片后缀',
  `SIZE` bigint(20) DEFAULT NULL COMMENT '照片规格大小',
  `WIDTH` int(11) DEFAULT NULL COMMENT '照片宽度',
  `HEIGHT` int(11) DEFAULT NULL COMMENT '照片高度',
  `UPDATE_DATE` datetime NOT NULL COMMENT '更新日期',
  `CITE_NUM` int(11) NOT NULL DEFAULT '0' COMMENT '被引用次数',
  `THUMB_PATH` varchar(2048) DEFAULT NULL COMMENT '文章缩略图',
  `MEDIA_TYPE` varchar(50) NOT NULL COMMENT '媒体类型',
  `FILE_KEY` varchar(200) NOT NULL COMMENT '物理磁盘的名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_profile
-- ----------------------------
DROP TABLE IF EXISTS `blogger_profile`;
CREATE TABLE `blogger_profile` (
  `BLOGGER_ID` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `ABOUT_ME` varchar(1024) DEFAULT NULL COMMENT '个人介绍',
  `NICKNAME` varchar(20) NOT NULL COMMENT '用户名',
  `AVATAR_ID` bigint(20) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`BLOGGER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_role
-- ----------------------------
DROP TABLE IF EXISTS `blogger_role`;
CREATE TABLE `blogger_role` (
  `ID` bigint(20) NOT NULL COMMENT '角色id',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `ROLE_ZH` varchar(50) DEFAULT NULL COMMENT '角色中文名称',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AK_KEY_2` (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogger_role_map
-- ----------------------------
DROP TABLE IF EXISTS `blogger_role_map`;
CREATE TABLE `blogger_role_map` (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户id',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色的关联表';

-- ----------------------------
-- Table structure for blog_content
-- ----------------------------
DROP TABLE IF EXISTS `blog_content`;
CREATE TABLE `blog_content` (
  `ID` bigint(20) NOT NULL COMMENT '博客id',
  `CONTENT` longtext NOT NULL COMMENT '博客内容',
  `CONTENT_MD` longtext NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_label
-- ----------------------------
DROP TABLE IF EXISTS `blog_label`;
CREATE TABLE `blog_label` (
  `ID` bigint(20) unsigned NOT NULL COMMENT '标签id',
  `TITLE` varchar(20) NOT NULL COMMENT '标签名称',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建日期',
  `CITE_NUM` int(11) NOT NULL COMMENT '引用人数',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BLOGGER_ID_2` (`TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `ID` bigint(20) NOT NULL COMMENT '博客日志id',
  `ROMOTEIP` varchar(20) NOT NULL COMMENT '远程登陆ip',
  `ROMOTETIME` datetime NOT NULL,
  `OPERATION` varchar(255) DEFAULT NULL COMMENT '远程操作指令',
  `USERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_status
-- ----------------------------
DROP TABLE IF EXISTS `blog_status`;
CREATE TABLE `blog_status` (
  `ID` bigint(20) NOT NULL COMMENT '博客id',
  `CLICKCOUNT` int(11) DEFAULT NULL COMMENT '点击数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客状态表 记录点赞数等';

-- ----------------------------
-- Table structure for blog_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag_map`;
CREATE TABLE `blog_tag_map` (
  `TAG_ID` bigint(20) NOT NULL COMMENT '标签id',
  `BLOG_ID` bigint(20) NOT NULL COMMENT '博客id',
  PRIMARY KEY (`TAG_ID`,`BLOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission_operation_map
-- ----------------------------
DROP TABLE IF EXISTS `permission_operation_map`;
CREATE TABLE `permission_operation_map` (
  `OPERATION_ID` bigint(20) NOT NULL COMMENT '操作权限id',
  `PERMISSION_ID` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`OPERATION_ID`,`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限和操作权限关联表';

-- ----------------------------
-- Table structure for photowall
-- ----------------------------
DROP TABLE IF EXISTS `photowall`;
CREATE TABLE `photowall` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORIGIN_TAGS` varchar(1024) DEFAULT NULL COMMENT '图片原名tag,并且'',''分割',
  `TAGS` varchar(1024) DEFAULT NULL COMMENT '图片tag,并且'',''分割',
  `WIDTH` int(11) DEFAULT NULL COMMENT '图片宽度',
  `HEIGHT` int(11) DEFAULT NULL COMMENT '图片高度',
  `TITLE` varchar(100) DEFAULT NULL COMMENT '图片名称',
  `PATH` varchar(100) DEFAULT NULL COMMENT '物理地址映射',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3978 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission_map
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_map`;
CREATE TABLE `role_permission_map` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色id',
  `PERMISSION_ID` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色和权限的关联表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` bigint(20) NOT NULL COMMENT '菜单id',
  `P_ID` bigint(20) DEFAULT NULL COMMENT '父级菜单id',
  `MENU_NAME` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `MENU_ICON` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `MENU_PATH` varchar(100) DEFAULT NULL COMMENT '菜单路径',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `ID` bigint(20) NOT NULL COMMENT '操作id',
  `OPERATION_NAME` varchar(50) DEFAULT NULL COMMENT '操作名称',
  `OPERATION_DES` varchar(100) DEFAULT NULL COMMENT '操作描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `ID` bigint(20) NOT NULL COMMENT '权限id',
  `PERMISSION_NAME` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `PERMISSION_DES` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `MENU_ID` bigint(20) DEFAULT NULL COMMENT '菜单id 保证它和菜单的关联',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
