<template>
  <div class="min-h-screen flex items-center justify-center px-4"
       style="background: linear-gradient(135deg, #0F172A 0%, #1E1B4B 40%, #312E81 70%, #0F172A 100%)">
    <div class="w-full max-w-sm">
      <div class="text-center mb-8">
        <router-link to="/" class="text-4xl font-bold tracking-wider text-indigo-300">CineBase</router-link>
        <p class="text-slate-400 mt-2 text-sm">创建新账号</p>
      </div>

      <div class="bg-white rounded-2xl shadow-2xl p-8">
        <el-form @submit.prevent="handleRegister" autocomplete="off">
          <el-form-item>
            <el-input v-model="form.username" placeholder="用户名" size="large" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.nickname" placeholder="昵称（选填）" size="large" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.email" placeholder="邮箱（选填）" size="large" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.password" type="password" placeholder="密码（至少6位）" size="large" show-password autocomplete="new-password" />
          </el-form-item>
          <el-button
            size="large" class="w-full !rounded-full !h-12 !text-base !font-semibold !text-white !border-0"
            style="background: linear-gradient(135deg, #6366F1, #8B5CF6)"
            :loading="loading" @click="handleRegister">
            注册
          </el-button>
        </el-form>
        <p class="mt-5 text-center text-sm text-slate-400">
          已有账号？<router-link to="/login" class="font-medium text-indigo-500 hover:text-indigo-600">立即登录</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: '', password: '', nickname: '', email: '' })

async function handleRegister() {
  if (!form.username || !form.password) return ElMessage.warning('请填写必填项')
  if (form.password.length < 6) return ElMessage.warning('密码至少6位')
  loading.value = true
  try {
    await register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (e) {
    ElMessage.error('注册失败')
  } finally { loading.value = false }
}
</script>
