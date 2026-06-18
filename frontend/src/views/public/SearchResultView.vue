<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h2 class="text-2xl font-bold mb-2">搜索结果</h2>
    <p class="text-muted mb-8">关键词："{{ keyword }}"</p>
    <MovieGrid v-if="movies.length" :movies="movies" />
    <div v-else class="text-center py-20 text-muted">未找到相关电影</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { searchMovies } from '@/api/movie'
import MovieGrid from '@/components/public/MovieGrid.vue'

const route = useRoute()
const keyword = route.query.q || ''
const movies = ref([])

onMounted(async () => {
  if (keyword) {
    try { movies.value = await searchMovies(keyword) || [] } catch (e) { console.error(e) }
  }
})
</script>
