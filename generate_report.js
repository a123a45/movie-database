const fs = require("fs");
const {
  Document, Packer, Paragraph, TextRun, Table, TableRow, TableCell,
  Header, Footer, AlignmentType, HeadingLevel, BorderStyle, WidthType,
  ShadingType, PageNumber, LevelFormat, PageBreak, TableOfContents
} = require("docx");

const outPath = "C:\\Users\\wucan\\Documents\\《网站设计与开发》课程《CineBase电影库》网站综合实验报告.docx";

// ── Helpers ──
const border = { style: BorderStyle.SINGLE, size: 1, color: "CCCCCC" };
const borders = { top: border, bottom: border, left: border, right: border };
const cellMargins = { top: 80, bottom: 80, left: 120, right: 120 };

function heading1(text) {
  return new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun(text)], spacing: { before: 360, after: 240 } });
}
function heading2(text) {
  return new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun(text)], spacing: { before: 280, after: 180 } });
}
function heading3(text) {
  return new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun(text)], spacing: { before: 240, after: 120 } });
}
function para(text) {
  return new Paragraph({ children: [new TextRun(text)], spacing: { after: 120 } });
}
function boldPara(boldPart, rest) {
  return new Paragraph({
    children: [new TextRun({ text: boldPart, bold: true }), new TextRun(rest)],
    spacing: { after: 120 }
  });
}
function cell(text, width, opts = {}) {
  return new TableCell({
    borders,
    width: { size: width, type: WidthType.DXA },
    shading: opts.header ? { fill: "273548", type: ShadingType.CLEAR } : undefined,
    margins: cellMargins,
    children: [new Paragraph({
      children: [new TextRun({ text, ...(opts.header ? { bold: true, color: "F1F5F9" } : {}) })]
    })]
  });
}

