<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">资讯管理</h2>
      <el-button type="primary" @click="openDialog()">添加资讯</el-button>
    </div>

    <el-table :data="newsList" dark>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" min-width="180" show-overflow-tooltip />
      <el-table-column prop="summary" label="摘要" min-width="220" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">
            {{ row.status === 1 ? '上线' : '下线' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="viewCount" label="阅读" width="70" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-button size="small" @click="toggleStatus(row)">
            {{ row.status === 1 ? '下线' : '上线' }}
          </el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showDialog" :title="editing.id ? '编辑资讯' : '添加资讯'" width="700px">
      <el-form label-position="top">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" rows="2" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" rows="8" /></el-form-item>
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="封面URL"><el-input v-model="form.coverUrl" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="关联电影ID"><el-input-number v-model="form.movieId" :min="0" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getNewsPage } from '@/api/news'
import { addNews, updateNews, deleteNews } from '@/api/admin'
import { ElMessage } from 'element-plus'

const newsList = ref([])
const showDialog = ref(false)
const saving = ref(false)
const editing = reactive({})
const form = reactive({ title: '', summary: '', content: '', coverUrl: '', movieId: 0 })

onMounted(async () => { await load() })

async function load() {
  try { const r = await getNewsPage({ page: 1, size: 50 }); newsList.value = r.records || [] } catch (e) { console.error(e) }
}

function openDialog(row) {
  Object.assign(editing, row || {})
  if (row) {
    form.title = row.title || ''
    form.summary = row.summary || ''
    form.content = row.content || ''
    form.coverUrl = row.coverUrl || ''
    form.movieId = row.movieId || 0
  } else {
    Object.assign(form, { title: '', summary: '', content: '', coverUrl: '', movieId: 0 })
  }
  showDialog.value = true
}

async function handleSave() {
  saving.value = true
  try {
    const data = { ...form }
    if (editing.id) {
      data.id = editing.id
      await updateNews(data)
    } else {
      await addNews(data)
    }
    ElMessage.success('保存成功')
    showDialog.value = false
    load()
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    saving.value = false
  }
}

async function handleDelete(id) {
  try { await deleteNews(id); ElMessage.success('删除成功'); load() } catch (e) { ElMessage.error('删除失败') }
}

async function toggleStatus(row) {
  try {
    await updateNews({ id: row.id, status: row.status === 1 ? 0 : 1 })
    ElMessage.success('状态已更新')
    load()
  } catch (e) {
    ElMessage.error('操作失败')
  }
}
</script>
