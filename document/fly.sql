/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : dream_admin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-11-29 12:02:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `aid` varchar(30) NOT NULL COMMENT '文章编号',
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
  `label` varchar(100) DEFAULT NULL COMMENT '标签',
  `weight` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权重',
  `is_top` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `is_comment` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否支持评论',
  `comment_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `is_good` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为精贴',
  PRIMARY KEY (`aid`)
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `alias` varchar(100) NOT NULL COMMENT '别名',
  `status` tinyint(4) NOT NULL COMMENT '状态 0：停用 1：启用',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_hot` tinyint(255) NOT NULL DEFAULT '0' COMMENT '是否是热门分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
-- Table structure for article_comment_love
-- ----------------------------
DROP TABLE IF EXISTS `article_comment_love`;
CREATE TABLE `article_comment_love` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `ac_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典组';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `nickname` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` int(2) DEFAULT NULL COMMENT '性别 1：男 2：女',
  `fr` varchar(64) DEFAULT NULL COMMENT '注册渠道',
  `platform` varchar(64) NOT NULL COMMENT '注册平台',
  `ip` varchar(64) DEFAULT NULL COMMENT '注册ip',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` int(5) NOT NULL DEFAULT '1' COMMENT '状态：0 无效，2：用户未激活邮箱  5：有效， -1 删除',
  `last_time` datetime NOT NULL COMMENT '最后登录时间',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `birthday` datetime DEFAULT NULL COMMENT '会员生日',
  `channel_id` int(11) NOT NULL DEFAULT '1' COMMENT '注册渠道编号 默认1 自注册 2:qq 3:微博',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=2454 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_info
-- ----------------------------
DROP TABLE IF EXISTS `member_info`;
CREATE TABLE `member_info` (
  `uid` int(11) NOT NULL,
  `vip` int(3) NOT NULL DEFAULT '1' COMMENT '会员当前vip等级',
  `vip_name` varchar(256) NOT NULL DEFAULT '普通会员' COMMENT '会员当前vip等级名',
  `growth_value` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员当前成长值',
  `point_value` bigint(20) DEFAULT '0' COMMENT '会员当前积分',
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
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `uuid` varchar(100) NOT NULL COMMENT '用户openID',
  `update_time` datetime NOT NULL COMMENT '授权状态 1：绑定中 2：解绑',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '授权状态 1：绑定中 2：解绑',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for member_weibo
-- ----------------------------
DROP TABLE IF EXISTS `member_weibo`;
CREATE TABLE `member_weibo` (
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `uuid` varchar(200) NOT NULL COMMENT '微博的用户uid',
  `update_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '授权状态 1：绑定中 2：解绑',
  PRIMARY KEY (`uid`),
  KEY `xcx_open_id` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_conf
-- ----------------------------
DROP TABLE IF EXISTS `sms_conf`;
CREATE TABLE `sms_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cmd` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '标记',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态  0：初始状态 10：启用 15：停用',
  `plan_class` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类名',
  `plan_describe` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `temp` varchar(64) NOT NULL COMMENT '模板',
  `aisle` varchar(64) NOT NULL DEFAULT '' COMMENT '通道',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `temp_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cmd` (`cmd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_oper
-- ----------------------------
DROP TABLE IF EXISTS `sms_oper`;
CREATE TABLE `sms_oper` (
  `sid` int(11) NOT NULL COMMENT '主键',
  `aclass` varchar(512) DEFAULT NULL COMMENT '操作类com.xx.oo',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_record`;
CREATE TABLE `sms_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(5) NOT NULL COMMENT '类型 1： 验证码类  2：通知类',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `vcode` varchar(20) NOT NULL COMMENT '验证码',
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '0 未使用（未验证） 1：已使用（已验证） 2：已过期',
  `temp_id` varchar(64) NOT NULL COMMENT '短信模板id',
  `temp_content` varchar(512) DEFAULT NULL COMMENT '短信模板内容',
  `provider` varchar(64) NOT NULL COMMENT '提供商',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用 时间',
  `expire_time` datetime NOT NULL COMMENT '有效期',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `ids_1` (`mobile`,`temp_id`),
  KEY `ids_2` (`status`),
  KEY `ids_3` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_temp
-- ----------------------------
DROP TABLE IF EXISTS `sms_temp`;
CREATE TABLE `sms_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `title` varchar(100) NOT NULL COMMENT '模板标题',
  `content` varchar(255) NOT NULL COMMENT '模板内容',
  `create_time` datetime NOT NULL,
  `upadate_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '模板状态 0：停用 1:启用 ',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
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

-- ----------------------------
-- Table structure for vip_grade
-- ----------------------------
DROP TABLE IF EXISTS `vip_grade`;
CREATE TABLE `vip_grade` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `vip` int(3) NOT NULL COMMENT 'vip等级',
  `name` varchar(64) NOT NULL COMMENT 'vip等级名',
  `info` varchar(256) DEFAULT NULL COMMENT 'vip描述',
  `icon` varchar(256) DEFAULT NULL COMMENT 'vip icon',
  `growth_value` bigint(20) NOT NULL DEFAULT '0' COMMENT '成长值量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`vid`),
  KEY `vip` (`vip`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vip_grade_task
-- ----------------------------
DROP TABLE IF EXISTS `vip_grade_task`;
CREATE TABLE `vip_grade_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip` int(11) NOT NULL COMMENT 'vip等级',
  `vtid` int(11) NOT NULL COMMENT 'vip等级任务id',
  `param` varchar(256) DEFAULT NULL COMMENT '参数',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip` (`vip`,`vtid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vip_task
-- ----------------------------
DROP TABLE IF EXISTS `vip_task`;
CREATE TABLE `vip_task` (
  `vtid` int(11) NOT NULL AUTO_INCREMENT COMMENT ' vip升级任务id',
  `name` varchar(64) NOT NULL COMMENT 'vip升级任务名',
  `info` varchar(500) DEFAULT NULL COMMENT 'vip升级任务描述',
  `clazz` varchar(256) NOT NULL COMMENT '可运行类名',
  `param_dec` varchar(512) NOT NULL COMMENT '参数说明',
  `status` int(5) NOT NULL DEFAULT '0' COMMENT '状态 0 无效 1 有效',
  `create_time` datetime NOT NULL,
  `remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`vtid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
