<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-2">🏆 CineBase Top 250</h1>
    <p class="text-muted mb-8">评分最高的电影</p>
    <div v-if="loading" class="flex justify-center py-20"><el-icon class="is-loading text-4xl"><Loading /></el-icon></div>
    <template v-else>
      <div class="space-y-3">
        <div v-for="(m, i) in movies" :key="m.id"
          class="flex items-center gap-4 p-4 rounded-lg bg-surface-card hover:bg-surface-hover transition">
          <span class="text-2xl font-bold text-muted w-8 text-center">{{ i + 1 }}</span>
          <img v-if="m.posterUrl" :src="m.posterUrl" class="w-12 h-16 object-cover rounded" />
          <div class="flex-1">
            <router-link :to="`/movie/${m.id}`" class="font-semibold hover:text-primary transition">{{ m.title }}</router-link>
            <div class="text-xs text-muted">{{ m.year }} · {{ m.genres?.join(' / ') }}</div>
          </div>
          <div class="text-right">
            <div class="text-gold font-bold text-lg">{{ m.rating }}</div>
            <div class="text-xs text-muted">{{ m.ratingCount }}人</div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getTop250 } from '@/api/movie'
import { Loading } from '@element-plus/icons-vue'

const movies = ref([])
const loading = ref(true)
onMounted(async () => {
  try { const r = await getTop250(); movies.value = r.records || [] } catch (e) { console.error(e) }
  loading.value = false
})
</script>
