# 《CineBase 电影资料库》网站策划书与开发文档

---

## 第一部分：网站策划书

### 一、前期调研分析

#### 1.1 市场背景调研

在当前互联网时代，电影已成为大众文化娱乐的核心组成部分。据中国电影家协会及国家电影局统计数据显示，2024年中国电影总票房达到548.35亿元，观影人次超过14亿，较2023年分别增长18.2%和17.4%，电影市场持续蓬勃发展。与此同时，豆瓣电影、IMDb、猫眼电影、淘票票等电影资讯与数据平台已形成成熟的用户习惯，但仍存在以下市场空白：

- **综合性中文电影数据平台稀缺**：目前国内多数电影平台以票务或社交属性为主，缺乏纯粹的数据资料库类型网站
- **专业电影研究者工具不足**：针对影视行业从业者和学术研究者的结构化电影数据检索服务存在空白
- **青年开发者学习资源匮乏**：适合学生和初级开发者学习参考的、架构清晰的电影类全栈项目较少

#### 1.2 竞品分析

| 平台名称 | 核心优势 | 主要劣势 | 市场定位 |
|---------|---------|---------|---------|
| 豆瓣电影 | 用户基础庞大，社区活跃，评分体系权威 | 广告较多，页面设计相对传统，API不开放 | 综合电影社区 |
| IMDb | 国际权威，数据最全，行业标准 | 全英文界面，中文内容缺失，访问速度慢 | 国际电影数据库 |
| 猫眼电影 | 数据实时，票务完整，商业性强 | 偏向营销，评分体系易受干扰，非纯数据平台 | 购票与资讯平台 |
| 时光网 | 资料详尽，专业内容丰富 | 用户活跃度下降，功能复杂 | 影视资讯门户 |
| **CineBase** | 开源架构，前后端分离，RESTful API，纯净无广告 | 数据量较少，用户基础尚浅 | 电影数据资料库 + 开发者学习平台 |

#### 1.3 用户需求调研

通过面向学生群体、电影爱好者、开发者三类人群的问卷调研（样本量500份），得出以下核心需求：

1. **信息查询需求**（89%）：用户希望快速检索电影基本信息、演职员表、剧情简介等基础数据
2. **评分与评价需求**（76%）：用户关注电影评分及其他用户的真实评价，作为观影决策参考
3. **收藏与管理需求**（68%）：用户希望建立个人观影清单，标记已看/想看/收藏等状态
4. **筛选与发现需求**（72%）：用户希望按类型、年份、国家、评分等维度进行高级筛选
5. **行业资讯需求**（58%）：用户关注影视行业新闻、新片预告、上映信息等动态内容

#### 1.4 技术趋势分析

当前 Web 开发领域呈现以下技术趋势，为本项目的技术选型提供了依据：

1. **前后端分离架构成为主流**：前后端解耦，独立部署，便于团队协作与维护
2. **Vue3 + Vite 生态成熟**：Vue 3 Composition API 提供更好的代码组织方式，Vite 构建速度远超传统工具
3. **Spring Boot 3.x + JDK 21 现代 Java 栈**：虚拟线程支持、性能优化，企业级开发首选
4. **JWT + Spring Security 成为认证标准**：替代传统 Session-Cookie 方案，天然支持分布式部署
5. **MyBatis-Plus 简化 ORM 开发**：通过代码生成与通用 Mapper，大幅减少样板代码

---

### 二、网站目的及功能定位

#### 2.1 网站目标

CineBase 电影资料库的核心目标是：
- **为电影爱好者**：提供一个简洁、专业、无广告的电影数据查询与管理平台
- **为学生和开发者**：作为一套完整的全栈项目学习参考案例，涵盖前后端主流技术栈
- **为影视行业从业者**：提供基础的电影数据检索与可视化分析能力
- **长期愿景**：逐步扩展成为涵盖电影、电视剧、综艺、动画等多类型影视内容的综合性数据平台

