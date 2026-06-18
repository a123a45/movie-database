<template>
  <router-link
    :to="`/movie/${movie.id}`"
    class="group block rounded-lg overflow-hidden bg-surface-card hover:bg-surface-hover transition-all duration-300 hover:scale-105 hover:shadow-2xl"
  >
    <div class="aspect-[2/3] overflow-hidden bg-surface">
      <img
        v-if="movie.posterUrl && !imgError"
        :src="movie.posterUrl"
        :alt="movie.title"
        class="w-full h-full object-cover"
        loading="lazy"
        @error="imgError = true"
      />
      <div v-if="!movie.posterUrl || imgError" class="w-full h-full flex items-center justify-center text-4xl text-muted">
        🎬
      </div>
    </div>
    <div class="p-3">
      <h3 class="text-sm font-semibold truncate group-hover:text-primary transition">
        {{ movie.title }}
      </h3>
      <div class="flex items-center gap-2 mt-1">
        <span class="text-gold text-xs font-bold">{{ movie.rating || 'N/A' }}</span>
        <span class="text-xs text-muted">{{ movie.year }}</span>
        <span v-if="movie.contentType === 'ANIMATION'" class="text-xs text-primary">动画</span>
      </div>
      <div class="flex flex-wrap gap-1 mt-1.5">
        <span
          v-for="g in movie.genres?.slice(0, 2)"
          :key="g"
          class="text-xs px-1.5 py-0.5 rounded bg-white/10 text-muted"
        >{{ g }}</span>
      </div>
    </div>
  </router-link>
</template>

<script setup>
import { ref } from 'vue'
defineProps({
  movie: { type: Object, required: true },
})
const imgError = ref(false)
</script>
