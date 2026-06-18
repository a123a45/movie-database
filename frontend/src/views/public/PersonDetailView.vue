<template>
  <div v-if="person" class="max-w-7xl mx-auto px-4 py-8">
    <div class="flex flex-col md:flex-row gap-8 mb-12">
      <div class="w-48 h-48 rounded-full overflow-hidden bg-surface flex-shrink-0 mx-auto md:mx-0">
        <img v-if="person.avatar" :src="person.avatar" class="w-full h-full object-cover" />
        <div v-else class="w-full h-full flex items-center justify-center text-5xl">👤</div>
      </div>
      <div>
        <h1 class="text-3xl font-bold">{{ person.name }}</h1>
        <p v-if="person.nameEn" class="text-muted">{{ person.nameEn }}</p>
        <div class="flex gap-4 mt-3 text-sm text-muted">
          <span>{{ person.type === 'DIRECTOR' ? '导演' : '演员' }}</span>
          <span v-if="person.nationality">{{ person.nationality }}</span>
          <span v-if="person.birthDate">{{ person.birthDate }}</span>
        </div>
        <p class="mt-4 text-muted leading-relaxed">{{ person.biography }}</p>
      </div>
    </div>
    <div class="border-t border-white/10 pt-8">
      <h2 class="text-2xl font-bold mb-6">作品列表</h2>
      <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
        <router-link v-for="f in person.films" :key="f.movieId" :to="`/movie/${f.movieId}`"
          class="group block rounded-lg overflow-hidden bg-surface-card hover:bg-surface-hover transition">
          <div class="aspect-[2/3] bg-surface flex items-center justify-center">
            <img v-if="f.posterUrl" :src="f.posterUrl" class="w-full h-full object-cover" />
            <span v-else class="text-3xl">🎬</span>
          </div>
          <div class="p-3">
            <p class="text-sm font-semibold truncate group-hover:text-primary">{{ f.movieTitle }}</p>
            <p class="text-xs text-muted">{{ f.year }} · {{ f.roleName || f.roleType }}</p>
          </div>
        </router-link>
      </div>
    </div>
  </div>
  <div v-else class="text-center py-20 text-muted">加载中...</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getPersonDetail } from '@/api/person'

const route = useRoute()
const person = ref(null)
onMounted(async () => {
  try { person.value = await getPersonDetail(route.params.id) } catch (e) { console.error(e) }
})
</script>
