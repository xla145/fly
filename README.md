# fly

#### 项目介绍
主要是通过最近流行的springBoot2.0 实现fly社区的一些功能，目前fly-web项目已经开发完成，fly-sys项目处于开发阶段，后续会慢慢补充。

#### 使用说明

1. 通过git下载源码
2. 创建数据库fly，数据库编码为UTF-8
3. 执行document/fly.sql文件，初始化数据，insert.sql 用来添加文章分类信息
4. 修改application-dev.yml，更新MySQL地址，账号，密码 application-pro 是上线的配置信息
5. 运行方式采用jar方式运行  java -jar -Dserver.port=8081 -Dspring.profiles.active=pro hblog-web/target/hblog-web-1.0.0-exec.jar
6. 项目访问：http://localhost:8080/ 端口号，在配置文件中设置


#### 注意事项

1：项目中有一些个人封装的小工具，由于小工具都是单独一个项目，目前已经上传到阿里的私有仓库，所以需要修改maven的配置文件信息-路径 document/settings.xml
2：fly社区官网 https://fly.layui.com/ 里面涉及到的功能都基本完成，如果有问题可以提issue 

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request