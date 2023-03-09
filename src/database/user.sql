create table user
(
    id           bigint unsigned auto_increment
        primary key,
    username     varchar(256)                        not null comment '用户昵称',
    userAccount  varchar(256)                        null comment '账号',
    avatarUrl    varchar(1024)                       null comment '头像',
    gender       tinyint                             null,
    userPassword varchar(512)                        not null,
    phone        varchar(128)                        null,
    email        varchar(512)                        null,
    userStatus   int       default 0                 not null comment ' 状态',
    createTime   datetime                            null comment '创建时间。默认当前时间。',
    updateTime   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint   default 0                 not null,
    userRole     int       default 0                 not null comment '用户角色
0：普通用户
1：管理员'
)
    comment '用户表';

