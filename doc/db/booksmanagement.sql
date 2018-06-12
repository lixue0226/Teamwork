/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : 127.0.0.1:3306
Source Database       : booksmanagement

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-12-20 13:02:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminid` int(11) NOT NULL AUTO_INCREMENT,
  `apassword` varchar(255) CHARACTER SET utf8 NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`adminid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'a3d5a7fd32f580a0c3a33348a37c8a58', 'manage');

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `bookid` int(255) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `authorname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('1', '骆驼祥子', '老舍', '6');
INSERT INTO `books` VALUES ('3', 'sd', 'dsf', '2');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `borrowid` int(11) NOT NULL AUTO_INCREMENT,
  `sno` int(11) NOT NULL,
  `borrowidbooktime` datetime NOT NULL,
  `returnbooktime` datetime DEFAULT NULL,
  `bookid` int(11) NOT NULL,
  PRIMARY KEY (`borrowid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('1', '1', '2017-07-07 00:00:00', '2017-07-29 00:00:00', '1');
INSERT INTO `borrow` VALUES ('2', '1', '2017-07-29 00:00:00', '2017-07-29 00:00:00', '3');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sno` int(20) NOT NULL AUTO_INCREMENT,
  `sname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `department` varchar(255) CHARACTER SET utf8 NOT NULL,
  `class` varchar(255) CHARACTER SET utf8 NOT NULL,
  `spassword` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', 'xiaoming', '软件学院', '1', 'a3d5a7fd32f580a0c3a33348a37c8a58');
INSERT INTO `student` VALUES ('2', 'qqqq', 'qqqtt', 'qqqqq', '60f5578f0a2e142b58fe31eb5ce6d670');
