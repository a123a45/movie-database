<template>
  <div class="max-w-4xl mx-auto px-4 py-12">
    <!-- Hero -->
    <section class="text-center mb-16">
      <h1 class="text-4xl font-bold mb-4">关于 <span class="text-primary">CineBase</span></h1>
      <p class="text-lg text-muted max-w-2xl mx-auto">
        一个专注于电影资料整理与分享的开放平台，致力于为影迷提供全面、准确的电影信息。
      </p>
    </section>

    <!-- Mission -->
    <section class="mb-16">
      <div class="bg-surface-card rounded-2xl p-8">
        <h2 class="text-2xl font-bold mb-4 flex items-center gap-2">
          <span class="text-primary">✦</span> 我们的使命
        </h2>
        <p class="text-muted leading-relaxed">
          CineBase 诞生于对电影的热爱。我们相信每一部电影都值得被记录、被讨论、被铭记。
          无论你是资深影迷还是偶然路过的观众，这里都有一片属于你的光影天地。
          我们致力于打造一个纯净、专业、充满温度的电影社区。
        </p>
      </div>
    </section>

    <!-- Features -->
    <section class="mb-16">
      <h2 class="text-2xl font-bold mb-8 text-center">平台特色</h2>
      <div class="grid md:grid-cols-3 gap-6">
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">🎬</div>
          <h3 class="font-bold text-lg mb-2">海量电影库</h3>
          <p class="text-sm text-muted">收录来自世界各地的优秀电影作品，涵盖动作、科幻、剧情、动画等多种类型。</p>
        </div>
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">📊</div>
          <h3 class="font-bold text-lg mb-2">多维筛选</h3>
          <p class="text-sm text-muted">按类型、国家、年份快速定位你感兴趣的电影，也可以通过关键词搜索。</p>
        </div>
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">⭐</div>
          <h3 class="font-bold text-lg mb-2">评分与评论</h3>
          <p class="text-sm text-muted">为你看过的电影打分，写下你的观后感，与其他影迷交流观点。</p>
        </div>
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">🏆</div>
          <h3 class="font-bold text-lg mb-2">排行榜</h3>
          <p class="text-sm text-muted">基于用户评分的 Top 250 榜单，以及最新上映的新片速递。</p>
        </div>
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">📰</div>
          <h3 class="font-bold text-lg mb-2">影视资讯</h3>
          <p class="text-sm text-muted">最新的电影动态、上映信息和行业新闻，让你不错过任何精彩。</p>
        </div>
        <div class="bg-surface-card rounded-xl p-6 text-center">
          <div class="text-primary text-4xl mb-4">👥</div>
          <h3 class="font-bold text-lg mb-2">影人档案</h3>
          <p class="text-sm text-muted">导演与演员的详细资料库，探索你最爱的电影人背后的故事。</p>
        </div>
      </div>
    </section>

    <!-- Tech Stack -->
    <section class="mb-16">
      <h2 class="text-2xl font-bold mb-8 text-center">技术架构</h2>
      <div class="grid md:grid-cols-2 gap-6">
        <div class="bg-surface-card rounded-xl p-6">
          <h3 class="font-bold text-lg mb-3 text-primary">后端</h3>
          <ul class="space-y-2 text-sm text-muted">
            <li>• Spring Boot 3.2 + Java 21</li>
            <li>• MyBatis-Plus + MySQL</li>
            <li>• Spring Security + JWT 认证</li>
            <li>• Knife4j API 文档</li>
          </ul>
        </div>
        <div class="bg-surface-card rounded-xl p-6">
          <h3 class="font-bold text-lg mb-3 text-primary">前端</h3>
          <ul class="space-y-2 text-sm text-muted">
            <li>• Vue 3 + Vite + Pinia</li>
            <li>• Element Plus + Tailwind CSS</li>
            <li>• ECharts 数据可视化</li>
            <li>• Vue Router 路由管理</li>
          </ul>
        </div>
      </div>
    </section>

    <!-- Contact -->
    <section class="text-center border-t border-white/10 pt-12">
      <h2 class="text-2xl font-bold mb-4">联系我们</h2>
      <p class="text-muted">
        反馈建议或合作意向，请发送邮件至
        <span class="text-primary">cinebase@movie.com</span>
      </p>
      <p class="text-sm text-muted mt-2">© 2026 CineBase. All rights reserved.</p>
    </section>
  </div>
</template>

<script setup>
// Static page — no data fetching needed
</script>
