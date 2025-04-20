

-- 切换库
use shuo_api;




INSERT INTO `interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `requestParams`, `responseParams`, `status`, `method`, `userId`) VALUES
-- 1. 用户服务接口
('用户登录', '通过用户名密码登录获取token', '/api/user/login', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"username":"admin","password":"123456"}', '{"code":200,"data":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"},"message":"success"}', 1, 'POST', 1),

-- 2. 用户信息接口
('获取用户信息', '根据用户ID获取用户详细信息', '/api/user/info', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"userId":123}', '{"code":200,"data":{"userId":123,"username":"admin","email":"admin@example.com"},"message":"success"}', 1, 'GET', 1),

-- 3-5. 支付相关接口
('创建支付订单', '创建新的支付订单', '/api/payment/create', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"orderId":"ORD123456","amount":100.00,"currency":"CNY"}', '{"code":200,"data":{"paymentId":"PAY789012","status":"pending"},"message":"success"}', 1, 'POST', 2),
('查询支付状态', '查询支付订单状态', '/api/payment/status', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"paymentId":"PAY789012"}', '{"code":200,"data":{"status":"completed","amount":100.00},"message":"success"}', 1, 'GET', 2),
('退款接口', '对已支付订单发起退款', '/api/payment/refund', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"paymentId":"PAY789012","refundAmount":50.00}', '{"code":200,"data":{"refundId":"REF654321","status":"processing"},"message":"success"}', 1, 'POST', 2),

-- 6-8. 商品服务接口
('获取商品列表', '分页获取商品列表', '/api/product/list', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"page":1,"pageSize":10,"categoryId":5}', '{"code":200,"data":{"list":[{"id":1,"name":"商品1"},{"id":2,"name":"商品2"}],"total":100},"message":"success"}', 1, 'GET', 3),
('获取商品详情', '根据商品ID获取商品详情', '/api/product/detail', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"productId":123}', '{"code":200,"data":{"id":123,"name":"示例商品","price":99.99,"stock":50},"message":"success"}', 1, 'GET', 3),
('创建商品', '创建新的商品', '/api/product/create', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"name":"新商品","price":199.99,"stock":100}', '{"code":200,"data":{"productId":124},"message":"success"}', 1, 'POST', 3),

-- 9-11. 订单服务接口
('创建订单', '创建新的订单', '/api/order/create', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"products":[{"id":1,"quantity":2},{"id":2,"quantity":1}]}', '{"code":200,"data":{"orderId":"ORD654321","totalAmount":299.97},"message":"success"}', 1, 'POST', 4),
('查询订单', '根据订单ID查询订单详情', '/api/order/detail', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"orderId":"ORD654321"}', '{"code":200,"data":{"orderId":"ORD654321","status":"paid","products":[{"id":1,"quantity":2}]},"message":"success"}', 1, 'GET', 4),
('取消订单', '取消未支付的订单', '/api/order/cancel', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"orderId":"ORD654321"}', '{"code":200,"data":{"orderId":"ORD654321","status":"cancelled"},"message":"success"}', 1, 'POST', 4),

-- 12-14. 短信服务接口
('发送短信验证码', '发送短信验证码到指定手机号', '/api/sms/send', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"phone":"13800138000","templateId":"VERIFY"}', '{"code":200,"data":{"smsId":"SMS123456"},"message":"success"}', 1, 'POST', 5),
('验证短信验证码', '验证短信验证码是否正确', '/api/sms/verify', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{"phone":"13800138000","code":"123456"}', '{"code":200,"data":{"verified":true},"message":"success"}', 1, 'POST', 5),
('查询短信发送记录', '查询短信发送历史记录', '/api/sms/history', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"phone":"13800138000","page":1,"pageSize":10}', '{"code":200,"data":{"list":[{"smsId":"SMS123456","status":"delivered"}]},"message":"success"}', 1, 'GET', 5),

-- 15-17. 文件服务接口
('文件上传', '上传文件到服务器', '/api/file/upload', '{"Content-Type":"multipart/form-data"}', '{"Content-Type":"application/json"}', 'FormData: file=@example.jpg', '{"code":200,"data":{"fileId":"FILE789012","url":"https://example.com/files/example.jpg"},"message":"success"}', 1, 'POST', 6),
('文件下载', '根据文件ID下载文件', '/api/file/download', '{"Content-Type":"application/json"}', '{"Content-Type":"application/octet-stream"}', '{"fileId":"FILE789012"}', 'Binary file data', 1, 'GET', 6),
('文件删除', '删除服务器上的文件', '/api/file/delete', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"fileId":"FILE789012"}', '{"code":200,"data":{"deleted":true},"message":"success"}', 1, 'POST', 6),

-- 18-20. 其他服务接口
('获取系统时间', '获取服务器当前时间', '/api/system/time', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{}', '{"code":200,"data":{"timestamp":1630000000,"datetime":"2023-08-27 12:00:00"},"message":"success"}', 1, 'GET', 7),
('获取服务器状态', '获取服务器运行状态信息', '/api/system/status', '{"Content-Type":"application/json"}', '{"Content-Type":"application/json"}', '{}', '{"code":200,"data":{"cpu":30,"memory":45,"disk":60},"message":"success"}', 1, 'GET', 7),
('批量操作接口', '批量执行多个操作', '/api/batch/execute', '{"Content-Type":"application/json","Authorization":"Bearer {token}"}', '{"Content-Type":"application/json"}', '{"operations":[{"type":"create","data":{"name":"test"}}]}', '{"code":200,"data":{"results":[{"success":true}]},"message":"success"}', 1, 'POST', 7);