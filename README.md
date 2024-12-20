+ # 音乐服务后端项目
+ 
+ ## 项目简介
+ 这是一个基于Spring Boot的音乐服务后端系统，提供用户管理、音乐播放、收藏、评论等功能。
+ 
+ ## 技术栈
+ - Spring Boot 2.7.18
+ - Spring Security + JWT认证
+ - MyBatis-Plus 3.5.5
+ - MySQL
+ - Redis
+ - Swagger/OpenAPI 3.0
+ 
+ ## 主要功能模块
+ 
+ ### 1. 用户管理
+ - 用户注册/登录
+ - 用户信息更新
+ - 头像上传
+ - 密码修改
+ - 用户关注/取消关注
+ - 粉丝列表/关注列表查看
+ 
+ ### 2. 音乐管理
+ - 歌曲列表查询
+ - 歌曲详情查看
+ - 歌曲搜索
+ - 播放记录管理
+ - 收藏管理
+ 
+ ### 3. 社交功能
+ - 评论发布/删除
+ - 用户关注系统
+ - 收藏系统
+ 
+ ### 4. 文件管理
+ - 支持音频文件上传(mp3, wav)
+ - 支持图片上传(jpg, png)
+ - 文件大小限制：10MB
+ 
+ ## API文档
+ 项目集成了Swagger文档，启动后访问：`http://localhost:8080/swagger-ui/`
+ 
+ ## 数据库设计
+ 
+ ### 用户表(user)
+ ```sql
+ CREATE TABLE user (
+     id BIGINT AUTO_INCREMENT PRIMARY KEY,
+     username VARCHAR(50) NOT NULL UNIQUE,
+     password VARCHAR(100) NOT NULL,
+     nickname VARCHAR(50),
+     avatar VARCHAR(255),
+     create_time DATETIME NOT NULL,
+     update_time DATETIME NOT NULL
+ );
+ ```
+ 
+ ### 歌曲表(song)
+ ```sql
+ CREATE TABLE song (
+     id BIGINT AUTO_INCREMENT PRIMARY KEY,
+     name VARCHAR(100) NOT NULL,
+     artist VARCHAR(50),
+     album VARCHAR(50),
+     duration INT,
+     url VARCHAR(255),
+     cover VARCHAR(255),
+     lyric TEXT
+ );
+ ```
+ 
+ ### 评论表(comment)
+ ```sql
+ CREATE TABLE comment (
+     id BIGINT AUTO_INCREMENT PRIMARY KEY,
+     user_id BIGINT NOT NULL,
+     song_id BIGINT NOT NULL,
+     content TEXT NOT NULL,
+     create_time DATETIME NOT NULL
+ );
+ ```
+ 
+ ## 配置说明
+ 
+ ### 应用配置
+ ```yaml
+ server:
+   port: 8080
+ 
+ spring:
+   datasource:
+     url: jdbc:mysql://localhost:3306/music_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
+     username: root
+     password: your-password
+     driver-class-name: com.mysql.cj.jdbc.Driver
+   
+   redis:
+     host: localhost
+     port: 6379
+     database: 0
+ 
+   servlet:
+     multipart:
+       max-file-size: 10MB
+       max-request-size: 10MB
+ ```
+ 
+ ## API认证说明
+ 
+ ### 1. 注册新用户
+ ```http
+ POST /api/auth/register
+ Content-Type: application/json
+ 
+ {
+     "username": "user123",
+     "password": "password123",
+     "nickname": "用户昵称"
+ }
+ ```
+ 
+ ### 2. 登录获取token
+ ```http
+ POST /api/auth/login
+ Content-Type: application/json
+ 
+ {
+     "username": "user123",
+     "password": "password123"
+ }
+ ```
+ 
+ ### 3. 使用token访问接口
+ ```http
+ GET /api/songs
+ Authorization: Bearer your-token-here
+ ```
+ 
+ ## 项目结构
+ ```
+ src/main/java/com/music/
+ ├── config/          # 配置类
+ │   ├── SecurityConfig.java
+ │   ├── SwaggerConfig.java
+ │   └── WebConfig.java
+ ├── controller/      # 控制器
+ │   ├── AuthController.java
+ │   ├── SongController.java
+ │   └── UserController.java
+ ├── service/         # 服务层
+ │   ├── UserService.java
+ │   └── SongService.java
+ ├── mapper/          # 数据访问层
+ ├── entity/          # 实体类
+ ├── dto/             # 数据传输对象
+ ├── common/          # 公共类
+ └── util/            # 工具类
+ ```
+ 
+ ## 如何运行
+ 1. 克隆项目
+ 2. 创建MySQL数据库：music_db
+ 3. 修改application.yml中的数据库配置
+ 4. 启动Redis服务
+ 5. 运行命令：`mvn spring-boot:run`
+ 
+ ## 开发环境要求
+ - JDK 8+
+ - Maven 3.6+
+ - MySQL 5.7+
+ - Redis 6.0+
+ 
+ ## 注意事项
+ - 生产环境部署前请修改JWT密钥
+ - 确保文件上传目录具有写入权限
+ - 建议使用HTTPS保护API调用
+ - 定期清理上传的临时文件 