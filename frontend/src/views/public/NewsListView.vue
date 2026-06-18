<template>
  <div class="max-w-4xl mx-auto px-4 py-8">
    <h2 class="text-2xl font-bold mb-8">影视资讯</h2>

    <template v-if="loading">
      <div v-for="i in 3" :key="i" class="mb-6">
        <el-skeleton :rows="2" animated />
      </div>
    </template>

    <template v-else-if="newsList.length">
      <div
        v-for="item in newsList"
        :key="item.id"
        class="bg-surface-card rounded-xl p-6 mb-4 cursor-pointer hover:bg-surface transition"
        @click="$router.push(`/news/${item.id}`)"
      >
        <div class="flex gap-6">
          <img
            v-if="item.coverUrl"
            :src="item.coverUrl"
            class="w-40 h-24 object-cover rounded-lg flex-shrink-0"
            alt=""
          />
          <div class="flex-1 min-w-0">
            <h3 class="text-lg font-bold mb-2 line-clamp-1 hover:text-primary transition">
              {{ item.title }}
            </h3>
            <p class="text-sm text-muted line-clamp-2 mb-3">{{ item.summary }}</p>
            <div class="flex items-center gap-4 text-xs text-muted">
              <span>{{ formatTime(item.createTime) }}</span>
              <span>{{ item.viewCount || 0 }} 次阅读</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="flex justify-center mt-8">
        <el-pagination
          v-model:current-page="currentPage"
          :total="total"
          :page-size="10"
          background
          layout="prev, pager, next"
          @current-change="loadNews"
        />
      </div>
    </template>

    <el-empty v-else description="暂无资讯" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNewsPage } from '@/api/news'

const newsList = ref([])
const loading = ref(true)
const currentPage = ref(1)
const total = ref(0)

onMounted(() => { loadNews() })

async function loadNews(page = 1) {
  loading.value = true
  try {
    const r = await getNewsPage({ page, size: 10 })
    newsList.value = r.records || []
    total.value = r.total || 0
    currentPage.value = page
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

function formatTime(t) {
  if (!t) return ''
  return t.substring(0, 10)
}
</script>
