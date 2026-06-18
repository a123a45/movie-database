<template>
  <span class="inline-flex items-center gap-0.5">
    <span v-for="i in 5" :key="i" class="text-lg" :class="i <= stars ? 'text-gold' : 'text-gray-600'">
      ★
    </span>
  </span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  rating: { type: Number, default: 0 },
  max: { type: Number, default: 10 },
})

const stars = computed(() => Math.round((props.rating / props.max) * 5))
</script>