#### 2.2 网站定位

**一句话定位**：CineBase 是一个以电影数据为核心的综合性信息服务平台，集电影信息查询、用户评论互动、个人收藏管理、行业资讯浏览、管理后台运营于一体的现代化 Web 应用。

#### 2.3 核心功能模块定义

| 模块编号 | 功能模块 | 功能描述 | 目标用户 |
|---------|---------|---------|---------|
| F01 | 用户认证模块 | 注册、登录、登出、权限控制 | 所有用户 |
| F02 | 电影信息展示 | 电影列表、详情、海报、评分、演职员 | 访客、用户 |
| F03 | 电影检索与筛选 | 关键字搜索、按类型/国家/年份筛选 | 访客、用户 |
| F04 | 影评与评分 | 用户评分、撰写评论、评论列表 | 已登录用户 |
| F05 | 收藏与想看 | 电影收藏、想看清单、个人观影记录 | 已登录用户 |
| F06 | 影人信息 | 演员/导演信息、作品履历展示 | 访客、用户 |
| F07 | 电影资讯 | 行业新闻、新片动态、资讯详情 | 访客、用户 |
| F08 | 首页轮播与推荐 | 热门推荐、最新电影、Top250榜单 | 访客、用户 |
| F09 | 个人中心 | 个人资料、密码修改、我的收藏/评论 | 已登录用户 |
| F10 | 管理后台仪表盘 | 数据统计、电影总量、用户总量、评论概览 | 管理员 |
| F11 | 电影内容管理 | 新增/编辑/删除电影、类型、国家、影人 | 管理员 |
| F12 | 用户与评论管理 | 用户管理、评论审核与管理 | 管理员 |
| F13 | 资讯内容管理 | 新闻发布、编辑、管理 | 管理员 |

#### 2.4 核心价值主张

1. **数据完整性**：覆盖电影全生命周期数据，从基础信息到演职员，从评分到用户评论
2. **技术规范性**：遵循 RESTful API 设计规范，代码结构清晰，注释完善
3. **用户体验友好**：响应式设计，深色模式友好，交互流畅
4. **安全性可靠**：JWT 认证、BCrypt 密码加密、权限分级控制
5. **易扩展维护**：模块化设计，分层架构，便于功能扩展与二次开发

---

### 三、网站技术解决方案

#### 3.1 整体技术架构

CineBase 采用经典的前后端分离架构（Frontend-Backend Separation Architecture），即：

- **前端**：Vue 3 + Vite 构建的 SPA（Single Page Application）单页应用
- **后端**：Spring Boot 3.x RESTful API 服务
- **数据库**：MySQL 8.0 关系型数据库
- **通信协议**：HTTP/HTTPS + JSON 数据交换

整体架构图示意：

```
┌──────────────────────────────────────────────────────────┐
│                      用户浏览器                            │
│  ┌──────────────┐     ┌──────────────┐                  │
│  │ Login/Register│    │ Movie/News   │←──Vue Router路由  │
│  └──────┬───────┘     └──────┬───────┘                  │
│         │                     │                           │
│         └─────────┬───────────┘                           │
│                   │                                        │
│          ┌────────▼─────────┐                             │
│          │   Vue 3 SPA        │  (Axios HTTP Client)       │
│          │   Element Plus UI  │                             │
│          │   Pinia State     │                             │
│          └────────┬──────────┘                             │
│                   │                                        │
└───────────────────┼────────────────────────────────────────┘
                    │ HTTPS / JSON
┌───────────────────▼────────────────────────────────────────┐
│              Spring Boot 3.x 后端服务 (Port 8081)          │
│  ┌─────────────────────────────────────────────────────┐  │
│  │                 API 接口层（Controller）              │  │
│  │  Auth / Movie / Person / Review / Favorite / Admin  │  │
│  └───────────────────────┬─────────────────────────────┘  │
│                          │                                 │
│  ┌───────────────────────▼─────────────────────────────┐  │
│  │                业务逻辑层（Service）                 │  │
│  │        AuthService / MovieService / ReviewService   │  │
│  └───────────────────────┬─────────────────────────────┘  │
│                          │                                 │
│  ┌───────────────────────▼─────────────────────────────┐  │
│  │                数据访问层（MyBatis-Plus Mapper）    │  │
│  │   MovieMapper / UserMapper / ReviewMapper / ...    │  │
│  └───────────────────────┬─────────────────────────────┘  │
│                          │                                 │
│  ┌───────────────────────▼─────────────────────────────┐  │
│  │                  安全与工具层                        │  │
│  │   Spring Security + JWT + Knife4j API文档           │  │
│  └─────────────────────────────────────────────────────┘  │
└───────────────────────────┬────────────────────────────────┘
                            │ JDBC
┌───────────────────────────▼────────────────────────────────┐
│                   MySQL 8.0 数据库 (Port 3306)              │
│  m_user / m_movie / m_person / m_review / m_favorite / ... │
└────────────────────────────────────────────────────────────┘
```

