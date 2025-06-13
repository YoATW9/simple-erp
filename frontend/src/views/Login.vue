<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="login-header">
          <h2>SimERP Login</h2>
        </div>
      </template>
      
      <el-form
        ref="loginForm"
        :model="loginData"
        :rules="rules"
        label-position="top"
      >
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="loginData.username"
            placeholder="Enter your username"
          >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item label="Password" prop="password">
          <el-input
            v-model="loginData.password"
            type="password"
            placeholder="Enter your password"
            show-password
          >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            class="login-button"
            @click="handleLogin"
          >
            Login
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const loading = ref(false)

const loginData = reactive({
  username: '',
  password: ''
})

const rules = reactive<FormRules>({
  username: [
    { required: true, message: 'Please enter your username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Please enter your password', trigger: 'blur' }
  ]
})

const loginForm = ref<FormInstance>()

const handleLogin = async () => {
  if (!loginForm.value) return
  
  await loginForm.value.validate((valid) => {
    if (valid) {
      loading.value = true
      // TODO: Implement actual login logic here
      setTimeout(() => {
        loading.value = false
        ElMessage.success('Login successful')
        router.push('/')
      }, 1000)
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.login-card {
  width: 400px;
}

.login-header {
  text-align: center;
}

.login-header h2 {
  margin: 0;
  color: #409eff;
}

.login-button {
  width: 100%;
}
</style> 