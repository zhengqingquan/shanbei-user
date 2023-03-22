create table user
(
    id           bigint unsigned auto_increment comment '用户id'
        primary key,
    username     varchar(256)                        not null comment '用户昵称',
    userAccount  varchar(256)                        not null comment '账号',
    userPassword varchar(512)                        not null comment '用户密码',
    avatarUrl    varchar(1024)                       null comment '头像',
    introduction varchar(1024)                       null comment '简介',
    gender       tinyint                             null comment '性别',
    phone        varchar(128)                        null comment '电话',
    email        varchar(512)                        null comment '邮箱',
    tags         varchar(1024)                       null comment '用户标签（Json格式）',
    userRole     int                                 null comment '用户角色',
    userStatus   int       default 0                 not null comment '状态',
    createTime   datetime  default CURRENT_TIMESTAMP not null comment '创建时间。默认当前时间。',
    updateTime   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间。',
    isDelete     tinyint   default 0                 not null comment '逻辑删除'
)
    comment '用户表';

