# CineBase 部署指南 — Netlify + Railway

## 最终效果

```
用户打开浏览器 → Netlify 域名
                    │
                    ├── 返回 Vue 前端页面（HTML/CSS/JS）
                    │
                    └── JS 发 API 请求 → Railway 后端
                                          │
                                          └── 读写 Railway MySQL 数据库
```

---

## 第 0 步：把代码推送到 GitHub

> 两个部署平台都需要从 GitHub 拉代码。如果你已经有 GitHub 仓库，跳到 0.3。

### 0.1 创建 GitHub 仓库

1. 浏览器打开 https://github.com ，登录你的账号
2. 点击右上角 **头像旁边的 + 号** → **New repository**
3. 填写：
   - **Repository name**：`movie-database`（和本地项目名一致）
   - **Description**：选填
   - **Public / Private**：选 Public（免费；Private 也免费但需要额外权限设置）
   - **不要**勾选 "Add a README file"、"Add .gitignore"、"Choose a license"（本地已有代码）
4. 点击绿色的 **Create repository** 按钮
5. 创建后页面会显示"Quick setup"命令行指引，先留着这页别关

### 0.2 推送本地代码到 GitHub

打开 Git Bash，逐条执行以下命令：

```bash
# 进入项目根目录
cd /c/Users/wucan/IdeaProjects/movie-database

# 初始化 Git（如果还没初始化）
git init

# 添加所有文件到暂存区
git add .

# 提交
git commit -m "Initial commit: CineBase movie database"

# 关联远程仓库（把下面的 YOUR_USERNAME 换成你的 GitHub 用户名）
git remote add origin https://github.com/YOUR_USERNAME/movie-database.git

# 推送到 GitHub
git branch -M main
git push -u origin main
```

推送成功后，刷新 GitHub 仓库页面，应该能看到所有代码文件。

---

## 第 1 步：注册 Railway 并创建 MySQL 数据库

### 1.1 注册 Railway

1. 浏览器打开 https://railway.app
2. 点击 **Start a New Project** 或 **Login**
3. 选择 **Login with GitHub**（用 GitHub 账号登录，后续自动拉代码）
4. 首次登录会要求授权 Railway 访问你的 GitHub，点 **Authorize Railway**

### 1.2 创建 MySQL 数据库

1. 登录后进入 Railway 控制台，点击右上角的 **New Project** 按钮
2. 在弹出的选项中选择 **Deploy a database** 或 **Database**
3. 选择 **MySQL** 点击创建
4. 等待几秒，MySQL 实例创建完成
5. 点击进入这个 MySQL 服务的详情页
6. 点击顶部 **Variables** 标签页，你会看到 Railway 自动生成的 5 个变量：

   | 变量名 | 示例值 | 含义 |
   |--------|--------|------|
   | `MYSQLHOST` | `containers-us-west-xx.railway.app` | 数据库地址 |
   | `MYSQLPORT` | `3306` | 端口 |
   | `MYSQLUSER` | `root` | 用户名 |
   | `MYSQLPASSWORD` | `随机密码` | 密码 |
   | `MYSQLDATABASE` | `railway` | 数据库名 |

7. **把 `MYSQLHOST`、`MYSQLPORT`、`MYSQLUSER`、`MYSQLPASSWORD`、`MYSQLDATABASE` 这 5 个值复制保存下来**，下一步要用。

> ⚠️ 密码只会显示一次，从 Variables 标签可以再看到。Railway 用星号遮蔽，点一下可以显示明文。

### 1.3 初始化数据表

SQL 文件在项目中：`backend/src/main/resources/sql/schema.sql` 和 `data.sql`

**方式一：Railway 自带的 CLI（推荐）**

1. Railway 控制台找到 MySQL 服务
2. 点击 **Connect** 标签
3. 复制 "Railway CLI" 那行的命令，在 Git Bash 中执行（只需要执行一次即可连接）
4. 或者直接用 Public Network 的连接信息，用本地 MySQL 客户端连接

**方式二：用本地 MySQL 客户端连接远程数据库**

1. 打开任意 MySQL 图形化工具（如 DBeaver、Navicat、DataGrip）
2. 新建连接，填入上一步保存的 5 个值：
   - Host：`MYSQLHOST` 的值
   - Port：`3306`
   - User：`MYSQLUSER` 的值（通常是 `root`）
   - Password：`MYSQLPASSWORD` 的值
   - Database：`MYSQLDATABASE` 的值（通常是 `railway`）
3. 连接成功后，打开 SQL 编辑器
4. 分别打开 `backend/src/main/resources/sql/schema.sql` 和 `data.sql`，复制全部 SQL 并执行
5. 执行完成后，刷新表列表，应该能看到 `movie`、`user`、`genre`、`review` 等十几张表

---

## 第 2 步：部署后端到 Railway

### 2.1 创建后端服务

1. 回到 Railway 项目页面（就是你刚才创建 MySQL 的那个 Project）
2. 点击右上角的 **New** → **Service** → **Git Repository**
3. 在 GitHub 仓库列表中找到 `movie-database`，点击选择
4. Railway 自动检测到根目录的 `Dockerfile`，会开始自动构建
5. 构建过程需要 3-8 分钟（首次构建较慢，Maven 要下载依赖）

### 2.2 关联 MySQL 数据库

1. 构建完成后，在服务详情页的右半部分，能看到"Variables"面板
2. 点击 **Add Variable** 或者直接找 **Add Reference**
3. 搜索 `MYSQL`，勾选所有 5 个 MySQL 变量（MYSQLHOST, MYSQLPORT, MYSQLUSER, MYSQLPASSWORD, MYSQLDATABASE）
4. Railway 会自动把它们注入到这个后端服务中

