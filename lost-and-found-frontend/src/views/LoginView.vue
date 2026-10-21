<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="title">系统登录</h2>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%;" :loading="loading" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="footer-tip">
        还没有账号？<el-link type="primary" @click="$router.push('/register')">去注册</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/user'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)

const form = reactive({ username: '', password: '' })

const handleLogin = async () => {
  if (!form.username || !form.password) return ElMessage.warning('请输入账号和密码')
  
  loading.value = true
  try {
    const res = await login(form)
    // 保存登录状态到 Pinia 和 localStorage
    userStore.setLoginInfo(res.data.token, res.data.userInfo)
    ElMessage.success('登录成功')
    router.push('/') // 跳转首页
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>
<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; height: calc(100vh - 80px); }
.login-card { width: 400px; padding: 20px; }
.title { text-align: center; margin-bottom: 30px; color: #409EFF; }
.footer-tip { text-align: center; font-size: 14px; color: #666; }
</style>