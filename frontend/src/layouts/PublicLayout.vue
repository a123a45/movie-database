<template>
  <div class="min-h-screen flex flex-col bg-background">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-background/95 backdrop-blur border-b border-white/10">
      <div class="max-w-7xl mx-auto px-4 h-16 flex items-center gap-6">
        <router-link to="/" class="flex items-center gap-2 flex-shrink-0">
          <span class="text-2xl font-bold text-primary tracking-wider">CineBase</span>
        </router-link>

        <nav class="hidden md:flex items-center gap-1">
          <router-link to="/" class="nav-link" active-class="text-primary">首页</router-link>
          <router-link to="/movies" class="nav-link" active-class="text-primary">电影库</router-link>
          <router-link to="/movies/top250" class="nav-link" active-class="text-primary">排行榜</router-link>
          <router-link to="/movies/new" class="nav-link" active-class="text-primary">新片榜</router-link>
          <router-link to="/news" class="nav-link" active-class="text-primary">资讯</router-link>
        </nav>

        <div class="flex-1 max-w-md">
          <el-input
            v-model="keyword"
            placeholder="搜索电影..."
            :prefix-icon="Search"
            class="search-input"
            @keyup.enter="doSearch"
            clearable
          />
        </div>

        <div class="flex items-center gap-3 ml-auto">
          <template v-if="userStore.token">
            <el-dropdown trigger="click">
              <button class="flex items-center gap-2 text-sm text-muted hover:text-white transition">
                <el-avatar :size="32" :src="userStore.userInfo?.avatar" />
                <span class="hidden sm:inline">{{ userStore.userInfo?.nickname }}</span>
              </button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="$router.push('/user/profile')">
                    <el-icon><User /></el-icon> 个人中心
                  </el-dropdown-item>
                  <el-dropdown-item @click="$router.push('/user/favorites')">
                    <el-icon><Star /></el-icon> 我的收藏
                  </el-dropdown-item>
                  <el-dropdown-item @click="$router.push('/user/watchlist')">
                    <el-icon><Clock /></el-icon> 想看
                  </el-dropdown-item>
                  <el-dropdown-item @click="$router.push('/user/reviews')">
                    <el-icon><Edit /></el-icon> 我的评论
                  </el-dropdown-item>
                  <el-dropdown-item v-if="userStore.isAdmin()" @click="$router.push('/admin')" divided>
                    <el-icon><Setting /></el-icon> 管理后台
                  </el-dropdown-item>
                  <el-dropdown-item @click="handleLogout" divided>
                    <el-icon><SwitchButton /></el-icon> 退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login" class="text-sm text-muted hover:text-white transition">登录</router-link>
            <router-link to="/register">
              <el-button type="primary" size="small" round>注册</el-button>
            </router-link>
          </template>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex-1">
      <router-view />
    </main>

    <!-- Footer -->
    <footer class="bg-surface border-t border-white/10 py-12 mt-16">
      <div class="max-w-7xl mx-auto px-4">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8">
          <div>
            <h3 class="text-lg font-bold mb-4">CineBase</h3>
            <p class="text-sm text-muted">一个专注于电影资料整理与分享的平台</p>
          </div>
          <div>
            <h4 class="font-semibold mb-3">浏览</h4>
            <ul class="space-y-2 text-sm text-muted">
              <li><router-link to="/movies" class="hover:text-white transition">电影库</router-link></li>
              <li><router-link to="/movies/top250" class="hover:text-white transition">Top 250</router-link></li>
              <li><router-link to="/movies/new" class="hover:text-white transition">新片榜</router-link></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-3">关于</h4>
            <ul class="space-y-2 text-sm text-muted">
              <li><router-link to="/about" class="hover:text-white transition">关于我们</router-link></li>
              <li><router-link to="/news" class="hover:text-white transition">影视资讯</router-link></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-3">联系</h4>
            <ul class="space-y-2 text-sm text-muted">
              <li>Email: cinebase@movie.com</li>
              <li>© 2026 CineBase</li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { Search, User, Star, Clock, Edit, Setting, SwitchButton } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const keyword = ref('')

function doSearch() {
  if (keyword.value.trim()) {
    router.push({ path: '/search', query: { q: keyword.value.trim() } })
  }
}

async function handleLogout() {
  await userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.nav-link {
  @apply px-3 py-2 text-sm text-muted hover:text-white transition rounded-lg hover:bg-white/5;
}
.search-input {
  --el-input-bg-color: #1E293B;
  --el-input-border-color: transparent;
  --el-input-hover-border-color: #334155;
}
</style>
