<template>
  <div class="home-container">
    <div class="search-bar">
      <!-- 1. 修复警告：将 :label 替换为 :value -->
      <el-radio-group v-model="queryParams.type" @change="fetchList" class="radio-group">
        <el-radio-button :value="undefined">全部</el-radio-button>
        <el-radio-button :value="0">寻物启事</el-radio-button>
        <el-radio-button :value="1">失物招领</el-radio-button>
      </el-radio-group>
      <el-input v-model="searchKeyword" placeholder="本地模拟搜索标题..." class="search-input" clearable />
    </div>

    <div v-loading="loading">
      <el-empty v-if="filteredList.length === 0" description="暂无相关信息" />
      
      <el-row :gutter="20">
        <!-- 2. 屏幕适配核心：使用响应式栅格 -->
        <!-- xs: 手机 1列，sm: 平板 2列，md: 小电脑 3列，lg: 电脑 4列，xl: 大屏 6列 -->
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" v-for="item in filteredList" :key="item.postId">
          <el-card shadow="hover" class="item-card" @click="goToDetail(item.postId)" style="cursor: pointer;">
            <div class="item-image">
              <!-- 3. 注意这里的图片路径，如果你的代理生效了，最好把 http://localhost:8080 换成你的后端实际IP(或者留空测试) -->
              <img v-if="item.imgUrl" :src="'/api' + item.imgUrl" alt="物品图片" />
              <span v-else>📦 暂无图片</span>
            </div>
            <div class="item-info">
              <h3>{{ item.title }}</h3>
              <p>地点：{{ item.location || '未知' }}</p>
              <p>分类：{{ item.goodsType || '其他' }}</p>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
                <el-tag :type="item.type === 0 ? 'danger' : 'success'">
                  {{ item.type === 0 ? '寻物' : '招领' }}
                </el-tag>
                <el-tag :type="item.status === 0 ? 'warning' : 'info'">
                  {{ item.status === 0 ? '进行中' : '已完成' }}
                </el-tag>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getPostList } from '@/api/post'


const router = useRouter()
const loading = ref(false)
const listData = ref([])
const searchKeyword = ref('')
const queryParams = ref({ type: undefined })

const filteredList = computed(() => {
  if (!searchKeyword.value) return listData.value
  return listData.value.filter(item => item.title.includes(searchKeyword.value))
})

const fetchList = async () => {
  loading.value = true
  try {
    const res = await getPostList(queryParams.value)
    listData.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const goToDetail = (id) => {
  router.push(`/post/${id}`)
}

onMounted(() => {
  fetchList()
})
</script>

<style scoped>
/* 4. 解决屏幕太空的问题：取消固定1200px，改为90%宽度和最大1400px */
.home-container { 
  width: 90%; 
  max-width: 1600px;
  margin: 0 auto; 
  padding: 20px 0; 
}

/* 5. 搜索栏适配：允许在小屏幕下自动换行 */
.search-bar { 
  margin-bottom: 20px; 
  display: flex; 
  align-items: center; 
  background: #fff; 
  padding: 15px; 
  border-radius: 8px;
  flex-wrap: wrap; /* 允许换行 */
  gap: 15px; /* 元素间距 */
}

.radio-group {
  flex-wrap: wrap;
}

/* 搜索输入框自适应宽度 */
.search-input {
  width: 300px;
  max-width: 100%;
}

.item-card { 
  margin-bottom: 20px; 
  transition: all 0.3s; 
}
.item-card:hover { 
  transform: translateY(-5px); 
}
.item-image { 
  height: 160px; 
  background-color: #f5f7fa; 
  display: flex; 
  justify-content: center; 
  align-items: center; 
  overflow: hidden; 
  margin-bottom: 15px; 
  border-radius: 4px;
}
.item-image img { 
  width: 100%; 
  height: 100%; 
  object-fit: cover; 
}
.item-info h3 { 
  margin: 0 0 10px 0; 
  font-size: 16px; 
  white-space: nowrap; 
  overflow: hidden; 
  text-overflow: ellipsis; 
}
.item-info p { 
  margin: 5px 0; 
  font-size: 13px; 
  color: #666; 
}
</style>