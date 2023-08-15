create table `user-manager`.user
(
    id            bigint auto_increment
        primary key,
    username      varchar(256)                       null comment '用户名称',
    user_account  varchar(256)                       null comment '账户',
    avatar_url    varchar(1024)                      null comment '头像',
    gender        tinyint                            null comment '性别',
    user_password varchar(256)                       not null comment '密码',
    email         varchar(512)                       null comment '邮箱',
    user_status   int      default 0                 not null comment '状态 0-正常',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    is_delete     tinyint  default 0                 not null comment '是否删除',
    user_role     int      default 0                 not null comment '用户角色 0-普通用户，1-管理员'
)
    comment '用户';