### 2.3 添加自定义环境变量

在同样的 Variables 面板中，点击 **New Variable**：

| Name | Value | 说明 |
|------|-------|------|
| `SPRING_PROFILES_ACTIVE` | `prod` | 激活生产配置 |
| `JWT_SECRET` | `Cin3B4se-2026-!Secure-Jwt-R4ilw4y-Deploy` | JWT 签名密钥（你自己编一个，越长越好） |

> ⚠️ JWT_SECRET 决定了登录 token 的安全性，别用我上面这个例子，自己随便输入长一点的随机字符串，比如键盘乱敲一段英文。

### 2.4 等待重新部署

1. 添加环境变量后，Railway 会自动触发重新部署（环境变量变了就得重建）
2. 等待日志显示 `Started MovieDatabaseApplication in X seconds`
3. 部署成功后，点击服务卡片右上角的 **域名链接**（类似 `https://movie-database.up.railway.app`）
4. **把这个域名完整复制下来**，下一步 Netlify 要用

### 2.5 验证后端是否正常

浏览器打开：`https://你的Railway域名/movie/page?page=1&size=2`

- ✅ 如果返回 JSON 数据（电影列表），后端部署成功
- ❌ 如果返回错误或打不开，点击 Railway 服务的 **Deployments** 标签，查看最新部署的 Logs

---

## 第 3 步：部署前端到 Netlify

### 3.1 注册 Netlify

1. 浏览器打开 https://www.netlify.com
2. 点击 **Sign up** → 选择 **GitHub** 登录
3. 授权 Netlify 访问你的 GitHub 仓库

### 3.2 导入项目并配置构建

1. 登录后，点击 **Add new site** → **Import an existing project**
2. 选择 **GitHub**，在列表中找到 `movie-database`
3. 进入配置页面，在 **Basic build settings** 中填写：

   | 字段 | 填写内容 |
   |------|---------|
   | **Owner** | 你的 GitHub 用户名（自动填充） |
   | **Branch to deploy** | `main` |
   | **Base directory** | `frontend` |
   | **Build command** | `npm run build` |
   | **Publish directory** | `dist` |

4. 点击 **Show advanced** 按钮（在 Build settings 下方）
5. 在 **Environment variables** 区域，点击 **New variable**：

   | Key | Value |
   |-----|-------|
   | `VITE_API_BASE_URL` | `https://你的Railway域名` |

   > ⚠️ 举例：如果你的 Railway 域名是 `movie-database-production.up.railway.app`，就填 `https://movie-database-production.up.railway.app`
   > ⚠️ **末尾不要加斜杠 `/`**，错了会 404

6. 点击底部的 **Deploy movie-database** 按钮

### 3.3 等待构建

1. 页面跳转到部署详情页，显示构建日志
2. 构建大约需要 1-2 分钟，日志会显示：
   ```
   npm run build
   vite build
   ✓ built in XXs
   ```
3. 构建成功后，页面显示 "Published" + 一个绿色链接
4. 点击这个链接，进入你的网站

> 默认域名格式：`https://xxx-xxx-xxx.netlify.app`
> 你也可以在 Site settings → Domain management 中修改子域名前缀，比如改成 `cinebase.netlify.app`

---

## 第 4 步：验证登录注册功能

打开 Netlify 给你的域名：

1. 点击右上角 **登录** 按钮
2. 点击 **立即注册**
3. 填写：
   - 用户名：`test`
   - 昵称：`测试用户`
   - 邮箱：`test@test.com`
   - 密码：`123456`
4. 点 **注册**，成功后会自动登录
5. 你会看到自己的昵称出现在导航栏右上角

如果注册成功，说明整个链路（Netlify → Railway → MySQL）全部打通了！🎉

---

## 第 5 步：登录管理员账号

数据库初始化时已经预置了管理员账号：

| 字段 | 值 |
|------|-----|
| 用户名 | `admin` |
| 密码 | `admin123` |

1. 退出测试用户的登录
2. 用 `admin` / `admin123` 登录
3. 你会看到导航栏出现 **管理后台** 入口
4. 进入后台可以增删改电影、管理用户、发布资讯等

---

## 日常维护

### 修改前端代码后更新

```bash
git add .
git commit -m "更新前端"
git push
```

Netlify 会自动检测到 GitHub 有新提交，自动重新构建部署。

### 修改后端代码后更新

```bash
git add .
git commit -m "更新后端"
git push
```

Railway 会自动检测到 GitHub 有新提交，自动重新构建 Docker 镜像并部署。

### 查看后端日志

1. 打开 Railway 控制台
2. 点击后端服务 → **Deployments** 标签 → 点击最新一条部署记录
3. 查看实时日志，排查问题

---

## 常见问题排查

| 现象 | 可能原因 | 解决 |
|------|---------|------|
| Netlify 构建报错 | `frontend` 路径没配对 | 检查 Base directory 是否填 `frontend` |
| 网页打开白屏 | VITE_API_BASE_URL 没设置 | Netlify → Site settings → Environment variables |
| 登录/注册提示网络错误 | VITE_API_BASE_URL 末尾多了一个 `/` | 改成 https://域名 不要末尾 `/` |
| 后端日志报 MySQL 连接拒绝 | MySQL 环境变量没关联 | Railway → 后端服务 → Variables → 给 MYSQL* 变量添加 Reference |
| 后端启动成功但 API 返回空 | 数据库没初始化 | 执行 schema.sql 和 data.sql |
| 管理员密码错误 | 数据库初始化时用了不同密码 | 用测试用户注册，或者直接查数据库 user 表 |
