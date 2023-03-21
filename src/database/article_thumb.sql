create table if not exists article_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    articleId  bigint                              not null comment '文章id',
    userId     bigint                              not null comment '创建用户id',
    createTime timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime  default null comment '更新时间',
    index idx_articleID (articleId),
    index idx_userID (userId)
) comment '帖子点赞';