<template>
  <div class="max-w-3xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-8">我的评论</h1>
    <div v-if="reviews.length" class="space-y-4">
      <div v-for="r in reviews" :key="r.id" class="p-4 bg-surface-card rounded-lg">
        <div class="flex items-center gap-2 mb-2">
          <span class="font-semibold">{{ r.title }}</span>
          <span class="text-gold">★ {{ r.rating }}</span>
        </div>
        <p class="text-sm text-muted">{{ r.content }}</p>
        <span class="text-xs text-muted mt-2 block">{{ r.createTime }}</span>
      </div>
    </div>
    <div v-else class="text-center py-20 text-muted">还没有写过评论</div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { getMyReviews } from '@/api/review'
const reviews = ref([])
onMounted(async () => {
  try { const r = await getMyReviews({ page: 1, size: 50 }); reviews.value = r.records || [] } catch (e) { console.error(e) }
})
</script>