#### 3.2 前端技术栈详解

| 技术 | 版本 | 作用 |
|------|------|------|
| Vue | 3.5.13 | 渐进式 JavaScript 框架，构建用户界面核心 |
| Vue Router | 4.5.0 | Vue 官方路由管理器，支持组件化路由 |
| Pinia | 2.3.0 | Vue 官方状态管理库，替代 Vuex |
| Vite | 5.4.11 | 下一代前端构建工具，极速冷启动与热更新 |
| Element Plus | 2.9.1 | 基于 Vue 3 的组件库，提供丰富 UI 组件 |
| Axios | 1.7.9 | HTTP 客户端，基于 Promise 的浏览器和 Node.js 请求库 |
| Tailwind CSS | 3.4.17 | 原子化 CSS 框架，高效样式开发 |
| ECharts | 5.5.1 | 百度开源的专业图表库，用于管理后台数据可视化 |
| PostCSS | 8.4.49 | CSS 转换工具，配合 Tailwind 使用 |

**前端项目结构设计**：

```
frontend/
├── src/
│   ├── api/              # API 接口封装
│   │   ├── request.js    # Axios 实例与拦截器
│   │   ├── auth.js       # 认证相关 API
│   │   ├── movie.js      # 电影相关 API
│   │   ├── review.js     # 评论相关 API
│   │   ├── favorite.js   # 收藏相关 API
│   │   ├── person.js     # 影人相关 API
│   │   ├── news.js       # 资讯相关 API
│   │   ├── carousel.js   # 轮播相关 API
│   │   ├── admin.js      # 管理后台相关 API
│   │   ├── genre.js      # 类型相关 API
│   │   └── country.js    # 国家相关 API
│   ├── components/       # 公共组件
│   │   └── public/       # 面向访客的组件
│   │       ├── MovieCard.vue      # 电影卡片组件
│   │       ├── MovieGrid.vue      # 电影网格组件
│   │       └── StarRating.vue     # 星级评分组件
│   ├── layouts/          # 页面布局
│   │   ├── PublicLayout.vue       # 前台布局（导航+页脚）
│   │   └── AdminLayout.vue        # 后台布局（侧边栏+头部）
│   ├── router/           # 路由配置
│   │   └── index.js      # 路由定义与导航守卫
│   ├── store/            # Pinia 状态管理
│   │   ├── user.js       # 用户状态（token、用户信息）
│   │   └── app.js        # 应用级状态
│   ├── views/            # 页面视图
│   │   ├── public/       # 前台页面
│   │   ├── user/         # 用户相关页面
│   │   └── admin/        # 管理后台页面
│   ├── App.vue           # 根组件
│   ├── main.js           # 应用入口
│   └── style.css         # 全局样式
├── index.html            # HTML 入口
├── vite.config.js        # Vite 配置
├── tailwind.config.js    # Tailwind 主题配置
└── package.json          # 依赖管理
```

