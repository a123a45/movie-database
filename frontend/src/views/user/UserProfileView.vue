<template>
  <div class="max-w-2xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-8">个人中心</h1>
    <div class="bg-surface rounded-xl p-6 mb-6">
      <div class="flex items-center gap-4 mb-6">
        <el-avatar :size="64" :src="userStore.userInfo?.avatar" />
        <div>
          <h2 class="text-xl font-bold">{{ userStore.userInfo?.nickname }}</h2>
          <p class="text-muted text-sm">{{ userStore.userInfo?.email || '未设置邮箱' }}</p>
        </div>
      </div>
      <el-form label-position="top">
        <el-form-item label="昵称"><el-input v-model="profile.nickname" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="profile.email" /></el-form-item>
        <el-button type="primary" round @click="saveProfile">保存</el-button>
      </el-form>
    </div>
    <div class="bg-surface rounded-xl p-6">
      <h3 class="font-semibold mb-4">修改密码</h3>
      <el-form label-position="top">
        <el-form-item label="旧密码"><el-input v-model="pwd.oldPassword" type="password" show-password /></el-form-item>
        <el-form-item label="新密码"><el-input v-model="pwd.newPassword" type="password" show-password /></el-form-item>
        <el-button type="primary" round @click="savePassword">修改密码</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { updateProfile, changePassword } from '@/api/auth'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const profile = reactive({ nickname: '', email: '' })
const pwd = reactive({ oldPassword: '', newPassword: '' })

onMounted(() => {
  if (userStore.userInfo) {
    profile.nickname = userStore.userInfo.nickname || ''
    profile.email = userStore.userInfo.email || ''
  }
})

async function saveProfile() {
  try { await updateProfile(profile); userStore.fetchUserInfo(); ElMessage.success('保存成功') }
  catch { ElMessage.error('保存失败') }
}
async function savePassword() {
  if (!pwd.oldPassword || !pwd.newPassword) return ElMessage.warning('请填写完整')
  try { await changePassword(pwd); pwd.oldPassword = ''; pwd.newPassword = ''; ElMessage.success('密码修改成功') }
  catch { ElMessage.error('修改失败') }
}
</script>
