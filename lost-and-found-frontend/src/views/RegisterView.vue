<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="title">注册账号</h2>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" placeholder="选填" />
        </el-form-item>
        <el-form-item>
          <el-button type="success" style="width: 100%;" :loading="loading" @click="handleRegister">注 册</el-button>
        </el-form-item>
      </el-form>
      <div class="footer-tip">
        已有账号？<el-link type="primary" @click="$router.push('/login')">去登录</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: '', password: '', phone: '' })

const handleRegister = async () => {
  if (!form.username || !form.password) return ElMessage.warning('账号密码不能为空')
  
  loading.value = true
  try {
    await register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
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
.title { text-align: center; margin-bottom: 30px; color: #67C23A; }
.footer-tip { text-align: center; font-size: 14px; color: #666; }
</style>