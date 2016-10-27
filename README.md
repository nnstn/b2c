# b2c
商城项目，仿京东网站

b2c-parent    商城父目录

b2c-common    公共库文件包

b2c-manager   商城后台管理  http://192.168.128.128:10102/

开发运行：clean tomcat7:run
工程安装：clean install -DskipTests
工程发布：clean tomcat7:redeploy -DskipTests

  b2c-manager-dao
  
  b2c-manager-pojo
  
  b2c-manager-service
  
  b2c-manager-web
  
b2c-portal 商城前台

b2c-search 商城搜索系统

b2c-rest  商城前台服务

b2c-sso    单点登录系统，session共享

b2c-order  订单系统,和钱相关所以不能使用缓存，缓存有可能会丢数据

fastdfs-client 文件服务器客户端

generatorSqlmapCustom  mybatis映射初始化


