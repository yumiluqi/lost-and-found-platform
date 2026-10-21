<template>
  <div class="navbar">
    <!-- 新增：这里套了一层容器，用于内容居中 -->
    <div class="navbar-content">
      <div class="logo" @click="$router.push('/')" style="cursor: pointer;">🔍 失物招领平台</div>
      <div class="menu">
        <el-button text @click="$router.push('/')">首页</el-button>
        
        <!-- 已登录状态 -->
        <template v-if="userStore.token">
          <el-button type="primary" plain @click="$router.push('/publish')">发布信息</el-button>
          <el-button text @click="$router.push('/my-posts')">我的发布</el-button>
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <!-- 把 arrow-down 改成 ArrowDown 首字母大写更规范 -->
              {{ userStore.userInfo?.username }} <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>

        <!-- 未登录状态 -->
        <template v-else>
          <el-button text @click="$router.push('/login')">登录</el-button>
          <el-button type="primary" @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowDown } from '@element-plus/icons-vue'

const userStore = useUserStore()
const router = useRouter()

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
/* 外层容器：背景全宽 */
.navbar {
  width: 100%;
  height: 60px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

/* 内层容器：限制最大宽度并居中 */
.navbar-content {
  width: 90%;
  max-width: 1600px;
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo { 
  font-size: 20px; 
  font-weight: bold; 
  color: #409EFF; 
}
.menu { 
  display: flex; 
  align-items: center; 
  gap: 15px; 
}
.user-info { 
  cursor: pointer; 
  display: flex; 
  align-items: center; 
  color: #409EFF; 
  font-weight: bold;
}
</style>