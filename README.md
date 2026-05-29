# AwesomeVideo (filmandvideo)

短视频平台，包含微信小程序前端、Java 后端 API、后台管理系统三部分。

## 项目结构

| 目录 | 说明 | 技术栈 |
|---|---|---|
| `AwesomeVideoWxApp-master/` | 微信小程序 | 原生微信小程序开发 |
| `AwesomeVideo-master/` | 后端 API | Spring Boot 2.1.4 + MyBatis + MySQL + Redis |
| `AwesomeVideoAdmin-master/` | 后台管理系统 | Spring Boot 2.1.2 + Thymeleaf |
| `数据库-Dump20210521/` | 数据库 | MySQL 5.7 |

## 功能

- 短视频播放、上传
- 豆瓣影讯（电影/电视剧/综艺）
- 用户注册登录、个人中心
- 视频评论、点赞、关注
- 搜索（热词/历史记录）
- BGM 选择与合并
- 后台视频/用户管理

## 运行环境

- JDK 1.8
- Maven 3.x
- MySQL 5.7+
- Redis
- FFmpeg
- 微信开发者工具（小程序）

## 快速开始

### 1. 导入数据库

```bash
mysql -u root -p < 数据库-Dump20210521/awesome_video_*.sql
```

### 2. 配置

修改两个项目的 `src/main/resources/application.properties`，或通过环境变量覆盖：

| 环境变量 | 默认值 | 说明 |
|---|---|---|
| `DB_PASSWORD` | root | 数据库密码 |
| `VIDEO_UPLOAD_PATH` | /tmp/video-upload | 视频上传目录 |
| `FFMPEG_PATH` | /usr/local/bin/ffmpeg | FFmpeg 可执行文件路径 |

### 3. 启动后端

```bash
cd AwesomeVideo-master
mvn spring-boot:run
```

### 4. 启动后台管理

```bash
cd AwesomeVideoAdmin-master
mvn spring-boot:run
```

### 5. 启动小程序

微信开发者工具打开 `AwesomeVideoWxApp-master/` 目录。

## 技术栈详情

### 后端
- Spring Boot 2.1.4
- MyBatis + tk.mapper + PageHelper
- MySQL + Druid 连接池
- Redis
- Swagger2 API 文档
- FFmpeg 视频处理

### 管理后台
- Spring Boot 2.1.2
- Thymeleaf 模板引擎
- jQuery + Bootstrap

### 小程序
- 原生微信小程序
- 豆瓣 API（电影/电视/综艺数据）

## 免责声明

本项目为 2021 年个人学习项目。部分后端源码从 .class 文件反编译恢复。依赖版本较老，仅供学习参考。
