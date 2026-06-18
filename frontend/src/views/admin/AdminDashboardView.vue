<template>
  <div>
    <h2 class="text-2xl font-bold mb-6">管理仪表盘</h2>
    <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
      <div class="p-6 rounded-xl bg-surface-card"><div class="text-3xl font-bold text-primary">{{ stats.movieCount }}</div><div class="text-muted text-sm mt-1">电影总数</div></div>
      <div class="p-6 rounded-xl bg-surface-card"><div class="text-3xl font-bold text-green-400">{{ stats.userCount }}</div><div class="text-muted text-sm mt-1">用户总数</div></div>
      <div class="p-6 rounded-xl bg-surface-card"><div class="text-3xl font-bold text-blue-400">{{ stats.reviewCount }}</div><div class="text-muted text-sm mt-1">评论总数</div></div>
      <div class="p-6 rounded-xl bg-surface-card"><div class="text-3xl font-bold text-yellow-400">{{ stats.totalViews }}</div><div class="text-muted text-sm mt-1">总浏览量</div></div>
    </div>
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <router-link to="/admin/movies" class="p-6 rounded-xl bg-surface-card hover:bg-surface-hover transition text-center">
        <div class="text-3xl mb-2">🎬</div>
        <div class="font-semibold">电影管理</div>
        <div class="text-muted text-sm">添加、编辑、删除电影</div>
      </router-link>
      <router-link to="/admin/persons" class="p-6 rounded-xl bg-surface-card hover:bg-surface-hover transition text-center">
        <div class="text-3xl mb-2">👤</div>
        <div class="font-semibold">影人管理</div>
        <div class="text-muted text-sm">管理导演和演员信息</div>
      </router-link>
      <router-link to="/admin/genres" class="p-6 rounded-xl bg-surface-card hover:bg-surface-hover transition text-center">
        <div class="text-3xl mb-2">🏷</div>
        <div class="font-semibold">类型管理</div>
        <div class="text-muted text-sm">管理电影分类</div>
      </router-link>
      <router-link to="/admin/users" class="p-6 rounded-xl bg-surface-card hover:bg-surface-hover transition text-center">
        <div class="text-3xl mb-2">👥</div>
        <div class="font-semibold">用户管理</div>
        <div class="text-muted text-sm">管理用户和权限</div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminStats } from '@/api/admin'

const stats = ref({ movieCount: 0, userCount: 0, reviewCount: 0, totalViews: 0 })
onMounted(async () => {
  try { stats.value = await getAdminStats() } catch (e) { console.error(e) }
})
</script>
