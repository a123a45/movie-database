<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-8">📋 想看</h1>
    <MovieGrid v-if="movies.length" :movies="movies" />
    <div v-else class="text-center py-20 text-muted">还没有标记想看的电影</div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { getWatchlistPage } from '@/api/favorite'
import MovieGrid from '@/components/public/MovieGrid.vue'
const movies = ref([])
onMounted(async () => {
  try { const r = await getWatchlistPage({ page: 1, size: 20 }); movies.value = r.records || [] } catch (e) { console.error(e) }
})
</script>