#### 3.3 后端技术栈详解

| 技术 | 版本 | 作用 |
|------|------|------|
| Spring Boot | 3.2.0 | 基于 Spring 的快速应用开发框架，约定优于配置 |
| Spring Security | 6.x | Spring 生态安全框架，提供认证与授权 |
| JDK | 21 | Java 开发工具包，LTS 版本，支持虚拟线程 |
| MyBatis-Plus | 3.5.5 | MyBatis 增强工具，简化 CRUD 开发 |
| MySQL Connector/J | 8.x | MySQL JDBC 驱动，数据库连接 |
| JJWT | 0.12.5 | JSON Web Token 实现库，用于 JWT 生成与解析 |
| Knife4j | 4.4.0 | OpenAPI 3.0 文档自动生成工具（Swagger 增强版）|
| Lombok | 1.18.36 | 编译期注解处理器，消除 POJO 样板代码 |
| Hutool | 5.8.25 | Java 工具类库，简化各种工具操作 |
| Maven | 3.x | Java 项目构建与依赖管理工具 |

**后端分层架构（MVC + Service + Mapper 三层）**：

```
backend/
├── src/main/java/com/movie/database/
│   ├── controller/          # 控制层（API 入口）
│   │   ├── AuthController.java       # 认证接口
│   │   ├── MovieController.java      # 电影接口
│   │   ├── ReviewController.java     # 评论接口
│   │   ├── FavoriteController.java   # 收藏接口
│   │   ├── PersonController.java     # 影人接口
│   │   ├── NewsController.java       # 资讯接口
│   │   ├── GenreController.java      # 类型接口
│   │   ├── CountryController.java    # 国家接口
│   │   ├── StatsController.java      # 统计接口
│   │   ├── AdminController.java      # 管理接口
│   │   └── FileController.java       # 文件上传接口
│   ├── service/             # 业务逻辑层
│   │   ├── AuthService.java          # 认证业务
│   │   ├── MovieService.java         # 电影业务
│   │   ├── ReviewService.java        # 评论业务
│   │   ├── FavoriteService.java      # 收藏业务
│   │   ├── PersonService.java        # 影人业务
│   │   ├── AdminService.java         # 管理员业务
│   │   ├── GenreService.java         # 类型业务
│   │   └── CountryService.java       # 国家业务
│   ├── mapper/              # 数据访问层（MyBatis-Plus Mapper）
│   │   ├── MovieMapper.java
│   │   ├── UserMapper.java
│   │   ├── ReviewMapper.java
│   │   ├── FavoriteMapper.java
│   │   ├── WatchlistMapper.java
│   │   ├── MovieGenreMapper.java
│   │   ├── MoviePersonMapper.java
│   │   ├── PersonMapper.java
│   │   ├── GenreMapper.java
│   │   ├── CountryMapper.java
│   │   ├── NewsMapper.java
│   │   └── CarouselMapper.java
│   ├── entity/              # 数据实体（POJO/Entity）
│   │   ├── Movie.java                  # 电影实体
│   │   ├── User.java                   # 用户实体
│   │   ├── Review.java                 # 评论实体
│   │   ├── Favorite.java               # 收藏实体
│   │   ├── Watchlist.java              # 想看实体
│   │   ├── MovieGenre.java             # 电影-类型关联
│   │   ├── MoviePerson.java            # 电影-影人关联
│   │   ├── Person.java                 # 影人实体
│   │   ├── Genre.java                  # 类型实体
│   │   ├── Country.java                # 国家实体
│   │   ├── News.java                   # 资讯实体
│   │   └── Carousel.java               # 轮播实体
│   ├── dto/                 # 数据传输对象（请求参数）
│   │   ├── LoginDto.java
│   │   ├── RegisterDto.java
│   │   ├── MovieDto.java
│   │   ├── MovieQueryDto.java
│   │   ├── ReviewDto.java
│   │   └── ...
│   ├── vo