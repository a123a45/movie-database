<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">{{ title }}</h1>
    <div v-if="loading" class="flex justify-center py-20"><el-icon class="is-loading text-4xl"><Loading /></el-icon></div>
    <MovieGrid v-else-if="movies.length" :movies="movies" />
    <div v-else class="text-center py-20 text-muted">暂无电影</div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getMoviePage } from '@/api/movie'
import { getGenreList } from '@/api/genre'
import { getCountryList } from '@/api/country'
import MovieGrid from '@/components/public/MovieGrid.vue'
import { Loading } from '@element-plus/icons-vue'

const route = useRoute()
const movies = ref([])
const loading = ref(true)

const title = computed(() => {
  if (route.params.genreId) return '类型浏览'
  if (route.params.countryId) return '地区浏览'
  if (route.params.year) return `${route.params.year}年电影`
  return '分类浏览'
})

onMounted(async () => {
  const params = {}
  if (route.params.genreId) params.genreId = route.params.genreId
  if (route.params.countryId) params.countryId = route.params.countryId
  if (route.params.year) params.year = route.params.year
  try {
    const r = await getMoviePage(params)
    movies.value = r.records || []
  } catch (e) { console.error(e) }
  loading.value = false
})
</script>
