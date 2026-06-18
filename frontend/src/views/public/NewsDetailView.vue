<template>
  <div class="max-w-3xl mx-auto px-4 py-8">
    <!-- Loading -->
    <template v-if="loading">
      <el-skeleton :rows="5" animated />
    </template>

    <!-- Content -->
    <template v-else-if="news">
      <h1 class="text-3xl font-bold mb-4">{{ news.title }}</h1>
      <div class="flex items-center gap-4 text-sm text-muted mb-6 pb-6 border-b border-white/10">
        <span>{{ formatTime(news.createTime) }}</span>
        <span>{{ news.viewCount || 0 }} 次阅读</span>
      </div>

      <img
        v-if="news.coverUrl"
        :src="news.coverUrl"
        class="w-full max-h-96 object-cover rounded-xl mb-8"
        alt=""
      />

      <div class="prose prose-invert max-w-none text-muted leading-relaxed whitespace-pre-wrap">
        {{ news.content }}
      </div>

      <div class="mt-12 pt-6 border-t border-white/10">
        <el-button @click="$router.back()">← 返回列表</el-button>
      </div>
    </template>

    <el-empty v-else description="资讯不存在" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getNewsDetail } from '@/api/news'

const route = useRoute()
const news = ref(null)
const loading = ref(true)

onMounted(async () => {
  try {
    news.value = await getNewsDetail(route.params.id)
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})

function formatTime(t) {
  if (!t) return ''
  return t.substring(0, 10)
}
</script>
