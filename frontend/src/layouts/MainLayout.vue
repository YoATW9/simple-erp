<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">
        <h2>SimERP</h2>
      </div>
      <el-menu
        :router="true"
        :default-active="route.path"
        class="el-menu-vertical"
      >
        <el-menu-item index="/">
          <el-icon><DataLine /></el-icon>
          <span>Dashboard</span>
        </el-menu-item>
        <el-menu-item index="/inventory">
          <el-icon><Box /></el-icon>
          <span>Inventory</span>
        </el-menu-item>
        <el-menu-item index="/sales">
          <el-icon><ShoppingCart /></el-icon>
          <span>Sales</span>
        </el-menu-item>
        <el-menu-item index="/purchases">
          <el-icon><ShoppingBag /></el-icon>
          <span>Purchases</span>
        </el-menu-item>
        <el-menu-item index="/customers">
          <el-icon><User /></el-icon>
          <span>Customers</span>
        </el-menu-item>
        <el-menu-item index="/suppliers">
          <el-icon><OfficeBuilding /></el-icon>
          <span>Suppliers</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-content">
          <h3>{{ currentPageTitle }}</h3>
          <div class="user-menu">
            <el-dropdown>
              <span class="user-dropdown">
                Admin User
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>Profile</el-dropdown-item>
                  <el-dropdown-item>Settings</el-dropdown-item>
                  <el-dropdown-item divided>Logout</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  DataLine,
  Box,
  ShoppingCart,
  ShoppingBag,
  User,
  OfficeBuilding,
  ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()

const currentPageTitle = computed(() => {
  const path = route.path
  if (path === '/') return 'Dashboard'
  return path.charAt(1).toUpperCase() + path.slice(2)
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #dcdfe6;
}

.logo h2 {
  margin: 0;
  color: #409eff;
}

.el-aside {
  background-color: #fff;
  border-right: 1px solid #dcdfe6;
}

.el-menu-vertical {
  border-right: none;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  padding: 0 20px;
}

.header-content {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.user-dropdown {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}

.el-main {
  background-color: #f5f7fa;
  padding: 20px;
}
</style> 