
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blob_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 504832 kB; (`SCHED_NAME` `TRIGGER_NAME` `TRIGGE' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 504832 kB; (`SCHED_NAME` `TRIGGER_NAME` `TRIGGE' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('MyScheduler', 'com.github.mustsd.modules.quartz.job.SimpleJob', 'DEFAULT', '3/2 * * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('MyScheduler', 'com.github.mustsd.modules.quartz.job.SimpleJob', 'DEFAULT', NULL, 'com.github.mustsd.modules.quartz.job.SimpleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000001740009706172616D65746572707800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('MyScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('MyScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE,
  INDEX `instance_key`(`instance_name`) USING BTREE COMMENT '加一个索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('MyScheduler', 'LAPTOP-JVPL2VMM1653744163877', 1653817358336, 10000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 504832 kB; (`SCHED_NAME` `TRIGGER_NAME` `TRIGGE' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `int_prop_1` int(11) NULL DEFAULT NULL,
  `int_prop_2` int(11) NULL DEFAULT NULL,
  `long_prop_1` bigint(20) NULL DEFAULT NULL,
  `long_prop_2` bigint(20) NULL DEFAULT NULL,
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL,
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 504832 kB; (`SCHED_NAME` `TRIGGER_NAME` `TRIGGE' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_fire_time` bigint(13) NULL DEFAULT NULL,
  `prev_fire_time` bigint(13) NULL DEFAULT NULL,
  `priority` int(11) NULL DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) NULL DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `misfire_instr` smallint(2) NULL DEFAULT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 504832 kB; (`SCHED_NAME` `JOB_NAME` `JOB_GROUP`' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('MyScheduler', 'com.github.mustsd.modules.quartz.job.SimpleJob', 'DEFAULT', 'com.github.mustsd.modules.quartz.job.SimpleJob', 'DEFAULT', NULL, 1648628171000, 1648628169000, 5, 'PAUSED', 'CRON', 1648628153000, 0, NULL, 0, '');

-- ----------------------------
-- Table structure for sys_article
-- ----------------------------
DROP TABLE IF EXISTS `sys_article`;
CREATE TABLE `sys_article`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章描述',
  `cover` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `render` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '渲染内容',
  `personal` tinyint(4) NULL DEFAULT 0 COMMENT '私有文章',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_article
-- ----------------------------
INSERT INTO `sys_article` VALUES ('431af36830e4ad830164c8405d32aa47', '465ff11f21da1de99a20258b2906ab87', '文档管理', NULL, NULL, '![default.jpg](http://dell.mustright.cn:9090/image/default.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=4bca24304861acde5770fdbe3cc2503b%2F20220402%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220402T094410Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=78c77194985fff4203be1f9107f996218942224c81e7dae34cf73165d9df6c28)', '<p><img src=\"http://dell.mustright.cn:9090/image/default.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&amp;X-Amz-Credential=4bca24304861acde5770fdbe3cc2503b%2F20220402%2Fus-east-1%2Fs3%2Faws4_request&amp;X-Amz-Date=20220402T094410Z&amp;X-Amz-Expires=604800&amp;X-Amz-SignedHeaders=host&amp;X-Amz-Signature=78c77194985fff4203be1f9107f996218942224c81e7dae34cf73165d9df6c28\" alt=\"default.jpg\" /></p>\n', 0, 'admin', NULL, 'admin', NULL);
INSERT INTO `sys_article` VALUES ('465ff11f21da1de99a20258b2906ab87', NULL, '使用说明', NULL, NULL, NULL, NULL, 0, 'admin', NULL, NULL, NULL);
INSERT INTO `sys_article` VALUES ('5c3dcf8c57659d750fb685191d09c2f5', '465ff11f21da1de99a20258b2906ab87', 'readme', '项目说明', NULL, '# Manage System\n## 1、项目采用前后端分离方式开发\n## 2、整体技术架构\n### 前端基于开源框架antd-pro-vue进行二次开发 \n### 后端采用springboot框架开发\n## 3、前端涉及的技术栈 \n#### 1、webpack\n#### 2、vue、vue-cli、vue-router、vuex\n#### 3、vue-socketIO、vue-socketIO-client\n#### 4、antd-vue....\n\n## 4、后端涉及技术栈\n#### 1、jdk8\n#### 2、springboot\n#### 3、shiro\n#### 4、netty-socket-IO\n#### 5、jwt\n#### 6、easy-xls\n#### 7、redis\n#### 8、mybatis、mybatis-plus\n#### 9、knife4j\n#### 10、quartz\n#### 11、beetl\n#### 12、minio\n[百度一下](https://www.baidu.com)\n\n', '<h1><a id=\"Manage_System_0\"></a>Manage System</h1>\n<h2><a id=\"1_1\"></a>1、项目采用前后端分离方式开发</h2>\n<h2><a id=\"2_2\"></a>2、整体技术架构</h2>\n<h3><a id=\"antdprovue_3\"></a>前端基于开源框架antd-pro-vue进行二次开发</h3>\n<h3><a id=\"springboot_4\"></a>后端采用springboot框架开发</h3>\n<h2><a id=\"3_5\"></a>3、前端涉及的技术栈</h2>\n<h4><a id=\"1webpack_6\"></a>1、webpack</h4>\n<h4><a id=\"2vuevueclivueroutervuex_7\"></a>2、vue、vue-cli、vue-router、vuex</h4>\n<h4><a id=\"3vuesocketIOvuesocketIOclient_8\"></a>3、vue-socketIO、vue-socketIO-client</h4>\n<h4><a id=\"4antdvue_9\"></a>4、antd-vue…</h4>\n<h2><a id=\"4_11\"></a>4、后端涉及技术栈</h2>\n<h4><a id=\"1jdk8_12\"></a>1、jdk8</h4>\n<h4><a id=\"2springboot_13\"></a>2、springboot</h4>\n<h4><a id=\"3shiro_14\"></a>3、shiro</h4>\n<h4><a id=\"4nettysocketIO_15\"></a>4、netty-socket-IO</h4>\n<h4><a id=\"5jwt_16\"></a>5、jwt</h4>\n<h4><a id=\"6easyxls_17\"></a>6、easy-xls</h4>\n<h4><a id=\"7redis_18\"></a>7、redis</h4>\n<h4><a id=\"8mybatismybatisplus_19\"></a>8、mybatis、mybatis-plus</h4>\n<h4><a id=\"9knife4j_20\"></a>9、knife4j</h4>\n<h4><a id=\"10quartz_21\"></a>10、quartz</h4>\n<h4><a id=\"11beetl_22\"></a>11、beetl</h4>\n<h4><a id=\"12minio_23\"></a>12、minio</h4>\n<p><a href=\"https://www.baidu.com\" target=\"_blank\">百度一下</a></p>\n', 0, 'dev', NULL, 'dev', NULL);
INSERT INTO `sys_article` VALUES ('ff863f4ac19cdb0120ea09db4ad7f0cf', '465ff11f21da1de99a20258b2906ab87', '文档查阅', NULL, NULL, '**测试文章** \n::: hljs-center\n\n![default.jpg](http://dell.mustright.cn:9090/image/default.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=4bca24304861acde5770fdbe3cc2503b%2F20220402%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220402T094336Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=dadd3614227610484534582b4812487686d68be9a88941a35e3c347235a3bc17)\n\n:::\n\n```java\n@PostMapping(value = \"/importExcel\")\n  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {\n    return super.importExcel(request, response, MUser.class);\n  }\n\n```\n![image.jpg](http://dell.mustright.cn:9090/image/image.jpg)', '<p><strong>测试文章</strong></p>\n<div class=\"hljs-center\">\n<p><img src=\"http://dell.mustright.cn:9090/image/default.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&amp;X-Amz-Credential=4bca24304861acde5770fdbe3cc2503b%2F20220402%2Fus-east-1%2Fs3%2Faws4_request&amp;X-Amz-Date=20220402T094336Z&amp;X-Amz-Expires=604800&amp;X-Amz-SignedHeaders=host&amp;X-Amz-Signature=dadd3614227610484534582b4812487686d68be9a88941a35e3c347235a3bc17\" alt=\"default.jpg\" /></p>\n</div>\n<pre><div class=\"hljs\"><code class=\"lang-java\"><span class=\"hljs-meta\">@PostMapping(value = &quot;/importExcel&quot;)</span>\n  <span class=\"hljs-keyword\">public</span> Result&lt;?&gt; importExcel(HttpServletRequest request, HttpServletResponse response) {\n    <span class=\"hljs-keyword\">return</span> <span class=\"hljs-built_in\">super</span>.importExcel(request, response, MUser.class);\n  }\n\n</code></div></pre>\n<p><img src=\"http://dell.mustright.cn:9090/image/image.jpg\" alt=\"image.jpg\" /></p>\n', 0, 'admin', NULL, 'dev', NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('3939e02fff07a5e6849cb736a74c6d02', '性别', 'gender', '用户性别', 'yangzhen', '2022-03-03 16:00:00', 'dev', '2022-04-19 16:00:00');
INSERT INTO `sys_dict` VALUES ('44737f9039ee2bdf06260ff7c26d9339', '按钮权限行为', 'btnAction', '按钮权限是显示与否还是禁用启用', 'yangzhen', '2022-03-04 16:12:10', NULL, NULL);
INSERT INTO `sys_dict` VALUES ('81dd74fab329859d1f9e28dfb5f95f4a', '通知类型', 'noticeType', '系统通知类型', 'dev', '2022-05-24 11:32:34', NULL, NULL);
INSERT INTO `sys_dict` VALUES ('a7bb7bca28683e0ae52bd70490e1b44a', '操作日志失败', 'operationFailed', '用户操作成功与否', 'yangzhen', '2022-03-03 21:09:33', NULL, NULL);
INSERT INTO `sys_dict` VALUES ('afa0b0873b46b5a593b585f80265d1f5', '系统通知状态', 'noticeStatus', '系统通知状态', 'dev', '2022-05-24 11:39:48', 'dev', '2022-05-24 11:40:47');
INSERT INTO `sys_dict` VALUES ('e15ad207c9c18e290246cb43ff791441', '是否', 'yn', '是、否字典', 'yangzhen', '2022-03-09 12:31:57', NULL, NULL);
INSERT INTO `sys_dict` VALUES ('e3b6135faf641cf272a5eac8deb0e531', '文件类型', 'bucket', '附件、图片', 'admin', '2022-03-23 16:00:00', 'admin', '2022-03-23 18:34:05');
INSERT INTO `sys_dict` VALUES ('eab656e1b2d66ead7258f595c03c74f2', '定时任务状态', 'jobStatus', '定时任务启停状态标识', 'admin', '2022-03-25 16:00:00', 'admin', '2022-04-02 23:54:31');
INSERT INTO `sys_dict` VALUES ('eb07a420fc25c4fbbbf11d01c1c5311a', '菜单类型', 'menuType', '描述菜单是功能页面或者按钮权限', 'yangzhen', '2022-03-02 16:00:00', 'yangzhen', '2022-03-02 16:00:00');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `dict_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典id',
  `item_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `item_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典明细表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('129fe9a3f8dc9858932d896c792ef316', '81dd74fab329859d1f9e28dfb5f95f4a', '1', '系统公告', '系统公告', 'dev', '2022-05-24 11:33:05', 'dev', '2022-05-24 11:34:00');
INSERT INTO `sys_dict_item` VALUES ('133999225b444f1229880df9d67bb3ee', 'eb07a420fc25c4fbbbf11d01c1c5311a', '1', '系统菜单', '', 'yangzhen', '2022-03-03 16:00:00', 'yangzhen', '2022-03-03 20:16:55');
INSERT INTO `sys_dict_item` VALUES ('13e73c2adc35c735cc4c2e07407821cf', 'a7bb7bca28683e0ae52bd70490e1b44a', '0', '操作成功', NULL, 'yangzhen', '2022-03-03 21:10:10', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('1cb5a4ddfcb344f73928ac30299d6dab', 'afa0b0873b46b5a593b585f80265d1f5', '1', '待发布', NULL, 'dev', '2022-05-24 11:40:04', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('2de41ea3a53f955ade917e411983ca9e', 'eb07a420fc25c4fbbbf11d01c1c5311a', '2', '按钮权限', '', 'yangzhen', '2022-03-03 16:00:00', 'yangzhen', '2022-03-03 20:17:12');
INSERT INTO `sys_dict_item` VALUES ('3226a0da84a0efa67bbef349a061f465', 'eab656e1b2d66ead7258f595c03c74f2', '1', '运行', NULL, 'admin', '2022-03-25 16:00:00', 'admin', '2022-03-25 22:36:04');
INSERT INTO `sys_dict_item` VALUES ('35785eeaa4234f3c2282494dc18080a0', 'e3b6135faf641cf272a5eac8deb0e531', 'image', '图片', NULL, 'admin', '2022-03-23 18:29:21', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('3f27b84271be1148edb027310ca64ce2', 'a7bb7bca28683e0ae52bd70490e1b44a', '1', '操作失败', NULL, 'yangzhen', '2022-03-03 21:10:00', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('40efbc1ea8c73fe1cf146a51638aaba1', 'e15ad207c9c18e290246cb43ff791441', '1', '是', NULL, 'yangzhen', '2022-03-09 12:32:08', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('4ff5274575368c993b90c7f97d35c0e6', 'afa0b0873b46b5a593b585f80265d1f5', '2', '已发布', NULL, 'dev', '2022-05-24 11:40:26', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('5076f6352ac6b6f0683a532d2f433571', '44737f9039ee2bdf06260ff7c26d9339', '2', '隐藏', '不显示无权限按钮', 'yangzhen', '2022-03-04 16:00:00', 'yangzhen', '2022-03-09 14:36:38');
INSERT INTO `sys_dict_item` VALUES ('5c602edfb8cc7e9e4c31e7de382a3326', '3939e02fff07a5e6849cb736a74c6d02', '1', '男', NULL, 'yangzhen', '2022-03-04 15:55:48', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('6cfd98fb49df244dd4eecfaded4742d6', '81dd74fab329859d1f9e28dfb5f95f4a', '2', '系统消息', '系统内用户消息', 'dev', '2022-05-24 11:33:27', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('9699e1511892e3280693c8c0149cc22d', 'afa0b0873b46b5a593b585f80265d1f5', '3', '已撤销', NULL, 'dev', '2022-05-24 11:40:34', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('a7986c1ad0e1a7b8dcfd99c2d760a9aa', 'e15ad207c9c18e290246cb43ff791441', '0', '否', NULL, 'yangzhen', '2022-03-09 12:32:14', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('bfa7eb0115a1d830e4a06442b17855ac', '3939e02fff07a5e6849cb736a74c6d02', '2', '女', NULL, 'yangzhen', '2022-03-04 15:55:54', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('c86d53d8556b34fcedfc5db27071287f', '3939e02fff07a5e6849cb736a74c6d02', '3', '未知', NULL, 'dev', '2022-04-20 15:29:30', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('d29ae35c8f9167dc27db4e2e1a486d2e', '44737f9039ee2bdf06260ff7c26d9339', '1', '禁用', '禁用无权限按钮', 'yangzhen', '2022-03-04 16:00:00', 'yangzhen', '2022-03-09 14:36:42');
INSERT INTO `sys_dict_item` VALUES ('db04dc63e73a5f8bcce23090574c57c5', 'eab656e1b2d66ead7258f595c03c74f2', '2', '停止', NULL, 'admin', '2022-03-25 17:07:39', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES ('e2dbecd67b927446b89f63beb9ed7aff', 'e3b6135faf641cf272a5eac8deb0e531', 'attachment', '附件', NULL, 'admin', '2022-03-23 18:29:11', NULL, NULL);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `bucket` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
  `file_size` decimal(10, 2) NULL DEFAULT NULL COMMENT '文件大小(MB)',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES ('16a33ecce798033e9a2f20444d3c4251', 'image.jpg', 'image', 'http://dell.mustright.cn:9090/image/image.jpg', 0.01, 'dev', '2022-05-27 09:34:12', NULL, NULL);
INSERT INTO `sys_file` VALUES ('24221dfe1a7d3e5cafc27168144a0a66', 'image.jpg', 'image', 'http://dell.mustright.cn:9090/image/image.jpg', 0.01, 'dev', '2022-05-27 09:24:43', NULL, NULL);
INSERT INTO `sys_file` VALUES ('35538f974766e448106c03da9fc2293a', 'image.jpg', 'attachment', 'http://dell.mustright.cn:9090/attachment/image.jpg', 0.01, 'dev', '2022-05-01 21:57:40', NULL, NULL);
INSERT INTO `sys_file` VALUES ('400618134ae097272bbf5c39f6dd17b5', 'image.jpg', 'image', 'http://dell.mustright.cn:9090/image/image.jpg', 0.01, 'dev', '2022-05-27 09:23:56', NULL, NULL);
INSERT INTO `sys_file` VALUES ('52de8be88e4f01850d65174ad7bfb284', '微信图片_20220528104231.png', 'image', 'http://dell.mustright.cn:9090/image/微信图片_20220528104231.png', 0.37, 'dev', '2022-05-28 16:39:27', NULL, NULL);
INSERT INTO `sys_file` VALUES ('a096d532c4c82aa114694cbfadbcb6c2', 'image.jpg', 'image', 'http://dell.mustright.cn:9090/image/image.jpg', 0.01, 'dev', '2022-05-01 22:12:42', NULL, NULL);
INSERT INTO `sys_file` VALUES ('c54deefdc68968256d815d0f651f45e2', 'image.jpg', 'image', 'http://dell.mustright.cn:9090/image/image.jpg', 0.01, 'dev', '2022-05-27 15:01:40', NULL, NULL);
INSERT INTO `sys_file` VALUES ('e75352899196c5baa3ce819814598a23', '微信图片_20220528104231.png', 'image', 'http://dell.mustright.cn:9090/image/微信图片_20220528104231.png', 0.37, 'dev', '2022-05-28 18:01:31', NULL, NULL);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `operation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作',
  `method` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用方法',
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `cost` bigint(20) NULL DEFAULT NULL COMMENT '操作耗时',
  `failure` tinyint(1) NULL DEFAULT 0 COMMENT '操作失败',
  `error` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '失败原因',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '操作 1、登录 2、登出',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('003c980ab37048090b7ecb6cd78d594a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-23 21:41:13');
INSERT INTO `sys_login_log` VALUES ('00eb4abfe9c190e4eaf16095b2636e9f', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-28 22:44:45');
INSERT INTO `sys_login_log` VALUES ('0116409c993f58bf5c61870c5db82ffa', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 09:10:17');
INSERT INTO `sys_login_log` VALUES ('0682ff08bc0c63e32dc9f9a22b7760fa', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 12:52:50');
INSERT INTO `sys_login_log` VALUES ('070387b84f429f0dfc4d83ee84f52544', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-27 09:08:29');
INSERT INTO `sys_login_log` VALUES ('0a22133d290d642b3994c033fe510e9e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 21:31:27');
INSERT INTO `sys_login_log` VALUES ('0f3cb344c202bb8e62ef50fb4d08435b', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 12:53:46');
INSERT INTO `sys_login_log` VALUES ('0f6b98774b1929da8abbe9bbd44bd05e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 21:20:15');
INSERT INTO `sys_login_log` VALUES ('1286a2a095b4a75c58f68b98d3ee9e22', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-26 17:00:26');
INSERT INTO `sys_login_log` VALUES ('1509c9321007c2e9db05e386b790bd12', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 12:59:54');
INSERT INTO `sys_login_log` VALUES ('16a2089b97883413e4d1bb881dc67ff7', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 2, 'yangzhen', '2022-05-27 09:08:20');
INSERT INTO `sys_login_log` VALUES ('16e5d25e40b43e85a97ad7e7cbe594c6', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-26 11:16:57');
INSERT INTO `sys_login_log` VALUES ('17e925d21930d19a944ddc5063e03ac2', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 13:00:11');
INSERT INTO `sys_login_log` VALUES ('1ac97d77fe68716fa81d37f3538bb757', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 12:55:28');
INSERT INTO `sys_login_log` VALUES ('1ce996730d8e8102f9838390a1e5a335', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-22 21:47:37');
INSERT INTO `sys_login_log` VALUES ('1f16f7ab690df845493e3106d850e5ee', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 12:35:27');
INSERT INTO `sys_login_log` VALUES ('1ffbc514300f3beb7919ed6601f5cb00', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-28 18:01:52');
INSERT INTO `sys_login_log` VALUES ('2108623de549d73f9bfe64375166e853', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-26 13:31:23');
INSERT INTO `sys_login_log` VALUES ('23442f3005066908ac15f80b6e9d8170', '5b43587df91c1aab40f351460ca17c8c', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 11:17:08');
INSERT INTO `sys_login_log` VALUES ('24513fdea8f0094660ec40a737276ce2', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-29 17:34:02');
INSERT INTO `sys_login_log` VALUES ('24564ffe0c1b68d487bc530cae3dcd60', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 09:36:19');
INSERT INTO `sys_login_log` VALUES ('2564f3f4a8e894d09033570f9c1196ef', '5b43587df91c1aab40f351460ca17c8c', '0:0:0:0:0:0:0:1', 2, 'admin', '2022-05-28 14:39:10');
INSERT INTO `sys_login_log` VALUES ('26e778e89e83237db348c4155f92418d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 14:21:32');
INSERT INTO `sys_login_log` VALUES ('2aa49ce2a6b7ab6343358c9cc1f010a2', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-21 21:36:06');
INSERT INTO `sys_login_log` VALUES ('2ddc816e3c1e424dd71005c50a4b0b0e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 08:54:11');
INSERT INTO `sys_login_log` VALUES ('2e2bf7d872984f626754b9dc42745fa7', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-21 22:18:11');
INSERT INTO `sys_login_log` VALUES ('2e8bf887012c36bfe852f876700e609f', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-29 17:33:52');
INSERT INTO `sys_login_log` VALUES ('2ed0251fd472c81fec18a0fd5903e438', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-22 22:11:20');
INSERT INTO `sys_login_log` VALUES ('30790ecc9528a229bd02a385de496bf9', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 21:02:24');
INSERT INTO `sys_login_log` VALUES ('3165f321bcac39cd4bbacb8bedc68a29', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-01 22:03:45');
INSERT INTO `sys_login_log` VALUES ('34ba0446998a0da1f72206f749b75fed', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 08:27:18');
INSERT INTO `sys_login_log` VALUES ('3652f4cffbdf1487047e427c3880bbd4', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-21 22:16:22');
INSERT INTO `sys_login_log` VALUES ('3700eba92f3361b2e41b3fa8d301019e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-27 14:44:12');
INSERT INTO `sys_login_log` VALUES ('3704e527ab4803ad2fd3eca8a2f96e9d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-30 19:30:00');
INSERT INTO `sys_login_log` VALUES ('376d70141b42629b3734e4b794940312', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 22:51:15');
INSERT INTO `sys_login_log` VALUES ('3842afb0d00038265412dd90322b7011', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-20 17:42:35');
INSERT INTO `sys_login_log` VALUES ('39a0ee430abc843ebe9802e86ece91d8', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-23 17:06:24');
INSERT INTO `sys_login_log` VALUES ('3e4ef3db0819da33e0ecd9f0f36af738', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-23 00:16:01');
INSERT INTO `sys_login_log` VALUES ('4030c3960db7693265c7305067e1301c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-28 18:40:34');
INSERT INTO `sys_login_log` VALUES ('40fec87e20769d74b70f51242eaaf03a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-10 17:09:18');
INSERT INTO `sys_login_log` VALUES ('410fccd5a11b17737b2c29bf6f8f9593', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-25 18:45:59');
INSERT INTO `sys_login_log` VALUES ('4137b1ea4239489e078c0cf00ab9713e', '007be7be4ddd6f09e51c4c46fc39d634', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 22:18:52');
INSERT INTO `sys_login_log` VALUES ('420f99275ec93f3fe8cbf24f897a235d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-26 11:44:04');
INSERT INTO `sys_login_log` VALUES ('45742858ecde8139c78521a44c06c2e4', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-25 09:20:37');
INSERT INTO `sys_login_log` VALUES ('45bfd898044cf51ae7182699340b6398', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-22 21:56:52');
INSERT INTO `sys_login_log` VALUES ('4686a6374ea3af85925613dbdf2d80bc', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 16:58:01');
INSERT INTO `sys_login_log` VALUES ('4cd73dc0556f60f892e15479fe23cd2e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-23 16:50:54');
INSERT INTO `sys_login_log` VALUES ('4cfdf79793a264fb64f909da7e75f01a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-24 08:55:10');
INSERT INTO `sys_login_log` VALUES ('4ef59ebb481b634a62acc58e398e7621', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 2, 'yangzhen', '2022-05-28 21:20:04');
INSERT INTO `sys_login_log` VALUES ('4f59bfd0964ec1ff729b911e0a950d13', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 21:46:14');
INSERT INTO `sys_login_log` VALUES ('4fefee4022068f292383432a9a80159a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-26 16:58:04');
INSERT INTO `sys_login_log` VALUES ('506ec87f0af5111e2a253fbc41cb660a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-22 22:15:19');
INSERT INTO `sys_login_log` VALUES ('527f62f61c809fab8a4275b34dcbb73c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 14:14:38');
INSERT INTO `sys_login_log` VALUES ('5469bdc6067a685af69712de6c042547', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 10:19:21');
INSERT INTO `sys_login_log` VALUES ('5882f5220b5bda9528e35340cba789cc', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 12:56:48');
INSERT INTO `sys_login_log` VALUES ('5a18b3d34931c3e13da3694671b5b599', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 22:12:10');
INSERT INTO `sys_login_log` VALUES ('5a89661eb64308d95c6180c702eb4f26', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 2, 'yangzhen', '2022-05-26 17:00:15');
INSERT INTO `sys_login_log` VALUES ('5a92076ff85f838a84e4336b27af3184', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-28 17:59:43');
INSERT INTO `sys_login_log` VALUES ('5b6c617cb45b4887de7bab275aaec776', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 12:53:39');
INSERT INTO `sys_login_log` VALUES ('5e40057c04596d8c7b8248e43b62b701', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 11:58:01');
INSERT INTO `sys_login_log` VALUES ('5f6eff9bbb19f6d9d8ecb21275fd0e18', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 14:38:56');
INSERT INTO `sys_login_log` VALUES ('5fe914f15a54bbe749b32368073c102a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-01 21:59:09');
INSERT INTO `sys_login_log` VALUES ('60866217e8b8a606dfc068f52a5058fb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-03 17:15:04');
INSERT INTO `sys_login_log` VALUES ('634e84249fc84bf363233c6d354ac4a6', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-26 09:06:23');
INSERT INTO `sys_login_log` VALUES ('63c9b7e2d0c766bfb025c75c0d55fd65', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 16:45:12');
INSERT INTO `sys_login_log` VALUES ('64fedbb9c322d72d1f4f913b482200bc', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 14:39:04');
INSERT INTO `sys_login_log` VALUES ('666fbffdeaec2bf0f28edab18a69243e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-22 22:14:03');
INSERT INTO `sys_login_log` VALUES ('679573cef9d75ed84c62a27434f7d954', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-26 16:58:20');
INSERT INTO `sys_login_log` VALUES ('6afa4beb02c807195f9a7c0d02e51301', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 11:29:28');
INSERT INTO `sys_login_log` VALUES ('6bb441886d1b01dc3680c9d77e91fed1', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 09:03:21');
INSERT INTO `sys_login_log` VALUES ('6c87a33879074376368295c58f6a0b8a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-25 11:57:35');
INSERT INTO `sys_login_log` VALUES ('6d3864f7dcece9a3ced65f8713a94740', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 17:06:19');
INSERT INTO `sys_login_log` VALUES ('6d84852d4fd491398476ca9830e367bb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 19:37:24');
INSERT INTO `sys_login_log` VALUES ('6e3c774c886012741894d878ef5ece41', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 2, 'yangzhen', '2022-05-23 17:06:09');
INSERT INTO `sys_login_log` VALUES ('6f684a3697f42931e9b731dee1972977', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 11:19:35');
INSERT INTO `sys_login_log` VALUES ('71c25b230e48f9ddb444d0565af08947', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 09:59:34');
INSERT INTO `sys_login_log` VALUES ('7299f862acdab6f40fb0bce45a99a3c5', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-05 16:54:27');
INSERT INTO `sys_login_log` VALUES ('72afa50e8ea897f9900b4ad1969760eb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 21:36:17');
INSERT INTO `sys_login_log` VALUES ('730aa0e8993b4e44ad52b64f3ef5df71', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-26 11:03:01');
INSERT INTO `sys_login_log` VALUES ('742067320fd6d224503861efb7c5b9b5', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-22 16:29:17');
INSERT INTO `sys_login_log` VALUES ('75818501eae6495950fa52f85258080a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 14:39:13');
INSERT INTO `sys_login_log` VALUES ('7a3695950b9daea3ef8e1b74076b6902', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 19:39:44');
INSERT INTO `sys_login_log` VALUES ('7a975b61847c394f1ed69a218a2648a1', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-21 10:18:09');
INSERT INTO `sys_login_log` VALUES ('7d4c8fba2770c7e4115ca181e512a420', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 20:59:45');
INSERT INTO `sys_login_log` VALUES ('7d84749107c9c4ac4b301318e206bcef', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 09:16:58');
INSERT INTO `sys_login_log` VALUES ('80af0225b5eafb43a700b459b7e9a7f7', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-26 18:16:10');
INSERT INTO `sys_login_log` VALUES ('85e905b909373b3bfa25596870ceefdc', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 14:33:01');
INSERT INTO `sys_login_log` VALUES ('86be11c8d87f66c9f05744663a8e83c0', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 11:52:18');
INSERT INTO `sys_login_log` VALUES ('88e838ac6196ab46053e126961ee9d78', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 17:20:12');
INSERT INTO `sys_login_log` VALUES ('8be8fce950ffceba5ed78523000ba5be', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 11:17:35');
INSERT INTO `sys_login_log` VALUES ('8c380b256c41bb6ba4cf0ef8e14be971', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 21:23:21');
INSERT INTO `sys_login_log` VALUES ('8ddfec003c61f531aea5571e3f84df05', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 16:54:21');
INSERT INTO `sys_login_log` VALUES ('8e06fe2ff0899ce805da6ccea70de64c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 12:55:10');
INSERT INTO `sys_login_log` VALUES ('8e8eff2e853d0629de984f750ec37d30', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 2, 'demo', '2022-04-21 22:16:50');
INSERT INTO `sys_login_log` VALUES ('8eb637578fa4b867c8003a7c5610622d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 09:07:51');
INSERT INTO `sys_login_log` VALUES ('91dc710782a629f059cee1de300d1718', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-27 07:54:35');
INSERT INTO `sys_login_log` VALUES ('91e5da9ebf01841107195c408d0537cb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-24 18:06:11');
INSERT INTO `sys_login_log` VALUES ('922c8aff0c82aa7302685eb4ee79221c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 07:45:59');
INSERT INTO `sys_login_log` VALUES ('9766f309fab5cbd57d6ace4063b060cb', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 22:16:33');
INSERT INTO `sys_login_log` VALUES ('991134d5697704fbc98c73cb8c9da3cd', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-12 10:05:58');
INSERT INTO `sys_login_log` VALUES ('99b03d8ec74502b6a9e511d4fbda0cd9', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 18:00:06');
INSERT INTO `sys_login_log` VALUES ('9a6f50e8b0dcf12b469189f20be591b8', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-23 00:16:21');
INSERT INTO `sys_login_log` VALUES ('a073139b60b7638ca164e5fc02456eea', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 21:26:43');
INSERT INTO `sys_login_log` VALUES ('a1dc4bb1de64f0b1cc735e0a5e7255a0', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 16:54:55');
INSERT INTO `sys_login_log` VALUES ('a29dce80be4bc0de78a6a92cb9950f69', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-29 17:32:13');
INSERT INTO `sys_login_log` VALUES ('a469ca6b54db368a12ff7e455d1514ee', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-12 15:55:20');
INSERT INTO `sys_login_log` VALUES ('a4c8f7d858fe0f9f8df28856b9b66413', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-25 11:13:12');
INSERT INTO `sys_login_log` VALUES ('a6eb6e399d03a62cf676ab1123843c0a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 22:54:54');
INSERT INTO `sys_login_log` VALUES ('a8abb2417f505436fcfddc697a8c8988', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 2, 'yangzhen', '2022-05-23 16:54:09');
INSERT INTO `sys_login_log` VALUES ('acb090d04a0295ad23b5b1dd7131aae4', '007be7be4ddd6f09e51c4c46fc39d634', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 22:18:25');
INSERT INTO `sys_login_log` VALUES ('b01ac391c99ed8a9657447b35a7abdc8', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 21:24:44');
INSERT INTO `sys_login_log` VALUES ('b3f44a58123d7e66931a9dfb96a57b22', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-01 21:59:19');
INSERT INTO `sys_login_log` VALUES ('b47eff468b462c7108e1c3cd5c203fdc', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-01 18:05:07');
INSERT INTO `sys_login_log` VALUES ('b4d39a94a934d6251d6f4dccbbf8fc3a', '007be7be4ddd6f09e51c4c46fc39d634', '0:0:0:0:0:0:0:1', 2, 'test', '2022-04-22 16:29:07');
INSERT INTO `sys_login_log` VALUES ('b721d63f87694fa356fafbdd457d4cf6', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-21 19:13:17');
INSERT INTO `sys_login_log` VALUES ('b7c725cf6f98d9fc75653dd2d8c7a762', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-05 18:14:20');
INSERT INTO `sys_login_log` VALUES ('bc613f9ac0f158481f7d2062ead6586c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 14:49:41');
INSERT INTO `sys_login_log` VALUES ('bded53f992ae60ce26e9aab2161aa7a6', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-23 16:53:48');
INSERT INTO `sys_login_log` VALUES ('be3fb67bec1da36bf30bb4ba5cc6ca87', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 11:22:46');
INSERT INTO `sys_login_log` VALUES ('bf5626fc46909408bd10dd3ac6b8536b', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 22:57:33');
INSERT INTO `sys_login_log` VALUES ('c035d3346ba7980fc91523aaaa9d4f14', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 14:15:04');
INSERT INTO `sys_login_log` VALUES ('c69e18792a4816a3d38ac4bd61c80e2f', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-20 20:54:48');
INSERT INTO `sys_login_log` VALUES ('c70052df180c9a05ebe76e2f37556a0d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-29 08:33:43');
INSERT INTO `sys_login_log` VALUES ('c72a09691213d1fd7d6d1c2c2629c5db', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-28 22:26:06');
INSERT INTO `sys_login_log` VALUES ('c7e1398525a9d087ae4a6dbcc4cc041c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 21:31:16');
INSERT INTO `sys_login_log` VALUES ('c957e5a1ab8e344e9a88edead936bb59', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 14:21:37');
INSERT INTO `sys_login_log` VALUES ('cadc1ee0ede654fd218429511a9b5291', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 2, 'demo', '2022-05-29 17:32:46');
INSERT INTO `sys_login_log` VALUES ('cbb28137cd9784976c30764d9498984e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 09:09:13');
INSERT INTO `sys_login_log` VALUES ('cc397f603a838c65f909ab7661d0d277', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 14:39:22');
INSERT INTO `sys_login_log` VALUES ('cdee9ac11affa057217df769d280b663', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 09:50:42');
INSERT INTO `sys_login_log` VALUES ('d02a118b3169be63512e74c6b252c252', '5b43587df91c1aab40f351460ca17c8c', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-28 07:55:02');
INSERT INTO `sys_login_log` VALUES ('d170c1198d52055b6eaead21c45d695b', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 08:48:58');
INSERT INTO `sys_login_log` VALUES ('d1b6a664c8f277268326a027a5b812be', 'd390364cab808f0e52ca6345b0b86e4f', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-27 08:33:13');
INSERT INTO `sys_login_log` VALUES ('d2b241c4664c5f64dcc017954c12691d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-27 18:36:39');
INSERT INTO `sys_login_log` VALUES ('d3f11967225ec8c2cf3152bef6010681', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 14:51:20');
INSERT INTO `sys_login_log` VALUES ('d41fdfa39de0ecb8988fcf41feea2d40', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 13:00:35');
INSERT INTO `sys_login_log` VALUES ('d5b9a6ac224f946f270debd9e351123f', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 11:02:01');
INSERT INTO `sys_login_log` VALUES ('d778546df4fe7536e0f42ada7591d41e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-29 15:17:19');
INSERT INTO `sys_login_log` VALUES ('d91f01eecb9047790283737b28d8385c', '007be7be4ddd6f09e51c4c46fc39d634', '0:0:0:0:0:0:0:1', 2, 'test', '2022-04-21 22:18:38');
INSERT INTO `sys_login_log` VALUES ('d95b00cf1124e7b5fab9d3656bb1c6be', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-25 11:38:49');
INSERT INTO `sys_login_log` VALUES ('db9f526fc188b2f1438862d68e2ffcdb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-01 22:03:35');
INSERT INTO `sys_login_log` VALUES ('deb6a6fe52abddc22506ebf0055842e8', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 08:34:04');
INSERT INTO `sys_login_log` VALUES ('e0242689c80bc87d4af069981b11342a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-27 20:01:50');
INSERT INTO `sys_login_log` VALUES ('e0337a3aa55464f4ebab4891515afb69', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-22 21:47:47');
INSERT INTO `sys_login_log` VALUES ('e2ecb4dc83c8b69a35a12a5b2318524c', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-27 18:42:32');
INSERT INTO `sys_login_log` VALUES ('e69e8ae13234017832ebeb13c8bd5606', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-23 16:55:04');
INSERT INTO `sys_login_log` VALUES ('e8a370cce05ba05af27ef0e82d7f0091', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-26 21:30:38');
INSERT INTO `sys_login_log` VALUES ('e8e2417bcc637ed9b3cd51a29ebd70ae', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-23 16:54:45');
INSERT INTO `sys_login_log` VALUES ('e8f04289fd48e1f161e5e1944d254628', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 22:16:58');
INSERT INTO `sys_login_log` VALUES ('ea519f2dc270108ac8db0b1acca3fdfe', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-27 18:37:28');
INSERT INTO `sys_login_log` VALUES ('ebee666dede06bceb8b84bdc8b2fd0f1', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-29 17:32:56');
INSERT INTO `sys_login_log` VALUES ('ee22b0d82276290d6d5ab72a2915251f', '5b43587df91c1aab40f351460ca17c8c', '0:0:0:0:0:0:0:1', 2, 'admin', '2022-04-26 11:17:27');
INSERT INTO `sys_login_log` VALUES ('ee9d1e4b193cf69aa37e1fc57e0adf37', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-28 07:54:50');
INSERT INTO `sys_login_log` VALUES ('f0741469f1db25ee6faca881ffab05eb', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-26 18:42:53');
INSERT INTO `sys_login_log` VALUES ('f0e8a49841a1ea90db8df0433c97a157', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-10 17:05:36');
INSERT INTO `sys_login_log` VALUES ('f127dd5964fb357c9d86d4094d537c41', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-26 19:37:53');
INSERT INTO `sys_login_log` VALUES ('f20a8017bab46a26ba796ecc3e2d7172', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-27 15:07:57');
INSERT INTO `sys_login_log` VALUES ('f2f43cbe07a313c1df257f8de6ae6eb3', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 12:58:47');
INSERT INTO `sys_login_log` VALUES ('f3a8a14b3462bafd03574d0ff486883e', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-25 11:20:45');
INSERT INTO `sys_login_log` VALUES ('f475a23583ad8c48057a5da7c6c5724d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-24 11:48:09');
INSERT INTO `sys_login_log` VALUES ('f49f0ba81ddbcd59cc48e296920c413a', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-27 15:01:11');
INSERT INTO `sys_login_log` VALUES ('f54e257ebd19b0a802520a6cd645b302', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-05-22 12:43:09');
INSERT INTO `sys_login_log` VALUES ('f7dbb429aa7153a8341a647e0e495182', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-25 11:35:33');
INSERT INTO `sys_login_log` VALUES ('f834302f9e8454215bdf3552323f6fe4', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 2, 'demo', '2022-04-21 22:12:01');
INSERT INTO `sys_login_log` VALUES ('f85d9121762d012ffcac6a489b32b07b', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-05-28 19:39:34');
INSERT INTO `sys_login_log` VALUES ('f893bb3bcda8bc0be8cdce7b94f94aab', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 09:50:53');
INSERT INTO `sys_login_log` VALUES ('f956431126bb0d4cd85d52db6e098482', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-29 08:50:49');
INSERT INTO `sys_login_log` VALUES ('fcc6190dd4dce4825f6b9c26f7802b72', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-21 09:02:04');
INSERT INTO `sys_login_log` VALUES ('fd43bb2e276f34fb01c8759e4a1764a3', '938a6fa68122553d83598611ea1c70bf', '0:0:0:0:0:0:0:1', 2, 'demo', '2022-05-29 17:42:34');
INSERT INTO `sys_login_log` VALUES ('fd90f1cd297182de8d85ce43663d1ef3', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 1, 'default', '2022-04-24 21:05:06');
INSERT INTO `sys_login_log` VALUES ('fde6a0e1a935fb8ce9ef02282d3f1a5d', '589a97faa745390b7888e491fcc754f1', '0:0:0:0:0:0:0:1', 2, 'dev', '2022-04-20 17:20:01');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '类型',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `send_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `cancel_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '撤销人',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '撤销时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('07f3bb565c82e398bc73acc2f02c50e5', 'adasdad', NULL, 2, '<p>asdasd</p>', 'dev', '2022-05-27 06:43:43', NULL, NULL, 'dev', '2022-05-27 14:43:40', NULL, '2022-05-27 14:43:43');
INSERT INTO `sys_notice` VALUES ('1b5aff435760d3b26e9bff8c14e8e921', '测试01', NULL, 2, '<pre><code class=\"Bash\"> watch: {\n    value(<span class=\"hljs-keyword\">val</span>) {\n      console.log(<span class=\"hljs-string\">\'watch =\'</span>, <span class=\"hljs-keyword\">val</span>)\n      <span class=\"hljs-keyword\">if</span> (<span class=\"hljs-keyword\">val</span> != undefined &amp;&amp; <span class=\"hljs-keyword\">val</span> != <span class=\"hljs-keyword\">this</span>.editorContent) {\n        console.log(<span class=\"hljs-string\">\'赋值 =\'</span>, <span class=\"hljs-keyword\">val</span>)\n        <span class=\"hljs-keyword\">this</span>.editor.txt.html(<span class=\"hljs-keyword\">val</span>)\n      }\n    },\n  },</code></pre>', 'dev', '2022-05-28 11:28:10', NULL, NULL, 'dev', '2022-05-28 16:00:00', 'dev', '2022-05-28 19:28:10');
INSERT INTO `sys_notice` VALUES ('1fed4844f67163817914c505acf84ff6', '测试一下', NULL, 2, '<h3 style=\"text-align: center;\"><span style=\"font-weight: bold;\">下面是图片看好了</span></h3><div style=\"text-align: center;\"><img src=\"http://dell.mustright.cn:9090/image/image.jpg\" style=\"max-width:100%;\"><span style=\"font-weight: bold;\"><br></span></div><p style=\"text-align: center;\"><span style=\"color: rgb(77, 128, 191);\">牛逼吧</span><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\" style=\"text-align: left;\"></p>', 'dev', '2022-05-27 01:34:45', NULL, NULL, 'dev', '2022-05-27 09:34:42', NULL, '2022-05-27 09:34:44');
INSERT INTO `sys_notice` VALUES ('285bb6620abc16b6b7c60fe1c453b16c', '测试01', NULL, 2, '<p><br/></p><p>下一下啊啊啊</p><p><br/></p><pre><code class=\"JavaScript\">  value(<span class=\"hljs-keyword\">val</span>) {\n      <span class=\"hljs-keyword\">if</span> (!<span class=\"hljs-keyword\">this</span>.isChange) {\n        <span class=\"hljs-keyword\">if</span> (<span class=\"hljs-keyword\">val</span>) {\n          <span class=\"hljs-keyword\">this</span>.editor.txt.html(<span class=\"hljs-keyword\">val</span>)\n        } <span class=\"hljs-keyword\">else</span> {\n          <span class=\"hljs-keyword\">this</span>.editor.txt.clear()\n        }\n      }\n      <span class=\"hljs-keyword\">this</span>.isChange = <span class=\"hljs-literal\">false</span>\n    }</code></pre><p><br/></p>', 'dev', '2022-05-28 10:01:56', NULL, NULL, 'dev', '2022-05-28 16:00:00', 'dev', '2022-05-28 18:01:56');
INSERT INTO `sys_notice` VALUES ('287483803e26e20ac1eedfb5709351e7', '测试01', NULL, 2, 'ceshiyixia', 'dev', '2022-05-25 08:32:06', NULL, NULL, 'dev', '2022-05-25 16:15:04', NULL, '2022-05-25 16:32:06');
INSERT INTO `sys_notice` VALUES ('2b930b03532719f4fa50d2a54a3a4434', 'qe', NULL, 2, '<pre><code class=\"JavaScript\">\n      <span class=\"hljs-keyword\">this</span>.editorContent = <span class=\"hljs-keyword\">val</span>\n      <span class=\"hljs-keyword\">if</span> (<span class=\"hljs-keyword\">val</span>) {\n        <span class=\"hljs-keyword\">this</span>.editor.txt.html(<span class=\"hljs-keyword\">val</span>)\n      } <span class=\"hljs-keyword\">else</span> {\n        <span class=\"hljs-keyword\">this</span>.editor.txt.clear()\n      }</code><code class=\"JavaScript\"><br/></code></pre><p>adsadsasd</p>', 'admin', '2022-05-28 06:22:32', NULL, NULL, 'admin', '2022-05-28 14:22:29', NULL, '2022-05-28 14:22:31');
INSERT INTO `sys_notice` VALUES ('340efdea4f4ba56bb3e158f961607254', '测试01', NULL, 2, '<p><br/></p><p>撒旦</p><p><br/></p><pre><code class=\"Bash\">啊实打实的</code></pre><p><br/></p><p><br/></p><hr/><p><br/></p><p><br/></p>', 'dev', '2022-05-28 10:01:59', NULL, NULL, 'dev', '2022-05-28 16:00:00', 'dev', '2022-05-28 18:01:58');
INSERT INTO `sys_notice` VALUES ('36ea96fee8b6d72ca59e8801b398e2a7', '11', NULL, 3, '<p>啊大苏打</p>', 'dev', '2022-05-26 09:05:55', 'dev', '2022-05-26 09:24:22', 'dev', '2022-05-26 17:05:51', NULL, '2022-05-26 17:24:22');
INSERT INTO `sys_notice` VALUES ('453d83ba6f8a62a4d950eeb07ce27b0c', '阿萨大', NULL, 2, '<p>阿萨大&nbsp;</p>', 'dev', '2022-05-27 06:43:12', NULL, NULL, 'dev', '2022-05-26 17:26:06', NULL, '2022-05-27 14:43:12');
INSERT INTO `sys_notice` VALUES ('4a2d5a8d16d256a6544b1131a1df2fe5', '啊实打实的', NULL, 1, '<p>下面请看代码示例</p><pre><code class=\"TypeScript\"><span class=\"hljs-keyword\">import</span> WEditor <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\'wangeditor\'</span>\n<span class=\"hljs-keyword\">import</span> { uploadAction } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\'@/api/manage\'</span>\n<span class=\"hljs-keyword\">import</span> hljs <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\'highlight.js\'</span>\n<span class=\"hljs-keyword\">import</span> <span class=\"hljs-string\">\'highlight.js/styles/idea.css\'</span></code><p><br/></p></pre>', NULL, NULL, NULL, NULL, 'admin', '2022-05-27 16:00:00', 'admin', '2022-05-27 16:00:00');
INSERT INTO `sys_notice` VALUES ('732f99e1212f2ffba9dc9965469ddbd8', '放假通知', NULL, 2, '明天放假，请大家注意下班后水电、门窗的关闭', 'dev', '2022-05-25 03:32:06', NULL, NULL, 'dev', '2022-05-25 11:32:02', NULL, '2022-05-25 11:32:06');
INSERT INTO `sys_notice` VALUES ('7b1f7a6b38db207126a83bb71d561230', '士大夫', NULL, 2, '<p><br/></p><pre>&lt;script&gt;alert(\"xss\");&lt;/script&gt;</pre><p>阿萨大</p><p><br/></p><p>啊实打实的</p><pre><code class=\"Bash\">阿萨大</code></pre>', 'dev', '2022-05-28 11:14:05', NULL, NULL, 'dev', '2022-05-28 16:00:00', 'dev', '2022-05-28 19:14:04');
INSERT INTO `sys_notice` VALUES ('82a68889004a9a0a52a91afd74a7d700', '放假前注意事项', NULL, 2, '<h3>&nbsp; &nbsp;&nbsp;<span style=\"font-weight: bold;\">各位同事请注意，下班时大家注意一下身边的水电是否关闭，没有关闭的请关掉，身边的窗子有没有关掉，<span style=\"color: rgb(194, 79, 74);\">请确认关好门窗</span>在下班&nbsp;</span>🙂</h3>', 'dev', '2022-05-26 06:30:17', NULL, NULL, 'dev', '2022-05-26 14:30:14', NULL, '2022-05-26 14:30:17');
INSERT INTO `sys_notice` VALUES ('8a81fa32a199deff33a654bafdc7bc14', '测试02', NULL, 2, 'asdasd', 'dev', '2022-05-25 08:32:59', NULL, NULL, 'dev', '2022-05-25 16:32:55', NULL, '2022-05-25 16:32:59');
INSERT INTO `sys_notice` VALUES ('965e70cbdc4a6513675eb39c30903bea', '测试图片', NULL, 2, '<h2 style=\"text-align: center;\"><span style=\"font-weight: bold;\">立图为证</span></h2><p style=\"text-align: center;\"><img src=\"https://pics4.baidu.com/feed/9825bc315c6034a88f6e824d9765295e0b2376ea.jpeg?token=bd62c87157297309746230124bf8c9d9\" style=\"max-width:100%;\"><br></p>', 'dev', '2022-05-26 05:48:04', NULL, NULL, 'dev', '2022-05-26 13:48:01', NULL, '2022-05-26 13:48:04');
INSERT INTO `sys_notice` VALUES ('975eef9435496a4bbf5654b3f5a7ec89', '测试01', NULL, 2, '<h3 style=\"text-align:center;\"><b id=\"hfowd\">我要<font color=\"#c24f4a\">测试</font>一下这个<span style=\"background-color: rgb(139, 170, 74);\">东西</span>了啊</b></h3><div><pre><code class=\"JavaScript\">watch: {\n    value(<span class=\"hljs-keyword\">val</span>) {\n      <span class=\"hljs-keyword\">if</span> (<span class=\"hljs-keyword\">val</span> == <span class=\"hljs-keyword\">this</span>.editorContent) {\n        <span class=\"hljs-keyword\">return</span>\n      }\n      <span class=\"hljs-keyword\">if</span> (<span class=\"hljs-keyword\">val</span>) {\n        <span class=\"hljs-keyword\">this</span>.editor.txt.html(<span class=\"hljs-keyword\">val</span>)\n      } <span class=\"hljs-keyword\">else</span> {\n        <span class=\"hljs-keyword\">this</span>.editor.txt.clear()\n      }\n    }\n  },</code></pre></div><div><b>试试咯</b></div><div style=\"text-align:center;\"><img src=\"http://dell.mustright.cn:9090/image/微信图片_20220528104231.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></div><div><b><br/></b></div><div><b>大师傅士大夫是士大夫非得说</b></div><div><b><br/></b></div>', 'dev', '2022-05-28 10:02:34', NULL, NULL, 'dev', '2022-05-28 16:00:00', 'dev', '2022-05-28 18:02:34');
INSERT INTO `sys_notice` VALUES ('976d5d03a7a3a0610b61f36e125994be', '测试03', NULL, 2, 'asdasd', 'dev', '2022-05-25 08:36:16', NULL, NULL, 'dev', '2022-05-25 16:36:14', NULL, '2022-05-25 16:36:16');
INSERT INTO `sys_notice` VALUES ('d4e22afcb9efe53832b646c16c66ff7f', '测试编辑器', NULL, 2, '<h1 style=\"text-align: center;\"><span style=\"color: rgb(194, 79, 74);\">测试一下</span></h1><p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\"><br></p><p><br></p><p><span style=\"font-weight: bold; font-family: 微软雅黑; background-color: rgb(70, 172, 200);\">今天是个好日志</span></p>', 'dev', '2022-05-26 06:03:09', NULL, NULL, 'dev', '2022-05-26 14:03:06', NULL, '2022-05-26 14:03:08');
INSERT INTO `sys_notice` VALUES ('e92a4fe50682d10ed4c4b8b3b216f6ae', '你好啊', NULL, 2, '<p><span style=\"font-weight: bold; background-color: rgb(194, 79, 74);\">阿萨大大</span></p><p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\"><span style=\"font-weight: bold; background-color: rgb(194, 79, 74);\"><br></span></p>', 'dev', '2022-05-26 03:46:28', NULL, NULL, 'dev', '2022-05-26 11:45:06', NULL, '2022-05-26 11:46:28');
INSERT INTO `sys_notice` VALUES ('e9e5ee0292283fef398775c8a04a4435', 'asdasd', NULL, 2, '<pre><code class=\"Java\"> public static &lt;T&gt; List&lt;List&lt;T&gt;&gt; arrange(T[] targetArray) {\n    boolean[] used = new boolean[targetArray.length];\n    Arrays.fill(used, false);\n    List&lt;List&lt;T&gt;&gt; result = new ArrayList&lt;&gt;();\n    List&lt;T&gt; path = new ArrayList&lt;&gt;();\n    backTrack(targetArray, used, path, result);\n    return result;\n  }</code></pre>', 'dev', '2022-05-27 07:04:38', NULL, NULL, 'dev', '2022-05-27 15:04:25', NULL, '2022-05-27 15:04:38');
INSERT INTO `sys_notice` VALUES ('ed7bf07651f8c758b403e0525cf995b9', '测试图片上传', NULL, 2, '<p style=\"text-align: center;\"><img src=\"http://dell.mustright.cn:9090/image/image.jpg\" style=\"max-width:100%;\"><br></p><p style=\"text-align: center;\">哈哈成了</p>', 'dev', '2022-05-27 01:25:08', NULL, NULL, 'dev', '2022-05-27 09:25:05', NULL, '2022-05-27 09:25:08');
INSERT INTO `sys_notice` VALUES ('ef0fb77172b8cecf7cfbc71f740e9a20', '系统维护通知', NULL, 2, '今天下班后系统维护，时间17点-19点，请大家注意', 'dev', '2022-05-24 09:36:24', NULL, NULL, 'dev', '2022-05-24 17:36:08', NULL, '2022-05-24 17:36:23');
INSERT INTO `sys_notice` VALUES ('ef10469fcf62e80470fc3c76ac184b6d', '测试01', NULL, 2, '<p>asdasd</p><p><br/></p>', 'dev', '2022-05-28 11:28:13', NULL, NULL, 'dev', '2022-05-28 19:21:45', 'dev', '2022-05-28 19:28:12');
INSERT INTO `sys_notice` VALUES ('f2bff4ab0589437dcf149643601b513f', '放假通知', NULL, 2, '<h3 style=\"text-align:center;\"><b id=\"j83f9\">明天放假<font color=\"#c24f4a\"> </font></b><font color=\"#c24f4a\">请大家关门窗</font></h3><div>😳<font color=\"#c24f4a\"><br/></font></div><p><br/></p><p data-we-empty-p=\"\" style=\"text-align:center;\"><img src=\"http://dell.mustright.cn:9090/image/微信图片_20220528104231.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 'dev', '2022-05-28 10:01:53', NULL, NULL, 'dev', '2022-05-28 18:01:41', NULL, '2022-05-28 18:01:53');

-- ----------------------------
-- Table structure for sys_notice_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_user`;
CREATE TABLE `sys_notice_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `notice_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `read_flag` tinyint(1) NULL DEFAULT 0 COMMENT '阅读状态',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '阅读时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_notice_user
-- ----------------------------
INSERT INTO `sys_notice_user` VALUES ('009e9f1eba78b7dc6863c989760c3958', '965e70cbdc4a6513675eb39c30903bea', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:26', 'dev', '2022-05-26 13:48:04', NULL, '2022-05-29 17:34:25');
INSERT INTO `sys_notice_user` VALUES ('0214a8a6f8f90470e2e95ea1d5586fdb', 'e9e5ee0292283fef398775c8a04a4435', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-27 07:04:42', 'dev', '2022-05-27 15:04:38', NULL, '2022-05-27 15:04:42');
INSERT INTO `sys_notice_user` VALUES ('043c98d917e4fdba8b1b56e962b81059', '975eef9435496a4bbf5654b3f5a7ec89', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 18:02:34', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('05ece79cbae9c00d7b088cfb55188912', '1b5aff435760d3b26e9bff8c14e8e921', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 19:28:10', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('08cc4fa8ca23be51d9a8fb93952ee1da', 'f2bff4ab0589437dcf149643601b513f', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 18:01:53', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('0a78f46753e6cf3b17b85f5c7af14654', '8a81fa32a199deff33a654bafdc7bc14', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-27 00:42:23', 'dev', '2022-05-25 16:32:59', NULL, '2022-05-27 08:42:22');
INSERT INTO `sys_notice_user` VALUES ('0a8894a12f9ef5c3275280e14b082fb1', '340efdea4f4ba56bb3e158f961607254', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 18:01:58', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('0b486537e3907c33fdec763754d09740', '340efdea4f4ba56bb3e158f961607254', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 10:02:14', 'dev', '2022-05-28 18:01:58', NULL, '2022-05-28 18:02:14');
INSERT INTO `sys_notice_user` VALUES ('0eae8ffc591d619e90ff1696e3c2ea33', 'ef10469fcf62e80470fc3c76ac184b6d', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 19:28:12', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('0fbea5d2730a1888d879c1a9e7f5ef68', '453d83ba6f8a62a4d950eeb07ce27b0c', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-27 14:43:12', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('1010bbb134620b7463a1f2e4ad6d37e2', '975eef9435496a4bbf5654b3f5a7ec89', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 10:02:44', 'dev', '2022-05-28 18:02:34', NULL, '2022-05-28 18:02:43');
INSERT INTO `sys_notice_user` VALUES ('15b2eff685355935359309eff99b5ecc', '285bb6620abc16b6b7c60fe1c453b16c', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 18:01:56', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('176a585b03773d979bde113c052f72c2', '8a81fa32a199deff33a654bafdc7bc14', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-25 16:32:59', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('17b0e654e713ec86b3f8902ce4cf2ff1', '285bb6620abc16b6b7c60fe1c453b16c', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 18:01:56', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('1b210a1a8aa70bc6258a277cc0af6e45', '976d5d03a7a3a0610b61f36e125994be', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-25 16:36:16', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('1c6eb1ffaff7a20d05c4261ff541790e', '340efdea4f4ba56bb3e158f961607254', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 18:01:58', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('1d2818c8c86f80a552ce118fcbbe533b', '340efdea4f4ba56bb3e158f961607254', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 13:14:30', 'dev', '2022-05-28 18:01:58', NULL, '2022-05-28 21:14:30');
INSERT INTO `sys_notice_user` VALUES ('1dc2d58b884357768d1e00d754e7c414', '36ea96fee8b6d72ca59e8801b398e2a7', '589a97faa745390b7888e491fcc754f1', 0, NULL, 'dev', '2022-05-26 17:05:54', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('200b8e27a0c417d675a131bfb34072f9', '07f3bb565c82e398bc73acc2f02c50e5', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-27 23:55:58', 'dev', '2022-05-27 14:43:43', NULL, '2022-05-28 07:55:57');
INSERT INTO `sys_notice_user` VALUES ('24361f2f6bb4cf768764eaf1ba7322dd', '453d83ba6f8a62a4d950eeb07ce27b0c', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-27 14:43:12', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('29e62eaf5bcc4d4ed875cb8f4ec78618', 'ef0fb77172b8cecf7cfbc71f740e9a20', '589a97faa745390b7888e491fcc754f1', 1, NULL, 'dev', '2022-05-24 17:36:23', NULL, '2022-05-26 10:47:27');
INSERT INTO `sys_notice_user` VALUES ('2a3c5eef78b38c06fe54b5b904ac7ae8', 'ed7bf07651f8c758b403e0525cf995b9', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-27 09:25:08', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('2e9f65a9662fb0ddade2b7a5f27e9bd0', '8a81fa32a199deff33a654bafdc7bc14', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-25 16:32:59', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('2eae893bbe361f44853b4432d65094c3', 'ef10469fcf62e80470fc3c76ac184b6d', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 11:39:51', 'dev', '2022-05-28 19:28:12', NULL, '2022-05-28 19:39:51');
INSERT INTO `sys_notice_user` VALUES ('2f221000e2121c585e281acf43f7057c', '7b1f7a6b38db207126a83bb71d561230', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 13:14:07', 'dev', '2022-05-28 19:14:04', NULL, '2022-05-28 21:14:06');
INSERT INTO `sys_notice_user` VALUES ('2fc6d150250b8b4d71c35c9b46dead53', 'ef10469fcf62e80470fc3c76ac184b6d', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:10', 'dev', '2022-05-28 19:28:12', NULL, '2022-05-29 17:34:10');
INSERT INTO `sys_notice_user` VALUES ('3971f06cd953c549d554195981fd451a', '732f99e1212f2ffba9dc9965469ddbd8', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-25 11:32:06', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('3c9621b4bdea83d85f10518f5d759199', '2b930b03532719f4fa50d2a54a3a4434', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 06:39:30', 'admin', '2022-05-28 14:22:31', NULL, '2022-05-28 14:39:29');
INSERT INTO `sys_notice_user` VALUES ('3e3c77c5ec95c4244e2d06076b3f2165', '82a68889004a9a0a52a91afd74a7d700', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-26 14:30:17', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('41583c28e1cdbedc821b0568daddf400', '82a68889004a9a0a52a91afd74a7d700', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:07:15', 'dev', '2022-05-26 14:30:17', NULL, '2022-05-28 08:07:14');
INSERT INTO `sys_notice_user` VALUES ('41903324e5c2d23d0a1828aebbd855ae', '453d83ba6f8a62a4d950eeb07ce27b0c', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-27 14:43:12', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('427775d3e7401a61bc10825a50c72005', '287483803e26e20ac1eedfb5709351e7', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-27 00:42:13', 'dev', '2022-05-25 16:32:06', NULL, '2022-05-27 08:42:12');
INSERT INTO `sys_notice_user` VALUES ('462d6da49060eab5a15390606a18c8a6', '287483803e26e20ac1eedfb5709351e7', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-25 16:32:06', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('471b016d99e57e4a37694707eb82f9b8', '8a81fa32a199deff33a654bafdc7bc14', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-26 02:51:40', 'dev', '2022-05-25 16:32:59', NULL, '2022-05-26 10:51:39');
INSERT INTO `sys_notice_user` VALUES ('48264d318f8119ce82e80859b2313325', '732f99e1212f2ffba9dc9965469ddbd8', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-27 00:42:20', 'dev', '2022-05-25 11:32:06', NULL, '2022-05-27 08:42:19');
INSERT INTO `sys_notice_user` VALUES ('493fa8697aa3191a0ee779ef91f3b9f6', 'ef0fb77172b8cecf7cfbc71f740e9a20', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:23', 'dev', '2022-05-24 17:36:23', NULL, '2022-05-29 17:34:22');
INSERT INTO `sys_notice_user` VALUES ('4c1711b2acdadd1f3b8c5f835afaaf39', '2b930b03532719f4fa50d2a54a3a4434', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 13:14:13', 'admin', '2022-05-28 14:22:31', NULL, '2022-05-28 21:14:12');
INSERT INTO `sys_notice_user` VALUES ('4d38a83f5e96cefbe91fd3a8efdc2d1a', '287483803e26e20ac1eedfb5709351e7', '589a97faa745390b7888e491fcc754f1', 1, NULL, 'dev', '2022-05-25 16:32:06', NULL, '2022-05-26 10:46:43');
INSERT INTO `sys_notice_user` VALUES ('4f06c6ab4fb52d6c7cf4772b44bfdf1d', '82a68889004a9a0a52a91afd74a7d700', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-26 08:58:38', 'dev', '2022-05-26 14:30:17', NULL, '2022-05-26 16:58:37');
INSERT INTO `sys_notice_user` VALUES ('4ff3e25a9551569adbac880e5b99f3c1', '285bb6620abc16b6b7c60fe1c453b16c', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 10:02:16', 'dev', '2022-05-28 18:01:56', NULL, '2022-05-28 18:02:16');
INSERT INTO `sys_notice_user` VALUES ('5073a3cdf12d5647b8c830558627843a', '07f3bb565c82e398bc73acc2f02c50e5', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:18', 'dev', '2022-05-27 14:43:43', NULL, '2022-05-29 17:34:18');
INSERT INTO `sys_notice_user` VALUES ('5108aaeb50a8b0cabfe2067a75ed519b', '1fed4844f67163817914c505acf84ff6', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-27 09:34:44', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('51ffceb4456c167c232ba10397ee0c6d', '975eef9435496a4bbf5654b3f5a7ec89', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-28 18:02:34', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('562966fa68c75ce0c46837694302cecc', '2b930b03532719f4fa50d2a54a3a4434', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'admin', '2022-05-28 14:22:31', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('56322a4099331797929534bddcc66546', '976d5d03a7a3a0610b61f36e125994be', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-25 16:36:16', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('5a7cd067a73b7ef9f345d5db7e3733df', '07f3bb565c82e398bc73acc2f02c50e5', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-27 14:43:43', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('5be85da369907e678ea5fb7ed16de118', '82a68889004a9a0a52a91afd74a7d700', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-26 14:30:17', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('5c68ae451083b39fdcf94d1d6bb2eb38', 'ef0fb77172b8cecf7cfbc71f740e9a20', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:08:06', 'dev', '2022-05-24 17:36:23', NULL, '2022-05-28 08:08:06');
INSERT INTO `sys_notice_user` VALUES ('5d5591f35a87382d8c34eb0babad5bd4', '732f99e1212f2ffba9dc9965469ddbd8', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:21', 'dev', '2022-05-25 11:32:06', NULL, '2022-05-29 17:34:21');
INSERT INTO `sys_notice_user` VALUES ('61af545de868d45e18b76d7d4db6064d', '732f99e1212f2ffba9dc9965469ddbd8', '589a97faa745390b7888e491fcc754f1', 1, NULL, 'dev', '2022-05-25 11:32:06', NULL, '2022-05-26 10:44:22');
INSERT INTO `sys_notice_user` VALUES ('631984c8e314b63c59172f5fe793ea9d', '976d5d03a7a3a0610b61f36e125994be', '589a97faa745390b7888e491fcc754f1', 1, NULL, 'dev', '2022-05-25 16:36:16', NULL, '2022-05-26 10:44:16');
INSERT INTO `sys_notice_user` VALUES ('6ba79a1b6eb28cea72406ce05cac1739', 'e9e5ee0292283fef398775c8a04a4435', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-27 15:04:38', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('6ce457d77eb859151103d096b9f88b30', '965e70cbdc4a6513675eb39c30903bea', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-26 08:59:01', 'dev', '2022-05-26 13:48:04', NULL, '2022-05-26 16:59:01');
INSERT INTO `sys_notice_user` VALUES ('6e0806628aa4d3b25d814d1c5c192dc4', '285bb6620abc16b6b7c60fe1c453b16c', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-28 18:01:56', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('734a863612e77eb140c7a5ff5f637515', '7b1f7a6b38db207126a83bb71d561230', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 19:14:04', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('738fa131cf7f55b718c5e503634c0288', '975eef9435496a4bbf5654b3f5a7ec89', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 18:02:34', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('73d540741f802a5b57478b477cc8f020', '7b1f7a6b38db207126a83bb71d561230', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 19:14:04', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('7443cc6d0ef0db20228eac3c3774237e', '340efdea4f4ba56bb3e158f961607254', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-28 18:01:58', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('7505b86966964072fb324e79577477c5', '1fed4844f67163817914c505acf84ff6', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-27 09:34:44', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('7812aef416c6e5192f7604b9aacd416f', 'ef0fb77172b8cecf7cfbc71f740e9a20', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-24 17:36:23', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('7ba984f6a903b14ab5503bae9b52ecca', 'f2bff4ab0589437dcf149643601b513f', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-28 18:01:53', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('817d5cf3b0d6b49772556398b001f9be', '8a81fa32a199deff33a654bafdc7bc14', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-25 16:32:59', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('831ffa91741a3d593d3abe44e852ed26', 'e92a4fe50682d10ed4c4b8b3b216f6ae', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-26 11:46:28', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('83f9a14cc0dbdb698ef3083ad0de5ccb', 'ef0fb77172b8cecf7cfbc71f740e9a20', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-27 00:42:16', 'dev', '2022-05-24 17:36:23', NULL, '2022-05-27 08:42:15');
INSERT INTO `sys_notice_user` VALUES ('87735829f694c1b2f7f3dacc71ad0c7e', '36ea96fee8b6d72ca59e8801b398e2a7', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-26 17:05:54', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('8b941bb08ac6c7c386c29814e7ab98a0', '965e70cbdc4a6513675eb39c30903bea', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-26 13:48:04', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('8e13925d0d1829e9e5bd42cdaddb48c9', '976d5d03a7a3a0610b61f36e125994be', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-26 08:59:16', 'dev', '2022-05-25 16:36:16', NULL, '2022-05-26 16:59:16');
INSERT INTO `sys_notice_user` VALUES ('924b0514d0b02959f64b88fc2ce76b9d', '1fed4844f67163817914c505acf84ff6', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:02:27', 'dev', '2022-05-27 09:34:44', NULL, '2022-05-28 08:02:26');
INSERT INTO `sys_notice_user` VALUES ('92b7982a7efc4ac4a06e8d4d6c6ec200', 'ed7bf07651f8c758b403e0525cf995b9', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-27 09:25:08', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('95379ed41c32186269ef48b68cd6ba18', '285bb6620abc16b6b7c60fe1c453b16c', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 13:14:10', 'dev', '2022-05-28 18:01:56', NULL, '2022-05-28 21:14:10');
INSERT INTO `sys_notice_user` VALUES ('98babd8fab189f7a2e659c6d9dc7e6a0', '975eef9435496a4bbf5654b3f5a7ec89', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 11:40:05', 'dev', '2022-05-28 18:02:34', NULL, '2022-05-28 19:40:05');
INSERT INTO `sys_notice_user` VALUES ('9c6e91638d09f7d011a91e252fbbe9d9', '1b5aff435760d3b26e9bff8c14e8e921', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-28 19:28:10', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('9d15c2aea6409c6e380e5e60345c38fb', '1b5aff435760d3b26e9bff8c14e8e921', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-28 11:39:56', 'dev', '2022-05-28 19:28:10', NULL, '2022-05-28 19:39:55');
INSERT INTO `sys_notice_user` VALUES ('9ec4747b6edfa8777e4fe93ba3926b6e', '36ea96fee8b6d72ca59e8801b398e2a7', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-26 17:05:54', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('a3bc2f259800b94aba8f97af4c6671be', 'd4e22afcb9efe53832b646c16c66ff7f', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-26 14:03:08', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('a45e460a4676d9eba9d73908c0596ebd', 'ef10469fcf62e80470fc3c76ac184b6d', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 11:28:21', 'dev', '2022-05-28 19:28:12', NULL, '2022-05-28 19:28:21');
INSERT INTO `sys_notice_user` VALUES ('a5d473af297d1d51a054c2bcf7848379', '287483803e26e20ac1eedfb5709351e7', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-25 16:32:06', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('ab02002c2e9d4570b0187c2956987723', '1fed4844f67163817914c505acf84ff6', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-27 01:34:50', 'dev', '2022-05-27 09:34:44', NULL, '2022-05-27 09:34:49');
INSERT INTO `sys_notice_user` VALUES ('ab2945e5af681951adc070c1203a0975', 'ed7bf07651f8c758b403e0525cf995b9', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-27 09:25:08', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('ab4526bda61e841918fad794a3431505', '1b5aff435760d3b26e9bff8c14e8e921', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:12', 'dev', '2022-05-28 19:28:10', NULL, '2022-05-29 17:34:11');
INSERT INTO `sys_notice_user` VALUES ('ac3b12ef83a67ba1e104acc019d7c90a', 'e92a4fe50682d10ed4c4b8b3b216f6ae', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-26 11:46:28', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('acd4fb326e5e97d100b8f1194d00e5c2', 'ed7bf07651f8c758b403e0525cf995b9', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:02:33', 'dev', '2022-05-27 09:25:08', NULL, '2022-05-28 08:02:33');
INSERT INTO `sys_notice_user` VALUES ('ada37b6734aca577edcec6afd2b66a7f', 'e9e5ee0292283fef398775c8a04a4435', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-27 23:55:09', 'dev', '2022-05-27 15:04:38', NULL, '2022-05-28 07:55:08');
INSERT INTO `sys_notice_user` VALUES ('b377a2c05c33ca7be14d9ec7ce40d5b1', 'f2bff4ab0589437dcf149643601b513f', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 18:01:53', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('b481bc50cb2d51ed73832c1dd9f83eca', 'f2bff4ab0589437dcf149643601b513f', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-28 18:01:53', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('b6dada3775f8e0e99e11b66e243b086a', '453d83ba6f8a62a4d950eeb07ce27b0c', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:01:45', 'dev', '2022-05-27 14:43:12', NULL, '2022-05-28 08:01:44');
INSERT INTO `sys_notice_user` VALUES ('b973ebea3bcef88f9dd94e9023dbb772', '965e70cbdc4a6513675eb39c30903bea', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:07:52', 'dev', '2022-05-26 13:48:04', NULL, '2022-05-28 08:07:52');
INSERT INTO `sys_notice_user` VALUES ('bb9c56fb0a654f8983b71ff87fd284cd', 'f2bff4ab0589437dcf149643601b513f', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 10:02:24', 'dev', '2022-05-28 18:01:53', NULL, '2022-05-28 18:02:24');
INSERT INTO `sys_notice_user` VALUES ('be93d43fbc579700c008a0b0e163291f', '7b1f7a6b38db207126a83bb71d561230', '938a6fa68122553d83598611ea1c70bf', 1, '2022-05-29 09:34:15', 'dev', '2022-05-28 19:14:04', NULL, '2022-05-29 17:34:15');
INSERT INTO `sys_notice_user` VALUES ('bfcadcf64a645c5729039feee43eb27e', '1fed4844f67163817914c505acf84ff6', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-27 09:34:44', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('c3edd770ea4bf58aac840c06e6f0ab19', 'e92a4fe50682d10ed4c4b8b3b216f6ae', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-26 08:59:13', 'dev', '2022-05-26 11:46:28', NULL, '2022-05-26 16:59:12');
INSERT INTO `sys_notice_user` VALUES ('c6c593637c77d23844e3e299e7a0d54a', '1b5aff435760d3b26e9bff8c14e8e921', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 11:28:15', 'dev', '2022-05-28 19:28:10', NULL, '2022-05-28 19:28:15');
INSERT INTO `sys_notice_user` VALUES ('c74c1ad21af169916cb414eaa3fa8cd2', '07f3bb565c82e398bc73acc2f02c50e5', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-27 06:43:56', 'dev', '2022-05-27 14:43:43', NULL, '2022-05-27 14:43:55');
INSERT INTO `sys_notice_user` VALUES ('c9ea68c842c00757c376c4429ef7f247', '287483803e26e20ac1eedfb5709351e7', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-25 16:32:06', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('ca80a74c39c1c50b992f5e0efc076680', 'd4e22afcb9efe53832b646c16c66ff7f', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 00:07:56', 'dev', '2022-05-26 14:03:08', NULL, '2022-05-28 08:07:56');
INSERT INTO `sys_notice_user` VALUES ('ceea9eb66821c322c955b81a80d40381', '36ea96fee8b6d72ca59e8801b398e2a7', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-26 17:05:54', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('d4077e24e35dcac16d1045b3c416266f', 'ef10469fcf62e80470fc3c76ac184b6d', '5b43587df91c1aab40f351460ca17c8c', 0, NULL, 'dev', '2022-05-28 19:28:12', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('d839f98cec03db02bb05ea4816bfbf72', '07f3bb565c82e398bc73acc2f02c50e5', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-27 14:43:43', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('d9e2988c294914e81ebd8fa610fe1bc0', 'd4e22afcb9efe53832b646c16c66ff7f', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-26 06:03:14', 'dev', '2022-05-26 14:03:08', NULL, '2022-05-26 14:03:14');
INSERT INTO `sys_notice_user` VALUES ('db04ce16dbbe1f99b8f5771418e3c35b', '732f99e1212f2ffba9dc9965469ddbd8', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-25 11:32:06', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('db6ad8eca91bc10babb5f8b41479c6a7', 'e92a4fe50682d10ed4c4b8b3b216f6ae', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-26 11:46:28', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('dc979d8508a331af3c3dba130716cc46', 'e9e5ee0292283fef398775c8a04a4435', '938a6fa68122553d83598611ea1c70bf', 0, NULL, 'dev', '2022-05-27 15:04:38', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('ddee2428a3b82564c857df9b2e976b07', '965e70cbdc4a6513675eb39c30903bea', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-26 05:48:09', 'dev', '2022-05-26 13:48:04', NULL, '2022-05-26 13:48:09');
INSERT INTO `sys_notice_user` VALUES ('dfc0ad43303e7217882d2927da88f9c6', '2b930b03532719f4fa50d2a54a3a4434', '5b43587df91c1aab40f351460ca17c8c', 1, '2022-05-28 06:22:35', 'admin', '2022-05-28 14:22:31', NULL, '2022-05-28 14:22:35');
INSERT INTO `sys_notice_user` VALUES ('e22f1580329570392f1468954f5b9f99', 'e9e5ee0292283fef398775c8a04a4435', 'd390364cab808f0e52ca6345b0b86e4f', 0, NULL, 'dev', '2022-05-27 15:04:38', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('e29caf31c038b2705b523124418be127', 'e92a4fe50682d10ed4c4b8b3b216f6ae', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-26 03:46:34', 'dev', '2022-05-26 11:46:28', NULL, '2022-05-26 11:46:33');
INSERT INTO `sys_notice_user` VALUES ('e96ea11e4fd235fb4693887f5c52d18d', '976d5d03a7a3a0610b61f36e125994be', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-25 16:36:16', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('ea30bdc46c09721a69ea72429a31ee73', 'ed7bf07651f8c758b403e0525cf995b9', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-27 01:25:16', 'dev', '2022-05-27 09:25:08', NULL, '2022-05-27 09:25:15');
INSERT INTO `sys_notice_user` VALUES ('ed77ff4196aa118578aae839d69cf312', 'd4e22afcb9efe53832b646c16c66ff7f', 'd390364cab808f0e52ca6345b0b86e4f', 1, '2022-05-26 08:58:50', 'dev', '2022-05-26 14:03:08', NULL, '2022-05-26 16:58:49');
INSERT INTO `sys_notice_user` VALUES ('f057b2dbdc0388b27434a1bc42095e23', '82a68889004a9a0a52a91afd74a7d700', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-26 06:30:28', 'dev', '2022-05-26 14:30:17', NULL, '2022-05-26 14:30:27');
INSERT INTO `sys_notice_user` VALUES ('f1029bb0cfbef2a1a39ad305789ac270', '7b1f7a6b38db207126a83bb71d561230', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-28 11:14:08', 'dev', '2022-05-28 19:14:04', NULL, '2022-05-28 19:14:08');
INSERT INTO `sys_notice_user` VALUES ('f2515b60d942f306b2da8b0295cd8692', '36ea96fee8b6d72ca59e8801b398e2a7', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-26 17:05:54', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('f2dfee341b037242b7c2e0952c536b3d', 'd4e22afcb9efe53832b646c16c66ff7f', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'dev', '2022-05-26 14:03:08', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('f6b1b9646ca8e0334096bd3948ebddc2', '2b930b03532719f4fa50d2a54a3a4434', '007be7be4ddd6f09e51c4c46fc39d634', 0, NULL, 'admin', '2022-05-28 14:22:31', NULL, NULL);
INSERT INTO `sys_notice_user` VALUES ('fe991e1ca2cf0ebfd6b0d624ded6262e', '453d83ba6f8a62a4d950eeb07ce27b0c', '589a97faa745390b7888e491fcc754f1', 1, '2022-05-27 06:43:54', 'dev', '2022-05-27 14:43:12', NULL, '2022-05-27 14:43:53');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件',
  `menu_type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单类型 1、菜单 2、按钮',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '页面缓存',
  `hidden` tinyint(1) NULL DEFAULT 0 COMMENT '隐藏菜单',
  `external` tinyint(1) NULL DEFAULT 0 COMMENT '外部页面',
  `sort_no` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `btn_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限编码',
  `btn_action` tinyint(4) NULL DEFAULT NULL COMMENT '按钮权限策略1显示2禁用',
  `btn_active` tinyint(1) NULL DEFAULT NULL COMMENT '按钮启用状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能描述',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_index`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('0400317280cc90522e712b0fe43989c7', 'default', '系统首页', '/dashboard', 'RouteView', 1, 0, 0, 0, 1, 'home', '', 0, 0, '首页显示', 0, 'yangzhen', '2022-02-24 16:00:00', '', '2022-03-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('0871590ae012018403757727afff6fc0', '632e0cfa4e906cfe0bf8a8edcd88e672', '业务字典', '/system/dict', 'modules/dict/MDictList', 1, 1, 0, 0, 4, 'read', NULL, 0, NULL, NULL, 0, 'yangzhen', '2022-03-02 16:00:00', 'dev', '2022-03-08 16:00:00');
INSERT INTO `sys_permission` VALUES ('0d3599f9136e88f35af28aa9f2f3ab19', 'b3a69b83efaeb2de55c1d9d7e802089d', '角色创建', NULL, NULL, 2, NULL, 0, 0, 1, NULL, 'addRole', 1, 1, NULL, 0, 'admin', '2022-03-20 21:02:39', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('13581b1536890e67105a0549ab610387', 'a409dd4aa877d72f79154004782e3837', '新增用户', NULL, NULL, 2, NULL, 0, 0, 1, NULL, 'addUser', 1, 1, NULL, 0, 'admin', '2022-03-17 16:00:00', 'admin', '2022-03-17 16:00:00');
INSERT INTO `sys_permission` VALUES ('138f4dd247bd5482acb83228c18b17dd', 'default', '系统开发', '/develop', 'RouteView', 1, 0, 0, 0, 4, 'laptop', NULL, NULL, NULL, '开发相关功能页面', 0, 'yangzhen', '2022-03-09 16:00:00', 'dev', '2022-03-09 16:00:00');
INSERT INTO `sys_permission` VALUES ('14f1eadefc02bef4ac57464810885925', '83b9ad426c143c68c5fb1dd0b6cd1d1d', '大屏', '/bigscreen', 'http://localhost:9990/manage/html/index?Access-Token=2', 1, 0, 0, 1, 3, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-25 16:00:00', 'dev', '2022-04-26 11:57:39');
INSERT INTO `sys_permission` VALUES ('23a5a866afb89d7c13e8f85c32b58089', '274bca36893d2add7b939ed57429283d', '新增菜单', NULL, NULL, 2, NULL, 0, 0, 1, NULL, 'addPermission', 1, 1, NULL, 0, 'admin', '2022-03-17 18:09:10', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('274bca36893d2add7b939ed57429283d', '632e0cfa4e906cfe0bf8a8edcd88e672', '菜单管理', '/system/permission', 'modules/system/MPermissionList', 1, 1, 0, 0, 1, 'bars', '', 0, 0, NULL, 0, 'yangzhen', '2022-02-24 16:00:00', '', '2022-03-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('2a0de9a0ffa89386200d25075c6b5a89', '632e0cfa4e906cfe0bf8a8edcd88e672', '在线管理', '/sys/socket/onlineuser', 'modules/system/OnlineUserList', 1, 1, 0, 0, 7, 'wifi', NULL, NULL, NULL, NULL, 0, 'admin', '2022-03-31 16:00:00', 'dev', '2022-04-06 16:00:00');
INSERT INTO `sys_permission` VALUES ('2fd7b6fd01fd7407b56fc2a2e8a96258', 'af4bd16ac00161a9a14a8e02a8faec7e', '系统信息', '/monitor/server', 'monitor/SystemInfo', 1, 0, 0, 0, 3, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-18 16:00:00', 'dev', '2022-04-19 11:40:48');
INSERT INTO `sys_permission` VALUES ('316076f7f3e425577d3da52e12855793', '632e0cfa4e906cfe0bf8a8edcd88e672', '文件管理', '/system/sysFile', 'modules/file/SysFileList', 1, 1, 0, 0, 6, 'folder', NULL, NULL, NULL, NULL, 0, 'admin', '2022-03-21 16:00:00', 'admin', '2022-03-21 16:00:00');
INSERT INTO `sys_permission` VALUES ('3ae7eda4015fe1b3ededd51a0c9ec248', 'f718e6ffd8de8bcd52145fa4446aa48e', '用户中心', '/account/usercenter', 'user/UserCenter', 1, 0, 0, 0, 1, 'solution', NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-18 17:27:29', 'dev', '2022-05-25 18:45:54');
INSERT INTO `sys_permission` VALUES ('3b235f4832eba1ed4f5af5440444e99b', '83b9ad426c143c68c5fb1dd0b6cd1d1d', '藏经阁', '/external/cjg', 'https://www.sunjs.com/doc.html', 1, 0, 0, 1, 2, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-22 16:00:00', 'dev', '2022-04-23 00:12:03');
INSERT INTO `sys_permission` VALUES ('3f2fe095b131bf4997644f141e043f65', 'af4bd16ac00161a9a14a8e02a8faec7e', '请求追踪', '/monitor/httptrace', 'monitor/HttpTrace', 1, 0, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-19 11:24:55', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('3ffd8910169c0fd79bd4f114c31b5340', 'b3a69b83efaeb2de55c1d9d7e802089d', '角色授权', NULL, NULL, 2, NULL, 0, 0, 2, NULL, 'handlePerm', 1, 1, NULL, 0, 'admin', '2022-03-20 21:04:56', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('4131d296f6b9e13734840f8c06b81d65', 'd7536d93f3e9ed56f18a060fd1fcbbd8', '文档管理', '/article/manage', 'modules/article/ArticleManage', 1, 1, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2022-03-22 16:00:00', 'dev', '2022-03-27 16:00:00');
INSERT INTO `sys_permission` VALUES ('628959f2ccb4405c203499ab512559d7', '0400317280cc90522e712b0fe43989c7', '工作台', '/dashboard/workplace', 'dashboard/Workplace', 1, 0, 0, 0, 1, '', '', 0, 0, NULL, 0, 'yangzhen', '2022-02-25 14:16:41', '', '2022-03-23 12:39:16');
INSERT INTO `sys_permission` VALUES ('632e0cfa4e906cfe0bf8a8edcd88e672', 'default', '系统管理', '/system', 'RouteView', 1, 0, 0, 0, 2, 'setting', '', 0, 0, '系统功能管理相关页面', 0, 'yangzhen', '2022-02-24 16:00:00', '', '2022-04-08 16:00:00');
INSERT INTO `sys_permission` VALUES ('80bcf7e8d344db4f5ae01160dbc053c7', 'af4bd16ac00161a9a14a8e02a8faec7e', 'TMC信息', '/monitor/tomcat', 'monitor/TomcatInfo', 1, 0, 0, 0, 4, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-18 16:00:00', 'dev', '2022-04-19 18:03:21');
INSERT INTO `sys_permission` VALUES ('83b9ad426c143c68c5fb1dd0b6cd1d1d', 'default', '外部网页', '/external', 'RouteView', 1, 0, 0, 0, 7, 'ie', NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-23 00:11:06', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('8804dbf2f76d612eb48628f8116e661d', '138f4dd247bd5482acb83228c18b17dd', '在线文档', '/system/swagger', '{{window._CONFIG.domianURL}}/doc.html', 1, 1, 0, 0, 2, 'cloud', NULL, NULL, NULL, NULL, 0, 'yangzhen', '2022-03-09 16:00:00', 'yangzhen', '2022-04-18 15:46:39');
INSERT INTO `sys_permission` VALUES ('9266fda552bad0a067ca66478c5df924', 'f718e6ffd8de8bcd52145fa4446aa48e', '我的消息', '/sys/myNotice', 'modules/notice/SysNoticeUserList', 1, 0, 1, 0, 2, 'message', NULL, NULL, NULL, NULL, 0, 'dev', '2022-05-25 11:45:49', 'dev', '2022-05-25 11:46:22');
INSERT INTO `sys_permission` VALUES ('9f644e165d5c94ec4b2456f993aa0c0c', '83b9ad426c143c68c5fb1dd0b6cd1d1d', '百度一下', '/external/baidu', 'https://www.baidu.com/', 1, 0, 0, 1, 1, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-22 16:00:00', 'dev', '2022-04-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('a409dd4aa877d72f79154004782e3837', '632e0cfa4e906cfe0bf8a8edcd88e672', '用户管理', '/system/user', 'modules/system/MUserList', 1, 1, 0, 0, 2, 'team', '', 0, 0, NULL, 0, 'yangzhen', '2022-02-24 16:00:00', '', '2022-03-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('a5010e0cefcfac71ff1193937f3517b6', 'd7536d93f3e9ed56f18a060fd1fcbbd8', '文档查阅', '/article/view', 'modules/article/ArticleView', 1, 1, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2022-03-22 17:57:28', NULL, '2022-03-27 18:30:08');
INSERT INTO `sys_permission` VALUES ('af4bd16ac00161a9a14a8e02a8faec7e', 'default', '系统监控', '/monitor', 'RouteView', 1, 0, 0, 0, 3, 'dashboard', NULL, NULL, NULL, '系统监控', 0, 'dev', '2022-04-18 16:00:00', 'dev', '2022-04-20 09:25:02');
INSERT INTO `sys_permission` VALUES ('b189e34b7b546dc34d329771c8dad337', '316076f7f3e425577d3da52e12855793', '文件上传', NULL, NULL, 2, NULL, 0, 0, 1, NULL, 'uploadFile', 1, 1, NULL, 0, 'admin', '2022-03-23 22:53:03', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('b3a69b83efaeb2de55c1d9d7e802089d', '632e0cfa4e906cfe0bf8a8edcd88e672', '角色管理', '/system/role', 'modules/system/MRoleList', 1, 1, 0, 0, 3, 'user', '', 0, 0, NULL, 0, 'yangzhen', '2022-02-24 16:00:00', '', '2022-03-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('b69bf26a81f33b0ceaa99602ee2ddd92', '138f4dd247bd5482acb83228c18b17dd', '定时任务', '/quartz/sysQuartzJob', 'modules/quartz/QuartzJobList', 1, 0, 0, 0, 3, 'block', NULL, NULL, NULL, 'quartz定时任务', 0, 'admin', '2022-03-24 16:00:00', 'dev', '2022-04-07 16:00:00');
INSERT INTO `sys_permission` VALUES ('bf3f4f727e07672faaf1199bf727db99', 'af4bd16ac00161a9a14a8e02a8faec7e', 'SQL监控', '/system/druid', '{{window._CONFIG.domianURL}}/druid/index.html', 1, 1, 0, 0, 5, '', NULL, NULL, NULL, NULL, 0, 'yangzhen', '2022-03-09 16:00:00', 'dev', '2022-04-17 16:00:00');
INSERT INTO `sys_permission` VALUES ('d7536d93f3e9ed56f18a060fd1fcbbd8', 'default', '文章管理', '/article', 'RouteView', 1, 0, 0, 0, 6, 'read', NULL, NULL, NULL, 'MarkDown文章管理', 0, 'admin', '2022-03-22 16:00:00', 'admin', '2022-03-22 16:00:00');
INSERT INTO `sys_permission` VALUES ('de7623291ac50f586fc119677d6c0e9d', '632e0cfa4e906cfe0bf8a8edcd88e672', '通知管理', '/sys/notice', 'modules/notice/SysNoticeList', 1, 1, 0, 0, 8, 'notification', NULL, NULL, NULL, NULL, 0, 'dev', '2022-05-24 09:15:18', 'dev', '2022-05-24 09:16:12');
INSERT INTO `sys_permission` VALUES ('e6bbb18871e6140c6a6b227eff6490ac', '274bca36893d2add7b939ed57429283d', '编辑菜单', NULL, NULL, 2, NULL, 0, 0, 2, NULL, 'editPermission', 1, 1, NULL, 0, 'admin', '2022-04-02 16:00:00', 'admin', '2022-04-02 16:00:00');
INSERT INTO `sys_permission` VALUES ('ed9eff53d69affee50a5f91759d3baee', 'af4bd16ac00161a9a14a8e02a8faec7e', 'JVM信息', '/monitor/jvm', 'monitor/JvmInfo', 1, 0, 0, 0, 1, NULL, NULL, NULL, NULL, NULL, 0, 'dev', '2022-04-19 11:21:02', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('f718e6ffd8de8bcd52145fa4446aa48e', 'default', '隐藏菜单', '/hidden', 'RouteView', 1, 0, 1, 0, 5, 'block', NULL, NULL, NULL, '用于系统中默认页面组件 eg ：用户中心', 0, 'dev', '2022-04-18 16:00:00', 'dev', '2022-04-18 16:00:00');
INSERT INTO `sys_permission` VALUES ('fa8b03b1f647547f9c1b3a0478c3ff86', '138f4dd247bd5482acb83228c18b17dd', '代码生成', '/develop/generator', 'modules/code/TableSchemaList', 1, 0, 0, 0, 1, 'code', NULL, 0, 0, NULL, 0, 'yangzhen', '2022-03-07 16:00:00', 'yangzhen', '2022-04-27 19:09:44');
INSERT INTO `sys_permission` VALUES ('ff161912c0345c3565964f598d074641', '632e0cfa4e906cfe0bf8a8edcd88e672', '操作日志', '/system/ulog', 'modules/log/MOperationLogList', 1, 1, 0, 0, 5, 'file-exclamation', '', 2, 0, NULL, 0, 'yangzhen', '2022-03-02 16:00:00', 'yangzhen', '2022-03-22 16:00:00');

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名',
  `job_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `parameter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '1正常 2停止',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `job_class_index`(`job_class`) USING BTREE,
  UNIQUE INDEX `name_index`(`name`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'quartz任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES ('b24ef6d3f5d1cf61e16dd9d6741fd91a', '无参基础任务', 'com.github.mustsd.modules.quartz.job.SimpleJob', '3/2 * * * * ? *', NULL, 2, '每2秒执行一次', 'admin', '2022-03-25 16:00:00', 'admin', '2022-03-30 16:15:53');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('96bd383ed593497d9a89a6beb22b7fc3', '游客', 'visitor', '演示用角色', 'yangzhen', '2022-03-12 16:00:00', 'dev', '2022-04-20 16:19:59');
INSERT INTO `sys_role` VALUES ('9dd87a2be0f6b45adce49eb5f6450dd0', '开发者', 'developer', '系统开发者', 'admin', '2022-04-02 22:57:31', NULL, NULL);
INSERT INTO `sys_role` VALUES ('e242442369c7229953b6f2001b4a9a13', '系统管理员', 'admin', '系统管理员，最高权限', 'yangzhen', '2022-03-12 16:00:00', 'dev', '2022-04-03 13:16:14');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('025a920626f1aff0cc79837cc90096a8', '9dd87a2be0f6b45adce49eb5f6450dd0', '9f644e165d5c94ec4b2456f993aa0c0c');
INSERT INTO `sys_role_permission` VALUES ('058c74fd7e453653e291365e67803bd7', '9dd87a2be0f6b45adce49eb5f6450dd0', '8804dbf2f76d612eb48628f8116e661d');
INSERT INTO `sys_role_permission` VALUES ('06941f4030cdeebaf3cc85b66388ee85', '9dd87a2be0f6b45adce49eb5f6450dd0', '0d3599f9136e88f35af28aa9f2f3ab19');
INSERT INTO `sys_role_permission` VALUES ('077db8120e17b20311fb10967dcaea33', 'e242442369c7229953b6f2001b4a9a13', 'b69bf26a81f33b0ceaa99602ee2ddd92');
INSERT INTO `sys_role_permission` VALUES ('087e7b7d5b4b753a6b76eb82f2581677', 'e242442369c7229953b6f2001b4a9a13', '0d3599f9136e88f35af28aa9f2f3ab19');
INSERT INTO `sys_role_permission` VALUES ('08e2132239b1de584982a26f7cbb6979', '9dd87a2be0f6b45adce49eb5f6450dd0', 'b189e34b7b546dc34d329771c8dad337');
INSERT INTO `sys_role_permission` VALUES ('0f4d22f64fdfda2d19986a44952f577a', '9dd87a2be0f6b45adce49eb5f6450dd0', '138f4dd247bd5482acb83228c18b17dd');
INSERT INTO `sys_role_permission` VALUES ('130f69b3b0346318a43139b1cc5e0013', 'e242442369c7229953b6f2001b4a9a13', 'ff161912c0345c3565964f598d074641');
INSERT INTO `sys_role_permission` VALUES ('19a25f6925fe38a358a3918b599ca37a', '96bd383ed593497d9a89a6beb22b7fc3', '628959f2ccb4405c203499ab512559d7');
INSERT INTO `sys_role_permission` VALUES ('1b65c51118a16d42e7f7f3a87d3b8995', 'e242442369c7229953b6f2001b4a9a13', 'd7536d93f3e9ed56f18a060fd1fcbbd8');
INSERT INTO `sys_role_permission` VALUES ('1d34a3d5757cb1cd7f5d13bd0d79c61b', '96bd383ed593497d9a89a6beb22b7fc3', 'a5010e0cefcfac71ff1193937f3517b6');
INSERT INTO `sys_role_permission` VALUES ('1f8fcc2b78cb03441d0c483f182939c1', 'e242442369c7229953b6f2001b4a9a13', '2a0de9a0ffa89386200d25075c6b5a89');
INSERT INTO `sys_role_permission` VALUES ('2194d44ccf83b5f8dd06ec5f2d322229', 'e242442369c7229953b6f2001b4a9a13', '9266fda552bad0a067ca66478c5df924');
INSERT INTO `sys_role_permission` VALUES ('287096e36a7f2670448e2afbe5f292b6', '96bd383ed593497d9a89a6beb22b7fc3', 'd7536d93f3e9ed56f18a060fd1fcbbd8');
INSERT INTO `sys_role_permission` VALUES ('2ff9e42b4c1c6621eb675e342f1a928a', 'e242442369c7229953b6f2001b4a9a13', '632e0cfa4e906cfe0bf8a8edcd88e672');
INSERT INTO `sys_role_permission` VALUES ('30b21c5653741e6b81e13fecebf594c0', 'e242442369c7229953b6f2001b4a9a13', '274bca36893d2add7b939ed57429283d');
INSERT INTO `sys_role_permission` VALUES ('341d4b405e5d141820e4dbc8c13915e7', '9dd87a2be0f6b45adce49eb5f6450dd0', '3b235f4832eba1ed4f5af5440444e99b');
INSERT INTO `sys_role_permission` VALUES ('348c3ee23eb60c75be2accb7b64fc2ba', '9dd87a2be0f6b45adce49eb5f6450dd0', 'ed9eff53d69affee50a5f91759d3baee');
INSERT INTO `sys_role_permission` VALUES ('3d1764f78226623d02a2b63ffbac7dc6', '96bd383ed593497d9a89a6beb22b7fc3', 'f718e6ffd8de8bcd52145fa4446aa48e');
INSERT INTO `sys_role_permission` VALUES ('432b63cb44db1a537686dca44aa6d373', '9dd87a2be0f6b45adce49eb5f6450dd0', '2fd7b6fd01fd7407b56fc2a2e8a96258');
INSERT INTO `sys_role_permission` VALUES ('46b333687357deb0b4b96175ed274c7b', '96bd383ed593497d9a89a6beb22b7fc3', '3ae7eda4015fe1b3ededd51a0c9ec248');
INSERT INTO `sys_role_permission` VALUES ('476795321e482ed0164e567ebb216ba5', '9dd87a2be0f6b45adce49eb5f6450dd0', 'af4bd16ac00161a9a14a8e02a8faec7e');
INSERT INTO `sys_role_permission` VALUES ('486e493721ce08a9052fe38e60dfcd8e', 'e242442369c7229953b6f2001b4a9a13', '3ffd8910169c0fd79bd4f114c31b5340');
INSERT INTO `sys_role_permission` VALUES ('4f03a6cefd2550ef16f54d9279cba230', '96bd383ed593497d9a89a6beb22b7fc3', 'ff161912c0345c3565964f598d074641');
INSERT INTO `sys_role_permission` VALUES ('52eda99284557a96bf86ab7523fdec4b', '9dd87a2be0f6b45adce49eb5f6450dd0', 'bf3f4f727e07672faaf1199bf727db99');
INSERT INTO `sys_role_permission` VALUES ('5387b1383ad688a4a17f51a706b71c8a', '9dd87a2be0f6b45adce49eb5f6450dd0', '83b9ad426c143c68c5fb1dd0b6cd1d1d');
INSERT INTO `sys_role_permission` VALUES ('543b4301be262208a35ab7cf18b12ed6', '96bd383ed593497d9a89a6beb22b7fc3', 'af4bd16ac00161a9a14a8e02a8faec7e');
INSERT INTO `sys_role_permission` VALUES ('56b9a31d615372beb8fa6f39e132c2ee', 'e242442369c7229953b6f2001b4a9a13', 'b189e34b7b546dc34d329771c8dad337');
INSERT INTO `sys_role_permission` VALUES ('58993c757d52418160fecb43dc3baa72', 'e242442369c7229953b6f2001b4a9a13', 'a5010e0cefcfac71ff1193937f3517b6');
INSERT INTO `sys_role_permission` VALUES ('5a923d56ac9cc601b80a2d78c806eb9f', '9dd87a2be0f6b45adce49eb5f6450dd0', 'a409dd4aa877d72f79154004782e3837');
INSERT INTO `sys_role_permission` VALUES ('5ee4a72c102c033a04dc2f47a2be1563', '96bd383ed593497d9a89a6beb22b7fc3', '316076f7f3e425577d3da52e12855793');
INSERT INTO `sys_role_permission` VALUES ('5fa457c612412aa2d3d67ff7ca116ea4', '9dd87a2be0f6b45adce49eb5f6450dd0', '23a5a866afb89d7c13e8f85c32b58089');
INSERT INTO `sys_role_permission` VALUES ('5fa62f88165391d808999fe33a04254a', '9dd87a2be0f6b45adce49eb5f6450dd0', '13581b1536890e67105a0549ab610387');
INSERT INTO `sys_role_permission` VALUES ('6098c5bbc89533bf3ddcad2d23dae452', '9dd87a2be0f6b45adce49eb5f6450dd0', '316076f7f3e425577d3da52e12855793');
INSERT INTO `sys_role_permission` VALUES ('63515a79ce6cb4ae375fd8b567953882', '9dd87a2be0f6b45adce49eb5f6450dd0', '274bca36893d2add7b939ed57429283d');
INSERT INTO `sys_role_permission` VALUES ('65c4818744f220bd334c66aa39acde71', '9dd87a2be0f6b45adce49eb5f6450dd0', '14f1eadefc02bef4ac57464810885925');
INSERT INTO `sys_role_permission` VALUES ('6690a8febdfb54315db57a8af1a3790a', '96bd383ed593497d9a89a6beb22b7fc3', 'b3a69b83efaeb2de55c1d9d7e802089d');
INSERT INTO `sys_role_permission` VALUES ('70bc7bd85895d870e1acde73f6b6022f', '9dd87a2be0f6b45adce49eb5f6450dd0', 'ff161912c0345c3565964f598d074641');
INSERT INTO `sys_role_permission` VALUES ('734f0738670e5b86983d69e5a5d65056', 'e242442369c7229953b6f2001b4a9a13', 'a409dd4aa877d72f79154004782e3837');
INSERT INTO `sys_role_permission` VALUES ('74c42d5c4c288d09f4f3cddc088169f6', 'e242442369c7229953b6f2001b4a9a13', '4131d296f6b9e13734840f8c06b81d65');
INSERT INTO `sys_role_permission` VALUES ('759f67a744c53abc8644b21e74da3f88', '9dd87a2be0f6b45adce49eb5f6450dd0', 'd7536d93f3e9ed56f18a060fd1fcbbd8');
INSERT INTO `sys_role_permission` VALUES ('7603c4a5aca8d0b47e0597faea4ba4a7', '96bd383ed593497d9a89a6beb22b7fc3', '9f644e165d5c94ec4b2456f993aa0c0c');
INSERT INTO `sys_role_permission` VALUES ('77c9d6ae27a9cb023fa08400c51dd391', 'e242442369c7229953b6f2001b4a9a13', 'f718e6ffd8de8bcd52145fa4446aa48e');
INSERT INTO `sys_role_permission` VALUES ('7d015373458e4ed4a830ed46a4b44fef', '9dd87a2be0f6b45adce49eb5f6450dd0', 'b69bf26a81f33b0ceaa99602ee2ddd92');
INSERT INTO `sys_role_permission` VALUES ('7f5a06fc6a76bdbd3cf37445b2c0a6a3', '96bd383ed593497d9a89a6beb22b7fc3', '2fd7b6fd01fd7407b56fc2a2e8a96258');
INSERT INTO `sys_role_permission` VALUES ('80e661fe993ec1b665b00831a6388a95', 'e242442369c7229953b6f2001b4a9a13', '3ae7eda4015fe1b3ededd51a0c9ec248');
INSERT INTO `sys_role_permission` VALUES ('81781632befb29cfaf3a3425af93a869', '9dd87a2be0f6b45adce49eb5f6450dd0', 'e6bbb18871e6140c6a6b227eff6490ac');
INSERT INTO `sys_role_permission` VALUES ('83d939bfa368f94ecc780f1882edac8a', '9dd87a2be0f6b45adce49eb5f6450dd0', '3ffd8910169c0fd79bd4f114c31b5340');
INSERT INTO `sys_role_permission` VALUES ('88ec2c9c1c07372c7dc50fd5f450976a', '9dd87a2be0f6b45adce49eb5f6450dd0', '80bcf7e8d344db4f5ae01160dbc053c7');
INSERT INTO `sys_role_permission` VALUES ('89b2428eced6b1518646372bf2bb4c80', 'e242442369c7229953b6f2001b4a9a13', '23a5a866afb89d7c13e8f85c32b58089');
INSERT INTO `sys_role_permission` VALUES ('8b14db2facf8b218a8ec1e20bb22c14a', 'e242442369c7229953b6f2001b4a9a13', '0871590ae012018403757727afff6fc0');
INSERT INTO `sys_role_permission` VALUES ('8b888ef7ddef998ef456a43eb1a5a706', 'e242442369c7229953b6f2001b4a9a13', '13581b1536890e67105a0549ab610387');
INSERT INTO `sys_role_permission` VALUES ('8c502fbfc4602f972fea46edfb97d4f8', '9dd87a2be0f6b45adce49eb5f6450dd0', '3ae7eda4015fe1b3ededd51a0c9ec248');
INSERT INTO `sys_role_permission` VALUES ('8f779fdea70b97389a2724d9f34ae55e', '96bd383ed593497d9a89a6beb22b7fc3', '83b9ad426c143c68c5fb1dd0b6cd1d1d');
INSERT INTO `sys_role_permission` VALUES ('9101638b4541fd1e713f2690e9f9e113', '96bd383ed593497d9a89a6beb22b7fc3', '274bca36893d2add7b939ed57429283d');
INSERT INTO `sys_role_permission` VALUES ('98af8bcbc278cfde6ca57de9a4686424', '9dd87a2be0f6b45adce49eb5f6450dd0', '632e0cfa4e906cfe0bf8a8edcd88e672');
INSERT INTO `sys_role_permission` VALUES ('9c15eee070f168f92ecafb094d71b947', '9dd87a2be0f6b45adce49eb5f6450dd0', 'fa8b03b1f647547f9c1b3a0478c3ff86');
INSERT INTO `sys_role_permission` VALUES ('9f3231cd3a9e0192c4545d8f3c7fc58a', '9dd87a2be0f6b45adce49eb5f6450dd0', '3f2fe095b131bf4997644f141e043f65');
INSERT INTO `sys_role_permission` VALUES ('ae6f0543c1dd0e0bf61c2ea31a968881', '96bd383ed593497d9a89a6beb22b7fc3', '632e0cfa4e906cfe0bf8a8edcd88e672');
INSERT INTO `sys_role_permission` VALUES ('b0d42a05a6af16ccfd3b3927d38a3ee9', '9dd87a2be0f6b45adce49eb5f6450dd0', '4131d296f6b9e13734840f8c06b81d65');
INSERT INTO `sys_role_permission` VALUES ('b5cb18bbbe5372d58e1aeae62b23bde1', '96bd383ed593497d9a89a6beb22b7fc3', 'a409dd4aa877d72f79154004782e3837');
INSERT INTO `sys_role_permission` VALUES ('b90bed7abd7459b63dc2321d3415ad98', '9dd87a2be0f6b45adce49eb5f6450dd0', 'de7623291ac50f586fc119677d6c0e9d');
INSERT INTO `sys_role_permission` VALUES ('b9ef30db0ba70a7a027ef431c3452cd1', 'e242442369c7229953b6f2001b4a9a13', '316076f7f3e425577d3da52e12855793');
INSERT INTO `sys_role_permission` VALUES ('c338d9733ad35d195a29c98a77d339b7', '9dd87a2be0f6b45adce49eb5f6450dd0', '0400317280cc90522e712b0fe43989c7');
INSERT INTO `sys_role_permission` VALUES ('c42ce14b0a34c78028d3349cb4e47380', 'e242442369c7229953b6f2001b4a9a13', 'b3a69b83efaeb2de55c1d9d7e802089d');
INSERT INTO `sys_role_permission` VALUES ('c6ae61773aa47ce5524b5370b7745de5', '9dd87a2be0f6b45adce49eb5f6450dd0', 'f718e6ffd8de8bcd52145fa4446aa48e');
INSERT INTO `sys_role_permission` VALUES ('d0e9daf0925bdffed87a8ddb8529fac3', '9dd87a2be0f6b45adce49eb5f6450dd0', '628959f2ccb4405c203499ab512559d7');
INSERT INTO `sys_role_permission` VALUES ('d3399d9a50e0ca4fe8c46ce8adacc2ca', '9dd87a2be0f6b45adce49eb5f6450dd0', '0871590ae012018403757727afff6fc0');
INSERT INTO `sys_role_permission` VALUES ('db2aaf2a2db26b45510c56cd853b7d09', 'e242442369c7229953b6f2001b4a9a13', '628959f2ccb4405c203499ab512559d7');
INSERT INTO `sys_role_permission` VALUES ('df349d3743532773dd03fe5cfbd8c85c', '9dd87a2be0f6b45adce49eb5f6450dd0', 'a5010e0cefcfac71ff1193937f3517b6');
INSERT INTO `sys_role_permission` VALUES ('dfad05d84f9418c32e48bc83d923deda', 'e242442369c7229953b6f2001b4a9a13', '0400317280cc90522e712b0fe43989c7');
INSERT INTO `sys_role_permission` VALUES ('e86f7873e9f73dcc93f749b99623e3b8', '9dd87a2be0f6b45adce49eb5f6450dd0', 'b3a69b83efaeb2de55c1d9d7e802089d');
INSERT INTO `sys_role_permission` VALUES ('e9b0b37ea4b4141e77b8a3f29f55b15d', '96bd383ed593497d9a89a6beb22b7fc3', '0400317280cc90522e712b0fe43989c7');
INSERT INTO `sys_role_permission` VALUES ('efb66dd010fc2329a7be42b44959710c', '9dd87a2be0f6b45adce49eb5f6450dd0', '2a0de9a0ffa89386200d25075c6b5a89');
INSERT INTO `sys_role_permission` VALUES ('f451c71b55cf34ca82a807775e18a438', 'e242442369c7229953b6f2001b4a9a13', '138f4dd247bd5482acb83228c18b17dd');
INSERT INTO `sys_role_permission` VALUES ('fbc6f86ce12e4f7f9bfb134cb2afd694', 'e242442369c7229953b6f2001b4a9a13', 'de7623291ac50f586fc119677d6c0e9d');
INSERT INTO `sys_role_permission` VALUES ('fbfd3809c53234223139f5d7e6b12b33', '9dd87a2be0f6b45adce49eb5f6450dd0', '9266fda552bad0a067ca66478c5df924');
INSERT INTO `sys_role_permission` VALUES ('fee3c5d65c80d2f0ee60d90b9ced9841', '96bd383ed593497d9a89a6beb22b7fc3', '9266fda552bad0a067ca66478c5df924');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '加密盐',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_index`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('007be7be4ddd6f09e51c4c46fc39d634', 'test', '测试用户', '91191a13f0063f29', 'CPKEFKQA', 1, 'http://dell.mustright.cn:9090/attachment/image.jpg', '1888888888', 'test@163.com', NULL, 'dev', '2022-04-21 16:00:00', 'test', '2022-05-01 21:58:49');
INSERT INTO `sys_user` VALUES ('589a97faa745390b7888e491fcc754f1', 'dev', '开发者', '416172c73fc731e2', 'LOPQXUJL', 1, 'http://dell.mustright.cn:9090/image/image.jpg', '18410187765', '18410147796@163.com', NULL, 'admin', '2022-04-02 16:00:00', 'admin', '2022-05-01 16:00:00');
INSERT INTO `sys_user` VALUES ('5b43587df91c1aab40f351460ca17c8c', 'admin', 'admin', 'd80c6ebfc747e974', 'PACRDWOO', 1, 'http://dell.mustright.cn:9090/attachment/image.jpg', '18410147796', '18410147796@163.com', NULL, 'yangzhen', '2022-03-15 16:00:00', 'yangzhen', '2022-05-01 21:58:53');
INSERT INTO `sys_user` VALUES ('938a6fa68122553d83598611ea1c70bf', 'demo', '演示账号', 'df2d37f62188b44b', 'IJWSKIBU', 1, 'http://dell.mustright.cn:9090/attachment/image.jpg', '1888888888', 'demo-user@163.com', NULL, 'dev', '2022-04-19 16:00:00', 'dev', '2022-05-01 21:58:59');
INSERT INTO `sys_user` VALUES ('d390364cab808f0e52ca6345b0b86e4f', 'yangzhen', '杨振', '7ae4c2bba627b0e9b00155ebbc362efc', 'ATHKRUCY', 1, 'http://dell.mustright.cn:9090/market//tx_1625627806044.jpg', '18410147796', 'yangzhen@mustright.cn', 0, '', '2022-02-25 16:00:00', '', '2022-03-01 16:00:00');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1f7e5b182c8a0adcdc58035e6224c350', '589a97faa745390b7888e491fcc754f1', '9dd87a2be0f6b45adce49eb5f6450dd0');
INSERT INTO `sys_user_role` VALUES ('3c8f32b12cc0460628e3a812c36e41e5', '007be7be4ddd6f09e51c4c46fc39d634', '96bd383ed593497d9a89a6beb22b7fc3');
INSERT INTO `sys_user_role` VALUES ('480d0acc669f2c06db0f189be66f380a', '938a6fa68122553d83598611ea1c70bf', '96bd383ed593497d9a89a6beb22b7fc3');
INSERT INTO `sys_user_role` VALUES ('6c09eddb37e73f57825c2662f924896d', 'd390364cab808f0e52ca6345b0b86e4f', 'e242442369c7229953b6f2001b4a9a13');
INSERT INTO `sys_user_role` VALUES ('8f330b885f9b6198ec0c539fa575588d', 'd390364cab808f0e52ca6345b0b86e4f', '96bd383ed593497d9a89a6beb22b7fc3');
INSERT INTO `sys_user_role` VALUES ('9f4dd5fd002b95d4c13bfe1e861cc7d5', '007be7be4ddd6f09e51c4c46fc39d634', 'e242442369c7229953b6f2001b4a9a13');
INSERT INTO `sys_user_role` VALUES ('bc349ee8abbcc809efbce28e2a44a47d', '5b43587df91c1aab40f351460ca17c8c', 'e242442369c7229953b6f2001b4a9a13');

SET FOREIGN_KEY_CHECKS = 1;
