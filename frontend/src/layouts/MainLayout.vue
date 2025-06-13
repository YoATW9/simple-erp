<template>
  <el-container class="layout-container" :class="{ 'dark': isDark }">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside">
      <div class="logo" :class="{ 'is-collapse': isCollapse }">
        <span v-if="!isCollapse">SimERP</span>
        <el-icon v-else><Monitor /></el-icon>
      </div>
      <el-menu
        :router="true"
        :default-active="route.path"
        class="el-menu-vertical"
        :collapse="isCollapse"
        :collapse-transition="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/">
          <el-icon><DataLine /></el-icon>
          <template #title>Dashboard</template>
        </el-menu-item>
        <el-menu-item index="/inventory">
          <el-icon><Box /></el-icon>
          <template #title>Inventory</template>
        </el-menu-item>
        <el-menu-item index="/sales">
          <el-icon><Sell /></el-icon>
          <template #title>Sales</template>
        </el-menu-item>
        <el-menu-item index="/purchases">
          <el-icon><ShoppingCart /></el-icon>
          <template #title>Purchases</template>
        </el-menu-item>
        <el-menu-item index="/customers">
          <el-icon><User /></el-icon>
          <template #title>Customers</template>
        </el-menu-item>
        <el-menu-item index="/suppliers">
          <el-icon><Van /></el-icon>
          <template #title>Suppliers</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button
            type="text"
            @click="toggleCollapse"
            class="collapse-btn"
          >
            <el-icon>
              <component :is="isCollapse ? 'Expand' : 'Fold'" />
            </el-icon>
          </el-button>
        </div>
        <div class="header-right">
          <ThemeSwitch />
          <el-dropdown>
            <span class="user-dropdown">
              Admin
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
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import ThemeSwitch from '@/components/ThemeSwitch.vue'
import { useTheme } from '@/composables/useTheme'
import {
  Monitor,
  DataLine,
  Box,
  Sell,
  ShoppingCart,
  User,
  Van,
  Expand,
  Fold,
  ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()
const isCollapse = ref(false)
const { isDark } = useTheme()

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.layout-container.dark {
  background-color: #1a1a1a;
  color: #e5eaf3;
}

.aside {
  background-color: #304156;
  transition: width 0.3s;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  background-color: #2b3649;
}

.logo.is-collapse {
  font-size: 0;
}

.el-menu-vertical {
  border-right: none;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.dark .header {
  background-color: #242424;
  border-bottom-color: #363637;
  color: #e5eaf3;
}

.header-left {
  display: flex;
  align-items: center;
}

.collapse-btn {
  font-size: 20px;
  padding: 0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: inherit;
}

.user-dropdown .el-icon {
  margin-left: 5px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

.dark .el-main {
  background-color: #1a1a1a;
  color: #e5eaf3;
}

/* Dark theme overrides */
.dark .el-button {
  color: #e5eaf3;
}

.dark .el-button:hover {
  color: var(--el-color-primary);
}

.dark .el-dropdown-menu {
  background-color: #242424;
  border-color: #363637;
}

.dark .el-dropdown-menu__item {
  color: #e5eaf3;
}

.dark .el-dropdown-menu__item:hover {
  background-color: #363637;
  color: #e5eaf3;
}

.dark .el-dropdown-menu__item:not(.is-disabled):hover {
  background-color: #363637;
  color: #e5eaf3;
}
</style> 