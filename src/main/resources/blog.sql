/*
 Navicat Premium Data Transfer

 Source Server         : blog
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 06/11/2019 12:13:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_advice
-- ----------------------------
DROP TABLE IF EXISTS `tb_advice`;
CREATE TABLE `tb_advice`  (
  `advice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `advice_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  PRIMARY KEY (`advice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_advice
-- ----------------------------
INSERT INTO `tb_advice` VALUES (5, '你可以听很丧的歌，但我希望你看看外面的太阳星星月亮行人树木花香棉花糖雨滴动物，看到这个世界也是亮晶晶的', '2019-08-25 15:11:22');
INSERT INTO `tb_advice` VALUES (7, '以学习的目的直接复制的 言曌博客 的页面，原博客在下方', '2019-08-25 15:28:07');

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `article_title` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `article_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `category_id` int(11) NOT NULL COMMENT '文章类别',
  `article_views` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  `article_tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签',
  `article_like_count` int(11) NULL DEFAULT 0 COMMENT '点赞量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `article_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `article_state` int(2) NULL DEFAULT NULL COMMENT '文章状态',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  INDEX `fk_category`(`category_id`) USING BTREE,
  CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `tb_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES (1, 1, '我是一个标题123456', '<p>我是html区域的内容我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv我是测试内容Serv</p><p>123456</p>', 21, 1002, 'mybatis,spring,javascript', 100, '2019-07-24 03:30:29', '2019-07-27 00:02:38', '/blog/upload/user/1/2019072914460642530.jpg', 0);
INSERT INTO `tb_article` VALUES (5, 1, '我是一个测试标题修改vice444', '<p>我是测试内容Service444我是最后添加的内容2233</p>', 31, 2, 'java,php,Service', 0, '2019-07-24 21:22:02', '2019-07-26 23:10:38', '/blog/upload/user/1/2019072623103733504.jpg', 0);
INSERT INTO `tb_article` VALUES (12, 2, 'test12', '<p>请输入内容</p><p><img src=\"/blog/upload/user/1/2019072623103733504.jpg\" style=\"max-width:100%;\"><br></p>', 12, 8, 'spring', 0, '2019-07-26 00:32:29', '2019-07-26 00:32:29', '/blog/upload/user/1/2019072914460642530.jpg', 0);
INSERT INTO `tb_article` VALUES (57, 1, '我是最后添加的标题修改22331', '<p>我是最后添加的内容2233</p>', 31, 4, 'mybatis,spring,javascript', 0, '2019-07-29 13:41:58', '2019-07-29 14:46:07', '/blog/upload/user/1/2019072914460642530.jpg', 0);
INSERT INTO `tb_article` VALUES (58, 1, '我是581', '<p>我是最后添加的内容223344444444444444</p>', 31, 9, 'mybatis,spring,javascript', 0, '2019-08-16 01:42:32', '2019-08-16 01:42:33', '/blog/upload/user/1/2019072623103733504.jpg', 0);
INSERT INTO `tb_article` VALUES (59, 1, '我是59呀1  修改后', '<p>我是测试内容Service444我是最后添加的内容2233</p><p><img src=\"/blog/upload/user/1/2019073123193054659.png\" style=\"max-width:100%;\"><br></p>', 30, 39, 'spring,java', 0, '2019-08-16 01:43:50', '2019-08-27 11:13:52', '/blog/upload/user/1/2019080114092646525.jpg', 0);
INSERT INTO `tb_article` VALUES (60, 1, '关于JSTL你需要知道的', '<h1><span style=\"color: inherit;\">直接搜索jstl，，找到下载量最高的，下载1.2版本。</span></h1><h1>在<a href=\"https://mvnrepository.com/\" rel=\"nofollow noreferrer\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255);\">mvnrepository</a>中，&nbsp;&nbsp;</h1><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th>&nbsp;21</th><th>12&nbsp;</th></tr><tr><td>&nbsp;33</td><td>&nbsp;44</td></tr></tbody></table><ul><li>123123463546</li><li>fmt：格式化标签库</li><li>sql：数据库标签库（过时）</li><li>xml：xml标签库（过时）</li></ul><p><br></p><p><br></p><p><br></p>', 2, 27, 'spring,redis,linux', 0, '2019-08-18 14:32:38', '2019-08-18 15:09:27', '/blog/upload/user/1/2019081814323711716.jpg', 0);
INSERT INTO `tb_article` VALUES (62, 1, '关于JSTL你需要知道的2', '<p>在 直接搜索jstl，，找到下载量最高的，下载1.sdfnilbnvilsvnilvjnil&nbsp; n你我就女的你女一撒UI布偶过刘波2版本。<a href=\"https://mvnrepository.com/\" rel=\"nofollow noreferrer\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255);\">mvnrepository</a>中，&nbsp;&nbsp;</p><p><br></p><p><br></p><ul><li>123123463546</li><li>fmt：格式化标签库</li><li>sql：数据库标签库（过时）</li><li>xml：xml标签库（过时）</li></ul><p><br></p><p><br></p><h2>在 直接搜索jstl，，找到下载量最高的，下载1.sdfnilbnvilsvnilvjnil&nbsp; n你我就女的你女一撒UI布偶过刘波2版本。<a href=\"https://mvnrepository.com/\" rel=\"nofollow noreferrer\" target=\"_blank\">mvnrepository</a>中，&nbsp;&nbsp;&nbsp;&nbsp;</h2><p><br></p><p><br></p>', 11, 38, 'spring,redis,linux', 4, '2019-08-18 14:35:41', '2019-08-27 11:14:37', '/blog/upload/user/1/2019081814354131773.jpg', 0);
INSERT INTO `tb_article` VALUES (63, 1, '使用js操作checkbox', '<h2>一.使用原生JavaScript判断是否选中checkbox框</h2><pre><code>input type=\"checkbox\" id=\"test\" class=\"test\"&gt;同意<br>&lt;script&gt;<br>// 获取checkbox元素<br>var box=document.getElementById(\"test\"); <br>// 判断是否被拒选中，选中返回true，未选中返回false<br>alert(box.checked);<br>&lt;/script&gt;</code></pre><p><br></p><p><br></p><h3>可爱的龙猫</h3><p><br></p><p><img src=\"/blog/upload/user/1/2019081910253195506.jpg\" style=\"max-width:100%;\"><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p>', 4, 423, 'spring', 16, '2019-08-19 10:26:39', '2019-08-19 10:26:39', '/blog/upload/user/1/2019081910263862194.jpg', 0);

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父类别ID',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (2, 'Java开发', '2019-07-22 05:31:03', 0);
INSERT INTO `tb_category` VALUES (3, 'Mysql上手', '2019-07-22 05:31:25', 0);
INSERT INTO `tb_category` VALUES (4, '资源分享', '2019-07-22 05:31:54', 0);
INSERT INTO `tb_category` VALUES (5, '网页基础', '2019-08-11 05:37:25', 0);
INSERT INTO `tb_category` VALUES (6, '移动端', '2019-08-11 05:40:32', 0);
INSERT INTO `tb_category` VALUES (11, 'spring', '2019-07-29 11:29:41', 2);
INSERT INTO `tb_category` VALUES (12, 'Javase', '2019-08-11 05:35:44', 2);
INSERT INTO `tb_category` VALUES (20, '图片分享', '2019-07-29 12:55:15', 4);
INSERT INTO `tb_category` VALUES (21, '视频分享', '2019-07-30 10:13:17', 4);
INSERT INTO `tb_category` VALUES (30, 'html', '2019-08-11 05:39:11', 5);
INSERT INTO `tb_category` VALUES (31, 'css', '2019-08-11 05:39:24', 5);
INSERT INTO `tb_category` VALUES (41, 'android', '2019-08-11 05:40:52', 6);

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `from_id` int(11) NULL DEFAULT NULL COMMENT '评论用户id',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  `to_id` int(11) NULL DEFAULT NULL COMMENT '评论目标用户id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '回复id',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (7, '我是评论的内容', 1, 63, '2019-08-20 23:34:00', NULL, NULL);
INSERT INTO `tb_comment` VALUES (8, '我是回复1的内容', 2, 63, '2019-08-21 08:22:09', 1, 7);
INSERT INTO `tb_comment` VALUES (9, '龙猫好看', 2, 63, '2019-08-21 02:40:04', NULL, NULL);
INSERT INTO `tb_comment` VALUES (10, '我是回复test的内容', 1, 63, '2019-08-21 06:34:13', 2, 9);
INSERT INTO `tb_comment` VALUES (11, '我是测试的小咸鱼', 12, 63, '2019-08-21 07:40:19', NULL, NULL);
INSERT INTO `tb_comment` VALUES (55, 'kkkkkkkkkkkkk', 1, 63, '2019-08-22 17:05:15', 12, 11);
INSERT INTO `tb_comment` VALUES (56, '再次评论龙猫好看', 2, 63, '2019-08-22 17:07:02', NULL, NULL);
INSERT INTO `tb_comment` VALUES (60, 'ffffffffffffffffffff ', 1, 62, '2019-08-24 19:37:17', NULL, NULL);
INSERT INTO `tb_comment` VALUES (61, 'ffffffffffffffffffff ffffffffffff', 1, 62, '2019-08-24 19:37:19', NULL, NULL);
INSERT INTO `tb_comment` VALUES (62, '1111111111111111111111111', 1, 62, '2019-08-24 19:37:23', NULL, NULL);
INSERT INTO `tb_comment` VALUES (63, '222222222222222222', 1, 62, '2019-08-24 19:37:27', NULL, NULL);
INSERT INTO `tb_comment` VALUES (64, '33333333333333', 1, 62, '2019-08-24 19:37:29', NULL, NULL);
INSERT INTO `tb_comment` VALUES (65, '4444444444444444', 1, 62, '2019-08-24 19:37:32', NULL, NULL);
INSERT INTO `tb_comment` VALUES (66, '55555555555555', 1, 62, '2019-08-24 19:37:35', NULL, NULL);
INSERT INTO `tb_comment` VALUES (67, '66666666666666666', 1, 62, '2019-08-24 19:37:36', NULL, NULL);
INSERT INTO `tb_comment` VALUES (68, '777777777777777777777777', 1, 62, '2019-08-24 19:37:39', NULL, NULL);
INSERT INTO `tb_comment` VALUES (69, '你是撒比吗', 2, 62, '2019-08-25 08:59:17', 1, 63);

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line`  (
  `line_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '头条ID',
  `line_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头条名称',
  `line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头条链接',
  `line_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头条图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '头条创建时间',
  PRIMARY KEY (`line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
INSERT INTO `tb_head_line` VALUES (3, '我是一个头条修改', 'https://www.bilibili.com/', 'https://i0.hdslb.com/bfs/album/9dbd56966a7fa7bf8fdca1dd4f800c52001db2a5.png', '2019-07-31 18:54:39');
INSERT INTO `tb_head_line` VALUES (5, '数码宝贝', '456465', '/blog/upload/user/1/2019081816053015039.jpg', '2019-07-31 23:14:08');
INSERT INTO `tb_head_line` VALUES (6, '我是我自己', '165465', '/blog/upload/user/1/2019073123193054659.png', '2019-07-31 23:19:31');
INSERT INTO `tb_head_line` VALUES (8, '我是乔巴', '哈哈', '/blog/upload/user/1/2019080114102658531.jpg', '2019-08-01 14:09:26');
INSERT INTO `tb_head_line` VALUES (9, '我是阿狸', 'https://www.bilibili.com/', '/blog/upload/user/1/2019080114125013835.gif', '2019-08-01 14:09:58');

-- ----------------------------
-- Table structure for tb_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_tags`;
CREATE TABLE `tb_tags`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tags
-- ----------------------------
INSERT INTO `tb_tags` VALUES (2, 'mybatis', '2019-07-13 12:04:41');
INSERT INTO `tb_tags` VALUES (3, 'spring', '2019-07-22 05:32:35');
INSERT INTO `tb_tags` VALUES (4, 'javascript', '2019-07-22 05:32:57');
INSERT INTO `tb_tags` VALUES (5, 'redis', '2019-08-11 13:40:10');
INSERT INTO `tb_tags` VALUES (6, 'linux', '2019-08-11 13:40:21');
INSERT INTO `tb_tags` VALUES (7, 'mevan', '2019-08-11 13:40:38');
INSERT INTO `tb_tags` VALUES (8, 'java', '2019-08-13 07:03:28');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子邮箱',
  `user_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_type` int(2) NULL DEFAULT 0 COMMENT '用户类型',
  `user_explain` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  UNIQUE INDEX `uk_user_email`(`user_email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1, 'admin', '626532990', '13619077004@163.com', '/blog/upload/user/1/2019080114095840822.jpg', 0, '嘿嘿嘿我是管理员', '2019-07-28 02:48:01');
INSERT INTO `tb_user_info` VALUES (2, 'test', 'test', '123456789@qq.com', '/blog/upload/user/2/2019081820182058862.gif', 1, '', NULL);
INSERT INTO `tb_user_info` VALUES (5, '测试用户1', '123456', '123@qq.com', '/blog/upload/user/2/2019081820182058862.gif', 1, '无', '2019-08-01 17:02:27');
INSERT INTO `tb_user_info` VALUES (6, '测试用户2', '1221344', '454454', 'http:8081/123', 1, NULL, NULL);
INSERT INTO `tb_user_info` VALUES (12, '测试小咸鱼', '7h81520W0X1t', '626532990@qq.com', '/blog/upload/user/1/2019080114095840822.jpg', 1, NULL, '2019-08-04 10:50:17');

SET FOREIGN_KEY_CHECKS = 1;
