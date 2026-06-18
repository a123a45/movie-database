import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const loading = ref(false)
  const searchKeyword = ref('')

  function setLoading(val) {
    loading.value = val
  }

  function setSearchKeyword(val) {
    searchKeyword.value = val
  }

  return { loading, searchKeyword, setLoading, setSearchKeyword }
})
