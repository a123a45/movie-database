<template>
  <div>
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold">电影管理</h2>
      <el-button type="primary" @click="openDialog()">添加电影</el-button>
    </div>

    <el-table :data="movies" dark>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="片名" min-width="180" />
      <el-table-column prop="year" label="年份" width="80" />
      <el-table-column prop="rating" label="评分" width="80" />
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column label="操作" width="140">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showDialog" :title="editing.id ? '编辑电影' : '添加电影'" width="600px">
      <el-form label-position="top">
        <el-form-item label="片名"><el-input v-model="form.title" /></el-form-item>
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="年份"><el-input-number v-model="form.year" :min="1900" :max="2030" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="时长(分钟)"><el-input-number v-model="form.duration" :min="1" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="国家ID"><el-input-number v-model="form.countryId" :min="1" /></el-form-item>
        <el-form-item label="语言"><el-input v-model="form.language" /></el-form-item>
        <el-form-item label="海报">
          <div class="poster-upload-area"
               :class="{ 'is-dragover': isDragover }"
               @click="triggerFileInput"
               @dragover.prevent="isDragover = true"
               @dragleave.prevent="isDragover = false"
               @drop.prevent="handleDrop">
            <input ref="fileInput" type="file" accept="image/*" hidden @change="handleFileChange" />
            <template v-if="uploading">
              <el-icon class="is-loading"><Loading /></el-icon>
              <span>上传中...</span>
            </template>
            <template v-else-if="posterPreview">
              <img :src="posterPreview" class="poster-preview" alt="海报预览" />
              <div class="poster-overlay">点击或拖拽更换海报</div>
            </template>
            <template v-else>
              <el-icon :size="36"><Plus /></el-icon>
              <span>点击或拖拽上传海报</span>
              <span class="text-xs text-gray-400 mt-1">支持 JPG/PNG，自动裁剪为 2:3</span>
            </template>
          </div>
        </el-form-item>
        <el-form-item label="简介"><el-input v-model="form.synopsis" type="textarea" rows="4" /></el-form-item>
        <el-form-item label="票房"><el-input v-model="form.boxOffice" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.contentType" placeholder="MOVIE / ANIMATION" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { Loading, Plus } from '@element-plus/icons-vue'
import { getMoviePage } from '@/api/movie'
import { addMovie, updateMovie, deleteMovie, uploadPoster } from '@/api/admin'
import { ElMessage } from 'element-plus'

const movies = ref([])
const showDialog = ref(false)
const saving = ref(false)
const editing = reactive({})
const isDragover = ref(false)
const uploading = ref(false)
const fileInput = ref(null)
const uploadedUrl = ref('')
const form = reactive({ title: '', year: 2025, duration: 120, countryId: 1, language: '汉语普通话', posterUrl: '', synopsis: '', boxOffice: '', contentType: 'MOVIE' })

const posterPreview = computed(() => {
  if (uploadedUrl.value) return uploadedUrl.value
  if (form.posterUrl) return form.posterUrl
  return ''
})

onMounted(async () => { await loadMovies() })

async function loadMovies() {
  try { const r = await getMoviePage({ size: 50 }); movies.value = r.records || [] } catch (e) { console.error(e) }
}

function openDialog(row) {
  Object.assign(editing, row || {})
  Object.keys(form).forEach(k => form[k] = (row && row[k] !== undefined ? row[k] : form[k]))
  if (row) form.year = row.year; else { form.title = ''; form.year = 2025 }
  uploadedUrl.value = ''
  showDialog.value = true
}

function triggerFileInput() {
  fileInput.value?.click()
}

function handleFileChange(e) {
  const file = e.target.files?.[0]
  if (file) doUpload(file)
  // Reset input so same file can be re-selected
  e.target.value = ''
}

function handleDrop(e) {
  isDragover.value = false
  const file = e.dataTransfer?.files?.[0]
  if (file) doUpload(file)
}

async function doUpload(file) {
  if (!file.type.startsWith('image/')) {
    ElMessage.warning('请选择图片文件')
    return
  }
  uploading.value = true
  try {
    const fd = new FormData()
    fd.append('file', file)
    const url = await uploadPoster(fd)
    form.posterUrl = url
    uploadedUrl.value = url
    ElMessage.success('海报上传成功')
  } catch (e) {
    ElMessage.error('海报上传失败')
    console.error(e)
  } finally {
    uploading.value = false
  }
}

async function handleSave() {
  saving.value = true
  try {
    if (editing.id) { await updateMovie(form, { params: { id: editing.id } }) }
    else { await addMovie(form) }
    ElMessage.success('保存成功'); showDialog.value = false; loadMovies()
  } catch (e) { ElMessage.error('保存失败') }
  saving.value = false
}

async function handleDelete(id) {
  try { await deleteMovie(id); ElMessage.success('删除成功'); loadMovies() } catch (e) { ElMessage.error('删除失败') }
}
</script>

<style scoped>
.poster-upload-area {
  width: 200px;
  height: 300px;
  border: 2px dashed #475569;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: border-color 0.2s, background 0.2s;
  background: #1E293B;
  color: #94A3B8;
  text-align: center;
  gap: 6px;
}
.poster-upload-area:hover,
.poster-upload-area.is-dragover {
  border-color: #6366F1;
  background: #1E293B;
  color: #6366F1;
}
.poster-upload-area .poster-preview {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.poster-upload-area .poster-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 13px;
  opacity: 0;
  transition: opacity 0.2s;
}
.poster-upload-area:hover .poster-overlay {
  opacity: 1;
}
</style>
