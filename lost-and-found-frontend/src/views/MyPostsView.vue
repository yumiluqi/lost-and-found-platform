<template>
  <div class="my-posts-container">
    <el-card>
      <h2>我的发布</h2>
      <el-table :data="myPosts" style="width: 100%; margin-top: 20px;" v-loading="loading">
        <el-table-column prop="title" label="标题" width="200" show-overflow-tooltip />
        <el-table-column prop="goodsType" label="分类" width="100" />
        <el-table-column prop="location" label="地点" width="150" />
        <el-table-column label="类型" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.type === 0 ? 'danger' : 'success'">
              {{ scope.row.type === 0 ? '寻物' : '招领' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'warning' : 'info'">
              {{ scope.row.status === 0 ? '进行中' : '已完成' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="$router.push(`/post/${scope.row.postId}`)">查看</el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row.postId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getMyPosts, deletePost } from '@/api/post'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const loading = ref(false)
const myPosts = ref([])

const fetchMyPosts = async () => {
  loading.value = true
  try {
    const res = await getMyPosts(userStore.userInfo.userId)
    myPosts.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deletePost(id)
    ElMessage.success('删除成功')
    fetchMyPosts()
  })
}

onMounted(() => {
  fetchMyPosts()
})
</script>

<style scoped>
.my-posts-container { max-width: 1000px; margin: 20px auto; padding: 20px; }
</style>