<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">电影库</h1>

    <div class="flex flex-col lg:flex-row gap-8">
      <!-- Filter Sidebar -->
      <aside class="w-full lg:w-56 flex-shrink-0">
        <div class="space-y-6 sticky top-20">
          <!-- Search -->
          <div>
            <h3 class="text-sm font-semibold text-muted mb-2">搜索</h3>
            <el-input v-model="filters.keyword" placeholder="电影名..." clearable @change="search" />
          </div>

          <!-- Genre -->
          <div>
            <h3 class="text-sm font-semibold text-muted mb-2">类型</h3>
            <div class="flex flex-wrap gap-1.5">
              <button
                v-for="g in genres"
                :key="g.id"
                @click="filters.genreId = filters.genreId === g.id ? null : g.id; search()"
                :class="filters.genreId === g.id ? 'bg-primary text-white' : 'bg-surface-card hover:bg-surface-hover'"
                class="px-3 py-1 rounded-full text-xs transition"
              >{{ g.name }}</button>
            </div>
          </div>

          <!-- Country -->
          <div>
            <h3 class="text-sm font-semibold text-muted mb-2">地区</h3>
            <div class="flex flex-wrap gap-1.5">
              <button
                v-for="c in countries"
                :key="c.id"
                @click="filters.countryId = filters.countryId === c.id ? null : c.id; search()"
                :class="filters.countryId === c.id ? 'bg-primary text-white' : 'bg-surface-card hover:bg-surface-hover'"
                class="px-3 py-1 rounded-full text-xs transition"
              >{{ c.name }}</button>
            </div>
          </div>

          <!-- Sort -->
          <div>
            <h3 class="text-sm font-semibold text-muted mb-2">排序</h3>
            <el-select v-model="filters.sort" @change="search" size="small" class="w-full">
              <el-option label="评分最高" value="rating" />
              <el-option label="最新上映" value="year" />
              <el-option label="最受欢迎" value="views" />
            </el-select>
          </div>
        </div>
      </aside>

      <!-- Results -->
      <div class="flex-1">
        <div v-if="loading" class="flex justify-center py-20">
          <el-icon class="is-loading text-4xl text-primary"><Loading /></el-icon>
        </div>
        <template v-else>
          <MovieGrid v-if="movies.length" :movies="movies" />
          <div v-else class="text-center py-20 text-muted">暂无电影数据</div>
          <div v-if="total > filters.size" class="flex justify-center mt-8">
            <el-pagination
              v-model:current-page="filters.page"
              :page-size="filters.size"
              :total="total"
              layout="prev, pager, next"
              background
              @current-change="search"
            />
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMoviePage } from '@/api/movie'
import { getGenreList } from '@/api/genre'
import { getCountryList } from '@/api/country'
import MovieGrid from '@/components/public/MovieGrid.vue'
import { Loading } from '@element-plus/icons-vue'

const movies = ref([])
const genres = ref([])
const countries = ref([])
const total = ref(0)
const loading = ref(false)

const filters = reactive({
  page: 1, size: 12, keyword: '', genreId: null, countryId: null, sort: 'rating',
})

async function search() {
  loading.value = true
  try {
    const res = await getMoviePage({ ...filters })
    movies.value = res.records || []
    total.value = res.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  genres.value = await getGenreList() || []
  countries.value = await getCountryList() || []
  search()
})
</script>
