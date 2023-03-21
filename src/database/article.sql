# 数据库版本
# SELECT VERSION();
# 5.5.20
create table if not exists article
(
    id         bigint auto_increment comment '文章id' primary key,
    title      varchar(512)                        null comment '文章标题',
    content    text                                null comment '文章内容',
    tags       varchar(1024)                       null comment '标签列表（Json 数组）',
    thumbNum   int       default 0                 not null comment '点赞数',
    favourNum  int       default 0                 not null comment '收藏数',
    userId     bigint                              not null comment '该文章的创建者（用户id）',
    createTime timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime  default null comment '更新时间',
    isDelete   tinyint   default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '文章' collate = utf8mb4_unicode_ci