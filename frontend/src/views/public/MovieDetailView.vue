<template>
  <div v-if="movie" class="max-w-7xl mx-auto px-4 py-8">
    <!-- Movie Header -->
    <div class="flex flex-col md:flex-row gap-8 mb-12">
      <div class="w-full md:w-72 flex-shrink-0">
        <div class="aspect-[2/3] rounded-lg overflow-hidden bg-surface">
          <img v-if="movie.posterUrl" :src="movie.posterUrl" class="w-full h-full object-cover" />
          <div v-else class="w-full h-full flex items-center justify-center text-6xl">🎬</div>
        </div>
      </div>

      <div class="flex-1">
        <h1 class="text-4xl font-bold mb-2">{{ movie.title }}</h1>
        <p v-if="movie.titleEn" class="text-muted mb-4">{{ movie.titleEn }}</p>

        <div class="flex items-center gap-4 mb-4">
          <StarRating :rating="movie.rating" />
          <span class="text-gold text-2xl font-bold">{{ movie.rating }}</span>
          <span class="text-muted text-sm">({{ movie.ratingCount }}人评分)</span>
        </div>

        <div class="grid grid-cols-2 gap-y-2 text-sm mb-6">
          <div><span class="text-muted">类型：</span>
            <span v-for="g in movie.genres" :key="g" class="text-white">{{ g }} </span>
          </div>
          <div><span class="text-muted">年份：</span><span>{{ movie.year }}</span></div>
          <div><span class="text-muted">地区：</span><span>{{ movie.countryName }}</span></div>
          <div><span class="text-muted">语言：</span><span>{{ movie.language }}</span></div>
          <div v-if="movie.duration"><span class="text-muted">时长：</span><span>{{ movie.duration }}分钟</span></div>
          <div v-if="movie.boxOffice"><span class="text-muted">票房：</span><span>{{ movie.boxOffice }}</span></div>
        </div>

        <div class="flex gap-3 mb-6">
          <el-button type="primary" round @click="toggleFav" :loading="favLoading">
            {{ isFavorited ? '❤ 已收藏' : '♡ 收藏' }}
          </el-button>
          <el-button round class="!border-white/20 !text-white" @click="toggleWatch" :loading="watchLoading">
            {{ isWatchlisted ? '✓ 已想看' : '+ 想看' }}
          </el-button>
        </div>

        <div>
          <h3 class="font-semibold mb-2">剧情简介</h3>
          <p class="text-muted leading-relaxed">{{ movie.synopsis }}</p>
        </div>
      </div>
    </div>

    <!-- Cast Section -->
    <div v-if="movie.directors?.length || movie.actors?.length" class="mb-12 border-t border-white/10 pt-8">
      <h2 class="text-2xl font-bold mb-6">演职人员</h2>
      <div v-if="movie.directors?.length" class="mb-4">
        <span class="text-muted text-sm">导演：</span>
        <router-link v-for="d in movie.directors" :key="d.personId"
          :to="`/person/${d.personId}`" class="text-primary hover:underline ml-1">{{ d.name }}</router-link>
      </div>
      <div v-if="movie.actors?.length">
        <span class="text-muted text-sm">主演：</span>
        <router-link v-for="a in movie.actors" :key="a.personId"
          :to="`/person/${a.personId}`" class="text-primary hover:underline ml-1">{{ a.name }}</router-link>
      </div>
    </div>

    <!-- Reviews -->
    <div class="border-t border-white/10 pt-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-2xl font-bold">用户评论</h2>
        <el-button type="primary" round size="small" @click="showReviewDialog = true"
          v-if="userStore.token">写评论</el-button>
      </div>

      <div v-if="reviews.length" class="space-y-4">
        <div v-for="r in reviews" :key="r.id" class="p-4 rounded-lg bg-surface-card">
          <div class="flex items-center gap-3 mb-2">
            <span class="font-semibold text-sm">{{ r.username }}</span>
            <StarRating :rating="r.rating" />
            <span class="text-gold text-sm">{{ r.rating }}</span>
          </div>
          <p class="text-sm text-muted">{{ r.content }}</p>
          <span class="text-xs text-muted mt-2 block">{{ r.createTime }}</span>
        </div>
      </div>
      <div v-else class="text-muted text-center py-8">暂无评论</div>

      <!-- Review Dialog -->
      <el-dialog v-model="showReviewDialog" title="写评论" :close-on-click-modal="false">
        <el-form>
          <el-form-item label="评分">
            <el-rate v-model="reviewForm.rating" :max="10" show-score />
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="reviewForm.content" type="textarea" rows="4" placeholder="分享你的观影感受..." />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="showReviewDialog = false">取消</el-button>
          <el-button type="primary" @click="submitReview">提交</el-button>
        </template>
      </el-dialog>
    </div>
  </div>
  <div v-else class="text-center py-20 text-muted">加载中...</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'
import { getMovieDetail } from '@/api/movie'
import { getReviewPage, submitReview as submitReviewApi } from '@/api/review'
import { toggleFavorite, toggleWatchlist } from '@/api/favorite'
import StarRating from '@/components/public/StarRating.vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const userStore = useUserStore()
const movie = ref(null)
const reviews = ref([])
const showReviewDialog = ref(false)
const isFavorited = ref(false)
const isWatchlisted = ref(false)
const favLoading = ref(false)
const watchLoading = ref(false)

const reviewForm = ref({ rating: 8, content: '' })

onMounted(async () => {
  try {
    movie.value = await getMovieDetail(route.params.id)
    const r = await getReviewPage({ movieId: route.params.id, page: 1, size: 10 })
    reviews.value = r.records || []
  } catch (e) {
    console.error(e)
  }
})

async function toggleFav() {
  if (!userStore.token) { ElMessage.warning('请先登录'); return }
  favLoading.value = true
  try {
    const msg = await toggleFavorite(route.params.id)
    isFavorited.value = !isFavorited.value
    ElMessage.success(msg)
  } catch (e) { ElMessage.error('操作失败') }
  finally { favLoading.value = false }
}

async function toggleWatch() {
  if (!userStore.token) { ElMessage.warning('请先登录'); return }
  watchLoading.value = true
  try {
    const msg = await toggleWatchlist(route.params.id)
    isWatchlisted.value = !isWatchlisted.value
    ElMessage.success(msg)
  } catch (e) { ElMessage.error('操作失败') }
  finally { watchLoading.value = false }
}

async function submitReview() {
  try {
    await submitReviewApi({ movieId: Number(route.params.id), ...reviewForm.value })
    ElMessage.success('评论成功')
    showReviewDialog.value = false
    const r = await getReviewPage({ movieId: route.params.id, page: 1, size: 10 })
    reviews.value = r.records || []
  } catch (e) { ElMessage.error('评论失败') }
}
</script>
