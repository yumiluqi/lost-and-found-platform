<template>
  <div class="publish-container">
    <el-card>
      <h2>发布信息</h2>
      <el-form :model="form" label-width="80px" style="max-width: 600px; margin: 20px auto;">
        <el-form-item label="信息类型">
          <el-radio-group v-model="form.type">
            <el-radio :label="0">寻物启事（我丢了东西）</el-radio>
            <el-radio :label="1">失物招领（我捡到东西）</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="例如：丢失一个黑色钱包" />
        </el-form-item>

        <el-form-item label="物品分类">
          <el-select v-model="form.goodsType" placeholder="请选择分类">
            <el-option label="电子产品" value="电子产品" />
            <el-option label="证件" value="证件" />
            <el-option label="书籍" value="书籍" />
            <el-option label="衣物" value="衣物" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>

        <el-form-item label="地点">
          <el-input v-model="form.location" placeholder="例如：一食堂二楼" />
        </el-form-item>

        <el-form-item label="详细描述">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请描述物品特征..." />
        </el-form-item>

        <!-- 图片上传（这里用 Element Plus 的 upload 组件，配置手动上传） -->
        <el-form-item label="物品图片">
          <div style="display: flex; align-items: flex-start; gap: 20px;">
            
            <!-- 如果还没有图片，显示上传按钮 -->
            <el-upload
              v-if="!form.imgUrl"
              class="upload-demo"
              action="/api/upload"
              :headers="{ Authorization: userStore.token }"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :show-file-list="false"
              accept="image/*"
            >
              <el-button type="primary" plain>点击上传图片</el-button>
            </el-upload>
            
            <!-- 如果已经有图片了，显示图片预览和删除按钮 -->
            <div v-else class="image-preview-box">
              <img :src="'/api' + form.imgUrl" alt="预览图" />
              <!-- 悬浮的删除按钮 -->
              <div class="delete-mask" @click="removeImage">
                <el-icon><Delete /></el-icon> 删除
              </div>
            </div>

            <!-- 提示文字 -->
            <div class="el-upload__tip" style="margin-top: 5px;">
              只能上传jpg/png文件<br>且不超过10MB
            </div>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="handlePublish" :loading="loading" style="width: 100%;">发 布</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { Delete } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { addPost } from '@/api/post'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)

const form = reactive({
  type: 0,
  title: '',
  goodsType: '',
  location: '',
  description: '',
  imgUrl: ''
})

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    form.imgUrl = res.data.url // 获取后端返回的相对路径
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error(res.msg || '上传失败')
  }
}

// 移除已上传的图片
const removeImage = () => {
  form.imgUrl = '' // 把图片路径清空
  ElMessage.success('图片已移除，可以重新上传') // 给个友好的提示
}

const handleUploadError = () => {
  ElMessage.error('图片上传失败，请重试')
}

const handlePublish = async () => {
  if (!form.title ||  form.type === undefined || form.type === null){ 
    return ElMessage.warning('标题和类型必填')
  }
  
  loading.value = true
  try {
    await addPost({
      ...form,
      userId: userStore.userInfo.userId // 从Pinia中取当前用户ID
    })
    ElMessage.success('发布成功')
    router.push('/') // 返回首页
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.publish-container { max-width: 800px; margin: 0 auto; padding: 20px; }
h2 { text-align: center; color: #409EFF; }
/* 图片预览容器 */
.image-preview-box {
  position: relative;
  width: 140px;
  height: 140px;
  border-radius: 8px;
  border: 1px dashed #dcdfe6;
  overflow: hidden;
  background-color: #f8f8f8;
}

.image-preview-box img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保证图片不变形 */
}

/* 删除遮罩层（默认透明，鼠标移上去出现） */
.delete-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s;
  font-size: 14px;
}

/* 鼠标悬浮时显示遮罩 */
.image-preview-box:hover .delete-mask {
  opacity: 1;
}

/* 覆盖一下默认的上传样式，让它和图片大小对齐 */
.upload-demo {
  width: 140px;
  height: 140px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px dashed #dcdfe6;
  border-radius: 8px;
  background-color: #fafafa;
}
</style>