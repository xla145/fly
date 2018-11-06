/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : dream_admin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-11-06 18:40:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `cat_id` int(11) NOT NULL COMMENT '文章分类',
  `cat_name` varchar(50) NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL COMMENT '文章创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `browse` bigint(11) NOT NULL DEFAULT '0' COMMENT '文章浏览量',
  `create_uid` int(11) NOT NULL COMMENT '创建用户uid',
  `info` text NOT NULL COMMENT '文章内容',
  `pay_point` int(11) NOT NULL DEFAULT '10' COMMENT '发布文章消耗积分 10 - 200 之间（后台字典配置）',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  -1：已删除 0：待审核 5：成功发布 10：未结 15：完结 20：不通过',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `label` varchar(100) NOT NULL COMMENT '标签',
  `weight` tinyint(4) NOT NULL COMMENT '权重',
  `is_top` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `is_comment` tinyint(4) NOT NULL COMMENT '是否支持评论',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_browse
-- ----------------------------
DROP TABLE IF EXISTS `article_browse`;
CREATE TABLE `article_browse` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL COMMENT '浏览文章用户',
  `article_id` int(11) NOT NULL COMMENT '文章编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `reamark` varchar(255) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `status` tinyint(4) NOT NULL COMMENT '状态 0：停用 1：启用',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL COMMENT '评论的文章',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `uid` int(11) NOT NULL COMMENT '评论用户uid',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_comment_replay
-- ----------------------------
DROP TABLE IF EXISTS `article_comment_replay`;
CREATE TABLE `article_comment_replay` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL COMMENT '评论用户',
  `c_id` int(11) NOT NULL COMMENT '评论编号',
  `content` varchar(255) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '父回复编号',
  `create_time` datetime NOT NULL,
  `remark` varchar(255) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_commont_love
-- ----------------------------
DROP TABLE IF EXISTS `article_commont_love`;
CREATE TABLE `article_commont_love` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `ac_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dict_group
-- ----------------------------
DROP TABLE IF EXISTS `dict_group`;
CREATE TABLE `dict_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL COMMENT '字典类型编号',
  `info` varchar(512) NOT NULL COMMENT '组描述',
  `belong` tinyint(4) NOT NULL COMMENT '组隶属于（0： 系统 1：运营）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_dict_group_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='字典组';

-- ----------------------------
-- Table structure for dict_item
-- ----------------------------
DROP TABLE IF EXISTS `dict_item`;
CREATE TABLE `dict_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典类型code',
  `group_code` varchar(100) NOT NULL COMMENT '组编号',
  `name` varchar(200) NOT NULL COMMENT '配置项名称 key',
  `value` varchar(512) NOT NULL COMMENT '业务代码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  `info` varchar(512) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ids_1` (`group_code`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='字典项';

-- ----------------------------
-- Table structure for image_text_group
-- ----------------------------
DROP TABLE IF EXISTS `image_text_group`;
CREATE TABLE `image_text_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '组编码',
  `name` varchar(50) NOT NULL COMMENT '组名字',
  `purpose` varchar(250) NOT NULL COMMENT '用途，目的',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '状态 1：启动 0：停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_data_dic_group_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for image_text_item
-- ----------------------------
DROP TABLE IF EXISTS `image_text_item`;
CREATE TABLE `image_text_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '组编码',
  `group_code` varchar(50) NOT NULL COMMENT '组编码',
  `title` varchar(512) DEFAULT NULL COMMENT '文字标题',
  `img_url` varchar(512) DEFAULT NULL COMMENT '图片地址',
  `url` varchar(512) NOT NULL DEFAULT 'javascript:void(0)' COMMENT '链接地址 ',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重，越小越靠前',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `status` int(5) NOT NULL DEFAULT '1' COMMENT '状态：0 无效， 1 有效， -1 删除',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `nickname` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` int(2) DEFAULT NULL COMMENT '性别 1：男 2：女',
  `fr` varchar(64) DEFAULT NULL COMMENT '注册渠道',
  `platform` varchar(64) NOT NULL COMMENT '注册平台',
  `ip` varchar(64) DEFAULT NULL COMMENT '注册ip',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `last_time` datetime NOT NULL COMMENT '最后登录时间',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `birthday` datetime DEFAULT NULL COMMENT '会员生日',
  `channel_id` int(11) NOT NULL DEFAULT '1' COMMENT '注册渠道编号 默认1 自注册 2:qq 3:微博',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16368 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_article
-- ----------------------------
DROP TABLE IF EXISTS `member_article`;
CREATE TABLE `member_article` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '收藏用户uid',
  `article_id` int(11) NOT NULL COMMENT '文章编号',
  `article_title` varchar(255) NOT NULL COMMENT '文章标题',
  `create_time` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏文章表';

-- ----------------------------
-- Table structure for member_grow_log
-- ----------------------------
DROP TABLE IF EXISTS `member_grow_log`;
CREATE TABLE `member_grow_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `growth_value` int(11) NOT NULL DEFAULT '0' COMMENT '成长值',
  `point_value` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `symbol` int(1) NOT NULL DEFAULT '1' COMMENT '符号   1：表示添加， 2：表示减少',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `oper_id` int(11) NOT NULL COMMENT '操作人id',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `source` varchar(100) NOT NULL COMMENT '来源',
  `type` tinyint(4) NOT NULL COMMENT '来源类型 1：签到 2：发表文章 3：后台赠送 4：其他',
  PRIMARY KEY (`id`),
  KEY `ids_1` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2446 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_info
-- ----------------------------
DROP TABLE IF EXISTS `member_info`;
CREATE TABLE `member_info` (
  `uid` int(11) NOT NULL,
  `vip` int(3) NOT NULL DEFAULT '1' COMMENT '会员当前vip等级',
  `vip_name` varchar(256) NOT NULL DEFAULT '普通会员' COMMENT '会员当前vip等级名',
  `growth_value` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员当前成长值',
  `point_value` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员当前积分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_log
-- ----------------------------
DROP TABLE IF EXISTS `member_log`;
CREATE TABLE `member_log` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `remark` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_message
-- ----------------------------
DROP TABLE IF EXISTS `member_message`;
CREATE TABLE `member_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_uid` int(11) NOT NULL COMMENT '发送消息的用户ID',
  `to_uid` int(11) NOT NULL COMMENT '接收消息的用户ID',
  `article_id` int(11) NOT NULL COMMENT '消息可能关联的帖子',
  `comment_id` int(11) NOT NULL COMMENT '消息可能关联的评论',
  `content` text NOT NULL COMMENT '消息内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for member_qq
-- ----------------------------
DROP TABLE IF EXISTS `member_qq`;
CREATE TABLE `member_qq` (
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `open_id` varchar(200) DEFAULT NULL COMMENT '用户openID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uid`),
  KEY `uniq_1` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_weibo
