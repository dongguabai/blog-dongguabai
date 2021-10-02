# ByteTCC Demo

官方地址：https://github.com/liuyangming/ByteTCC-sample

## 使用

1. 创建日志表

   ```sql
   DROP TABLE IF EXISTS `bytejta`;
   CREATE TABLE `bytejta`  (
     `xid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `gxid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `bxid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `ctime` bigint(20) NULL DEFAULT NULL,
     PRIMARY KEY (`xid`) USING BTREE
   ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
   ```

2. 启动 sample-eureka-server，访问：http://localhost:7000/ 验证

3. 启动 sample-provider，日志在 provider.log

4. 启动 sample-consumer

5. post 请求：localhost:7080/simplified/transfer?sourceAcctId=1001&targetAcctId=2001&amount=1。也就是从 `tb_account_one` 表中的 1001 号用户给 `tb_account_two` 表中的 2001 用户转 1 块钱；查看数据库发现数据变化正常，即表示项目基本使用成功

