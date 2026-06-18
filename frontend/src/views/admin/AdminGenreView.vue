<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">类型管理</h2>
      <el-button type="primary" @click="openDialog()">添加类型</el-button>
    </div>
    <el-table :data="genres" dark>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="nameEn" label="英文名" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column label="操作" width="140">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="showDialog" :title="form.id ? '编辑类型' : '添加类型'" width="400px">
      <el-form label-position="top">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="英文名"><el-input v-model="form.nameEn" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getGenreList } from '@/api/genre'
import { addGenre, updateGenre, deleteGenre } from '@/api/admin'
import { ElMessage } from 'element-plus'

const genres = ref([])
const showDialog = ref(false)
const form = reactive({ id: null, name: '', nameEn: '', sort: 0 })

onMounted(async () => { genres.value = await getGenreList() || [] })

function openDialog(row) {
  Object.assign(form, row || { id: null, name: '', nameEn: '', sort: 0 })
  showDialog.value = true
}

async function handleSave() {
  try {
    if (form.id) await updateGenre(form)
    else await addGenre(form)
    ElMessage.success('保存成功'); showDialog.value = false
    genres.value = await getGenreList() || []
  } catch (e) { ElMessage.error('保存失败') }
}

async function handleDelete(id) {
  try { await deleteGenre(id); genres.value = await getGenreList() || []; ElMessage.success('删除成功') } catch (e) { ElMessage.error('删除失败') }
}
</script>
