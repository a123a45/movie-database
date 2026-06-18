<template>
  <div>
    <!-- Hero Section -->
    <section class="relative h-[500px] flex items-center bg-gradient-to-r from-background via-surface to-background">
      <div class="max-w-7xl mx-auto px-4 w-full">
        <div class="max-w-2xl">
          <h1 class="text-5xl font-bold mb-4 leading-tight">
            发现好电影<span class="text-primary">.</span>
          </h1>
          <p class="text-lg text-muted mb-8">
            CineBase 收录了来自世界各地的优秀电影作品，帮助你发现下一部值得观看的影片。
          </p>
          <div class="flex gap-4">
            <router-link to="/movies">
              <el-button type="primary" size="large" round>浏览电影库</el-button>
            </router-link>
            <router-link to="/movies/top250">
              <el-button size="large" round class="!border !border-white/20 !text-white !bg-transparent hover:!bg-white/10">
                Top 250
              </el-button>
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- Hot Movies -->
    <section class="max-w-7xl mx-auto px-4 py-12">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold">热门推荐</h2>
        <router-link to="/movies" class="text-primary text-sm hover:underline">查看全部 →</router-link>
      </div>
      <MovieGrid v-if="hotMovies.length" :movies="hotMovies" />
      <el-skeleton v-else :rows="3" animated />
    </section>

    <!-- Genre Shortcuts -->
    <section class="max-w-7xl mx-auto px-4 py-12 border-t border-white/10">
      <h2 class="text-2xl font-bold mb-6">分类浏览</h2>
      <div class="flex flex-wrap gap-3">
        <router-link
          v-for="g in genres"
          :key="g.id"
          :to="`/movies/genre/${g.id}`"
          class="px-5 py-3 rounded-full bg-surface-card hover:bg-primary hover:text-white transition text-sm"
        >
          {{ g.name }}
        </router-link>
      </div>
    </section>

    <!-- New Movies -->
    <section class="max-w-7xl mx-auto px-4 py-12 border-t border-white/10">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold">新片速递</h2>
        <router-link to="/movies/new" class="text-primary text-sm hover:underline">查看全部 →</router-link>
      </div>
      <MovieGrid v-if="newMovies.length" :movies="newMovies" />
      <el-skeleton v-else :rows="3" animated />
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHotMovies, getNewMovies } from '@/api/movie'
import { getGenreList } from '@/api/genre'
import MovieGrid from '@/components/public/MovieGrid.vue'

const hotMovies = ref([])
const newMovies = ref([])
const genres = ref([])

onMounted(async () => {
  try {
    const hot = await getHotMovies()
    hotMovies.value = hot.records || []
    const fresh = await getNewMovies()
    newMovies.value = fresh.records || []
    genres.value = await getGenreList() || []
  } catch (e) {
    console.error('Failed to load homepage:', e)
  }
})
</script>
