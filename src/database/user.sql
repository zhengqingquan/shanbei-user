DROP TABLE IF EXISTS user;

CREATE TABLE `shanbei`.`user` (
  `id` BIGINT UNSIGNED auto_increment NOT NULL,
  `username` VARCHAR(256) NOT NULL COMMENT '用户昵称',
  `userAccount` VARCHAR(256) NULL COMMENT '账号',
  `avatarUrl` VARCHAR(1024) NULL COMMENT '头像',
  `gender` TINYINT NULL,
  `userPassword` VARCHAR(512) NOT NULL,
  `phone` VARCHAR(128) NULL,
  `email` VARCHAR(512) NULL,
  `userStatus` INT NOT NULL DEFAULT 0 COMMENT ' 状态',
  `createTime` DATETIME NULL COMMENT '创建时间。默认当前时间。',
  `updateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
COMMENT = '用户表';

alter table user auto_increment = 1;