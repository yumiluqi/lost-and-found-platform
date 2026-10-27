<template>
  <div class="detail-container" v-loading="loading">
    <el-card v-if="postInfo">
      <div class="header">
        <h2>{{ postInfo.title }}</h2>
        <div class="tags">
          <el-tag :type="postInfo.type === 0 ? 'danger' : 'success'" style="margin-right: 10px;">
            {{ postInfo.type === 0 ? '寻物启事' : '失物招领' }}
          </el-tag>
          <el-tag :type="postInfo.status === 0 ? 'warning' : 'info'">
            {{ postInfo.status === 0 ? '进行中' : '已认领/已完成' }}
          </el-tag>
        </div>
      </div>
      
      <div class="content">
        <p><strong>分类：</strong> {{ postInfo.goodsType }}</p>
        <p><strong>地点：</strong> {{ postInfo.location }}</p>
        <p><strong>时间：</strong> {{ formatTime(postInfo.createTime) }}</p>
        <p><strong>描述：</strong> {{ postInfo.description }}</p>
        <img v-if="postInfo.imgUrl" :src="'/api' + postInfo.imgUrl" class="detail-img" />

      <!-- 仅本人可见的操作按钮 -->
      <div class="actions" v-if="isOwner">
        <el-button type="danger" plain @click="handleDelete">删除帖子</el-button>
        <el-button type="success" v-if="postInfo.status === 0" @click="handleClaim">标记为已完成</el-button>
      </div>

      <el-divider>留言区</el-divider>
      
      <!-- 评论列表 -->
      <div class="comments">
        <el-empty v-if="comments.length === 0" description="暂无留言" :image-size="60" />
        <div v-for="c in comments" :key="c.commentId" class="comment-item">
          <span class="username">用户 {{ c.userId }}：</span>
          <span>{{ c.content }}</span>
          <span class="time">{{ formatTime(c.createTime) }}</span>
        </div>
      </div>

      <!-- 发表评论 -->
      <div class="add-comment" v-if="userStore.token">
        <el-input v-model="newComment" placeholder="说点什么吧..." @keyup.enter="submitComment" />
        <el-button type="primary" @click="submitComment" style="margin-left: 10px;">发送</el-button>
      </div>
      <div v-else class="login-tip">
        登录后才能发表评论哦！<el-link type="primary" @click="$router.push('/login')">去登录</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getPostDetail, claimPost, deletePost } from '@/api/post'
import { getCommentList, addComment } from '@/api/comment'
import { ElMessage, ElMessageBox } from 'element-plus'
 

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const postInfo = ref(null)
const comments = ref([])
const newComment = ref('')

// 判断当前登录用户是不是帖子的发布者
const isOwner = computed(() => {
  return userStore.userInfo && postInfo.value && userStore.userInfo.userId === postInfo.value.userId
})

// 时间格式化工具
const formatTime = (isoString) => {
  if(!isoString) return ''
  const date = new Date(isoString)
  return `${date.getFullYear()}-${String(date.getMonth()+1).padStart(2,'0')}-${String(date.getDate()).padStart(2,'0')} ${String(date.getHours()).padStart(2,'0')}:${String(date.getMinutes()).padStart(2,'0')}`
}

const fetchData = async () => {
  loading.value = true
  const postId = route.params.id
  try {
    const [postRes, commentRes] = await Promise.all([
      getPostDetail(postId),
      getCommentList({ postId })
    ])
    postInfo.value = postRes.data
    comments.value = commentRes.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleDelete = () => {
  ElMessageBox.confirm('确定要删除该帖子吗？', '警告', { type: 'warning' }).then(async () => {
    await deletePost(postInfo.value.postId)
    ElMessage.success('删除成功')
    router.push('/')
  })
}

const handleClaim = async () => {
  try {
    await claimPost(postInfo.value.postId)
    ElMessage.success('状态已更新')
    fetchData() // 刷新页面数据
  } catch (error) {
    console.error(error)
  }
}

const submitComment = async () => {
  if (!newComment.value.trim()) return
  try {
    await addComment({ postId: postInfo.value.postId, content: newComment.value })
    ElMessage.success('评论成功')
    newComment.value = ''
    fetchData() // 刷新评论列表
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.detail-container { max-width: 800px; margin: 20px auto; padding: 20px; }
.header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #ebeef5; padding-bottom: 15px; margin-bottom: 15px;}
.header h2 { margin: 0; }
.content p { color: #333; line-height: 1.6; }
.detail-img { max-width: 100%; border-radius: 8px; margin-top: 10px; }
.actions { margin-top: 20px; text-align: right; }
.comments { margin-top: 10px; }
.comment-item { padding: 10px 0; border-bottom: 1px dashed #ebeef5; }
.username { font-weight: bold; color: #409EFF; margin-right: 10px; }
.time { font-size: 12px; color: #999; float: right; }
.add-comment { display: flex; margin-top: 20px; }
.login-tip { text-align: center; color: #666; margin-top: 20px; }
</style>