-- ----------------------------
DROP TABLE IF EXISTS `member_weibo`;
CREATE TABLE `member_weibo` (
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `open_id` varchar(200) DEFAULT NULL COMMENT '用户openID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `xcx_open_id` varchar(200) DEFAULT NULL COMMENT '小程序用户openid',
  PRIMARY KEY (`uid`),
  KEY `uniq_1` (`open_id`) USING BTREE,
  KEY `xcx_open_id` (`xcx_open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '功能名称',
  `url` varchar(100) NOT NULL DEFAULT 'javascript:void(0);' COMMENT '功能url',
  `type` int(2) NOT NULL COMMENT '1：系统功能 2：导航菜单',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单id',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  `perms` varchar(255) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `icon` varchar(500) DEFAULT '' COMMENT '图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `weight` int(11) NOT NULL DEFAULT '99' COMMENT '权重 越大越靠前',
  `status` int(11) DEFAULT '1' COMMENT '状态 1:可用 0：不可用 -1：删除',
  `update_time` datetime NOT NULL,
  `parent_name` varchar(20) DEFAULT NULL COMMENT '父级名称',
  `level` tinyint(4) NOT NULL COMMENT '等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_log`;
CREATE TABLE `sys_operator_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_uid` varchar(20) NOT NULL COMMENT '操作人uid',
  `sys_name` varchar(255) NOT NULL COMMENT '操作人名字',
  `operation` varchar(100) NOT NULL COMMENT '操作名字',
  `ip` varchar(20) NOT NULL COMMENT '用户ip地址',
  `params` varchar(512) NOT NULL COMMENT '操作内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `method` varchar(255) NOT NULL COMMENT '方法名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(200) NOT NULL COMMENT '角色名称',
  `describe` varchar(200) NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_action`;
CREATE TABLE `sys_role_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `action_id` int(11) NOT NULL COMMENT '功能id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_uid` int(11) NOT NULL COMMENT '创建人id',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14379 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户uid',
  `name` varchar(20) NOT NULL COMMENT '用户名',
  `pswd` varchar(100) NOT NULL COMMENT '用户密码',
  `real_name` varchar(100) NOT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `qq` varchar(15) DEFAULT NULL COMMENT '联系qq',
  `email` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `create_uid` int(11) NOT NULL COMMENT '创建人',
  `type` int(2) NOT NULL COMMENT '1:根用户 2：商户',
  `is_valid` int(1) NOT NULL DEFAULT '0' COMMENT '0 无效 1 有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `sys_code` varchar(128) DEFAULT 'xdwl' COMMENT '系统码',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uniq` (`name`),
  KEY `idx` (`name`,`pswd`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_action`;
CREATE TABLE `sys_user_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `action_id` int(11) NOT NULL COMMENT '功能id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_uid` int(11) NOT NULL COMMENT '创建人',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  `role_id` int(11) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_1` (`uid`,`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_cache`;
CREATE TABLE `sys_user_cache` (
  `cache_id` varchar(100) NOT NULL COMMENT 'session编号',
  `session` blob NOT NULL COMMENT 'session 值',
  `expiration` double NOT NULL DEFAULT '0' COMMENT '过期时间',
  PRIMARY KEY (`cache_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
