-- 创建库
create database if not exists shuo_api;

-- 切换库
use shuo_api;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;


-- 接口信息
create table if not exists `interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `requestParams` text NULL COMMENT '请求参数示例',
    `responseParams` text NULL COMMENT '响应参数示例',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

INSERT INTO `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`, `createTime`, `updateTime`, `isDelete`) VALUES
-- 开启状态的接口 (status=1)
('用户登录', '通过账号密码获取访问令牌', '/api/user/login', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 1, 'POST', 1, '2023-01-01 09:00:00', '2023-01-01 09:00:00', 0),
('获取用户信息', '根据用户ID查询详细信息', '/api/user/get', '{"Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'GET', 1, '2023-01-02 10:00:00', '2023-01-02 10:00:00', 0),
('创建订单', '提交商品生成新订单', '/api/order/create', '{"Content-Type":"application/json","Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'POST', 2, '2023-01-03 11:00:00', '2023-01-03 11:00:00', 0),
('支付接口', '调用第三方支付平台', '/api/pay/request', '{"Content-Type":"application/x-www-form-urlencoded"}', '{"Content-Type":"text/html"}', 1, 'POST', 3, '2023-01-04 12:00:00', '2023-01-04 12:00:00', 0),
('商品列表', '分页查询商品数据', '/api/product/list', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 1, 'GET', 2, '2023-01-05 13:00:00', '2023-01-05 13:00:00', 0),

-- 关闭状态的接口 (status=0)
('旧版登录接口', '已废弃的MD5加密登录', '/api/v1/login', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 0, 'POST', 5, '2022-12-01 14:00:00', '2023-01-10 14:30:00', 0),
('短信发送', '运营商维护暂停服务', '/api/sms/send', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 0, 'POST', 4, '2023-01-06 15:00:00', '2023-01-08 09:00:00', 0),
('文件上传', '系统升级临时关闭', '/api/file/upload', '{"Content-Type":"multipart/form-data"}', '{"Content-Type":"application/json"}', 0, 'PUT', 3, '2023-01-07 16:00:00', '2023-01-09 10:00:00', 0),

-- 不同请求方法的接口
('删除用户', '根据ID逻辑删除用户', '/api/user/delete', '{"Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'DELETE', 1, '2023-01-08 17:00:00', '2023-01-08 17:00:00', 0),
('更新商品', '修改商品信息', '/api/product/update', '{"Content-Type":"application/json","Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'PUT', 2, '2023-01-09 18:00:00', '2023-01-09 18:00:00', 0),

-- 特殊Header的接口
('微信回调', '处理微信支付回调', '/api/wechat/notify', '{"Content-Type":"text/xml"}', '{"Content-Type":"text/xml"}', 1, 'POST', 5, '2023-01-10 19:00:00', '2023-01-10 19:00:00', 0),
('文件下载', '通过文件ID下载资源', '/api/file/download', '{"Accept":"application/octet-stream"}', '{"Content-Type":"application/octet-stream"}', 1, 'GET', 4, '2023-01-11 20:00:00', '2023-01-11 20:00:00', 0),

-- 不同用户的创建记录
('权限列表', '获取所有权限节点', '/api/permission/list', '{"Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'GET', 10, '2023-01-12 21:00:00', '2023-01-12 21:00:00', 0),
('角色分配', '给用户分配角色', '/api/role/assign', '{"Content-Type":"application/json","Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'POST', 8, '2023-01-13 22:00:00', '2023-01-13 22:00:00', 0),

-- 已删除的接口 (isDelete=1)
('测试接口1', '已删除的测试接口', '/api/test/1', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 0, 'GET', 1, '2022-11-01 08:00:00', '2022-12-01 08:00:00', 1),
('测试接口2', '压力测试专用接口', '/api/stress/test', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 0, 'POST', 2, '2022-11-05 09:00:00', '2022-12-05 09:00:00', 1),

-- 混合场景
('天气查询', '查询城市天气信息', '/api/weather', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 1, 'GET', 7, '2023-01-14 23:00:00', '2023-01-14 23:00:00', 0),
('IP定位', '根据IP获取地理位置', '/api/location/ip', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', 1, 'POST', 6, '2023-01-15 00:00:00', '2023-01-15 00:00:00', 0),
('验证码生成', '生成图形验证码', '/api/captcha', '{"Content-Type":"application/json"}', '{"Content-Type":"image/png"}', 1, 'GET', 5, '2023-01-16 01:00:00', '2023-01-16 01:00:00', 0),
('数据统计', '获取系统运行指标', '/api/metrics', '{"Authorization":"Bearer token"}', '{"Content-Type":"application/json"}', 1, 'GET', 9, '2023-01-17 02:00:00', '2023-01-17 02:00:00', 0);

