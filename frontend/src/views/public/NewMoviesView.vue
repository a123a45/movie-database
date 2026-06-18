<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">新片榜</h2>
      <span class="text-sm text-muted">最新上映的电影</span>
    </div>

    <MovieGrid v-if="movies.length" :movies="movies" />
    <el-skeleton v-else-if="loading" :rows="3" animated />
    <el-empty v-else description="暂无新片数据" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNewMovies } from '@/api/movie'
import MovieGrid from '@/components/public/MovieGrid.vue'

const movies = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const r = await getNewMovies()
    movies.value = r.records || []
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})
</script>
