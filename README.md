# blog-dubbo
 
 ##介绍
 本框架是平常随便搭建的，记录自己在其他项目中学到了好用的架构知识和编码规范。
 这其中也参考了很多大牛的，感谢这些开源世界大大么的分享。
 
 ##工程介绍
 1：hy-blog-app-log 日志收集服务（使用kafka，没有对外暴露接口，只处理不同topic的日志写入）
 
 2：hy-blog-app-notify 通知接偶服务（使用rockrtmq和kafka，没有对外暴露接口，只处理消息，比如发信息，发邮件等）
 
 3：hy-blog-common-base 基础jar包（代码归类，底层的类，静态方法等，保证代码整洁性）
 
 4：hy-blog-common-biz 业务处理分装jar包（把底层业务分装，对上层暴露）
 
 4：hy-blog-common-config 配置类jar包（统一配置文件管理）
 
 5：hy-blog-service-notify 通知服务类（通知业务提供方）
 
 6：hy-blog-service-message 与外部交互服务（和外部相互调用工程（比如调用微信接口，请求，返回可能都需要入库））
 
 7：hy-blog-service-user 用户服务（））
 
 8：hy-blog-service-vip 会员体系服务
 
 9：hy-blog-web-boss 后台管理平台服务（响应请求）
 
 10：hy-blog-web-timer 定时服务器服务（响应请求）
 
 11：hy-blog-web-weixin 处理微信服务（闲来无事，写的二次开发微信公众号的服务）
 
 ...
 
 
 ##架构
 springboot + dubbo + zookeeper/nacos + mysql + redis + kafka + rocketmq
 
 后面有空会给分布式事物加上（TCC）
 
 
 坑点一：dubbo如果用注解暴露服务，则没办法开启本事事物   需要配置：aop:
           proxy-target-class: true，所以其他的服务都用xml配置暴露dubbo
           
 坑点二：我搭建的时候，使用的spring 1.5.10版本，所以后期想使用阿里的nacos作为配置中心时，一只没成功
 ，官方建议是2.0版本以上。
