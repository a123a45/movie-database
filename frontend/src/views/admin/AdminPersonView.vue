<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">影人管理</h2>
      <el-button type="primary" @click="openDialog()">添加影人</el-button>
    </div>

    <el-table :data="persons" dark>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="姓名" min-width="120" />
      <el-table-column prop="nameEn" label="英文名" min-width="140" />
      <el-table-column label="性别" width="70">
        <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
      </el-table-column>
      <el-table-column label="类型" width="80">
        <template #default="{ row }">
          <el-tag :type="row.type === 'DIRECTOR' ? 'warning' : 'success'" size="small">
            {{ row.type === 'DIRECTOR' ? '导演' : '演员' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nationality" label="国籍" width="100" />
      <el-table-column prop="birthDate" label="出生日期" width="110" />
      <el-table-column label="操作" width="140">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showDialog" :title="editing.id ? '编辑影人' : '添加影人'" width="600px">
      <el-form label-position="top">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="英文名"><el-input v-model="form.nameEn" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="性别">
              <el-select v-model="form.gender" style="width:100%">
                <el-option :value="1" label="男" />
                <el-option :value="2" label="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类型">
              <el-select v-model="form.type" style="width:100%">
                <el-option value="DIRECTOR" label="导演" />
                <el-option value="ACTOR" label="演员" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8"><el-form-item label="国籍"><el-input v-model="form.nationality" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="出生日期"><el-input v-model="form.birthDate" placeholder="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="简介"><el-input v-model="form.biography" type="textarea" rows="3" /></el-form-item>
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
import { getPersonPage } from '@/api/person'
import { addPerson, updatePerson, deletePerson } from '@/api/admin'
import { ElMessage } from 'element-plus'

const persons = ref([])
const showDialog = ref(false)
const saving = ref(false)
const editing = reactive({})
const form = reactive({ name: '', nameEn: '', gender: 1, type: 'ACTOR', nationality: '', birthDate: '', biography: '' })

onMounted(async () => { await load() })

async function load() {
  try { const r = await getPersonPage({ page: 1, size: 100 }); persons.value = r.records || [] } catch (e) { console.error(e) }
}

function openDialog(row) {
  Object.assign(editing, row || {})
  form.name = row?.name || ''
  form.nameEn = row?.nameEn || ''
  form.gender = row?.gender ?? 1
  form.type = row?.type || 'ACTOR'
  form.nationality = row?.nationality || ''
  form.birthDate = row?.birthDate || ''
  form.biography = row?.biography || ''
  showDialog.value = true
}

async function handleSave() {
  saving.value = true
  try {
    const data = { ...form }
    if (editing.id) {
      data.id = editing.id
      await updatePerson(data)
    } else {
      await addPerson(data)
    }
    ElMessage.success('保存成功'); showDialog.value = false; load()
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    saving.value = false
  }
}

async function handleDelete(id) {
  try { await deletePerson(id); ElMessage.success('删除成功'); load() } catch (e) { ElMessage.error('删除失败') }
}
</script>
