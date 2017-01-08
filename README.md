# moana  
	谢洽选 771350067@qq.com
	项目运行小提示：
		1. nginx-file目录并不是这个java应用里的目录，只是为了上传，将部署在nginx的文件放在此目录下
		2. 部署时，应将nginx-file目录下的src静态文件复制到运行机子的nginx目录下，并根据nginx.conf修改运行机的nginx.conf
		3. 数据库的账号密码等信息在src/main/resource/properties/database.properties中，请根据实际情况修改
		4. 要使用微博授权登录，应该去申请微博第三方的资格，并将client_ID,client_SERCRET等信息根据实际情况填到src/main/java/config.properties中
		5. 应用启动入口为IP/rush/login.html
	项目技术组件：
		1. 使用Spring+Spring MVC+Datanucles作为后端框架，展示页面使用BootStrap框架
		2. 服务器使用tomcat，用nginx作为代理服务器并处理静态资源（html/css/js)
		3. 数据库使用mariadb，内存数据库使用redis
		4. 使用maven管理项目
	项目运行流程：
		1. 使用微博登录系统
		2. 根据从微博获取到的微博ID检查此用户在本地数据库内是否有记录，没有则在数据库中新增一个用户
		3. 检查用户是否有绑定邮箱，如果没有，提示绑定
		4. 进入购票主页，显示剩余票数，利用js轮询服务器，查询剩余票数，并实时更新显示
		5. 用户点击购票，如果有余票，则在redis数据库中新增一条购买记录，并修改余票（修改操作要加锁),然后往用户绑定的邮箱发送一封购买成功的通知，并在网站页面上进行相应提示
		6. 售票结束后，将redis数据库中保存的数据持久化到mariadb数据库中