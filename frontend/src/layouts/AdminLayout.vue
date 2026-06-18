<template>
  <div class="flex h-screen bg-background">
    <!-- Sidebar -->
    <aside class="w-56 bg-surface border-r border-white/10 flex flex-col flex-shrink-0">
      <div class="h-16 flex items-center px-5 border-b border-white/10">
        <router-link to="/" class="text-xl font-bold text-primary">CineBase Admin</router-link>
      </div>
      <nav class="flex-1 py-4 px-3 space-y-1 overflow-y-auto">
        <router-link to="/admin" class="nav-item" active-class="active">
          <el-icon><DataAnalysis /></el-icon> 仪表盘
        </router-link>
        <router-link to="/admin/movies" class="nav-item" active-class="active">
          <el-icon><VideoCamera /></el-icon> 电影管理
        </router-link>
        <router-link to="/admin/persons" class="nav-item" active-class="active">
          <el-icon><User /></el-icon> 影人管理
        </router-link>
        <router-link to="/admin/genres" class="nav-item" active-class="active">
          <el-icon><Collection /></el-icon> 类型管理
        </router-link>
        <router-link to="/admin/users" class="nav-item" active-class="active">
          <el-icon><Avatar /></el-icon> 用户管理
        </router-link>
        <router-link to="/admin/news" class="nav-item" active-class="active">
          <el-icon><Document /></el-icon> 资讯管理
        </router-link>
      </nav>
      <div class="p-3 border-t border-white/10">
        <router-link to="/" class="nav-item">
          <el-icon><HomeFilled /></el-icon> 返回前台
        </router-link>
      </div>
    </aside>

    <!-- Main -->
    <div class="flex-1 flex flex-col overflow-hidden">
      <header class="h-16 flex items-center px-6 bg-surface border-b border-white/10 flex-shrink-0">
        <h1 class="text-lg font-semibold">{{ route.meta?.title || '管理后台' }}</h1>
        <div class="ml-auto flex items-center gap-4">
          <span class="text-sm text-muted">{{ userStore.userInfo?.nickname }}</span>
          <el-button text @click="handleLogout">退出</el-button>
        </div>
      </header>
      <main class="flex-1 overflow-auto p-6">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { DataAnalysis, VideoCamera, User, Collection, Avatar, Document, HomeFilled } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

async function handleLogout() {
  await userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.nav-item {
  @apply flex items-center gap-3 px-3 py-2.5 text-sm text-muted rounded-lg hover:bg-white/5 hover:text-white transition;
}
.nav-item.active {
  @apply bg-primary/20 text-primary font-medium;
}
</style>
