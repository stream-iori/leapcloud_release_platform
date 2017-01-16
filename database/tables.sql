CREATE DATABASE IF NOT EXISTS leapcloud_release_platform;

USE leapcloud_release_platform;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(30),
`mail` varchar(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `release_type`;
CREATE TABLE `release_type` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(30),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `release_task`;
CREATE TABLE `release_task` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`release_type` int(11),
`title` varchar(40),
`project_location` varchar(80),
`project_desc` varchar(200),
`proposal` varchar(30),
`proposal_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
`update_time` DATETIME COMMENT '更新这个字段,当状态改变的时候',
`status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'project status,0:wait, 1:done, 2:fail, 3:discard',
`release_remark` varchar(150),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



--增加一个新的字段在ReleaseTask中


ALTER TABLE `leapcloud_release_platform`.`release_task` ADD COLUMN `tag` varchar(30) AFTER `release_remark`;
