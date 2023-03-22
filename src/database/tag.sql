create table tag
(
    id         bigint unsigned auto_increment comment 'id'
        primary key,
    tagName    varchar(200)                        null comment '标签名称',
    userId     bigint                              null comment '标签 id',
    parentId   bigint                              null comment '父标签 id',
    isParent   tinyint                             null comment '是否为父标签 0-不是，1-是',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间。默认当前时间。',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint   default 0                 not null comment '是否删除',
    constraint idx_userId
        unique (userId),
    constraint uniIdx_tagName
        unique (tagName)
)
    comment '标签表';

