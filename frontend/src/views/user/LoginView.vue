<template>
  <div class="min-h-screen flex items-center justify-center px-4"
       style="background: linear-gradient(135deg, #0F172A 0%, #1E1B4B 40%, #312E81 70%, #0F172A 100%)">
    <div class="w-full max-w-sm">
      <div class="text-center mb-8">
        <router-link to="/" class="text-4xl font-bold tracking-wider text-indigo-300">CineBase</router-link>
        <p class="text-slate-400 mt-2 text-sm">登录你的账号</p>
      </div>

      <div class="bg-white rounded-2xl shadow-2xl p-8">
        <el-form @submit.prevent="handleLogin" autocomplete="off">
          <el-form-item>
            <el-input v-model="form.username" placeholder="用户名" size="large" clearable autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.password" type="password" placeholder="密码" size="large" show-password @keyup.enter="handleLogin" autocomplete="off" />
          </el-form-item>
          <el-button
            size="large" class="w-full !rounded-full !h-12 !text-base !font-semibold !text-white !border-0"
            style="background: linear-gradient(135deg, #6366F1, #8B5CF6)"
            :loading="loading" @click="handleLogin">
            登录
          </el-button>
        </el-form>
        <p class="mt-5 text-center text-sm text-slate-400">
          还没有账号？<router-link to="/register" class="font-medium text-indigo-500 hover:text-indigo-600">立即注册</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({ username: '', password: '' })

async function handleLogin() {
  if (!form.username || !form.password) return ElMessage.warning('请填写完整信息')
  loading.value = true
  try {
    await userStore.login(form)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (e) {
    ElMessage.error('用户名或密码错误')
  } finally { loading.value = false }
}
</script>