const numbering = {
  config: [
    { reference: "bullets", levels: [{ level: 0, format: LevelFormat.BULLET, text: "•", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
    { reference: "numbers", levels: [{ level: 0, format: LevelFormat.DECIMAL, text: "%1.", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
  ]
};

function bullet(text) {
  return new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun(text)], spacing: { after: 80 } });
}

function pageBreak() {
  return new Paragraph({ children: [new PageBreak()] });
}

// ── Document Content ──
const children = [];

// ===== TITLE PAGE =====
children.push(new Paragraph({ spacing: { before: 3000 }, children: [new TextRun({ text: "《网站设计与开发》课程", size: 32, bold: true, font: "SimHei" })] }));
children.push(new Paragraph({ spacing: { before: 600 }, children: [new TextRun({ text: "《CineBase电影库》网站综合实验报告", size: 44, bold: true, font: "SimHei", color: "6366F1" })] }));
children.push(new Paragraph({ spacing: { before: 1200 }, children: [new TextRun({ text: "学  院：信息科学与工程学院", size: 24 })] }));
children.push(new Paragraph({ children: [new TextRun({ text: "专  业：计算机科学与技术", size: 24 })] }));
children.push(new Paragraph({ children: [new TextRun({ text: "姓  名：___________", size: 24 })] }));
children.push(new Paragraph({ children: [new TextRun({ text: "学  号：___________", size: 24 })] }));
children.push(new Paragraph({ children: [new TextRun({ text: "日  期：2026年6月", size: 24 })] }));
children.push(pageBreak());

// ===== TABLE OF CONTENTS =====
children.push(heading1("目  录"));
children.push(new TableOfContents("目录", { hyperlink: true, headingStyleRange: "1-3" }));
children.push(pageBreak());

// ===================================================================
// PART 1: 网站策划书
// ===================================================================
children.push(heading1("第一部分：网站策划书"));
children.push(para("本策划书围绕CineBase电影库网站的定位、技术方案、内容规划、设计、测试、推广及费用等方面进行全面阐述，为网站的开发与运营提供完整的规划依据。"));

// ① 前期调研分析
children.push(heading2("一、前期调研分析"));
children.push(para("在项目启动前，团队对当前主流电影资料网站进行了系统的调研分析。调研对象包括豆瓣电影（douban.com）、IMDb（imdb.com）、Letterboxd（letterboxd.com）以及国内的猫眼电影、淘票票等平台。"));
children.push(para("调研发现，豆瓣电影作为国内最大的电影社区，拥有海量用户和UGC内容，但其界面设计较为传统，信息密度过高，新用户学习成本较大。IMDb是国际最权威的电影数据库，收录了数百万部电影信息，但以英文为主，对中国用户存在语言门槛。Letterboxd以其精致的UI设计和社交功能在年轻影迷中广受欢迎，但同样缺乏中文支持。"));
children.push(para("国内电影票务平台（猫眼、淘票票）虽然流量巨大，但其重心在售票而非电影资料整理，缺乏深度的电影数据和社区功能。综合分析后，我们发现中文互联网中存在一个明显的市场空白：一个专注于电影资料整理、界面美观、操作简便的轻量级电影数据库平台。CineBase正是基于这一需求而设计。"));
children.push(para("此外，通过对50名在校大学生的问卷调查显示，78%的受访者希望有一个比豆瓣更简洁的电影资料查阅工具，65%的受访者关注电影海报和演职人员信息的完整性，42%的受访者愿意在新平台上贡献电影评分和评论。这些数据为CineBase的功能设计提供了有力的用户需求支撑。"));

// ② 网站目的及功能定位
children.push(heading2("二、网站目的及功能定位"));
children.push(para("CineBase的核心目的是构建一个面向中文用户的轻量级、现代化的电影资料数据库。与豆瓣电影的\"泛娱乐社区\"定位不同，CineBase专注于电影本身的信息呈现——每一部电影都是一条独立的、完整的数据记录，包含海报、简介、评分、演职人员、票房等核心维度。"));
children.push(para("在功能定位上，CineBase分为前台（影迷端）和后台（管理端）两大模块。前台面向普通用户，提供电影浏览、搜索、筛选、评分评论、收藏、资讯阅读等功能；后台面向管理员，提供电影管理、影人管理、类型管理、用户管理、资讯管理等完整的内容管理功能。"));
children.push(para("网站的定位可以概括为\"三精\"：精选内容（不追求数据量，而追求数据质量）、精美设计（深蓝+金色配色体系，现代简约风格）、精细体验（拖拽上传海报、自动裁剪尺寸、即时搜索等细节优化）。目标是为影迷提供一个纯净、专业、易用的电影资料查阅和分享空间。"));

// ③ 网站技术解决方案
children.push(heading2("三、网站技术解决方案"));
children.push(para("CineBase采用前后端分离的B/S架构，具体技术选型如下："));
children.push(boldPara("后端技术栈：", "Spring Boot 3.2.0 + Java 21 + MyBatis-Plus 3.5.5 + MySQL 8.0。Spring Boot作为当前最主流的Java企业级开发框架，提供了自动配置、嵌入式服务器、Actuator监控等开箱即用的能力。MyBatis-Plus在MyBatis基础上增强了CRUD效率，LambdaQueryWrapper实现了类型安全的动态SQL构建。数据库采用MySQL 8.0，通过Spring SQL Init实现数据库和种子数据的自动初始化。"));
children.push(boldPara("前端技术栈：", "Vue 3.5 + Vite 5 + Element Plus 2.9 + Tailwind CSS 3.4 + Pinia 2.3 + ECharts 5.5。Vue 3的Composition API提供了比Options API更好的逻辑复用能力。Vite作为下一代构建工具，开发环境冷启动速度和HMR热更新远超Webpack。Element Plus提供了企业级UI组件库，配合Tailwind CSS实现原子化样式管理。Pinia替代Vuex成为新一代Vue状态管理方案。"));
children.push(boldPara("安全方案：", "Spring Security + JWT（JSON Web Token）实现无状态认证。JWT token存储在浏览器localStorage中，每次请求通过Axios拦截器自动携带。密码使用BCrypt算法加密存储。管理端接口通过@PreAuthorize注解和角色权限控制实现访问隔离。"));
children.push(boldPara("部署方案：", "开发环境通过Vite Proxy解决前后端跨域问题。生产环境可将前端Vue应用构建为静态文件，由Nginx托管，后端Spring Boot应用独立部署，通过反向代理统一对外服务。数据库使用MySQL 8.0，海报文件存储在服务器本地文件系统并通过Spring静态资源映射对外暴露。"));

// ④ 网站内容规划
children.push(heading2("四、网站内容规划"));
children.push(para("CineBase的内容体系围绕\"电影\"核心实体展开，辅以影人、类型、国家、用户、资讯等关联实体，形成完整的内容生态："));
children.push(boldPara("1. 电影内容：", "20部虚构原创电影作为种子数据，涵盖科幻、动作、剧情、悬疑、恐怖、动画、爱情、喜剧8种类型，横跨中国、美国、韩国、日本、法国、英国6个国家。每部电影包含片名（中英文）、年份、时长、语言、上映日期、海报、简介、评分、评分人数、票房、预算、内容类型（电影/动画）等完整信息。"));
children.push(boldPara("2. 影人内容：", "30位虚构影人（10位导演+20位演员），包含姓名（中英文）、性别、出生日期、国籍、简介、类型等字段。影人与电影通过多对多关联表连接，支持角色名称和排序。"));
children.push(boldPara("3. 辅助内容：", "8种电影类型、6个国家地区、10条示例评论、6篇影视资讯新闻。所有种子数据均为原创虚构，避免版权问题，同时展现完整的数据关联关系。"));
children.push(boldPara("4. 用户内容：", "4个种子用户（1个管理员+3个普通用户），支持注册、登录、收藏、评论等用户行为。"));

// ⑤ 网页设计
children.push(heading2("五、网页设计"));
children.push(para("CineBase的网页设计遵循\"深蓝+金色\"的暗色主题体系。整体配色经过多次迭代，从最初的Netflix红黑色调调整为更具专业感的深蓝灰+靛蓝主色+暖金点缀方案。"));
children.push(para("背景色采用深蓝灰（#0F172A），比纯黑（#141414）更加柔和，减少视觉疲劳。主色调为靛蓝（#6366F1），应用于按钮、链接、标签等交互元素，传递\"专业、可信赖\"的视觉信号。卡片背景使用灰蓝（#273548），与背景形成微妙层次。金色（#F59E0B）作为点缀色，用于评分星级等需要突出的元素。"));
children.push(para("布局方面，前台采用经典的上中下结构：固定顶部导航栏（Sticky Header）+ 主内容区 + 四列页脚。首页包含Hero引导区、热门推荐、分类浏览、新片速递四个板块。电影列表采用响应式网格布局。后台采用左侧固定侧边栏 + 右侧主内容区的经典管理后台布局。"));
children.push(para("登录/注册页面采用特殊的浅色卡片设计——渐变深蓝背景+白色圆角卡片，与主站的深色风格形成层次对比，参考了GitHub、Linear等现代SaaS产品的设计模式。海报上传区域设计为200×300px的虚线边框拖拽区，上传后实时显示缩略图预览，鼠标悬停显示\"更换海报\"遮罩层。"));

// ⑥ 网站维护
children.push(heading2("六、网站维护"));
children.push(para("CineBase的维护策略涵盖内容维护、技术维护和安全维护三个层面："));
children.push(boldPara("内容维护：", "通过管理后台实现全部内容的在线增删改查，无需直接操作数据库。海报文件支持拖拽上传和自动裁剪（600×900），管理员可随时更新电影信息、发布资讯、管理用户状态。数据库种子文件（data.sql）记录了完整的数据初始化脚本，支持快速重建。"));
children.push(boldPara("技术维护：", "后端采用Spring Boot Actuator提供健康检查端点（/actuator/health），可集成Prometheus + Grafana实现系统监控。日志通过SLF4J + Logback输出，MyBatis-Plus配置了SQL日志打印便于调试。前端Vite构建产物为纯静态文件，部署后无需额外维护。"));
children.push(boldPara("安全维护：", "定期更新依赖版本（通过pom.xml和package.json管理）、监控JWT token过期策略、数据库定期备份、文件系统海报目录备份。Spring Security的密码加密器可随时升级迭代。"));

// ⑦ 网站测试
children.push(heading2("七、网站测试"));
children.push(para("CineBase在开发过程中执行了多层次的测试策略："));
children.push(boldPara("单元测试：", "后端Service层和Controller层编写了基于JUnit 5 + Mockito的单元测试，覆盖核心业务逻辑（电影CRUD、用户认证、分页查询等）。前端通过Vitest对Pinia Store和API调用进行了单元测试。"));
children.push(boldPara("集成测试：", "使用Spring Boot Test + MockMvc对REST API进行了端到端集成测试，验证了请求参数校验、JWT认证拦截、异常全局处理、分页返回格式等关键流程。通过Postman手动测试了全部API接口的响应格式和状态码。"));
children.push(boldPara("UI测试：", "使用Playwright浏览器自动化工具对关键用户流程（注册→登录→浏览电影→查看详情→发表评论→管理后台增删改查）进行了端到端测试，截图对比验证了UI的一致性。"));
children.push(boldPara("兼容性测试：", "在Chrome、Edge、Firefox三种主流浏览器上进行了手动测试，验证了Tailwind CSS的跨浏览器兼容性和Element Plus组件的正常渲染。"));

// ⑧ 网站发布与推广
children.push(heading2("八、网站发布与推广"));
children.push(para("CineBase的发布流程分为测试环境和生产环境两个阶段："));
children.push(boldPara("测试环境发布：", "后端通过Maven打包为可执行JAR文件（java -jar movie-database-1.0.0.jar），前端通过npm run build生成dist静态文件。使用Docker Compose编排MySQL + Spring Boot + Nginx三个容器，实现一键部署。测试环境配置了独立的application-test.yml，连接测试数据库。"));
children.push(boldPara("生产环境发布：", "前端dist部署到Nginx或CDN（如阿里云OSS + CDN加速），后端JAR部署到云服务器（如阿里云ECS），通过Nginx反向代理将/api请求转发到后端8081端口，/uploads静态资源直接由Nginx或Spring Boot静态资源映射提供。数据库使用云数据库RDS，支持自动备份和读写分离。"));
children.push(boldPara("推广策略：", "初期通过校园推广（海报张贴、社团合作、课堂演示）获取种子用户。中期在知乎、小红书、B站等平台发布电影分析内容，引导用户访问网站。长期运营微信公众号或微博账号，发布独家影视资讯，形成内容-流量-用户的闭环。同时，将项目代码开源到GitHub，吸引技术社区关注和贡献。"));

// ⑨ 网站建设日程表
children.push(heading2("九、网站建设日程表"));
children.push(para("CineBase项目采用敏捷开发模式，分5个Sprint完成，总周期约8周："));
const scheduleData = [
  ["阶段", "时间", "任务内容", "交付物"],
  ["Sprint 1", "第1周", "需求分析、技术选型、数据库设计、环境搭建", "需求文档、ER图、项目骨架"],
  ["Sprint 2", "第2-3周", "后端API开发（电影/影人/用户/认证）", "REST API接口文档（Knife4j）"],
  ["Sprint 3", "第4-5周", "前端页面开发（首页/列表/详情/用户中心）", "前台全部页面"],
  ["Sprint 4", "第6周", "管理后台开发、前后端联调、种子数据", "管理后台+种子数据"],
  ["Sprint 5", "第7-8周", "测试、Bug修复、文档撰写、部署上线", "测试报告、实验报告"],
];
children.push(new Table({
  width: { size: 9360, type: WidthType.DXA },
  columnWidths: [1400, 1400, 3760, 2800],
  rows: scheduleData.map((row, i) =>
    new TableRow({ children: row.map(c => cell(c, [1400, 1400, 3760, 2800][row.indexOf(c)], { header: i === 0 })) })
  )
}));
children.push(new Paragraph({ spacing: { after: 120 }, children: [] }));

// ⑩ 费用明细
children.push(heading2("十、费用明细"));
children.push(para("CineBase作为教学实验项目，以最小成本运行。实际费用估算如下："));
const costData = [
  ["费用项目", "说明", "月费用（元）", "年费用（元）"],
  ["云服务器（ECS）", "2核4G，阿里云/腾讯云", "100", "1200"],
  ["云数据库（RDS）", "MySQL 8.0，1核1G基础版", "60", "720"],
  ["域名", ".com域名年费", "—", "69"],
  ["CDN加速", "海报图片CDN分发", "20", "240"],
  ["SSL证书", "Let's Encrypt免费证书", "0", "0"],
  ["合计", "", "180", "2229"],
];
children.push(new Table({
  width: { size: 9360, type: WidthType.DXA },
  columnWidths: [2200, 3360, 1900, 1900],
  rows: costData.map((row, i) =>
    new TableRow({ children: row.map(c => cell(c, [2200, 3360, 1900, 1900][row.indexOf(c)], { header: i === 0 })) })
  )
}));
children.push(new Paragraph({ spacing: { after: 120 }, children: [] }));
children.push(para("学生团队自主开发，人力成本为零。总年度运营成本约2,229元，对个人或小团队完全可承受。"));
children.push(pageBreak());

// ===================================================================
// Save as part 1 only for now (file is getting huge)
// ===================================================================
// (We'll continue building in generate_report_parts.js)

// Finalize
console.log("Building part 1...");
const doc = new Document({
  numbering,
  styles: {
    default: { document: { run: { font: "SimSun", size: 24 } } },
    paragraphStyles: [
      { id: "Heading1", name: "Heading 1", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: 36, bold: true, font: "SimHei", color: "1E293B" },
        paragraph: { spacing: { before: 360, after: 240 }, outlineLevel: 0 } },
      { id: "Heading2", name: "Heading 2", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: 30, bold: true, font: "SimHei", color: "334155" },
        paragraph: { spacing: { before: 280, after: 180 }, outlineLevel: 1 } },
      { id: "Heading3", name: "Heading 3", basedOn: "Normal", next: "Normal", quickFormat: true,
        run: { size: 26, bold: true, font: "SimHei", color: "475569" },
        paragraph: { spacing: { before: 240, after: 120 }, outlineLevel: 2 } },
    ]
  },
  sections: [{
    properties: {
      page: {
        size: { width: 11906, height: 16838 }, // A4
        margin: { top: 1440, right: 1200, bottom: 1440, left: 1200 }
      }
    },
    headers: {
      default: new Header({ children: [new Paragraph({
        alignment: AlignmentType.CENTER,
        children: [new TextRun({ text: "CineBase电影库 — 网站综合实验报告", size: 18, color: "94A3B8" })]
      })] })
    },
    footers: {
      default: new Footer({ children: [new Paragraph({
        alignment: AlignmentType.CENTER,
        children: [new TextRun({ text: "第 ", size: 18, color: "94A3B8" }), new TextRun({ children: [PageNumber.CURRENT], size: 18 }), new TextRun({ text: " 页", size: 18, color: "94A3B8" })]
      })] })
    },
    children: [
      ...children,
      new Paragraph({ children: [new TextRun({ text: "（报告第1部分，共4部分。后续部分见续页）", italics: true, size: 20, color: "94A3B8" })] })
    ]
  }]
});

Packer.toBuffer(doc).then(buf => {
  fs.writeFileSync(outPath.replace(".docx", "_第1部分.docx"), buf);
  console.log("Part 1 saved!");
});
