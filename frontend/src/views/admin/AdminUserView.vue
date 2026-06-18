<template>
  <div>
    <h2 class="text-2xl font-bold mb-6">用户管理</h2>
    <el-table :data="users" dark>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="role" label="角色" width="80" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <span :class="row.status === 1 ? 'text-green-400' : 'text-red-400'">{{ row.status === 1 ? '正常' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="80">
        <template #default="{ row }">
          <el-button size="small" :type="row.status === 1 ? 'warning' : 'success'"
            @click="toggleUser(row.id)">{{ row.status === 1 ? '禁用' : '启用' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserPage, updateUserStatus } from '@/api/admin'
import { ElMessage } from 'element-plus'

const users = ref([])

onMounted(async () => {
  try { const r = await getUserPage({ page: 1, size: 50 }); users.value = r.records || [] } catch (e) { console.error(e) }
})

async function toggleUser(id) {
  try { await updateUserStatus(id); ElMessage.success('状态已更新')
    const r = await getUserPage({ page: 1, size: 50 }); users.value = r.records || []
  } catch (e) { ElMessage.error('操作失败') }
}
</script>
